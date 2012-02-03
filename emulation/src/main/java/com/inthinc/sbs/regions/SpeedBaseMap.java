package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.inthinc.sbs.simpledatatypes.LatLong;

/**
 * @author jlitzinger
 * 
 * Implementation of an Sbs basemap.  This map, prior to a merge, holds the 
 * following contracts true.
 * 
 * 1.  isException is false.
 * 2.  baseline version is -1 for empty maps, >= 3 otherwise
 * 3.  exception version is -1;
 * 4.  isEmpty will be true for maps that are not loaded from disk.
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public class SpeedBaseMap extends SpeedMap {
	public static final String TAG = "SbsService";
	
	/**
	 * Constructor to create an empty map.
	 * @param fileAsInt
	 */
	public SpeedBaseMap(int fileAsInt){
		isException = false;
		this.fileAsInt = fileAsInt;
		lowerLeftCorner = new LatLong();
		upperRightCorner = new LatLong();
		streets = null;
		isEmpty = true;
	}	
	
	
	@Override
	public int mergeMap(SbsMap exmap) {
		
		if((exmap == null)
				|| isEmpty
				|| (exmap.getBaselineVersion() == -1)
				|| (exmap.fileAsInt != fileAsInt)){
			Log.e(TAG,"mergeMap: exmap null or fileAsInt match failed");
			return 0;
		}

		int numMerged = 0;
		
		List<SbsStreet> exStreets = exmap.getStreets();
		SbsStreet baseStreet = null;
		exceptionVersion = exmap.getExceptionVersion();
		
		for(SbsStreet st : exStreets){
			baseStreet = streetHashMap.get(st.getStreetHash());
			if(baseStreet != null){
				baseStreet.speedlimit = st.speedlimit;
				baseStreet.category = st.category;
				baseStreet.numLanes = st.numLanes;
				baseStreet.isKph = st.isKph;
				numMerged++;
			}
		}
		
		return numMerged;
	}
	
	
	/**
	 * Create a speedmap from the file data
	 * @param mapData - ByteBuffer aligned to the beginning of the map data.
	 * @param baseVer - baseline version if this is an exception map.  0 if this is a basemap.
	 */
	public SpeedBaseMap(ByteBuffer mapData){
		isEmpty = false;
		if(mapData.order() != ByteOrder.LITTLE_ENDIAN){
			mapData.order(ByteOrder.LITTLE_ENDIAN);	
		}
		
		streets = new ArrayList<SbsStreet>();
		streetHashMap = new HashMap<Long, SbsStreet>();
		
		parseHeader(mapData,false);
		parseStreets(mapData,baseVersion);
		
	}
	
}
