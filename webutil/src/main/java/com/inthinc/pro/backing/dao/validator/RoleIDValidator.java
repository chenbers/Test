package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.security.Role;

public class RoleIDValidator extends DefaultValidator {

	RoleDAO roleDAO;
	
	

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
			Role role = roleDAO.findByID(id);
			
			return (role != null && isValidAccountID(role.getAcctID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The roleID is not valid.";
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}



}
