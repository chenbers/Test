package com.inthinc.device.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OffOnDevice implements OptionValue {
    OFF(0, "OFF"),
    DEVICE(2, "DEVICE");
    
    private int code;
    private String name;
    
    private OffOnDevice(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    private static final Map<Integer, OffOnDevice> lookup = new HashMap<Integer, OffOnDevice>();
    static
    {
        for (OffOnDevice p : EnumSet.allOf(OffOnDevice.class))
        {
            lookup.put(p.code, p);
        }
    }

    
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public Integer getValue() {
        return getCode();
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
    static public OptionValue valueOf(Integer value) {
        return lookup.get(value);
    }
    
    @Override
    public Boolean getBooleanValue() {
        return this == DEVICE;
    }

}
