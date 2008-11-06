package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccountStatus implements BaseEnum
{
    ACCOUNT_ACTIVE(1, "ACCOUNT_ACTIVE"),
    ACCOUNT_DISABLED(2, "ACCOUNT_DISABLED"),
    ACCOUNT_DELETED(3, "ACCOUNT_DELETED");

    private String description;
    private int code;

    private AccountStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AccountStatus> lookup = new HashMap<Integer, AccountStatus>();
    static
    {
        for (AccountStatus p : EnumSet.allOf(AccountStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static AccountStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

}
