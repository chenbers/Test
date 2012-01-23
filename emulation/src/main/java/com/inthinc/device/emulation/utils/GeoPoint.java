package com.inthinc.device.emulation.utils;

import java.util.EnumSet;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.utils.MasterTest;

public class GeoPoint {
	
	public static class Distance_Calc {
		
	    
	    private static final double earth_radius = 3960;
	    private static final double deg2rad = Math.PI / 180;
	    private static final double rad2deg = 180 / Math.PI;
	    private static final double nauticalMilePerLat = 60.00721;
	    private static final double nauticalMilePerLongitude = 60.10793;
//	    private final double milesPerNauticalMile = 1.5077945;
	    
	    public static Double change_in_latitude( Double miles ){
	        double delta_lat = ( miles / earth_radius ) * rad2deg;
	        MasterTest.print("Change in Lat: "+delta_lat, Level.DEBUG);
	        return delta_lat;
	    }
	    
	    public static Double change_in_longitude( Double latitude, Double miles ){
	        
	        double r = earth_radius * Math.cos(latitude * deg2rad );
	        double delta_lng = ( miles / r ) * rad2deg;
	        MasterTest.print("Change in Lng: "+delta_lng, Level.DEBUG);
	        return delta_lng;
	    }
	    
	    public static Double calc_distance(GeoPoint start, GeoPoint stop){
	        double y = ( stop.getLat() - start.getLat() ) * nauticalMilePerLat;
	        double x = ( Math.cos( start.getLat() * deg2rad) + Math.cos( stop.getLat() * deg2rad ) ) * ( stop.getLng() - start.getLng() )* (nauticalMilePerLongitude / 2 );
	        double distance = Math.sqrt( Math.pow(y, 2 ) + Math.pow(x, 2)  );
	        double nautToFeet = ( distance * 6076 );
	        double deltaX = nautToFeet / 5280;
	        MasterTest.print("Change in Miles: " + deltaX, Level.DEBUG);
	        return deltaX;
	    }
	    
	    public static Integer get_heading( GeoPoint start, GeoPoint stop  ){
	        MasterTest.print(start, Level.DEBUG);
	        MasterTest.print(stop, Level.DEBUG);

	        double lat1 = start.getLat() * deg2rad;
	        double lat2 = stop.getLat() * deg2rad;
	        double lng1 = start.getLng() * deg2rad;
	        double lng2 = stop.getLng() * deg2rad;
	        
	        
	        double tc2 = Math.atan2(Math.sin(lng2-lng1)*Math.cos(lat2),
	                Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(lng2-lng1)) % 2 * Math.PI;
	        MasterTest.print("Direction of travel in radians: " + tc2, Level.DEBUG);
	        
	        Integer tc1 = Math.abs(((Double)(tc2 * rad2deg)).intValue());
	        while (tc1 > 360 ){
	            tc1 -= 360;
	        }
	        MasterTest.print("Direction of travel: " + tc1, Level.DEBUG);

	        return tc1;
	    }
	}

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
    
    public GeoPoint(String location){
    	GoogleTrips finder = new GoogleTrips();
    	GeoPoint temp = finder.getLocation(location);
    	lat = temp.getLat();
    	lng = temp.getLng();
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
    
	

public enum Heading {
    NORTH(0),
    NORTH_EAST(45),
    EAST(90),
    SOUTH_EAST(135),
    SOUTH(180),
    SOUTH_WEST(225),
    WEST(270),
    NORTH_WEST(315),
    ;
    
    private Integer min, max;
    private final Integer heading;
    
    private Heading(int heading){
        this.heading = heading/45;
        min = heading - 22;
        max = heading + 23;
        if (min < 0){
            min = 0;
        }
    }
    
    public Integer getHeading(){
        return heading;
    }

    public static Heading getHeading(GeoPoint start, GeoPoint stop){
        Integer direction = Distance_Calc.get_heading(start, stop);
        for (Heading heading : EnumSet.allOf(Heading.class)){
            if (direction >= heading.min && direction < heading.max){
                return heading;
            }
        }
        return Heading.NORTH;
    }
    
    @Override
    public String toString(){
        return name() + "(" + heading + ")";
    }
}

}
