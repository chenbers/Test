package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

public class GroupIDValidator extends DefaultValidator {

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
			Group group = groupDAO.findByID(id);
			
			return (group != null && isValidAccountID(group.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The groupID is not valid.";
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}


}
