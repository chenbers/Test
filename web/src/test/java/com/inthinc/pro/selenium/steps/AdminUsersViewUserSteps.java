package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUsersViewUserSteps extends AdminUserSteps {
    
    private static final PageAdminUsers adminUsers = new PageAdminUsers();
    private static final PageAdminUserDetails adminDetails = new PageAdminUserDetails();
    private static final PageAdminAddEditUser adminEditAccount = new PageAdminAddEditUser();
    
    @When("I click the Back To Users link")
    public void whenIClickTheBackToUsersLink(){
        adminDetails._link().backToUsers().click();
    }

    @Then("I am on the Admin Users page")
    public void thenIAmOnTheAdminUsersPage(){
        adminUsers.verifyOnPage();
    }

}
