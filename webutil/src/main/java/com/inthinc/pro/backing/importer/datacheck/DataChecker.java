package com.inthinc.pro.backing.importer.datacheck;


public class DataChecker {
    
    protected DataCache dataCache;
    
    public String checkForErrors(String... data)
    {
        return null;
    }
    public String checkForWarnings(String... data)
    {
        return null;
    }

    public DataCache getDataCache() {
        return dataCache;
    }
    public void setDataCache(DataCache dataCache) {
        this.dataCache = dataCache;
    }
}
