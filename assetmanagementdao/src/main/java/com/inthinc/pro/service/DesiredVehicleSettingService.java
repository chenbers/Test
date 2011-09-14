package com.inthinc.pro.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.DesiredVehicleSetting;
import com.inthinc.pro.repository.DesiredVehicleSettingRepository;


@Service
public class DesiredVehicleSettingService{
	
    @Autowired
    DesiredVehicleSettingRepository desiredVehicleSettingRepository;
    
	public List<DesiredVehicleSetting> getDesiredVehicleSettingsForVehicle(Integer vehicleID){
		return desiredVehicleSettingRepository.getDesiredVehicleSettingsForVehicle(vehicleID);
	}
	public Number getDesiredVehicleSettingCount(){
		return desiredVehicleSettingRepository.count();
	}
	public DesiredVehicleSetting updateDesiredVehicleSetting(DesiredVehicleSetting desiredVehicleSetting){
		desiredVehicleSetting.setModified(new Date());
		return desiredVehicleSettingRepository.update(desiredVehicleSetting);
	}
//	public Number updateDesiredVehicleSettings(List<DesiredVehicleSetting> desiredVehicleSettings){
//		return 0;
//	}
	public DesiredVehicleSetting createDesiredVehicleSetting(DesiredVehicleSetting desiredVehicleSetting){
		desiredVehicleSetting.setModified(new Date());
		DesiredVehicleSetting createdDesiredVehicleSetting = desiredVehicleSettingRepository.save(desiredVehicleSetting);
		return createdDesiredVehicleSetting;
	}
	public void deleteDesiredSetting(DesiredVehicleSetting desiredVehicleSetting){
		desiredVehicleSettingRepository.delete(desiredVehicleSetting);
	}
}
