package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;

public class StepsAdminUserDetails extends WebSteps {
    

    private PageAdminUserDetails userDetails = new PageAdminUserDetails();

    

    @When("I click the edit button")
    public void whenIClickTheEditButton(){
        userDetails._button().edit().click();
    }

}
