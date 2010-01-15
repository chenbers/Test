package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleAjaxAddressLookup extends AddressLookup {

	public GoogleAjaxAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.LATLNG);
	}

	private LatLng latLng;
	
	@Override
	public String getAddress(LatLng latLng, boolean returnLatLng)
			throws NoAddressFoundException {

		this.latLng = latLng;
		return null;
	}

	@Override
	public String getAddress(double lat, double lng)
			throws NoAddressFoundException {

		this.latLng = new LatLng(lat,lng);
		return null;
	}

	@Override
	public String getAddress(LatLng latLng) throws NoAddressFoundException {

		this.latLng = latLng;
		return null;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public double getLat(){
		
		return latLng.getLat();
	}
	
	public double getLng(){
		
		return latLng.getLng();
	}
}
