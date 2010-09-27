package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum AlertMessageType implements BaseEnum
{
    ALERT_TYPE_SPEEDING(1, "ALERT_TYPE_SPEEDING"),
    ALERT_TYPE_AGGRESSIVE_DRIVING(2, "ALERT_TYPE_AGGRESSIVE_DRIVING"),
    ALERT_TYPE_SEATBELT(3, "ALERT_TYPE_SEATBELT"),
    ALERT_TYPE_ENTER_ZONE(4, "ALERT_TYPE_ENTER_ZONE"),
    ALERT_TYPE_EXIT_ZONE(5, "ALERT_TYPE_EXIT_ZONE"),
    ALERT_TYPE_LOW_BATTERY(6, "ALERT_TYPE_LOW_BATTERY"),
    ALERT_TYPE_UNKNOWN(7, "ALERT_TYPE_UNKNOWN"),
    ALERT_TYPE_TAMPERING(8, "ALERT_TYPE_TAMPERING"),
    ALERT_TYPE_CRASH(9, "ALERT_TYPE_CRASH"),
    ALERT_TYPE_HARD_BRAKE(10, "ALERT_TYPE_HARD_BRAKE"),
    ALERT_TYPE_HARD_ACCELL(11, "ALERT_TYPE_HARD_ACCELL"),
    ALERT_TYPE_HARD_TURN(12, "ALERT_TYPE_HARD_TURN"),
    ALERT_TYPE_HARD_BUMP(13, "ALERT_TYPE_HARD_BUMP"),
    ALERT_TYPE_NO_DRIVER(14, "ALERT_TYPE_NO_DRIVER"),
    ALERT_TYPE_OFFHOUR(15, "ALERT_TYPE_OFFHOUR"),

    ALERT_TYPE_HOS_VIOLATON(16, "ALERT_TYPE_HOS_VIOLATON"),
    ALERT_TYPE_THEFT(17, "ALERT_TYPE_THEFT"),
    ALERT_TYPE_RPM(18, "ALERT_TYPE_RPM"),
    ALERT_TYPE_HEADLIGHT(19, "ALERT_TYPE_HEADLIGHT"),
    ALERT_TYPE_WIRELINE(20, "ALERT_TYPE_WIRELINE"),
    ALERT_TYPE_SEEING_MACHINES(21, "ALERT_TYPE_SEEING_MACHINES");
    
    
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
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public String getDescription()
    {
        return description;
    }

}
