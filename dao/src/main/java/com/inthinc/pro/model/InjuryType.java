package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum InjuryType implements BaseEnum{
    
    APPARENT_BROKEN_BONES(0),
    POSSIBLE_INTERNAL_INJURY(1),
    SEVERE_LACERATION(2),
    MINOR_INJURY(3),
    LOSS_OF_TEETH(4),
    UNCONSCIOUS(5),
    OTHER_MAJOR_INJURY(6),
    NONE(7);
    
    int code;
    
    private InjuryType(int code){
        this.code = code;
    }
    
    @Override
    public Integer getCode() {
        return this.code;
    }
    
    private static final Map<Integer, InjuryType> lookup = new HashMap<Integer, InjuryType>();
    static
    {
        for (InjuryType p : EnumSet.allOf(InjuryType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static InjuryType valueOf(Integer code)
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
