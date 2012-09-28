package com.inthinc.pro.backing.dao.validator;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.security.userdetails.ProUser;

public abstract class BaseValidator implements GenericValidator {

	private SuperuserDAO superuserDAO;
	
	public boolean isValidAccountID(Integer accountID)
	{
		if (isSuperuser())
			return true;
		
		return (getAccountID().equals(accountID));
	}
	
    public boolean isSuperuser() {
    	
    	Integer userID = getProUser().getUser().getUserID();
    	
    	return superuserDAO.isSuperuser(userID);
    }
	
    public Integer getAccountID() {
        return getProUser().getUser().getPerson().getAcctID();
    }

    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
	public abstract String invalidMsg();

	@Override
	public abstract boolean isValid(String input);
	
    public SuperuserDAO getSuperuserDAO() {
		return superuserDAO;
	}

	public void setSuperuserDAO(SuperuserDAO superuserDAO) {
		this.superuserDAO = superuserDAO;
	}
}
