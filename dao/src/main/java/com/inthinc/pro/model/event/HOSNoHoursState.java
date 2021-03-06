package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum HOSNoHoursState  implements BaseEnum, EventAttrEnum {
    NONE(0),
    DRIVING(1),
    ON_DUTY(2),
    ON_DUTY_7_DAY(3),
    ON_DUTY_8_DAY(4),
    ON_DUTY_14_DAY(5),
    ON_DUTY_NO_BREAK(6),
    ON_DUTY_ELAPSE(7),
    DAILY_ON_DUTY(8),
    DAILY_DRIVING(9),
    DAILY_OFF_DUTY(10),
    CYCLE(11),
    PERSONAL_USE_DISTANCE(12),
    PERSONAL_USE_TIME(13),
    DRIVING_BREAK(14);
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

    @Override
    public EventAttrEnum mapFromInteger(Integer value) {
        return valueOf(value);
    }

    @Override
    public Integer mapToInteger() {
        return getCode();
    }
}
