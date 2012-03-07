package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUserSteps { 
    
    private PageAdminUsers users = new PageAdminUsers();
    private static final PageAdminUsers adminUsers = new PageAdminUsers();
    private static final AutomationUser autouser = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);

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
    
    @Given("i am on the admin page")
    @When("i am on the admin page")
    public void givenIAmOnTheAdminPage(){
        if (!adminUsers.verifyOnPage()) {
            adminUsers._link().admin().click();
        }
        adminUsers.assertTrue(adminUsers.verifyOnPage(), "I need to be on the admin page");
    }
    
    @Then("the admin page is displayed")
    public void thenTheAdminPageIsDisplayed(){
        adminUsers.verifyOnPage();
    }
    
    @Then("i confirm the admin page contains all necessary elements")
    public void thenIConfirmTheAdminPageContainsAllNecessaryElements(){
        Boolean validate = adminUsers._link().adminUsers().isPresent() & 
                adminUsers._link().adminAddUser().isPresent() &
                adminUsers._link().adminVehicles().isPresent() &
                adminUsers._link().adminAddVehicle().isPresent() &
                adminUsers._link().adminDevices().isPresent() &
                adminUsers._link().adminZones().isPresent() &
                adminUsers._link().adminRedFlags().isPresent() &
                adminUsers._link().adminAddRedFlag().isPresent() &
                adminUsers._link().adminReports().isPresent() &
                adminUsers._link().adminAddReport().isPresent() &
                adminUsers._link().adminOrganization().isPresent() &
                adminUsers._link().adminCustomRoles().isPresent() &
                adminUsers._link().adminAddCustomRole().isPresent() &
                adminUsers._link().adminSpeedByStreet().isPresent() &
                adminUsers._link().adminAccount().isPresent();
        
        adminUsers.validateTrue(validate, "All elements exist on this page");
    }
    
    @When("i log into a different admin account")
    @Composite(steps = {"When I log out", 
                            "When I type another admin username and password",
                            "When I click log in"})
    
    public void whenILogIntoADifferentAdminAccount(){

    }
    
    @When("i log into a different nonadmin account")
    public void whenILogIntoADifferentNonadminAccount(){
 
    }    
    
    @Then("i get an alert 'Access Denied'")
    public void thenIGetAnAlertAccessDenied() {
        //page..validate();  WHere is this page access denied?
    }
    

}
