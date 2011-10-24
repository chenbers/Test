package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.ForwardCommandIridium;
import com.inthinc.pro.repository.ForwardCommandIridiumRepository;

@Repository
public class ForwardCommandIridiumRepositoryImpl extends GenericRepositoryImpl<ForwardCommandIridium, Integer> 
implements ForwardCommandIridiumRepository {

	@SuppressWarnings("unchecked")
	public List<ForwardCommandIridium> getForwardCommandsByVehicleID(Integer vehicleID) {
		Query query = entityManager.createQuery("SELECT v.deviceID FROM VDDLog v WHERE v.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		List<ForwardCommandIridium> forwardCommands = (List<ForwardCommandIridium>)query.getResultList();
		return forwardCommands;
	}

}
