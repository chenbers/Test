package com.inthinc.pro.model;

public class NoAddressFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3325595192727392049L;

	Double lat;
	Double lng;
	
	
	public NoAddressFoundException(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "No address found at lat "+lat+", lng "+lng;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "No address found at lat "+lat+", lng "+lng;
	}

}
