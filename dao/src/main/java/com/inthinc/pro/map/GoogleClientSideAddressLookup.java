package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleClientSideAddressLookup implements AddressLookup {

	GoogleMapKeyFinder googleMapKeyFinder;

	@Override
	public String getAddress(LatLng latLng, boolean returnLatLng)
			throws NoAddressFoundException {
		
		if (returnLatLng){
			
			return latLng.getLat() + ", " + latLng.getLng();
		}
		else {
			StringBuilder request = new StringBuilder("http://maps.google.com/maps/geo?q=");
			request.append(latLng.getLat());
			request.append(",");
			request.append(latLng.getLng());
			request.append("&output=csv&sensor=true&key=");
			request.append(getGoogleMapKeyFinder().getKey());
			
			return request.toString();
//			throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.CLIENTSIDE);
		}
	}

	@Override
	public String getAddress(double lat, double lng)
			throws NoAddressFoundException {
		
		return getAddress(new LatLng(lat,lng),false);
	}

	@Override
	public String getAddress(LatLng latLng) throws NoAddressFoundException {
		
		return getAddress(latLng,false);
	}

	public GoogleMapKeyFinder getGoogleMapKeyFinder() {
		return googleMapKeyFinder;
	}

	public void setGoogleMapKeyFinder(GoogleMapKeyFinder googleMapKeyFinder) {
		this.googleMapKeyFinder = googleMapKeyFinder;
	}
	@Override
	public boolean isLink() {

		return true;
	}

}
