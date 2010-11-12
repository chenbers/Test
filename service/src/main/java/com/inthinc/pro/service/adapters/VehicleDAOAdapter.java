/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;

/**
 * Adapter for the Vehicle resources.
 * 
 * @author dcueva
 *
 */
public class VehicleDAOAdapter extends BaseDAOAdapter<Vehicle> {

    private VehicleDAO vehicleDAO;
	
	@Override
	public List<Vehicle> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<Vehicle, Integer> getDAO() {
		return vehicleDAO;
	}

    /**
     * Retrieve the ID for the vehicle. </br>
     * Overriding because the ID for the vehicle is the Group ID, not the account ID.</br>
     * The create() method from BaseDAOAdapter will call this overriden method.</br>
     * 
     * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#getResourceID(java.lang.Object)
	 */
	@Override
	protected Integer getResourceID(Vehicle vehicle) {
		return vehicle.getGroupID();
	}	
	
}
