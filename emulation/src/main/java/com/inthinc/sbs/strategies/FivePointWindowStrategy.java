package com.inthinc.sbs.strategies;

import java.util.Map;

import android.util.Log;

import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SbsWindow;
import com.inthinc.sbs.regions.Window;
import com.inthinc.sbs.simpledatatypes.SbsPoint;


/**
 * Attempts to cover a window consisting of 5 points, the center point and 4 points
 * around it.
 * @author jlitzinger
 *
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public class FivePointWindowStrategy implements CoverageStrategy {

	private SbsWindow currentWindow = new Window();
	private int mapToLoad = 0;
	
	public FivePointWindowStrategy(){
		
	}
	
	/**
	 * @return false if all 5 points are not covered, true otherwise.
	 */
	@Override
	public boolean isCovered(int lat, int lng, Map<Integer, SbsMap> loadedMaps) throws IllegalArgumentException{

		
		if(loadedMaps == null){
			throw new IllegalArgumentException();
		}
		
		SbsWindow w = new Window(lat,lng);
		SbsPoint [] points = w.getPoints();
		currentWindow = w;
		for(SbsPoint p : points){
			if(!p.isCovered()){
				SbsMap map = loadedMaps.get(Integer.valueOf(p.fileAsInt));
				if(map == null){
					Log.d("SbsService","attempt to cover " + p.fileAsInt);
					p.setCovered(false);
					mapToLoad = p.fileAsInt;
					return false;
				}else{
					p.setCovered(true);
				}
			}
		}
		
		return true;
	}

	@Override
	public SbsWindow getWindow() {
		return currentWindow;
	}

	@Override
	public int getMapToLoad() {
		return mapToLoad;
	}

}
