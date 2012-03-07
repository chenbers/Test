package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminSteps extends LoginSteps { 
    
    private static final PageAdminUsers adminUsers = new PageAdminUsers();
    private static final PageAdminUserDetails adminDetails = new PageAdminUserDetails();
    
    @When("i select admin")
    public void whenISelectAdmin() {
        adminUsers._link().admin().click();
    }
    
    @When("i select users")
    public void whenISelectUsers() {
        adminUsers._link().adminUsers().click();
    }
    
    @When("i select a valid user")
    public void whenISelectAValidUser() {
        adminUsers._link().tableEntryUserFullName().row(1).click();
    }
    
    @When("i click the edit link")
    public void whenIClickTheEditLink() {
        adminDetails._button().edit().click();
    }
    
  
    
    //TODO: steps that apply to any ADMIN tab tests ???
    
    
 
    
    //ROLES section (custom roles apply to most if not all admin sections)
//    private PageAdminCustomRoles roles = new PageAdminCustomRoles();
//    private PageAdminCustomRoles roleDetails = new PageAdminCustomRoles();
//    private PageAdminCustomRoleAddEdit roleAddEdit = new PageAdminCustomRoleAddEdit();
//    
//    @When("I input the role name as $roleName")
//    @Pending
//    public void whenIInputTheRoleNameAs(String roleName){
//        if(roleAddEdit.isOnPage()){
//            roleAddEdit._textField().name().type(roleName);
//        } else {
//            addError("not on the correct page to call whenIInputTheRoleNameAs("+roleName+")", ErrorLevel.ERROR);
//        }
//    }
    
    
    
    
}
