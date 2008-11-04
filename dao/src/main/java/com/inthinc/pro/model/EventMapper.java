package com.inthinc.pro.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class EventMapper
{
    
    private static final Logger logger = Logger.getLogger(EventMapper.class);
    
    // codes for events that come from device (this is a subset of the events that it can generate)
    public static final int TIWIPRO_EVENT_BASE = 0;
    public static final int TIWIPRO_EVENT_FULLEVENT = 1;
    public static final int TIWIPRO_EVENT_NOTEEVENT = 2;
    public static final int TIWIPRO_EVENT_SEATBELT = 3;  
    public static final int TIWIPRO_EVENT_SPEEDING = 4; // NOT USED
    public static final int TIWIPRO_EVENT_LOCATION = 6;
    public static final int TIWIPRO_EVENT_RPM = 14;
    public static final int TIWIPRO_EVENT_IGNITION_ON = 19;
    public static final int TIWIPRO_EVENT_IGNITION_OFF = 20;
    public static final int TIWIPRO_EVENT_LOW_BATTERY = 22;
    public static final int TIWIPRO_EVENT_VERSION = 27;
    public static final int TIWIPRO_EVENT_STATS = 51;
    public static final int TIWIPRO_EVENT_LOW_POWER_MODE = 52;
    public static final int TIWIPRO_EVENT_FIRMWARE_UP_TO_DATE = 74;
    public static final int TIWIPRO_EVENT_MAPS_UP_TO_DATE = 75;
    public static final int TIWIPRO_EVENT_ZONES_UP_TO_DATE = 76;
    public static final int TIWIPRO_EVENT_BOOT_LOADER_UP_TO_DATE = 77;
    public static final int TIWIPRO_EVENT_WSZONES_ARRIVAL = 78;
    public static final int TIWIPRO_EVENT_WSZONES_DEPARTURE = 87;
    public static final int TIWIPRO_EVENT_SPEEDING_EX3 = 93;
    public static final int TIWIPRO_EVENT_WSZONES_ARRIVAL_EX = 117;
    public static final int TIWIPRO_EVENT_WSZONES_DEPARTURE_EX = 118;
    public static final int TIWIPRO_EVENT_WITNESSII_STATUS = 120;
    public static final int TIWIPRO_EVENT_WITNESS_UP_TO_DATE = 123;
    public static final int TIWIPRO_EVENT_SMTOOLS_EMULATION_UP_TO_DATE = 144;
    public static final int TIWIPRO_EVENT_SMTOOLS_FIRMWARE_UP_TO_DATE = 145;
    public static final int TIWIPRO_EVENT_SMTOOLS_SILICON_ID = 146;
    public static final int TIWIPRO_EVENT_POWER_ON = 150;
    public static final int TIWIPRO_EVENT_DIAGNOSTICS_REPORT = 200;
    public static final int TIWIPRO_EVENT_UNPLUGGED = 202;
    public static final int TIWIPRO_EVENT_LOW_TIWI_BATTERY = 207;
    public static final int TIWIPRO_EVENT_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA    = 246;
    public static final int TIWIPRO_EVENT_ZONE_ENTER_ALERTED = 247;
    public static final int TIWIPRO_EVENT_ZONE_EXIT_ALERTED = 248;
    public static final int TIWIPRO_EVENT_STRIPPED_ACKNOWLEDGE = 254;
    
    @SuppressWarnings("unchecked")
    private static final Map<Integer, Class> typeMap = new HashMap<Integer, Class> (); 
    static
    {
        typeMap.put(TIWIPRO_EVENT_BASE, Event.class);
        typeMap.put(TIWIPRO_EVENT_NOTEEVENT, AggressiveDrivingEvent.class);
        typeMap.put(TIWIPRO_EVENT_SPEEDING, SpeedingEvent.class);
        typeMap.put(TIWIPRO_EVENT_SPEEDING_EX3, SpeedingEvent.class);
        typeMap.put(TIWIPRO_EVENT_SEATBELT, SeatBeltEvent.class);
        typeMap.put(TIWIPRO_EVENT_UNPLUGGED, TamperingEvent.class);
        typeMap.put(TIWIPRO_EVENT_WSZONES_ARRIVAL_EX, ZoneArrivalEvent.class);
        typeMap.put(TIWIPRO_EVENT_WSZONES_DEPARTURE_EX, ZoneDepartureEvent.class);
        typeMap.put(TIWIPRO_EVENT_ZONE_ENTER_ALERTED, ZoneArrivalEvent.class);
        typeMap.put(TIWIPRO_EVENT_ZONE_EXIT_ALERTED, ZoneDepartureEvent.class);
        typeMap.put(TIWIPRO_EVENT_LOCATION, Event.class);
    }
    
    
    @SuppressWarnings("unchecked")
    public static Class getEventType(Integer proEventType)
    {
        if (typeMap.get(proEventType) == null)
        {
            logger.error("Unsupported Event Type requested type = [" + proEventType + "] returning Base Event");
            return typeMap.get(TIWIPRO_EVENT_BASE);
        }
        
        return typeMap.get(proEventType);
    }


}
