package com.inthinc.pro.repository.jpa;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.repository.DeviceRepository;

@Repository
public class DeviceRepositoryImpl extends GenericRepositoryImpl<Device, Integer> 
								  implements DeviceRepository {
	@Override
	public Device getDeviceIDByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT d FROM VDDLog v, Device d WHERE v.vehicleID=:vehicleID and v.stop is null and d.deviceID = v.deviceID")
		.setParameter("vehicleID", vehicleID);

		Device device = (Device)query.getSingleResult();
		return device;
	}
}
