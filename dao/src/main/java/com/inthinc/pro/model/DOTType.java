package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DOTType
{

    DOT_NONE(0, "DOT_NONE");
    // TODO: Add other type -- see Gain for supported list

    private String description;
    private int code;

    private DOTType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, DOTType> lookup = new HashMap<Integer, DOTType>();
    static
    {
        for (DOTType p : EnumSet.allOf(DOTType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static DOTType getDOTType(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
