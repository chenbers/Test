package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class LbsToKgConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent compoent, String value)
    {
        if(value != null && !value.equals("")){
            if(getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
            {
                Long weight = Long.valueOf(value);
                return MeasurementConversionUtil.fromKgToPounds(weight).intValue();
            }
            else
            {
                return Integer.valueOf(value);
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (Integer.class.isInstance(value))
        {
            if(getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromPoundsToKg(((Integer)value).longValue()).toString();
            else
                return value.toString();
        }
        return null;
    }
    
}
