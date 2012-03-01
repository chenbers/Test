package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;

public class AdminVehicleSteps extends StepsAdmin { 
    
    private PageAdminVehicles vehicles = new PageAdminVehicles();
    private PageAdminVehicleView vehicleDetails = new PageAdminVehicleView();
    private PageAdminVehicleEdit vehicleAddEdit = new PageAdminVehicleEdit();
    
    @When ("I click any user link")
    public void whenIClickAnyUserLink(){
        vehicles._link().edit().row(1).click();
    }
    
    @When("I click the edit button")
    public void whenIClickTheEditButton(){
        vehicleDetails._button().edit().click();
    }
    
    @Then ("I should be able to edit Vehicle information")
    public void thenIShouldBeAbleToEditVehicleInformation(){
        test.assertEquals(true, vehicleAddEdit._textField().VIN().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().make().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().model().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().year().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().color().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().weight().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().licence().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().state().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().state().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().zone().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().DOT().isEditable());
        test.assertEquals(true, vehicleAddEdit._checkBox().iftaCheckbox().isClickable());
        
    }
    
    @Then ("I should be able to edit Wireline information")
    public void thenIShouldBeAbleToEditWirelineInformation(){
        test.assertEquals(true, vehicleAddEdit._textField().killMotorPass().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().doorAlarmPass().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().autoArmTime().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().killMotor().isEditable());
        test.assertEquals(true, vehicleAddEdit._dropDown().doorAlarm().isEditable());
        test.assertEquals(true, vehicleAddEdit._button().killButton().isClickable());
        test.assertEquals(true, vehicleAddEdit._button().doorButton().isClickable());
    }
    
    @Then ("I should be able to edit Profile information")
    public void thenIshouldBeAbleToEditProfileInformation(){
       test.assertEquals(true, vehicleAddEdit._textField().vehicleID().isEditable());
       test.assertEquals(true, vehicleAddEdit._dropDown().status().isEditable());
    }
    
    @Then ("I should be able to edit Assignment information")
    public void thenIShouldBeAbleToEditAssignmentInformation() {
        test.assertEquals(true, vehicleAddEdit._link().assignDriver().isClickable());
    }
    
    @Then ("I should be able to edit 'Speed and Sensitivity' information")
    public void thenIShouldBeAbleToEditSpeedAndSensitivityinformation(){
        test.assertEquals(true, vehicleAddEdit._textField().maxSpeed().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().speedBuffer().isEditable());
        test.assertEquals(true, vehicleAddEdit._textField().severeSpeeding().isEditable());
    }
    
    @Then ("I should not be able to edit Vehicle information")
    public void thenIShouldNotBeAbleToEditVehicleInformation(){
        test.assertEquals(false, vehicleAddEdit._textField().VIN().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().make().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().model().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().year().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().color().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().weight().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().licence().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().state().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().state().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().zone().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().DOT().isEditable());
        test.assertEquals(false, vehicleAddEdit._checkBox().iftaCheckbox().isClickable());
    }
    
    @Then ("I should not be able to edit Wireline information")
    public void thenIShouldNotBeAbleToEditWirelineInformation(){
        test.assertEquals(false, vehicleAddEdit._textField().killMotorPass().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().doorAlarmPass().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().autoArmTime().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().killMotor().isEditable());
        test.assertEquals(false, vehicleAddEdit._dropDown().doorAlarm().isEditable());
        test.assertEquals(false, vehicleAddEdit._button().killButton().isClickable());
        test.assertEquals(false, vehicleAddEdit._button().doorButton().isClickable());
    }
    
    @Then ("I should not be able to edit Profile information")
    public void thenIShouldNotBeAbleToEditProfileInformation(){
       test.assertEquals(false, vehicleAddEdit._textField().vehicleID().isEditable());
       test.assertEquals(false, vehicleAddEdit._dropDown().status().isEditable());
    }
    
    @Then ("I should not be able to edit Assignment information")
    public void thenIShouldNotBeAbleToEditAssignmentInformation() {
        test.assertEquals(false, vehicleAddEdit._link().assignDriver().isClickable());
    }
    
    @Then ("I should notbe able to edit 'Speed and Sensitivity' information")
    public void thenIShouldNotBeAbleToEditSpeedAndSensitivityinformation(){
        test.assertEquals(false, vehicleAddEdit._textField().maxSpeed().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().speedBuffer().isEditable());
        test.assertEquals(false, vehicleAddEdit._textField().severeSpeeding().isEditable());
    }
    
    @Then ("I should be able to save")
    public void thenIShouldBeAbleToSave(){
        test.assertEquals(true, vehicleAddEdit._button().saveTop().isClickable());
        test.assertEquals(true, vehicleAddEdit._button().saveBottom().isClickable());
    }

    @Then ("I should not be able to save")
    public void thenIShouldNotBeAbleToSave(){
        test.assertEquals(false, vehicleAddEdit._button().saveTop().isClickable());
        test.assertEquals(false, vehicleAddEdit._button().saveBottom().isClickable());
    }
}
