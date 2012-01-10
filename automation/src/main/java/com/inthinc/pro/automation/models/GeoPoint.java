package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.Heading;
import com.inthinc.pro.automation.device_emulation.Distance_Calc;
import com.inthinc.pro.automation.utils.MasterTest;

public class GeoPoint {

    private double lat;
    private double lng;
    
    private final static long notebc = 4294967295L;
    private final static int note = 0x00FFFFFF;
    
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
    	Double delX = deltaX(nextPoint);
    	double delT = deltaT / (60.0 * 60.0);
    	MasterTest.print("delX:%f, delT:%f", Level.DEBUG, delX, delT);
        return ((Double)(delX / delT)).intValue();
    }
    
    @Override
    public String toString(){
        return ("(" + lat + "," + lng + ")");
    }
    
    private double partiallyEncodedLat(){
    	return (90.0 - lat ) / 180.0;
    }
    
    private double partiallyEncodedLng(){
    	return lng / 360.0;
    }
    
    public int encodeLat(){
        return (int)( partiallyEncodedLat() * note );
    }
    
    public long encodeLatBC(){
        return (int)( partiallyEncodedLat() * notebc );
    }
    
    public int encodeLng(){
        return (int)( partiallyEncodedLng() * note );
    }
    
    public long encodeLngBC(){
        return (long)( partiallyEncodedLng() * notebc );
    }
    
    public GeoPoint copy(){
        return new GeoPoint(lat, lng);
    }
    
    public Heading getHeading(GeoPoint next){
        return Heading.getHeading(this, next);
    }
    
    @Override
    public boolean equals(Object obj){
    	if (obj instanceof GeoPoint){
    		GeoPoint other = (GeoPoint) obj;
    		return lat == other.lat && lng == other.lng;
    	} else {
    		return false;
    	}
    }

	public void changeLocation(GeoPoint location) {
		lat = location.lat;
		lng = location.lng;
	}
    

}
