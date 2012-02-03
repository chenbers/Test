package com.inthinc.sbs.regions;

public interface SbsRegion {

	/**
	 * Interface to determine whether a point falls within a region.
	 * @param lat - current latitude
	 * @param lng - current longitude
	 * @return - true if point falls within region, false otherwise.
	 */
	public boolean isWithin(int lat,int lng);
}
