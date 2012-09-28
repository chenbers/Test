package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class VehicleIDValidator extends DefaultValidator {

	VehicleDAO vehicleDAO;
	GroupDAO groupDAO;
	


	@Override
	public boolean isValid(String input) {
		
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			Vehicle vehicle = vehicleDAO.findByID(id);
			
			if (vehicle == null)
				return false;
			
			Integer groupID = vehicle.getGroupID();
			Group group = groupDAO.findByID(groupID);
			
			return (group != null && isValidAccountID(group.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The vehicleID is not valid.";
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}


}
