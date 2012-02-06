package com.inthinc.sbs.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.inthinc.sbs.downloadmanager.ConcreteDownloadManager;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SpeedBaseMap;

/**
 * Implementation of AbstractSbsMapLoader for loading, parsing, and inserting
 * maps into the runtime table.  This object is a callable that returns the list
 * of all maps it loaded and merged from disk.
 * @author jlitzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2012 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 */
public final class MapLoader extends AbstractSbsMapLoader {
	public static final String TAG = "%s";
	public final String prefix;
	private final int fileAsInt;
	private final ConcreteDownloadManager manager;
	
	
	public static String findConcatFile(String filename,int fileAsInt){
		File f = new File(filename);
		String dir = f.getParent();
		File d = new File(dir);
		String [] files = d.list();
		if(files == null){
			return null;
		}
		
		int maxLat = 0;
		int minLat = 0;
		int src = 0;
		String name = null;
		
		int desiredLat = SbsUtils.createLatitudeIndex(fileAsInt);
		for(String file : files){
			src = Integer.valueOf(file.substring(0, file.length() - 4)).intValue();
			minLat = src / 10000;
			maxLat = src % 10000;
			Log.d(TAG,String.format("min=%d,max=%d,desired=%d",minLat,maxLat,desiredLat));
			if((desiredLat >= minLat)
					&& (desiredLat <= maxLat)){
				name = file;
				break;
			}
		}
		
		if(name != null){
			name = String.format("%s/%s",dir,name);
		}
		
		return name;
	}
	
	public MapLoader(String prefix, int fileAsInt, ConcreteDownloadManager manager){
		this.prefix = prefix;
		this.fileAsInt = fileAsInt;
		this.manager = manager;
	}
	
	public byte [] loadFromNewTree(int fileAsInt,boolean base){
		
		StringBuilder fullFilename = new StringBuilder();
		String filenameNewPath = SbsUtils.createMapFilename(fileAsInt, false);
		String prefix = this.prefix.startsWith("\\") ? this.prefix.substring(1) : this.prefix;
		
		if(prefix != null){
			fullFilename.append(prefix);
		}
		
		if(base){
			fullFilename.append(SBSV2);
		}else{
			fullFilename.append(EXMAPV2);
		}
		fullFilename.append(filenameNewPath);
		File file = new File(fullFilename.toString());
		if (!file.exists()){
			if (base){
				manager.getSbsBase(fileAsInt, 7);
				manager.run();
			} else {
				manager.checkSbsEdit(fileAsInt, 7, 0);
				manager.run();
			}
		}
		return AbstractSbsMapLoader.loadFile(file);
	}
	
	public byte [] loadFromOldTree(int fileAsInt,boolean base){
		
		StringBuilder fullFilename = new StringBuilder();
		String filenameOldPath = SbsUtils.createMapFilename(fileAsInt, true);
		
		if(prefix != null){
			fullFilename.append(prefix);
		}
		
		if(!base){
			fullFilename.append(EXMAPOLD);
		}
		fullFilename.append(filenameOldPath);
		File file = new File(fullFilename.toString());
		byte [] data = AbstractSbsMapLoader.loadFile(file);
		if(data == null){
			//Look for a concatenated file 
			//file = new File(findConcatFile(file.getName(),fileAsInt));
			if(file != null){
				data = AbstractSbsMapLoader.loadFile(file);
			}
		}
		
		return data;
	}
	
	
	public static boolean writeSbsFile(String pfix,int fileAsInt,byte [] fileData,boolean base){
		
		
		StringBuilder newFilename = new StringBuilder();
		
		//First create the new tree
		newFilename.append(pfix);
		if(base){
			newFilename.append(AbstractSbsMapLoader.SBSV2);
		}else{
			newFilename.append(AbstractSbsMapLoader.EXMAPV2);
		}
		
		newFilename.append(SbsUtils.buildThreeDirTiers(SbsUtils.createLatitudeIndex(fileAsInt)
				,SbsUtils.createLongitudeIndex(fileAsInt)));
		
		AbstractSbsMapLoader.createNewTree(newFilename.toString());
		
		
		newFilename.delete(0, newFilename.length());
		newFilename.append(pfix);
		if(base){
			newFilename.append(AbstractSbsMapLoader.SBSV2);
		}else{
			newFilename.append(AbstractSbsMapLoader.EXMAPV2);
		}
		newFilename.append(SbsUtils.createMapFilename(fileAsInt, false));
		Log.d(TAG,String.format("Moving file %d to %s",fileAsInt,newFilename.toString()));
		
		return AbstractSbsMapLoader.writeFile(newFilename.toString(), fileData);
	}
	
	/**
	 * Load a map file from disk.
	 * @param fileAsInt - map to load
	 * @param base - baseline of the map.
	 * @return LoadedFile wrapping the loaded map data.  data member will
	 * be null if no map was loaded.
	 */
	public LoadedFile loadMapFile(int fileAsInt, boolean base){
		byte [] fileData = null;
		boolean moveRequired = false;
		
		if(base){
			Log.d(TAG, String.format("Attempting to load basemap %d",fileAsInt));
		}else{
			Log.d(TAG, String.format("Attempting to load exmap %d",fileAsInt));
		}
		
		fileData = loadFromNewTree(fileAsInt,base);
		if(fileData == null){
			fileData = loadFromOldTree(fileAsInt,base); 
			if(fileData == null){
				if(base){
					Log.d(TAG,"MapLoader:  map does not exist on disk");
				}else{
					Log.d(TAG,"MapLoader:  exception map does not exist on disk");
				}
			}else{
				moveRequired = true;
			}
		}else{
			if(base){
				Log.d(TAG, String.format("Baseap %d loaded from new tree",fileAsInt));
			}else{
				Log.d(TAG, String.format("Exmap %d loaded from new tree",fileAsInt));
			}
		}
		
		return new LoadedFile(fileData, moveRequired,fileAsInt);
	}
	
