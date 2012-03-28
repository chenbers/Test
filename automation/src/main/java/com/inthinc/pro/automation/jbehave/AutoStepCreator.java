package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.AbstractStepResult.failed;
import static org.jbehave.core.steps.AbstractStepResult.pending;
import static org.jbehave.core.steps.AbstractStepResult.successful;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.RestartingScenarioFailure;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.StepPattern;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepCreator;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepResult;
import org.jbehave.core.steps.StepType;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.utils.MasterTest;
import com.thoughtworks.paranamer.NullParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class AutoStepCreator extends StepCreator {
    
    private Class<?> stepsType;
    private final InjectableStepsFactory stepsFactory;
    private final ParameterConverters parameterConverters;
    private StepMonitor stepMonitor;
    private Paranamer paranamer = new NullParanamer();
    private boolean dryRun = false;

    public AutoStepCreator(Class<?> stepsType, InjectableStepsFactory stepsFactory, ParameterConverters parameterConverters, StepMatcher stepMatcher, StepMonitor stepMonitor) {
        super(stepsType, stepsFactory, parameterConverters, stepMatcher, stepMonitor);
        this.stepMonitor = stepMonitor;
        this.parameterConverters = parameterConverters;
        this.stepsType = stepsType;
        this.stepsFactory = stepsFactory;
    }

    public AutoStepCreator(Embedder embedder, AbstractPage currentPage, Class<? extends AbstractPage> currentPageClass) {
        this(currentPageClass, embedder.stepsFactory(), embedder.configuration().parameterConverters(), new FauxMatcher(), new SilentStepMonitor());
    }
    
    public static class FauxMatcher implements StepMatcher {

        @Override
        public boolean matches(String stepWithoutStartingWord) {
            return true;
        }

        @Override
        public boolean find(String stepWithoutStartingWord) {
            return true;
        }

        @Override
        public String parameter(int matchedPosition) {
            return "";
        }

        @Override
        public String[] parameterNames() {
            return new String[]{};
        }

        @Override
        public StepPattern pattern() {
            return new StepPattern(StepType.GIVEN, "","");
        }
        
    }

    
    public AutoPageStep createPageStep(PendingStep step, Object instance, Method method){
        return new AutoPageStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), new HashMap<String, String>());
    }
    
    public AutoPageStep createPageStep(PendingStep step, Object instance, Method method, Object ...parameters){
        return new AutoPageStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), parameters);
    }
    
    public AutoPageStep createPageStep(PendingStep step, Object instance, Method method, Boolean expectedResult, Object ...parameters){
        return new AutoPageStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), expectedResult, parameters);
    }
    

    
    public class AutoPageStep extends ParameterizedStep {
        
        private String parametrisedStep;
        private final String stepAsString;
        private final Method method;
        private Object[] parameters;
        private Object instance;
        private boolean expectedResult;

        public AutoPageStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
            stepsType = instance.getClass();
            this.instance = instance; 
            this.stepAsString = stepAsString;
            this.method = method;
            this.parameters = new Object[]{};
            
        }
        
        
        public AutoPageStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Object[] parameters) {
            this(instance, stepAsString, method, stepWithoutStartingWord, new HashMap<String, String>());
            this.parameters = parameters;
        }
        
        public AutoPageStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, boolean expectedResult, Object[] parameters) {
            this(instance, stepAsString, method, stepWithoutStartingWord, parameters);
            this.expectedResult = expectedResult;
        }
        
        
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            try {
                stepMonitor.performing(stepAsString, dryRun);
                if (!dryRun) {
                    Object result = method.invoke(instance, parameters);
                    MasterTest.print("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                }
                return successful(stepAsString).withParameterValues(parametrisedStep);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString).withParameterValues(parametrisedStep);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause)).withParameterValues(
                        parametrisedStep);
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t)).withParameterValues(
                        parametrisedStep);
            }
        }
    }
    

    public SaveVariableStep createSaveVariableStep(PendingStep step, Object instance, Method method){
        return new SaveVariableStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), new HashMap<String, String>());
    }
    
    public SaveVariableStep createSaveVariableStep(PendingStep step, Object instance, Method method, Object ...parameters){
        return new SaveVariableStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), parameters);
    }
    
    public class SaveVariableStep extends ParameterizedStep {
        
        private Object[] convertedParameters;
        private String parametrisedStep;
        private final String stepAsString;
        private final Method method;
        private final String stepWithoutStartingWord;
        private Object[] parameters;
        private Object instance;
        private boolean expectedResult;

        public SaveVariableStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
            stepsType = instance.getClass();
            this.instance = instance; 
            this.stepAsString = stepAsString;
            this.method = method;
            this.stepWithoutStartingWord = stepWithoutStartingWord;
            this.parameters = new Object[]{};
            
        }
        
        
        public SaveVariableStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Object[] parameters) {
            this(instance, stepAsString, method, stepWithoutStartingWord, new HashMap<String, String>());
            this.parameters = parameters;
        }
        
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            try {
                stepMonitor.performing(stepAsString, dryRun);
                if (!dryRun) {
                    Object result = method.invoke(instance, parameters);
                    MasterTest.setComparator(stepAsString, result);
                    MasterTest.print("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                }
                return successful(stepAsString).withParameterValues(parametrisedStep);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString).withParameterValues(parametrisedStep);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause)).withParameterValues(
                        parametrisedStep);
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t)).withParameterValues(
                        parametrisedStep);
            }
        }
    }


    public ValidationStep createValidationStep(PendingStep step, Object elementClass, Method method, Object[] parameters) {
        return new ValidationStep(elementClass, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), parameters);
    }
    
    public class ValidationStep extends ParameterizedStep {
        
        private Object[] convertedParameters;
        private String parametrisedStep;
        private final String stepAsString;
        private final Method method;
        private final String stepWithoutStartingWord;
        private Object[] parameters;
        private Object instance;
        private boolean expectedResult;

        public ValidationStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
            stepsType = instance.getClass();
            this.instance = instance; 
            this.stepAsString = stepAsString;
            this.method = method;
            this.stepWithoutStartingWord = stepWithoutStartingWord;
            this.parameters = new Object[]{};
            
        }
        
        
        public ValidationStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Object[] parameters) {
            this(instance, stepAsString, method, stepWithoutStartingWord, new HashMap<String, String>());
            this.parameters = parameters;
        }
        
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            try {
                stepMonitor.performing(stepAsString, dryRun);
                if (!dryRun) {
                    Object result = null;
                    try {
                        result = method.invoke(instance, parameters);
                    } catch (AssertionError e ){
                        result = false;
                        MasterTest.print(e);
                    }
                    if (result.equals(false)){
                        String failure = String.format("Executing method %s%s returned %s, for step %s", 
                                method.getName(), asList(parameters), result, stepAsString);
                        return failed(stepAsString, new UUIDExceptionWrapper(failure)).withParameterValues(parametrisedStep);
                    }
                    MasterTest.print("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                }
                return successful(stepAsString).withParameterValues(parametrisedStep);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString).withParameterValues(parametrisedStep);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause)).withParameterValues(
                        parametrisedStep);
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t)).withParameterValues(
                        parametrisedStep);
            }
        }
    }

}