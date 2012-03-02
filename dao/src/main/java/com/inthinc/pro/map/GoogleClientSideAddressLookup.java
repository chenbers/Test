package com.inthinc.pro.map;

import java.util.List;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Zone;

public class GoogleClientSideAddressLookup extends AddressLookup {
    private String googleMapGeoUrl;

	public GoogleClientSideAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.LINK);
	}
	
  @Override
  public String getAddress(LatLng latLng) throws NoAddressFoundException{
 
  	if (!isValidLatLngRange(latLng)){
    	throw new NoAddressFoundException(latLng.getLat(),latLng.getLng(), NoAddressFoundException.reasons.INVALID_LATLNG);
	}

            StringBuilder request = new StringBuilder(googleMapGeoUrl)
              .append(String.format("%f", latLng.getLat()))
              .append(",")
              .append(String.format("%f", latLng.getLng()))
              .append("&output=csv");
          
          return request.toString();
  }

    @Override
    public String getAddressOrLatLng(LatLng latLng){
        try {
			return getAddress(latLng);
		} catch (NoAddressFoundException e) {
			// TODO Auto-generated catch block
		}
        return null;
    }

    @Override
    public String getAddressOrZoneOrLatLng(LatLng latLng, List<Zone> zones){
        try {
			return getAddress(latLng);
		} catch (NoAddressFoundException e) {
			// TODO Auto-generated catch block
			
		}
        return null;
    }

    public String getGoogleMapGeoUrl() {
        return googleMapGeoUrl;
    }

    public void setGoogleMapGeoUrl(String googleMapGeoUrl) {
        this.googleMapGeoUrl = googleMapGeoUrl;
    }

}
