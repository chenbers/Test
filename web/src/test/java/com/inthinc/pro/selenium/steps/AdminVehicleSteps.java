package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;

public class AdminVehicleSteps extends AdminSteps { 
    
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
        assertEquals(true, vehicleAddEdit._textField().VIN().isEditable());
        assertEquals(true, vehicleAddEdit._textField().make().isEditable());
        assertEquals(true, vehicleAddEdit._textField().model().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().year().isEditable());
        assertEquals(true, vehicleAddEdit._textField().color().isEditable());
        assertEquals(true, vehicleAddEdit._textField().weight().isEditable());
        assertEquals(true, vehicleAddEdit._textField().licence().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().state().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().state().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().zone().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().DOT().isEditable());
        assertEquals(true, vehicleAddEdit._checkBox().iftaCheckbox().isClickable());
        
    }
    
    @Then ("I should be able to edit Wireline information")
    public void thenIShouldBeAbleToEditWirelineInformation(){
        assertEquals(true, vehicleAddEdit._textField().killMotorPass().isEditable());
        assertEquals(true, vehicleAddEdit._textField().doorAlarmPass().isEditable());
        assertEquals(true, vehicleAddEdit._textField().autoArmTime().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().killMotor().isEditable());
        assertEquals(true, vehicleAddEdit._dropDown().doorAlarm().isEditable());
        assertEquals(true, vehicleAddEdit._button().killButton().isClickable());
        assertEquals(true, vehicleAddEdit._button().doorButton().isClickable());
    }
    
    @Then ("I should be able to edit Profile information")
    public void thenIshouldBeAbleToEditProfileInformation(){
       assertEquals(true, vehicleAddEdit._textField().vehicleID().isEditable());
       assertEquals(true, vehicleAddEdit._dropDown().status().isEditable());
    }
    
    @Then ("I should be able to edit Assignment information")
    public void thenIShouldBeAbleToEditAssignmentInformation() {
        assertEquals(true, vehicleAddEdit._link().assignDriver().isClickable());
    }
    
    @Then ("I should be able to edit 'Speed and Sensitivity' information")
    public void thenIShouldBeAbleToEditSpeedAndSensitivityinformation(){
        assertEquals(true, vehicleAddEdit._textField().maxSpeed().isEditable());
        assertEquals(true, vehicleAddEdit._textField().speedBuffer().isEditable());
        assertEquals(true, vehicleAddEdit._textField().severeSpeeding().isEditable());
    }
    
    @Then ("I should not be able to edit Vehicle information")
    public void thenIShouldNotBeAbleToEditVehicleInformation(){
        assertEquals(false, vehicleAddEdit._textField().VIN().isEditable());
        assertEquals(false, vehicleAddEdit._textField().make().isEditable());
        assertEquals(false, vehicleAddEdit._textField().model().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().year().isEditable());
        assertEquals(false, vehicleAddEdit._textField().color().isEditable());
        assertEquals(false, vehicleAddEdit._textField().weight().isEditable());
        assertEquals(false, vehicleAddEdit._textField().licence().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().state().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().state().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().zone().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().DOT().isEditable());
        assertEquals(false, vehicleAddEdit._checkBox().iftaCheckbox().isClickable());
    }
    
    @Then ("I should not be able to edit Wireline information")
    public void thenIShouldNotBeAbleToEditWirelineInformation(){
        assertEquals(false, vehicleAddEdit._textField().killMotorPass().isEditable());
        assertEquals(false, vehicleAddEdit._textField().doorAlarmPass().isEditable());
        assertEquals(false, vehicleAddEdit._textField().autoArmTime().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().killMotor().isEditable());
        assertEquals(false, vehicleAddEdit._dropDown().doorAlarm().isEditable());
        assertEquals(false, vehicleAddEdit._button().killButton().isClickable());
        assertEquals(false, vehicleAddEdit._button().doorButton().isClickable());
    }
    
    @Then ("I should not be able to edit Profile information")
    public void thenIShouldNotBeAbleToEditProfileInformation(){
       assertEquals(false, vehicleAddEdit._textField().vehicleID().isEditable());
       assertEquals(false, vehicleAddEdit._dropDown().status().isEditable());
    }
    
    @Then ("I should not be able to edit Assignment information")
    public void thenIShouldNotBeAbleToEditAssignmentInformation() {
        assertEquals(false, vehicleAddEdit._link().assignDriver().isClickable());
    }
    
    @Then ("I should notbe able to edit 'Speed and Sensitivity' information")
    public void thenIShouldNotBeAbleToEditSpeedAndSensitivityinformation(){
        assertEquals(false, vehicleAddEdit._textField().maxSpeed().isEditable());
        assertEquals(false, vehicleAddEdit._textField().speedBuffer().isEditable());
        assertEquals(false, vehicleAddEdit._textField().severeSpeeding().isEditable());
    }
    
    @Then ("I should be able to save")
    public void thenIShouldBeAbleToSave(){
        assertEquals(true, vehicleAddEdit._button().saveTop().isClickable());
        assertEquals(true, vehicleAddEdit._button().saveBottom().isClickable());
    }

    @Then ("I should not be able to save")
    public void thenIShouldNotBeAbleToSave(){
        assertEquals(false, vehicleAddEdit._button().saveTop().isClickable());
        assertEquals(false, vehicleAddEdit._button().saveBottom().isClickable());
    }
}
