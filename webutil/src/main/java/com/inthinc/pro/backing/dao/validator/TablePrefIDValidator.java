package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.User;

public class TablePrefIDValidator extends DefaultValidator {

	TablePreferenceDAO tablePreferenceDAO;
	UserDAO userDAO;

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
			TablePreference tablePreference  = tablePreferenceDAO.findByID(id);
			
			if (tablePreference == null)
				return false;

			Integer userID = tablePreference.getUserID();
			
			User user = userDAO.findByID(userID);
			
			return (user != null && user.getPerson() != null && isValidAccountID(user.getPerson().getAcctID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The tablePrefID is not valid.";
	}

	public TablePreferenceDAO getTablePreferenceDAO() {
		return tablePreferenceDAO;
	}

	public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO) {
		this.tablePreferenceDAO = tablePreferenceDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


}
