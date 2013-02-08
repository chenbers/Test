package com.inthinc.pro.map;

import java.util.List;
import java.util.Locale;

import com.google.code.geocoder.Geocoder;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Zone;

public abstract class  AddressLookup {
	public enum AddressFormat{ADDRESS(1), LINK(2), LATLNG(3);
	
		private int code;
		
		AddressFormat(int code){
			
			this.code = code;
		}
		public int getCode(){
			
			return code;
		}
	}
	
	private AddressFormat addressFormat;
	private Locale locale;
	
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    protected void setAddressFormat(AddressFormat addressFormat) {
        this.addressFormat = addressFormat;
    }
    public AddressFormat getAddressFormat() {
        return addressFormat;
    }
    public abstract String getAddress(LatLng latLng) throws NoAddressFoundException;

    public String getAddress(double lat, double lng)
            throws NoAddressFoundException {
        
        return getAddress(new LatLng(lat,lng));
    }
	
    public String getAddressOrLatLng(LatLng latLng){
        try{
            return getAddress(latLng);
        }
        catch(NoAddressFoundException nafe){
            return getLatLngString(latLng);
        }
    }

    public String getAddressOrZoneOrLatLng(LatLng latLng, List<Zone> zones){
        try{
            String returnAddress = getAddress(latLng);
            if (returnAddress == null)
                throw new NoAddressFoundException(null, null);
            return returnAddress;
        }
        catch(NoAddressFoundException nafe){
            String zone = findZoneName(zones,latLng);
            if(zone != null){
                return zone;
            }
            return getLatLngString(latLng);
        }
    }
    protected boolean isValidLatLngRange(LatLng latLng){
        return (Math.abs(latLng.getLat()) > 0.0001) || (Math.abs(latLng.getLng()) > 0.0001);
    }
    private String getLatLngString(LatLng latLng){
        return String.format("%f", latLng.getLat()) + ", " + String.format("%f", latLng.getLng());
    }
    
    private String findZoneName(List<Zone> zoneList,LatLng latLng) {
        for ( Zone z: zoneList ) {
            if (z.containsLatLng(latLng) ) {
                return z.getName();
            }
        }
        return null;
    }
    protected String getLanguage() {
        String language = "en";
         if (getLocale() != null && getLocale().getLanguage() != null)
             language = getLocale().getLanguage();
         return language;
     }

    protected String getClientID() {
        return "gme-inthinc";
    }
    protected String getClientKey() {
        return "gme-inthinc";
    }
    protected Geocoder getGeocoder() throws NoAddressFoundException {
//        Geocoder geocoder;
//        try {
//            geocoder = new Geocoder(getClientID(), getClientKey());
//        } catch (InvalidKeyException e) {
//            throw new NoAddressFoundException(null, null, NoAddressFoundException.reasons.NO_MAP_KEY);
//        }
//        
//        return geocoder;
        return new Geocoder();
    }

}
