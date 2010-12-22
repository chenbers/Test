package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.MessageUtil;

public class ScoreConverter extends BaseConverter
{
    private static final Logger logger = Logger.getLogger(ScoreConverter.class);
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if ((value == null) || value.toString().isEmpty() || !Integer.class.isInstance(value) || Integer.class.cast(value).intValue() < 0) {
            return MessageUtil.getMessageString("NotApplicable",getLocale());
        }
        
        	
        NumberFormat format = NumberFormat.getNumberInstance(getLocale());
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
   	
        return format.format(Integer.class.cast(value).doubleValue()/10.0d);

    }

}
