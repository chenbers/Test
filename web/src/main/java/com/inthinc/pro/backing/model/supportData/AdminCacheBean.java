package com.inthinc.pro.backing.model.supportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCacheBean {
    
    private Map<String, CacheItemMap<?,?>> mapOfAssetMaps;
    
    public Object getAsset(String assetType, Integer assetID) throws IllegalArgumentException{
        
        CacheItemMap<? ,? > map = mapOfAssetMaps.get(assetType);
        if (map != null){
            return map.getItem(assetID);
        }
        throw new IllegalArgumentException();
    }
    public List<?> getAssets(String assetType){
        
        return (List<?>) mapOfAssetMaps.get(assetType).getItems();
    }
    
    public CacheItemMap<? ,? > getMap(String assetType){
        return mapOfAssetMaps.get(assetType);
    }
    public void addAssetMap(String key, CacheItemMap<?,?> assetMap){
        mapOfAssetMaps.put(key, assetMap);
    }
    public AdminCacheBean() {
        mapOfAssetMaps = new HashMap<String,CacheItemMap<?,?>>();
    }
}
