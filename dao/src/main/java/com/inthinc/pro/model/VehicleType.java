package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;

public enum VehicleType implements BaseEnum
{
    LIGHT(0, "LIGHT"), MEDIUM(1, "MEDIUM"), HEAVY(2, "HEAVY");

    private int    code;
    private String description;

    private VehicleType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    private static final HashMap<Integer, VehicleType> lookup = new HashMap<Integer, VehicleType>();
    static
    {
        for (VehicleType p : EnumSet.allOf(VehicleType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static VehicleType valueOf(Integer code)
    {
        return lookup.get(code);
    }
}
