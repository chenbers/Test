package com.inthinc.pro.repository.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import com.inthinc.pro.domain.settings.ActualVehicleSetting;
import com.inthinc.pro.repository.ActualVehicleSettingRepository;


@Repository
public class ActualVehicleSettingRepositoryImpl extends
		GenericRepositoryImpl<ActualVehicleSetting, Integer> implements
		ActualVehicleSettingRepository {
	static final Logger LOG = LoggerFactory.getLogger(ActualVehicleSettingRepositoryImpl.class);
	
	public List<ActualVehicleSetting> getActualVehicleSettingsForVehicle(Integer vehicleID){
	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	    StatusPrinter.print(lc);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActualVehicleSetting> c = cb.createQuery(ActualVehicleSetting.class);
		Root<ActualVehicleSetting> actualVehicleSetting = c.from(ActualVehicleSetting.class);
		c.select(actualVehicleSetting)
		.where(cb.equal(actualVehicleSetting.get("vehicleID"), 2));
		TypedQuery<ActualVehicleSetting> typedQuery = entityManager.createQuery(c);
		
		return typedQuery.getResultList();
	}
}
