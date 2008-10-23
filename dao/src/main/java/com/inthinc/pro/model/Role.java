package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum Role implements BaseEnum
{

    ROLE_READONLY(1, "ROLE_READONLY"),
    ROLE_NORMAL_USER(2, "ROLE_NORMAL_USER"),
    ROLE_SUPERVISOR(3, "ROLE_SUPERVISOR"),
    ROLE_CUSTOM_USER(4, "ROLE_CUSTOM_USER"),
    ROLE_SUPER_USER(5, "ROLE_SUPER_USER");

    private String description;
    private int code;

    private Role(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, Role> lookup = new HashMap<Integer, Role>();
    static
    {
        for (Role p : EnumSet.allOf(Role.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static Role getRole(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
