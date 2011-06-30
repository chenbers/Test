package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MeasurementTextConverter extends BaseConverter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        
        if(String.class.isInstance(value))
        {
            MeasurementType measurementType = getMeasurementType();
            if (measurementType==null){
                measurementType = MeasurementType.ENGLISH;
            }
            String text = String.class.cast(value);
               
            return MessageUtil.getMessageString(measurementType + "_" + text, getLocale());
           
        }
        return null;
    }
}
