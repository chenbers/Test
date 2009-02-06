package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Duration implements BaseEnum
{
    DAYS(1, "30 days", 30, "dd", 0, 30), 
    THREE(3, "3 months", 90, "MMM", 2, 3), 
    SIX(4, "6 months", 180, "MMM", 2, 6), 
    TWELVE(5, "12 months", 360, "MMM", 2, 12);

    private final int numberOfDays;
    private String durationValue;
    private Integer code;
    private String datePattern;
    private Integer dvqMetric;
    private Integer dvqCount;
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
    Duration(Integer code, String durationValue, int numberOfDays, String datePattern, Integer dvqMetric, Integer dvqCount)
    {
        this.code = code;
        this.durationValue = durationValue;
        this.numberOfDays = numberOfDays;
        this.datePattern = datePattern;
        this.dvqMetric = dvqMetric;
        this.dvqCount = dvqCount;
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
       
    public String getDatePattern()
    {
        return this.datePattern;
    }

    public Integer getDvqMetric()
    {
        return dvqMetric;
    }

    public Integer getDvqCount()
    {
        return dvqCount;
    }

}
