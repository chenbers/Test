package com.inthinc.pro.repository;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.domain.Vehicle;

public interface VddLogRepository extends GenericRepository<VDDLog, Integer>{

	public Device getDeviceByVehicleID(Integer vehicleID);
	public Vehicle getVehicleByDeviceID(Integer deviceID);
}
