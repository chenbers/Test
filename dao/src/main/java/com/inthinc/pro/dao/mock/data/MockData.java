package com.inthinc.pro.dao.mock.data;


import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.User;

public class MockData
{
    Map<Class, Map<String, Map <Object, Map<String, Object>>>> lookupMaps; 
    
    public MockData()
    {
        lookupMaps = new HashMap<Class, Map<String, Map <Object, Map<String, Object>>>>();
        lookupMaps.put(User.class, new UserMockData().getLookupMaps());
    }
    
    public Map<String, Object> lookup(Class clas, String primaryKey, Object searchValue)
    {
        Map<String, Map <Object, Map<String, Object>>> classLookupMap = lookupMaps.get(clas);
        
        Map <Object, Map<String, Object>> lookupMap = classLookupMap.get(primaryKey);
        
        return lookupMap.get(searchValue);
    }
    
}
