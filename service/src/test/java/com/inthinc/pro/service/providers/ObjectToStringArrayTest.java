package com.inthinc.pro.service.providers;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;

public class ObjectToStringArrayTest {
    
    @Test
    public void testStringArrayNotEmpty(){

        Driver driver = new Driver();
//        <modified>2011-03-24T09:04:52-06:00</modified>
//        <certifications/>
//        <dot>NON_DOT</dot>
//        <driverID>20821</driverID>
//        <expiration>1969-12-31T17:00:00-07:00</expiration>
//        <groupID>278</groupID>
//        <personID>22934</personID>
//        <status>ACTIVE</status> 
        driver.setModified(new Date());
        driver.setCertifications("certs");
        driver.setDot(RuleSetType.US);
        driver.setExpiration(new Date());
        driver.setDriverID(20821);
        driver.setGroupID(278);
        driver.setPersonID(22934);
        driver.setPerson(new Person());
        driver.setStatus(Status.ACTIVE);
        String[] fields = ObjectToStringArray.convertObjectToStringArray(driver, Driver.class, null);
        assertTrue("fields should not be empty", fields.length != 0);
        
        for(String field : fields){
            System.out.println(field);
        }
    }

}
