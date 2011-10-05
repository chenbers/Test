package com.inthinc.pro.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.settings.VehicleSettingHistory;
import com.inthinc.pro.repository.VehicleSettingHistoryRepository;


@Repository
public class VehicleSettingHistoryRepositoryImpl 
								extends GenericRepositoryImpl<VehicleSettingHistory,Integer>
								implements VehicleSettingHistoryRepository 
{
	public List<VehicleSettingHistory> getVehicleSettingHistoryForVehicle(Integer vehicleID, Date startTime, Date endTime){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<VehicleSettingHistory> c = cb.createQuery(VehicleSettingHistory.class);
		Root<VehicleSettingHistory> vehicleSettingHistoryJPA = c.from(VehicleSettingHistory.class);
		Predicate vehicle = cb.equal(vehicleSettingHistoryJPA.get("vehicleID"), vehicleID);
		Predicate vehicleAndDateRange = cb.and(vehicle,cb.between(vehicleSettingHistoryJPA.<Date>get("modified"), startTime, endTime));
		c.select(vehicleSettingHistoryJPA).where(vehicleAndDateRange);
		TypedQuery<VehicleSettingHistory> typedQuery = entityManager.createQuery(c);
		
		return typedQuery.getResultList();
	}

}
