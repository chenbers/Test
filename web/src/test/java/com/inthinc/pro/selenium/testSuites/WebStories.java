package com.inthinc.pro.selenium.testSuites;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import com.inthinc.pro.automation.jbehave.JBehaveStories;
import com.inthinc.pro.automation.test.Test;

public abstract class WebStories extends JBehaveStories{
    
    public WebStories(){
        super(codeLocationFromClass(WebStories.class).getFile().replace("%2520", " ") + "/stories");
    }
	
		
	protected Test getTest(){
	    return new WebRallyTest();
	}
}
