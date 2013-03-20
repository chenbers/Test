package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.thoughtworks.selenium.DefaultSelenium;

public class MapMarkerSteps {
	
	PageLiveFleet liveFleet = new PageLiveFleet();
	
	@When("I wait")
	public void whenIWait() throws InterruptedException {
		Thread.sleep(5000);
	}
	
	@When("I Evaluate Javacript 'optimization = false;'") 
	public void whenIEvaluateJavascript(DefaultSelenium selenium) {
		selenium.getEval("optimization : false;");
	}
}
