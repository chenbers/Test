package com.inthinc.sbs;

import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.util.Log;

import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.sbs.downloadmanager.ConcreteDownloadManager;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.simpledatatypes.SbsPoint;
import com.inthinc.sbs.simpledatatypes.VisitedMap;
import com.inthinc.sbs.strategies.CoverageStrategy;
import com.inthinc.sbs.strategies.FivePointWindowStrategy;
import com.inthinc.sbs.strategies.ProximityAndHeadingStrategy;
import com.inthinc.sbs.strategies.SpeedlimitStrategy;
import com.inthinc.sbs.utils.AbstractSbsMapLoader;


/**
 * Primary object to manage speed by street.  This object coordinates the various strategies to obtain a 
 * speedlimit and report it back to a caller.  Significant testing to ensure thread safety has NOT been
 * performed on this object.
 * 
 * @author jason litzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 **/
public class Sbs implements SpeedLimitProvider{
	public static final String TAG = "%s";
	
	private CoverageStrategy coverageStrategy;
	private final ConcreteDownloadManager downloadManager;
	private final Map<Integer,SbsMap> loadedMaps = new HashMap<Integer, SbsMap>();
	
	private Future<List<SbsMap>> loader = null;
	
	private final Map<Integer,VisitedMap> mapsVisited;


	private int requiredBaselineVersion;


	private final SpeedlimitStrategy speedlimitStrat;


	private final ExecutorService threadManager;



	/**
	 * Create a new Sbs object.
	 * 
	 * @param requiredBaseline - baseline used by this device.
	 * @param entryCountThresh - Threshold used to determine if a map has been
	 * entered enough times that it should be updated.
	 * 
	 * @param downloadManager - object to queue downloads in.
	 * @param coverageStrat - Strategy to use to load maps.
	 * @param speedlimStrat - Strategy to use to obtain speedlimits.
	 * @param visitedMaps - Map to store the maps this object visits.
	 * @throws IllegalArgumentException - if any input parameters are null, or the
	 * required baseline is less than 7.
	 */
	public Sbs(String mcmid, int requiredBaseline, Addresses server) throws IllegalArgumentException{
		this(mcmid, requiredBaseline, server.getMCMUrl(), server.getMCMPort());
	}
	
	public Sbs(String mcmid, int requiredBaseline, String server, int port) throws IllegalArgumentException{
		
		if(requiredBaseline < 7) {
			throw new IllegalArgumentException("Baseline HAS to be over 7");
		}
		
		
		requiredBaselineVersion = requiredBaseline;
		mapsVisited = new HashMap<Integer, VisitedMap>();
		downloadManager = new ConcreteDownloadManager(mcmid, "target/", mapsVisited, server, port);
		coverageStrategy = new FivePointWindowStrategy();
		speedlimitStrat = new ProximityAndHeadingStrategy(12000, 45);
		
		threadManager = Executors.newCachedThreadPool();
	}


	@Override
	public void dumpAllMaps() {
		loadedMaps.clear();
	}


	public CoverageStrategy getCoverageStrategy() {
		return coverageStrategy;
	}


	public ConcreteDownloadManager getDownloadManager() {
		return downloadManager;
	}



	/**
	 * Methods intended for test access only.
	 */
	public Map<Integer,SbsMap> getLoadedMaps(){
		Map<Integer,SbsMap> maps = new HashMap<Integer, SbsMap>(loadedMaps);
		return Collections.unmodifiableMap(maps);
	}


	public Future<List<SbsMap>> getLoader() {
		return loader;
	}


	public Map<Integer, VisitedMap> getMapsVisited() {
		return mapsVisited;
	}


	public int getRequiredBaselineVersion() {
		return requiredBaselineVersion;
	}
	
	public SpeedLimit getSpeedLimit(GeoPoint location, Heading heading) {
		SbsPoint point = new SbsPoint(location.getLat(), location.getLng());
		return getSpeedLimit(point.lat, point.lng, heading.getDegree()*10);
	}
	
	public SpeedLimit getSpeedLimit(GeoPoint location, int headingX10) {
		SbsPoint point = new SbsPoint(location.getLat(), location.getLng());
		return getSpeedLimit(point.lat, point.lng, headingX10);
	}

