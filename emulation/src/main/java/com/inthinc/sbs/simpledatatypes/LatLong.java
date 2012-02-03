package com.inthinc.sbs.simpledatatypes;

import com.inthinc.sbs.utils.SbsUtils;

public final class LatLong {
	
    public final double latitude;
    public final double longitude;
    public final int ilatitude;
    public final int ilongitude;

    public LatLong()
    {
    	latitude = 0.0;
    	longitude = 0.0;
    	ilatitude = 0;
    	ilongitude = 0;
    }

    /**
     * @param inputLat:  input latitude
     * @param inputLong: input longitude
     */
    public LatLong(double latitude,double longitude)
    {
    	this.latitude = latitude;
    	this.longitude = longitude;
    	ilatitude = (int) (latitude * SbsUtils.LAT_LNG_SCALE_FACTOR);
    	ilongitude = (int) (longitude * SbsUtils.LAT_LNG_SCALE_FACTOR);
    }
    
}
