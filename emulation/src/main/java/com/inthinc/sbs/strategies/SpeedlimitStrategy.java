package com.inthinc.sbs.strategies;

import java.util.Map;

import com.inthinc.sbs.SpeedLimit;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SbsWindow;
import com.inthinc.sbs.simpledatatypes.SbsPoint;

/**
 * Interface defining a strategy used to select a speedlimit from a set of maps.
 * @author jlitzinger
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public interface SpeedlimitStrategy {

	/**
	 * For the given point and maps, find the best speedlimit in the
	 * provided window.
	 * @param currentPos - current position.
	 * @param heading - current heading, degrees * 10.
	 * @param maps - Map containing the SbsMaps currently in RAM.
	 * @param window - window to search.
	 * @return - Best speedlimit according to the strategy.
	 */
	public SpeedLimit getSpeedlimit(SbsPoint currentPos, int heading,
			Map<Integer,SbsMap> maps,SbsWindow window);
	

	/**
	 * @return - SpeedLimit selected the last time getSpeedLimit was invoked.
	 */
	public SpeedLimit getLastSpeedlimit();
	
	/**
	 * Set a specific property for a strategy.
	 * @param key - key identifying the property.
	 * @param value - value to set the property to.
	 */
	public void setStrategyProperty(int key,int value);
	
}
