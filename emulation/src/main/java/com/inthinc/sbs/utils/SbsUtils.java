package com.inthinc.sbs.utils;




/**
 * @author jason litzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * **/

public class SbsUtils {
	public static final int LAT_LNG_SCALE_FACTOR = 1000000;
	public static final int SPEEDLIMIT_SCALE_FACTOR = 100;
	private static final double MILEPERLANE = 12./5280.0;
	
	private SbsUtils(){
		
	}
	public static double atan_66s(double ta)
	{
	    final double c1=1.6867629106;
	    final double c2=0.4378497304;
	    final double c3=1.6867633134;
	    double ta2 = ta * ta;
	    return (ta*(c1 + ta2*c2)/(c3 + ta2));
	}
	
	public static double atan66(double dx,double dy)
	{
		final double tantwelfthpi = 0.2679492;
	    final double tansixthpi = 0.5773503;
	    final double sixthpi = 0.5235988;
	    final double halfpi = 1.5707963;
	    double rads; // return from atan__s function
	    
	    if (dy==0) {
	        if (dx > 0) {
	            rads= halfpi;
	        } else {
	            rads=-halfpi;
	        }
	    } else {
	        double ta = dx/dy;
	        boolean complement= false; // true if arg was >1
	        boolean region= false; // true depending on region arg is in
	        boolean sign= false; // true if arg was < 0

	        if (ta <0 ){
	            ta=-ta;
	            sign=true; // arctan(-ta)=-arctan(ta)
	        }
	        if (ta > 1.0){
	            ta=1.0/ta; // keep arg between 0 and 1
	            complement=true;
	        }
	        if (ta > tantwelfthpi){
	            ta = (ta-tansixthpi)/(1+tansixthpi*ta); // reduce arg to under tan(pi/12)
	            region=true;
	        }
	        rads=atan_66s(ta); // run the approximation

	        if (region) {
	            rads+=sixthpi; // correct for region we're in
	        }
	        if (complement) {
	            rads=halfpi-rads; // correct for 1/ta if we did that
	        }
	        if (sign) {
	            rads=-rads; // correct for negative arg
	        }
	    }
	    return (rads);
	}	


	/**
	 * 
	 * @param latitude - current latitude
	 * @param longitude - current longitude
	 * @param heading - current heading in degrees * 10
	 * @param p1lat - segment point 1 latitude
	 * @param p1lon - segment point 1 longitude
	 * @param p2lat - segment point 2 latitude
	 * @param p2lon - segment point 2 longitude
	 * @param maxWindow - maximum window in feet.
	 * @param maxAngle - maximum difference between heading and
	 *        angle of segment.
	 * @return 9999 if distance is outside window, distance in feet otherwise.
	 */
	public static double getPointLineDistance(double latitude,double longitude, 
			int heading,double p1lat,double p1lon,double p2lat,double p2lon,
			int maxWindow,int maxAngle)
	{	
		double u;   // fraction along p1-p2 of normal projection
	    double lonProj;   // projection point longitude
	    double latProj;   //  projection point latitude
	    double dx21;  // delta x from p1 to p2
	    double dy21;  // delta y from p1 to p2
	    double dx31;  // delta x from p1 to p2
	    double dy31;  // delta y from p1 to p2
	    double d21; // square of distance from 1 to 2
	    double dist;    // distance from line to point
	    double weight = 1;
	    double LONGITUDE_MILES = GeoUtil.getLongToMile(latitude);
	    double LATITUDE_MILES = GeoUtil.getLatToMile(latitude);

	    //Copied as much as possible from sbslogic.c
	    dx21 = (p2lon - p1lon)*LONGITUDE_MILES;
	    dy21 = (p2lat  - p1lat)*LATITUDE_MILES;
	    d21 = dx21*dx21 + dy21*dy21;
	    if (d21==0.) 
	    {
	        // Coincident points at segment ends.  Calculate distance to p3.
	        u = 0.;
	    } 
	    else 
	    {
	        dx31 = (longitude-p1lon)*LONGITUDE_MILES;
	        dy31 = (latitude-p1lat)*LATITUDE_MILES;
	        u = (dx31*dx21 + dy31*dy21) / d21;
	        // If outside the segment, measure to an end point.
	        if (u < 0.) u = 0.;
	        if (u > 1.) u = 1.;
	    }
	    
	    
	    lonProj = p1lon + u*dx21/LONGITUDE_MILES;
	    latProj = p1lat  + u*dy21/LATITUDE_MILES;
	    dx31 = (longitude-lonProj)*LONGITUDE_MILES;
	    dy31 = (latitude-latProj)*LATITUDE_MILES;
	    
	    dist = Math.sqrt((dx31*dx31) + (dy31*dy31));
	    if(0 <= heading && heading <= 3600) {
	        // By weighting the distance more heavily away from the proper heading
	        // the algorithm will better avoid cross streets.
	    	double diffang = SbsUtils.lineHeadingAngle(p1lat, p1lon, p2lat, p2lon, heading);
	        weight = SbsUtils.weightHeading(diffang,maxAngle);  
	        if(weight >= 999.0)
	        {
	        	return weight;
	        }


	        // if we want to weight it, use at least a lane width or so
	        // Might be nice if we had the number of lanes
	        if (dist < MILEPERLANE && weight > 1.1) {
	            dist = MILEPERLANE;
	        }
	        dist *= weight;
	    }

	    /**
	     *The old code was:
	     * This excludes > 38 degrees with <= 2.0
	     * (dist*5280.0 <= maxWindow && weight <= 3)
	     * 
	     * I modified it so that the angle set as max angle is what is used.  We can
	     * lower the default to 38 degrees.
	     */
	    double ftDistance = dist*5280.0;
	    if(ftDistance <= maxWindow)
	    {
	    	return ftDistance;
	    }
	    else
	    {
	    	return 9999.0;
	    }

	}
	
