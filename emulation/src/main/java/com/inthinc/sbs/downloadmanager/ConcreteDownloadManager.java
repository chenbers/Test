package com.inthinc.sbs.downloadmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.util.Log;

import com.inthinc.device.emulation.interfaces.MCMService;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.simpledatatypes.DownloadedFile;
import com.inthinc.sbs.simpledatatypes.SubscriptionListElement;
import com.inthinc.sbs.simpledatatypes.VisitedMap;
import com.inthinc.sbs.utils.AbstractSbsMapLoader;
import com.inthinc.sbs.utils.SbsUtils;

/**
 * Concrete implementation of an Sbs Download Manager.
 * @author jlitzinger
 *
 * This implementation manages all sbs operations that involve interaction with a server, including:
 * - Downloading the files and writing them to disk.
 * - Checking the version of a file.
 * - Obtaining the subscription list for a device.
 * 
 * The contents of this file are confidential and/or proprietary.
 * Any unauthorized release or use of this information is
 * prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.
 * All rights reserved worldwide.
 */
public final class ConcreteDownloadManager implements SbsDownloadManager {
	public static final String TAG = "%s";
	
	/**
	 * Protected based on a google recommend when sharing variables to private
	 * inner classes.  Better performance, no cost since they are final.
	 */
	public static final String CHECKSBSEDIT = "checkSbsEdit";
	public static final String GETSBSEDIT = "getSbsEdit";
	public static final String GETSBSBASE = "getSbsBase";
	public static final String CHECKSBSSUBSCRIBED = "checkSbsSubscribed";
	public static final int MAX_ATTEMPTS = 5;
	private final Queue<SbsDownloadAction> actions = new ConcurrentLinkedQueue<SbsDownloadAction>();;
	private Map<Integer,VisitedMap> visitedMaps;
	private final String prefix;
	private final MCMService serverCall;
	private List<SbsMap> downloadedMaps;
	private AtomicBoolean downloadsEnabled = new AtomicBoolean(false);
	private final String mcmid;
	

	private final Map<Integer,AutomationCalendar> mapcheck;
	
	/**
	 * Create a download manager with a single thread in the pool.  
	 * 
	 * @param prefix - prefix to prepend to the location maps will be written to.
	 * @param visitedMaps - maps visited table
	 * @param serverCall - caller object to use to communicate with the server.
	 * @throws InvalidParameterException if any of the arguments are invalid. 
	 */
	public ConcreteDownloadManager(String mcmid,String prefix,Map<Integer,
			VisitedMap> visitedMaps, String server, int port) throws InvalidParameterException{
		if(visitedMaps == null){
			throw new InvalidParameterException();
		}
		
		this.visitedMaps = visitedMaps;
		this.serverCall = new AutomationHessianFactory().getMcmProxy(server, port);
		downloadsEnabled.set(true);
		this.mcmid = mcmid;
		this.prefix = prefix;
		mapcheck = new HashMap<Integer,AutomationCalendar>();
		
		/**
		 * Warning:  If the client doesn't properly clear the list after fetching
		 * the latest downloads, we'll leak memory here.
		 */
		this.downloadedMaps = new CopyOnWriteArrayList<SbsMap>();
	}

	
	@Override
	public void setDownloadsEnabledState(boolean enabled) {
		downloadsEnabled.set(enabled);
	}


	@Override
	public void checkSbsEdit(int fileAsInt, int b, int cv) {
		AutomationCalendar now = new AutomationCalendar();
		if (!mapcheck.containsKey(fileAsInt)){
			mapcheck.put(fileAsInt, now);
		}else if (!mapcheck.get(fileAsInt).compareDays(new AutomationCalendar())){
			actions.add(new SbsDownloadAction(CHECKSBSEDIT,fileAsInt,b,cv,-1));
		}

	}

	@Override
	public void getSbsBase(int fileAsInt, int b) {
		actions.add(new SbsDownloadAction(GETSBSBASE,fileAsInt,b,-1,-1));
	}

	@Override
	public void getSbsEdit(int fileAsInt, int b, int cv, int nv) {
		actions.add(new SbsDownloadAction(GETSBSEDIT,fileAsInt,b,cv,nv));
	}

	@Override
	public void checkSbsSubscribed(int fileAsInt, int b, int subscriptionWindow,
			int serverWindow, int entryCountThresh) {
		actions.add(new SbsDownloadAction(CHECKSBSSUBSCRIBED,fileAsInt,b,
				subscriptionWindow,serverWindow,entryCountThresh));
	}
	
	
	
