package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum DOTStoppedState  implements BaseEnum {
    UNKNOWN(0),
    NO_HOURS(1),
    UNSAFE_VEHICLE(2),
    CARGO(3),
    LICENSE(4),
    DOT_INSPECTION(5);
    
    private Integer code;
    
    private DOTStoppedState(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    
    private static final Map<Integer, DOTStoppedState> lookup = new HashMap<Integer, DOTStoppedState>();
    static
    {
        for (DOTStoppedState p : EnumSet.allOf(DOTStoppedState.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static DOTStoppedState valueOf(Integer code)
    {
        return lookup.get(code);
    }


}
