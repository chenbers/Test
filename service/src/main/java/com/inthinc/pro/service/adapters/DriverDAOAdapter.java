/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Driver;

/**
 * Adapter for the Driver resources.
 *  
 * @author dcueva
 */
public class DriverDAOAdapter extends BaseDAOAdapter<Driver> {


	private DriverDAO driverDAO;
	
	@Override
	public List<Driver> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<Driver, Integer> getDAO() {
		return driverDAO;
	}

    /**
     * Retrieve the ID for the driver. </br>
     * Overriding because the ID for the driver is the Person ID, not the account ID.</br>
     * The create() method from BaseDAOAdapter will call this overriden method.</br>
     *  
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#getAccountID(java.lang.Object)
	 */
	@Override
	protected Integer getResourceID(Driver driver) {
		return driver.getPersonID();
	}
}
