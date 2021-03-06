package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.AbstractStepResult.failed;
import static org.jbehave.core.steps.AbstractStepResult.notPerformed;
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
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepResult;
import org.jbehave.core.steps.StepType;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AbstractPage;

public class AutoStepCreator extends StepCreator {
    
    private StepMonitor stepMonitor;
    private boolean dryRun = false;
    private Object instance;
    private final StepMatcher stepMatcher;

    public AutoStepCreator(Object stepsType, InjectableStepsFactory stepsFactory, ParameterConverters parameterConverters, StepMatcher stepMatcher, StepMonitor stepMonitor) {
        super((Class<?>) ((stepsType instanceof Class<?>) ? stepsType : stepsType.getClass())
        , stepsFactory, parameterConverters, new ParameterControls(), stepMatcher, stepMonitor);
        this.stepMonitor = stepMonitor;
        this.instance = stepsType;
        this.stepMatcher = stepMatcher;
    }
    
    @Override
    public Map<String, String> matchedParameters(final Method method, final String stepAsString, 
            final String stepWithoutStartingWord, final Map<String, String> namedParameters) {
        Map<String, String> temp = new HashMap<String, String>();
        if (stepMatcher.find(stepWithoutStartingWord)){
            temp = super.matchedParameters(method, stepAsString, stepWithoutStartingWord, namedParameters);
        }
        return temp;
    }
    
    @Override
    public Object stepsInstance() {
        return instance;
    }

    public AutoStepCreator(Embedder embedder, AbstractPage currentPage, Class<? extends AbstractPage> currentPageClass) {
        this(currentPage, embedder.stepsFactory(), embedder.configuration().parameterConverters(), new FauxMatcher(), new SilentStepMonitor());
    }
    
    public AutoStepCreator(Embedder embedder) {
        this("", embedder.stepsFactory(), embedder.configuration().parameterConverters(), 
                new FauxMatcher(), new SilentStepMonitor());
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
    
    public NullPointerStep createNullPointerStep(String stepAsString, String message){
        return new NullPointerStep(stepAsString, new NullPointerException(message));
    }
    

    public Step createNullPointerStep(String stepAsString, NullPointerException e) {
        return new NullPointerStep(stepAsString, e);
    }

    
    public class NullPointerStep extends ParametrisedStep {
        
        private final String stepAsString;
        private final NullPointerException e;
        
        public NullPointerStep(String stepAsString, NullPointerException e){
            super(stepAsString, null, null, null);
            this.stepAsString = stepAsString;
            this.e = e;
        }

        @Override
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, e));
        }

        @Override
        public StepResult doNotPerform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, e));
        }
        
    }
    
    public PopupStep createPopupStep(String stepAsString){
        return new PopupStep(stepAsString);
    }
    
    public class PopupStep extends ParametrisedStep {
        private final String stepAsString;
        
        public PopupStep(String stepAsString){
            super(stepAsString, null, null, null);
            this.stepAsString = stepAsString;
        }

        @Override
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return successful(stepAsString);
        }

        @Override
        public StepResult doNotPerform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return notPerformed(stepAsString);
        }
        
        public String stepAsString(){
            return stepAsString;
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
    

    
    public class AutoPageStep extends ParametrisedStep {
        
        private final String stepAsString;
        private final Method method;
        private Object[] parameters;
        private Object instance;

        public AutoPageStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
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
        }
        
        
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            try {
                stepMonitor.performing(stepAsString, dryRun);
                if (!dryRun) {
                    try {
                        Object result = method.invoke(instance, parameters);
                        Log.debug("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                    } catch (AssertionError e){
                        return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, e));
                    }
                }
                return successful(stepAsString);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause));
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t));
            }
        }


        public String stepAsString() {
            return stepAsString;
        }
    }
    
    public EmptyAutoStep createEmptyStep(String stepAsString){
        return new EmptyAutoStep(stepAsString);
    }
    
    public class EmptyAutoStep extends AbstractStep {
        private final String stepAsString;
        public EmptyAutoStep(String stepAsString){
            this.stepAsString = stepAsString;
        }
        @Override
        public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return successful(stepAsString);
        }
        @Override
        public StepResult doNotPerform(UUIDExceptionWrapper storyFailureIfItHappened) {
            return notPerformed(stepAsString);
        }
    }

    public SaveVariableStep createSaveVariableStep(PendingStep step, Object instance, Method method){
        return new SaveVariableStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), new HashMap<String, String>());
    }
    
    public SaveVariableStep createSaveVariableStep(PendingStep step, Object instance, Method method, Object ...parameters){
        return new SaveVariableStep(instance, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), parameters);
    }
    
    public class SaveVariableStep extends ParametrisedStep {
        
        private final String stepAsString;
        private final Method method;
        private Object[] parameters;
        private Object instance;

        public SaveVariableStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
            this.instance = instance; 
            this.stepAsString = stepAsString;
            this.method = method;
            this.parameters = new Object[]{};
            
        }

        public String stepAsString(){
            return stepAsString;
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
                    AutoStepVariables.setComparator(stepAsString, result);
                    Log.info("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                }
                return successful(stepAsString);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause));
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t));
            }
        }
    }


    public ValidationStep createValidationStep(PendingStep step, Object elementClass, Method method, Object[] parameters) {
        return new ValidationStep(elementClass, step.stepAsString(), method, step.stepAsString().substring(step.stepAsString().indexOf(" ")), parameters);
    }
    
    public class ValidationStep extends ParametrisedStep {
        
        private final String stepAsString;
        private final Method method;
        private Object[] parameters;
        private Object instance;

        public ValidationStep(Object instance, String stepAsString, Method method, String stepWithoutStartingWord, Map<String, String> namedParameters) {
            super(stepAsString, method, stepWithoutStartingWord, namedParameters);
            this.instance = instance; 
            this.stepAsString = stepAsString;
            this.method = method;
            this.parameters = new Object[]{};
            
        }

        public String stepAsString(){
            return stepAsString;
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
                        Log.debug(e);
                    }
                    if (result.equals(false)){
                        String failure = String.format("Executing method %s%s returned %s, for step %s", 
                                method.getName(), asList(parameters), result, stepAsString);
                        return failed(stepAsString, new UUIDExceptionWrapper(failure));
                    }
                    Log.debug("Executing method %s%s returned %s", method.getName(), asList(parameters), result);
                }
                return successful(stepAsString);
            } catch (ParameterNotFound e) {
                // step parametrisation failed, return pending StepResult
                return pending(stepAsString);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof RestartingScenarioFailure) {
                    throw (RestartingScenarioFailure) e.getCause();
                }
                Throwable failureCause = e.getCause();
                if (failureCause instanceof UUIDExceptionWrapper) {
                    failureCause = failureCause.getCause();
                }
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, failureCause));
            } catch (Throwable t) {
                return failed(stepAsString, new UUIDExceptionWrapper(stepAsString, t));
            }
        }
    }
    
}
