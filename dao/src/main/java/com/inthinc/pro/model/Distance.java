package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Distance
{
    FIVEHUNDRED     ("500mi", 500), 
    ONETHOUSAND     ("1,000mi", 1000), 
    FIVETHOUSAND    ("5,000mi", 5000), 
    TENTHOUSAND     ("10,000mi", 10000);

    private final int numberOfMiles;
    private String distanceValue;
    private static final Map<Integer, Distance> lookup = new HashMap<Integer, Distance>();
    
    static
    {
        for(Distance d : EnumSet.allOf(Distance.class))
            lookup.put(d.getNumberOfMiles(), d);
    }

    Distance(String distanceValue, int numberOfMiles)
    {
        this.distanceValue = distanceValue;
        this.numberOfMiles = numberOfMiles;
    }

    public int getNumberOfMiles()
    {
        return this.numberOfMiles;
    }
    
    public static Distance getDistanceByDays(int numberOfMiles)
    {
        return lookup.get(numberOfMiles);
    }
    
    @Override
    public String toString()
    {
        return this.distanceValue;
    }
}
