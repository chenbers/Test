package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public class GoogleClientSideAddressLookup extends AddressLookup {


//	GoogleMapKeyFinder googleMapKeyFinder;
    private String googleMapGeoUrl;

	public GoogleClientSideAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.LINK);
	}
	
	@Override
	public String getAddress(LatLng latLng, boolean returnLatLng)
			throws NoAddressFoundException {
		
		if (returnLatLng){
			
			return latLng.getLat() + ", " + latLng.getLng();
		}
		else {
//			StringBuilder request = new StringBuilder("http://maps.google.com/maps/geo?q=");
//			request.append(latLng.getLat());
//			request.append(",");
//			request.append(latLng.getLng());
//			request.append("&output=csv&sensor=true&key=");
//			request.append(getGoogleMapKeyFinder().getKey());
			
		     StringBuilder request = new StringBuilder(googleMapGeoUrl)
	            .append(latLng.getLat())
	            .append(",")
	            .append(latLng.getLng())
	            .append("&output=csv");
			
			return request.toString();
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

    public String getGoogleMapGeoUrl() {
        return googleMapGeoUrl;
    }

    public void setGoogleMapGeoUrl(String googleMapGeoUrl) {
        this.googleMapGeoUrl = googleMapGeoUrl;
    }

//	public GoogleMapKeyFinder getGoogleMapKeyFinder() {
//		return googleMapKeyFinder;
//	}
//
//	public void setGoogleMapKeyFinder(GoogleMapKeyFinder googleMapKeyFinder) {
//		this.googleMapKeyFinder = googleMapKeyFinder;
//	}

}
