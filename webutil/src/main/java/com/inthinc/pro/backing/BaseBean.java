package com.inthinc.pro.backing;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

public class BaseBean {
	
	private SuperuserDAO superuserDAO;
	private Boolean superuserFlag;
	
    public boolean isLoggedIn() {
        return isProUserLoggedIn();
    }

    public boolean isSuperuser() {
        
        if (superuserFlag == null) {
            Integer userID = getProUser().getUser().getUserID();
    	
            setSuperuserFlag(superuserDAO.isSuperuser(userID));
        }
        return getSuperuserFlag();
    }

   
    public boolean getIsSuperuser() {
    	
        return isSuperuser();
    }

    public Person getPerson() {
        return getProUser().getUser().getPerson();
    }

    public User getUser() {
    	if (getProUser() == null)
    		return null;
        return getProUser().getUser();
    }

    public Integer getAccountID() {
        return getPerson().getAcctID();
    }

    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isProUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof ProUser;
    }

    public SuperuserDAO getSuperuserDAO() {
		return superuserDAO;
	}

	public void setSuperuserDAO(SuperuserDAO superuserDAO) {
		this.superuserDAO = superuserDAO;
	}

    public GroupHierarchy getGroupHierarchy() {
        return getProUser().getGroupHierarchy();
    }
    public Boolean getSuperuserFlag() {
        return superuserFlag;
    }

    public void setSuperuserFlag(Boolean superuserFlag) {
        this.superuserFlag = superuserFlag;
    }


}
