package com.inthinc.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.repository.DeviceRepository;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    
	public Number getDeviceCount(){
		return deviceRepository.count();
	}

	public Device getDeviceByID(Integer id){
		return deviceRepository.findByID(id);
	}
	public Device getDeviceByVehicleID(Integer vehicleID){
		return deviceRepository.getDeviceIDByVehicleID(vehicleID);
	}

	public Device getDeviceByVehicleIDCriteria(Integer vehicleID) {
		// TODO Auto-generated method stub
		return deviceRepository.getDeviceIDByVehicleIDCriteria(vehicleID);
	}
}
