package com.inthinc.pro.map;

import com.inthinc.pro.model.LatLng;

public class GoogleAjaxAddressLookup extends AddressLookup {


	public GoogleAjaxAddressLookup() {
		super();
		setAddressFormat(AddressLookup.AddressFormat.LATLNG);
	}

	@Override
	public String getAddress(LatLng latLng){

		return null;
	}
}
