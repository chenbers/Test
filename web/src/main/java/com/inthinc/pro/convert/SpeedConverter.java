package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import com.inthinc.pro.util.MessageUtil;

public class SpeedConverter extends BaseConverter
{
    // converts speed to mph or km/h string
    // speed is an integer in miles
   
    Boolean isMetric;
    private static final double KILOMETERS_PER_MILE = 1.609344;
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value instanceof Long)
        {
            value = new Integer(((Long)value).intValue());
        }
        Integer speed = (Integer)value;
        NumberFormat format = NumberFormat.getInstance(getLocale());
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
    
        if (getIsMetric())
        {
            return format.format(KILOMETERS_PER_MILE * speed) + " " + MessageUtil.getMessageString(context, "kmh_label");
            
        }
        else
        {
            return format.format(speed + " " + MessageUtil.getMessageString(context, "mph_label"));
        }
        
    }
    
    public Boolean getIsMetric()
    {
        //TODO: get isMetric from the user record
        return (isMetric == null) ? false : isMetric;
    }
    
    public void setIsMetric(Boolean isMetric)
    {
        this.isMetric = isMetric;
    }

}
