package com.inthinc.pro.dao.mock.data;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria
{
    Map<String, Object> searchCriteriaMap = new HashMap<String, Object>();
    
    public void addKeyValue(String key, Object value)
    {
        searchCriteriaMap.put(key, value);
    }
    public void addKeyValueRange(String key, Comparable low, Comparable high)
    {
        searchCriteriaMap.put(key, new ValueRange(low, high));
    }
    public Map<String, Object> getCriteriaMap()
    {
        return searchCriteriaMap;
    }
}
