package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.selenium.steps.LoginSteps;


public class LoginStory extends WebStories {


	@Override
	public List<CandidateSteps> candidateSteps() {
		return candidateSteps(new LoginSteps());
	}

	@Override
	protected List<String> storyPaths() {
		return storyPaths("login.story");
	}
}
