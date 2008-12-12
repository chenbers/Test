package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GroupType implements BaseEnum
{
    FLEET(1, "Fleet"), 
    DIVISION(2, "Division"), 
    TEAM(3, "Team");

    private Integer code;
    private String description;

    private GroupType(Integer code, String description)
    {
        this.description = description;
        this.code = code;
    }

    private static final Map<Integer, GroupType> lookup = new HashMap<Integer, GroupType>();
    static
    {
        for (GroupType p : EnumSet.allOf(GroupType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GroupType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
