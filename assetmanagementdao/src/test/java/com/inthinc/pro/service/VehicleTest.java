package com.inthinc.pro.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.service.VehicleService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleTest {
    @Autowired
	private VehicleService vehicleService;
	
    @Test
    public void vehicleCountTest(){
    	Number count = vehicleService.getVehicleCount();
    	System.out.println(count.toString());
    }
    
//    @Test
//    @Ignore
//    public void getVehicleTest(){
//    	Vehicle vehicle = vehicleService.getVehicleByID(2);
//    	System.out.println(vehicle.toString());
//    	
//    }
    
	@Test
	public void vehicleIDsGroupDeepServiceTest(){
		List<Integer> vehicleIDsByGroupIDDeep = vehicleService.getVehicleIDsByGroupIDDeep(2);
		System.out.println("vehicleIDsByGroupIDDeep size for groupID = 2 is: "+vehicleIDsByGroupIDDeep.size());
		for (Integer vehicleID : vehicleIDsByGroupIDDeep){
			System.out.println(vehicleID.toString());
		}
		assertNotNull(vehicleIDsByGroupIDDeep);
	}
}
