package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

public class WebSteps extends WebRallyTest {   
    @When("I press the enter key on my keyboard")
    public void whenIPressTheEnterKeyOnMyKeyboard() {
        enterKey();
    }

    @When("I hit the Tab Key")
    public void whenIHitTheTabKey() {
        tabKey();
    }
    
    @When("I click the $name button")
    public void whenIClick(String name){
        PageExecutiveDashboard page = new PageExecutiveDashboard(); //TODO: this feels wrong, but it needs to be concrete?  problem is I do NOT know what pageObject the browser is ON at this point... and in theory it should not matter
        
        getSelenium().click("//[text()='"+name+"']");
    }

}
