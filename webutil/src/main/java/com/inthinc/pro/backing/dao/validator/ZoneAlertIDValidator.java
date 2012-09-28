package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.RedFlagAlert;

public class ZoneAlertIDValidator extends DefaultValidator {

	RedFlagAlertDAO zoneAlertDAO;

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
			RedFlagAlert  zoneAlert = zoneAlertDAO.findByID(id);
			
			if (zoneAlert == null)
				return false;
			
			if(!zoneAlert.getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE) &&
			   !zoneAlert.getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE))
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


	public RedFlagAlertDAO getZoneAlertDAO() {
		return zoneAlertDAO;
	}

	public void setZoneAlertDAO(RedFlagAlertDAO zoneAlertDAO) {
		this.zoneAlertDAO = zoneAlertDAO;
	}


}
