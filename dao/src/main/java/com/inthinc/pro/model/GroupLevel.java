package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GroupLevel implements BaseEnum
{
	FLEET(1, "FLEET"),
	DIVISION(2, "DIVISION"),
	TEAM(3, "TEAM");
	
    private String description;
    private int code;

    private GroupLevel(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, GroupLevel> lookup = new HashMap<Integer, GroupLevel>();
    static
    {
        for (GroupLevel p : EnumSet.allOf(GroupLevel.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GroupLevel valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

}
