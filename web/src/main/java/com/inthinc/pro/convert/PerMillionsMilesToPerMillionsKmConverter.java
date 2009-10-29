package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class PerMillionsMilesToPerMillionsKmConverter extends BaseConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		
        throw new NotImplementedException();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {

        if(Number.class.isInstance(value))
        {
	        if (getMeasurementType().equals(MeasurementType.METRIC))
	        {
	              return NumberFormat.getInstance(getLocale()).format(MeasurementConversionUtil.fromPerMillionMilesToPerMillionKm(Number.class.cast(value).doubleValue()));
	        }
	        
	        return NumberFormat.getInstance(getLocale()).format(value);
		}
        return value.toString();
	}

}
