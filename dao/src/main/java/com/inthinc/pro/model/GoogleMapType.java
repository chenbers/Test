package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GoogleMapType implements BaseEnum {
    NONE(0),
    G_NORMAL_MAP(1),
    G_SATELLITE_MAP(2),     // This map type displays satellite images.
    G_AERIAL_MAP(3),        // This map type displays aerial imagery.
    G_HYBRID_MAP(4),        // This map type displays a transparent layer of major streets on satellite images.
    G_AERIAL_HYBRID_MAP(5), // This map type displays a transparent layer of major streets on top of aerial imagery.
    G_PHYSICAL_MAP(6); 

    private int code;

    private GoogleMapType(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, GoogleMapType> lookup = new HashMap<Integer, GoogleMapType>();
    static
    {
        for (GoogleMapType p : EnumSet.allOf(GoogleMapType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GoogleMapType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "." + name();
    }
}
