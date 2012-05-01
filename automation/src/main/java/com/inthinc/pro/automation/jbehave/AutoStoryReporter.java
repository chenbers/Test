package com.inthinc.pro.automation.jbehave;

import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;

import com.inthinc.pro.automation.test.BrowserRallyTest;
import com.inthinc.pro.automation.test.Test;

public class AutoStoryReporter extends ConcurrentStoryReporter {
    
    private Test test;

    public AutoStoryReporter(StoryReporter crossReferencing, StoryReporter delegate, boolean multiThreading, 
            Test test) {
        super(crossReferencing, delegate, multiThreading);
        this.test = test;
    }

    
    @Override
    public void beforeScenario(String scenarioTitle) {
        if (test instanceof BrowserRallyTest){
            ((BrowserRallyTest)test).parseJBehaveStep(scenarioTitle);
        }
        super.beforeScenario(scenarioTitle);
    }
    
}
