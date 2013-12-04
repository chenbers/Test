package com.inthinc.pro.dao.service.mapper;

import java.util.HashMap;
import java.util.Map;

public class MapperFactory {
    
    
    private static Map<String, GenericMapper<?,?>> mapperFactory = new HashMap<String, GenericMapper<?,?>>();
    static {
        mapperFactory.put("Account", new AccountMapper());
        mapperFactory.put("Device", new DeviceMapper());
        mapperFactory.put("Driver", new DriverMapper());
        mapperFactory.put("Group", new GroupMapper());
        mapperFactory.put("Trailer", new TrailerMapper());
        mapperFactory.put("Vehicle", new VehicleMapper());
    }
    
    private MapperFactory() {}
    
    
    
    public static GenericMapper<?,?> getMapper(String mapperType) {
        return mapperFactory.get(mapperType);
    }

}
