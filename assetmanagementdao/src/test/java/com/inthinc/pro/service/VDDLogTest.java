package com.inthinc.pro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.domain.Vehicle;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class VDDLogTest {
    @Autowired
	private VddLogService vddLogService;
	
    @Test
    public void vddLogCountTest(){
    	Number count = vddLogService.getVddLogCount();
    	System.out.println(count.toString());
    }
    
    @Test
    public void getVddLogTest(){
    	VDDLog vddLog = vddLogService.getVddLogByID(11462);
    	System.out.println(vddLog.toString());
    	
    }
    @Test
    public void getVddLogByVehicleIDTest(){
    	Device device = vddLogService.getDeviceByVehicleID(2);
    	System.out.println("device for vehicle 2 is "+device.toString());
    }
    @Test
    public void getVddLogByDeviceIDTest(){
    	Vehicle vehicle = vddLogService.getVehicleByDeviceID(2);
    	System.out.println("vehicle for device 2 is "+vehicle.toString());
    }

}
