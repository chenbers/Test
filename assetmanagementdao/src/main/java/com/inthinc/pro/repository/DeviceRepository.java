package com.inthinc.pro.repository;

import com.inthinc.pro.domain.Device;

public interface DeviceRepository extends GenericRepository<Device, Integer>{
	public Device getDeviceIDByVehicleID(Integer vehicleID);
}
