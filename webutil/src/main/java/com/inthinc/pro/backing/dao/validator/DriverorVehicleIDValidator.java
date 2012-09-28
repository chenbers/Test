package com.inthinc.pro.backing.dao.validator;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class DriverorVehicleIDValidator extends DefaultValidator {

	DriverDAO driverDAO;
	VehicleDAO vehicleDAO;
	GroupDAO groupDAO;
	
	private final static String TYPE_CLIENT_ID = "paramsForm:param:1:selValue"; 
	private final static String DRIVER = "1"; 
	private final static String VEHICLE = "2"; 

	@Override
	public boolean isValid(String input) {
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		
		// first figure out if we are looking at driver or vehicle
		if (isDriverSelected())	{
			try {
				Driver driver = driverDAO.findByID(id);
				
				return (driver != null && isValidAccountID(driver.getPerson().getAcctID()));
			}
			catch (Exception ex) {
				return false;
			}
		}
		else {
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
	}

	@Override
	public String invalidMsg() {
		if (isDriverSelected())
			return "The driverID is not valid.";
		
		return "The vehicleID is not valid.";
	}

	private boolean isDriverSelected() {
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String value = requestParamMap.get(TYPE_CLIENT_ID);
		return value != null && value.equals(DRIVER);

	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
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
