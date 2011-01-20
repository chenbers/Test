package com.inthinc.pro.model.event;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.BaseEnum;


public enum NoteType implements BaseEnum {
    
    //TODO These trip types are used by web TeamTripsBean but not clear why?
    // Because the trips need start and end or in progress events to map onto the correct icon on the map
    TRIP_START(-1),
    TRIP_INPROGRESS(-2),
    TRIP_END(-3),
    
    BASE(0, EventType.UNKNOWN),
    FULLEVENT(1,FullEvent.class),
    NOTEEVENT(2,AggressiveDrivingEvent.class),
    SEATBELT(3,SeatBeltEvent.class),  
    WAYSMART_THEFT(5), //WAYSMART
    SPEEDING(4), // NOT USED
    LOCATION(6), //??
    NEW_DRIVER(7),
    WAYSMART_MANDOWN(8, EventType.MAN_DOWN), //WAYSMART
    WAYSMART_PANIC(9, EventType.PANIC), //WAYSMART
    WAYSMART_HOME(11), //WAYSMART
    WAYSMART_CHECKUNIT(12), //WAYSMART
    WAYSMART_GEOFENCE(13), //WAYSMART
    
    RPM(14),
    
    WAYSMART_MAN_OK(15, EventType.MAN_DOWN_CANCELED), //WAYSMART
    WAYSMART_TEMPERATURE(18), //WAYSMART
    
    IGNITION_ON(19, EventType.IGNITION_ON),
    IGNITION_OFF(20, EventType.IGNITION_OFF),
    
    WAYSMART_MODEM_OFF(21), //WAYSMART
    
    LOW_BATTERY(22, EventType.LOW_BATTERY),
    
    WAYSMART_ACCELERATION(23), //WAYSMART
    WAYSMART_DECELERATION(24), //WAYSMART
    
    VERSION(27),
    
    WAYSMART_GPS_STATE(28), //WAYSMART
    WAYSMART_DATA(29), //WAYSMART
    WAYSMART_EVENTDATA(30), //WAYSMART
    WAYSMART_CONF(31), //WAYSMART
    WAYSMART_ACKNOWLEDGE(33), //WAYSMART
    WAYSMART_INSTALL(35, EventType.INSTALL), //WAYSMART
    WAYSMART_BASE_VERSION(36), //WAYSMART
    WAYSMART_MISSING_PARTS(37), //WAYSMART
    WAYSMART_HEADLIGHT(38), //WAYSMART
    WAYSMART_BOUNDARY_CHANGE(39), //WAYSMART
    WAYSMART_DRIVERSTATE_CHANGE(40), //WAYSMART
    WAYSMART_ENDOFMONTH(41), //WAYSMART
    WAYSMART_LOCATIONDEBUG(44, EventType.LOCATION_DEBUG), //WAYSMART
    WAYSMART_CRASH_DATA(45), //WAYSMART
    
    NO_DRIVER(46,EventType.NO_DRIVER),
    
    WAYSMART_ON_ROAD(47), //WAYSMART
    WAYSMART_OFF_ROAD(48), //WAYSMART
    WAYSMART_SPEEDING_EX(49), //WAYSMART
    WAYSMART_ERROR(50), //WAYSMART
    
    STATS(51),
    LOW_POWER_MODE(52),
    
    WAYSMART_SATELLITE_SWITCH(53), //WAYSMART
    WAYSMART_WITNESS_HEARTBEAT_VIOLATION(54, EventType.WITNESS_HEARTBEAT_VIOLATION), //WAYSMART
    WAYSMART_DRIVER_HOURS(55), //WAYSMART
    WAYSMART_DOT_STOPPED(56, DOTStoppedEvent.class), //WAYSMART
    WAYSMART_ODOMETER(57), //WAYSMART
    WAYSMART_SPEEDING_EX2(58), //WAYSMART
    WAYSMART_NO_TRAILER(59), //WAYSMART
    WAYSMART_TRAILER_DATA(64), //WAYSMART
    
    CLEAR_DRIVER(66),
    
    WAYSMART_HOS_NO_HOURS(67, HOSNoHoursEvent.class), //WAYSMART
    WAYSMART_WAYPOINT(71), //WAYSMART
    WAYSMART_TEXT_MSG(72), //WAYSMART
    WAYSMART_FUEL_STOP(73), //WAYSMART
    
    FIRMWARE_UP_TO_DATE(74, FirmwareVersionEvent.class),
    MAPS_UP_TO_DATE(75),
    ZONES_UP_TO_DATE(76, ZonesVersionEvent.class),
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

    SPEEDING_EX3(93,SpeedingEvent.class),
    
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

    WSZONES_ARRIVAL_EX(117,ZoneArrivalEvent.class),
    WSZONES_DEPARTURE_EX(118,ZoneDepartureEvent.class),
    WITNESSII_STATUS(120),
    
    WAYSMART_WITNESSII_LIST(121), //WAYSMART
    WAYSMART_QSI_UP_TO_DATE(122, QSIVersionEvent.class), //WAYSMART

    WITNESS_UP_TO_DATE(123, WitnessVersionEvent.class),
    
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
    WAYSMART_AUTOMANDOWN(142, EventType.MAN_DOWN), //WAYSMART
    WAYSMART_AUTO_MAN_OK(143, EventType.MAN_DOWN_CANCELED), //WAYSMART
    
    SMTOOLS_EMULATION_UP_TO_DATE(144),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    SMTOOLS_SILICON_ID(146),
    
    WAYSMART_TRIAX_STATUS_WITNESSIII(147), //WAYSMART
    WAYSMART_SMTOOLS_DEVICE_STATUS(148), //WAYSMART
    WAYSMART_OBD_PARAMS_STATUS(149), //WAYSMART
    
