package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OccupantType implements BaseEnum {
    DRIVER(0), PASSENGER(1);

    int code;

    private OccupantType(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    private static final Map<Integer, OccupantType> lookup = new HashMap<Integer, OccupantType>();
    static {
        for (OccupantType p : EnumSet.allOf(OccupantType.class)) {
            lookup.put(p.code, p);
        }
    }

    public static OccupantType valueOf(Integer code) {
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
