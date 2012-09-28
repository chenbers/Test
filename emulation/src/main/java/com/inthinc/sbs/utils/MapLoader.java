package com.inthinc.sbs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.inthinc.device.emulation.utils.EmulationSBSDownloadManager;
import com.inthinc.sbs.Sbs;
import com.inthinc.sbs.regions.SbsMap;
import com.inthinc.sbs.regions.SpeedBaseMap;

public class MapLoader extends AbstractSbsMapLoader {

    public static final ThreadLocal<EmulationSBSDownloadManager> manager = new ThreadLocal<EmulationSBSDownloadManager>();
    public final String prefix;
    private final int fileAsInt;
    private final SbsFileWriter writer;
    
    
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
    
    public MapLoader(){
        prefix = "/sdcard";
        this.fileAsInt = 0;
        this.writer = new SbsFileWriterImpl();
    }
    
    public MapLoader(String prefix,int fileAsInt){
        this.prefix = prefix;
        this.fileAsInt = fileAsInt;
        this.writer = new SbsFileWriterImpl();
    }
    
    public MapLoader(String prefix,int fileAsInt,SbsFileWriter writer){
        this.prefix = prefix;
        this.fileAsInt = fileAsInt;
        this.writer = writer;
    }
    
    public byte [] loadFromNewTree(StringBuilder builder,int fileAsInt,boolean base){
        
        if(!SbsUtils.createMapFilename(builder,prefix,base,fileAsInt,false)){
            Log.e(Sbs.TAG,"Error creating new filename");
            return null;
        }
        
        Log.e("Manager is: %s", manager.get().toString());
        
        if (manager.get() != null){
            File file = new File(builder.toString());
            if (!file.exists()){
                if (base){
                    manager.get().getSbsBase(fileAsInt, 7);
                } else {
                    manager.get().checkSbsEditNG(fileAsInt, 7, 0);
                }
            }
        }
        
        return AbstractSbsMapLoader.loadFile(builder.toString());
    }
    
    public byte [] loadFromOldTree(StringBuilder builder,int fileAsInt,boolean base){
        
        if(!SbsUtils.createMapFilename(builder,prefix,base,fileAsInt, true)){
            Log.e(Sbs.TAG,"Error creating new filename");
            return null;
        }
        
        String fname = builder.toString();
        byte [] data = AbstractSbsMapLoader.loadFile(fname);
        if(data == null){
            //Look for a concatenated file 
            fname = findConcatFile(fname,fileAsInt);
            if(fname != null){
                data = AbstractSbsMapLoader.loadFile(fname);
            }
        }
        
        return data;
    }
    
    /**
     * Load a map file from disk.
     * @param fileAsInt - map to load
     * @param base - baseline of the map.
     * @return LoadedFile wrapping the loaded map data.  data member will
     * be null if no map was loaded.
     */
    public LoadedFile loadMapFile(StringBuilder builder,int fileAsInt, boolean base){
        byte [] fileData = null;
        boolean moveRequired = false;
        
        if(base){
            Log.i(Sbs.TAG, String.format("Attempting to load basemap %d",fileAsInt));
        }else{
            Log.i(Sbs.TAG, String.format("Attempting to load exmap %d",fileAsInt));
        }
        
        fileData = loadFromNewTree(builder,fileAsInt,base);
        if(fileData == null
                && base){ //Exception maps never exist in the old tree.
            fileData = loadFromOldTree(builder,fileAsInt,base); 
            if(fileData == null){
                if(base){
                    Log.d(Sbs.TAG,String.format("MapLoader:  map %d does not exist on disk",fileAsInt));
                }else{
                    Log.d(Sbs.TAG,String.format("MapLoader:  exception map %d does not exist on disk",fileAsInt));
                }
            }else{
                moveRequired = true;
            }
        }else{
            if(base){
                Log.d(Sbs.TAG, String.format("Baseap %d loaded from new tree",fileAsInt));
            }else if(fileData != null){
                Log.d(Sbs.TAG, String.format("Exmap %d loaded from new tree",fileAsInt));
            }
        }
        
        return new LoadedFile(fileData, moveRequired,fileAsInt);
    }
    


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
            Log.wtf(Sbs.TAG, String.format("We failed to parse the map %d, baseVer=%d (0 means basemap)!!!",currentFileAsInt,baseVer));
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
        }
        
        return mapContainer;
    }
    
    @Override
    public List<SbsMap> call() {
        
        StringBuilder builder = new StringBuilder(256);
        LoadedFile loadedFile = loadMapFile(builder,fileAsInt, true);
        
        ArrayList<SbsMap> maps = new ArrayList<SbsMap>(1);
        SbsMap map = null;
        if(loadedFile.data == null){
            Log.i(Sbs.TAG, "No map on disk, return an empty map");
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
                    writer.writeSbsFile(prefix,container.map.getFileAsInt(), container.data, true);
                }
            }
        }
        
        if(!mapFound){
            Log.i(Sbs.TAG, "Located maps didn't match, return an empty map");
            map = new SpeedBaseMap(fileAsInt);
            maps.add(map);
            return maps;
        }
        
        loadedFile = null;
        container = null;
        
        //Load the exception(s)
        //This code does not support concatenated exception files.
        for(SbsMap baseMap : maps){
            loadedFile = null;
            loadedFile = loadMapFile(builder,baseMap.getFileAsInt(), false);
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
                        writer.writeSbsFile(prefix,container.map.getFileAsInt(), container.data, true);
                    }
                }
            }
            
        }
        return maps;
                
    }

}
