package com.inthinc.pro.automation.test;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.Steps;

import com.inthinc.pro.automation.utils.MasterTest;

public class AutoSteps extends Steps {

    public AutoSteps(Configuration configuration) {
        super(configuration);
    }

    public AutoSteps(Configuration configuration, Object instance) {
        super(configuration, instance);
    }

    public AutoSteps(Configuration configuration, Class<?> type, InjectableStepsFactory stepsFactory) {
        super(configuration, type, stepsFactory);
    }
    
    public List<StepCandidate> listCandidates() {
        try {
            return super.listCandidates();
        } catch (DuplicateCandidateFound e){
            MasterTest.print(e);
            throw e;
        }
    }

}
