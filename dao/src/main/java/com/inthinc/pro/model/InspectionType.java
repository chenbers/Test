package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum InspectionType  implements BaseEnum
{
    NONE(0),
    PRETRIP(1), 
    POSTTRIP(2), 
    NO_PRETRIP(4),
    NO_POSTTRIP(8);

    private Integer code;

    private InspectionType(Integer code)
    {
        this.code = code;
    }

    private static final Map<Integer, InspectionType> lookup = new HashMap<Integer, InspectionType>();
    static
    {
        for (InspectionType p : EnumSet.allOf(InspectionType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static InspectionType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
