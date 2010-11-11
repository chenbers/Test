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
    WAYSMART_THEFT(5), //WAYSMART
    SPEEDING(4), // NOT USED
    LOCATION(6, EnumSet.of(EventCategory.NONE), EventType.UNKNOWN), //??
    NEW_DRIVER(7),
    WAYSMART_MANDOWN(8), //WAYSMART
    WAYSMART_PANIC(9), //WAYSMART
    WAYSMART_HOME(11), //WAYSMART
    WAYSMART_CHECKUNIT(12), //WAYSMART
    vGEOFENCE(13), //WAYSMART
    
    RPM(14),
    
    WAYSMART_MAN_OK(15), //WAYSMART
    WAYSMART_TEMPERATURE(18), //WAYSMART
    
    IGNITION_ON(19),
    IGNITION_OFF(20),
    
    WAYSMART_MODEM_OFF(21), //WAYSMART
    
    LOW_BATTERY(22,EnumSet.of(EventCategory.WARNING), EventType.LOW_BATTERY),
    
    WAYSMART_ACCELERATION(23), //WAYSMART
    WAYSMART_DECELERATION(24), //WAYSMART
    
    VERSION(27),
    
    WAYSMART_GPS_STATE(28), //WAYSMART
    WAYSMART_DATA(29), //WAYSMART
    WAYSMART_EVENTDATA(30), //WAYSMART
    WAYSMART_CONF(31), //WAYSMART
    WAYSMART_ACKNOWLEDGE(33), //WAYSMART
    WAYSMART_INSTALL(35), //WAYSMART
    WAYSMART_BASE_VERSION(36), //WAYSMART
    WAYSMART_MISSING_PARTS(37), //WAYSMART
    WAYSMART_HEADLIGHT(38), //WAYSMART
    WAYSMART_BOUNDARY_CHANGE(39), //WAYSMART
    WAYSMART_DRIVERSTATE_CHANGE(40), //WAYSMART
    WAYSMART_ENDOFMONTH(41), //WAYSMART
    WAYSMART_LOCATIONDEBUG(44), //WAYSMART
    WAYSMART_CRASH_DATA(45), //WAYSMART
    
    NO_DRIVER(46,EnumSet.of(EventCategory.NO_DRIVER), EventType.NO_DRIVER),
    
    WAYSMART_ON_ROAD(47), //WAYSMART
    WAYSMART_OFF_ROAD(48), //WAYSMART
    WAYSMART_SPEEDING_EX(49), //WAYSMART
    WAYSMART_ERROR(50), //WAYSMART
    
    STATS(51),
    LOW_POWER_MODE(52),
    
    WAYSMART_SATELLITE_SWITCH(53), //WAYSMART
    WAYSMART_WITNESS_HEARTBEAT_VIOLATION(54), //WAYSMART
    WAYSMART_DRIVER_HOURS(55), //WAYSMART
    WAYSMART_DOT_STOPPED(56), //WAYSMART
    WAYSMART_ODOMETER(57), //WAYSMART
    WAYSMART_SPEEDING_EX2(58), //WAYSMART
    WAYSMART_NO_TRAILER(59), //WAYSMART
    WAYSMART_TRAILER_DATA(64), //WAYSMART
    
    CLEAR_DRIVER(66),
    
    WAYSMART_HOS_NO_HOURS(67), //WAYSMART
    WAYSMART_WAYPOINT(71), //WAYSMART
    WAYSMART_TEXT_MSG(72), //WAYSMART
    WAYSMART_FUEL_STOP(73), //WAYSMART
    
    FIRMWARE_UP_TO_DATE(74),
    MAPS_UP_TO_DATE(75),
    ZONES_UP_TO_DATE(76),
    BOOT_LOADER_UP_TO_DATE(77),
    WSZONES_ARRIVAL(78),
    
    WAYSMART_TEXT_MSG_CANNED(80), //WAYSMART
    WAYSMART_QUEUE_FULL(81), //WAYSMART
    WAYSMART_WAYPOINT_EX(83), //WAYSMART
    WAYSMART_MANDOWN_PENDING(83), //WAYSMART
    WAYSMART_PRE_TRIP_INSPECTION(85), //WAYSMART
    WAYSMART_POST_TRIP_INSPECTION(86), //WAYSMART
    
    WSZONES_DEPARTURE(87),
    
    WAYSMART_HOS_30_MIN(88), //WAYSMART
    WAYSMART_HOS_MINUS_15(89), //WAYSMART
    WAYSMART_HOS_MINUS_30(90), //WAYSMART
    WAYSMART_XCAT(91), //WAYSMART
    WAYSMART_DMR(92), //WAYSMART

    SPEEDING_EX3(93,EnumSet.of(EventCategory.VIOLATION), SpeedingEvent.class),
    
    WAYSMART_COLILO_VERSION(94), //WAYSMART
    WAYSMART_TRIAX_STATUS(95), //WAYSMART
    WAYSMART_DRIVERSTATE_CHANGE_EX(96), //WAYSMART
    WAYSMART_LIGHT_DUTY(97), //WAYSMART
    WAYSMART_HEAVY_DUTY(98), //WAYSMART
    WAYSMART_MISSING_IWI_CONF(99), //WAYSMART
    WAYSMART_INVALID_OCCUPANT(104), //WAYSMART
    WAYSMART_TRIAX_STATUS_EX(110), //WAYSMART
    WAYSMART_CRASH_DATA_EXTENDED(112), //WAYSMART
    WAYSMART_HOS_CHANGE_STATE_NO_GPS_LOCK(113), //WAYSMART
    WAYSMART_NEWDRIVER_HOSRULE(116), //WAYSMART

    WSZONES_ARRIVAL_EX(117,EnumSet.of(EventCategory.DRIVER,EventCategory.ZONE_ALERT), ZoneArrivalEvent.class),
    WSZONES_DEPARTURE_EX(118,EnumSet.of(EventCategory.DRIVER,EventCategory.ZONE_ALERT), ZoneDepartureEvent.class),
    WITNESSII_STATUS(120),
    
    WAYSMART_WITNESSII_LIST(121), //WAYSMART
    WAYSMART_QSI_UP_TO_DATE(122), //WAYSMART

    WITNESS_UP_TO_DATE(123),
    
    WAYSMART_TRIAX_UP_TO_DATE(124), //WAYSMART
    WAYSMART_TRIAX_STATUS_DIAGNOSTIC(125), //WAYSMART
    WAYSMART_IWICONF_LOAD_ERROR(126), //WAYSMART
    WAYSMART_INSTALLATION_CHECKLIST(127), //WAYSMART
    WAYSMART_WITNESSII_CHANGE_CONFIG_ACK(128), //WAYSMART
    WAYSMART_GET_WITNESSII_STATUS(129), //WAYSMART
    WAYSMART_WITNESSII_TRACE(130), //WAYSMART
    WAYSMART_CLEAR_OCCUPANT(133), //WAYSMART
    WAYSMART_ACKNOWLEDGE_UPDATE(135), //WAYSMART
    WAYSMART_IDLE_STATS(140), //WAYSMART
    WAYSMART_AUTOMANDOWN(142), //WAYSMART
    WAYSMART_AUTO_MAN_OK(143), //WAYSMART
    
    SMTOOLS_EMULATION_UP_TO_DATE(144),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    SMTOOLS_SILICON_ID(146),
    
    WAYSMART_TRIAX_STATUS_WITNESSIII(147), //WAYSMART
    WAYSMART_SMTOOLS_DEVICE_STATUS(148), //WAYSMART
    WAYSMART_OBD_PARAMS_STATUS(149), //WAYSMART
    
    POWER_ON(150, EnumSet.of(EventCategory.NONE),  PowerOnEvent.class),

    WAYSMART_INTRASTATE_VIOLATION(151), //WAYSMART
    WAYSMART_POTENTIAL_CRASH(152), //WAYSMART
    WAYSMART_CANCEL_POTENTIAL_CRASH(153), //WAYSMART
    WAYSMART_CHECK_HOSMINUTES(157), //WAYSMART
    WAYSMART_WIRELINE_STATUS (158), //WAYSMART //sent after every forward command received
    WAYSMART_WIRELINE_ALARM ( 159), //WAYSMART //sent when door alarm siren triggered
    WAYSMART_FUEL_STOP_EX(166), //WAYSMART
    WAYSMART_VERTICALEVENT(171), //WAYSMART 
    WAYSMART_PARKINGBRAKE(172), //WAYSMART 
    WAYSMART_UNIT_INFO(173), //WAYSMART 
    WAYSMART_FULLEVENT_CONFIDENCE_LEVEL(174), //WAYSMART 
    WAYSMART_CRASH_DATA_HIRES(175), //WAYSMART
    WAYSMART_DSS_MICROSLEEP(176), //WAYSMART 
    WAYSMART_DSS_STATISTICS(177), //WAYSMART 
    WAYSMART_NOTEEVENT_SECONDARY(178), //WAYSMART
    WAYSMART_VERTICALEVENT_SECONDARY(179), //WAYSMART 
    WAYSMART_WEEKLY_GPRS_USAGE(180), //WAYSMART    
    WAYSMART_MCM_APP_FIRMWARE_UP_TO_DATE(181), //WAYSMART    
    WAYSMART_REMOTE_AUTO_MANDOWN(182), //WAYSMART   // automatic -no motion     mandown
    WAYSMART_REMOTE_MAN_MANDOWN(183), //WAYSMART  // manual mandown
    WAYSMART_REMOTE_OK_MANDOWN(184), //WAYSMART  // mandown cancelled
    WAYSMART_REMOTE_OFF_MANDOWN(185), //WAYSMART  // remote turned off
    WAYSMART_REMOTE_LOW_BATT_MANDOWN(186), //WAYSMART  // low battery on remote
    WAYSMART_REMOTE_ON_MANDONW(187), //WAYSMART  // remote turned on
    WAYSMART_SBS_UPDATE(189), //WAYSMART  // remote turned on
    WAYSMART_SPEEDING_EX4(191), //WAYSMART
    
    DIAGNOSTICS_REPORT(200),
    START_SPEEDING(201),
    UNPLUGGED(202,EnumSet.of(EventCategory.WARNING), EventType.TAMPERING),
    START_SEATBELT(203),
    DMM_MONITOR(204),
    INCOMING_CALL(205),
    OUTGOING_CALL(206),
    LOW_TIWI_BATTERY(207,EnumSet.of(EventCategory.WARNING), EventType.DEVICE_LOW_BATTERY), //TODO EventType.LOW_BATTERY??
    IDLE(208,EnumSet.of(EventCategory.WARNING), IdleEvent.class),
    ROLLOVER(209,EnumSet.of(EventCategory.EMERGENCY), FullEvent.class),
    COACHING_SPEEDING(210),
    COACHING_SEATBELT(211),
    UNPLUGGED_ASLEEP(213,EnumSet.of(EventCategory.WARNING), EventType.TAMPERING),
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    ZONE_ENTER_ALERTED(247,EnumSet.of(EventCategory.DRIVER), ZoneArrivalEvent.class),
    ZONE_EXIT_ALERTED(248,EnumSet.of(EventCategory.DRIVER), ZoneDepartureEvent.class),
    
    WAYSMART_STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250), //WAYSMART
    WAYSMART_STRIPPED_GET_SHORT_ID       (251), //WAYSMART
    WAYSMART_GET_OCCUPANTS_STRIPPED        (252), //WAYSMART
    WAYSMART_GET_OCCUPANT_INFO_STRIPPED    (253), //WAYSMART
    
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
