package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.ForwardCommand;
import com.inthinc.pro.repository.ForwardCommandRepository;

@Repository
public class ForwardCommandRepositoryImpl extends GenericRepositoryImpl<ForwardCommand, Integer> 
								   implements ForwardCommandRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<ForwardCommand> getForwardCommandsByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT v.deviceID FROM VDDLog v WHERE v.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		List<ForwardCommand> forwardCommands = (List<ForwardCommand>)query.getResultList();
		return forwardCommands;
	}


}