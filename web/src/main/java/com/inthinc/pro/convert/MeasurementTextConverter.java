package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.model.FuelEfficiencyType;
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
            MeasurementType measurementType = getUser().getUser().getPerson().getMeasurementType();
            if (measurementType==null){
                measurementType = MeasurementType.ENGLISH;
            }
            String text = String.class.cast(value);
            
            FuelEfficiencyType fuelEfficiencyType = getUser().getUser().getPerson().getFuelEfficiencyType();
            
            if (fuelEfficiencyType == null) {
                
                return MessageUtil.getMessageString(measurementType + "_" + "DEFAULT"+"_"+text, getUser().getUser().getLocale());
            }
            else {
            
                return MessageUtil.getMessageString(measurementType + "_" + fuelEfficiencyType.name()+"_"+text, getUser().getUser().getLocale());
            }
            
//                return MessageUtil.getMessageString(MeasurementType.ENGLISH + "_" + text, getUser().getUser().getLocale());
//            if(measurementType.equals(MeasurementType.METRIC))
//                return MessageUtil.getMessageString(MeasurementType.METRIC + "_" + text, getUser().getUser().getLocale());
        }
        return null;
    }
}
