package com.inthinc.pro.automation.jbehave;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.reporters.DelegatingStoryReporter;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

public class AutoStoryReporterBuilder extends StoryReporterBuilder {
    
    @Override
    public StoryReporter build(String storyPath) {
        Map<org.jbehave.core.reporters.Format, StoryReporter> delegates = new HashMap<org.jbehave.core.reporters.Format, StoryReporter>();
        for (org.jbehave.core.reporters.Format format : this.formats()) {
            delegates.put(format, reporterFor(storyPath, format));
        }

        DelegatingStoryReporter delegate = new DelegatingStoryReporter(delegates.values());
        StoryReporter crossReferencing = (this.crossReference() == null ? new NullStoryReporter() : reporterFor(storyPath,
                this.crossReference()));
        return new AutoStoryReporter(crossReferencing, delegate, this.multiThreading());
    }
    
    

}
