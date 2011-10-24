package com.inthinc.pro.service;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.settings.ActualVehicleSetting;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ActualVehicleSettingTest {

    @Autowired
	private ActualVehicleSettingService actualVehicleSettingService;
	
	@Test
	public void actualVehicleSettingsServiceTest(){
		List<ActualVehicleSetting> actualVehicleSettings = actualVehicleSettingService.getActualVehicleSettingsForVehicle(2);
		System.out.println("actualVehicleSettings size for vehicleID = 2 is: "+actualVehicleSettings.size());
		for (ActualVehicleSetting avs : actualVehicleSettings){
			System.out.println(avs.toString());
		}
		assertNotNull(actualVehicleSettings);
	}
	@Test
	public void actualVehicleSettingsServiceCountTest(){
		Number actualVehicleSettingsCount = actualVehicleSettingService.getActualVehicleSettingCount();
		assertNotNull(actualVehicleSettingsCount);
		System.out.println("actualVehicleSettingsCount is: "+actualVehicleSettingsCount);
	}
}
