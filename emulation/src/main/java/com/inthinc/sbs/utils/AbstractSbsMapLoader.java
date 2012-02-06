package com.inthinc.sbs.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;

import android.util.Log;

import com.inthinc.sbs.downloadmanager.ConcreteDownloadManager;
import com.inthinc.sbs.regions.SbsMap;

/**
 * @author jason litzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * **/



/**
 * Base class for all classes implementing map loads.  The purpose
 * of a map loader is to load a single map from disk, merge any exception
 * speed limits, and provide the results to the caller.
 * 
 * @author jason
 *
 */
public abstract class AbstractSbsMapLoader implements Callable<List<SbsMap>>{
	public static final String TAG = "%s";
	public static final String SBSV2 = "/sbsV2";
	public static final String EXMAPV2 = "/exmapV2";
	public static final String EXMAPOLD = "/ex_maps";
	public static final int CONCAT_WINDOW = 1;		//Number of maps above/below our current position to allow loading.
	
	
	/**
	 * Load a file from disk.  
	 * @param filename
	 * @return null if the file is not loaded, a byte [] containing the file data
	 * otherwise.
	 */
	public static byte [] loadFile(File file){
		
		if(file.length() <= 0){
			return null;
		}
		
		int length = (int) file.length();
		byte [] buf = new byte[length];
		
		InputStream in;
		BufferedInputStream bin = null;
		try{
			in = new FileInputStream(file);
			bin = new BufferedInputStream(in);

			int totalBytesRead = 0;
			int bytesToRead = length;
			while(totalBytesRead < length){
				totalBytesRead += bin.read(buf, totalBytesRead,bytesToRead);
				bytesToRead = (length - totalBytesRead);
			}			
		}catch (FileNotFoundException e) {
			Log.e(TAG,String.format("MapLoader: %s not found",file.getName()));
			buf = null;
		}catch (IOException ie){
			Log.e(TAG,"IO Exception reading from file");
		}finally{
			if(bin != null){
				try {
					bin.close();
				} catch (IOException e) {
					Log.e(TAG,"MapLoader:  IO Exception when closing file: " + e.getLocalizedMessage());
				}
			}
		}
		
		return buf;
	}

	/**
	 * 
	 * @return - number of maps contained in this binary data.
	 */
	
	
	/**
	 * Write a file that will contain the data in buf
	 * @param buf
	 * @return true if success, false otherwise
	 */
	public static boolean writeFile(String filename,byte [] buf){
		
		boolean result = false;
		
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(filename,false));
			bos.write(buf);
			bos.flush();
		} catch (FileNotFoundException e) {
			Log.e(TAG,String.format("File not found when attempting to write %s?!?",filename));
		} catch(IOException ie){
			Log.e(TAG,"IO Error on write: " + ie.getLocalizedMessage());
		}finally{
			if(bos != null){
				try{
					bos.close();
					result = true;
				}catch(IOException ie){
					Log.e(TAG,"IO Error on closing file: " + ie.getLocalizedMessage());
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Create a directory tree specified by the input variable.  
	 * @param path
	 * @return true if the directory tree was created, false otherwise.
	 */
	public static boolean createNewTree(String path){
		
		File file = new File(path);
		
		if(file.exists()){
			return false;
		}
		
		return file.mkdirs();
	}
	
	public static boolean createNewExceptionTree(String prefix,int fileAsInt){
		StringBuilder dirBuilder = new StringBuilder();
		dirBuilder.append(prefix);
		dirBuilder.append(EXMAPV2);
		String dir = SbsUtils.buildThreeDirTiers(
				SbsUtils.createLatitudeIndex(fileAsInt),
				SbsUtils.createLongitudeIndex(fileAsInt));
		dirBuilder.append(dir);
		
		return createNewTree(dirBuilder.toString());
		
	}
	
	
	public static boolean createNewBaseTree(String prefix,int fileAsInt){
		StringBuilder dirBuilder = new StringBuilder();
		dirBuilder.append(prefix);
		dirBuilder.append(SBSV2);
		String dir = SbsUtils.buildThreeDirTiers(
				SbsUtils.createLatitudeIndex(fileAsInt),
				SbsUtils.createLongitudeIndex(fileAsInt));
		dirBuilder.append(dir);
		
		return createNewTree(dirBuilder.toString());
		
	}
	
	public static boolean isNewMapMarker(ByteBuffer buf){
		if(buf.remaining() < 4){
			Log.e(TAG,String.format("Attempt to look in a buffer that doesn't have 4 left, pos=%d,len=%d",
					buf.position(),buf.remaining()));
			return false;
		}
		
		int currentPos = buf.position();
		//newM == 0x6E 0x65 0x77 0x4D 
		if(buf.get(currentPos) != (byte) 0x6E){
			return false;
		}else if(buf.get(currentPos + 1) != (byte) 0x65){
			return false;
		}else if(buf.get(currentPos + 2) != (byte) 0x77){
			return false;
		}else if(buf.get(currentPos + 3) != (byte) 0x4D){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	/**
	 * Static factory to generate map loaders.
	 */
	public static AbstractSbsMapLoader newMapLoader(String prefix,int fileAsInt, ConcreteDownloadManager manager){
		return new MapLoader(prefix,fileAsInt, manager);
	}
	
	/**
	 * Instance methods
	 */
	
	
}
