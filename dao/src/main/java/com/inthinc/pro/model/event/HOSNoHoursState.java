package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum HOSNoHoursState  implements BaseEnum {
    NONE(0),
    DRIVING(1),
    ON_DUTY(2),
    ON_DUTY_7_DAY(3),
    ON_DUTY_8_DAY(4),
    ON_DUTY_NO_BREAK(5);
    
    private Integer code;
    
    private HOSNoHoursState(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    
    private static final Map<Integer, HOSNoHoursState> lookup = new HashMap<Integer, HOSNoHoursState>();
    static
    {
        for (HOSNoHoursState p : EnumSet.allOf(HOSNoHoursState.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static HOSNoHoursState valueOf(Integer code)
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
