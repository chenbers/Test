package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class MphToKphSpeedLimitConverter extends BaseConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        if(value != null){
            if(getMeasurementType().equals(MeasurementType.METRIC))
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
        if (Number.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC)){
                
                Integer nearestFive = MeasurementConversionUtil.fromMPHtoKPHSpeedLimit(Number.class.cast(value));
//                Double addSome= new Double(MeasurementConversionUtil.fromMPHtoKPH(Number.class.cast(value)).doubleValue())+2.5d;
//                   int nearestFive = addSome.intValue()/5 * 5;
                return NumberFormat.getInstance(getLocale()).format(nearestFive);
            }
        }
      
        if(value != null)
            return NumberFormat.getInstance(getLocale()).format(value);
        else
            return null;
    }    

}
