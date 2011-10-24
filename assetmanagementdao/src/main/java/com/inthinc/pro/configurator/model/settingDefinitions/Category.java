package com.inthinc.pro.configurator.model.settingDefinitions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Category
{
    VEHICLE(0, "Vehicle"), HOS(1, "Hours of Service"), THRESHOLDS(2, "Thresholds"),
    NOTIFICATIONS(3,"Notifications"), GEOFENCE(4, "Geofence"), ALARMS(5,"Alarms"),
    DMM_CRASH(6, "Dmm and Crash"), REMOTEUPDATE(7, "Remote Update"), COMM(8, "Communication"),
    DEVICE(9,"Device");

    private int code;
    private String name;
    
    private static final Map<Integer, Category> lookup = new HashMap<Integer, Category>();
    static
    {
        for (Category p : EnumSet.allOf(Category.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static Category valueOf(Integer code)
    {
        return lookup.get(code);
    }

    Category(int code, String name){
        
        this.code = code;
        this.name = name;
    }
    public Integer getCode(){
        
        return code;
    }
    public String getName(){
        
        return name;
    }
}

