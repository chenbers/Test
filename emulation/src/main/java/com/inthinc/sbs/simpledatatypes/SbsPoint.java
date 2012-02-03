package com.inthinc.sbs.simpledatatypes;


import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.sbs.utils.SbsUtils;

/**
 * @author jason litzinger
 * An object representing an SbsPoint.  This object holds the following contracts
 * true.
 * 
 * - lat/lng are always the integer version of a latitude/longitude pair scaled by
 *   SbsUtils.LAT_LNG_SCALE_FACTOR.
 * - isCovered is initialized to false when constructor arguments are provided, true when the
 *   default constructor is true.
 * - fileAsInt is the integer fileAsInt of the latitude/longitude pair.
 * 
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * **/

public final class SbsPoint {
	public final double latitude;
	public final double longitude;
	public final int lat;
	public final int lng;
	public final int fileAsInt;
	private boolean isCovered = false;
	
	public SbsPoint()
	{
		latitude = 0.0;
		longitude = 0.0;
		fileAsInt = -1;
		isCovered = true;
		lat = 0;
		lng = 0;
	}
	
	public SbsPoint(GeoPoint location){
		this(location.getLat(), location.getLng());
	}
	
	public SbsPoint(double latitude,double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		fileAsInt = SbsUtils.createFileAsInt(latitude, longitude);
		lat = (int)(this.latitude * SbsUtils.LAT_LNG_SCALE_FACTOR);
		lng = (int)(this.longitude * SbsUtils.LAT_LNG_SCALE_FACTOR);
	}
	
	public SbsPoint(int latitude,int longitude)
	{
		this.lat = latitude;
		this.lng = longitude;
		this.latitude = ((double) lat / ((double) SbsUtils.LAT_LNG_SCALE_FACTOR));
		this.longitude = ((double) lng / ((double) SbsUtils.LAT_LNG_SCALE_FACTOR));
		fileAsInt = SbsUtils.createFileAsInt(this.latitude, this.longitude);
		
	}
	
	public void setCovered(boolean val){
		isCovered = val;
	}
	
	public boolean isCovered(){
		return isCovered;
	}
	
}

