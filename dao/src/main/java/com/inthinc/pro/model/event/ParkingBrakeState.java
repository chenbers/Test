package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum ParkingBrakeState  implements BaseEnum {
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


}
