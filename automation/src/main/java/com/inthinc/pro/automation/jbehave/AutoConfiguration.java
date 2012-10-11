package com.inthinc.pro.automation.jbehave;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.reporters.StoryReporter;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

public class AutoConfiguration extends MostUsefulConfiguration {
    
    private RunNotifier notifier;
    private Description storyDescription;

    public AutoConfiguration useJunitNotifier(RunNotifier notifier){
        this.notifier = notifier;
        return this;
    }
    
    public RunNotifier useJunitNotifier(){
        return notifier;
    }
    
    public StoryReporter storyReporter(String storyPath) {
        return ((AutoStoryReporterBuilder)storyReporterBuilder()).build(storyPath, notifier, storyDescription);
    }
    
    public AutoConfiguration setJunitStoryDescription(Description description){
        this.storyDescription = description;
        return this;
    }
}
