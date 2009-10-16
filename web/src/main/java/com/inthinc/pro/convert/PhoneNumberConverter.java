package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.util.MiscUtil;

public class PhoneNumberConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
//        return MiscUtil.unformatPhone(value);
    	return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        return MiscUtil.formatPhone(value.toString());
    }
}
