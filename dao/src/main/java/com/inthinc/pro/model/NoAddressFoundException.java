package com.inthinc.pro.model;

public class NoAddressFoundException extends Exception {

	public enum reasons {NO_ADDRESS_FOUND, NO_MAP_KEY, COULD_NOT_REACH_SERVICE, CLIENTSIDE, CLIENTSIDE_UNRECOGNISED_RESULTTYPE, INVALID_LATLNG}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3325595192727392049L;

	Double lat;
	Double lng;
	reasons reason;
	
	public NoAddressFoundException(Double lat, Double lng, reasons reason) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.reason = reason;
	}

	@Override
	public String getMessage() {
		return reason+": "+lat+", "+lng;
	}

	@Override
	public String toString() {
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
