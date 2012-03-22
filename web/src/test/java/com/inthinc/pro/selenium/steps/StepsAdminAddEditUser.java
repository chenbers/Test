package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Then;

import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;

public class StepsAdminAddEditUser extends WebSteps {
    

    private PageAdminAddEditUser editUser = new PageAdminAddEditUser();

    @Then("I should not be able to edit $user information")
    public void thenIShouldNotBeAbleToEditUserInformation(String user){
        //user information is defined as: first name, middle name, lastname, suffix, dob, and gender
        //make sure these fields are NOT editable
        test.assertEquals(false, editUser._textField().personFields(UserColumns.FIRST_NAME).isEditable());
        test.assertEquals(false, editUser._textField().personFields(UserColumns.MIDDLE_NAME).isEditable());
        test.assertEquals(false, editUser._textField().personFields(UserColumns.LAST_NAME).isEditable());
        
        test.assertEquals(false, editUser._textField().personFields(UserColumns.DOB_ADD_EDIT).isEditable());
        test.assertEquals(false, editUser._dropDown().suffix().isEditable());
        test.assertEquals(false, editUser._dropDown().gender().isEditable());
    }
    
    

    @Then("I should be able to edit $accessPointName information")
    public void thenICanEditInformationIn(String accessPointName){
        //TODO: Todd: this COULD be much more robust... but I just wanted to get you something to work on sooner
        if(accessPointName.equalsIgnoreCase("user")){
            test.assertEquals(true, editUser._textField().personFields(UserColumns.MIDDLE_NAME).isEditable());
        } else if(accessPointName.equalsIgnoreCase("driver")){
            editUser._checkBox().driverInformation().check();
            test.assertEquals(true, editUser._textField().driverFields(UserColumns.LICENSE_NUMBER).isEditable());
        }else if(accessPointName.equalsIgnoreCase("RFID")){
            editUser._checkBox().driverInformation().check();
            test.assertEquals(true, editUser._textField().driverFields(UserColumns.BAR_CODE).isEditable());
        }else if(accessPointName.equalsIgnoreCase("employee")){
            test.assertEquals(true, editUser._textField().personFields(UserColumns.EMPLOYEE_ID).isEditable());
        }else if(accessPointName.equalsIgnoreCase("login")){
            editUser._checkBox().userInformation().check();                
            test.assertEquals(true, editUser._textField().userFields(UserColumns.USER_NAME).isEditable());
        }else if(accessPointName.equalsIgnoreCase("notification")){
            test.assertEquals(true, editUser._textField().personFields(UserColumns.EMAIL_1).isEditable());
        }else{
            test.addError("Automation does not understand accessPointName: "+accessPointName);
        }
            
            
    }
    

}
