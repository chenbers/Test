package com.inthinc.pro.repository;

import com.inthinc.pro.domain.VDDLog;

public interface VddLogRepository extends GenericRepository<VDDLog, Integer>{

	public Integer getDeviceIDByVehicleID(Integer vehicleID);
}
