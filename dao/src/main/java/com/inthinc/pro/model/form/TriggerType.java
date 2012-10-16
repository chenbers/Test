package com.inthinc.pro.model.form;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TriggerType {
    NO_TRIGGER(0),
    PRE_TRIP(1),
    POST_TRIP(2),
    ROAD_HAZARD(3),
    STOP(4);
//    DRIVER_SELECT();
    
    private Integer code;
    
    private TriggerType(Integer code)
    {
        this.code = code;
    }

    private static final Map<Integer, TriggerType> lookup = new HashMap<Integer, TriggerType>();
    static
    {
        for (TriggerType p : EnumSet.allOf(TriggerType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static TriggerType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    public static String description() {
        StringBuffer buffer = new StringBuffer();
        for (TriggerType p : EnumSet.allOf(TriggerType.class))
            buffer.append(p.name()).append(" (").append(p.code).append(") ");
        return buffer.toString();
    }
}
