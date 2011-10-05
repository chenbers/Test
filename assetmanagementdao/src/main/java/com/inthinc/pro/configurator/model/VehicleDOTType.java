package com.inthinc.pro.configurator.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum VehicleDOTType{
    
    NON_DOT(0, 1), 
    DOT(1, 0 ), 
    PROMPT_FOR_DOT_TRIP(2, 2); 
    
    private int code;
    private int configuratorSetting;

    private VehicleDOTType(int code, int configuratorSetting) {
        this.code = code;
        this.configuratorSetting = configuratorSetting;
    }

    private static final Map<Integer, VehicleDOTType> lookup = new HashMap<Integer, VehicleDOTType>();
    static {
        for (VehicleDOTType p : EnumSet.allOf(VehicleDOTType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static VehicleDOTType valueOf(Integer code) {
        return lookup.get(code);
    }
    public static VehicleDOTType getFromSetting(Integer configuratorSetting) {
        if (configuratorSetting == null)
            return null;
        for (VehicleDOTType p : EnumSet.allOf(VehicleDOTType.class)) {
            if (p.configuratorSetting == configuratorSetting.intValue())
                return p;
        }
        return null;
    }
    public static Set<VehicleDOTType> getDOTTypes(){
        return EnumSet.of(VehicleDOTType.DOT,VehicleDOTType.PROMPT_FOR_DOT_TRIP);
    }
    public int getConfiguratorSetting() {
        return configuratorSetting;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    
}
