package com.inthinc.device.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ZoneVehicleType implements OptionValue {
    ALL(0, "ALL"),
    LIGHT(1, "LIGHT"),
    HEAVY(2, "HEAVY");
    
    private int code;
    private String name;
    private ZoneVehicleType(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    private static final Map<Integer, ZoneVehicleType> lookup = new HashMap<Integer, ZoneVehicleType>();
    static
    {
        for (ZoneVehicleType p : EnumSet.allOf(ZoneVehicleType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Integer getValue() {
        return getCode();
    }
    @Override
    public String toString() {
        return name;
    }
    static public OptionValue valueOf(Integer value) {
        return lookup.get(value);
    }
    
    @Override
    public Boolean getBooleanValue() {
        return null;
    }

}
