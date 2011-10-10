package com.inthinc.pro.selenium.testSuites;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.AutomationLogins;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.utils.Unique;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;

public class AdminAddVehicleTest extends WebRallyTest {
    
    private static String username;
    private static String password;
    
    private PageAdminVehicleEdit editVehicle;
    private Unique values;
    
    @BeforeClass
    public static void beforeClass(){
        AutomationLogins login = AutomationLogins.getOneBy(LoginCapabilities.RoleAdmin);
        username = login.getUserName();
        password = login.getPassword();
    }
    @Before
    public void before(){
        editVehicle = new PageAdminVehicleEdit();
        values = new Unique(Addresses.getSilo(getAutomationPropertiesBean().getSilo()));
        editVehicle.loginProcess(username, password);
    }
    
    @Test
    public void defaultCommandButton(){
        set_test_case("TC166");
        //TODO: dtanner: Implement this test
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
        //TODO: dtanner: finish this test???
    }
}
