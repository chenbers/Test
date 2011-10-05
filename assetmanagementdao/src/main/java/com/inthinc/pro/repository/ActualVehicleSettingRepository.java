package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.settings.ActualVehicleSetting;


public interface ActualVehicleSettingRepository extends
		GenericRepository<ActualVehicleSetting, Integer> {
	
	List<ActualVehicleSetting> getActualVehicleSettingsForVehicle(Integer vehicleID);
}
