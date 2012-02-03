package com.inthinc.sbs.downloadmanager;

import java.util.List;

import com.inthinc.sbs.regions.SbsMap;

/**
 * Interface for a DownloadManager for sbs files.
 * @author jlitzinger
 * 
 * The contents of this file are confidential and/or proprietary.
 * Any unauthorized release or use of this information is
 * prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.
 * All rights reserved worldwide.
 */
public interface SbsDownloadManager {

	
	/**
	 * Toggle whether downloads are enabled or disabled.  If downloads
	 * are disabled, the queue of pertinent actions/updates will not be
	 * maintained or saved. 
	 * 
	 * @param enabled - if true, downloads are enabled, 
	 * if false, downloads are disabled.
	 */
	public void setDownloadsEnabledState(boolean enabled);
	
	
	public List<SbsMap> getDownloadedMaps();
	
	/**
	 * 
	 * @return the number of actions in the queue.
	 */
	public int getNumActions();
	
	/**
	 * Clear the download manager's list of downloaded sbs maps.
	 */
	public void clearDownloadedMaps();
	
	/**
	 * Check the version of the map.
	 * @param downloadManager
	 * @param fileAsInt - fileAsInt of the map to check.
	 * @param b - baseline version of the current map
	 * @param cv - current exception version of the map.
	 */
	public void checkSbsEdit(int fileAsInt,int b, int cv);
	
	/**
	 * Fetch a new baseline map
	 * @param fileAsInt - fileAsInt of the map to download.
	 * @param b - desired baseline
	 */
	public void getSbsBase(int fileAsInt,int b);
	
	/**
	 * Download an exception map.
	 * 
	 * @param fileAsInt - fileAsInt of the map to download.
	 * @param b - baseline of the current map
	 * @param cv - current exception version of the map
	 * @param nv - desired exception version of the map
	 */
	public void getSbsEdit(int fileAsInt,int b,int cv,int nv);
	
	/**
	 * @param fileAsInt - fileAsInt of current location
	 * @param b - baseline version
	 * @param subscriptionWindow - window to apply on the results on the server.
	 * @param serverWindow - window that the server will apply.
	 * @param entryCountThresh - entry count threshold to use for filtering.
	 */
	public void checkSbsSubscribed(int fileAsInt, int b, int subscriptionWindow,
			int serverWindow, int entryCountThresh);

	
	
	public List<SbsDownloadAction> getActions();
}
