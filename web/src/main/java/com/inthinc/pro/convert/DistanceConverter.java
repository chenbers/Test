package com.inthinc.pro.convert;

import java.text.NumberFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class DistanceConverter extends BaseConverter
{

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
        
        Double miles = 0.0D;
        
        if( value instanceof Double)
        {
            miles = (Double)value;
        }
        else if( value instanceof Integer)
        {
            miles = (Integer)value / 100D;
        }
        else if (value instanceof Long){
        	
            miles = (Long)value / 100D;
        }
        NumberFormat format = NumberFormat.getInstance(getLocale());
        format.setMaximumFractionDigits(1);

        if (getMeasurementType().equals(MeasurementType.METRIC))
        {
            return format.format(MeasurementConversionUtil.fromMilesToKilometers(miles));
            
        }
        else
        {
            return format.format(miles);
        }
        
    }

}
