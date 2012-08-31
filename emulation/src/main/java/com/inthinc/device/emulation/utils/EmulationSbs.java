package com.inthinc.device.emulation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.inthinc.device.emulation.interfaces.SbsHessianInterface;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.sbs.Sbs;
import com.inthinc.sbs.SpeedLimit;
import com.inthinc.sbs.downloadmanager.SbsDownloadManager;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.simpledatatypes.SbsPoint;
import com.inthinc.sbs.simpledatatypes.VisitedMap;
import com.inthinc.sbs.strategies.CoverageStrategy;
import com.inthinc.sbs.strategies.FivePointWindowStrategy;
import com.inthinc.sbs.strategies.ProximityAndHeadingStrategy;
import com.inthinc.sbs.strategies.SpeedlimitStrategy;

public class EmulationSbs extends Sbs {

	public EmulationSbs(String prefix, int requiredBaseline,
			int entryCountThresh, SbsDownloadManager downloadManager,
			CoverageStrategy coverageStrat, SpeedlimitStrategy speedlimStrat,
			Map<Integer, VisitedMap> visitedMaps, ExecutorService threadManager)
			throws IllegalArgumentException {
		
		super(prefix, requiredBaseline, entryCountThresh, downloadManager,
				coverageStrat, speedlimStrat, visitedMaps, threadManager);
	}
	
	public EmulationSbs(SbsHessianInterface server, String mcmID) {
		this(server, mcmID, 10);
	}
	
	public EmulationSbs(SbsHessianInterface server, String mcmID, int baseLine){
		this("target/sbs", baseLine, 100, new EmulationSBSDownloadManager(server, mcmID),
				new FivePointWindowStrategy(), new ProximityAndHeadingStrategy(12000, 45), 
    			new HashMap<Integer, VisitedMap>(), new EmulationSameThreadExecutor());
	}

	@Override
	protected void postProcessLoad(SbsMap m){
		if (m.isEmpty()){
			
		} else {
			super.postProcessLoad(m);
		}
	}
	
	public SpeedLimit getSpeedLimit(GeoPoint location, Heading heading) {
		SbsPoint point = new SbsPoint(location.getLat(), location.getLng());
		return getSpeedLimit(point.lat, point.lng, heading.getDegree()*10);
	}
	
	public SpeedLimit getSpeedLimit(GeoPoint location, int headingX10) {
		SbsPoint point = new SbsPoint(location.getLat(), location.getLng());
		return getSpeedLimit(point.lat, point.lng, headingX10);
	}
}
