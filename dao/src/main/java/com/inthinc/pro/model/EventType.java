package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EventType implements BaseEnum 
{
	TAMPERING(1, "Tampering", "TAMPERING"),
	SEATBELT(2, "Seat Belt", "SEATBELT"), 
	SPEEDING(3, "Speeding", "SPEEDING"), 
	HARD_ACCEL(4, "Rapid Acceleration", "HARD_ACCEL"), 
	HARD_BRAKE(5, "Hard Brake", "HARD_BRAKE"),
    HARD_TURN(6, "Unsafe Turn", "HARD_TURN"),
//	HARD_LEFT_TURN(6, "Unsafe Left Turn", "HARD_LEFT_TURN"),
//    HARD_RIGHT_TURN(7, "Unsafe Right Turn", "HARD_RIGHT_TURN"),
	HARD_VERT(8, "Hard Dip/Bump", "HARD_VERT"),
	ZONES_ARRIVAL(9, "Zone Arrival", "ZONES_ARRIVAL"),
	ZONES_DEPARTURE(10, "Zone Departure", "ZONES_DEPARTURE"),
	LOW_BATTERY(11, "Low Battery", "LOW_BATTERY"),
	DEVICE_LOW_BATTERY(12, "tiwi Low Battery", "DEVICE_LOW_BATTERY"),
	IDLING(13, "Idling", "IDLING"),
	UNKNOWN(14, "Unknown", "UNKNOWN");
	

    private String description;
    private int code;
    private String key;

    private EventType(int code, String description, String key)
    {
        this.code = code;
        this.description = description;
        this.key = key;
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

    public String getKey()
    {
        return key;
    }
}
