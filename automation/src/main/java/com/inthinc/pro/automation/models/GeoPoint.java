package com.inthinc.pro.automation.models;

import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.Distance_Calc;

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
    
    public int encodeLat(){
        double latitude = ( 90.0 - lat ) / 180.0;
        int val;
        val  = (int)( latitude  * 0x00FFFFFF );
        return val;
    }
    
    public long encodeLatBC(){
        Double latitude = 90.0 - lat;
        latitude = latitude / 180.0;
        int val = 0;
        val  = (int)( latitude  * 4294967295L );
        return val;
    }
    
    public int encodeLng(){
        double longitude = ( lng < 0.0 ? lng + 360.0 : lng / 360.0 );
        int val;
        val = (int)( longitude * 0x00FFFFFF );
        return val;
    }
    
    public long encodeLngBC(){
        
        double longitude = ( lng / 360.0 );
        long val;
        val = (long)( longitude * 4294967295L );
        return val;
    }
    
    public GeoPoint copy(){
        return new GeoPoint(lat, lng);
    }
    
    public Heading getHeading(GeoPoint next){
        return Heading.getHeading(this, next);
    }
    

}
