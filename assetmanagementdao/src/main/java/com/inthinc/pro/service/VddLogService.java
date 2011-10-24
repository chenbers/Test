package com.inthinc.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.domain.Vehicle;
import com.inthinc.pro.repository.VddLogRepository;

@Service
public class VddLogService {
    @Autowired
    VddLogRepository vddLogRepository;
    
	public Number getVddLogCount(){
		return vddLogRepository.count();
	}

	public VDDLog getVddLogByID(Integer id){
		return vddLogRepository.findByID(id);
	}
	public Device getDeviceByVehicleID(Integer vehicleID){
		return vddLogRepository.getDeviceByVehicleID(vehicleID);
	}
	public Vehicle getVehicleByDeviceID(Integer deviceID){
		return vddLogRepository.getVehicleByDeviceID(deviceID);
	}
}
