package com.inthinc.pro.util;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class WebUtil
{
    public static String getRequestServerName()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        return request.getServerName();
    }

    public static int getRequestServerPort()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    
        return request.getServerPort();
    }
    
    public static String getRequestContextPath()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        return request.getContextPath();
    }

    public static Map getRequestParameterMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
    
    public static Map getRequestMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    }
    
    
    public static Map getSessionMap()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public static Locale getLocale()
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public static String getRealPath(String virtualPath){
    	
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    	return servletContext.getRealPath(virtualPath);
    	
    }
    public static String getFullRequestContextPath(){
    	
		return "http://"+getRequestServerName()+":"+String.valueOf(getRequestServerPort())+getRequestContextPath();

    }
}
