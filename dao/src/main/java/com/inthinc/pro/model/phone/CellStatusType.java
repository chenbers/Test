package com.inthinc.pro.model.phone;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

/**
 * Cell phone status enumeration.
 */
public enum CellStatusType implements BaseEnum{
    DISABLED(0),
    ENABLED(1);
    
    Integer code;
    private CellStatusType(Integer code)
    {
    
        this.code = code;
    }
    
    private static final Map<Integer, CellStatusType> lookup = new HashMap<Integer, CellStatusType>();
    static
    {
        for (CellStatusType p : EnumSet.allOf(CellStatusType.class))
        {
            lookup.put(p.code, p);
        }
    }

    @Override
    public Integer getCode()
    {
        return this.code;
    }
    
    public static CellStatusType valueOf(Integer code)
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
