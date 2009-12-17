package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.util.MessageUtil;
public class LatLngAddressConverter implements Converter
{
    private AddressLookup addressLookup;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        // Implement if an Address component is created
        throw new NotImplementedException("Implement LatLngAddressConverter.getAsObject() if an Address component is created");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (LatLng.class.isInstance(value))
        {
            LatLng latlng = LatLng.class.cast(value);
            if ((latlng.getLat() < -0.0001 || latlng.getLat() > 0.0001) && (latlng.getLng() < -0.0001 || latlng.getLng() > 0.0001)){
            	try{
            		return addressLookup.getAddress(latlng);
            	}
            	catch (NoAddressFoundException nafe){
        			return MessageUtil.formatMessageString("noAddressFound", nafe.getLat(),nafe.getLng());
            	}
            }
        }
        return MessageUtil.getMessageString("noAddressFound");
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }
    
    public boolean isLink(){
    	
    	return addressLookup.isLink();
    }
}
