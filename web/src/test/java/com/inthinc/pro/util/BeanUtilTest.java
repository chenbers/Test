package com.inthinc.pro.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.FatalBeanException;

import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.VehicleType;

public class BeanUtilTest{
    
    private Vehicle vehicle1, vehicle2;
    
    @Before
    public void setUp(){
        
        try {
        
            vehicle1 = new Vehicle();
            vehicle1.setColor("red");
            vehicle1.setCreated(new Date());
            vehicle1.setDeviceID(100);
            vehicle1.setDot(VehicleDOTType.DOT);
            vehicle1.setDriverID(100);
            vehicle1.setGroupID(101);
            vehicle1.setIfta(false);
            vehicle1.setLicense("license");
            vehicle1.setMake("Subaru");
            vehicle1.setModel("Impreza");
            vehicle1.setModified(new Date());
            vehicle1.setName("vehicle1 name");
            vehicle1.setOdometer(1000);
            State state1 = new State();
            state1.setAbbrev("UT");
            state1.setCreated(new Date());
            state1.setModified(new Date());
            state1.setName("Utah");
            state1.setStateID(47);
            
            vehicle1.setState(state1);
            
            vehicle1.setStatus(Status.ACTIVE);
            vehicle1.setVehicleID(102);
            vehicle1.setVIN("hjdfhjfjhfgjhgjhhjg");
            vehicle1.setVtype(VehicleType.HEAVY);
            vehicle1.setWeight(1002);
            vehicle1.setYear(2011);
            
            vehicle2 = new Vehicle();
            vehicle2.setColor("blue");
            vehicle2.setCreated(null);
            vehicle2.setDeviceID(100);
            vehicle2.setDot(null);
            vehicle2.setDriverID(100);
            vehicle2.setGroupID(101);
            vehicle2.setIfta(false);
            vehicle2.setLicense("license");
            vehicle2.setMake("Subaru");
            vehicle2.setModel("Impreza");
            vehicle2.setModified(new Date());
            vehicle2.setName("vehicle2 name");
            vehicle2.setOdometer(1000);
            State state2 = new State();
            state2.setAbbrev("UT");
            state2.setCreated(new Date());
            state2.setModified(new Date());
            state2.setName("Utah");
            state2.setStateID(47);
            
            vehicle2.setState(null);
            
            vehicle2.setStatus(Status.ACTIVE);
            vehicle2.setVehicleID(102);
            vehicle2.setVIN("hjdfhjfjhfgjhgjhhjg");
            vehicle2.setVtype(VehicleType.HEAVY);
            vehicle2.setWeight(1002);
            vehicle2.setYear(2011);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void deepCloneTest(){
        
        Vehicle clone = (Vehicle)BeanUtil.deepClone(vehicle1);
        assertEquals(clone.getName(),vehicle1.getName());
    }
    @Test
    public void compareAndInitTest(){
        BeanUtil.compareAndInit(vehicle1, vehicle2);
        assertEquals(vehicle1.getName(),null);
        
        try{
            BeanUtil.compareAndInit(vehicle1, null);
        }
        catch(FatalBeanException fbe){
            assertTrue("Exception should have been thrown", true);
        }
    }
}
