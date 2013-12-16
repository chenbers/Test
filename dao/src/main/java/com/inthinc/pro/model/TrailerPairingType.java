package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TrailerPairingType implements BaseEnum {
    NONE(0),
    DEVICE_DETECTED(1),
    DEVICE_ENTERED(2),
    PORTAL_ENTERED(3);

    private Integer code;

    private TrailerPairingType(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, TrailerPairingType> lookup = new HashMap<Integer, TrailerPairingType>();
    static {
        for (TrailerPairingType p : EnumSet.allOf(TrailerPairingType.class)) {
            lookup.put(p.code, p);
        }
    }

    public static TrailerPairingType valueOf(Integer code) {
        return lookup.get(code);
    }

}
