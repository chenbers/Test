package com.inthinc.pro.automation.jbehave;

import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;

public class AutoStoryReporter extends ConcurrentStoryReporter {

    public AutoStoryReporter(StoryReporter crossReferencing, StoryReporter delegate, boolean multiThreading) {
        super(crossReferencing, delegate, multiThreading);
    }

    
    @Override
    public void beforeScenario(String scenarioTitle) {
        super.beforeScenario(scenarioTitle);
    }
    
}
