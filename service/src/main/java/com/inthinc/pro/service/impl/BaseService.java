package com.inthinc.pro.service.impl;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.util.SecurityBean;

public abstract class BaseService {
    
    protected SecurityBean securityBean;

    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean securityBean) {
        this.securityBean = securityBean;
    }

    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public User getUser() {
        return getProUser().getUser();
    }

    public Integer getAccountID() {
        return securityBean.getAccountID();
    }
}
