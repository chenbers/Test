package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
public class MpgToKplConverter extends BaseConverter
{
//    private static final DecimalFormat decimalFormat = new DecimalFormat("######.##");
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
    	if (value==null) return null;
        if (Number.class.isInstance(value))
        {
        	NumberFormat nf = NumberFormat.getNumberInstance(getLocale());
        	nf.setMaximumFractionDigits(2);
            return nf.format(MeasurementConversionUtil.convertMpgToFuelEfficiencyType((Number.class.cast(value)).doubleValue(),
                                                                                                getMeasurementType(), 
                                                                                                getFuelEfficiencyType()));
        }
        return value.toString();
    }
}
