package com.inthinc.pro.repository;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.domain.settings.VehicleSettingHistory;


public interface VehicleSettingHistoryRepository
			extends GenericRepository<VehicleSettingHistory,Integer>{

	public List<VehicleSettingHistory> getVehicleSettingHistoryForVehicle(Integer vehicleID, Date startTime, Date endTime);

}
