package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;


public class BaseBean
{
    private static final Logger logger = Logger.getLogger(BaseBean.class);
    private ErrorBean errorBean;
    
    public BaseBean()
    {
        
    }
    public ErrorBean getErrorBean()
    {
        return errorBean;
    }
    public void setErrorBean(ErrorBean errorBean)
    {
        this.errorBean = errorBean;
    }
    
    public Object getParameter(String name){
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("state");
    }
    
    public boolean isLoggedIn()
    {
        return isProUserLoggedIn();
    }

    public User getUser()
    {
        return getProUser().getUser();
    }
    
    public GroupHierarchy getGroupHierarchy()
    {
        return getProUser().getGroupHierarchy();
    }
    
    public Integer getAccountID()
    {
        return getProUser().getGroupHierarchy().getTopGroup().getAccountID();
        
    }
    
    public ProUser getProUser()
    {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public boolean isProUserLoggedIn()
    {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth != null && 
                   auth.isAuthenticated() &&
                   auth.getPrincipal() instanceof ProUser;
    }


}
