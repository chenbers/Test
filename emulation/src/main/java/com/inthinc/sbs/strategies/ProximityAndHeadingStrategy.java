package com.inthinc.sbs.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.inthinc.sbs.SpeedLimit;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SbsStreet;
import com.inthinc.sbs.regions.SbsWindow;
import com.inthinc.sbs.simpledatatypes.SbsPoint;

/**
 * This strategy attempts to find a street that is within a specific distance,
 * and whose angle with the current heading is less than a specified amount.
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * @author jlitzinger
 *
 */
public final class ProximityAndHeadingStrategy implements SpeedlimitStrategy {
	public static final String TAG = "%s";
	
	public static final int MAX_DISTANCE =  0;
	public static final int MAX_ANGLE = 1;
	private int maxDistance;
	private int maxAngle;
	private SpeedLimit lastLimit;
	private SbsStreet lastStreet = null;
	private SpeedLimit noHits = new SpeedLimit();
	
	public ProximityAndHeadingStrategy(int maxDistance,int maxAngle){
		this.maxAngle = maxAngle;
		this.maxDistance = maxDistance;
		lastLimit = new SpeedLimit();
	}

	private final List<SbsStreet> streetsFound = new ArrayList<SbsStreet>(5);
	
	@Override
	public SpeedLimit getSpeedlimit(SbsPoint currentPos, int heading,
			Map<Integer, SbsMap> maps, SbsWindow window) {
		
		if(maps == null
				|| currentPos == null
				|| window == null){
			Log.e(TAG,"Invalid parameter with getSpeedLimit");
			return noHits;
		}
		
		if(checkLastStreet(currentPos,heading)){
			return lastLimit;
		}
		if(findStreetByBoundingBox(currentPos, window, maps) <= 0){
			return noHits;
		}
		
		double minDist = this.maxDistance+1;
		double tdist = 0;
		int maxLimit = 0;
		int tLimit = 0;
		SbsStreet closest = null;
		SbsStreet selected = null;
		for(SbsStreet st : streetsFound){
			
			tdist =  st.getDistanceToStreet(currentPos.latitude, 
					currentPos.longitude,heading, this.maxDistance,this.maxAngle);
			
			if(tdist > maxDistance){
				continue;
			}
			
			if(tdist < minDist){
				Log.d(TAG,String.format("tdist = %.2f",tdist));
				minDist = tdist;
				closest = st;
			}
			
			tLimit = st.getSpeedLimit();
			if(tLimit > maxLimit){
				Log.d(TAG,"lim = " + tLimit);
				maxLimit = tLimit;
				selected = st;
			}
			
		}
		
		long closestGID = -1;
		if(closest != null){
			closestGID = closest.getGID();
		}
		
		SpeedLimit sl;
		lastStreet = null;
		
		if(selected != null){
			sl = new SpeedLimit(maxLimit,closestGID, 
					selected.getGID(),selected.getFileAsInt(),selected.isKph());
			lastStreet = selected;
		}else{
			sl = new SpeedLimit();
		}
		
		lastLimit = sl;
		
		return sl;
	}

	
	private final Object o = new Object();
	
	@Override
	public void setStrategyProperty(int key, int value) {
		
		synchronized (o) {
			switch(key){
			case MAX_DISTANCE:
				this.maxDistance = value;
				break;
			case MAX_ANGLE:
				this.maxAngle = value;
				break;
			default:
				break;
			}			
		}

		
	}

	@Override
	public SpeedLimit getLastSpeedlimit() {
		return lastLimit;
	}
	
	/**
	 * Find those streets for which the provided point is within the bounding
	 * box.
	 * @param currentPoint - current location.
	 * @param window - Window that is currently covered.
	 * @param maps - maps currently loaded in RAM.
	 * @return - number of streets found.
	 */
	int findStreetByBoundingBox(SbsPoint currentPoint, SbsWindow window,
			Map<Integer, SbsMap> maps) {
		
		
		streetsFound.clear();
		
		SbsPoint [] points = window.getPoints();
		SbsMap m = null;
		List<SbsStreet> mapStreets;
		for(SbsPoint p : points){
			
			m = maps.get(Integer.valueOf(p.fileAsInt));
			if(m == null){
				continue;
			}
			
			if(m.isEmpty()){
				continue;
			}
			
			mapStreets = m.getStreets();
			for(SbsStreet st : mapStreets){
				if(st.isWithin(currentPoint.lat, currentPoint.lng)){
					streetsFound.add(st);
				}
			}
			
		}
		
		return streetsFound.size();
	}

	/**
	 * Method is intended for testing only
	 * @return
	 */
	List<SbsStreet> getStreetsFound(){
		return streetsFound;
	}
	
	/**
	 * Determine whether a point still on the last street found.
	 * @param currentPos - current position.
	 * @param heading - current heading.
	 * @return true if the point is still on the last street, false otherwise.
	 */
	private boolean checkLastStreet(SbsPoint currentPos,int heading){
		
		if(lastStreet == null){
			return false;
		}
		
		if(lastStreet.isWithin(currentPos.lat, currentPos.lng)){
			//We might be on the right street.  Still need to verify that it passes
			//proximity and angle checks.
			double dist = lastStreet.getDistanceToStreet(currentPos.latitude, 
					currentPos.longitude,heading,maxDistance,maxAngle);
			
			if(dist < maxDistance){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Testing purposes only
	 * @param expected
	 */
	boolean validateLastStreet(SbsStreet expected){
		if(expected == null){
			return (lastStreet == null);
		}else{
			if(lastStreet == null){
				return false;
			}else{ 
				return lastStreet.equals(expected);
			}
		}
	}
	
}
