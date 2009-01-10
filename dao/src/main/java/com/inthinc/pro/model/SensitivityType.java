package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SensitivityType implements BaseEnum
{
    HARD_ACCEL_SETTING(1, "HARD_ACCEL_SETTING"),
    HARD_BRAKE_SETTING(2, "HARD_BRAKE_SETTING"),
    HARD_TURN_SETTING(3, "HARD_TURN_SETTING"),
    HARD_VERT_SETTING(4, "HARD_VERT_SETTING");

    private String description;
    private int code;

    private SensitivityType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, SensitivityType> lookup = new HashMap<Integer, SensitivityType>();
    static
    {
        for (SensitivityType p : EnumSet.allOf(SensitivityType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static SensitivityType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

    public String getDescription()
    {
        return description;
    }

}

