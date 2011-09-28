package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.UniqueValues;
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
        values = new Unique(Addresses.getSilo(getAutomationPropertiesBean().getSilo()));
        editVehicle.loginProcess(username, password);
    }
    
    @Test
    public void defaultCommandButton(){
        set_test_case("TC166");
        
    }
    
    
    @Test
    public void variableLengthVINs(){
        
        editVehicle._link().admin().click();
        editVehicle._link().adminVehicles().click();
        editVehicle._link().adminAddVehicle().click();
        editVehicle._textField().VIN().type(1);
        print(editVehicle._text().errorVIN().getText());
        editVehicle._textField().VIN().type(values.getUniqueValue(17, UniqueValues.VEHICLE_VIN));
        print(editVehicle._text().errorVIN().getText());
    }
    
    @Test
    public void secondaryTestForWebDriver(){
        editVehicle.loginProcess(username, password);
    }

}
