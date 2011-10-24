package com.inthinc.pro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.Device;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceTest {
    @Autowired
	private DeviceService deviceService;
	
    @Test
    public void deviceCountTest(){
    	Number count = deviceService.getDeviceCount();
    	System.out.println(count.toString());
    }
    
    @Test
    public void getDeviceTest(){
    	Device device = deviceService.getDeviceByID(2);
    	System.out.println(device.toString());
    	
    }
    @Test
    public void getVddLogByVehicleIDTest(){
    	Device device = deviceService.getDeviceByVehicleID(2);
    	System.out.println("deviceID for vehicle 2 is "+device.getDeviceID());
    }
    
}