	/**
	 * 
	 * @param diffang - the angle difference between a segment and the 
	 * 					current heading.
	 * @param maxAngle - the maximum angle difference allowed.
	 * @return a weighting factor relative to the diffang.  The large the difference, the larger the
	 * factor will be.  For diffangs that exceed maxAngle, 9999 will be returned.  0 returns 1.
	 */
	public static double weightHeading(double diffang,int maxAngle)
	{
	    double weight = 1.;
	    final double cFactor=3.0;
	    
	    
	    // diffang is now between 0 and 180
	    // get weighting for distance that is 1 at 0 or 180, 2 or 3 at 90
	    double ct = 1.-diffang/90.;

	    double mAngle = (double) maxAngle;
	    if(diffang > 90.0)
	    {
	    	if(180.0-diffang >= mAngle)
	    	{
	    		return 9999.0;
	    	}
	    }
	    else if(diffang >= mAngle)
	    {
	    	return 9999.0;
	    }

	    weight += cFactor*(1.-ct*ct);
	    // just apply the weighting to the distance calculated for each segment
	    // the rest will take care of itself
	    return weight;
	}
	
	/**
	 * Calculate the difference between the angle of a line segment
	 * and a heading.
	 * @param p1lat
	 * @param p1lon
	 * @param p2lat
	 * @param p2lon
	 * @param heading - current heading in degrees * 10
	 * @return
	 */
	public static double lineHeadingAngle(double p1lat,double p1lon,
			double p2lat,double p2lon,int heading)
	{
		double diffang = 0.;
		double LONGITUDE_MILES = GeoUtil.getLongToMile(p1lat);
	    double LATITUDE_MILES = GeoUtil.getLatToMile(p1lat);
	    double dx = (p2lon - p1lon)*LONGITUDE_MILES;
	    double dy = (p2lat - p1lat)*LATITUDE_MILES;

	    //Note:  I tried using the Java libs here, and they work for angles in the same quadrant,
	    //but, it appears our implementation rotates.  See the test cases for the one that will break
	    //with the java maths stuff.
	    //final double rad2deg = 57.29578;
	    //OLD CODE: double segang = rad2deg * SbsUtils.atan66(dx,dy);
	    //Java code attempted:  double segang = Math.toDegrees(Math.atan2(dy, dx));
	    double segang = Math.toDegrees(SbsUtils.atan66(dx,dy));
	    double course = ((double) heading) / 10.0;
	    if (course > 270.0) {
	        course -= 360.0;
	    }
	    if (course > 90.0) {
	        course -= 180.0;
	    }

	    diffang = course - segang;
	    if (diffang < 0.) {
	        diffang = -diffang;
	    }
	    
	    return diffang;
	}
	
	public static int createLongitudeIndex(double longitude)
	{
		int longitudeIndex = 0;
		
	    longitude += 180.0;
	    longitude *=  20.0;
	    longitudeIndex = (int) longitude;

	    // If it is close to the line, move West except
	    // in case where longitude = -180.0

	    if (longitude - longitudeIndex < 0.00000001) {
	        longitudeIndex -= 1;
	        if(longitudeIndex < 0)
	        {
	        	longitudeIndex = 7199;
	        }
	    }
	    
	    return longitudeIndex;
	}
	
	public static int createLatitudeIndex(double latitude)
	{
		int latitudeIndex = 0;
		
		latitude += 90.0;
	    latitude *= 20.0;
	    latitudeIndex = (int) latitude;
	    // If it is close to the line, move N

	    if (latitude - latitudeIndex > 0.99999999) {
	        latitudeIndex += 1;
	    }

	    return latitudeIndex;
	}
	
	
	/**
	 * Create a fileAsInt from a lat/long.
	 * Note that this has a boundary condition at -180.  Instead
	 * of 0, it produces 7199.  
	 * @param latitude - current latitude
	 * @param longitude - current longitude
	 * @return fileAsInt in the form of <lat><long>
	 */
	public static int createFileAsInt(double latitude,double longitude)
	{
		int lat_index = createLatitudeIndex(latitude);
		int long_index = createLongitudeIndex(longitude);
		return (lat_index * 10000) + long_index;
	}
	
