package com.inthinc.pro.automation.device_emulation;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.utils.MasterTest;

public class Distance_Calc {
	
	private final static Logger logger = Logger.getLogger(Distance_Calc.class);
    
    private static final double earth_radius = 3960;
    private static final double deg2rad = Math.PI / 180;
    private static final double rad2deg = 180 / Math.PI;
    private static final double nauticalMilePerLat = 60.00721;
    private static final double nauticalMilePerLongitude = 60.10793;
//    private final double milesPerNauticalMile = 1.5077945;
    
    public static Double change_in_latitude( Double miles ){
        double delta_lat = ( miles / earth_radius ) * rad2deg;
        logger.debug("Change in Lat: "+delta_lat);
        return delta_lat;
    }
    
    public static Double change_in_longitude( Double latitude, Double miles ){
        
        double r = earth_radius * Math.cos(latitude * deg2rad );
        double delta_lng = ( miles / r ) * rad2deg;
        logger.debug("Change in Lng: "+delta_lng);
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
        
        
//        double dlat = lat2 - lat1;
//        double dlng = lng2 - lng1;
//        double y = Math.sin( dlat ) * Math.cos( lat2 );
//        double x = Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( dlng );
//        double arcTan = Math.atan2(y, x);
//        double modulus = arcTan % (2*Math.PI);
        
        double tc2 = Math.atan2(Math.sin(lng2-lng1)*Math.cos(lat2),
                Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(lng2-lng1)) % 2 * Math.PI;
        MasterTest.print("Direction of travel in radians: " + tc2, Level.DEBUG);
        
        Integer tc1 = Math.abs(((Double)(tc2 * rad2deg)).intValue());
        while (tc1 > 360 ){
            tc1 -= 360;
        }
        MasterTest.print("Direction of travel: " + tc1, Level.DEBUG);
//        if (tc1 != -99){
//            throw new NullPointerException();
//        }
        return tc1;
    }
}
