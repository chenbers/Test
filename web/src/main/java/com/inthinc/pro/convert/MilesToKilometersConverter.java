package com.inthinc.pro.convert;

import java.text.NumberFormat;
import java.util.Formatter.BigDecimalLayoutForm;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class MilesToKilometersConverter extends BaseConverter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        throw new NotImplementedException();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {

        if (Number.class.isInstance(value)) {
            NumberFormat formatter = NumberFormat.getInstance(getLocale());
            formatter.setMaximumFractionDigits(1);
            
            double miles = Number.class.cast(value).doubleValue();
            
            if (getMeasurementType().equals(MeasurementType.METRIC)) {
                return formatter.format(MeasurementConversionUtil.fromMilesToKilometers(miles));
            } 
            return formatter.format(miles);
        }

        return value.toString();
    }
}
