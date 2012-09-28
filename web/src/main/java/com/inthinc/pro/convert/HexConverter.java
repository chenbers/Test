package com.inthinc.pro.convert;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class HexConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        if ((value != null) && (value.length() > 0))
            try
            {
                return new BigInteger(value, 16).longValue();
            }
            catch (NumberFormatException e)
            {
                throw new ConverterException(e);
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value instanceof Long){
            String hexString = Long.toHexString((Long) value).toUpperCase();
            return hexString.equals("0")? "" : hexString;
        }else
            return null;
    }
}
