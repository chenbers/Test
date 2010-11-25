/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.dao;

import java.util.List;

/**
 * This interface allows the access to a list of drivers with disabled phones.
 * It uses the Data Access Object pattern.
 * 
 * It is up to the implementations how to handle the list.
 * 
 * @author dcueva
 */
public interface DriverPhoneDAO {

	/**
	 * Returns a list of all drivers with disabled phones.
	 * 
	 * @return List of driver IDs.
	 */
	public List<Integer> getDriversWithDisabledPhones();
	
	/**
	 * Adds a driver ID to the list of drivers with disabled phones.
	 * 
	 * @param driverID Driver ID to be added.
	 */
	public void addDriverToDisabledPhoneList(Integer driverID);
	
	/**
	 * Removes a driver ID to the list of drivers with disabled phones.
	 * 
	 * @param driverID Driver ID to be removed.
	 */
	public void removeDriverFromDisabledPhoneList(Integer driverID);
	
}
