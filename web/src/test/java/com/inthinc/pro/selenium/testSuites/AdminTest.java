package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.steps.AdminSteps;

public class AdminTest extends WebStories {

    @Override
    protected List<String> storyPaths() {

        return storyPaths("Admin.story");
    }

    @Override
    public List<CandidateSteps> candidateSteps() {

        return candidateSteps(new AdminSteps());
    }

    @Override
    public List<AbstractPage> requiredPageObjectsList() {
        // TODO Auto-generated method stub
        return pageList();
    }
    
    
    
}
