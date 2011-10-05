package com.inthinc.pro.repository.jpa;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.repository.VddLogRepository;

@Repository
public class VddLogRepositoryImpl  extends GenericRepositoryImpl<VDDLog, Integer> 
								   implements VddLogRepository {

	@Override
	public Integer getDeviceIDByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT v.deviceID FROM VDDLog v WHERE v.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		Integer deviceID = (Integer)query.getSingleResult();
		return deviceID;
	}
}
