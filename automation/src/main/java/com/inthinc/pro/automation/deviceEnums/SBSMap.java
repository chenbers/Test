package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;
import java.util.HashMap;


public enum SBSMap {
    
    BASE_LINE("b", Integer.class),
    VERSION("v", String.class),
    FILE("f", new byte[]{}.getClass()),
    FIRST_FOLDER("d1", String.class),
    SECOND_FOLDER("d2", String.class),
    FILE_NAME("n", String.class),
    
    ;
    
    private String key;
    private Class<?> clazz;
    
    private SBSMap(String key, Class<?> type){
        this.key = key;
        this.clazz = type;
    }
    
    
    private static HashMap<String, Class<?>> lookupByCode = new HashMap<String, Class<?>>();
    
    static {
        for (SBSMap p : EnumSet.allOf(SBSMap.class))
        {
            lookupByCode.put(p.key, p.clazz);
        }
    }
    
    public static Class<?> classByKey(String key){
        return lookupByCode.get(key);
    }
    

}
