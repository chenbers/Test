package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

public class UserIDValidator extends DefaultValidator {

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
			User user = userDAO.findByID(id);
			
			return (user != null && isValidAccountID(user.getPerson().getAcctID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The userID is not valid.";
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


}