    POWER_ON(150, PowerOnEvent.class),

    WAYSMART_INTRASTATE_VIOLATION(151), //WAYSMART
    WAYSMART_POTENTIAL_CRASH(152), //WAYSMART
    WAYSMART_CANCEL_POTENTIAL_CRASH(153), //WAYSMART
    WAYSMART_CHECK_HOSMINUTES(157), //WAYSMART
    WAYSMART_WIRELINE_STATUS (158), //WAYSMART //sent after every forward command received
    WAYSMART_WIRELINE_ALARM ( 159, EventType.WIRELINE_ALARM), //WAYSMART //sent when door alarm siren triggered
    WAYSMART_NO_INTERNAL_THUMB_DRIVE(164, EventType.NO_INTERNAL_THUMB_DRIVE), //WAYSMART
    WAYSMART_FUEL_STOP_EX(166), //WAYSMART
    WAYSMART_LOW_BATTERY_POTENIAL_TAMPERING(167, EventType.POTENTIAL_TAMPERING), //WAYSMART
    WAYSMART_ROLLOVER(169, FullEvent.class),
    WAYSMART_VERTICALEVENT(171, HardVertical820Event.class), //WAYSMART 
    WAYSMART_PARKINGBRAKE(172, ParkingBrakeEvent.class), //WAYSMART 
    WAYSMART_UNIT_INFO(173), //WAYSMART 
    WAYSMART_FULLEVENT_CONFIDENCE_LEVEL(174), //WAYSMART 
    WAYSMART_CRASH_DATA_HIRES(175), //WAYSMART
    WAYSMART_DSS_MICROSLEEP(176, EventType.MICRO_SLEEP), //WAYSMART 
    WAYSMART_DSS_STATISTICS(177), //WAYSMART 
    WAYSMART_NOTEEVENT_SECONDARY(178, AggressiveDrivingEvent.class), //WAYSMART
    WAYSMART_VERTICALEVENT_SECONDARY(179, HardVertical820Event.class), //WAYSMART 
    WAYSMART_WEEKLY_GPRS_USAGE(180), //WAYSMART    
    WAYSMART_MCM_APP_FIRMWARE_UP_TO_DATE(181), //WAYSMART    
    WAYSMART_REMOTE_AUTO_MANDOWN(182, EventType.MAN_DOWN), //WAYSMART   // automatic -no motion     mandown
    WAYSMART_REMOTE_MAN_MANDOWN(183, EventType.MAN_DOWN), //WAYSMART  // manual mandown
    WAYSMART_REMOTE_OK_MANDOWN(184, EventType.MAN_DOWN_CANCELED), //WAYSMART  // mandown cancelled
    WAYSMART_REMOTE_OFF_MANDOWN(185), //WAYSMART  // remote turned off
    WAYSMART_REMOTE_LOW_BATT_MANDOWN(186), //WAYSMART  // low battery on remote
    WAYSMART_REMOTE_ON_MANDONW(187), //WAYSMART  // remote turned on
    WAYSMART_SBS_UPDATE(189), //WAYSMART  // remote turned on
    WAYSMART_SPEEDING_EX4(191, SpeedingEvent.class), //WAYSMART
    
    DIAGNOSTICS_REPORT(200),
    START_SPEEDING(201),
    UNPLUGGED(202,EventType.TAMPERING),
    START_SEATBELT(203),
    DMM_MONITOR(204),
    INCOMING_CALL(205),
    OUTGOING_CALL(206),
    LOW_TIWI_BATTERY(207,EventType.DEVICE_LOW_BATTERY), //TODO EventType.LOW_BATTERY??
    IDLE(208,IdleEvent.class),
    ROLLOVER(209,FullEvent.class),
    COACHING_SPEEDING(210),
    COACHING_SEATBELT(211),
    UNPLUGGED_ASLEEP(213,EventType.TAMPERING),
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    
    WAYSMART_STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250), //WAYSMART
    WAYSMART_STRIPPED_GET_SHORT_ID       (251), //WAYSMART
    WAYSMART_GET_OCCUPANTS_STRIPPED        (252), //WAYSMART
    WAYSMART_GET_OCCUPANT_INFO_STRIPPED    (253), //WAYSMART
    
    STRIPPED_ACKNOWLEDGE(254);
    private int code;
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


    private NoteType(int code, Class<?> eventClass) {
        this.code = code;
        this.eventClass = eventClass;
    }

    private NoteType(int code, EventType eventType) {
        this.code = code;
        this.eventClass = Event.class;
        this.eventType = eventType;
    }

    private NoteType(int code) {
        this.code = code;
        eventClass=null;
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
    

    public EventType getEventType() {
        if (eventType == null) {
            if (getEventClass()!=null)
            {
                try {
                    return ((Event)getEventClass().newInstance()).getEventType();
                } catch (InstantiationException e) {
                    return EventType.UNKNOWN;
                } catch (IllegalAccessException e) {
                    return EventType.UNKNOWN;
                }
            }
        }
        return eventType;
    }

    public Set<EventType> getEventTypes() {
        if (eventType == null) {
            if (getEventClass()!=null && Arrays.asList(getEventClass().getInterfaces()).contains(MultipleEventTypes.class))
            {
//                System.out.println(getEventClass().getName() + " " +  Arrays.asList(getEventClass().getInterfaces()).contains(MultipleEventTypes.class));            
                try {
                    return ((MultipleEventTypes)getEventClass().newInstance()).getEventTypes();
                } catch (InstantiationException e) {
                    return null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name();
    }
}
