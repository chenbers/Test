package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.LoginSteps;


public class LoginStoryTest extends WebStories {

    @Override
    protected List<String> storyPaths() {
        return storyPaths("login.story");
    }
    
	@Override
	public List<CandidateSteps> candidateSteps() {
		return candidateSteps(new LoginSteps());
	}

    @Override
    public List<AbstractPage> requiredPageObjectsList() {
        return pageList(new PageLogin());
    }
}
