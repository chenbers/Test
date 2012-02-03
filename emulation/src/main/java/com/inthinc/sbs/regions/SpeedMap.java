package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.inthinc.sbs.simpledatatypes.LatLong;
import com.inthinc.sbs.utils.AbstractSbsMapLoader;
import com.inthinc.sbs.utils.SbsUtils;

/**
 * SpeedMaps are the current maps used as of 12/2011.  There are two direct
 * subclasses, SpeedBaseMap and SbsExceptionMap.
 * @author jlitzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public abstract class SpeedMap extends SbsMap {
	public static final String TAG = "SbsService";
	

	@Override
	/**
	 * @return read only copy of the list of streets in this map.
	 */
	public List<SbsStreet> getStreets() {
		return Collections.unmodifiableList(streets);
	}

	public LatLong getLowerLeft(){
		return lowerLeftCorner;
	}
	
	public LatLong getUpperRight(){
		return upperRightCorner;
	}
	
	protected final void parseHeader(ByteBuffer mapData,boolean isEx){
		
		int ver = mapData.get();
		this.isException = isEx;
		if(!isException){
			baseVersion = ver;
		}else{
			exceptionVersion = ver;
		}
		
		double lng = mapData.getDouble();
		double lat = mapData.getDouble();
		lowerLeftCorner = new LatLong(lat,lng);
		lng = mapData.getDouble();
		lat = mapData.getDouble();
		upperRightCorner = new LatLong(lat, lng);
		
		Log.d(TAG,String.format("lower left lat=%f,lng=%f",lowerLeftCorner.latitude,lowerLeftCorner.longitude));
		Log.d(TAG,String.format("upper right lat=%f,lng=%f",upperRightCorner.latitude,upperRightCorner.longitude));
		
		fileAsInt = SbsUtils.createFileAsInt(lowerLeftCorner.latitude + 0.025,
				lowerLeftCorner.longitude + 0.025);
	}
	
	protected final void parseStreets(ByteBuffer mapData,int baseVer){
		
		while(mapData.hasRemaining()
				&& (!AbstractSbsMapLoader.isNewMapMarker(mapData))){
			SbsStreet st = SbsStreet.fromByteBuf(mapData,baseVer,lowerLeftCorner.latitude,
					lowerLeftCorner.longitude,fileAsInt); 
			streets.add(st);
			if(!isException){
				streetHashMap.put(st.getStreetHash(),st);
			}
			//Log.i(TAG,st.toString());
		}
		
	}
	
}
