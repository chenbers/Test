package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;

public class FloatConverter extends BaseConverter
{
    private static final Logger logger = Logger.getLogger(FloatConverter.class);
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Float(value);
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        Float floatValue = (Float)value;
        NumberFormat format = NumberFormat.getInstance(getLocale());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        return format.format(floatValue);

    }

}

