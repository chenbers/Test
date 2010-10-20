package com.inthinc.pro.model.zone.option;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.zone.option.type.IntegerValue;
import com.inthinc.pro.model.zone.option.type.OffOn;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public enum ZoneAvailableOption implements BaseEnum {
    VEHICLE_TYPE(1, "VEHICLE_TYPE", ZoneOptionType.VEHICLE_TYPE, ZoneVehicleType.ALL, true),
    SPEED_LIMIT(2, "SPEED_LIMIT", ZoneOptionType.INTEGER, new IntegerValue(0), true),
    CAUTION_AREA(3, "CAUTION_AREA", ZoneOptionType.OFF_ON, OffOn.ON, true),
    REPORT_ON_ARRIVAL(4, "REPORT_ON_ARRIVAL", ZoneOptionType.OFF_ON, OffOn.ON, false),
    REPORT_ON_DEPARTURE(5, "REPORT_ON_DEPARTURE", ZoneOptionType.OFF_ON, OffOn.ON, false),
    MONITOR_IDLE(6, "MONITOR_IDLE", ZoneOptionType.OFF_ON, OffOn.ON, true),
    SEATBELT_VIOLATION(7, "SEATBELT_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    SPEEDING_VIOLATION(8, "SPEEDING_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    DRIVER_ID_VIOLATION(9, "DRIVER_ID_VIOLATION", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    MASTER_BUZZER(10, "MASTER_BUZZER", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_TURN_EVENT(11, "HARD_TURN_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_BRAKE_EVENT(12, "HARD_BRAKE_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    HARD_VERTICAL_EVENT(13, "HARD_VERTICAL_EVENT", ZoneOptionType.OFF_ON_DEVICE, OffOnDevice.DEVICE, true),
    LOAD_ZONES_ON_MAP(14, "LOAD_ZONES_ON_MAP", ZoneOptionType.OFF_ON, OffOn.OFF, true);
    
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
}
