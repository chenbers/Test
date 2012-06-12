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

    /**
     * Generated when a "crash" is detected <br/>
     * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
     */
    FULLEVENT(1,FullEvent.class),

    /**
     * Generated when aggressive driving is detected <br/>
     * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
     */
    NOTEEVENT(2,AggressiveDrivingEvent.class),

    /**
     * Generated when the seatbelt violation thresholds are exceeded <br/>
     * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, [ATTR_AVG_RPM], [ATTR_VIOLATION_FLAGS]
     */
    SEATBELT(3,SeatBeltEvent.class),  
    SPEEDING(4), // NOT USED
    THEFT(5), //WAYSMART

    /**
     * A generic location note sent every 15 seconds while vehicle is in motion <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    LOCATION(6), //??
    NEW_DRIVER(7),
    MANDOWN(8, EventType.MAN_DOWN), //WAYSMART
    PANIC(9, EventType.PANIC), //WAYSMART
    HOME(11), //WAYSMART
    CHECKUNIT(12), //WAYSMART
    GEOFENCE(13), //WAYSMART

    /**
     * Sent if vehicle exceeds RPM threshold <br/>
     * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, ATTR_AVG_RPM, [ATTR_VIOLATION_FLAGS]
     */
    RPM(14),
    
    MAN_OK(15, EventType.MAN_DOWN_CANCELED), //WAYSMART
    TEMPERATURE(18), //WAYSMART

    /**
     * Sent when ignition on is detected <br/>
     * Attributes ATTR_CURRENT_IGN, [ATTR_NUM_GPS_REBOOTS], [ATTR_VIOLATION_FLAGS]
     */
    IGNITION_ON(19, EventType.IGNITION_ON),

    /**
     * Sent when ignition off is detected <br/>
     * Attributes [ATTR_MPG], [ATTR_MPG_DISTANCE], [ATTR_TRIP_DURATION], ATTR_PERCENTAGE_GPS_FILTERED, [ATTR_SPEEDING_SQUELCHED], ATTR_CURRENT_IGN, [ATTR_NUM_GPS_REBOOTS], [ATTR_OBD_PCT], [ATTR_GPS_PCT], [ATTR_AGPS_DOWNLOADED], [ATTR_VIOLATION_FLAGS]
     */
    IGNITION_OFF(20, EventType.IGNITION_OFF),
    
    MODEM_OFF(21), //WAYSMART

    /**
     * Sent if we detect low vehicle battery <br/>
     * Attributes ATTR_VEHICLE_BATTERY, [ATTR_VIOLATION_FLAGS]
     */
    LOW_BATTERY(22, EventType.LOW_BATTERY),
    
    ACCELERATION(23), //WAYSMART
    DECELERATION(24), //WAYSMART
    WATCHDOG(25), //WAYSMART
    REBOOT(26), //WAYSMART

    /**
     * response to GET_VERSION fwd cmd <br/>
     * Attributes ATTR_FIRMWARE_VERSION, [ATTR_DMM_VERSION], [ATTR_EMU_HASH_1], [ATTR_EMU_HASH_2], [ATTR_EMU_HASH_3], [ATTR_EMU_HASH_4], [ATTR_VIOLATION_FLAGS]
     */
    VERSION(27),
    
    GPS_STATE(28), //WAYSMART
    DATA(29), //WAYSMART
    EVENTDATA(30), //WAYSMART
    CONF(31), //WAYSMART
    ACKNOWLEDGE(33), //WAYSMART
    TRACE(34), //WAYSMART
    INSTALL(35, EventType.INSTALL), //WAYSMART
    BASE_VERSION(36), //WAYSMART
    MISSING_PARTS(37), //WAYSMART
    HEADLIGHT(38), //WAYSMART
    BOUNDARY_CHANGE(39), //WAYSMART
    HOS_CHANGE_STATE(40), //WAYSMART
    MONTHLY_UPDATE(41), //WAYSMART
    TIMESTAMP(42), //WAYSMART
    HOS_RECEIVED(43), //WAYSMART
    LOCATION_DEBUG(44, EventType.LOCATION_DEBUG), //WAYSMART
    CRASH_DATA(45), //WAYSMART

    /**
     * Sent when driving without a driver logged in <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    NO_DRIVER(46,EventType.NO_DRIVER),
    
    ON_ROAD(47), //WAYSMART
    OFF_ROAD(48), //WAYSMART
    SPEEDING_EX(49), //WAYSMART
    ERROR(50), //WAYSMART

    /**
     * Sent weekly to give diagnostics on how the unit is performing <br/>
     * Attributes [ATTR_EMU_HASH_1], [ATTR_EMU_HASH_2], [ATTR_EMU_HASH_3], [ATTR_EMU_HASH_4], [ATTR_TOTAL_AGPS_BYTES], [ATTR_VIOLATION_FLAGS], ATTR_TOTAL_BYTES_DUMPSET
     */
    STATS(51),

    /**
     * Sent when device goes into low power mode <br/>
     * Attributes ATTR_LOW_POWER_MODE_TIMEOUT, [ATTR_VIOLATION_FLAGS]
     */
    LOW_POWER_MODE(52),
    
    SATELLITE_SWITCH(53), //WAYSMART
    WITNESS_HEARTBEAT_VIOLATION(54, EventType.WITNESS_HEARTBEAT_VIOLATION), //WAYSMART
    DRIVER_HOURS(55), //WAYSMART
    DOT_STOPPED(56, DOTStoppedEvent.class), //WAYSMART
    ODOMETER(57), //WAYSMART
    SPEEDING_EX2(58), //WAYSMART
    NO_TRAILER(59), //WAYSMART
    _24HR_REBOOT(60), //WAYSMART
    FIRMWARE_REBOOT(61), //WAYSMART
    KEYPAD_REBOOT(62), //WAYSMART
    CMD_REBOOT(63), //WAYSMART
    TRAILER_DATA(64, TrailerDataEvent.class), //WAYSMART
    KEYPAD(65), //WAYSMART

    /**
     * Sent when driver logs out of the vehicle using RFID card <br/>
     * Attributes ATTR_DRIVER_ID (if available) or ATTR_RFID0, ATTR_RFID0, [ATTR_VIOLATION_FLAGS]
     */
    CLEAR_DRIVER(66),
    
    HOS_NO_HOURS(67, HOSNoHoursEvent.class), //WAYSMART
    HOS_LOG_RECORD(68), //WAYSMART
    HOS_SUMMARY_RECEIVED(69), //WAYSMART
    
    HOS_SUMMARY_RECORD(70), //WAYSMART
    WAYPOINT(71), //WAYSMART
    TEXT_MSG(72,TextMessageEvent.class), //WAYSMART
    FUEL_STOP(73), //WAYSMART
    
    FIRMWARE_UP_TO_DATE(74, FirmwareVersionEvent.class),
    MAPS_UP_TO_DATE(75),
    ZONES_UP_TO_DATE(76, ZonesVersionEvent.class),
    BOOT_LOADER_UP_TO_DATE(77),

    /**
     * Sent on a zone arrival, used in the case where the zone id is not known. <br/>
     * Attributes ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_ARRIVAL(78),
    
    TEXT_MSG_CANNED(80,TextMessageEvent.class), //WAYSMART
    QUEUE_FULL(81), //WAYSMART
    GPIO_SHUT_DOWN(82), //WAYSMART
    WAYPOINT_EX(83), //WAYSMART
    MANDOWN_PENDING(84), //WAYSMART
    PRE_TRIP_INSPECTION(85), //WAYSMART
    POST_TRIP_INSPECTION(86), //WAYSMART

    /**
     * Sent on a zone departure, used in the case where the zone id is not known <br/>
     * Attributes ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_DEPARTURE(87),
    
    HOS_30_MIN(88), //WAYSMART
    HOS_MINUS_15(89), //WAYSMART
    HOS_MINUS_30(90), //WAYSMART
    XCAT(91,TextMessageEvent.class), //WAYSMART
    DMR(92,TextMessageEvent.class), //WAYSMART

    /**
     * Sent at the end of a speeding violation <br/>
     * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, [ATTR_AVG_RPM], [ATTR_VIOLATION_FLAGS]
     */
    SPEEDING_EX3(93,SpeedingEvent.class),
    
    COLILO_VERSION(94), //WAYSMART

    /**
     * Sent as a response to GET_DMM_STATUS forward command <br/>
     * Attributes ATTR_MSP_CRASH_X, ATTR_MSP_CRASH_Y, ATTR_MSP_CRASH_Z, ATTR_MSP_HARD_ACCEL_LEVEL, ATTR_MSP_HARD_ACCEL_DV, ATTR_MSP_HARD_BRAKE_LEVEL, ATTR_MSP_HARD_BRAKE_DV, ATTR_MSP_HARD_TURN_LEVEL, ATTR_MSP_HARD_TURN_DV, ATTR_MSP_HARD_BUMP_RMSLEV, ATTR_MSP_HARD_BUMP_PK2PKLEV, ATTR_MSP_TEMPSLOPE_U, ATTR_MSP_TEMPSLOPE_V, ATTR_MSP_TEMPSLOPE_W,, [ATTR_VIOLATION_FLAGS]
     */
    TRIAX_STATUS(95), //WAYSMART
    HOS_CHANGE_STATE_EX(96), //WAYSMART
    LIGHT_DUTY(97), //WAYSMART
    HEAVY_DUTY(98), //WAYSMART
    MISSING_IWI_CONF(99), //WAYSMART
    
    INVALID_DRIVER(103, InvalidDriverEvent.class), //WAYSMART
    INVALID_OCCUPANT(104, InvalidOccupantEvent.class), //WAYSMART
    VALID_OCCUPANT(105, ValidOccupantEvent.class), //WAYSMART
    TRIAX_STATUS_EX(110), //WAYSMART
    CRASH_DATA_EXTENDED(112), //WAYSMART
    HOS_CHANGE_STATE_NO_GPS_LOCK(113), //WAYSMART
    NEWDRIVER_HOSRULE(116, ValidDriverEvent.class), //WAYSMART


    /**
     * Sent on a zone arrival <br/>
     * Attributes ATTR_ZONE_ID, ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_ARRIVAL_EX(117,ZoneArrivalEvent.class),
    
    /**
     * Sent on a zone departure <br/>
     * Attributes ATTR_ZONE_ID, ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_DEPARTURE_EX(118,ZoneDepartureEvent.class),
    REQUEST_SPECIFIC_DETAIL_RECORDS(119), //WAYSMART
    WITNESSII_STATUS(120),
    
    WITNESSII_LIST(121), //WAYSMART
    QSI_UP_TO_DATE(122, QSIVersionEvent.class), //WAYSMART

    WITNESS_UP_TO_DATE(123, WitnessVersionEvent.class),
    
    TRIAX_UP_TO_DATE(124), //WAYSMART
    TRIAX_STATUS_DIAGNOSTIC(125), //WAYSMART
    IWICONF_LOAD_ERROR(126), //WAYSMART
    INSTALLATION_CHECKLIST(127), //WAYSMART
    WITNESSII_CHANGE_CONFIG_ACK(128), //WAYSMART
    GET_WITNESSII_STATUS(129), //WAYSMART
    
    WITNESSII_TRACE(130), //WAYSMART
    WITNESSII_TRACE_OVERWRITTEN(131), //WAYSMART
    WITNESSII_ARCHIVE_HEADER(132), //WAYSMART
    CLEAR_OCCUPANT(133), //WAYSMART
    WITNESSII_ARCHIVED_TRACE_UPLOADED(134), //WAYSMART
    ACKNOWLEDGE_UPDATE(135), //WAYSMART
    BOUNDARYDAT_UP_TO_DATE(136),
    PLACES2DAT_UP_TO_DATE(137),
//    SBDRING_NULL(138), // not uploaded to portal
//    SBDRING(139), // not uploaded to portal
    
    
    IDLE_STATS(140), //WAYSMART
    WITNESSII_DIAGNOSTIC(141),
    AUTO_MAN_DOWN(142, EventType.MAN_DOWN), //WAYSMART
    AUTO_MAN_OK(143, EventType.MAN_DOWN_CANCELED), //WAYSMART
    
    SMTOOLS_EMULATION_UP_TO_DATE(144),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    SMTOOLS_SILICON_ID(146),
    
    TRIAX_STATUS_WITNESSIII(147), //WAYSMART
    SMTOOLS_DEVICE_STATUS(148), //WAYSMART
    OBD_PARAMS_STATUS(149), //WAYSMART
    
    /**
     * Sent every time the device powers on <br/>
     * Attributes ATTR_RESET_REASON, ATTR_MANUAL_RESET_REASON, ATTR_FIRMWARE_VERSION, ATTR_GPS_LOCK_TIME, [ATTR_DMM_VERSION], [ATTR_DMM_ORIENTATION], [ATTR_DMM_CAL_STATUS], ATTR_PRODUCT_VERSION, [ATTR_VIOLATION_FLAGS]
     */
    POWER_ON(150, PowerOnEvent.class),

    INTRASTATE_VIOLATION(151), //WAYSMART SMTOOLS_ERROR(151),  
    POTENTIAL_CRASH(152), //WAYSMART
    CANCEL_POTENTIAL_CRASH(153), //WAYSMART
    CHECK_HOSMINUTES(157), //WAYSMART
    WIRELINE_STATUS (158), //WAYSMART //sent after every forward command received
    WIRELINE_ALARM ( 159, EventType.WIRELINE_ALARM), //WAYSMART //sent when door alarm siren triggered
    WIRELINE_IGN_TEST_NOT_RUN(160), //WAYSMART
    WIRELINE_IGN_TEST_FAIL(161), //WAYSMART
    WIRELINE_IGN_TEST_PASS(162), //WAYSMART
    SHELL_OUTPUT(163), //WAYSMART

    NO_INTERNAL_THUMB_DRIVE(164, EventType.NO_INTERNAL_THUMB_DRIVE), //WAYSMART
    AUTO_DETECT(165), //WAYSMART
    FUEL_STOP_EX(166), //WAYSMART
    LOW_BATTERY_POTENIAL_TAMPERING(167, EventType.POTENTIAL_TAMPERING), //WAYSMART
    FULL_EVENT_BELOW_THRESHOLD(168), //WAYSMART
    ROLLOVER_WAYSMART(169, FullEvent.class),
    
    TRIAX_STATUS_WITNESSIII_TEMPCOMP(170), //WAYSMART
    VERTICAL_EVENT(171, HardVertical820Event.class), //WAYSMART 
    PARKING_BRAKE(172, ParkingBrakeEvent.class), //WAYSMART 
    UNIT_INFO(173), //WAYSMART 
    FULL_EVENT_CONFIDENCE_LEVEL(174), //WAYSMART 
    CRASH_DATA_HI_RES(175), //WAYSMART
    DSS_MICROSLEEP(176, EventType.MICRO_SLEEP), //WAYSMART 
    DSS_STATISTICS(177), //WAYSMART 
    NOTE_EVENT_SECONDARY(178, AggressiveDrivingEvent.class), //WAYSMART
    VERTICAL_EVENT_SECONDARY(179, HardVertical820Event.class), //WAYSMART 
    
    WEEKLY_GPRS_USAGE(180), //WAYSMART    
    MCM_APP_FIRMWARE_UP_TO_DATE(181), //WAYSMART    
    REMOTE_AUTO_MANDOWN(182, EventType.MAN_DOWN), //WAYSMART   // automatic -no motion     mandown
    REMOTE_MAN_MANDOWN(183, EventType.MAN_DOWN), //WAYSMART  // manual mandown
    REMOTE_OK_MANDOWN(184, EventType.MAN_DOWN_CANCELED), //WAYSMART  // mandown cancelled
    REMOTE_OFF_MANDOWN(185), //WAYSMART  // remote turned off
    REMOTE_LOW_BATT_MANDOWN(186), //WAYSMART  // low battery on remote
    REMOTE_ON_MANDONW(187), //WAYSMART  // remote turned on
    REMOTE_AACK_MANDOWN(188), //WAYSMART
    
    /**
     * Sent when a speed by street map is updated.
     * ATTR_SBS_UPDATE_TYPE denotes the type of update, ATTR_MAP_HASH is the map square expressed as an integer, and ATTR_BASE_VER
     * will be the version of the map (base version for baseline updates, exception version for exception updates).
     */
    SBS_UPDATE(189), //WAYSMART  // remote turned on
    
    EMU_TARBALL_UP_TO_DATE(190), //WAYSMART
    SPEEDING_EX4(191, SpeedingEvent.class), //WAYSMART
    SPEEDING_LOG4(192), //WAYSMART
    BLAST_ZONE_RECEIVED(193), //WAYSMART
    SPEEDING_AV(194), //WAYSMART
    UNIT_TEST_INFORMATION(195), //WAYSMART
    UNIT_TEST_WARNING(196), //WAYSMART
    UNIT_TEST_CRITICAL(197), //WAYSMART
    
    /**
     * Sent on internal errors <br/>
     * Attributes ATTR_BAD_ERROR, [ATTR_VIOLATION_FLAGS]
     */
    DIAGNOSTICS_REPORT(200),
    
    /**
     * Sent at the beginning of a speeding violation - disabled by default <br/>
     * Attributes ATTR_SPEED_LIMIT, ATTR_SPEED_ID, [ATTR_VIOLATION_FLAGS]
     */
    START_SPEEDING(201),
    
    /**
     * Sent when the device is unplugged <br/>
     * Attributes ATTR_BACKUP_BATTERY (meaningless on tiwiPro), [ATTR_VIOLATION_FLAGS]
     */
    UNPLUGGED(202,EventType.TAMPERING),
    
    /**
     * Sent at the beginning of a seatbelt violation - disabled by default <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    START_SEATBELT(203),
    DMM_MONITOR(204),
    
    /**
     * Sent when an incoming call has been received <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    INCOMING_CALL(205),
    
    /**
     * Sent just prior to an outgoing call <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    OUTGOING_CALL(206),
    LOW_TIWI_BATTERY(207,EventType.DEVICE_LOW_BATTERY), //TODO EventType.LOW_BATTERY??
    
    /**
     * Sent after an ignition off if idling data exists <br/>
     * Attributes ATTR_LOW_IDLE, ATTR_HIGH_IDLE, [ATTR_VIOLATION_FLAGS]
     */
    IDLE(208,IdleEvent.class),
    
    /**
     * Generated when a rollover is detected <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    ROLLOVER(209,FullEvent.class),
    
    /**
     * Sent if driver was speeding, but fixed behavior after audio is played and before a violation is generated <br/>
     * Attributes ATTR_SPEED_ID, [ATTR_VIOLATION_FLAGS]
     */
    COACHING_SPEEDING(210),
    
    /**
     * Sent if driver wasn't wearing their seatbelt, but fixed behavior after audio is played and before a violation is generated <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    COACHING_SEATBELT(211),
    
    /**
     * Generated if something goes over the hardware threshold for a crash, but doesn't make it past the software deltaV filter <br/>
     * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
     */
    SUB_THRESH_CRASH(212), //tiwiPro
    
    /**
     * Note to indicate tampering has occurred and been detected after the fact.  Unlike the unplugged note, this will not end a trip.
     */
    UNPLUGGED_ASLEEP(213,EventType.TAMPERING),
    
    /**
     * Note that is not actually sent to the portal.  During initialize notification data it is modified to a normal idling, but the time
     * is always: now() - 1.
     */
