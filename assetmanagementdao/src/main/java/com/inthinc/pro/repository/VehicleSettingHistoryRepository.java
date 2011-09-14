package com.inthinc.pro.repository;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.domain.VehicleSettingHistoryJPA;


public interface VehicleSettingHistoryRepository
			extends GenericRepository<VehicleSettingHistoryJPA,Integer>{

	public List<VehicleSettingHistoryJPA> getVehicleSettingHistoryForVehicle(Integer vehicleID, Date startTime, Date endTime);

}
