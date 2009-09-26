package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EventCategory implements BaseEnum
{
    VIOLATION(1, "VIOLATION"),
    WARNING(2, "WARNING"),
    DRIVER(3, "DRIVER"),
    NONE(4, "NONE"),
    EMERGENCY(5, "EMERGENCY"),
    ZONE_ALERT(6,"ZONE_ALERT");

    private String description;
    private int code;

    private EventCategory(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, EventCategory> lookup = new HashMap<Integer, EventCategory>();
    static
    {
        for (EventCategory p : EnumSet.allOf(EventCategory.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static EventCategory valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
    
}
