package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.security.userdetails.ProUser;


public abstract class BaseConverter implements Converter
{

	
    public ProUser getUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ProUser)
            return (ProUser)principal;
        
        return null;
    }

	public abstract Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException;

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
	{
	    return value.toString();
	}

}
