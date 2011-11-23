package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;
import java.util.HashMap;

import com.inthinc.pro.automation.interfaces.DeviceTypes;

public enum DeviceNoteTypes implements DeviceTypes{
    

    FULLEVENT(1),
    NOTE_EVENT(2),
    SEATBELT(3),
    SPEEDING(4),
    THEFT(5),
    LOCATION(6),
    NEWDRIVER(7),
    MANDOWN(8),
    PANIC(9),
    
    HOME(11),
    CHECKUNIT(12),
    GEOFENCE(13),
    RPM(14),
    MAN_OK(15),
    TEMPERATURE(18),
    IGNITION_ON(19),
    
    IGNITION_OFF(20),    
    MODEM_OFF(21),
    LOW_BATTERY(22),
    ACCELERATION(23),
    DECELERATION(24),
    WATCHDOG(25),
    REBOOT(26),
    VERSION(27),
    GPS_STATE(28),
    DATA(29),
    
    EVENTDATA(30),
    CONF(31),
    ACKNOWLEDGE(33),
    TRACE(34),
    INSTALL(35),
    BASE_VERSION(36),
    MISSING_PARTS(37),
    HEADLIGHT(38),
    BOUNDARY_CHANGE(39),
    

    DRIVERSTATE_CHANGE(40),
    ENDOFMONTH(41),
    TIMESTAMP(42),
    HOS_RECEIVED(43),
    LOCATIONDEBUG(44),
    CRASH_DATA(45),
    NO_DRIVER(46),
    ON_ROAD(47),
    OFF_ROAD(48),
    SPEEDING_EX(49),
    
    ERROR(50),
    STATS(51),
    LOW_POWER_MODE(52),
    SATELLITE_SWITCH(53),
    WITNESS_HEARTBEAT_VIOLATION(54),
    DRIVER_HOURS(55),
    DOT_STOPPED(56),
    ODOMETER(57),
    SPEEDING_EX2(58),
    NO_TRAILER(59),
    
    _24HR_REBOOT(60),
    FIRMWARE_REBOOT(61),
    KEYPAD_REBOOT(62),
    CMD_REBOOT(63),
    TRAILER_DATA(64),
    KEYPAD(65),
    CLEAR_DRIVER(66),
    HOS_NO_HOURS(67),
    HOS_LOG_RECORD(68),
    HOS_SUMMARY_RECEIVED(69),

    HOS_SUMMARY_RECORD(70),
    WAYPOINT(71),
    TEXT_MSG(72),
    FUEL_STOP(73),
    FIRMWARE_UP_TO_DATE(74),
    MAPS_UP_TO_DATE(75),
    ZONES_UP_TO_DATE(76),
    BOOT_LOADER_UP_TO_DATE(77),
    WSZONES_ARRIVAL(78),
    

    TEXT_MSG_CANNED(80),
    QUEUE_FULL(81),
    GPIO_SHUT_DOWN(82),
    WAYPOINT_EX(83),
    MANDOWN_PENDING(84),
    PRE_TRIP_INSPECTION(85),
    POST_TRIP_INSPECTION(86),
    WSZONES_DEPARTURE(87),
    HOS_30_MIN(88),
    HOS_MINUS_15(89),
    
    HOS_MINUS_30(90),
    XCAT(91),
    DMR(92),
    SPEEDING_EX3(93),
    COLILO_VERSION(94),
    DMM_STATUS(95),
    DRIVERSTATE_CHANGE_EX(96),
    LIGHT_DUTY(97),
    HEAVY_DUTY(98),
    MISSING_IWI_CONF(99),
    

    INVALID_OCCUPANT(104),

    TRIAX_STATUS_EX(110),
    CRASH_DATA_EXTENDED(112),
    _A_AND_D_SPACE___HOS_CHANGE_STATE_NO_GPS_LOCK(113),
    NEWDRIVER_HOSRULE(116),
    REQUEST_SPECIFIC_DETAIL_RECORDS(119),
    WSZONES_ARRIVAL_EX(117),
    WSZONES_DEPARTURE_EX(118),
    
    WITNESSII_STATUS(120),
    WITNESSII_LIST(121),
    QSI_UP_TO_DATE(122),
    WITNESS_UP_TO_DATE(123),
    TRIAX_UP_TO_DATE(124),
    TRIAX_STATUS_DIAGNOSTIC(125),
    IWICONF_LOAD_ERROR(126),
    INSTALLATION_CHECKLIST(127),
    WITNESSII_CHANGE_CONFIG_ACK(128),
    GET_WITNESSII_STATUS(129),
    
    WITNESSII_TRACE(130),
    WITNESSII_TRACE_OVERWRITTEN(131),
    WITNESSII_ARCHIVE_HEADER(132),
    CLEAR_OCCUPANT(133),
    WITNESSII_ARCHIVED_TRACE_UPLOADED(134),
    ACKNOWLEDGE_UPDATE(135),
    BOUNDARYDAT_UP_TO_DATE(136),
    PLACES2DAT_UP_TO_DATE(137),
    
