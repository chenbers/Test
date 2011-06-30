package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MilesToKilometersConverterLong extends BaseConverter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        try
        {
            if (value != null && !value.equals(""))
            {
                if (getMeasurementType().equals(MeasurementType.METRIC))
                {
                    Double dist = Double.valueOf(value);
                    dist++;
                    return MeasurementConversionUtil.fromKilometersToMiles(dist).longValue();                    
                }
                else
                {
                    return Long.valueOf(value);
                }
            }
        }
        catch (NumberFormatException e)
        {
            final String summary = MessageUtil.getMessageString("error_number_format");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            context.addMessage(component.getClientId(context), message);
            if(component instanceof UIInput){
                ((UIInput)component).setValid(false);
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {

        if (Long.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromMilesToKilometers(((Long) value).longValue()).toString();
            else
                return value.toString();
        }
        return null;
    }
}
