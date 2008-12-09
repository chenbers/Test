package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
    ASSET_REPORT(9, "ASSET_REPORT");

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
