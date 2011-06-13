package com.inthinc.pro.map;

import java.util.List;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Zone;

public class GoogleClientSideAddressLookup extends AddressLookup {
    private String googleMapGeoUrl;

	public GoogleClientSideAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.LINK);
	}
	
  @Override
  public String getAddress(LatLng latLng){
      
            StringBuilder request = new StringBuilder(googleMapGeoUrl)
              .append(latLng.getLat())
              .append(",")
              .append(latLng.getLng())
              .append("&output=csv");
          
          return request.toString();
  }

    @Override
    public String getAddressOrLatLng(LatLng latLng){
        return getAddress(latLng);
    }

    @Override
    public String getAddressOrZoneOrLatLng(LatLng latLng, List<Zone> zones){
        return getAddress(latLng);
    }

    public String getGoogleMapGeoUrl() {
        return googleMapGeoUrl;
    }

    public void setGoogleMapGeoUrl(String googleMapGeoUrl) {
        this.googleMapGeoUrl = googleMapGeoUrl;
    }

}
