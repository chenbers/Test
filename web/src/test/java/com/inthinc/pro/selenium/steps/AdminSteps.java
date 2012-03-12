package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminSteps extends LoginSteps { 
    
    private PageAdminUsers users = new PageAdminUsers();
    private static final PageAdminUsers adminUsers = new PageAdminUsers();
    private static final PageAdminUserDetails adminDetails = new PageAdminUserDetails();

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
    
    @When("I am on the Admin page")
    @Alias("I select admin")
    public void whenIAmOnTheAdminPage(){
        if (!adminUsers.verifyOnPage()) {
            adminUsers._link().admin().click();
        }
        // adminUsers.assertTrue(adminUsers.verifyOnPage(), "I am on the admin page");   
    }
    
    @When("I log into a different admin account")
    @Composite(steps = {"When I log out", 
                            "When I type another admin username and password",
                            "When I click log in"})    
    public void whenILogIntoADifferentAdminAccount(){

    }
    
    @When("I log into a different nonadmin account")
    @Composite(steps = {"When I log out", 
            "When I type another nonadmin username and password",
            "When I click log in"})    
    public void whenILogIntoADifferentNonadminAccount(){
 
    }    
    
    @When("I attempt to login with the same username password combination")
    public void whenIAttemptToLoginWithTheSameUsernamePasswordCombination() {
        login = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusInactive);
        loginPage.loginProcess(login.getUsername(), login.getPassword());
    }
    
    @When("I am on the Live Fleet page")
    public void whenIAmOnTheLiveFleetPage(){
        if (!adminDetails.verifyOnPage()){
            adminDetails._link().liveFleet().click();
        }
    }
    
    @Then("the admin page is displayed")
    @Alias("I confirm the Admin page contains all necessary elements")
    public void thenTheAdminPageIsDisplayed(){
        adminUsers.verifyOnPage();
    }
    
    @Then("I get an alert 'Access Denied'")
    public void thenIGetAnAlertAccessDenied() {
        //page..validate();  WHere is this page alert 'access denied'?
    }
    
    
 
    
}
