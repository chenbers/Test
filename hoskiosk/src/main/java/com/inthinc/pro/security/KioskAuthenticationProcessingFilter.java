package com.inthinc.pro.security;

import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.security.userdetails.ProUserServiceImpl;

public class KioskAuthenticationProcessingFilter extends AuthenticationProcessingFilter {
    
    private HOSDAO hosDAO;
    
    ProUserServiceImpl userDetailsService;

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    protected String obtainPassword(javax.servlet.http.HttpServletRequest request)
    {
        String lastName = super.obtainPassword(request);
        String employeeId = super.obtainUsername(request);
        getUserDetailsService().setHosDriverLogin(null);

        HOSDriverLogin hosDriverLogin = hosDAO.getDriverForEmpidLastName(employeeId, lastName);
        if (hosDriverLogin != null)
        {
            getUserDetailsService().setHosDriverLogin(hosDriverLogin);
        }
        
        return lastName;
        
        
    }

    public ProUserServiceImpl getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(ProUserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    
}
