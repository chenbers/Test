package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum WS850HOSDOTType {
    
    LIGHT_DUTY_NO_HOS(0),
    LIGHT_DUTY_PROMPT(1),
    LIGHT_DUTY_HOS(2),
    HEAVY_DUTY(3);
    
    private Integer configuratorSetting;

    private WS850HOSDOTType(Integer configuratorSetting) {
        this.configuratorSetting = configuratorSetting;
    }

    public Integer getConfiguratorSetting() {
        return configuratorSetting;
    }
    private static final Map<Integer, WS850HOSDOTType> configuratorLookup = new HashMap<Integer, WS850HOSDOTType>();
    static {
        for (WS850HOSDOTType p : EnumSet.allOf(WS850HOSDOTType.class)) {
            configuratorLookup.put(p.configuratorSetting, p);
        }
    }
    public static WS850HOSDOTType getFromSetting(Integer configuratorSetting) {
        if (configuratorSetting == null)
            return null;
        return configuratorLookup.get(configuratorSetting);
    }
    public static Set<WS850HOSDOTType> getDOTTypes(){
        return EnumSet.of(WS850HOSDOTType.LIGHT_DUTY_HOS,WS850HOSDOTType.LIGHT_DUTY_PROMPT,WS850HOSDOTType.HEAVY_DUTY);
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