	public List<SbsMap> getDownloadedMaps(){
		return Collections.unmodifiableList(downloadedMaps);
	}
	
	public void clearDownloadedMaps(){
		downloadedMaps.clear();
	}

	@Override
	public int getNumActions() {
		return actions.size();
	}
	
	public void run() {
		boolean actionSucceeded = false;
		
		SbsDownloadAction action = actions.peek();
		if(action == null){
			Log.d(TAG,"SbsDownloadManager: Nothing to do");
			return;
		}
		
		DownloadedFile df = null;
		StringBuilder filename = new StringBuilder();
		boolean writeData = false;
		
		if(action.actionName.equals(GETSBSEDIT)){
			df = actionGetSbsEdit(action.fileAsInt, action.b, action.cv, action.nv);
			if(df.data != null){
				actionSucceeded = true;
				filename.append(prefix);
				filename.append(AbstractSbsMapLoader.EXMAPV2);
				filename.append(SbsUtils.createMapFilename(action.fileAsInt, false));
				writeData = true;
				AbstractSbsMapLoader.createNewExceptionTree(prefix,action.fileAsInt);
				SbsMap map = SbsMap.exmapFromByteArray(ByteBuffer.wrap(df.data), action.b);
				downloadedMaps.add(map);
				
				VisitedMap vm = visitedMaps.get(Integer.valueOf(df.fileAsInt));
				if(vm != null){
					Log.d(TAG,String.format("Clear entry count for %d",df.fileAsInt));
					vm.entryCount = 0;
				}
				visitedMaps.put(action.fileAsInt, vm);
				
			}else if(df.errorCode == 304){
				Log.d(TAG,"The requested file does not exist");
				actionSucceeded = true;
			}
		}else if(action.actionName.equals(CHECKSBSEDIT)){
			int numMapsToUpdate = actionCheckSbsEdit(action.fileAsInt, action.b, action.cv);
			if(numMapsToUpdate < 0){
				Log.e(TAG,"checkSbsEdit failed");
			}else{
				actionSucceeded = true;
				if(numMapsToUpdate > 0){
					getSbsEdit(action.fileAsInt, action.b, action.cv, action.nv);
				}
			}
		}else if(action.actionName.equals(GETSBSBASE)){
			df = actionGetSbsBase(action.fileAsInt, action.b);
			if(df.data != null){
				actionSucceeded = true;
				filename.append(prefix);
				filename.append(AbstractSbsMapLoader.SBSV2);
				filename.append(SbsUtils.createMapFilename(action.fileAsInt, false));
				writeData = true;
				AbstractSbsMapLoader.createNewBaseTree(prefix,action.fileAsInt);
				//Delete any existing exception files BEFORE we write the new baseline
				String exfile = String.format("%s%s%s",prefix,AbstractSbsMapLoader.EXMAPV2,SbsUtils.createMapFilename(action.fileAsInt, false));
				File f = new File(exfile);
				if(f.exists()){
					if(!f.delete()){
						Log.d(TAG,"ConcreteSbsDownloadMgr: Failed to delete old exception file in new tree");
					}
				}
				
				visitedMaps.remove(Integer.valueOf(df.fileAsInt));
				
			}else if(df.errorCode == 304){
				Log.d(TAG,"The requested file does not exist");
				actionSucceeded = true;
			}
		}else if(action.actionName.equals(CHECKSBSSUBSCRIBED)){
			SubscriptionResult res = actionCheckSbsSubscribed(action.fileAsInt, action.b, action.serverWindow);
			if(res.retCode != null){
				Log.e(TAG,String.format("checkSbsSubscribed failed with code %d",res.retCode.intValue()));
			}else{
				//TODO:  Must get the current position from somewhere here.....or is this better?
				List<SubscriptionListElement> filteredList = filterList(action.fileAsInt, res.list,action.b, 
						action.subscriptionWindow,action.entryCountThresh);
				
				if(!postProcessSubscriptionList(filteredList, action.b)){
					Log.e(TAG,"Failed to process all list elements, queued what we did process");
				}
				actionSucceeded = true;
				
			}
		}else{
			Log.d(TAG,String.format("Unknown action: %s",action.actionName));
		}
		
		if(writeData){
			AbstractSbsMapLoader.writeFile(filename.toString(), df.data);
		}
		
		if(actionSucceeded){
			actions.poll();
		}else{
			action.numTries++;
			Log.d(TAG,String.format("DownloadManager:  Action %s failed, attempt %d of %d",action.actionName,action.numTries,MAX_ATTEMPTS));
			if(action.numTries >= MAX_ATTEMPTS){
				actions.poll();
			}
		}
		
		
	}

