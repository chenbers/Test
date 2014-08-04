package com.inthinc.pro.model;

import com.inthinc.pro.model.event.EventType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MaintenanceEventType {
    RED_STOP(1),
    AMBER_WARNING(2),
    PROTECT(3),
    BATTERY_VOLTAGE(4),
    ENGINE_TEMP(5),
    TRANSMISSION_TEMP(6),
    DPF_FLOW_RATE(7),
    OIL_PRESSURE(8),
    MALFUNCTION_INDICATOR_LAMP(9),
    ATTR_ENGINE_HOURS(10),
    ODOMETER(11);

    private int code;

    private MaintenanceEventType(int code) {
        this.code = code;
    }

    private static final Map<Integer, MaintenanceEventType> lookup = new HashMap<Integer, MaintenanceEventType>();
    static {
        for (MaintenanceEventType p : EnumSet.allOf(MaintenanceEventType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static MaintenanceEventType getEventType(Integer code) {
        return lookup.get(code);
    }


}
