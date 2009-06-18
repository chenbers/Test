package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum MeasurementType implements BaseEnum
{
    ENGLISH(1), METRIC(2);
    
    private int code;
    
    private MeasurementType(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, MeasurementType> lookup = new HashMap<Integer, MeasurementType>();
    static
    {
        for (MeasurementType type : EnumSet.allOf(MeasurementType.class))
        {
            lookup.put(type.code, type);
        }
    }
    
    public static MeasurementType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public Integer getCode()
    {
        return this.code;
    }
}