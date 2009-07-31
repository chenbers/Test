package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
public enum FuelEfficiencyType implements BaseEnum {
    MPG_US(1), MPG_UK(2), KMPL(3), LP100KM(4);
    private int code;

    private FuelEfficiencyType(int code) {
        this.code = code;
    }

    private static final Map<Integer, FuelEfficiencyType> lookup = new HashMap<Integer, FuelEfficiencyType>();
    static {
        for (FuelEfficiencyType type : EnumSet.allOf(FuelEfficiencyType.class)) {
            lookup.put(type.code, type);
        }
    }

    public static FuelEfficiencyType valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
