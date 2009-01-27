package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import com.inthinc.pro.util.MessageUtil;

public class DistanceConverter extends BaseConverter
{

    // converts distance to mi/km string
    // distance is an integer in hundreths of miles
    
    Boolean isMetric;
    private static final double KILOMETERS_PER_MILE = 1.609344;

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value == null)
        {
            return "";
        }
        Integer distance = (Integer)value;
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        Float miles = distance/100.0f;

        if (getIsMetric())
        {
            return format.format(KILOMETERS_PER_MILE * miles) + " " + MessageUtil.getMessageString(context, "km_label");
            
        }
        else
        {
            return format.format(miles) + " " + MessageUtil.getMessageString(context, "mi_label");
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
