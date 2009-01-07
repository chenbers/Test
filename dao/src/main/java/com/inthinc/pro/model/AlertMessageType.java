package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AlertMessageType implements BaseEnum
{
    ALERT_TYPE_SPEEDING(1, "ALERT_TYPE_SPEEDING"),
    ALERT_TYPE_AGGRESSIVE_DRIVING(2, "ALERT_TYPE_AGGRESSIVE_DRIVING"),
    ALERT_TYPE_SEATBELT(3, "ALERT_TYPE_SEATBELT"),
    ALERT_TYPE_ENTER_ZONE(4, "ALERT_TYPE_ENTER_ZONE"),
    ALERT_TYPE_EXIT_ZONE(5, "ALERT_TYPE_EXIT_ZONE"),
    ALERT_TYPE_LOW_BATTERY(6, "ALERT_TYPE_LOW_BATTERY"),
    ALERT_TYPE_UNKNOWN(7, "ALERT_TYPE_UNKNOWN"),
    ALERT_TYPE_TAMPERING(8, "ALERT_TYPE_TAMPERING");

    private String description;
    private int code;

    private AlertMessageType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AlertMessageType> lookup = new HashMap<Integer, AlertMessageType>();
    static
    {
        for (AlertMessageType p : EnumSet.allOf(AlertMessageType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static AlertMessageType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

    public String getDescription()
    {
        return description;
    }

}
