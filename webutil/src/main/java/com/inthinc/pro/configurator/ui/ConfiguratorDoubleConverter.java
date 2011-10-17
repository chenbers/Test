package com.inthinc.pro.configurator.ui;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConfiguratorDoubleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		try{
			return ""+Double.parseDouble(value);
		}
		catch (NumberFormatException nfe){
			return value;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) return null;
		if (!(value instanceof String)) return value.toString();
		try{
			return ""+Double.parseDouble((String)value);
		}
		catch (NumberFormatException nfe){
			return (String)value;
		}
//		return ""+NumberUtil.convertStringToDouble((String)value);
	}

}
