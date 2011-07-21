package com.inthinc.pro.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.util.MessageUtil;

/**
 * 
 * @author mstrong
 * 
 * This dateTimeConverter is to assist when the f:convertDateTime does not work. 
 * This usually occurs when the f:converDateTime is used withing a table.
 * 
 */

public class DateTimeConverter extends BaseConverter
{
     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
        sdf.setTimeZone(getTimeZone());
        try
        {
           date =  sdf.parse(value);
        }catch(ParseException ex){
           throw new ConverterException(ex);
        }
        return date;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        String returnString = null;
        if (value instanceof Date)
        {            
            Date date = (Date) value;

            SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
            sdf.setTimeZone(getTimeZone());
            returnString = sdf.format(date);
        }
        return returnString;
    }
}
