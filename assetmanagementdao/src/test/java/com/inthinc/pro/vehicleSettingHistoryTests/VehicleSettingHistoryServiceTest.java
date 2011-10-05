package com.inthinc.pro.vehicleSettingHistoryTests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.settings.VehicleSettingHistory;
import com.inthinc.pro.service.VehicleSettingHistoryService;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class VehicleSettingHistoryServiceTest {
    @Autowired
	private VehicleSettingHistoryService vehicleSettingHistoryService;
	
	@Test
	public void getVehicleSettingHistory(){
		List<VehicleSettingHistory> history = vehicleSettingHistoryService.findAllHistory();
        for (VehicleSettingHistory e : history)
            System.out.println("Found history: " + e.toString());
	}
}
