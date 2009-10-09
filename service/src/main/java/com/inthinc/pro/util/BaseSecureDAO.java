package com.inthinc.pro.util;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

public abstract class BaseSecureDAO {

	
    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser() {
        return getProUser().getUser();
    }

    public Person getPerson() {
        return getUser().getPerson();
    }

    public Integer getGroupID() {
        return getUser().getGroupID();
    }

    public Integer getAccountID() {
        return getUser().getPerson().getAcctID();
    }
}