	DownloadedFile actionGetSbsEdit(int fileAsInt, int b, int cv, int nv) {
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("b",b);
		m.put("f",fileAsInt);
		m.put("cv",cv);
		m.put("nv",nv);
		
		DownloadedFile df = null;
		Map<String, Object> result = null;
		try {
			 result = serverCall.getSbsEdit(this.mcmid, m);
		} catch (HessianException e){
			Integer response = e.getErrorCode();
			Log.e(TAG,String.format("actionGetSbsEdit:  Integer Error returned: %d",response.intValue()));
			df = new DownloadedFile(response);
		}
		if(result == null){
			Log.e(TAG,"actionGetSbsEdit:  Null result returned");
			df = new DownloadedFile();
		}else {
			Log.d(TAG,"actionGetSbsEdit:  Sbs Exception map returned");
			Map<String,Object> reply = (Map<String,Object>) result;
			
			byte [] data = (byte []) reply.get("f");
			String name = (String) reply.get("n");
			Integer version = (Integer) reply.get("v");

			if(data != null
					&& name != null
					&& version != null){
				Log.d(TAG,String.format("%s: %d bytes version %d returned",name,data.length,version.intValue()));
			}
			
			df = new DownloadedFile(fileAsInt, data, true,version.intValue());
			
		}
		
		return df;
	}
	
