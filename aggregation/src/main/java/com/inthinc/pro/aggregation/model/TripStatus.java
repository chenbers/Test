package com.inthinc.pro.aggregation.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TripStatus
{
    TRIP_IN_TRIP(0, "TripStatusInTrip"),
    TRIP_NOT_IN_TRIP(1, "TripStatusNotInTrip"),
    TRIP_COMPLETED(2, "TripStatusComplete"),
    TRIP_ACTIVE(3, "TripStatusActive"),
    TRIP_FILTERED(4, "TripStatusFiltered"),
    TRIP_INVALID_REMOVED(5, "TripStatusInvalidRemoved"),
    TRIP_INVALID_IGNORED(6, "TripStatusInvalidIgnored");
    

    private String description;
    private int code;

    private TripStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, TripStatus> lookup = new HashMap<Integer, TripStatus>();
    static
    {
        for (TripStatus p : EnumSet.allOf(TripStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static TripStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }

    public String toString()
    {
        return this.description;
    }

}

