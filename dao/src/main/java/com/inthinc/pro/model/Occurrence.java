package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Occurrence implements BaseEnum
{
    DAILY(0,"Daily"),
    DAILY_CUSTOM(1,"Daily - Customizable"),
    WEEKLY(2,"Weekly"),
    MONTHLY(3,"Monthly");
    
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

    public String getDescription()
    {
        return description;
    }
    
    @Override
    public String toString()
    {
        return this.description;
    }
}
