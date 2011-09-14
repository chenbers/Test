package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.ActualVehicleSetting;
import com.inthinc.pro.repository.ActualVehicleSettingRepository;


@Repository
public class ActualVehicleSettingRepositoryImpl extends
		GenericRepositoryImpl<ActualVehicleSetting, Integer> implements
		ActualVehicleSettingRepository {

	public List<ActualVehicleSetting> getActualVehicleSettingsForVehicle(Integer vehicleID){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActualVehicleSetting> c = cb.createQuery(ActualVehicleSetting.class);
		Root<ActualVehicleSetting> actualVehicleSetting = c.from(ActualVehicleSetting.class);
		c.select(actualVehicleSetting)
		.where(cb.equal(actualVehicleSetting.get("vehicleID"), 2));
		TypedQuery<ActualVehicleSetting> typedQuery = entityManager.createQuery(c);
		
		return typedQuery.getResultList();
	}
}
