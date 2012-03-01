package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUserSteps extends StepsAdmin { 
    
    private PageAdminUsers users = new PageAdminUsers();

    @When("I click any user link")
    public void whenIClickAnyUserLink(){
        users._link().tableEntryUserFullName().row(1).click();
    }


    //TODO: Todd: this is an example of something that seems like a good step when written in English, but is very hard (if not impossible) to implement i.e. what ARE the "other" accessPoints?
    @Then("I cannot edit any other access points")
    @Pending
    public void thenICannotEditAnyOtherAccessPoints(){
      // PENDING
    }
    
 

}
