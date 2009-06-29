package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
public class MilesToKilometersConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
        {
            if (Long.class.isInstance(value))
            {
                    return MeasurementConversionUtil.fromMilesToKilometers(Long.class.cast(value).doubleValue()).toString();
            }
            if(Double.class.isInstance(value))
            {
                    return MeasurementConversionUtil.fromMilesToKilometers(((Double)value)).toString();
            }
        }
        
        return value.toString();
    }
}
