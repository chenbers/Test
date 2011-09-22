package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inthinc.pro.automation.interfaces.MCMProxy;

public class SpeedByStreet {
    
    private Map<Integer, Map<String, Object>> maps;
    
    
    public SpeedByStreet(){
        maps = new TreeMap<Integer, Map<String, Object>>();
    }
    
    public SpeedByStreet addNewMap(Map<String, Object> mapSegment){
        maps.put((Integer) mapSegment.get("f"), mapSegment);
        return this;
    }

    public SpeedByStreet addMaps(List<Map<String, Object>> listOfMaps) {
        for (Map<String, Object> map: listOfMaps){
            addNewMap(map);
        }
        return this;
    }
    
    private class MapSection{
        
        private final String name;
        private final String firstFolder;
        private final String secondFolder;
        private final byte[] file;
        private final Integer fileNumber;
        
        private Integer version;
        
        
        public MapSection(Map<String, Object> mapSegment){
            name = (String) mapSegment.get("n");
            firstFolder = (String) mapSegment.get("d1");
            secondFolder = (String) mapSegment.get("d2");
            file = (byte[]) mapSegment.get("f");
            fileNumber = Integer.parseInt(name.split(".")[0]);
            version = (Integer) mapSegment.get("v");
        }
        
        public MapSection(Integer fileNumber, Integer vesion, MCMProxy proxy){
            this(proxy.getSbsEdit("", new HashMap<String, Object>()));
        }
    }

}
