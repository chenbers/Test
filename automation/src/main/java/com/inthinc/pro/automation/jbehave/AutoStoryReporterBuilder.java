package com.inthinc.pro.automation.jbehave;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.reporters.DelegatingStoryReporter;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

import com.inthinc.pro.automation.test.Test;

public class AutoStoryReporterBuilder extends StoryReporterBuilder {
    
    private Test test;
    
    public AutoStoryReporterBuilder(Test test){
        this.test = test;
    }
    
    
    @Override
    public StoryReporter build(String storyPath) {
        return build(storyPath, null, null);
    }


    public StoryReporter build(String storyPath, RunNotifier notifier, Description storyDescription) {
        Map<org.jbehave.core.reporters.Format, StoryReporter> delegates = new HashMap<org.jbehave.core.reporters.Format, StoryReporter>();
        for (org.jbehave.core.reporters.Format format : this.formats()) {
            delegates.put(format, reporterFor(storyPath, format));
        }

        DelegatingStoryReporter delegate = new DelegatingStoryReporter(delegates.values());
        StoryReporter crossReferencing = (this.crossReference() == null ? new NullStoryReporter() : reporterFor(storyPath,
                this.crossReference()));
        return new AutoStoryReporter(crossReferencing, delegate, this.multiThreading(), test, notifier, storyDescription);
    }
    
    

}
