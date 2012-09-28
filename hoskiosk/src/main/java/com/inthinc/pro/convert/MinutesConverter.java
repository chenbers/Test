package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class MinutesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "00:00";
        
        
        Integer minutes = 0;
        try {
            minutes = Integer.valueOf(value.toString());
        }
        catch (NumberFormatException ex) {
            return "00:00";
        }
        Integer hours = minutes / 60;
        minutes = minutes % 60;

        return  (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes;
    }
}
