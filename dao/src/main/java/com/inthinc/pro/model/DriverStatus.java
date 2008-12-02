package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DriverStatus implements BaseEnum
{
    ACTIVE(1, "ACTIVE"), DISABLED(2, "DISABLED"), DELETED(3, "DELETED");

    private int    code;
    private String description;

    private DriverStatus(int code, String description)
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

    private static final Map<Integer, DriverStatus> lookup = new HashMap<Integer, DriverStatus>();
    static
    {
        for (DriverStatus p : EnumSet.allOf(DriverStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static DriverStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }
}

