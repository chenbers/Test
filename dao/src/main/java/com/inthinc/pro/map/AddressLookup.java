package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

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
		
	public abstract String getAddress(LatLng latLng,boolean returnLatLng) throws NoAddressFoundException;
	public abstract String getAddress(double lat, double lng) throws NoAddressFoundException;
	public abstract String getAddress(LatLng latLng) throws NoAddressFoundException;
	
	public AddressFormat getAddressFormat() {
		return addressFormat;
	}
	public void setAddressFormat(AddressFormat addressFormat) {
		this.addressFormat = addressFormat;
	}
		
}
