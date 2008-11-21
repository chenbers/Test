package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Duration implements BaseEnum
{
    DAYS(1, "30 days", 30), THREE(2, "3 months", 90), SIX(3, "6 months", 180), TWELVE(4, "12 months", 360);

    private final int numberOfDays;
    private String durationValue;
    private Integer code;
    private static final Map<Integer, Duration> lookup = new HashMap<Integer, Duration>();
    
    static
    {
        for(Duration d : EnumSet.allOf(Duration.class))
            lookup.put(d.getNumberOfDays(), d);
    }

    private static final Map<Integer, Duration> codeLookup = new HashMap<Integer, Duration>();
    
    static
    {
        for(Duration d : EnumSet.allOf(Duration.class))
            codeLookup.put(d.getCode(), d);
    }
    Duration(Integer code, String durationValue, int numberOfDays)
    {
        this.code = code;
        this.durationValue = durationValue;
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfDays()
    {
        return this.numberOfDays;
    }
    
    public static Duration getDurationByDays(int numberOfDays)
    {
        return lookup.get(numberOfDays);
    }
    
    @Override
    public String toString()
    {
        return this.durationValue;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }
    
    public static Duration valueOf(Integer code)
    {
        return codeLookup.get(code);
    }

}
