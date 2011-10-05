package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.ForwardCommand;

public interface ForwardCommandRepository  extends GenericRepository<ForwardCommand, Integer>{

	public List<ForwardCommand> getForwardCommandsByVehicleID(Integer vehicleID);


}
