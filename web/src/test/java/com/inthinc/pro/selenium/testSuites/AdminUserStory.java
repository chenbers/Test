package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.steps.AdminUserSteps;


public class AdminUserStory extends WebStories {

	@Override
	public List<CandidateSteps> candidateSteps() {
		return candidateSteps(new AdminUserSteps());
	}

	@Override
	protected List<String> storyPaths() {
		return storyPaths("AdminUser.story");
	}
	
	@Override
    public List<AbstractPage> setPageObjects() {
        return pageList();
    }
}
