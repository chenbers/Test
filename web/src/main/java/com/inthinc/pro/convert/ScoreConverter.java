package com.inthinc.pro.convert;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;

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
        Integer score = (Integer)value;
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);

        if(score == null || score < 0)
            return "N/A";
        else
            return format.format((double)((double)score/(double)10.0));

    }

}
