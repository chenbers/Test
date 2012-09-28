package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ReportMileageType implements BaseEnum
{

    MILEAGE_500(1, 500, 100, 5),
    MILEAGE_1000(2, 1000, 100, 10),
    MILEAGE_5000(3, 5000, 500, 10),
    MILEAGE_10000(4, 10000, 1000, 10);

    private int code;
    private int miles;
    private int milesPerBin;
    private int binCount;
    
    private ReportMileageType(int code, int miles, int milesPerBin, int binCount)
    {
        this.code = code;
        this.miles = miles;
        this.milesPerBin = milesPerBin;
        this.binCount = binCount;
    }

    private static final Map<Integer, ReportMileageType> lookup = new HashMap<Integer, ReportMileageType>();
    static
    {
        for (ReportMileageType p : EnumSet.allOf(ReportMileageType.class))
        {
            lookup.put(p.miles, p);
        }
    }
    public static ReportMileageType valueOf(Integer miles)
    {
        return lookup.get(miles);
    }

    public Integer getCode()
    {
        return this.code;
    }

    public int getMiles()
    {
        return miles;
    }

    public void setMiles(int miles)
    {
        this.miles = miles;
    }

    public int getMilesPerBin()
    {
        return milesPerBin;
    }

    public void setMilesPerBin(int milesPerBin)
    {
        this.milesPerBin = milesPerBin;
    }

    public int getBinCount()
    {
        return binCount;
    }

    public void setBinCount(int binCount)
    {
        this.binCount = binCount;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
    
    

}

