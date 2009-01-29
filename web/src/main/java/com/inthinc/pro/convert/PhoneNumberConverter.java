package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class PhoneNumberConverter extends BaseConverter
{
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        String number = getDigitsOnly(value.toString());
        if (number.length() == 10)
        {
            return makePretty(number);
        }
        
        return number;
    }
    
    /**
     * returns 888-111-2222
     */
    public String addDashes(String s)
    {
        return s.substring(0, 3) + "-" + s.substring(3, 6) + "-" + s.substring(6, 10);
    }
    
    /**
     * returns (888) 111-2222
     */
    public String makePretty(String s)
    {
        return "(" + s.substring(0, 3) + ") " + s.substring(3, 6) + "-" + s.substring(6, 10);
    }

    public String getDigitsOnly(String s)
    {
        StringBuilder digitsOnly = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);
            if (Character.isDigit(c))
            {
                digitsOnly.append(c);
            }
        }
        return digitsOnly.toString();
    }

}
