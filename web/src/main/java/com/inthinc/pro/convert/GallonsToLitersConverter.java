package com.inthinc.pro.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class GallonsToLitersConverter extends BaseConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        try
        {
            if (value != null && !value.equals(""))
            {
                Float volume = Float.valueOf(value);
            	if(volume <= 0.0f) {
            		setErrorMessage(context,component);
            		return null;
            	}
                if (getMeasurementType().equals(MeasurementType.METRIC))
                {
                    volume++;
                    return MeasurementConversionUtil.fromLitersToGallons(volume).floatValue();                    
                }
                else
                {
                    return Float.valueOf(value);
                }
            }
        }
        catch (NumberFormatException e)
        {
        	setErrorMessage(context,component);
        }

        return null;
    }
    private void setErrorMessage(FacesContext context, UIComponent component){
        final String summary = MessageUtil.getMessageString("error_float_format");
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        context.addMessage(component.getClientId(context), message);
        if(component instanceof UIInput){
            ((UIInput)component).setValid(false);
        }
    	
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value==null) return null;
        if (Float.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC)){
                return MeasurementConversionUtil.fromGallonsToLiters((Float)value).toString();
            }
        }
        return value.toString();
    }

}
