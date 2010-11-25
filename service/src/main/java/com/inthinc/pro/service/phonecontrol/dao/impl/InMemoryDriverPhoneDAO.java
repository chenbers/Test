package com.inthinc.pro.service.phonecontrol.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Implements a container for list of drivers with disabled phones  
 * It uses a simple in-memory approach.
 * 
 * @author dcueva
 *
 */
public class InMemoryDriverPhoneDAO implements DriverPhoneDAO {

	private List<Integer> driverIDList = new ArrayList<Integer>(); 
	

	/**
	 * @see com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO#getDriversWithDisabledPhones()
	 */
	@Override
	public List<Integer> getDriversWithDisabledPhones() {
		return driverIDList;
	}
	
	/**
	 * @see com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO#addDriverToDisabledPhoneList(java.lang.Integer)
	 */
	@Override
	public void addDriverToDisabledPhoneList(Integer driverID) {
		driverIDList.add(driverID);

	}

	@Override
	public void removeDriverFromDisabledPhoneList(Integer driverID) {
		driverIDList.remove(driverID);
		
	}

}
