package com.inthinc.pro.map;

import java.util.List;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Zone;

public class LatLngFormatterBean extends AddressLookup {
    
    public LatLngFormatterBean() {
        super();
        setAddressFormat(AddressLookup.AddressFormat.ADDRESS);

    }

    @Override
    public String getAddress(LatLng latLng){
        return getFormattedLatLng(latLng);
    }

    @Override
    public String getAddressOrLatLng(LatLng latLng){
        return getFormattedLatLng(latLng);
    }

    @Override
    public String getAddressOrZoneOrLatLng(LatLng latLng, List<Zone> zones){
        return getFormattedLatLng(latLng);
    }
    private String getFormattedLatLng(LatLng latLng){
        return "Lat: " + latLng.getLat() + " Lng: " + latLng.getLng();
    }
}
