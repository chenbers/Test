package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.Roles;

public class RoleConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
 //       return Roles.getRoleByName(value);
    	return new Role();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value instanceof Role)
            return ((Role)value).getName();
        else
            return null;
    }
}