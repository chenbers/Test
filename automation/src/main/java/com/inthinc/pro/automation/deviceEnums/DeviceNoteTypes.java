package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;
import java.util.HashMap;

import com.inthinc.pro.automation.interfaces.DeviceTypes;

public enum DeviceNoteTypes implements DeviceTypes{
    

    FULLEVENT(1, DeviceAttrs.DELTA_VS),
    NOTE_EVENT(2, DeviceAttrs.DELTA_VS),
    SEATBELT(3, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    SPEEDING(4, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT, DeviceAttrs.AVG_SPEED, DeviceAttrs.AVG_RPM),
    THEFT(5),
    LOCATION(6),
    NEWDRIVER(7, DeviceAttrs.DRIVER_STR, DeviceAttrs.MCM_RULESET),
    MANDOWN(8),
    PANIC(9),
    
    HOME(11),
    CHECKUNIT(12, DeviceAttrs.BATTERY_VOLTAGE),
    GEOFENCE(13),
    RPM(14, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    MAN_OK(15),
    TEMPERATURE(18),
    IGNITION_ON(19),
    
    IGNITION_OFF(20),    
    MODEM_OFF(21),
    LOW_BATTERY(22),
    ACCELERATION(23, DeviceAttrs.ACCELERATION),
    DECELERATION(24, DeviceAttrs.ACCELERATION),
    WATCHDOG(25),
    REBOOT(26),
    VERSION(27, DeviceAttrs.FIRMWARE_VERSION),
    GPS_STATE(28, DeviceAttrs.GPS_STATE),
    DATA(29),
    
    EVENTDATA(30),
    CONF(31),
    ACKNOWLEDGE(33, DeviceAttrs.FORWARD_COMMAND_ID),
    TRACE(34),
    INSTALL(35, DeviceAttrs.VEHICLE_ID_STR, DeviceAttrs.MCM_ID_STR, DeviceAttrs.COMPANY_ID),
    BASE_VERSION(36, DeviceAttrs.FIRMWARE_VERSION),
    MISSING_PARTS(37),
    HEADLIGHT(38, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    BOUNDARY_CHANGE(39, DeviceAttrs.BOUNDARY_ID),
    

    DRIVERSTATE_CHANGE(40, DeviceAttrs.DRIVER_FLAG, DeviceAttrs.TRIP_REPORT_FLAG, DeviceAttrs.TRIP_INSPECTION_FLAG, DeviceAttrs.CLEAR_DRIVER_FLAG, DeviceAttrs.DRIVER_STR, DeviceAttrs.LOCATION, DeviceAttrs.DRIVER_STATUS),
    ENDOFMONTH(41),
    TIMESTAMP(42),
    HOS_RECEIVED(43),
    LOCATIONDEBUG(44),
    CRASH_DATA(45),
    NO_DRIVER(46, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    ON_ROAD(47),
    OFF_ROAD(48),
    SPEEDING_EX(49, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT),
    
    ERROR(50),
    STATS(51, DeviceAttrs.LOCK_PERCENTAGE, DeviceAttrs.NO_STRING_PERCENTAGE, DeviceAttrs.IGNITIONON_PERCENTAGE, DeviceAttrs.SEATBELT_IN_PERCENTAGE, DeviceAttrs.FILE_SIZES, DeviceAttrs.REBOOTS, DeviceAttrs.AVERAGE_LOCK_TIME, DeviceAttrs.LOOPS, DeviceAttrs.MESSAGES_LATENT, DeviceAttrs.MESSAGES_SIZE, DeviceAttrs.ODOMETER_ODB_COUNT, DeviceAttrs.ODOMETER_GPS_COUNT, DeviceAttrs.MAPS_MOUNTED_PERCENTAGE, DeviceAttrs.MAPS_UNMOUNTED_COUNT),
    LOW_POWER_MODE(52),
    SATELLITE_SWITCH(53, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    WITNESS_HEARTBEAT_VIOLATION(54),
    DRIVER_HOURS(55, DeviceAttrs.DRIVER_STR),
    DOT_STOPPED(56, DeviceAttrs.REASON_CODE_DOT),
    ODOMETER(57),
    SPEEDING_EX2(58, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT, DeviceAttrs.AVG_SPEED),
    NO_TRAILER(59, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM),
    
    _24HR_REBOOT(60),
    FIRMWARE_REBOOT(61),
    KEYPAD_REBOOT(62),
    CMD_REBOOT(63),
    TRAILER_DATA(64, DeviceAttrs.DATA_LENGTH, DeviceAttrs.TRAILER_ID, DeviceAttrs.SERVICE_ID, DeviceAttrs.HAZMAT_FLAG),
    KEYPAD(65),
    CLEAR_DRIVER(66),
    HOS_NO_HOURS(67, DeviceAttrs.REASON_CODE_HOS),
    HOS_LOG_RECORD(68),
    HOS_SUMMARY_RECEIVED(69),

    HOS_SUMMARY_RECORD(70),
    WAYPOINT(71),
    TEXT_MSG(72, DeviceAttrs.TEXT_MESSAGE),
    FUEL_STOP(73, DeviceAttrs.VEHICLE_GALLONS, DeviceAttrs.ODOMETER, DeviceAttrs.TRAILER_GALLONS, DeviceAttrs.LOCATION),
    FIRMWARE_UP_TO_DATE(74, DeviceAttrs.UP_TO_DATE_STATUS),
    MAPS_UP_TO_DATE(75, DeviceAttrs.UP_TO_DATE_STATUS),
    ZONES_UP_TO_DATE(76, DeviceAttrs.UP_TO_DATE_STATUS),
    BOOT_LOADER_UP_TO_DATE(77, DeviceAttrs.UP_TO_DATE_STATUS),
    WSZONES_ARRIVAL(78),
    

    TEXT_MSG_CANNED(80, DeviceAttrs.EVENT_CODE),
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
    XCAT(91, DeviceAttrs.X_CAT_EVENT, DeviceAttrs.X_CAT_DATA),
    DMR(92, DeviceAttrs.EVENT_CODE_INT),
    SPEEDING_EX3(93, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT, DeviceAttrs.AVG_SPEED, DeviceAttrs.AVG_RPM),
    COLILO_VERSION(94),
    DMM_STATUS(95, DeviceAttrs.MAGICA, DeviceAttrs.ORIENTATION_TRIAX, DeviceAttrs.DMM_ZLEVEL, DeviceAttrs.RMS_LEVEL, DeviceAttrs.RMS_WINDOW, DeviceAttrs.Y_WINDOW, DeviceAttrs.DMM_YLEVEL, DeviceAttrs.X_ACCEL, DeviceAttrs.SLOPE, DeviceAttrs.DVX, DeviceAttrs.CAL_VERSION, DeviceAttrs.NUMERATOR, DeviceAttrs.DENOMINATOR, DeviceAttrs.INTERCEPTS, DeviceAttrs.G_TRIGGER_LEVEL, DeviceAttrs.DIAGNOSTIC),
    DRIVERSTATE_CHANGE_EX(96, DeviceAttrs.DRIVER_STATUS, DeviceAttrs.DRIVER_FLAG, DeviceAttrs.DRIVER_STR),
    LIGHT_DUTY(97),
    HEAVY_DUTY(98),
    MISSING_IWI_CONF(99),
    

    INVALID_OCCUPANT(104),

    TRIAX_STATUS_EX(110, DeviceAttrs.MAGICA, DeviceAttrs.ORIENTATION_TRIAX, DeviceAttrs.DMM_ZLEVEL, DeviceAttrs.RMS_LEVEL, DeviceAttrs.RMS_WINDOW, DeviceAttrs.Y_WINDOW, DeviceAttrs.DMM_YLEVEL, DeviceAttrs.X_ACCEL, DeviceAttrs.SLOPE, DeviceAttrs.DVX, DeviceAttrs.CAL_VERSION, DeviceAttrs.NUMERATOR, DeviceAttrs.DENOMINATOR, DeviceAttrs.INTERCEPTS, DeviceAttrs.G_TRIGGER_LEVEL, DeviceAttrs.DIAGNOSTIC),
    CRASH_DATA_EXTENDED(112),
    A_AND_D_SPACE___HOS_CHANGE_STATE_NO_GPS_LOCK(113, DeviceAttrs.DRIVER_STATUS, DeviceAttrs.DRIVER_FLAG, DeviceAttrs.DRIVER_STR, DeviceAttrs.LOCATION),
    NEWDRIVER_HOSRULE(116, DeviceAttrs.DRIVER_STR, DeviceAttrs.MCM_RULESET),
    WSZONES_ARRIVAL_EX(117, DeviceAttrs.ZONE_ID),
    WSZONES_DEPARTURE_EX(118, DeviceAttrs.ZONE_ID),
    REQUEST_SPECIFIC_DETAIL_RECORDS(119),
    
    WITNESSII_STATUS(120),
    WITNESSII_LIST(121),
    QSI_UP_TO_DATE(122, DeviceAttrs.UP_TO_DATE_STATUS),
    WITNESS_UP_TO_DATE(123, DeviceAttrs.UP_TO_DATE_STATUS),
    TRIAX_UP_TO_DATE(124, DeviceAttrs.UP_TO_DATE_STATUS),
    TRIAX_STATUS_DIAGNOSTIC(125, DeviceAttrs.MAGICA, DeviceAttrs.ORIENTATION_TRIAX, DeviceAttrs.DMM_ZLEVEL, DeviceAttrs.RMS_LEVEL, DeviceAttrs.RMS_WINDOW, DeviceAttrs.Y_WINDOW, DeviceAttrs.DMM_YLEVEL, DeviceAttrs.X_ACCEL, DeviceAttrs.SLOPE, DeviceAttrs.DVX, DeviceAttrs.CAL_VERSION, DeviceAttrs.NUMERATOR, DeviceAttrs.DENOMINATOR, DeviceAttrs.INTERCEPTS, DeviceAttrs.G_TRIGGER_LEVEL, DeviceAttrs.DIAGNOSTIC),
    IWICONF_LOAD_ERROR(126),
    INSTALLATION_CHECKLIST(127),
    WITNESSII_CHANGE_CONFIG_ACK(128, DeviceAttrs.FORWARD_COMMAND_ID, DeviceAttrs.ERROR_CODE, DeviceAttrs.DATA_EXPECTED, DeviceAttrs.DATA_CURRENT),
    GET_WITNESSII_STATUS(129, DeviceAttrs.ERROR_CODE_CONF, DeviceAttrs.STRUCT_VERSION, DeviceAttrs.FIRMWARE_VERSION, DeviceAttrs.ORIENTATION_ROLL_PITCH_YAW, DeviceAttrs.CALIBRATION_STATUS, DeviceAttrs.PERCENT_MEMORY_AVAILABLE, DeviceAttrs.DIAGNOSTIC, DeviceAttrs.WITNESS_ID, DeviceAttrs.STATUS_DELTAV_X, DeviceAttrs.STATUS_DELTAV_Y, DeviceAttrs.STATUS_DELTAV_Z, DeviceAttrs.NUMERATOR, DeviceAttrs.DENOMINATOR, DeviceAttrs.INTERCEPTS, DeviceAttrs.TIME_CALIBRATED, DeviceAttrs.TIME_RTC, DeviceAttrs.BOOTLOADER_REV),
    
    WITNESSII_TRACE(130, DeviceAttrs.CRASH_TRACE),
    WITNESSII_TRACE_OVERWRITTEN(131, DeviceAttrs.TIME),
    WITNESSII_ARCHIVE_HEADER(132),
    CLEAR_OCCUPANT(133, DeviceAttrs.OCCUPANT_STR),
    WITNESSII_ARCHIVED_TRACE_UPLOADED(134, DeviceAttrs.WITNESS_II_ARCHIVE_HEADER, DeviceAttrs.WITNESS_II_ARCHIVE),
    ACKNOWLEDGE_UPDATE(135),
    BOUNDARYDAT_UP_TO_DATE(136),
    PLACES2DAT_UP_TO_DATE(137),
    
    IDLE_STATS(140, DeviceAttrs.DURATION, DeviceAttrs.LOW_IDLE_2, DeviceAttrs.HIGH_IDLE_2),
    WITNESSII_EVENT_DIAGNOSTIC(141),
    AUTOMANDOWN(142),
    AUTO_MAN_OK(143),
    SMTOOLS_EMULATION_UP_TO_DATE(144, DeviceAttrs.UP_TO_DATE_STATUS),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145, DeviceAttrs.UP_TO_DATE_STATUS),
    SMTOOLS_SILICON_ID___SMTOOLS_SECURITY_DISABLE(146, DeviceAttrs.SENDER, DeviceAttrs.SILICON_ID),
    TRIAX_STATUS_WITNESSIII(147, DeviceAttrs.ERROR_CODE_CONF, DeviceAttrs.MAGICA, DeviceAttrs.ORIENTATION_TRIAX, DeviceAttrs.DMM_ZLEVEL, DeviceAttrs.RMS_LEVEL, DeviceAttrs.RMS_WINDOW, DeviceAttrs.Y_WINDOW, DeviceAttrs.DMM_YLEVEL, DeviceAttrs.X_ACCEL, DeviceAttrs.SLOPE, DeviceAttrs.DVX, DeviceAttrs.CAL_VERSION, DeviceAttrs.NUMERATOR, DeviceAttrs.DENOMINATOR, DeviceAttrs.INTERCEPTS, DeviceAttrs.G_TRIGGER_LEVEL, DeviceAttrs.DIAGNOSTIC,
            DeviceAttrs.BOOTLOADER_REV, DeviceAttrs.TIME_CALIBRATED, DeviceAttrs.TIME_RTC, DeviceAttrs.Y_SLOPE, DeviceAttrs.Z_SLOPE, DeviceAttrs.X_WINDOW, DeviceAttrs.STRUCT_VERSION, DeviceAttrs.Z_WINDOW, DeviceAttrs.TEMP_AT_CALIBRATION, DeviceAttrs.DVY, DeviceAttrs.DVZ, DeviceAttrs.TEMP_SENSE_ADC_ATCAL, DeviceAttrs.TEMP_COMP_SLOPE, DeviceAttrs.TEMP_COMP_SLOPES_IN_BUFFER, DeviceAttrs.TEMP_COMP_SLOPES_MEASURED, DeviceAttrs.TOTAL_TEMP_COMP_SLOPE_ESTIMATES,
            DeviceAttrs.TOTAL_TEMP_COMP_SLOPE_UPDATES, DeviceAttrs.TEMP_COMP_SAMPLE_MAX, DeviceAttrs.TEMP_COMP_SAMPLE_MAX, DeviceAttrs.TEMP_COMP_DATA, DeviceAttrs.TEMP_COMP_ENABLED, DeviceAttrs.WIGGLE_TRIGGER_COUNT_TOTAL, DeviceAttrs.FIRMWARE_REV_PREVIOUS, DeviceAttrs.HARD_ACCEL_LEVEL, DeviceAttrs.HARD_ACCEL_AVERAGE_CHANGE, DeviceAttrs.HARD_ACCEL_DELTA_V, DeviceAttrs.HARD_VERT_DMM_TO_CRASH_RATIO_THRES,
            DeviceAttrs.HARD_VERT_DMM_PEAK_TO_PEAK_LEVEL, DeviceAttrs.PRE_INSTALL_FULL_EVENTS_ENABLED, DeviceAttrs.IGNITION_OFF_FULL_EVENTS_ENABLED),
    SMTOOLS_DEVICE_STATUS(148, DeviceAttrs.MODE_OF_OPERATIONS, DeviceAttrs.WEI_COUNT, DeviceAttrs.ERROR_CODE, DeviceAttrs.POWER_UP_COUNT, DeviceAttrs.WATCHDOG_COUNT, DeviceAttrs.DIGITAL_INPUT_STATUS, DeviceAttrs.DIGITAL_OUTPUT_STATUS, DeviceAttrs.CPU_PERCENTAGE, DeviceAttrs.MAX_TIME,
            DeviceAttrs.RECEIVED_MESSAGE_COUNT, DeviceAttrs.VEHICLE_GALLONS, DeviceAttrs.ERROR_DETAILS, DeviceAttrs.SOFTWARE_RESET_COUNT, DeviceAttrs.EMU_NAME_VERIFIED, DeviceAttrs.EMU_NAME_DEVICE, DeviceAttrs.EMU_NAME_TRANSFORM, DeviceAttrs.SILICON_ID, DeviceAttrs.VIN, DeviceAttrs.SMTOOLSTIMERRUNNING,
            DeviceAttrs.SMTOOLS_RESET_STATUS, DeviceAttrs.SMTOOLS_FIRMWARE_REV, DeviceAttrs.SMTOOLS_HARDWARE_REV),
    OBD_PARAMS_STATUS(149, DeviceAttrs.RPM_COLLECTED, DeviceAttrs.SPEED_COLLECTED, DeviceAttrs.ODOMETER_COLLECTED, DeviceAttrs.BRAKE_COLLECTED, DeviceAttrs.SEATBELT_COLLECTED, DeviceAttrs.RPM_REQUESTS, DeviceAttrs.SPEED_REQUESTS, DeviceAttrs.ODOMETER_REQUESTS, DeviceAttrs.BRAKE_REQUESTS,
            DeviceAttrs.SEATBELT_REQUESTS, DeviceAttrs.RPM_RECEIVES, DeviceAttrs.SPEED_RECEIVES, DeviceAttrs.ODOMETER_RECEIVES, DeviceAttrs.BRAKE_RECEIVES, DeviceAttrs.SEATBELT_RECEIVES),
    
    POWER_ON(150),
    SMTOOLS_ERROR(151),    
    POTENTIAL_CRASH(152, DeviceAttrs.DELTA_VS),
    CANCEL_POTENTIAL_CRASH(153, DeviceAttrs.DELTA_VS),
    USB_REBOOT(154),
    SMTOOLS_DEVICE_STATUS_EX(155),
    OBD_PARAMS_STATUS_EX(156),
    CHECK_HOSMINUTES(157, DeviceAttrs.DRIVER_STR),
    WIRELINE_STATUS(158), // sent after every forward command received
    WIRELINE_ALARM(159), // sent when door alarm siren triggered
    
    MPG_VALUE__WIRELINE_IGN_TEST_NOT_RUN(160),
    WIRELINE_IGN_TEST_FAIL(161),
    WIRELINE_IGN_TEST_PASS(162),
    SHELL_OUTPUT(163),
    NO_MAPS_DRIVE(164),
    AUTODETECT(165),
    FUEL_STOP_EX(166, DeviceAttrs.VEHICLE_GALLONS, DeviceAttrs.ODOMETER, DeviceAttrs.TRAILER_GALLONS, DeviceAttrs.LOCATION),
    LOWPOWER_TAMPER_EVENT(167),
    FULLEVENT_BELOW_THRESHOLD(168),
    ROLLOVER_WAYS(169, DeviceAttrs.DELTA_VS, DeviceAttrs.DURATION),
    
    TRIAX_STATUS_WITNESSIII_TEMPCOMP(170),
    VERTICALEVENT(171, DeviceAttrs.DELTA_VS, DeviceAttrs.DURATION),
    PARKINGBRAKE(172, DeviceAttrs.STATE),
    UNIT_INFO(173, DeviceAttrs.DATA),
    FULLEVENT_CONFIDENCE_LEVEL(174, DeviceAttrs.DELTA_VS, DeviceAttrs.CONFIDENCE_LEVEL),
    CRASH_DATA_HIRES(175),
    DSS_MICROSLEEP(176, DeviceAttrs.DATA),
    DSS_STATISTICS(177, DeviceAttrs.DATA),
    NOTEEVENT_SECONDARY(178, DeviceAttrs.DELTA_VS),
    VERTICALEVENT_SECONDARY(179),
    
    WEEKLY_GPRS_USAGE(180, DeviceAttrs.NOPT_WEEKLY_WRITE_COUNT, DeviceAttrs.UPLOAD_WEEK_TOTAL_WITTNESS_II_TRACE, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_FIRMWARE, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_MAPS, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_ZONES, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_BOOTLOADER, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_QSI_FIRMWARE,
            DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_WITNESS_II_FIRMWARE, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_TRIAX_II_FIRMWARE, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_BOUNDARY_DAT, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_BOUNDARY_DAT, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_EMULATION, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_FIRMWARE, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_CHECK_BYTES,
            DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_DOWNLOAD_BYTES, DeviceAttrs.DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_DOWNLOAD_COUNT, DeviceAttrs.UPLOAD_WEEK_TOTAL_NOTIFICATION_BYTES, DeviceAttrs.UPLOAD_WEEK_TOTAL_NOTIFICATION_COUNT),
    MCM_APP_FIRMWARE_UP_TO_DATE(181, DeviceAttrs.UP_TO_DATE_STATUS),
    REMOTE_AUTO_MANDOWN(182), // automatic -no motion mandown
    REMOTE_MAN_MANDOWN(183), // manual mandown
    REMOTE_OK_MANDOWN(184), // mandown cancelled
    REMOTE_OFF_MANDOWN(185), // remote turned off
    REMOTE_LOW_BATT_MANDOWN(186), // low battery on remote
    REMOTE_ON_MANDONW(187), // remote turned on
    REMOTE_AACK_MANDOWN(188),
    SBS_UPDATE(189, DeviceAttrs.SKIP_INT, DeviceAttrs.FILE_NAME, DeviceAttrs.BASELINE_VERSION, DeviceAttrs.EXCEPTION_VERSION, DeviceAttrs.TIME_LAST_VISTED, DeviceAttrs.TIME_LAST_CHECKED, DeviceAttrs.MAP_HASH, DeviceAttrs.MAP_FILE_SIZE, DeviceAttrs.MIN_LATITUDE, DeviceAttrs.MIN_LONGITUDE, DeviceAttrs.MAX_LATITUDE, DeviceAttrs.MAX_LONGITUDE, DeviceAttrs.SBS_EX_MAP_UPDATE_RESULT, DeviceAttrs.SBS_DB_UPDATE_RESULT), // remote turned on
    

    EMU_TARBALL_UP_TO_DATE(190),
    SPEEDING_EX4(191, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT, DeviceAttrs.AVG_SPEED, DeviceAttrs.AVG_RPM, DeviceAttrs.SBS_LINK_ID, DeviceAttrs.ZONE_ID, DeviceAttrs.SPEEDING_TYPE, DeviceAttrs.SEATBELT_ENGAGED, DeviceAttrs.START_TIME, DeviceAttrs.STOP_TIME, DeviceAttrs.MAX_TIME, DeviceAttrs.COURSE, DeviceAttrs.MAX_SPEED_LIMIT, DeviceAttrs.SBS_SPEED_LIMIT, DeviceAttrs.ZONE_SPEED_LIMIT,
            DeviceAttrs.WEATHER_SPEED_LIMIT_PERCENT, DeviceAttrs.SEVERE_SPEED_THRESHOLD, DeviceAttrs.SPEEDING_BUFFER, DeviceAttrs.SPEEDING_GRACE_PERIOD, DeviceAttrs.SEVERE_SPEED_SECONDS, DeviceAttrs.SPEED_MODULE_ENABLED, DeviceAttrs.SPEED_SOURCE),
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
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246, DeviceAttrs.FWDCMD_COMMAND, DeviceAttrs.ACK_DATA, DeviceAttrs.FWDCMD_ID),
    ZONE_ALERTED(247),
    ZONE_EXIT_ALERTED___WIFI_EVENT_STRIPPED_GET_WITNESSII_EVENT_TRACE(248, DeviceAttrs.CRASH_TRACE),
    STRIPPED_GET_WITNESSII_EVENT_LIST_EX(249),


    STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250),
    STRIPPED_GET_SHORT_ID(251, DeviceAttrs.EMP_ID),
    GET_OCCUPANTS_STRIPPED(252, DeviceAttrs.EMP_ID),
    GET_OCCUPANT_INFO_STRIPPED(253, DeviceAttrs.EMP_ID),
    STRIPPED_ACKNOWLEDGE(254),
    STRIPPED_UPPER_LIMIT(255),
    
    MAX_ID(300),

    ;

    

    private int code;
    private DeviceAttrs[] attributes;

    private DeviceNoteTypes(int c, DeviceAttrs ...attributes) {
        code = c;
        this.attributes = attributes;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public DeviceAttrs[] getAttributes(){
        return attributes;
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
