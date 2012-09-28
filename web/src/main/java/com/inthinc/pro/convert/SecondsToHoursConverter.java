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

public class SecondsToHoursConverter extends BaseConverter {

    private static final double SECONDS_TO_HOURS = 3600.0d;

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        try
        {
            if (value != null && !value.trim().isEmpty())
            {
            		return Long.valueOf(value).doubleValue()/SECONDS_TO_HOURS;  
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

        return 0;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {

    	if (Number.class.isInstance(value)) {
  
            NumberFormat formatter = NumberFormat.getInstance(getLocale());
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            
            double hours = Number.class.cast(value).doubleValue()/SECONDS_TO_HOURS;
            return formatter.format(hours);
        }

        return value.toString();
    }
}

