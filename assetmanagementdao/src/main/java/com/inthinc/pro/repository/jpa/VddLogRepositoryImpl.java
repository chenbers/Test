package com.inthinc.pro.repository.jpa;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.domain.Vehicle;
import com.inthinc.pro.repository.VddLogRepository;

@Repository
public class VddLogRepositoryImpl  extends GenericRepositoryImpl<VDDLog, Integer> 
								   implements VddLogRepository {

	public Vehicle getVehicleByDeviceID(Integer deviceID) {
		Query query = entityManager.createQuery("SELECT v.vehicle FROM VDDLog v WHERE v.device.deviceID=:deviceID and v.stop is null")
		.setParameter("deviceID", deviceID);

		Vehicle vehicle = (Vehicle)query.getSingleResult();
		return vehicle;
	}

	public Device getDeviceByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT v.device FROM VDDLog v WHERE v.vehicle.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		Device device = (Device)query.getSingleResult();
		return device;
	}
}
