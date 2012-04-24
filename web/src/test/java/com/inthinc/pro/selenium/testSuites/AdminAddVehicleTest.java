package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.emulation.utils.Unique;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditVehicle;

//@Ignore
public class AdminAddVehicleTest extends WebRallyTest {

    private AutomationUser login;
    
    private PageAdminAddEditVehicle editVehicle;
    private Unique values;
    
    @Before
    public void before(){
        editVehicle = new PageAdminAddEditVehicle();
        login = AutomationUsers.getUsers().getOneBy(LoginCapability.RoleAdmin);
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
        Log.info(editVehicle._text().errorVIN().getText());
        editVehicle._textField().VIN().type(values.getUniqueValue(17, UniqueValues.VEHICLE_VIN));
        Log.info(editVehicle._text().errorVIN().getText());
        //TODO: dtanner: finish this test???
    }
}
