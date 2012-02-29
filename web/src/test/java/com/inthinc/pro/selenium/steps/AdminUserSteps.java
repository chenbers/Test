package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUserSteps extends AdminSteps { 
    
//    private PageAdminUsers users = new PageAdminUsers();
//    private PageAdminUserDetails userDetails = new PageAdminUserDetails();
//    private PageAdminUserAddEdit editUser = new PageAdminUserAddEdit();
//
//    @When("I click any user link")
//    public void whenIClickAnyUserLink(){
//        users._link().tableEntryUserFullName().row(1).click();
//    }
//
//    @When("I click the edit button")
//    public void whenIClickTheEditButton(){
//        userDetails._button().edit().click();
//    }
//
//    @Then("I should not be able to edit $user information")
//    public void thenIShouldNotBeAbleToEditUserInformation(String user){
//        //user information is defined as: first name, middle name, lastname, suffix, dob, and gender
//        //make sure these fields are NOT editable
//        assertEquals(false, editUser._textField().personFields(UserColumns.FIRST_NAME).isEditable());
//        assertEquals(false, editUser._textField().personFields(UserColumns.MIDDLE_NAME).isEditable());
//        assertEquals(false, editUser._textField().personFields(UserColumns.LAST_NAME).isEditable());
//        
//        assertEquals(false, editUser._textField().personFields(UserColumns.DOB_ADD_EDIT).isEditable());
//        assertEquals(false, editUser._dropDown().suffix().isEditable());
//        assertEquals(false, editUser._dropDown().gender().isEditable());
//    }
//
//    //TODO: Todd: this is an example of something that seems like a good step when written in English, but is very hard (if not impossible) to implement i.e. what ARE the "other" accessPoints?
//    @Then("I cannot edit any other access points")
//    @Pending
//    public void thenICannotEditAnyOtherAccessPoints(){
//      // PENDING
//    }
//    
//    @Then("I should be able to edit $accessPointName information")
//    public void thenICanEditInformationIn(String accessPointName){
//        //TODO: Todd: this COULD be much more robust... but I just wanted to get you something to work on sooner
//        if(accessPointName.equalsIgnoreCase("user")){
//            assertEquals(true, editUser._textField().personFields(UserColumns.MIDDLE_NAME).isEditable());
//        } else if(accessPointName.equalsIgnoreCase("driver")){
//            editUser._checkBox().driverInformation().check();
//            assertEquals(true, editUser._textField().driverFields(UserColumns.LICENSE_NUMBER).isEditable());
//        }else if(accessPointName.equalsIgnoreCase("RFID")){
//            editUser._checkBox().driverInformation().check();
//            assertEquals(true, editUser._textField().driverFields(UserColumns.BAR_CODE).isEditable());
//        }else if(accessPointName.equalsIgnoreCase("employee")){
//            assertEquals(true, editUser._textField().personFields(UserColumns.EMPLOYEE_ID).isEditable());
//        }else if(accessPointName.equalsIgnoreCase("login")){
//            editUser._checkBox().userInformation().check();                
//            assertEquals(true, editUser._textField().userFields(UserColumns.USER_NAME).isEditable());
//        }else if(accessPointName.equalsIgnoreCase("notification")){
//            assertEquals(true, editUser._textField().personFields(UserColumns.EMAIL_1).isEditable());
//        }else{
//            addError("Automation does not understand accessPointName: "+accessPointName);
//        }
//            
//            
//    }
 

}
