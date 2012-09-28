package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

public class DeviceIDValidator extends DefaultValidator {

	DeviceDAO deviceDAO;
	

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
			Device device = deviceDAO.findByID(id);
			
			return (device != null && isValidAccountID(device.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The deviceID is not valid.";
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}


}
