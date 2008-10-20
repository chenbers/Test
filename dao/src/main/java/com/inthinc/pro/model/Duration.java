package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Duration
{
    DAYS("thirty days", 30), THREE("three months", 3), SIX("six months", 6), TWELVE("year", 12);

    private final int numberOfDays;
    private String durationValue;
    private static final Map<Integer, Duration> lookup = new HashMap<Integer, Duration>();
    
    static
    {
        for(Duration d : EnumSet.allOf(Duration.class))
            lookup.put(d.getNumberOfDays(), d);
    }

    Duration(String durationValue, int numberOfDays)
    {
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
}
