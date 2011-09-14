package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.DesiredVehicleSetting;


public interface DesiredVehicleSettingRepository extends
							GenericRepository<DesiredVehicleSetting, Integer>{

	List<DesiredVehicleSetting> getDesiredVehicleSettingsForVehicle(Integer vehicleID);
}
