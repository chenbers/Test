package com.inthinc.pro.convert;

import java.text.NumberFormat;
import java.util.Formatter.BigDecimalLayoutForm;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class HundrethMilesToKilometersConverter extends BaseConverter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        try
        {
            if (value != null && !value.trim().isEmpty())
            {
                if (getMeasurementType().equals(MeasurementType.METRIC))
                {
                    Long dist = Long.valueOf(value)/100l;
                    return MeasurementConversionUtil.fromKilometersToMiles(dist).intValue();                    
                }
                else
                {
                    return Integer.valueOf(value);
                }
            }
            else {
            	
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
            formatter.setMaximumFractionDigits(1);
            
            double miles = Number.class.cast(value).doubleValue()/100d;
            
            if (getMeasurementType().equals(MeasurementType.METRIC)) {
                return formatter.format(MeasurementConversionUtil.fromMilesToKilometers(miles));
            } 
            return formatter.format(miles);
        }

        return value.toString();
    }
}
