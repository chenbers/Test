package com.inthinc.pro.model.zone.option;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.zone.option.type.SpeedValue;
import com.inthinc.pro.model.zone.option.type.OffOn;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public enum ZoneAvailableOption implements BaseEnum {
    VEHICLE_TYPE(1, "VEHICLE_TYPE", ZoneOptionType.VEHICLE_TYPE, ZoneVehicleType.ALL, true),
    SPEED_LIMIT(2, "SPEED_LIMIT", ZoneOptionType.SPEED, new SpeedValue(0), true),
    CAUTION_AREA(3, "CAUTION_AREA", ZoneOptionType.OFF_ON, OffOn.ON, true),
    REPORT_ON_ARRIVAL_DEPARTURE(4, "REPORT_ON_ARRIVAL_DEPARTURE", ZoneOptionType.OFF_ON, OffOn.ON, false),
    MONITOR_IDLE(5, "MONITOR_IDLE", ZoneOptionType.OFF_ON, OffOn.ON, true),
    SEATBELT_VIOLATION(6, "SEATBELT_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    SPEEDING_VIOLATION(7, "SPEEDING_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    DRIVER_ID_VIOLATION(8, "DRIVER_ID_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    MASTER_BUZZER(9, "MASTER_BUZZER", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_TURN_EVENT(10, "HARD_TURN_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_BRAKE_EVENT(11, "HARD_BRAKE_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_VERTICAL_EVENT(12, "HARD_VERTICAL_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true);
// removing for now, might be put back later    
//    LOAD_ZONES_ON_MAP(13, "LOAD_ZONES_ON_MAP", ZoneOptionType.OFF_ON, OffOn.OFF, true);
    
    Integer id;
    OptionValue defaultValue;
    String name;
    Boolean waySmartOnly;
    ZoneOptionType optionType;
    
    private ZoneAvailableOption(Integer id, String name, ZoneOptionType optionType, OptionValue defaultValue, Boolean waySmartOnly) 
    {
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.waySmartOnly = waySmartOnly;
        this.optionType = optionType;
    }
    
    public ZoneOptionType getOptionType() {
        return optionType;
    }
    public void setOptionType(ZoneOptionType optionType) {
        this.optionType = optionType;
    }

    private static final Map<Integer, ZoneAvailableOption> lookup = new HashMap<Integer, ZoneAvailableOption>();
    static
    {
        for (ZoneAvailableOption p : EnumSet.allOf(ZoneAvailableOption.class))
        {
            lookup.put(p.id, p);
        }
    }
    
    public static ZoneAvailableOption valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public OptionValue getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(OptionValue defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Boolean getWaySmartOnly() {
        return waySmartOnly;
    }
    public void setWaySmartOnly(Boolean waySmartOnly) {
        this.waySmartOnly = waySmartOnly;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Integer getCode() {
        return id;
    }

    static public OptionValue convertOptionValue(ZoneOptionType zoneOptionType, Integer value) { 
        if (zoneOptionType == ZoneOptionType.SPEED)
            return SpeedValue.valueOf(value);
        if (zoneOptionType == ZoneOptionType.OFF_ON)
            return OffOn.valueOf(value);
        if (zoneOptionType == ZoneOptionType.OFF_ON_DEVICE)
            return OffOnDevice.valueOf(value);
        if (zoneOptionType == ZoneOptionType.VEHICLE_TYPE)
            return ZoneVehicleType.valueOf(value);
        
        return null;
    }

}
