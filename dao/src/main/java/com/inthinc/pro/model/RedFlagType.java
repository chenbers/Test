package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RedFlagType implements BaseEnum
{
    SPEED(1, "SPEED"), DRIVING_STYLE(2, "DRIVING_STYLE"), SAFETY(3, "SAFETY"), DIAGNOSTICS(4, "DIAGNOSTICS"), EFFICIENCY(5, "EFFICIENCY"), HOURS(6, "HOURS");

    private int    code;
    private String description;

    private RedFlagType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public String getDescription()
    {
        return description;
    }

    private static final Map<Integer, RedFlagType> lookup = new HashMap<Integer, RedFlagType>();
    static
    {
        for (RedFlagType p : EnumSet.allOf(RedFlagType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static RedFlagType valueOf(Integer code)
    {
        return lookup.get(code);
    }
}
