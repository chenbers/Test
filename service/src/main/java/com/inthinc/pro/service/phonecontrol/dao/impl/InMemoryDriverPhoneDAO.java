package com.inthinc.pro.service.phonecontrol.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Implements a container for list of drivers with disabled phones  
 * It uses a simple in-memory approach.
 * 
 * @author dcueva
 *
 */
@Component
public class InMemoryDriverPhoneDAO implements DriverPhoneDAO {

	private Set<Integer> driverIDSet = new HashSet<Integer>(); 
	

	/**
	 * @see com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO#getDriversWithDisabledPhones()
	 */
	@Override
	public Set<Integer> getDriversWithDisabledPhones() {
		return driverIDSet;
	}
	
	/**
	 * @see com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO#addDriverToDisabledPhoneList(java.lang.Integer)
	 */
	@Override
	public void addDriverToDisabledPhoneList(Integer driverID) {
		driverIDSet.add(driverID);

	}

	@Override
	public void removeDriverFromDisabledPhoneList(Integer driverID) {
		driverIDSet.remove(driverID);
	}

}
