package com.inthinc.pro.automation.device_emulation;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

}
