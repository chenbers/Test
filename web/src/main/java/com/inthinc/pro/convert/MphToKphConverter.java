package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class MphToKphConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        if(value != null){
            if(getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
            {
                Integer speed = Integer.valueOf(value);
                return MeasurementConversionUtil.fromKPHtoMPH(speed).intValue(); 
            }
            else
            {
                return Integer.valueOf(value);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (Long.class.isInstance(value))
        {
            if (getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromMPHtoKPH(Long.class.cast(value).intValue()).toString();
        }
        
        if (Integer.class.isInstance(value))
        {
            if (getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromMPHtoKPH(Integer.class.cast(value)).toString();
        }
        
     
        if(value != null)
            return value.toString();
        else
            return null;
    }    
}
