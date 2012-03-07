package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GoogleMapType implements BaseEnum {
    NONE(0, false), 
    G_NORMAL_MAP(1, true),
    G_SATELLITE_MAP(2, true),       // This map type displays satellite images.
    G_AERIAL_MAP(3, false),         // This map type displays aerial imagery.
    G_HYBRID_MAP(4, true),          // This map type displays a transparent layer of major streets on satellite images.
    G_AERIAL_HYBRID_MAP(5, false),  // This map type displays a transparent layer of major streets on top of aerial imagery.
    G_PHYSICAL_MAP(6, false); 

    private int code;
    private boolean displayed;

    private GoogleMapType(int code, boolean displayed)
    {
        this.code = code;
        this.displayed = displayed;
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

    public boolean getDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
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
