package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MeasurementConverter extends BaseConverter implements Converter
{
    private static final String METRIC_PREFIX = "metric_";
    private static final String ENGLISH_PREFIX = "english_";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        
        if(String.class.isInstance(value))
        {
            MeasurementType measurementType = getUser().getUser().getMeasurementType();
            String text = String.class.cast(value);
            if(measurementType == null || measurementType.equals(MeasurementType.ENGLISH))
                return MessageUtil.getMessageString(ENGLISH_PREFIX + text, getUser().getUser().getLocale());
            if(getUser().getUser().getMeasurementType().equals(MeasurementType.METRIC))
                return MessageUtil.getMessageString(METRIC_PREFIX + text, getUser().getUser().getLocale());
        }
        // TODO Auto-generated method stub
        return null;
    }
}
