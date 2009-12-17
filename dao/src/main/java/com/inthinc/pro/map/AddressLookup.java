package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public interface AddressLookup {
	
	   public String getAddress(LatLng latLng,boolean returnLatLng) throws NoAddressFoundException;
	   public String getAddress(double lat, double lng) throws NoAddressFoundException;
	   public String getAddress(LatLng latLng) throws NoAddressFoundException;
	   
	   public boolean isLink();
}
