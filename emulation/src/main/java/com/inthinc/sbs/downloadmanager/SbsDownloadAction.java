package com.inthinc.sbs.downloadmanager;

public class SbsDownloadAction{

	public final String actionName;
	public final int fileAsInt;
	public final int b;
	public final int cv;
	public final int nv;
	protected int numTries = 0;
	public final int subscriptionWindow;
	public final int serverWindow;
	public final int entryCountThresh;
	
	/**
	 * 
	 * @param action - action to perform
	 * @param fileAsInt - file as int for the map to which this applies
	 * @param b baseline value
	 * @param cv current version
	 * @param nv - new version
	 */
	public SbsDownloadAction(String action,int fileAsInt,int b,int cv,int nv){
		
		this.actionName = action;
		this.fileAsInt = fileAsInt;
		this.b = b;
		this.cv = cv;
		this.nv = nv;
		this.subscriptionWindow = 0;
		this.serverWindow = 0;
		this.entryCountThresh = 0;
	}
	
	public SbsDownloadAction(String action,int fileAsInt, int b, 
			int subscriptionWindow, int serverWindow, int entryCountThresh){
		
		this.actionName = action;
		this.fileAsInt = fileAsInt;
		this.b = b;
		this.cv = -1;
		this.nv = -1;
		this.subscriptionWindow = subscriptionWindow;
		this.serverWindow = serverWindow;
		this.entryCountThresh = entryCountThresh;
	}
	
	
}
