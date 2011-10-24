package com.inthinc.pro.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.settings.VehicleSettingHistory;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleSettingHistoryServiceTest {
    @Autowired
	private VehicleSettingHistoryService vehicleSettingHistoryService;
	
	@Test
	@Ignore
	public void getVehicleSettingHistory(){
		List<VehicleSettingHistory> history = vehicleSettingHistoryService.findAllHistory();
        for (VehicleSettingHistory e : history)
            System.out.println("Found history: " + e.toString());
	}
}
