package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.pagination.EventCategoryFilter;

@XmlRootElement
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
    public static final int TIWIPRO_EVENT_NO_DRIVER = 46;
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
    public static final int TIWIPRO_EVENT_START_SPEEDING = 201;
    public static final int TIWIPRO_EVENT_UNPLUGGED = 202;
    public static final int TIWIPRO_EVENT_START_SEATBELT = 203;
    public static final int TIWIPRO_EVENT_DMM_MONITOR = 204;
    public static final int TIWIPRO_EVENT_INCOMING_CALL = 205;
    public static final int TIWIPRO_EVENT_OUTGOING_CALL = 206;
    public static final int TIWIPRO_EVENT_LOW_TIWI_BATTERY = 207;
    public static final int TIWIPRO_EVENT_IDLE = 208;
    public static final int TIWIPRO_EVENT_ROLLOVER = 209;
    public static final int TIWIPRO_EVENT_COACHING_SPEEDING = 210;
    public static final int TIWIPRO_EVENT_COACHING_SEATBELT = 211;
    public static final int TIWIPRO_EVENT_UNPLUGGED_ASLEEP = 213;
    public static final int TIWIPRO_EVENT_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA    = 246;
    public static final int TIWIPRO_EVENT_ZONE_ENTER_ALERTED = 247;
    public static final int TIWIPRO_EVENT_ZONE_EXIT_ALERTED = 248;
    public static final int TIWIPRO_EVENT_STRIPPED_ACKNOWLEDGE = 254;
    
    
    private static final Map<Integer, Class<?>> typeMap = new HashMap<Integer, Class<?>> (); 
    static
    {
        typeMap.put(TIWIPRO_EVENT_BASE, Event.class);
        typeMap.put(TIWIPRO_EVENT_FULLEVENT, FullEvent.class);
        typeMap.put(TIWIPRO_EVENT_NOTEEVENT, AggressiveDrivingEvent.class);
        typeMap.put(TIWIPRO_EVENT_SPEEDING_EX3, SpeedingEvent.class);
        typeMap.put(TIWIPRO_EVENT_SEATBELT, SeatBeltEvent.class);
        typeMap.put(TIWIPRO_EVENT_UNPLUGGED, TamperingEvent.class);
        typeMap.put(TIWIPRO_EVENT_UNPLUGGED_ASLEEP, TamperingEvent.class);
        typeMap.put(TIWIPRO_EVENT_WSZONES_ARRIVAL_EX, ZoneArrivalEvent.class);
        typeMap.put(TIWIPRO_EVENT_WSZONES_DEPARTURE_EX, ZoneDepartureEvent.class);
        typeMap.put(TIWIPRO_EVENT_ZONE_ENTER_ALERTED, ZoneArrivalEvent.class);
        typeMap.put(TIWIPRO_EVENT_ZONE_EXIT_ALERTED, ZoneDepartureEvent.class);
        typeMap.put(TIWIPRO_EVENT_LOCATION, Event.class);
        typeMap.put(TIWIPRO_EVENT_LOW_BATTERY, LowBatteryEvent.class);
        typeMap.put(TIWIPRO_EVENT_LOW_TIWI_BATTERY, DeviceLowBatteryEvent.class);
        typeMap.put(TIWIPRO_EVENT_IDLE, IdleEvent.class);
        typeMap.put(TIWIPRO_EVENT_ROLLOVER, FullEvent.class);
        typeMap.put(TIWIPRO_EVENT_POWER_ON, PowerOnEvent.class);
        typeMap.put(TIWIPRO_EVENT_NO_DRIVER, NoDriverEvent.class);
        
    }

    private static final Map<EventCategory, List<Integer>> categoryMap = new HashMap<EventCategory, List<Integer>> (); 
    static
    {
        List<Integer> violationList = new ArrayList<Integer>();
        violationList.add(TIWIPRO_EVENT_NOTEEVENT);
        violationList.add(TIWIPRO_EVENT_SPEEDING_EX3);
        violationList.add(TIWIPRO_EVENT_SEATBELT);
        categoryMap.put(EventCategory.VIOLATION, violationList);
        
        List<Integer> warningList = new ArrayList<Integer>();
        warningList.add(TIWIPRO_EVENT_LOW_BATTERY);
        warningList.add(TIWIPRO_EVENT_LOW_TIWI_BATTERY);
        warningList.add(TIWIPRO_EVENT_UNPLUGGED);
        warningList.add(TIWIPRO_EVENT_IDLE);
        warningList.add(TIWIPRO_EVENT_UNPLUGGED_ASLEEP);
        categoryMap.put(EventCategory.WARNING, warningList);

        List<Integer> driverCatList = new ArrayList<Integer>();
        driverCatList.add(TIWIPRO_EVENT_WSZONES_ARRIVAL_EX);
        driverCatList.add(TIWIPRO_EVENT_WSZONES_DEPARTURE_EX);
        driverCatList.add(TIWIPRO_EVENT_ZONE_ENTER_ALERTED);
        driverCatList.add(TIWIPRO_EVENT_ZONE_EXIT_ALERTED);
        categoryMap.put(EventCategory.DRIVER, driverCatList);
        
        List<Integer> emergencyList = new ArrayList<Integer>();
        emergencyList.add(TIWIPRO_EVENT_ROLLOVER);
        emergencyList.add(TIWIPRO_EVENT_FULLEVENT);
        categoryMap.put(EventCategory.EMERGENCY, emergencyList);
        
        List<Integer> zoneAlertList = new ArrayList<Integer>();
        zoneAlertList.add(TIWIPRO_EVENT_WSZONES_ARRIVAL_EX);
        zoneAlertList.add(TIWIPRO_EVENT_WSZONES_DEPARTURE_EX);
        categoryMap.put(EventCategory.ZONE_ALERT, zoneAlertList);

        List<Integer> nodriverCatList = new ArrayList<Integer>();
        nodriverCatList.add(TIWIPRO_EVENT_NO_DRIVER);
        categoryMap.put(EventCategory.NO_DRIVER, nodriverCatList);
    }
    
	private static final Map<EventCategory, List<EventCategoryFilter>> categoryFilterMap = new HashMap<EventCategory, List<EventCategoryFilter>>();
    static {
    	List<EventCategoryFilter> eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.SEATBELT, new Integer[] {EventMapper.TIWIPRO_EVENT_SEATBELT}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.SPEEDING, new Integer[] {EventMapper.TIWIPRO_EVENT_SPEEDING_EX3}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_BRAKE, new Integer[] {EventMapper.TIWIPRO_EVENT_NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_BRAKE.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_ACCEL, new Integer[] {EventMapper.TIWIPRO_EVENT_NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_ACCEL.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_VERT, new Integer[] {EventMapper.TIWIPRO_EVENT_NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_VERT.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_TURN, new Integer[] {EventMapper.TIWIPRO_EVENT_NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_TURN.getCode()}));
    	categoryFilterMap.put(EventCategory.VIOLATION, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.TAMPERING, new Integer[] {EventMapper.TIWIPRO_EVENT_UNPLUGGED, EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.LOW_BATTERY, new Integer[] {EventMapper.TIWIPRO_EVENT_LOW_BATTERY}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.DEVICE_LOW_BATTERY, new Integer[] {EventMapper.TIWIPRO_EVENT_LOW_TIWI_BATTERY}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.IDLING, new Integer[] {EventMapper.TIWIPRO_EVENT_IDLE}, null));
    	categoryFilterMap.put(EventCategory.WARNING, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.CRASH, new Integer[] {EventMapper.TIWIPRO_EVENT_FULLEVENT}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ROLLOVER, new Integer[] {EventMapper.TIWIPRO_EVENT_ROLLOVER}, null));
    	categoryFilterMap.put(EventCategory.EMERGENCY, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ZONES_ARRIVAL, new Integer[] {EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX, EventMapper.TIWIPRO_EVENT_ZONE_ENTER_ALERTED}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ZONES_DEPARTURE, new Integer[] {EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE_EX, EventMapper.TIWIPRO_EVENT_ZONE_EXIT_ALERTED}, null));
    	categoryFilterMap.put(EventCategory.DRIVER, eventCategoryFilterList);

    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.NO_DRIVER, new Integer[] {EventMapper.TIWIPRO_EVENT_NO_DRIVER}, null));
    	categoryFilterMap.put(EventCategory.NO_DRIVER, eventCategoryFilterList);

    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.UNKNOWN, (List<Integer>)null, null));
    	categoryFilterMap.put(EventCategory.NONE, eventCategoryFilterList);

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

    public static List<Integer> getEventTypesInCategory(EventCategory category)
    {
        return categoryMap.get(category);
    }

    public static List<EventCategoryFilter> getEventCategoryFilter(EventCategory category)
    {
        return categoryFilterMap.get(category);
    }
}
