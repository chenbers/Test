package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.StepType.GIVEN;
import static org.jbehave.core.steps.StepType.THEN;
import static org.jbehave.core.steps.StepType.WHEN;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Steps;

import com.inthinc.pro.automation.utils.MasterTest;

public class AutoSteps extends Steps {

    private Class<?> type;
    private InjectableStepsFactory stepsFactory;
    private final Configuration configuration;
    

    public AutoSteps(Configuration configuration) {
        super(configuration);
        this.type = this.getClass();
        this.stepsFactory = new AutoStepsFactory(configuration, this);
        this.configuration = configuration;
    }

    public AutoSteps(Configuration configuration, Object instance) {
        super(configuration, instance);
        this.type = instance.getClass();
        this.stepsFactory = new AutoStepsFactory(configuration, this);
        this.configuration = configuration;
    }

    public AutoSteps(Configuration configuration, Class<?> type, InjectableStepsFactory stepsFactory) {
        super(configuration, type, stepsFactory);
        this.type = type;
        this.stepsFactory = stepsFactory;
        this.configuration = configuration;
    }
    
//    public List<StepCandidate> listCandidates() {
//        try {
//            return super.listCandidates();
//        } catch (DuplicateCandidateFound e){
//            MasterTest.print(e);
//            throw e;
//        }
//    }
    
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
            MasterTest.print(e);
            throw e;
        }
    }
    
    private StepCandidate createCandidate(Method method, StepType stepType, String stepPatternAsString, int priority,
            Configuration configuration) {
        return new AutoStepCandidate(stepPatternAsString, priority, stepType, method, type, stepsFactory,
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
        return asList(type.getMethods());
    }

}
