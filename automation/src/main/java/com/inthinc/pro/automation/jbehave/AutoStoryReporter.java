package com.inthinc.pro.automation.jbehave;

import java.util.Iterator;
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
    
    private Iterator<Description> scenarios;
    private Description scenario;
    
    
    private RunNotifier runNotifier;
    
    private Description storyDescription;
    
    private Description step;
    private Iterator<Description> steps;

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
        if (scenarios != null) {
            scenario = scenarios.next();
            steps = scenario.getChildren().iterator();
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
                    scenarios = desc.getChildren().iterator();
                    runNotifier.fireTestStarted(this.story);
                    break; 
                }
            }
        } else {
            this.story = null;
            this.scenarios = null;
            this.scenario = null;
        }
        
        super.beforeStory(story, givenStory);
    }

    @Override
    public void afterStory(boolean paramBoolean) {
        if (this.story != null){
            runNotifier.fireTestFinished(this.story);
            this.story = null;
        }
        
        super.afterStory(paramBoolean);
    }

    @Override
    public void afterScenario() {
        if (runNotifier != null && scenario != null){
            runNotifier.fireTestFinished(scenario);
            scenario = null;
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
    
    private Description nextStep;
    
    @Override
    public void beforeStep(String step){
        if (runNotifier != null && scenario != null){
            if (nextStep == null){
                nextStep = steps.next();    
            }
            if (nextStep.getDisplayName().equals(step)){
                this.step = nextStep;
                runNotifier.fireTestStarted(this.step);
                
            }
        }
        super.beforeStep(step);
    }
    
    public void afterStep(){
        if (step != null){
            nextStep = null;
            step = null;
        }
    }

    @Override
    public void afterExamples() {
        super.afterExamples();
    }

    

    @Override
    public void successful(String step) {
        if (runNotifier != null && this.step != null){
            runNotifier.fireTestFinished(this.step);
            afterStep();
        }
        super.successful(step);
    }

    @Override
    public void ignorable(String step) {
        if (runNotifier != null && this.step != null){
            runNotifier.fireTestIgnored(this.step);
            afterStep();
        }
        super.ignorable(step);
    }

    @Override
    public void pending(String step) {
        if (runNotifier != null && this.step != null){
            runNotifier.fireTestIgnored(this.step);
            afterStep();
        }
        super.pending(step);
    }

    @Override
    public void failed(String step, Throwable cause) {
        if (runNotifier != null && this.step != null){
            runNotifier.fireTestFailure(new Failure(this.step, cause.getCause()));
            afterStep();
        }
        super.failed(step, cause);
    }
}
