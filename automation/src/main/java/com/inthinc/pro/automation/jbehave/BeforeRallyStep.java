package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.Method;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.model.Meta;
import org.jbehave.core.steps.BeforeOrAfterStep;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCollector.Stage;
import org.jbehave.core.steps.StepCreator;
import org.jbehave.core.steps.StepMonitor;

public class BeforeRallyStep extends BeforeOrAfterStep{

    private final Stage stage;
    private final Method method;
    private final StepCreator stepCreator;
    private final Outcome outcome;
    private final Object instance;
    private StepMonitor stepMonitor = new SilentStepMonitor();

    public BeforeRallyStep(Stage stage, Method method, StepCreator stepCreator, Object instance) {
        this(stage, method, Outcome.ANY, stepCreator, instance);
    }

    public BeforeRallyStep(Stage stage, Method method, Outcome outcome, StepCreator stepCreator, Object instance) {
        super(stage, method, outcome, stepCreator);
        this.stage = stage;
        this.method = method;
        this.outcome = outcome;
        this.stepCreator = stepCreator;
        this.instance = instance;
    }

    public Stage getStage() {
        return stage;
    }

    public Method getMethod() {
        return method;
    }
    
    public Object getInstance(){
        return instance;
    }

    public Step createStep() {
        return createStepWith(Meta.EMPTY);
    }

    public Step createStepWith(Meta meta) {
        return stepCreator.createBeforeOrAfterStep(method, meta);
    }

    public Step createStepUponOutcome(Meta storyAndScenarioMeta) {
        return stepCreator.createAfterStepUponOutcome(method, outcome, storyAndScenarioMeta);
    }

    public void useStepMonitor(StepMonitor stepMonitor) {
        this.stepMonitor = stepMonitor;
        this.stepCreator.useStepMonitor(stepMonitor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(stage).append(method).append(outcome)
                .append(stepMonitor).toString();
    }
}