	@Override
	public synchronized SpeedLimit getSpeedLimit(int lat, int lng, int heading) {
		
		SbsPoint currentPoint = new SbsPoint(lat,lng);
    	mergeDownloadedMaps(downloadManager.getDownloadedMaps(),loadedMaps);
		
    	Log.d(TAG,String.format("Get limit for %d,%d,%d",lat,lng,heading));
    	if(!coverageStrategy.isCovered(lat, lng, loadedMaps)){
    		if(loader == null){
    			launchLoader(coverageStrategy.getMapToLoad());
    		}
    	}
		Log.d(TAG,"Loader is complete, process");
		List<SbsMap> maps = null;
		try {
			maps = loader.get();
			if(maps != null){
				for(SbsMap map : maps){
					loadedMaps.put(Integer.valueOf(map.getFileAsInt()), map);
					postProcessLoad(map);
				}
			}
		} catch (InterruptedException e) {
			Log.e(TAG, e);
		} catch (ExecutionException e) {
			Log.e(TAG, e);
		} 
    	
    	if(!coverageStrategy.isCovered(lat, lng, loadedMaps)){
    		if(loader == null){
    			launchLoader(coverageStrategy.getMapToLoad());
    		}
    	}
    	
    	
    	Log.d(TAG,"Coverage acheived, look for a limit");
    	return speedlimitStrat.getSpeedlimit(currentPoint,heading,loadedMaps,coverageStrategy.getWindow());
    	
	}
	public SpeedlimitStrategy getSpeedlimitStrat() {
		return speedlimitStrat;
	}
	
	public ExecutorService getThreadManager() {
		return threadManager;
	}
	public boolean isLoaderComplete(){
		if(loader != null){
			return loader.isDone();
		}
		return false;
	}
	
	private void launchLoader(int fileAsInt){
		Log.d(TAG,"Loader submitted for processing");
		loader = threadManager.submit(AbstractSbsMapLoader.newMapLoader("target", fileAsInt, downloadManager));
		while (!loader.isDone()){
			Thread.yield();
		}
	}
	
	
	public boolean loaderExists(){
		if(loader == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Merge any downloaded maps into the currently loaded maps.  Only exception
	 * maps should be placed in this list.
	 * @param downloadedMaps - list of downloaded maps
	 * @param mapsLoaded - maps currently loaded in RAM
	 */
	public void mergeDownloadedMaps(List<SbsMap> downloadedMaps,Map<Integer,SbsMap> mapsLoaded){
		
		for(SbsMap map : downloadedMaps){
			SbsMap m = mapsLoaded.get(map.getFileAsInt());
			if(m != null){
				m.mergeMap(map);
			}
		}
		
	}
	
	/**
	 * Post process a loaded map to queue any necessary updates.
	 * @param m - map loaded, must not be null
	 */
	public void postProcessLoad(SbsMap m){
		
		if(m.isEmpty()){
			return;
		}
		
		if(m.getBaselineVersion() < requiredBaselineVersion){
			downloadManager.getSbsBase(m.getFileAsInt(), requiredBaselineVersion);
		}else{
			VisitedMap vmap = mapsVisited.get(m.getFileAsInt());
			if(vmap != null){
				vmap.entryCount++;
				if(vmap.entryCount > 15){
					downloadManager.checkSbsEdit(m.getFileAsInt(),
							m.getBaselineVersion(), m.getExceptionVersion());
				}
			}else{
				vmap = new VisitedMap(m.getFileAsInt(), m.getExceptionVersion(), 1);
				mapsVisited.put(Integer.valueOf(m.getFileAsInt()), vmap);
				
				downloadManager.checkSbsEdit( m.getFileAsInt(), 
						m.getBaselineVersion(), m.getExceptionVersion());
			}
		}
		downloadManager.run();
		
	}
	
	public void setCoverageStrategy(CoverageStrategy coverageStrategy) {
		this.coverageStrategy = coverageStrategy;
	}

	public void setLoader(Future<List<SbsMap>> loader) {
		this.loader = loader;
	}
	
	public void setMapsVisited(Map<Integer, VisitedMap> mapsVisited) {
		this.mapsVisited.putAll(mapsVisited);
	}
	
	public void setRequiredBaselineVersion(int requiredBaselineVersion) {
		this.requiredBaselineVersion = requiredBaselineVersion;
	}
	
	
	public static void main(String[] args){
		GeoPoint location;
		if (args[0].equals("latlng")){
			location = new GeoPoint(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else {
			StringWriter writer = new StringWriter();
			for (String arg: args){
				System.out.println(arg);
				if (arg.equals(args[0])){
					continue;
				}
				writer.write(arg);
			}
			location = new GeoPoint(writer.toString());
		}
		Sbs sbs = new Sbs("DEVICEDOESNTEXIST", 7, Addresses.QA);
		for (Heading heading: Heading.values()){
			System.out.printf("\nHeading: %s, Limit: %d\n", heading, sbs.getSpeedLimit(location, heading).speedLimit/100);
		}
		sbs.getThreadManager().shutdown();
	}
	
}
