package com.inthinc.pro.dao.service.mapper;

import java.util.HashMap;
import java.util.Map;

public class MapperFactory {
    
    
    private static Map<String, GenericMapper<?,?>> mapperFactory = new HashMap<String, GenericMapper<?,?>>();
    static {
        mapperFactory.put("Account", new AccountMapper());
        mapperFactory.put("Device", new DeviceMapper());
        mapperFactory.put("Trailer", new TrailerMapper());
    }
    
    private MapperFactory() {}
    
    
    
    public static GenericMapper<?,?> getMapper(String mapperType) {
        return mapperFactory.get(mapperType);
    }

}
