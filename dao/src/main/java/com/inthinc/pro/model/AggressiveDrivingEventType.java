package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AggressiveDrivingEventType {
    HARD_BRAKE(1),
    HARD_ACCEL(2),
    HARD_VERT(3),
    HARD_TURN(4);
    
    private int code;

    private AggressiveDrivingEventType(int code) {
        this.code = code;
    }

    private static final Map<Integer, AggressiveDrivingEventType> lookup = new HashMap<Integer, AggressiveDrivingEventType>();
    static {
        for (AggressiveDrivingEventType p : EnumSet.allOf(AggressiveDrivingEventType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static AggressiveDrivingEventType getEventType(Integer code) {
        return lookup.get(code);
    }


}
