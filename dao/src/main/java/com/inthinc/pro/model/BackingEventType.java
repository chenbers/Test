package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BackingEventType {
    BACKING(1),
    FIRST_MOVE_FORWARD(2);

    private int code;

    private BackingEventType(int code) {
        this.code = code;
    }

    private static final Map<Integer, BackingEventType> lookup = new HashMap<Integer, BackingEventType>();
    static {
        for (BackingEventType p : EnumSet.allOf(BackingEventType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
            return this.code;
    }

    public static BackingEventType getEventType(Integer code) {
        return lookup.get(code);
    }


}
