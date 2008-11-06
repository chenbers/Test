package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EventType implements BaseEnum 
{
	TAMPERING(1, "Tampering"),
	SEATBELT(2, "Seat Belt"), 
	SPEEDING(3, "Speeding"), 
	HARD_ACCEL(4, "Rapid Acceleration"), 
	HARD_BRAKE(5, "Hard Brake"), 
	HARD_LEFT_TURN(6, "Unsafe Left Turn"),
    HARD_RIGHT_TURN(7, "Unsafe Left Turn"),
	HARD_VERT(8, "Hard Dip/Bump"),
	ZONES_ARRIVAL(9, "Zone Arrival"),
	ZONES_DEPARTURE(10, "Zone Departure"),
	UNKNOWN(11, "Unknown");

    private String description;
    private int code;

    private EventType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, EventType> lookup = new HashMap<Integer, EventType>();
    static
    {
        for (EventType p : EnumSet.allOf(EventType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static EventType getEventType(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}
