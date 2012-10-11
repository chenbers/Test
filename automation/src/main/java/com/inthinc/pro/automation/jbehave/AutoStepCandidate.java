package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepCreator;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;

public class AutoStepCandidate extends StepCandidate {


    private final Method method;
    private final StepMatcher stepMatcher;
    private final AutoStepCreator stepCreator;
    private String[] composedSteps;
    private StepMonitor stepMonitor = new SilentStepMonitor();
    private final Keywords keywords;
    private final StepType stepType;
    
    
    public AutoStepCandidate(String patternAsString, int priority, StepType stepType, Method method, Object stepsType, InjectableStepsFactory stepsFactory, Keywords keywords,
            StepPatternParser stepPatternParser, ParameterConverters parameterConverters) { 
        super(patternAsString, priority, stepType, method, (Class<?>) ((stepsType instanceof Class<?>) ? stepsType : stepsType.getClass()), stepsFactory, keywords, stepPatternParser, parameterConverters, new ParameterControls());
        this.stepType = stepType;
        this.method = method;
        this.keywords = keywords;
        this.stepMatcher = stepPatternParser.parseStep(stepType, patternAsString);
        this.stepCreator = new AutoStepCreator(stepsType, stepsFactory, parameterConverters, stepMatcher, stepMonitor);
    }
    
   
    private StepCandidate findComposedCandidate(String composedStep, List<StepCandidate> allCandidates) {
        for (StepCandidate candidate : allCandidates) {
            if (StringUtils.startsWith(composedStep, candidate.getStartingWord())
                    && (StringUtils.endsWith(composedStep, candidate.getPatternAsString()) || candidate.matches(composedStep))) {
                return candidate;
            }
        }
        return null;
    }
    
    public void composedOf(String[] steps) {
        this.composedSteps = steps;
        super.composedOf(steps);
    }
    
    private void addComposedStep(List<Step> steps, String composedStep, Map<String, String> matchedParameters,
            List<StepCandidate> allCandidates) {
        StepCandidate candidate = findComposedCandidate(composedStep, allCandidates);
        if (candidate != null) {
            steps.add(candidate.createMatchedStep(composedStep, matchedParameters));
            if (candidate.isComposite()) {
                // candidate is itself composite: recursively add composed steps
                addComposedStepsRecursively(steps, composedStep, matchedParameters, allCandidates,
                        candidate.composedSteps());
            }
        } else {
            steps.add(StepCreator.createPendingStep(composedStep, null));
        }
    }
    
    private void addComposedStepsRecursively(List<Step> steps, String stepAsString,
            Map<String, String> namedParameters, List<StepCandidate> allCandidates, String[] composedSteps) {
        Map<String, String> matchedParameters = stepCreator.matchedParameters(method, stepAsString,
                stripStartingWord(stepAsString), namedParameters);
        matchedParameters.putAll(namedParameters);
        for (String composedStep : composedSteps) {
            addComposedStep(steps, composedStep, matchedParameters, allCandidates);
        }
    }
    
    public void addComposedSteps(List<Step> steps, String stepAsString, Map<String, String> namedParameters,
            List<StepCandidate> allCandidates) {
        addComposedStepsRecursively(steps, stepAsString, namedParameters, allCandidates, composedSteps);
    }
    
    private String stripStartingWord(String stepAsString) {
        return keywords.stepWithoutStartingWord(stepAsString, stepType);
    }

}
