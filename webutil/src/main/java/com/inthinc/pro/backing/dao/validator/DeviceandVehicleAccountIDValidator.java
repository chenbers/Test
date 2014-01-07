package com.inthinc.pro.backing.dao.validator;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class DeviceandVehicleAccountIDValidator extends DefaultValidator {

	DeviceDAO deviceDAO;
	VehicleDAO vehicleDAO;
	GroupDAO groupDAO;
	Integer vehicleAcctId;
	Integer deviceAcctId;
	Boolean vehicleDeviceAccountIDMatch = true;
	private final static String VEHICLE_ID = "paramsForm:param:0:value"; 
	

	@Override
	public boolean isValid(String input) {
		vehicleDeviceAccountIDMatch = true;
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			Device device = deviceDAO.findByID(id);
			
			if (device == null || !isValidAccountID(device.getAccountID()))
				return false;
			
			deviceAcctId = device.getAccountID();
			vehicleAcctId = checkVehicleAccountID();
			
			if (vehicleAcctId != null){
				if (!vehicleAcctId.equals(deviceAcctId)){
					vehicleDeviceAccountIDMatch = false;
					return false;
				}
			}
			
			return true;
				
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	private Integer checkVehicleAccountID(){
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String value = requestParamMap.get(VEHICLE_ID);
		Integer vehicleID = null;
		
		try {
			vehicleID = Integer.valueOf(value);
		}
		catch (NumberFormatException ex) {
			return null;
		}
		try {
			Vehicle vehicle = vehicleDAO.findByID(vehicleID);
			
			if (vehicle == null)
				return null;
			
			Integer groupID = vehicle.getGroupID();
			Group group = groupDAO.findByID(groupID);
			
			if (group == null) 
				return null;
			
			return group.getAccountID();
		}
		catch (Exception ex) {
			return null;
		}
	}

	@Override
	public String invalidMsg() {
		if (!vehicleDeviceAccountIDMatch)
			return "vehicle and device are not in the same account.";
		else
		   return "The deviceID is not valid.";
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
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
