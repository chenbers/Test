package com.inthinc.pro.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MilesToKilometersConverterInput extends BaseConverter  {
    
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
                    return MeasurementConversionUtil.fromKilometersToMiles(dist).intValue();                    
                }
                else
                {
                    return Integer.valueOf(value);
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

        if (Integer.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return MeasurementConversionUtil.fromMilesToKilometers(((Integer) value).longValue()).toString();
            else
                return value.toString();
        }
        return null;
    }
}
