package com.inthinc.pro.model;

import com.google.code.geocoder.model.GeocoderStatus;

public class NoAddressFoundException extends Exception {

	public enum reasons {NO_ADDRESS_FOUND, NO_MAP_KEY, COULD_NOT_REACH_SERVICE, CLIENTSIDE, CLIENTSIDE_UNRECOGNISED_RESULTTYPE, INVALID_LATLNG, NO_LAT_LNG_FOUND}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3325595192727392049L;

	Double lat;
	Double lng;
	String address;
	reasons reason;
	GeocoderStatus geocoderStatus;
	
	public NoAddressFoundException(Double lat, Double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.reason = reasons.NO_ADDRESS_FOUND;
	}
	public NoAddressFoundException(Double lat, Double lng, reasons reason) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.reason = reason;
	}
    public NoAddressFoundException(Double lat, Double lng, GeocoderStatus geocoderStatus) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.geocoderStatus = geocoderStatus;
    }

    public NoAddressFoundException(Double lat, Double lng, reasons reason, String address) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.reason = reason;
    }

	public GeocoderStatus getGeocoderStatus() {
        return geocoderStatus;
    }
    public void setGeocoderStatus(GeocoderStatus geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }
    @Override
	public String getMessage() {
        
        return toString();
	}

	@Override
	public String toString() {
        if (geocoderStatus != null)
            return geocoderStatus+": "+lat+", "+lng;
		return reason+": "+lat+", "+lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public reasons getReason() {
		return reason;
	}

	public void setReason(reasons reason) {
		this.reason = reason;
	}

}
