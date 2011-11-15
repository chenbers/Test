package com.inthinc.pro.automation.models;

import com.inthinc.pro.automation.device_emulation.Distance_Calc;
import com.inthinc.pro.automation.device_emulation.NoteManager;

public class GeoPoint {

    private final double lat;
    private final double lng;
    
    public GeoPoint(final double lat, final double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    public GeoPoint(final int lat, final int lng) {
        this.lat = lat/1e6;
        this.lng = lng/1e6;
    }
    
    public Double getLat(){
        return lat;
    }
    
    public Double getLng(){
        return lng;
    }
    
    public Double deltaX(GeoPoint stop){
        return Distance_Calc.calc_distance(this, stop);
    }
    
    public int deltaT(Integer speedInMPH, GeoPoint nextLocation){
        Double delX = deltaX(nextLocation);
        Double v = speedInMPH.doubleValue() / 3600.0 ; //Miles/Seconds
        
        return ((Double)(delX/v)).intValue();
    }
    
    public int speed(Integer deltaT, GeoPoint nextPoint){
        return ((Double)(deltaX(nextPoint) / deltaT)).intValue();
    }
    
    @Override
    public String toString(){
        return ("(" + lat + "," + lng + ")");
    }
    
    public int getEncodedLat(){
        return NoteManager.encodeLat(lat);
    }
    
    public int getEncodedLng(){
        return NoteManager.encodeLng(lng);
    }
    
    public GeoPoint copy(){
        return new GeoPoint(lat, lng);
    }
    

}
