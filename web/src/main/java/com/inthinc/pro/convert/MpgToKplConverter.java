package com.inthinc.pro.convert;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
public class MpgToKplConverter extends BaseConverter
{
    private static final DecimalFormat decimalFormat = new DecimalFormat("######.##");
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (Number.class.isInstance(value))
        {
            if (getUser().getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC))
                return decimalFormat.format(MeasurementConversionUtil.fromMPGtoKPL(Number.class.cast(value)).doubleValue());
        }
        return value.toString();
    }
}
