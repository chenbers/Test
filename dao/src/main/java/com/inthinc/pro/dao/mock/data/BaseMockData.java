package com.inthinc.pro.dao.mock.data;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseMockData
{
    public BaseMockData()
    {
        
    }
    
    protected abstract Map<String, Map <Object, Map<String, Object>>>  getLookupMaps();
    
    protected Map<String, Map <Object, Map<String, Object>>> initData(Data data)
    {
        String primaryKeys[] = data.getPrimaryKeys(); 
        int numPrimary = primaryKeys.length;
        Map<String, Map <Object, Map<String, Object>>> lookupMaps = new HashMap <String, Map <Object, Map<String, Object>>>();
        for (int i = 0; i < data.getSize(); i++)
        {
            Map<String,Object> userMap = data.getMap(i);
            
            for (int j = 0; j < numPrimary; j++)
            {
                Map <Object, Map<String, Object>> lookupMap = lookupMaps.get(primaryKeys[j]);
                if (lookupMap == null)
                {
                    lookupMap = new HashMap<Object, Map<String, Object>>();
                    lookupMaps.put(primaryKeys[j], lookupMap);
                }
                lookupMap.put(userMap.get(primaryKeys[j]), userMap);
                
            }
        }
        
        return lookupMaps;
    }
    
    public class Data 
    {
        String[] primaryKeys;
        String[] keys;
        Object[][] values;
        public Data(String[] primaryKeys, String[] keys, Object[][] values)
        {
            this.primaryKeys = primaryKeys;
            this.keys = keys;
            this.values = values;
        }
        
        public String[] getPrimaryKeys()
        {
            return primaryKeys;
        }

        public void setPrimaryKeys(String[] primaryKeys)
        {
            this.primaryKeys = primaryKeys;
        }

        public int getSize()
        {
            return values.length;
        }
        
        public Map<String,Object> getMap(int idx)
        {
            if (idx >= values.length)
            {
                return null;
            }
            Map<String, Object> returnMap  = new HashMap<String, Object>();
            int valueIdx = 0;
            for (int i = 0; i < primaryKeys.length; i++)
            {
                returnMap.put(primaryKeys[i], values[idx][valueIdx++]);
            }
            for (int i = 0; i < keys.length; i++)
            {
                returnMap.put(keys[i], values[idx][valueIdx++]);
            }
            return returnMap;
        }
    }

}
