package com.inthinc.pro.convert;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class PerMillionsMilesToPerMillionsKmEstimateConverter extends BaseConverter {
	
	private static final DecimalFormat onePlace = new DecimalFormat("#0.0");
	static final String BELOW_POINT_ZERO_ONE = "<0.01";
	static final String ABOVE_FIFTY = ">50";

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
			Number milesOrKM = Number.class.cast(value); 
	        if (getMeasurementType().equals(MeasurementType.METRIC))
	        {
	        	milesOrKM = MeasurementConversionUtil.fromPerMillionMilesToPerMillionKm(Number.class.cast(value).doubleValue());
	        }
	        if (milesOrKM.doubleValue() < 0.01d)
	        {
	        	return BELOW_POINT_ZERO_ONE;
	        }
	        else if (milesOrKM.doubleValue() > 50.0)
	        {
	        	return ABOVE_FIFTY;
	        }
	        
	        return onePlace.format(milesOrKM.doubleValue());
        }
        return value.toString();
	}

}