//    IDLE_FAKETIME(214),

    /**
     * This note indicates that a state change has occured for one of the tiwiPro general purpose inputs.  Attribute ATTR_GPINPUT_STATE(57) is sent with this note
     * and is a one byte value consisting of the input states in the first 3 bits.
     */

    CHECK_HOURS_EX(215),
    
    /**
     * This note indicates that the vehicle has started moving.  This event is used primarily for cell phone control (ZoomSafer, Cell Control) and
     * is disabled by default.
     * Attributes None
     */
    DRIVER_HISTOGRAM_STATS(216),
    
    /**
     * This note indicates that the vehicle has stopped moving.  This event is used primarily for cell phone control (ZoomSafer, Cell Control) and
     * is disabled by default.  The timeout for no motion is usually 2 minutes or on ignition off.
     * Attributes None
     */
    STOP_MOTION(217), // reserved for tiwiPro
    OFF_HOURS_DRIVING(218),
    SAT_EVENT_RF_KILL(219, EventType.RF_SWITCH),
    
    DIAGNOSTIC(220), // send general diagnostic info to server
    MAN_DOWN_EX(221),
    STATS2(222),
    
    // new notifications without header information - not really a real notification.
    // used primarily for background communication
    STRIPPED_LOWER_LIMIT(243),
    
    /**
     * Sent on a forward command acknowledgement
     * Attributes ATTR_FWDCMD_ID, ATTR_FWDCMD_STATUS, ATTR_FWDCMD_COMMAND, [ATTR_FWDCMD_ERROR], [ATTR_VIOLATION_FLAGS]
     */
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    
    STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250), //WAYSMART
    STRIPPED_GET_SHORT_ID       (251), //WAYSMART
    GET_OCCUPANTS_STRIPPED        (252), //WAYSMART
    GET_OCCUPANT_INFO_STRIPPED    (253), //WAYSMART
    
    STRIPPED_ACKNOWLEDGE(254),
    
    STRIPPED_UPPER_LIMIT(255),
    
    MAX_ID(300),
    ;
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
