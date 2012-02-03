package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * @author jlitzinger
 * 
 * Concrete implementation of an sbs exception map.  This map holds the following
 * contracts true:
 * 
 * 1.  isException will return true.
 * 2.  isEmpty will return false except in testing.
 * 3.  merge() will always return 0.
 * 
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public final class SbsExceptionMap extends SpeedMap {

	/**
	 * Used for testing only.
	 */
	/*package*/ SbsExceptionMap(){
		isEmpty = true;
		isException = true;
	}
	
	@Override
	public int mergeMap(SbsMap exmap) {
		return 0;
	}
	
	/**
	 * Create a speedmap from the file data
	 * @param mapData - ByteBuffer aligned to the beginning of the map data.
	 * @param baseVer - baseline version if this is an exception map.  0 if this is a basemap.
	 */
	public SbsExceptionMap(ByteBuffer mapData,int b){
		isEmpty = false;
		baseVersion = b;
		if(mapData.order() != ByteOrder.LITTLE_ENDIAN){
			mapData.order(ByteOrder.LITTLE_ENDIAN);	
		}
		
		streets = new ArrayList<SbsStreet>();
		streetHashMap = null;
		
		parseHeader(mapData,true);
		parseStreets(mapData,baseVersion);
		
	}
}
