package com.inthinc.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.repository.VehicleRepository;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    
	public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID){
		return vehicleRepository.getVehicleIDsByGroupIDDeep(groupID);
	}
	public Number getVehicleCount(){
		return vehicleRepository.count();
	}

//	public Vehicle getVehicleByID(Integer id){
//		return vehicleRepository.findByID(id);
//	}

}
