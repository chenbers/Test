package com.inthinc.pro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.VDDLog;
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
	public Integer getDeviceIDByVehicleID(Integer vehicleID){
		return vddLogRepository.getDeviceIDByVehicleID(vehicleID);
	}
}
