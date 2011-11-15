package com.inthinc.pro.automation.device_emulation;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.models.GeoPoint;

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
        logger.debug("Change in Miles: " + deltaX);
        return deltaX;
    }
    
    public static Integer get_heading( GeoPoint start, GeoPoint stop  ){
        double dlat = stop.getLat() - start.getLat();
        double dlng = stop.getLng() - start.getLng();
        double y = Math.sin( dlat ) * Math.cos( stop.getLat() );
        double x = Math.cos( start.getLat() ) * Math.sin( stop.getLat() ) - Math.sin( start.getLat() ) * Math.cos( dlng );
        Integer tc1 = Math.abs( 360 - (int)( Math.atan2(y, x) % ( 2 * 180 ) ));
        logger.debug("Direction of travel: "+tc1);
        return tc1;
    }
}
