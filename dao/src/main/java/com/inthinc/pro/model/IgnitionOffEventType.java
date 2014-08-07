package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum IgnitionOffEventType {
    RED_STOP(1),
    AMBER_WARNING(2),
    PROTECT(3),
    MALFUNCTION_INDICATOR_LAMP(9);

    private int code;

    private IgnitionOffEventType(int code) {
        this.code = code;
    }

    private static final Map<Integer, IgnitionOffEventType> lookup = new HashMap<Integer, IgnitionOffEventType>();
    static {
        for (IgnitionOffEventType p : EnumSet.allOf(IgnitionOffEventType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static IgnitionOffEventType getEventType(Integer code) {
        return lookup.get(code);
    }


}
