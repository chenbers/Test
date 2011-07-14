package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class ForceUpperCaseConverter extends BaseConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        return value.toUpperCase(getLocale());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if(value instanceof String){
            return ((String)value).toUpperCase(getLocale());
        }
        return "";
    }

}
