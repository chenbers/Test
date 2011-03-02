package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum WirelineStatus implements BaseEnum
{
    DISABLE(0),
    ENABLE(1);
    
    Integer code;
    private WirelineStatus(Integer code)
    {
    
        this.code = code;
    }
    
    private static final Map<Integer, WirelineStatus> lookup = new HashMap<Integer, WirelineStatus>();
    static
    {
        for (WirelineStatus p : EnumSet.allOf(WirelineStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    @Override
    public Integer getCode()
    {
        return this.code;
    }
    
    public static WirelineStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

}

