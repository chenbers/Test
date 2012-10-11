package com.inthinc.pro.dao.mock.data;

import java.util.HashMap;
import java.util.List;
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
    public void addKeyValueInList(String key, List<Object> valueList)
    {
        searchCriteriaMap.put(key, new ValueList(valueList));
    }
    public Map<String, Object> getCriteriaMap()
    {
        return searchCriteriaMap;
    }
}