	public static int createLatitudeIndex(int fileAsInt){
		return fileAsInt / 10000;
	}
	
	public static int createLongitudeIndex(int fileAsInt){
		return fileAsInt % 10000;
	}
	
	/**
	 * Create the filename.  If old is true, then the old directory structure
	 * is used, which is of the form <long_idx / 10>/<long_idx>/<lat_idx><long_idx>.MAP.
	 * Otherwise, the new format is used.
	 * @param fileAsInt
	 * @param old true if trying to create the old directory tree.
	 * @return  A string representing the directory path.
	 */
	public static String createMapFilename(int fileAsInt,boolean old){
		
		int lat_idx = createLatitudeIndex(fileAsInt);
		int long_idx = createLongitudeIndex(fileAsInt);
		
		if(old){
			return String.format("/%03d/%04d/%04d%04d.MAP",long_idx / 10, long_idx, lat_idx, long_idx);
		}else{
			return String.format("/%02d/%02d/%02d/%04d%04d.MAP",long_idx / 100, long_idx % 100, lat_idx/100,lat_idx,long_idx);
		}
		
	}
	
	
	/**
	 * Create a string representing the three tiers of the sbs directory structure.
	 * This value is /<long_idx / 100>/<long_idx % 100>/<lat_idx / 100>.  It does not 
	 * bounds check, if an invalid long or lat
	 * index is passed, it will create the string regardless.
	 * @param long_idx - the longitude index
	 * @return
	 */
	public static String buildThreeDirTiers(int lat_idx,int long_idx)
	{
		return String.format("/%02d/%02d/%02d",	long_idx / 100, long_idx % 100, lat_idx/100);
	}
	
	/**
	 * Calculate the largest distance, in units of map squares, from the
	 * current location to the proposed location.
	 * @param curLatIdx - current latitude index.
	 * @param curLongIdx - current longitude index.
	 * @param proposedLatIdx - proposed latitude index.
	 * @param proposedLongIdx - proposed longitude index.
	 * 
	 * @return largest distance in squares.
	 */
	public static int distanceInSquares(int curLatIdx,int curLongIdx,int proposedLatIdx,
			int proposedLongIdx){
		
		int latDist = Math.abs(curLatIdx - proposedLatIdx);
		int lonDist	= Math.abs(curLongIdx - proposedLongIdx);
		
		if(latDist >= lonDist){
			return latDist;
		}else{
			return lonDist;
		}
		
	}
	
	
	/**
	 * 
	 * @param curLatIdx
	 * @param curLongIdx
	 * @param proposedLatIdx
	 * @param proposedLongIdx
	 * @param squares
	 * @return - true if the map is within 'squares' of the current location.
	 */
	public static boolean mapIsWithinSquares(int curLatIdx,int curLongIdx,int proposedLatIdx,
			int proposedLongIdx,int squares){
		
		if(proposedLatIdx == curLatIdx
				&& proposedLongIdx == curLongIdx)
		{
			return true;
		}
		else if(distanceInSquares(curLatIdx, curLongIdx, proposedLatIdx, proposedLongIdx) <= squares)
		{
			return true;
		}

		return false;

	}	
	
	public static boolean mapIsWithinSquares(int currentFileAsInt,int proposedFileAsInt,int squares){
		
		int clai = createLatitudeIndex(currentFileAsInt);
		int cloi = createLongitudeIndex(currentFileAsInt);
		int plai = createLatitudeIndex(proposedFileAsInt);
		int ploi = createLongitudeIndex(proposedFileAsInt);
		
		return mapIsWithinSquares(clai, cloi, plai, ploi, squares);
	}
	
	/**
	 * Test whether a point falls within a specific region.
	 * @param testLat - integer latitude to be tested
	 * @param testLon - integer longitude to be tested
	 * @param lowerLat - integer lower left latitude
	 * @param upperLat - integer upper right latitude
	 * @param lowerLon - integer lower left longitude
	 * @param upperLon - integer upper right longitude
	 * 
	 * @return - true if test points are within, false otherwise.
	 */
	public static boolean isWithinRegion(int testLat,int testLon,
			int lowerLat,int upperLat,int lowerLon,int upperLon){
		
		if((testLat <= upperLat) 
				&& (testLat >= lowerLat)){
			if((testLon <= upperLon)
					&& (testLon >= lowerLon)){
				return true;
			}
		}
		
		
		return false;
	}
		
}

