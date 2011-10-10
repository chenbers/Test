package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.ForwardCommandHttp;
import com.inthinc.pro.repository.ForwardCommandHttpRepository;

@Repository
public class ForwardCommandHttpRepositoryImpl extends GenericRepositoryImpl<ForwardCommandHttp, Integer> 
								   implements ForwardCommandHttpRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<ForwardCommandHttp> getForwardCommandsByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT v.deviceID FROM VDDLog v WHERE v.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		List<ForwardCommandHttp> forwardCommands = (List<ForwardCommandHttp>)query.getResultList();
		return forwardCommands;
	}


}