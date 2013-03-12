package com.inthinc.pro.convert;

import java.text.NumberFormat;
import java.text.ParseException;

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
            	NumberFormat nf = NumberFormat.getInstance(getLocale());
            	Float volume = nf.parse(value).floatValue();
            	if((Float)volume < 0) {
            		setErrorMessage(context,component);
            		return null;
            	}
                if (getMeasurementType().equals(MeasurementType.METRIC))
                {
//                	volume++;
                    return MeasurementConversionUtil.fromLitersToGallonsExact(volume).floatValue();                    
                }
                else
                {
                    return volume;
                }
            }
        }
        catch (ParseException e)
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
        Number volume = 0.0f;
        if (Number.class.isInstance(value))
        {
            if (getMeasurementType().equals(MeasurementType.METRIC)){
            	volume = MeasurementConversionUtil.fromGallonsToLitersExact((Number)value);
            }
            else {
            	volume = (Number)value;
            }
            return formatValue(volume);
        }
        return value.toString();
    }
    private String formatValue(Number value){
        NumberFormat format = NumberFormat.getNumberInstance(getLocale());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        return format.format(value);
    }
}
