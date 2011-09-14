package com.inthinc.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.ActualVehicleSetting;
import com.inthinc.pro.repository.ActualVehicleSettingRepository;

@Service
public class ActualVehicleSettingService{

    @Autowired
    ActualVehicleSettingRepository actualVehicleSettingRepository;
	public List<ActualVehicleSetting> getActualVehicleSettingsForVehicle(Integer vehicleID){
		return actualVehicleSettingRepository.getActualVehicleSettingsForVehicle(vehicleID);
	}
	public Number getActualVehicleSettingCount(){
		return actualVehicleSettingRepository.count();
	}
}
