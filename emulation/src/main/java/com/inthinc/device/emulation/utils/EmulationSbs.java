package com.inthinc.device.emulation.utils;

import java.util.concurrent.ExecutorService;

import com.inthinc.device.emulation.interfaces.SbsHessianInterface;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.sbs.Sbs;
import com.inthinc.sbs.SpeedLimit;
import com.inthinc.sbs.downloadmanager.SbsDownloadManager;
import com.inthinc.sbs.simpledatatypes.SbsPoint;
import com.inthinc.sbs.strategies.CoverageStrategy;
import com.inthinc.sbs.strategies.FivePointWindowStrategy;
import com.inthinc.sbs.strategies.ProximityAndHeadingStrategy;
import com.inthinc.sbs.strategies.SpeedlimitStrategy;

public class EmulationSbs extends Sbs {

	public EmulationSbs(String prefix, int requiredBaseline,
			int entryCountThresh, SbsDownloadManager downloadManager,
			CoverageStrategy coverageStrat, SpeedlimitStrategy speedlimStrat,
			ExecutorService threadManager)
			throws IllegalArgumentException {
		
		super(prefix, requiredBaseline, entryCountThresh, downloadManager,
				coverageStrat, speedlimStrat, true, threadManager);
		downloadManager.setSbsUpdater(this);
	}
	
	public EmulationSbs(SbsHessianInterface server, String mcmID) {
		this(server, mcmID, 7);
	}
	
	public EmulationSbs(SbsHessianInterface server, String mcmID, int baseLine){
		this(EmulationSBSDownloadManager.prefix, baseLine, 100, new EmulationSBSDownloadManager(server, mcmID),
				new FivePointWindowStrategy(), new ProximityAndHeadingStrategy(12000, 45), 
    			new EmulationSameThreadExecutor());
	}

	public SpeedLimit getSpeedLimit(GeoPoint location, Heading heading) {
		return getSpeedLimit(location, heading.getDegree()*10);
	}
	
	public SpeedLimit getSpeedLimit(GeoPoint location, int headingX10) {
		SbsPoint point = new SbsPoint(location.getLat(), location.getLng());
		SpeedLimit limit = getSpeedLimit(point.lat, point.lng, headingX10);
		int count = 0;
        while (limit.speedLimit == -1 && count++ < 4){
            limit = getSpeedLimit(point.lat, point.lng, headingX10);
        }
        return limit;
	}
}
