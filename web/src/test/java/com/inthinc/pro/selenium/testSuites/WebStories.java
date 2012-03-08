package com.inthinc.pro.selenium.testSuites;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.jbehave.JBehaveStories;

public abstract class WebStories extends JBehaveStories{
    
    public WebStories(){
        super(codeLocationFromClass(WebStories.class).getFile().replace("%2520", " ") + "/stories");
    }
	
		
	/**
	 * This method provides the different steps objects<br />
	 * for the story to run.  By default it will always<br />
	 * include the ConfiguratorRallyTest so that things<br />
	 * can be recorded in Rally, and it provides the <br />
	 * Test before and after methods.
	 * 
	 * @param steps
	 * @return
	 */
	public List<CandidateSteps> candidateSteps(Object ...steps){
	    
		return super.candidateSteps(new WebRallyTest(), steps);
	}
}
