package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum Role
{

    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_MANAGER(2, "ROLE_MANAGER"),
    ROLE_SUPERUSER(3, "ROLE_SUPERUSER"),
    ROLE_USER(4, "ROLE_USER");

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
