package com.inthinc.pro.configurator.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SliderType{

	UNKNOWN(0),
    HARD_ACCEL_SLIDER (1),
    HARD_BRAKE_SLIDER (2),
    HARD_TURN_SLIDER  (3),
    HARD_BUMP_SLIDER  (4);
    
    private Integer code;
    

    private SliderType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    private static final Map<Integer, SliderType> lookup = new HashMap<Integer, SliderType>();
    static
    {
        for (SliderType p : EnumSet.allOf(SliderType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static SliderType valueOf(Integer code)
    {
        return lookup.get(code);
    }
}
