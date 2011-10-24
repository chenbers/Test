package com.inthinc.pro.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.inthinc.pro.domain.settings.DesiredVehicleSetting;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DesiredVehicleSettingTest {
    @Autowired
	private DesiredVehicleSettingService desiredVehicleSettingService;
	
	@Test
    @Transactional
	public void desiredVehicleSettingsServiceCreateUpdateDeleteTest(){
		DesiredVehicleSetting dvs = new DesiredVehicleSetting();
		dvs.setReason("Testing CreateDesiredVehicleSetting");
		dvs.setSettingID(8);
		dvs.setUserID(10087); //jpatestuser
		dvs.setValue("20");
		dvs.setVehicleID(2);
//		dvs.setDeviceID(2);
		DesiredVehicleSetting createdDesiredVehicleSetting = desiredVehicleSettingService.createDesiredVehicleSetting(dvs);
		assertNotNull(createdDesiredVehicleSetting);
		assertNotNull(createdDesiredVehicleSetting.getModified());
//		assertNotNull(createdDesiredVehicleSetting.getDesiredVehicleSettingID());
		Date modified = createdDesiredVehicleSetting.getModified();
		Integer id = createdDesiredVehicleSetting.getDesiredVehicleSettingID();
		createdDesiredVehicleSetting = null;
		try {
			Thread.sleep(5000);//sleep so modified time is later than current modified time
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		DesiredVehicleSetting updateddvs = new DesiredVehicleSetting();
		updateddvs.setDesiredVehicleSettingID(id);
		updateddvs.setReason("Testing updateDesiredVehicleSetting");
		updateddvs.setSettingID(8);
		updateddvs.setUserID(10087); //jpatestuser
		updateddvs.setValue("15");
		updateddvs.setVehicleID(2);
//		updateddvs.setDeviceID(2);
		DesiredVehicleSetting updatedDesiredVehicleSetting = desiredVehicleSettingService.updateDesiredVehicleSetting(updateddvs);
		assertNotNull(updatedDesiredVehicleSetting);
		assertEquals("15", updatedDesiredVehicleSetting.getValue());
		assertEquals("Testing updateDesiredVehicleSetting", updatedDesiredVehicleSetting.getReason());
		assertTrue(updatedDesiredVehicleSetting.getModified().after(modified));
		desiredVehicleSettingService.deleteDesiredSetting(updatedDesiredVehicleSetting);
	}
	@Test
	public void desiredVehicleSettingsServiceGetTest(){
		List<DesiredVehicleSetting> desiredVehicleSettings = desiredVehicleSettingService.getDesiredVehicleSettingsForVehicle(2);
		System.out.println("desiredVehicleSettings size for vehicleID = 2 is: "+desiredVehicleSettings.size());
		for (DesiredVehicleSetting avs : desiredVehicleSettings){
			System.out.println(avs.toString());
		}
		assertNotNull(desiredVehicleSettings);
	}
	@Test
	public void desiredVehicleSettingsServiceCountTest(){
		Number desiredVehicleSettingsCount = desiredVehicleSettingService.getDesiredVehicleSettingCount();
		assertNotNull(desiredVehicleSettingsCount);
		System.out.println("desiredVehicleSettingsCount is: "+desiredVehicleSettingsCount);
	}	
}
