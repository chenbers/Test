package com.inthinc.pro.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.VehicleSettingHistoryJPA;
import com.inthinc.pro.repository.VehicleSettingHistoryRepository;


@Service
public class VehicleSettingHistoryService{
	
    @Autowired
    VehicleSettingHistoryRepository vehicleSettingHistoryRepository;
    
    public List<VehicleSettingHistoryJPA> findAllHistory() {
		List<VehicleSettingHistoryJPA> ts = vehicleSettingHistoryRepository.findAll();
		return ts;
    }
	public List<VehicleSettingHistoryJPA> getVehicleSettingHistoryForVehicle(Integer vehicleID, Date startTime, Date endTime){
		return vehicleSettingHistoryRepository.getVehicleSettingHistoryForVehicle(vehicleID, startTime, endTime);
	}
}
