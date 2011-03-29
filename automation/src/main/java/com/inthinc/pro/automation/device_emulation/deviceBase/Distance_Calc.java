package com.inthinc.QA.deviceBase;

import org.apache.log4j.Logger;

import com.inthinc.QA.util.QALogger;

public class Distance_Calc {
	
	private final static Logger logger = Logger.getLogger(QALogger.class);
    
    private final double earth_radius = 3960;
    private final double deg2rad = Math.PI / 180;
    private final double rad2deg = 180 / Math.PI;
    private final double nauticalMilePerLat = 60.00721;
    private final double nauticalMilePerLongitude = 60.10793;
//    private final double milesPerNauticalMile = 1.5077945;
    
    public Double change_in_latitude( Double miles ){
        double delta_lat = ( miles / earth_radius ) * rad2deg;
        logger.debug("Change in Lat: "+delta_lat);
        return delta_lat;
    }
    
    public Double change_in_longitude( Double latitude, Double miles ){
        
        double r = earth_radius * Math.cos(latitude * deg2rad );
        double delta_lng = ( miles / r ) * rad2deg;
        logger.debug("Change in Lng: "+delta_lng);
        return delta_lng;
    }
    
    public Double calc_distance( Double lat1, Double lng1, Double lat2, Double lng2 ){
        
        double y = ( lat2 - lat1 ) * nauticalMilePerLat;
        double x = ( Math.cos( lat1 * deg2rad) + Math.cos( lat2* deg2rad ) ) * ( lng2 - lng1 )* (nauticalMilePerLongitude / 2 );
        double distance = Math.sqrt( Math.pow(y, 2 ) + Math.pow(x, 2)  );
        double nautToFeet = ( distance * 6076 );
        double deltaX = nautToFeet / 5280;
        logger.debug("Change in Miles: " + deltaX);
        return deltaX;
    }
    
    public Integer get_heading( Double lat1, Double lng1, Double lat2, Double lng2  ){
        
        double dlat = lat2 - lat1;
        double dlng = lng2 - lng1;
        double y = Math.sin( dlat ) * Math.cos( lat2 );
        double x = Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( dlng );
        Integer tc1 = Math.abs( 360 - (int)( Math.atan2(y, x) % ( 2 * 180 ) ));
        logger.debug("Direction of travel: "+tc1);
        return tc1;
    }

}