	public final class LoadedFile {
		public final boolean moveRequired;
		public final byte [] data;
		public final int fileAsInt;
		public ByteBuffer mapData;
		
		public LoadedFile(byte [] data,boolean moveRequired,int fileAsInt){
			this.data = data;
			this.moveRequired = moveRequired;
			this.fileAsInt = fileAsInt;
			if(data != null){
				this.mapData = ByteBuffer.wrap(data);
				mapData.order(ByteOrder.LITTLE_ENDIAN);
			}
		}
	};
	
	/**
	 * Structure to hold all relevant details of a parsed map.
	 * Used when moving maps from one tree to another.
	 * @author jlitzinger
	 *
	 */
	final class ParsedMapContainer {
		public final SbsMap map;
		public final byte [] data;
		
		public ParsedMapContainer(SbsMap map,byte [] data){
			this.map = map;
			this.data = data;
		}
		
	};

	/**
	 * This method parses a single map from a byte array, and returns
	 * a container with the map and the raw data representing that map,
	 * later used for moving maps.
	 * 
	 * @param currentFileAsInt - current lat/long, expressed as fileAsInt
	 * @param loadedFile - the loaded file to parse a map from.
	 * @param baseVer - baseVersion if we are parsing an exception file.
	 * @return
	 */
	ParsedMapContainer parseMap(int currentFileAsInt,LoadedFile loadedFile,int baseVer){
	
		ParsedMapContainer mapContainer = null;
		SbsMap map = null;
			
		int start = loadedFile.mapData.position();
		if(baseVer == 0){
			map = SbsMap.basemapFromByteArray(loadedFile.mapData);
		}else{
			map = SbsMap.exmapFromByteArray(loadedFile.mapData,baseVer);
		}
		
		if(map == null){
			Log.wtf(TAG, String.format("We failed to parse the map %d, baseVer=%d (0 means basemap)!!!",currentFileAsInt,baseVer));
			return null;
		}
		
		/**
		 * Slice out the binary data for this map.  For single squares, this is a no-op,
		 * for concat maps, it's trickier.  If no move is required later, just stuff the
		 * loaded byte array, it won't be used anyway.
		 */
		if(SbsUtils.mapIsWithinSquares(currentFileAsInt,map.getFileAsInt(),AbstractSbsMapLoader.CONCAT_WINDOW)){
			if(loadedFile.moveRequired){
				int end = loadedFile.mapData.position();
				byte [] slicedData = null;
				if((end - start) != loadedFile.data.length){
					//Concat map, so slice out the relevant section
					slicedData = new byte[end - start];
					System.arraycopy(loadedFile.data, start, slicedData, 0, (end - start));
				}else{
					slicedData = loadedFile.data;
				}
				mapContainer = new ParsedMapContainer(map,slicedData);				
			}else{
				mapContainer = new ParsedMapContainer(map, loadedFile.data);
			}
		}else{
			Log.d(TAG,String.format("Rejecting map %d",map.getFileAsInt()));
		}
		
		return mapContainer;
	}
	
	@Override
	public List<SbsMap> call() {
		
		LoadedFile loadedFile = loadMapFile(fileAsInt, true);
		
		ArrayList<SbsMap> maps = new ArrayList<SbsMap>(1);
		SbsMap map = null;
		if(loadedFile.data == null){
			Log.d(TAG, "No map on disk, return an empty map");
			map = new SpeedBaseMap(fileAsInt);
			maps.add(map);
			return maps;
		}
		
		ParsedMapContainer container = null;
		
		//Get the basemaps
		boolean mapFound = false;
		while(loadedFile.mapData.hasRemaining()){
			if(isNewMapMarker(loadedFile.mapData)){
				loadedFile.mapData.position(loadedFile.mapData.position() + 4);
			}
			
			container = parseMap(fileAsInt, loadedFile, 0);
			if(container != null){
				maps.add(container.map);
				mapFound = true;
				if(loadedFile.moveRequired){
					writeSbsFile(prefix,container.map.getFileAsInt(), container.data, true);
				}
			}
		}
		
		if(!mapFound){
			Log.d(TAG, "Located maps didn't match, return an empty map");
			map = new SpeedBaseMap(fileAsInt);
			maps.add(map);
			return maps;
		}
		
		loadedFile = null;
		container = null;
		
		//Load the exception(s)
		//This code does not support concatenated exception files.
		Log.d(TAG, "Load exception maps");
		for(SbsMap baseMap : maps){
			loadedFile = null;
			loadedFile = loadMapFile(baseMap.getFileAsInt(), false);
			if(loadedFile.data == null){
				continue;
			}
			while(loadedFile.mapData.hasRemaining()){
				if(isNewMapMarker(loadedFile.mapData)){
					loadedFile.mapData.position(loadedFile.mapData.position() + 4);
				}
				
				container = parseMap(fileAsInt, loadedFile, baseMap.getBaselineVersion());
				if(container != null){
					baseMap.mergeMap(container.map);
					if(loadedFile.moveRequired){
						writeSbsFile(prefix,container.map.getFileAsInt(), container.data, true);
					}
				}
			}
			
		}
		
		return maps;
				
	}

}
