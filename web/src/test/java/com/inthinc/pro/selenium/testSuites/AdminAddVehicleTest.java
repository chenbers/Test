package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.emulation.enums.Addresses;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.utils.Unique;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;

//@Ignore
public class AdminAddVehicleTest extends WebRallyTest {

    private AutomationUser login;
    
    private PageAdminVehicleEdit editVehicle;
    private Unique values;
    
    @Before
    public void before(){
        editVehicle = new PageAdminVehicleEdit();
        login = users.getOneBy(LoginCapability.RoleAdmin);
        values = new Unique(Addresses.getSilo(getAutomationPropertiesBean().getSilo()));
    }
    
    @Test
    @Ignore
    public void defaultCommandButton(){
        set_test_case("TC166");
        //TODO: dtanner: Implement this test
    }
    
    
    @Test
    public void variableLengthVINs(){
        editVehicle.loginProcess(login);
        editVehicle._link().admin().click();
        editVehicle._link().adminVehicles().click();
        editVehicle._link().adminAddVehicle().click();
        pause(3, "Page still loading?");
        editVehicle._textField().VIN().type(1);
        print(editVehicle._text().errorVIN().getText());
        editVehicle._textField().VIN().type(values.getUniqueValue(17, UniqueValues.VEHICLE_VIN));
        print(editVehicle._text().errorVIN().getText());
        //TODO: dtanner: finish this test???
    }
}
