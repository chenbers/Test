package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.settings.DesiredVehicleSetting;
import com.inthinc.pro.repository.DesiredVehicleSettingRepository;


@Repository
public class DesiredVehicleSettingRepositoryImpl extends
GenericRepositoryImpl<DesiredVehicleSetting, Integer> implements
DesiredVehicleSettingRepository {

	public List<DesiredVehicleSetting> getDesiredVehicleSettingsForVehicle(Integer vehicleID){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DesiredVehicleSetting> c = cb.createQuery(DesiredVehicleSetting.class);
		Root<DesiredVehicleSetting> desiredVehicleSetting = c.from(DesiredVehicleSetting.class);
		c.select(desiredVehicleSetting)
		 .where(cb.equal(desiredVehicleSetting.get("vehicleID"), vehicleID));
		TypedQuery<DesiredVehicleSetting> typedQuery = entityManager.createQuery(c);
	
		return typedQuery.getResultList();
	}
}
