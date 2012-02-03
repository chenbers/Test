package com.inthinc.sbs.regions;


import com.inthinc.sbs.simpledatatypes.SbsPoint;
import com.inthinc.sbs.utils.SbsUtils;

/**
 * @author jason litzinger
 * 
 * This class implements a window that is based on 4 quadrants and a center point.  The four
 * points are 0.0125 degrees away from the center point.
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * **/

public class Window extends SbsWindow
{
	public static final int NUM_POINTS = 5;
	private static final double OFFSET = 0.0125;
	private static final int OFFSET_INT = (int) (OFFSET * SbsUtils.LAT_LNG_SCALE_FACTOR);
    private final SbsPoint [] windowPoints = new SbsPoint[NUM_POINTS];

    
    public Window(){
    	windowPoints[0] = new SbsPoint();
		windowPoints[1] = new SbsPoint();
		windowPoints[2] = new SbsPoint();
		windowPoints[3] = new SbsPoint();
		windowPoints[4] = new SbsPoint();
    }
    
    public Window(double centerLat,double centerLong)
    {

		windowPoints[0] = new SbsPoint(centerLat,centerLong);
		windowPoints[1] = new SbsPoint(centerLat-OFFSET,centerLong-OFFSET);
		windowPoints[2] = new SbsPoint(centerLat-OFFSET,centerLong+OFFSET);
		windowPoints[3] = new SbsPoint(centerLat+OFFSET,centerLong+OFFSET);
		windowPoints[4] = new SbsPoint(centerLat+OFFSET,centerLong-OFFSET);
		
    }
    
    public Window(int lat,int lng){
    	
    	windowPoints[0] = new SbsPoint(lat,lng);
		windowPoints[1] = new SbsPoint(lat-OFFSET_INT,lng-OFFSET_INT);
		windowPoints[2] = new SbsPoint(lat-OFFSET_INT,lng+OFFSET_INT);
		windowPoints[3] = new SbsPoint(lat+OFFSET_INT,lng+OFFSET_INT);
		windowPoints[4] = new SbsPoint(lat+OFFSET_INT,lng-OFFSET_INT);
    }
    
	@Override
	public SbsPoint [] getPoints(){
		
    	return windowPoints;
	}

    
    
}

