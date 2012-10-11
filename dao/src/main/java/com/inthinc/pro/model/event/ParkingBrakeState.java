package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum ParkingBrakeState  implements BaseEnum, EventAttrEnum {
    PARKED(0),
    DRIVING(1);
    
    private Integer code;
    
    private ParkingBrakeState(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    
    private static final Map<Integer, ParkingBrakeState> lookup = new HashMap<Integer, ParkingBrakeState>();
    static
    {
        for (ParkingBrakeState p : EnumSet.allOf(ParkingBrakeState.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static ParkingBrakeState valueOf(Integer code)
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
        return getCode();
    }
}
