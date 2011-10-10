package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.ForwardCommandIridium;

public interface ForwardCommandIridiumRepository extends GenericRepository<ForwardCommandIridium, Integer>{
	public List<ForwardCommandIridium> getForwardCommandsByVehicleID(Integer vehicleID);



}
