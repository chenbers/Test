package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.model.RedFlagAlert;

public class RedFlagAlertIDValidator extends DefaultValidator {

	RedFlagAlertDAO redFlagAlertDAO;

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
			RedFlagAlert  redFlagAlert = redFlagAlertDAO.findByID(id);
			
			if (redFlagAlert == null)
				return false;
			
			return (isValidAccountID(redFlagAlert.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The redFlagAlertID is not valid.";
	}
	public RedFlagAlertDAO getRedFlagAlertDAO() {
		return redFlagAlertDAO;
	}

	public void setRedFlagAlertDAO(RedFlagAlertDAO redFlagAlertDAO) {
		this.redFlagAlertDAO = redFlagAlertDAO;
	}


}
