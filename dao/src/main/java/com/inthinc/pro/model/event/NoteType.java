package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.BaseEnum;


public enum NoteType implements BaseEnum {
    
    //TODO These trip types are used by web TeamTripsBean but not clear why?
    TRIP_START(-1),
    TRIP_INPROGRESS(-2),
    TRIP_END(-3),
    
    BASE(0, EnumSet.of(EventCategory.NONE), EventType.UNKNOWN),
    FULLEVENT(1,EnumSet.of(EventCategory.EMERGENCY), FullEvent.class),
    NOTEEVENT(2,EnumSet.of(EventCategory.VIOLATION), AggressiveDrivingEvent.class),
    SEATBELT(3,EnumSet.of(EventCategory.VIOLATION), SeatBeltEvent.class),  
    SPEEDING(4), // NOT USED
    LOCATION(6, EnumSet.of(EventCategory.NONE), EventType.UNKNOWN), //??
    NEW_DRIVER(7),
    RPM(14),
    IGNITION_ON(19),
    IGNITION_OFF(20),
    LOW_BATTERY(22,EnumSet.of(EventCategory.WARNING), LowBatteryEvent.class),
    VERSION(27),
    NO_DRIVER(46,EnumSet.of(EventCategory.NO_DRIVER), EventType.NO_DRIVER),
    STATS(51),
    LOW_POWER_MODE(52),
    CLEAR_DRIVER(66),
    FIRMWARE_UP_TO_DATE(74),
    MAPS_UP_TO_DATE(75),
    ZONES_UP_TO_DATE(76),
    BOOT_LOADER_UP_TO_DATE(77),
    WSZONES_ARRIVAL(78),
    WSZONES_DEPARTURE(87),
    SPEEDING_EX3(93,EnumSet.of(EventCategory.VIOLATION), SpeedingEvent.class),
    WSZONES_ARRIVAL_EX(117,EnumSet.of(EventCategory.DRIVER,EventCategory.ZONE_ALERT), ZoneArrivalEvent.class),
    WSZONES_DEPARTURE_EX(118,EnumSet.of(EventCategory.DRIVER,EventCategory.ZONE_ALERT), ZoneDepartureEvent.class),
    WITNESSII_STATUS(120),
    WITNESS_UP_TO_DATE(123),
    SMTOOLS_EMULATION_UP_TO_DATE(144),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    SMTOOLS_SILICON_ID(146),
    POWER_ON(150, EnumSet.of(EventCategory.NONE),  PowerOnEvent.class),
    DIAGNOSTICS_REPORT(200),
    START_SPEEDING(201),
    UNPLUGGED(202,EnumSet.of(EventCategory.WARNING), TamperingEvent.class),
    START_SEATBELT(203),
    DMM_MONITOR(204),
    INCOMING_CALL(205),
    OUTGOING_CALL(206),
    LOW_TIWI_BATTERY(207,EnumSet.of(EventCategory.WARNING), EventType.LOW_BATTERY),
    IDLE(208,EnumSet.of(EventCategory.WARNING), IdleEvent.class),
    ROLLOVER(209,EnumSet.of(EventCategory.EMERGENCY), FullEvent.class),
    COACHING_SPEEDING(210),
    COACHING_SEATBELT(211),
    UNPLUGGED_ASLEEP(213,EnumSet.of(EventCategory.WARNING), TamperingEvent.class),
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    ZONE_ENTER_ALERTED(247,EnumSet.of(EventCategory.DRIVER), ZoneArrivalEvent.class),
    ZONE_EXIT_ALERTED(248,EnumSet.of(EventCategory.DRIVER), ZoneDepartureEvent.class),
    STRIPPED_ACKNOWLEDGE(254);
    private int code;
    private Set<EventCategory> categorySet;
    private Class<?> eventClass;
    private EventType eventType;
    
    private static final Map<Integer, NoteType> lookup = new HashMap<Integer, NoteType>();
    static
    {
        for (NoteType p : EnumSet.allOf(NoteType.class))
        {
            lookup.put(p.code, p);
        }
    }
    private static final Map<EventCategory, Set<NoteType>> categoryMap = new EnumMap<EventCategory, Set<NoteType>>(EventCategory.class);

    static {
        for (EventCategory category : EventCategory.values()) {
            final Set<NoteType> set = new HashSet<NoteType>();
            categoryMap.put(category, set);
        }
        for (NoteType type : NoteType.values()) {
            for (EventCategory category : type.getEventCategories()) {
                NoteType.categoryMap.get(category).add(type);
            }
        }
    }

    private NoteType(int code, Set<EventCategory> categorySet, Class<?> eventClass) {
        this.code = code;
        this.categorySet = categorySet;
        this.eventClass = eventClass;
    }

    private NoteType(int code, Set<EventCategory> categorySet, EventType eventType) {
        this.code = code;
        this.categorySet = categorySet;
        this.eventClass = Event.class;
        this.eventType = eventType;
    }

    private NoteType(int code) {
        this.code = code;
        this.categorySet = EnumSet.of(EventCategory.NONE);
        eventClass=null;
    }

    public Set<EventCategory> getEventCategories() {
        return categorySet;
    }
    
    public Class<?> getEventClass() {
        return eventClass;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public static NoteType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    public static List<NoteType> getNoteTypesInCategory(EventCategory category)
    {       
        return new ArrayList(categoryMap.get(category));
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
