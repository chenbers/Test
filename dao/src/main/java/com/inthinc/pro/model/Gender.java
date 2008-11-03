package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Gender implements BaseEnum
{
    MALE(1, "Male"), FEMALE(2, "Female");

    private int    code;
    private String description;

    private Gender(int code, String description)
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

    private static final Map<Integer, Gender> lookup = new HashMap<Integer, Gender>();
    static
    {
        for (Gender p : EnumSet.allOf(Gender.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static Gender getGender(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
