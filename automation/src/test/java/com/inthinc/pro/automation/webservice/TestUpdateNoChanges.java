package com.inthinc.pro.automation.webservice;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.models.Vehicle;
import com.inthinc.pro.automation.rest.RestCommands;
import com.inthinc.pro.automation.selenium.AutomationProperties;
@Ignore
public class TestUpdateNoChanges {
    
    @Test
    public void testUpdatingModels(){
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        RestCommands services = new RestCommands(apb.getMainAutomation().get(1), apb.getPassword());
        
        List<Account> accounts = services.getBulk(Account.class);
        for (Account base : accounts){
            Account original = services.getObject(base.getClass(), base.getAccountID());
            Account updated = services.putObject(original.getClass(), original, null);
            assertEquals("Accounts should be the same", original.toJsonString(), updated.toJsonString());
        }
        
//        List<Device> devices = services.getBulk(Device.class);
//        for (Device base : devices){
//            Device original = services.getObject(base.getClass(), base.getDeviceID());
//            Device updated = services.putObject(original.getClass(), original, null);
//            assertEquals("Devices should be the same", null, updated);
//        }
        
        List<Person> persons = services.getBulk(Person.class);
        for (Person base : persons){
            Person original = services.getObject(base.getClass(), base.getPersonID());
            Person updated = services.putObject(original.getClass(), original, null);
            String a = original.toJsonString();
            String b = updated.toJsonString();
            if (!a.equals(b)){
                Log.info(a); 
                Log.info(b);
            }
            assertEquals("Persons should be the same", original.toJsonString(), updated.toJsonString());
        }
        
        List<Vehicle> vehicles = services.getBulk(Vehicle.class);
        for (Vehicle base : vehicles){
            Vehicle original = services.getObject(base.getClass(), base.getVehicleID());
            Vehicle updated = services.putObject(original.getClass(), original, null);
            assertEquals("Vehicles should be the same", original.toJsonString(), updated.toJsonString());
        }
    }
}
