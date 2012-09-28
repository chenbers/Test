package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum PhoneNumberType implements BaseEnum
{
    WORK(1, "WORK"), HOME(2, "HOME"), CELL(3, "CELL"), OTHER(4, "OTHER");

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

    public static PhoneNumberType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
