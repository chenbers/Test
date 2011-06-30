package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class PerMillionsMilesToPerMillionsKmEstimateConverter extends BaseConverter {
	
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
        	NumberFormat nf = NumberFormat.getInstance(getLocale());
			Number milesOrKM = Number.class.cast(value); 
	        if (getMeasurementType().equals(MeasurementType.METRIC))
	        {
	        	milesOrKM = MeasurementConversionUtil.fromPerMillionMilesToPerMillionKm(Number.class.cast(value).doubleValue());
	        }
	        if (milesOrKM.doubleValue() < 0.01d)
	        {
	        	return "<"+nf.format(0.01);
	        }
	        if (milesOrKM.doubleValue() > 50.0)
	        {
	        	return ">"+nf.format(50.0);
	        }
	        
	        return nf.format(milesOrKM.doubleValue());
        }
        return value.toString();
	}

}
