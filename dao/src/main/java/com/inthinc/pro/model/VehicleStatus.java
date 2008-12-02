package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VehicleStatus implements BaseEnum
{
    ACTIVE(1, "ACTIVE"), DISABLED(2, "DISABLED"), DELETED(3, "DELETED");

    private int    code;
    private String description;

    private VehicleStatus(int code, String description)
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

    private static final Map<Integer, VehicleStatus> lookup = new HashMap<Integer, VehicleStatus>();
    static
    {
        for (VehicleStatus p : EnumSet.allOf(VehicleStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static VehicleStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }
}

