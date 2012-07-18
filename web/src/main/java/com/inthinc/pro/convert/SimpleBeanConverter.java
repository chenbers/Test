package com.inthinc.pro.convert;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ajax4jsf.util.base64.Base64;
import org.apache.commons.lang.SerializationUtils;

public class SimpleBeanConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent uiComponent, String serializedString) {
	    Object test = deSerializeBean(serializedString);
	    return deSerializeBean(serializedString);
	}
	
	@Override
	public String getAsString(FacesContext arg0, UIComponent uiComponent, Object object) {
		return serializeBean((Serializable)object);
	}
	
	private String serializeBean(Serializable object) {
    	try {
    		byte[] bytes = Base64.encodeBase64(SerializationUtils.serialize(object));
    		return new String(bytes, "utf-8");
    	} catch(UnsupportedEncodingException e) {
    		throw new RuntimeException("Error encoding object:"+object.toString(), e);
    	}
    }

    private Serializable deSerializeBean(String serializedString) {
    	try {
    		byte[] bytes = Base64.decodeBase64(serializedString.getBytes("utf-8"));
	    	return (Serializable)SerializationUtils.deserialize(bytes);
		} catch(UnsupportedEncodingException e) {
			throw new RuntimeException("Error decoding object:"+serializedString, e);
		}

    }

}
