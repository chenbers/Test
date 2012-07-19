package com.inthinc.device.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OffOn implements OptionValue {
    OFF(0, "OFF"),
    ON(1, "ON");
    
    private int code;
    private String name;
    
    private OffOn(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    private static final Map<Integer, OffOn> lookup = new HashMap<Integer, OffOn>();
    static
    {
        for (OffOn p : EnumSet.allOf(OffOn.class))
        {
            lookup.put(p.code, p);
        }
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
    public String getName() {
        return name;
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
        return this == ON;
    }
}