    IDLE_STATS(140),
    WITNESSII_EVENT_DIAGNOSTIC(141),
    AUTOMANDOWN(142),
    AUTO_MAN_OK(143),
    SMTOOLS_EMULATION_UP_TO_DATE(144),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    SMTOOLS_SILICON_ID___SMTOOLS_SECURITY_DISABLE(146),
    TRIAX_STATUS_WITNESSIII(147),
    SMTOOLS_DEVICE_STATUS(148),
    OBD_PARAMS_STATUS(149),
    
    POWER_ON(150),
    SMTOOLS_ERROR(151),    
    POTENTIAL_CRASH(152),
    CANCEL_POTENTIAL_CRASH(153),
    USB_REBOOT(154),
    SMTOOLS_DEVICE_STATUS_EX(155),
    OBD_PARAMS_STATUS_EX(156),
    CHECK_HOSMINUTES(157),
    WIRELINE_STATUS(158), // sent after every forward command received
    WIRELINE_ALARM(159), // sent when door alarm siren triggered
    
    MPG_VALUE__WIRELINE_IGN_TEST_NOT_RUN(160),
    WIRELINE_IGN_TEST_FAIL(161),
    WIRELINE_IGN_TEST_PASS(162),
    SHELL_OUTPUT(163),
    NO_MAPS_DRIVE(164),
    AUTODETECT(165),
    FUEL_STOP_EX(166),
    LOWPOWER_TAMPER_EVENT(167),
    FULLEVENT_BELOW_THRESHOLD(168),
    ROLLOVER_WAYS(169),
    
    TRIAX_STATUS_WITNESSIII_TEMPCOMP(170),
    VERTICALEVENT(171),
    PARKINGBRAKE(172),
    UNIT_INFO(173),
    FULLEVENT_CONFIDENCE_LEVEL(174),
    CRASH_DATA_HIRES(175),
    DSS_MICROSLEEP(176),
    DSS_STATISTICS(177),
    NOTEEVENT_SECONDARY(178),
    VERTICALEVENT_SECONDARY(179),
    
    WEEKLY_GPRS_USAGE(180),
    MCM_APP_FIRMWARE_UP_TO_DATE(181),
    REMOTE_AUTO_MANDOWN(182), // automatic -no motion mandown
    REMOTE_MAN_MANDOWN(183), // manual mandown
    REMOTE_OK_MANDOWN(184), // mandown cancelled
    REMOTE_OFF_MANDOWN(185), // remote turned off
    REMOTE_LOW_BATT_MANDOWN(186), // low battery on remote
    REMOTE_ON_MANDONW(187), // remote turned on
    REMOTE_AACK_MANDOWN(188),
    SBS_UPDATE(189), // remote turned on
    

    EMU_TARBALL_UP_TO_DATE(190),
    SPEEDING_EX4(191),
    SPEEDING_LOG4(192),
    BLAST_ZONE_RECEIVED(193),
    SPEEDING_AV(194),
    UNIT_TEST_INFORMATION(195),
    UNIT_TEST_WARNING(196),
    UNIT_TEST_CRITICAL(197),
    
    DIAGNOSTICS_REPORT(200),
    SPEEDING_START(201),
    UNPLUGGED(202),
    SEATBELT_START(203),
    DMM_MONITOR(204),
    INCOMING_CALL(205),
    OUTGOING_CALL(206),
    LOW_BACKUP_BATTERY(207),
    IDLING(208),
    ROLLOVER(209),
    
    COACH_SPEEDING(210),
    COACH_SEATBELT(211),
    SUB_THRESHOLD_CRASH(212),
    UNPLUGGED_WHILE_ASLEEP(213),
    IDLE_FAKETIME(214),

    // new notifications without header information - not really a real notification.
    // used primarily for background communication
    STRIPPED_LOWER_LIMIT(243),
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    ZONE_ALERTED(247),
    ZONE_EXIT_ALERTED___WIFI_EVENT_STRIPPED_GET_WITNESSII_EVENT_TRACE(248),
    STRIPPED_GET_WITNESSII_EVENT_LIST_EX(249),


    STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250),
    STRIPPED_GET_SHORT_ID(251),
    GET_OCCUPANTS_STRIPPED(252),
    GET_OCCUPANT_INFO_STRIPPED(253),
    STRIPPED_ACKNOWLEDGE(254),
    STRIPPED_UPPER_LIMIT(255),
    
    MAX_ID(300),

    STATIC, 
    
    ;

    

    private int code;

    private DeviceNoteTypes() {}
    private DeviceNoteTypes(int c) {
        code = c;
    }
    public Integer getCode() {
        return code;
    } 
    
    private static HashMap<Integer, DeviceNoteTypes> lookupByCode = new HashMap<Integer, DeviceNoteTypes>();
    
    static {
        for (DeviceNoteTypes p : EnumSet.allOf(DeviceNoteTypes.class))
        {
            lookupByCode.put(p.getCode(), p);
        }
    }
    
    public DeviceNoteTypes valueOf(Integer code){
        return lookupByCode.get(code);
    }
    
    @Override
    public String toString(){
        return this.name() + "(" + code + ")";
    }

}