	/**
	 * 
	 * @param fileAsInt - file as int for this map
	 * @param b - baseline
	 * @param cv - current version
	 * @return number of maps needing an update, -1 if attempt failed
	 */
	int actionCheckSbsEdit(int fileAsInt, int b, int cv) {
		
		int numMapsToUpdate = 0;
		
		Map<String,Object> m = new HashMap<String, Object>();
		ArrayList<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
		m.put("b",Integer.valueOf(b));
		m.put("f",Integer.valueOf(fileAsInt));
		m.put("cv",Integer.valueOf(cv));
		maps.add(m);
		
		List<Map<String,Object>> result = null;

		try {
			result = serverCall.checkSbsEdit(mcmid, maps);
			Log.i(TAG, result);
		} catch (HessianException e){
			Integer response = e.getErrorCode();
			Log.d(TAG,String.format("actionGetSbsEdit:  Integer Error returned: %d",response.intValue()));
			numMapsToUpdate = -1;
		}
		
		if(result == null){
			Log.d(TAG,"actionGetSbsEdit:  Null result returned");
			numMapsToUpdate = -1;
		}else if(result instanceof ArrayList){
			
			Log.d(TAG,"actionCheckSbsEdit:  List returned");
			numMapsToUpdate = result.size();
			if (numMapsToUpdate == 0){
				return 0;
			}
			Integer fileAsInteger = null;
			Integer version = null;
			Integer baselineVersion = null;
			
			for(Map<String,Object> el : result){
				fileAsInteger = (Integer) el.get("f");
				version = (Integer) el.get("v");
				baselineVersion = (Integer) el.get("b");
				Log.d(TAG,String.format("f=%d,b=%d,v=%d\n",fileAsInteger,baselineVersion,version));
				getSbsEdit(fileAsInteger, baselineVersion, cv, version);
			}
//			run();
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	DownloadedFile actionGetSbsBase(int fileAsInt, int b) {
		
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("b",Integer.valueOf(b));
		m.put("f",Integer.valueOf(fileAsInt));
		
		Log.d(TAG,String.format("actionGetSbsBase: %d,%d",fileAsInt,b));
		DownloadedFile df = null;
		Object result = serverCall.getSbsBase(mcmid, m);
		if(result == null){
			Log.e(TAG,"actionGetSbsBase:  Null result returned");
			df = new DownloadedFile();
		}else if(result instanceof Integer){
			Integer response = (Integer) result;
			Log.e(TAG,String.format("actionGetSbsBase:  Integer Error returned: %d",response.intValue()));
			df = new DownloadedFile(((Integer) result).intValue());
		}else if(result instanceof Map){
			Log.d(TAG,"actionGetSbsBase:  Sbs base map returned");
			Map<String,Object> reply = (Map<String,Object>) result;
			
			byte [] data = (byte []) reply.get("f");
			String name = (String) reply.get("n");

			if(data != null
					&& name != null){
				Log.d(TAG,String.format("basemap %s: %d bytes returned",name,data.length));
			}
			df = new DownloadedFile(fileAsInt, data, false,0);
			
		}else{
			Log.e(TAG,"actionGetSbsBase:  Unknown object returned.");
			df = new DownloadedFile();
		}
		
		return df;
	}
	
	
	
	/**
	 * Filter the list to remove any maps that are not within the specified window, or that do not
	 * meet the entry count criteria.  
	 * 
	 * JAL NOTE:  returning a new list is not the most memory efficient
	 * way to do this, however, it is the cleanest, so until there is cause for change, I'm leaving
	 * it as is.
	 * 
	 * @param currentFileAsInt
	 * @param elements
	 * @return - Filtered list of elements.  The list may be empty, but it will not be null.
	 */
	List<SubscriptionListElement> filterList(int currentFileAsInt,
			List<Map<String,Integer>> elements,int base, int window,int entryCount){
		
		List<SubscriptionListElement> filteredList = new ArrayList<SubscriptionListElement>();
		VisitedMap vm = null;
		int dist = 0;
		
		Log.d(TAG,String.format("Filter subscription list with %d elements, by window %d and entry count threshold %d",
				elements.size(),window,entryCount));
		
		int fileAsInt = 0;
		int version = 0;
		Integer fai = null;
		Integer ver = null;
		
		for(Map<String,Integer> e : elements){
			
			fai = e.get("f");
			ver = e.get("v");
			
			if((fai == null) || (ver == null)){
				Log.d(TAG,"NULL Element, not sure how we got this");
				continue;
			}
			
			fileAsInt = fai.intValue();
			version = ver.intValue();
			
			dist = SbsUtils.distanceInSquares(SbsUtils.createLatitudeIndex(fileAsInt),
					SbsUtils.createLongitudeIndex(fileAsInt),
					SbsUtils.createLatitudeIndex(currentFileAsInt),
					SbsUtils.createLongitudeIndex(currentFileAsInt));
			
			if(dist <= window){
				filteredList.add(new SubscriptionListElement(fileAsInt, version, base, dist));
			}else{
				vm = visitedMaps.get(Integer.valueOf(fileAsInt));
				if(vm == null){
					continue;
				}else if(vm.entryCount > entryCount){
					dist = SbsUtils.distanceInSquares(SbsUtils.createLatitudeIndex(fileAsInt),
							SbsUtils.createLongitudeIndex(fileAsInt),
							SbsUtils.createLatitudeIndex(currentFileAsInt),
							SbsUtils.createLongitudeIndex(currentFileAsInt));
					Log.d(TAG,String.format("Adding %d because it exceeds entryCount",fileAsInt));
					filteredList.add(new SubscriptionListElement(fileAsInt, version, base, dist));
				}
			}
						
		}
		
		Log.d(TAG,String.format("List contains %d elements after filtering",filteredList.size()));
		//Sort list
		Collections.sort(filteredList,new Comparator<SubscriptionListElement>() {

			@Override
			public int compare(SubscriptionListElement lhs,
					SubscriptionListElement rhs) {
				
				if(lhs.distanceInSquares < rhs.distanceInSquares){
					return -1;
				}else if(lhs.distanceInSquares > rhs.distanceInSquares){
					return 1;
				}else{
					return 0;
				}
			}
			
		});
		
		return filteredList;
	}
	
	class SubscriptionResult {
		public final List<Map<String,Integer>> list;
		public final Integer retCode;
		
		public SubscriptionResult(List<Map<String,Integer>> list){
			this.list = list;
			this.retCode = null;
		}
		
		public SubscriptionResult(Integer retCode){
			this.retCode = retCode;
			this.list = null;
		}
		
	}
	
	
	/**
	 * This function actually calls checkSbsSubscribed to the server.
	 * @param fileAsInt -- The fileAsInt of the current location when this was queued.
	 * @param base - baseline of the device
	 * @param serverWindow - window the server should apply.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SubscriptionResult actionCheckSbsSubscribed(int fileAsInt,int base,int serverWindow){
		
		SubscriptionResult subsResult = null;
		
		Map<String,Object> m = new HashMap<String, Object>();
		ArrayList<Object> args = new ArrayList<Object>(2);
		m.put("b",Integer.valueOf(base));
		
		if(serverWindow > 0){
			Log.d(TAG,String.format("Server will filter with window %d",serverWindow));
			m.put("lai",Integer.valueOf(SbsUtils.createLatitudeIndex(fileAsInt)));
			m.put("loi",Integer.valueOf(SbsUtils.createLongitudeIndex(fileAsInt)));
			m.put("w",Integer.valueOf(serverWindow));
		}
		
		Log.d(TAG,String.format("checkSbsSubscribed: %d,%d",fileAsInt,base));
		args.add(mcmid);
		args.add(m);

		Object result = serverCall.checkSbsSubscribed(mcmid, m);
		if(result == null){
			Log.e(TAG,"checkSbsSubscribed:  Null result returned");
		}else if(result instanceof Integer){
			Integer response = (Integer) result;
			Log.e(TAG,String.format("checkSbsSubscribed:  Integer Error returned: %d",response.intValue()));
			subsResult = new SubscriptionResult(response);
		}else if(result instanceof ArrayList){
			List<Map<String,Integer>> l = (ArrayList<Map<String,Integer>>) result;
			if(l != null){
				Log.d(TAG,String.format("Retrieved a list of %d maps",l.size()));
			}
			subsResult = new SubscriptionResult(l);
		}
		
		return subsResult;
	}
	
	private final StringBuffer sb = new StringBuffer();
	
	/**
	 * Check a file's version against the one provided.
	 * @param f
	 * @param version  expected version
	 * @return true if the version is greater than or equal to
	 * the expected version.
	 */
	private final boolean checkVersion(File f,int version){
		FileInputStream fis = null;
		boolean res = false;
		try {
			fis = new FileInputStream(f);
			byte [] buf = new byte[33];
			fis.read(buf);
			if(buf[0] >= version){
				res = true;
			}
		} catch (FileNotFoundException e) {
			Log.e(TAG,String.format("failed to load header: %s",e.getLocalizedMessage()));
		} catch (IOException ioe){
			Log.e(TAG,String.format("failed to load header: %s",ioe.getLocalizedMessage()));
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					Log.e(TAG,String.format("failed to close after load header: %s",
							e.getLocalizedMessage()));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Compare the baseline version to that on disk.
	 * @param fileAsInt - fileAsInt of the map.
	 * @param base - expected baseline
	 * @return true if the map matches expected, false otherwise.
	 */
	protected boolean checkBaselineVersion(int fileAsInt,int base){
		sb.delete(0, sb.length());
		sb.append(prefix);
		sb.append(AbstractSbsMapLoader.SBSV2);
		sb.append(SbsUtils.createMapFilename(fileAsInt, false));
		File f = new File(sb.toString());
		if(!f.exists()){
			sb.delete(0, sb.length());
			sb.append(prefix);
			sb.append(SbsUtils.createMapFilename(fileAsInt, true));
			f = new File(sb.toString());
			if(!f.exists()){
				return false;
			}
		}
		
		return checkVersion(f, base);
	}
	
	/**
	 * Compare the exception version on disk to the expected version
	 * @param fileAsInt - fileAsInt of the map
	 * @param version - expected version
	 * @return - true if the map matches expected, false otherwise.
	 */
	protected boolean checkExceptionVersion(int fileAsInt,int version){
		sb.delete(0, sb.length());
		sb.append(prefix);
		sb.append(AbstractSbsMapLoader.EXMAPV2);
		sb.append(SbsUtils.createMapFilename(fileAsInt, false));
		File f = new File(sb.toString());
		if(!f.exists()){
			return false;
		}
		
		return checkVersion(f, version);
	}
	
	/**
	 * Post process the subscription list, queuing the necessary actions.
	 * @param list - list of elements to be processed.
	 * @param base - current baseline
	 * @return true if all elements were processed, false otherwise
	 */
	final boolean postProcessSubscriptionList(List<SubscriptionListElement> list,int base){
		int size = list.size();
		
		SubscriptionListElement e = null;
		int cnt = 0;
		for(int i = 0;i < size;i++){
			e = list.get(0);
			
			if(e == null){
				Log.d(TAG,"Somehow, we have a null list element");
				continue;
			}
			
			if(!checkBaselineVersion(e.fileAsInt,base)){
				getSbsBase(e.fileAsInt, e.base);
			}
			else if(!checkExceptionVersion(e.fileAsInt,e.version)){
				getSbsEdit(e.fileAsInt, e.base, 0, e.version);
			}
			
			cnt++;
		}
		
		
		
		return (cnt == size);
	}

	/**
	 * This is intended for test use ONLY.
	 */
	void clearActions(){
		actions.clear();
	}
	
	
	@Override
	public List<SbsDownloadAction> getActions() {
		LinkedList<SbsDownloadAction> l = new LinkedList<SbsDownloadAction>(actions);
		return l;
	}


	public Map<Integer, VisitedMap> getVisitedMaps() {
		return visitedMaps;
	}
	
}
