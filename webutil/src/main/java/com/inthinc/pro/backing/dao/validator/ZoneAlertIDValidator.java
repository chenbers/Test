package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.model.ZoneAlert;

public class ZoneAlertIDValidator extends DefaultValidator {

	ZoneAlertDAO zoneAlertDAO;

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
			ZoneAlert  zoneAlert = zoneAlertDAO.findByID(id);
			
			if (zoneAlert == null)
				return false;
			
			return (isValidAccountID(zoneAlert.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The zoneAlertID is not valid.";
	}


	public ZoneAlertDAO getZoneAlertDAO() {
		return zoneAlertDAO;
	}

	public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO) {
		this.zoneAlertDAO = zoneAlertDAO;
	}


}
