package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserStatus implements BaseEnum
{
    ACTIVE(1, "ACTIVE"), DISABLED(2, "DISABLED"), DELETED(3, "DELETED");

    private int    code;
    private String description;

    private UserStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    private static final Map<Integer, UserStatus> lookup = new HashMap<Integer, UserStatus>();
    static
    {
        for (UserStatus p : EnumSet.allOf(UserStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static UserStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }
}

