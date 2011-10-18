package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum VehicleDOTType{
    
    NON_DOT (1), 
    DOT( 0 ), 
    PROMPT_FOR_DOT_TRIP( 2); 
    
    private int configuratorSetting;

    private VehicleDOTType(int configuratorSetting) {
        this.configuratorSetting = configuratorSetting;
    }

    private static final Map<Integer, VehicleDOTType> configuratorLookup = new HashMap<Integer, VehicleDOTType>();
    static {
        for (VehicleDOTType p : EnumSet.allOf(VehicleDOTType.class)) {
        	configuratorLookup.put(p.configuratorSetting, p);
        }
    }
    public static VehicleDOTType getFromSetting(Integer configuratorSetting) {
        if (configuratorSetting == null)
            return null;
        return configuratorLookup.get(configuratorSetting);
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
