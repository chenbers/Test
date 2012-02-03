package com.inthinc.sbs.strategies;

import java.util.Map;

import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SbsWindow;

/**
* The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
* of this information is prohibited and punishable by law.
*
* Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
**/
public interface CoverageStrategy {

	/**
	 * @param lat - current latitude
	 * @param lng - current longitude
	 * @param heading - current heading
	 * @param loadedMaps - maps loaded in RAM
	 * @return true if this strategy considers itself fully covered for the
	 * provided point, false otherwise.
	 * 
	 * @throws IllegalArgumentException if loadedMaps is null.
	 */
	public boolean isCovered(int lat,int lng, Map<Integer,SbsMap> loadedMaps) throws IllegalArgumentException;

	/**
	 * Get the window used by this strategy
	 * @return
	 */
	public SbsWindow getWindow();
	
	/**
	 * Get the map that should be loaded if this strategy is not fully covered.
	 * @return 0 if the strategy is covered, fileAsInt to load otherwise.
	 */
	public int getMapToLoad();
	
}
