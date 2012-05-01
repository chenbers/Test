package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.annotations.AfterScenario.Outcome.ANY;
import static org.jbehave.core.annotations.AfterScenario.Outcome.FAILURE;
import static org.jbehave.core.annotations.AfterScenario.Outcome.SUCCESS;
import static org.jbehave.core.steps.StepType.GIVEN;
import static org.jbehave.core.steps.StepType.THEN;
import static org.jbehave.core.steps.StepType.WHEN;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.BeforeOrAfterStep;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepCollector.Stage;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Steps;

import com.inthinc.pro.automation.logging.Log;

public class AutoSteps extends Steps {

    private final Object instance;
    private InjectableStepsFactory stepsFactory;
    private final Configuration configuration;
    

    public AutoSteps(Configuration configuration, Object instance) {
        super(configuration, instance);
        this.instance = instance;
        this.stepsFactory = new AutoStepsFactory(configuration, this);
        this.configuration = configuration;
    }

    public Object getInstance(){
        return instance;
    }
    
    public List<StepCandidate> listCandidates() {
        List<StepCandidate> candidates = new ArrayList<StepCandidate>();
        try {
            for (Method method : allMethods()) {
                if (method.isAnnotationPresent(Given.class)) {
                    Given annotation = method.getAnnotation(Given.class);
                    String value = annotation.value();
                    int priority = annotation.priority();
                    addCandidate(candidates, method, GIVEN, value, priority);
                    addCandidatesFromAliases(candidates, method, GIVEN, priority);
                }
                if (method.isAnnotationPresent(When.class)) {
                    When annotation = method.getAnnotation(When.class);
                    String value = annotation.value();
                    int priority = annotation.priority();
                    addCandidate(candidates, method, WHEN, value, priority);
                    addCandidatesFromAliases(candidates, method, WHEN, priority);
                }
                if (method.isAnnotationPresent(Then.class)) {
                    Then annotation = method.getAnnotation(Then.class);
                    String value = annotation.value();
                    int priority = annotation.priority();
                    addCandidate(candidates, method, THEN, value, priority);
                    addCandidatesFromAliases(candidates, method, THEN, priority);
                }
            }
            return candidates;
        } catch (DuplicateCandidateFound e){
            Log.info(e);
            throw e;
        }
    }
    
    private StepCandidate createCandidate(Method method, StepType stepType, String stepPatternAsString, int priority,
            Configuration configuration) {
        return new AutoStepCandidate(stepPatternAsString, priority, stepType, method, instance, stepsFactory,
                configuration.keywords(), configuration.stepPatternParser(), configuration.parameterConverters());
    }
    
    private void addCandidate(List<StepCandidate> candidates, Method method, StepType stepType,
            String stepPatternAsString, int priority) {
        checkForDuplicateCandidates(candidates, stepType, stepPatternAsString);
        StepCandidate candidate = createCandidate(method, stepType, stepPatternAsString, priority, configuration);
        candidate.useStepMonitor(configuration.stepMonitor());
        candidate.useParanamer(configuration.paranamer());
        candidate.doDryRun(configuration.storyControls().dryRun());
        if (method.isAnnotationPresent(Composite.class)) {
            candidate.composedOf(method.getAnnotation(Composite.class).steps());
        }
        candidates.add(candidate);
    }
    
    private void checkForDuplicateCandidates(List<StepCandidate> candidates, StepType stepType, String patternAsString) {
        for (StepCandidate candidate : candidates) {
            if (candidate.getStepType() == stepType && candidate.getPatternAsString().equals(patternAsString)) {
                throw new DuplicateCandidateFound(stepType, patternAsString);
            }
        }
    }

    private void addCandidatesFromAliases(List<StepCandidate> candidates, Method method, StepType stepType, int priority) {
        if (method.isAnnotationPresent(Aliases.class)) {
            String[] aliases = method.getAnnotation(Aliases.class).values();
            for (String alias : aliases) {
                addCandidate(candidates, method, stepType, alias, priority);
            }
        }
        if (method.isAnnotationPresent(Alias.class)) {
            String alias = method.getAnnotation(Alias.class).value();
            addCandidate(candidates, method, stepType, alias, priority);
        }
    }

    

    private List<Method> allMethods() {
        if (instance instanceof Class<?>){
            return asList(((Class<?>) instance).getMethods());
        }
        return asList(instance.getClass().getMethods());
    }
    
    
    public List<BeforeOrAfterStep> listBeforeOrAfterScenario(ScenarioType type) {
        List<BeforeOrAfterStep> steps = new ArrayList<BeforeOrAfterStep>();
        steps.addAll(scenarioStepsHaving(type, Stage.BEFORE, BeforeScenario.class));
        steps.addAll(scenarioStepsHaving(type, Stage.AFTER, AfterScenario.class, ANY, SUCCESS, FAILURE));
        return steps;
    }



    private List<BeforeOrAfterStep> scenarioStepsHaving(ScenarioType type, Stage stage,
            Class<? extends Annotation> annotationClass, Outcome... outcomes) {
        List<BeforeOrAfterStep> steps = new ArrayList<BeforeOrAfterStep>();
        
        for (Method method : methodsAnnotatedWith(annotationClass)) {
            ScenarioType scenarioType = scenarioType(method, annotationClass);
            if (type == scenarioType) {
                if (stage == Stage.BEFORE) {
                    steps.add(createBeforeOrAfterStep(stage, method));
                }
                if (stage == Stage.AFTER) {
                    Outcome scenarioOutcome = scenarioOutcome(method, annotationClass);
                    for (Outcome outcome : outcomes) {
                        if (outcome.equals(scenarioOutcome)) {
                            steps.add(createBeforeOrAfterStep(stage, method, outcome));
                        }
                    }
                }
            }
        }
        return steps;
    }

    private ScenarioType scenarioType(Method method, Class<? extends Annotation> annotationClass) {
        if (annotationClass.isAssignableFrom(BeforeScenario.class)) {
            return ((BeforeScenario) method.getAnnotation(annotationClass)).uponType();
        }
        if (annotationClass.isAssignableFrom(AfterScenario.class)) {
            return ((AfterScenario) method.getAnnotation(annotationClass)).uponType();
        }
        return ScenarioType.NORMAL;
    }

    private Outcome scenarioOutcome(Method method, Class<? extends Annotation> annotationClass) {
        if (annotationClass.isAssignableFrom(AfterScenario.class)) {
            return ((AfterScenario) method.getAnnotation(annotationClass)).uponOutcome();
        }
        return Outcome.ANY;
    }

    private BeforeOrAfterStep createBeforeOrAfterStep(Stage stage, Method method) {
        return createBeforeOrAfterStep(stage, method, Outcome.ANY);
    }

    private BeforeOrAfterStep createBeforeOrAfterStep(Stage stage, Method method, Outcome outcome) {
        return new BeforeOrAfterStep(stage, method, outcome, new AutoStepCreator(instance, stepsFactory,
                configuration.parameterConverters(), null, null));
    }

    private List<Method> methodsAnnotatedWith(Class<? extends Annotation> annotationClass) {
        List<Method> annotated = new ArrayList<Method>();
        for (Method method : allMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotated.add(method);
            }
        }
        return annotated;
    }
    
    

}
