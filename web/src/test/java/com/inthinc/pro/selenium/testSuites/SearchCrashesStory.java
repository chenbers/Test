package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.steps.SearchCrashesSteps;


public class SearchCrashesStory extends WebStories {


	@Override
	public List<CandidateSteps> candidateSteps() {
		return candidateSteps(new SearchCrashesSteps());
	}

	@Override
	protected List<String> storyPaths() {
		return storyPaths("SearchCrashes.story");
	}
	
	@Override
    public List<AbstractPage> setPageObjects() {
        return null;
    }
}
