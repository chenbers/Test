package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.Unique;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;

public class AdminAddVehicle extends WebRallyTest {
    
    private final String username = "autovehicle";
    private final String password = "passw0rd";
    
    private PageAdminVehicleEdit editVehicle;
    private Unique values;
    
    @Before
    public void before(){
        editVehicle = new PageAdminVehicleEdit();
        values = new Unique(Addresses.QA);
    }
    
    @Test
    public void variableLengthVINs(){
        editVehicle.loginProcess(username, password);
        editVehicle._link().admin().click();
        editVehicle._link().adminVehicles().click();
        editVehicle._link().adminAddVehicle().click();
        editVehicle._textField().VIN().type(1);
        print(editVehicle._text().errorVIN().getText());
        editVehicle._textField().VIN().type("12345678901234567");
        print(editVehicle._text().errorVIN().getText());
        pause(10,"");
        
        
    }

}
