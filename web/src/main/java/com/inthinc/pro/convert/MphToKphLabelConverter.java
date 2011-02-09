package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.dao.util.MathUtil;
/**
 * 
 * @author mstrong
 * 
 * This does the same thing as MphToKphTextConverter but uses rounding. 
 * As requirement from Petty-5, we are rounding the converted KPH value to the nearest
 * 5 (i.g. 43 will become 45 and 42 will become 40
 *
 */
public class MphToKphLabelConverter extends BaseConverter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
		if (Long.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return MathUtil.roundToNearestFive(MeasurementConversionUtil.fromMPHtoKPH(Long.class.cast(value).longValue())).toString();
        }
        
        if (Integer.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC))
            	return MathUtil.roundToNearestFive(MeasurementConversionUtil.fromMPHtoKPH(Integer.class.cast(value).intValue())).toString();
        }
        
     
        if(value != null)
            return value.toString();
        else
            return null;
    }
}
