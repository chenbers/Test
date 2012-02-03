package com.inthinc.sbs;

/**
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * @author jlitzinger
 *
 */
public interface SpeedLimitProvider {
	
	/**
	 * Obtain the current speed limit.
	 * @param lat - current latitude x 1e6
	 * @param lng - current longitude x 1e6
	 * @param heading - current heading x 10
	 * @return SpeedLimit object containing current speed limit.
	 */
	public SpeedLimit getSpeedLimit(int lat,int lng,int heading);
	
	/**
	 * Public method to flush all maps currently held in RAM.
	 */
	public void dumpAllMaps();
	
	
}
