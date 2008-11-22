package com.inthinc.pro.util;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class WebUtil
{
    public String getRequestServerName()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        return request.getServerName();
    }

    public int getRequestServerPort()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    
        return request.getServerPort();
    }
    
    public String getRequestContextPath()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        return request.getContextPath();
    }

    public Map getRequestParameterMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
    
    public Map getRequestMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    }
    
    
    public Map getSessionMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public Locale getLocale()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public String getRealPath(String virtualPath){
    	
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    	return servletContext.getRealPath(virtualPath);
    	
    }
    public String getFullRequestContextPath(){
    	
		return "http://"+getRequestServerName()+":"+String.valueOf(getRequestServerPort())+getRequestContextPath();

    }
}
