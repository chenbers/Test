package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum VersionState  implements BaseEnum, EventAttrEnum {
    UNKNOWN(0),
    UPDATED(1),
    CURRENT(2),
    SERVER_OLDER(3),
    NOT_UPDATED(4);
    
    
    private Integer code;
    
    private VersionState(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    
    private static final Map<Integer, VersionState> lookup = new HashMap<Integer, VersionState>();
    static
    {
        for (VersionState p : EnumSet.allOf(VersionState.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static VersionState valueOf(Integer code)
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

    @Override
    public EventAttrEnum mapFromInteger(Integer value) {
        return valueOf(value);
    }

    @Override
    public Integer mapToInteger() {
        return code;
    }

}
