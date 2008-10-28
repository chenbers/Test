package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PhoneNumberType implements BaseEnum
{
    WORK(1, "Work"), HOME(2, "Home"), CELL(3, "Cell"), OTHER(4, "Other");

    private String description;
    private int    code;

    private PhoneNumberType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, PhoneNumberType> lookup = new HashMap<Integer, PhoneNumberType>();
    static
    {
        for (PhoneNumberType p : EnumSet.allOf(PhoneNumberType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static PhoneNumberType getPhoneNumberType(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
