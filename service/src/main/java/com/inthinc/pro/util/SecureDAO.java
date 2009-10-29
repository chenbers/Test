package com.inthinc.pro.util;

import java.util.List;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.security.userdetails.ProUser;

public abstract class SecureDAO<T> {

    protected static final Role inthincRole = Roles.getRoleByName("inthinc");
	
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
    
    public abstract List<T> getAll();
    public abstract T findByID(Integer id);
    public abstract Integer create(T object);
    public abstract T update(T object);
    public abstract Integer delete(Integer id);

    public abstract boolean isAuthorized(T object);    
}
