package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.steps.AdminUserSteps;

public class AdminSpeedByStreet extends WebStories {

    @Override
    protected List<String> storyPaths() {

        return storyPaths("CurrentStory.story");
    }

    @Override
    public List<CandidateSteps> candidateSteps() {

        return candidateSteps(new AdminUserSteps());
    }

    @Override
    public List<AbstractPage> setPageObjects() {
        // TODO Auto-generated method stub
        return null;
    }

}
