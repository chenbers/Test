package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum RedFlagLevel implements BaseEnum
{
    NONE(0, "none"),
    CRITICAL(1, "critical"),
    WARNING(2, "warning"),
    INFO(3, "info");

    private String description;
    private int code;

    private RedFlagLevel(int code, String description)
    {
        this.code = code;
        this.description = description;
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

    public static RedFlagLevel valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public String getDescription()
    {
        return description;
    }
}