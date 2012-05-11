package com.inthinc.pro.selenium.testSuites;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import com.inthinc.pro.automation.test.JBehaveTest;
import com.inthinc.pro.automation.test.Test;
import com.inthinc.pro.selenium.testSuites.WebStories.WebJBehaveTest;

@RunWith(WebJBehaveTest.class)
@UsingSteps()
public abstract class WebStories {
    
    public final static String URI = codeLocationFromClass(WebStories.class).getFile().replace("%2520", " ") + "/stories"; 
    
    
	public static class WebJBehaveTest extends JBehaveTest{
	    
	    public WebJBehaveTest(Class<?> testClass) throws InitializationError{
	        super(testClass);
	    }
	    
	        
	    protected Test getTest(){
	        return new WebRallyTest();
	    }    
	    
	    public String getUri(){
	        return super.getUri(URI);
	    }
	}
}
