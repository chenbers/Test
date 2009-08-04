package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class FuelEfficiencyTextConverter extends BaseConverter {
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
            String text = String.class.cast(value);
            
            FuelEfficiencyType fuelEfficiencyType = getFuelEfficiencyType();
            
            if (fuelEfficiencyType == null) {
                
                MeasurementType measurementType = getMeasurementType();
                if (measurementType==null){
                    measurementType = MeasurementType.ENGLISH;
                }

                fuelEfficiencyType = measurementType.equals( MeasurementType.ENGLISH)?FuelEfficiencyType.MPG_US:FuelEfficiencyType.KMPL;
            }
            
            return MessageUtil.getMessageString(fuelEfficiencyType +"_"+text, getLocale());
            
        }
        return null;
    }
}
