package com.inthinc.pro.service.phonecontrol.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
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
	
	@Autowired
	private PhoneControlDAO phoneControlDAO;
	
	/**
	 * Constructor.
	 */
	public InMemoryDriverPhoneDAO() {
		regenerateDriverIDSet();
	}

	/**
	 * Tries to retrieve the drivers with disabled phones from the back-end
	 * This is done in case the JVM was shut down and there were still drivers with disabled phones.
	 * 
	 * The method must silently swallow all exceptions because it is called in the constructor.
	 **/
	void regenerateDriverIDSet() {
		try {
			List<Cellblock> drivers = phoneControlDAO.getDriversWithDisabledPhones();
			for (Cellblock driver : drivers) {
				this.driverIDSet.add(driver.getDriverID());
			}
		} catch (Exception e){}
	}

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
