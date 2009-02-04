package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Occurrence
{
    EVERY_WEEEK(0,"Once a week"),
    EVERY_OTHER(1,"Every other week"),
    ONCE_MONTH(2,"Once a Month");
    
    private Integer code;
    private String description;
    
    private Occurrence(Integer code,String description)
    {
        this.code = code;
        this.description = description;
    }
    
    private static final Map<Integer, Occurrence> lookup = new HashMap<Integer, Occurrence>();
    static
    {
        for (Occurrence p : EnumSet.allOf(Occurrence.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static Occurrence valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
