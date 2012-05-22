package com.inthinc.pro.automation.jbehave;

import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.inthinc.pro.automation.test.BrowserRallyTest;
import com.inthinc.pro.automation.test.Test;

public class AutoStoryReporter extends ConcurrentStoryReporter {

    private Test test;

    private Description story;
    private Description scenario;
    
    private RunNotifier runNotifier;
    
    private Description storyDescription;
    

    public AutoStoryReporter(StoryReporter crossReferencing, StoryReporter delegate, boolean multiThreading, Test test, RunNotifier notifier, Description storyDescription) {
        super(crossReferencing, delegate, multiThreading);
        this.test = test;
        this.runNotifier = notifier;
        this.storyDescription = storyDescription;
    }
    
    @Override
    public void beforeScenario(String scenarioTitle) {
        if (test instanceof BrowserRallyTest) {
            ((BrowserRallyTest) test).parseJBehaveStep(scenarioTitle);
        }
        if (story != null) {
            for (Description desc : story.getChildren()){
                String name = desc.getDisplayName();
                if (name.equals(scenarioTitle)){
                    scenario = desc;
                    runNotifier.fireTestStarted(scenario);
                    break; 
                } 
                scenario = null;
            }
        }
        super.beforeScenario(scenarioTitle);
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        if (runNotifier != null && storyDescription != null){
            runNotifier.fireTestStarted(storyDescription);
            for (Description desc : storyDescription.getChildren()){
                String name = desc.getDisplayName();
                if (name.equals(story.getName().replace(".story", ""))){
                    this.story = desc;
                    runNotifier.fireTestStarted(this.story);
                    break; 
                }
            }
        }
        
        super.beforeStory(story, givenStory);
    }

    @Override
    public void afterStory(boolean paramBoolean) {
        if (this.story != null){
            runNotifier.fireTestStarted(this.story);
            this.story = null;
        }
        
        super.afterStory(paramBoolean);
    }

    @Override
    public void afterScenario() {
        if (runNotifier != null && scenario != null){
            runNotifier.fireTestFinished(scenario);
        }
        super.afterScenario();
    }
    
    private int i = 0;

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {
        i = 0;
        super.beforeExamples(steps, table);
    }

    @Override
    public void example(Map<String, String> tableRow) {
        if (runNotifier != null && scenario != null){
            runNotifier.fireTestStarted(scenario.getChildren().get(i));
        }
        super.example(tableRow);
        i++;
    }
    
    private Description getStepDescription(String step){
        if (runNotifier != null && scenario != null){
            for (Description stepDesc : scenario.getChildren()){
                String name = stepDesc.getDisplayName();
                if (name.equals(step)){
                    return stepDesc;
                }
            }
        }
        return null;
    }
    
    @Override
    public void beforeStep(String step){
        Description desc = getStepDescription(step);
        if (runNotifier != null && desc != null){
            runNotifier.fireTestStarted(desc);
        }
        super.beforeStep(step);
    }

    @Override
    public void afterExamples() {
        super.afterExamples();
    }

    

    @Override
    public void successful(String step) {
        Description desc = getStepDescription(step);
        if (runNotifier != null && desc != null){
            runNotifier.fireTestFinished(desc);
        }
        super.successful(step);
    }

    @Override
    public void ignorable(String step) {
        Description desc = getStepDescription(step);
        if (runNotifier != null && desc != null){
            runNotifier.fireTestIgnored(desc);
        }
        super.ignorable(step);
    }

    @Override
    public void pending(String step) {
        Description desc = getStepDescription(step);
        if (runNotifier != null && desc != null){
            runNotifier.fireTestIgnored(desc);
        }
        super.pending(step);
    }

    @Override
    public void failed(String step, Throwable cause) {
        Description desc = getStepDescription(step);
        if (runNotifier != null && desc != null){
            runNotifier.fireTestFailure(new Failure(desc, cause.getCause()));
        }
        super.failed(step, cause);
    }
}
