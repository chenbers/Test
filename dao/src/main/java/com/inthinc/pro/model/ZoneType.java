package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;

public enum ZoneType implements BaseEnum
{
    NONE(0, "NONE"), CIRCLE(1, "CIRCLE"), POLYGON(2, "POLYGON"), RECTANGLE(3, "RECTANGLE");

    private Integer code;
    private String  name;

    private ZoneType(Integer code, String name)
    {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    private static final HashMap<Integer, ZoneType> lookup = new HashMap<Integer, ZoneType>();
    static
    {
        for (ZoneType z : EnumSet.allOf(ZoneType.class))
        {
            lookup.put(z.code, z);
        }
    }

    public static ZoneType valueOf(Integer code)
    {
        return lookup.get(code);
    }
}
