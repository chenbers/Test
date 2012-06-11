package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import com.inthinc.sbs.simpledatatypes.LatLong;
import com.inthinc.sbs.utils.SbsUtils;

/**
 * All implementing classes should support a constructor with byte[]
 * argument.
 * @author jlitzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public abstract class SbsMap implements SbsRegion{
	public static final double MAP_SEGMENT = 0.05;
	public static final double CENTER_SNAP_OFFSET = 0.025;

	protected boolean isException = false;
	
	//TODO: 99% certain the redundancy is not necessary here.  A Set, which can be
	//obtained from a map, is easily iterable as well.
	protected List<SbsStreet> streets;
	protected Map<Long,SbsStreet> streetHashMap;
	protected int baseVersion = -1;
	protected int exceptionVersion = 0;
	protected LatLong lowerLeftCorner;
	protected LatLong upperRightCorner;
	protected int fileAsInt = -1;
	protected boolean isEmpty = false;
	
	/**
	 * Create an SbsMap from a byte array.
	 * @param mapData - the map data
	 * @param baseVer - the basemap version if this is an exception.  Use 0 to parse basemaps.
	 * @return
	 */
	public static SbsMap basemapFromByteArray(ByteBuffer mapData) throws NullPointerException{
		
		if(mapData == null){
			throw new NullPointerException();
		}
		
		if((mapData.get(0) & 0xFF) != 0xFF){
			return new SpeedBaseMap(mapData);
		}else{
			return null;
		}
	}
	
	public static SbsMap createEmptyMap(int fileAsInt){
		return new SpeedBaseMap(fileAsInt);
	}
	
	/**
	 * Parse an sbs map from a byte array knowing it is of the given
	 * baseline.  This method should be used for exception maps.
	 * @param data
	 * @param baseline
	 * @return
	 */
	public static SbsMap exmapFromByteArray(ByteBuffer mapData,int baseline){
		return new SbsExceptionMap(mapData, baseline);
	}
	
	
	/**
	 * 
	 * @return an array of all streets within this map.
	 */
	abstract public List<SbsStreet> getStreets();
	
	public boolean isException(){
		return isException;
	}
	
	public int getFileAsInt(){
		return fileAsInt; 
	}
	
	/**
	 *@return true if map is an empty map, false otherwise 
	 */
	public boolean isEmpty(){
		return isEmpty;
	}
	
	/**
	 * 
	 * @return baseline version of this map.
	 */
	public int getBaselineVersion() {
		return baseVersion;
	}

	/**
	 * @return exception version of this map, defaults to 0.
	 */
	public int getExceptionVersion() {
		return exceptionVersion;
	}

	
	/**
	 * Determine if lat/lng provided are within this map.
	 * @param lat - latitude x SbsUtils.LAT_LNG_SCALE_FACTOR
	 * @param lng - longitude x SbsUtils.LAT_LNG_SCALE_FACTOR
	 */
	@Override
	public boolean isWithin(int lat, int lng) {
		
		return SbsUtils.isWithinRegion(lat, lng, 
				lowerLeftCorner.ilatitude, upperRightCorner.ilatitude,
				lowerLeftCorner.ilongitude,upperRightCorner.ilongitude);
	}
	
	/**
	 * Merge an exception map into a basemap.  This method adheres to the following
	 * contracts:
	 * 0 is returned if:
	 * -- exmap is null
	 * -- exmap is not an exception map
	 * -- The map for which this method is invoked is empty
	 * -- The fileAsInts of exmap and the map on which this is invoked do not match.
	 *
	 * @param exmap
	 * @return - number of streets merged.
	 */
	abstract public int mergeMap(SbsMap exmap);
	
}
