package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum DamageType implements BaseEnum{
    
    VEHICLE_TOWED(1),
    ROLLOVER(2),
    UNDER_CAR(3),
    TOTALED(4),
    UNKNOWN(5);
    
    int code;
    
    private DamageType(int code){
        this.code = code;
    }
    
    @Override
    public Integer getCode() {
        return this.code;
    }
    
    private static final Map<Integer, DamageType> lookup = new HashMap<Integer, DamageType>();
    static
    {
        for (DamageType p : EnumSet.allOf(DamageType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static DamageType valueOf(Integer code)
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
