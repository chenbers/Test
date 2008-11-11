package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RedFlagLevel implements BaseEnum
{

    CRITICAL(1, "CRITICAL", "critical"),
    INFO(2, "INFO", "info"),
    WARNING(3, "WARNING", "warning");

    private String description;
    private int code;
    private String style;

    private RedFlagLevel(int code, String description, String style)
    {
        this.code = code;
        this.description = description;
        this.style = style;
    }

    private static final Map<Integer, RedFlagLevel> lookup = new HashMap<Integer, RedFlagLevel>();
    static
    {
        for (RedFlagLevel p : EnumSet.allOf(RedFlagLevel.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public String getStyle()
    {
        return this.style;
    }

    public static RedFlagLevel valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}


