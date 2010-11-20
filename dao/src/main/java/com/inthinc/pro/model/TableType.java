package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TableType implements BaseEnum
{

    RED_FLAG(1, "RED_FLAG"),
    DRIVER_REPORT(2, "DRIVER_REPORT"),
    VEHICLE_REPORT(3, "VEHICLE_REPORT"),
    ADMIN_PEOPLE(4, "ADMIN_PEOPLE"),
    ADMIN_VEHICLES(5, "ADMIN_VEHICLES"),
    ADMIN_DEVICES(6, "ADMIN_DEVICES"),
    ADMIN_RED_FLAG_PREFS(7, "ADMIN_RED_FLAG_PREFS"),
    ADMIN_ZONE_ALERTS(8, "ADMIN_ZONE_ALERTS"),
    ASSET_REPORT(9, "ASSET_REPORT"),
    DEVICE_REPORT(10, "DEVICE_REPORT"),
    IDLING_REPORT(11, "IDLING_REPORT"),
    EVENTS(12, "EVENTS"),
    DIAGNOSTICS(13, "DIAGNOSTICS"),
    REPORT_SCHEDULES(14,"REPORT_SCHEDULES"),
    EMERGENCY(15, "EMERGENCY"),
    ZONE_ALERTS(16, "ZONE_ALERTS"),
    CRASH_HISTORY(17, "CRASH_HISTORY"),
    ADMIN_CUSTOMROLE(18, "ADMIN_CUSTOMROLE"),
    TEAM_STATS(19, "TEAM_STATS"),
    HOS_LOGS(20, "HOS_LOGS"),
    ZONE_EVENTS(21, "ZONE_EVENTS"),
    HOS_EVENTS(22, "HOS_EVENTS");
    
    private String description;
    private int    code;

    private TableType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, TableType> lookup = new HashMap<Integer, TableType>();
    static
    {
        for (TableType p : EnumSet.allOf(TableType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static TableType valueOf(Integer code)
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
