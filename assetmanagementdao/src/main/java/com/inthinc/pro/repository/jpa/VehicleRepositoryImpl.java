package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.Vehicle;
import com.inthinc.pro.repository.VehicleRepository;

@Repository
public class VehicleRepositoryImpl  extends GenericRepositoryImpl<Vehicle, Integer> 
									implements VehicleRepository {

	public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID){
		Query query = entityManager.createQuery("SELECT v.vehicleID FROM Vehicle v WHERE v.groupPath LIKE :groupPath")
									.setParameter("groupPath", "%/"+groupID+"/%");
		
		@SuppressWarnings("unchecked")
		List<Integer> vehicleIDs = (List<Integer>)query.getResultList();
		return vehicleIDs;
	}
}
