package com.inthinc.pro.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.VDDLog;
import com.inthinc.pro.repository.DeviceRepository;

@Repository
public class DeviceRepositoryImpl extends GenericRepositoryImpl<Device, Integer> 
								  implements DeviceRepository {
	public Device getDeviceIDByVehicleID(Integer vehicleID) {
		
		Query query = entityManager.createQuery("SELECT v.device FROM VDDLog v WHERE v.vehicle.vehicleID=:vehicleID and v.stop is null")
		.setParameter("vehicleID", vehicleID);

		Device device = (Device)query.getSingleResult();
		return device;
	}
	public Device getDeviceIDByVehicleIDCriteria(Integer vehicleID){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Device> c = cb.createQuery(Device.class);
		Root<VDDLog> vddLog = c.from(VDDLog.class);
		c.select(vddLog.<Device>get("device"));
		 List<Predicate> criteria = new ArrayList<Predicate>();
         if (vehicleID != null) {
            ParameterExpression<Integer> p =
                cb.parameter(Integer.class, "vehicleID");
            criteria.add(cb.equal(vddLog.get("vehicle").get("vehicleID"), p));
         }
         criteria.add(cb.isNull(vddLog.get("stop")));
	  
//         if (criteria.size() == 0) {
//             throw new RuntimeException("no criteria");
//         } else if (criteria.size() == 1) {
//            c.where(criteria.get(0));
//         } else {
             c.where(cb.and(criteria.toArray(new Predicate[0])));
//         }

         TypedQuery<Device> query = entityManager.createQuery(c);
         if (vehicleID != null) { query.setParameter("vehicleID", vehicleID); }
 		 return (Device)query.getSingleResult();

//		c.select(device)
//		 .where(cb.equal(vddLog.get("device").get("vehicleID"), vehicleID));
//		return new Device();
	}
}
