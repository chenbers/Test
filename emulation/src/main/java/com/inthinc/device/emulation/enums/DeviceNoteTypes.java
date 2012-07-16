package com.inthinc.device.emulation.enums;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.inthinc.device.emulation.utils.AutomationFileHandler;
import com.inthinc.pro.automation.utils.AutomationNumberManager;


public enum DeviceNoteTypes  {
    
	/**
	 * Generated when a "crash" is detected <br/>
	 * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
	 */
    FULLEVENT(1, EventAttr.PACKED_DELTAV),

	/**
	 * Generated when aggressive driving is detected <br/>
	 * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
	 */
    NOTE_EVENT(2, EventAttr.PACKED_DELTAV),

	/**
	 * Generated when the seatbelt violation thresholds are exceeded <br/>
	 * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, [ATTR_AVG_RPM], [ATTR_VIOLATION_FLAGS]
	 */
    SEATBELT(3, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    
    SPEEDING(4, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM, EventAttr.SPEED_LIMIT, EventAttr.AVG_SPEED, EventAttr.AVG_RPM),
    THEFT(5),
    
	/**
	 * A generic location note sent every 15 seconds while vehicle is in motion <br/>
	 * Attributes [ATTR_VIOLATION_FLAGS]
	 */
    LOCATION(6),
    NEWDRIVER(7, EventAttr.DRIVER_ID_STR, EventAttr.CURRENT_HOS_RULESET),
    MANDOWN(8),
    PANIC(9),
    
    HOME(11),
    CHECKUNIT(12, EventAttr.BATTERY_VOLTAGE),
    GEOFENCE(13),
    
    /**
     * Sent if vehicle exceeds RPM threshold <br/>
	 * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, ATTR_AVG_RPM, [ATTR_VIOLATION_FLAGS]
     */
    RPM(14, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    MAN_OK(15),
    TEMPERATURE(18),

	/**
	 * Sent when ignition on is detected <br/>
	 * Attributes ATTR_CURRENT_IGN, [ATTR_NUM_GPS_REBOOTS], [ATTR_VIOLATION_FLAGS]
	 */
    IGNITION_ON(19),


	/**
	 * Sent when ignition off is detected <br/>
	 * Attributes [ATTR_MPG], [ATTR_MPG_DISTANCE], [ATTR_TRIP_DURATION], ATTR_PERCENTAGE_GPS_FILTERED, [ATTR_SPEEDING_SQUELCHED], ATTR_CURRENT_IGN, [ATTR_NUM_GPS_REBOOTS], [ATTR_OBD_PCT], [ATTR_GPS_PCT], [ATTR_AGPS_DOWNLOADED], [ATTR_VIOLATION_FLAGS]
	 */
    IGNITION_OFF(20),    
    MODEM_OFF(21),

	/**
	 * Sent if we detect low vehicle battery <br/>
	 * Attributes ATTR_VEHICLE_BATTERY, [ATTR_VIOLATION_FLAGS]
	 */
    LOW_BATTERY(22),
    ACCELERATION(23, EventAttr.ACCELERATION),
    DECELERATION(24, EventAttr.ACCELERATION),
    WATCHDOG(25),
    REBOOT(26),

	/**
	 * response to GET_VERSION fwd cmd <br/>
	 * Attributes ATTR_FIRMWARE_VERSION, [ATTR_DMM_VERSION], [ATTR_EMU_HASH_1], [ATTR_EMU_HASH_2], [ATTR_EMU_HASH_3], [ATTR_EMU_HASH_4], [ATTR_VIOLATION_FLAGS]
	 */
    VERSION(27, EventAttr.FIRMWARE_VERSION),
    GPS_STATE(28, EventAttr.GPS_STATE),
    DATA(29),
    
    EVENTDATA(30),
    CONF(31),
    ACKNOWLEDGE(33, EventAttr.FORWARD_COMMAND_ID),
    TRACE(34),
    INSTALL(35, EventAttr.VEHICLE_ID_STR, EventAttr.MCM_ID_STR, EventAttr.COMPANY_ID),
    BASE_VERSION(36, EventAttr.FIRMWARE_VERSION),
    MISSING_PARTS(37),
    HEADLIGHT(38, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    BOUNDARY_CHANGE(39, EventAttr.BOUNDARY_ID),
    

    HOS_CHANGE_STATE(40, EventAttr.CLEAR_DRIVER_FLAG, EventAttr.TRIP_REPORT_FLAG, EventAttr.TRIP_INSPECTION_FLAG, EventAttr.CLEAR_DRIVER_FLAG_WHAT, EventAttr.DRIVER_ID_STR, EventAttr.NO_GPS_LOCK_LOCATION, EventAttr.DRIVER_HOS_STATE),
    MONTHLY_UPDATE(41),
    TIMESTAMP(42),
    HOS_RECEIVED(43),
    LOCATION_DEBUG(44),
    CRASH_DATA(45),

	/**
	 * Sent when driving without a driver logged in <br/>
	 * Attributes [ATTR_VIOLATION_FLAGS]
	 */
    NO_DRIVER(46, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    ON_ROAD(47),
    OFF_ROAD(48),
    SPEEDING_EX(49, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM, EventAttr.SPEED_LIMIT),
    
    ERROR(50),

    /**
     * Sent weekly to give diagnostics on how the unit is performing <br/>
     * Attributes [ATTR_EMU_HASH_1], [ATTR_EMU_HASH_2], [ATTR_EMU_HASH_3], [ATTR_EMU_HASH_4], [ATTR_TOTAL_AGPS_BYTES], [ATTR_VIOLATION_FLAGS], ATTR_TOTAL_BYTES_DUMPSET
     */
    STATS(51, EventAttr.LOCK_PERCENTAGE, EventAttr.NO_STRING_PERCENTAGE, EventAttr.IGNITION_ON_PERCENTAGE, EventAttr.SEATBELT_IN_PERCENTAGE, EventAttr.FILE_SIZES, EventAttr.REBOOTS, EventAttr.AVERAGE_LOCK_TIME, EventAttr.LOOPS, EventAttr.MESSAGES_LATENT, EventAttr.MESSAGES_SIZE, EventAttr.ODOMETER_OBD_COUNT, EventAttr.ODOMETER_GPS_COUNT, EventAttr.MAPS_MOUNTED_PERCENTAGE, EventAttr.MAPS_UNMOUNTED_COUNT),

	/**
	 * Sent when device goes into low power mode <br/>
	 * Attributes ATTR_LOW_POWER_MODE_TIMEOUT, [ATTR_VIOLATION_FLAGS]
	 */
    LOW_POWER_MODE(52),
    SATELLITE_SWITCH(53, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    WITNESS_HEARTBEAT_VIOLATION(54),
    DRIVER_HOURS(55, EventAttr.DRIVER_ID_STR),
    DOT_STOPPED(56, EventAttr.REASON_CODE_DOT),
    ODOMETER(57),
    SPEEDING_EX2(58, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM, EventAttr.SPEED_LIMIT, EventAttr.AVG_SPEED),
    NO_TRAILER(59, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM),
    
    _24HR_REBOOT(60),
    FIRMWARE_REBOOT(61),
    KEYPAD_REBOOT(62),
    CMD_REBOOT(63),
    TRAILER_DATA(64, EventAttr.DATA_LENGTH, EventAttr.TRAILER_ID, EventAttr.SERVICE_ID, EventAttr.HAZMAT_FLAG),
    KEYPAD(65),

	/**
	 * Sent when driver logs out of the vehicle using RFID card <br/>
	 * Attributes ATTR_DRIVER_ID (if available) or ATTR_RFID0, ATTR_RFID0, [ATTR_VIOLATION_FLAGS]
	 */
    CLEAR_DRIVER(66),
    HOS_NO_HOURS(67, EventAttr.REASON_CODE_HOS),
    HOS_LOG_RECORD(68),
    HOS_SUMMARY_RECEIVED(69),

    HOS_SUMMARY_RECORD(70),
    WAYPOINT(71),
    TEXT_MSG(72, EventAttr.TEXT_MESSAGE),
    FUEL_STOP(73, EventAttr.VEHICLE_GALLONS, EventAttr.ODOMETER, EventAttr.TRAILER_GALLONS, EventAttr.NO_GPS_LOCK_LOCATION),
    FIRMWARE_UP_TO_DATE(74, EventAttr.UP_TO_DATE_STATUS),
    MAPS_UP_TO_DATE(75, EventAttr.UP_TO_DATE_STATUS),
    ZONES_UP_TO_DATE(76, EventAttr.UP_TO_DATE_STATUS),
    BOOT_LOADER_UP_TO_DATE(77, EventAttr.UP_TO_DATE_STATUS),

    /**
     * Sent on a zone arrival, used in the case where the zone id is not known. <br/>
     * Attributes ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_ARRIVAL(78),

    WHAT_THE_HECK(79),

    TEXT_MSG_CANNED(80, EventAttr.EVENT_CODE),
    QUEUE_FULL(81),
    GPIO_SHUT_DOWN(82),
    WAYPOINT_EX(83),
    MANDOWN_PENDING(84),
    PRE_TRIP_INSPECTION(85),
    POST_TRIP_INSPECTION(86),

    /**
     * Sent on a zone departure, used in the case where the zone id is not known <br/>
     * Attributes ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_DEPARTURE(87),
    HOS_30_MIN(88),
    HOS_MINUS_15(89),
    
    HOS_MINUS_30(90),
    XCAT(91, EventAttr.XCAT_EVENT, EventAttr.XCAT_DATA),
    DMR(92, EventAttr.SPEED_LAT_START),

	/**
	 * Sent at the end of a speeding violation <br/>
	 * Attributes ATTR_TOP_SPEED, ATTR_AVG_SPEED, ATTR_SPEED_LIMIT, ATTR_DISTANCE, [ATTR_AVG_RPM], [ATTR_VIOLATION_FLAGS]
	 */
    SPEEDING_EX3(93, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM, EventAttr.SPEED_LIMIT, EventAttr.AVG_SPEED, EventAttr.AVG_RPM),
    COLILO_VERSION(94),

	/**
	 * Sent as a response to GET_DMM_STATUS forward command <br/>
	 * Attributes ATTR_MSP_CRASH_X, ATTR_MSP_CRASH_Y, ATTR_MSP_CRASH_Z, ATTR_MSP_HARD_ACCEL_LEVEL, ATTR_MSP_HARD_ACCEL_DV, ATTR_MSP_HARD_BRAKE_LEVEL, ATTR_MSP_HARD_BRAKE_DV, ATTR_MSP_HARD_TURN_LEVEL, ATTR_MSP_HARD_TURN_DV, ATTR_MSP_HARD_BUMP_RMSLEV, ATTR_MSP_HARD_BUMP_PK2PKLEV, ATTR_MSP_TEMPSLOPE_U, ATTR_MSP_TEMPSLOPE_V, ATTR_MSP_TEMPSLOPE_W,, [ATTR_VIOLATION_FLAGS]
	 */
    DMM_STATUS(95, EventAttr.MAGICA, EventAttr.ORIENTATION_TRIAX, EventAttr.DMM_ZLEVEL, EventAttr.RMS_LEVEL, EventAttr.RMS_WINDWO, EventAttr.YWINDOW, EventAttr.DMM_YLEVEL, EventAttr.XACCEL, EventAttr.SLOPE, EventAttr.DVX, EventAttr.CAL_VERSION, EventAttr.NUMERATOR, EventAttr.DENOMINATOR, EventAttr.INTERCEPTS, EventAttr.G_TRIGGER_LEVEL, EventAttr.DIAGNOSTIC),
    HOS_CHANGE_STATE_EX(96, EventAttr.DRIVER_HOS_STATE, EventAttr.CLEAR_DRIVER_FLAG, EventAttr.DRIVER_ID_STR),
    LIGHT_DUTY(97),
    HEAVY_DUTY(98),
    MISSING_IWI_CONF(99),
    

    INVALID_OCCUPANT(104),

    TRIAX_STATUS_EX(110, EventAttr.MAGICA, EventAttr.ORIENTATION_TRIAX, EventAttr.DMM_ZLEVEL, EventAttr.RMS_LEVEL, EventAttr.RMS_WINDWO, EventAttr.YWINDOW, EventAttr.DMM_YLEVEL, EventAttr.XACCEL, EventAttr.SLOPE, EventAttr.DVX, EventAttr.CAL_VERSION, EventAttr.NUMERATOR, EventAttr.DENOMINATOR, EventAttr.INTERCEPTS, EventAttr.G_TRIGGER_LEVEL, EventAttr.DIAGNOSTIC),
    CRASH_DATA_EXTENDED(112),
    HOS_CHANGE_STATE_NO_GPS_LOCK(113, EventAttr.DRIVER_HOS_STATE, EventAttr.CLEAR_DRIVER_FLAG, EventAttr.DRIVER_ID_STR, EventAttr.NO_GPS_LOCK_LOCATION),
    NEWDRIVER_HOSRULE(116, EventAttr.DRIVER_ID_STR, EventAttr.CURRENT_HOS_RULESET),

    /**
     * Sent on a zone arrival <br/>
     * Attributes ATTR_ZONE_ID, ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_ARRIVAL_EX(117, EventAttr.ZONE_ID),

    /**
     * Sent on a zone departure <br/>
     * Attributes ATTR_ZONE_ID, ATTR_GPS_SATS_SNR_MEDIAN, [ATTR_VIOLATION_FLAGS]
     */
    WSZONES_DEPARTURE_EX(118, EventAttr.ZONE_ID),
    REQUEST_SPECIFIC_DETAIL_RECORDS(119),
    
    WITNESSII_STATUS(120),
    WITNESSII_LIST(121),
    QSI_UP_TO_DATE(122, EventAttr.UP_TO_DATE_STATUS),
    WITNESS_UP_TO_DATE(123, EventAttr.UP_TO_DATE_STATUS),
    TRIAX_UP_TO_DATE(124, EventAttr.UP_TO_DATE_STATUS),
    TRIAX_STATUS_DIAGNOSTIC(125, EventAttr.MAGICA, EventAttr.ORIENTATION_TRIAX, EventAttr.DMM_ZLEVEL, EventAttr.RMS_LEVEL, EventAttr.RMS_WINDWO, EventAttr.YWINDOW, EventAttr.DMM_YLEVEL, EventAttr.XACCEL, EventAttr.SLOPE, EventAttr.DVX, EventAttr.CAL_VERSION, EventAttr.NUMERATOR, EventAttr.DENOMINATOR, EventAttr.INTERCEPTS, EventAttr.G_TRIGGER_LEVEL, EventAttr.DIAGNOSTIC),
    IWICONF_LOAD_ERROR(126),
    INSTALLATION_CHECKLIST(127),
    WITNESSII_CHANGE_CONFIG_ACK(128, EventAttr.FORWARD_COMMAND_ID, EventAttr.ERROR_CODE, EventAttr.DATA_EXPECTED, EventAttr.DATA_CURRENT),
    GET_WITNESSII_STATUS(129, EventAttr.ERROR_CODE_CONF, EventAttr.STRUCT_VERSION, EventAttr.FIRMWARE_VERSION, EventAttr.ORIENTATION_ROLL_PITCH_YAW, EventAttr.CALIBRATION_STATUS, EventAttr.PERCENT_MEMORY_AVAILABLE, EventAttr.DIAGNOSTIC, EventAttr.WITNESS_ID, EventAttr.STATUS_DELTAV_X, EventAttr.STATUS_DELTAV_Y, EventAttr.STATUS_DELTAV_Z, EventAttr.NUMERATOR, EventAttr.DENOMINATOR, EventAttr.INTERCEPTS, EventAttr.TIME_CALIBRATED, EventAttr.TIME_RTC, EventAttr.BOOT_LOADER_REV),
    
    WITNESSII_TRACE(130, EventAttr.CRASH_TRACE),
    WITNESSII_TRACE_OVERWRITTEN(131, EventAttr.TIME),
    WITNESSII_ARCHIVE_HEADER(132),
    CLEAR_OCCUPANT(133, EventAttr.OCCUPANT_ID_STR),
    WITNESSII_ARCHIVED_TRACE_UPLOADED(134, EventAttr.WITNESSII_ARCHIVE_HEADER, EventAttr.WITNESSII_ARCHIVE),
    ACKNOWLEDGE_UPDATE(135),
    BOUNDARYDAT_UP_TO_DATE(136),
    PLACES2DAT_UP_TO_DATE(137),
    SBDRING_NULL(138), // not uploaded to portal
    SBDRING(139), // not uploaded to portal
    
    IDLE_STATS(140, EventAttr.DURATION, EventAttr.LOW_IDLE_2, EventAttr.HIGH_IDLE_2),
    WITNESSII_DIAGNOSTIC(141),
    AUTOMANDOWN(142),
    AUTO_MAN_OK(143),
    SMTOOLS_EMULATION_UP_TO_DATE(144, EventAttr.UP_TO_DATE_STATUS),
    SMTOOLS_FIRMWARE_UP_TO_DATE(145, EventAttr.UP_TO_DATE_STATUS),
    SMTOOLS_SILICON_ID___SMTOOLS_SECURITY_DISABLE(146, EventAttr.SENDER, EventAttr.SILICON_ID),
    TRIAX_STATUS_WITNESSIII(147, EventAttr.ERROR_CODE_CONF, EventAttr.MAGICA, EventAttr.ORIENTATION_TRIAX, EventAttr.DMM_ZLEVEL, EventAttr.RMS_LEVEL, EventAttr.RMS_WINDWO, EventAttr.YWINDOW, EventAttr.DMM_YLEVEL, EventAttr.XACCEL, EventAttr.SLOPE, EventAttr.DVX, EventAttr.CAL_VERSION, EventAttr.NUMERATOR, EventAttr.DENOMINATOR, EventAttr.INTERCEPTS, EventAttr.G_TRIGGER_LEVEL, EventAttr.DIAGNOSTIC,
            EventAttr.BOOT_LOADER_REV, EventAttr.TIME_CALIBRATED, EventAttr.TIME_RTC, EventAttr.YSLOPE, EventAttr.ZSLOPE, EventAttr.XWINDOW, EventAttr.STRUCT_VERSION, EventAttr.ZWINDOW, EventAttr.TEMP_AT_CALIBRATION, EventAttr.DVY, EventAttr.DVZ, EventAttr.TEMP_SENSE_ADC_AT_CAL, EventAttr.TEMP_COMP_SLOPE, EventAttr.TEMP_COMP_SLOPES_IN_BUFFER, EventAttr.TEMP_COMP_SLOPES_MEASURED, EventAttr.TOTAL_TEMP_COMP_SLOPE_ESTIMATES,
            EventAttr.TOTAL_TEMP_COMP_SLOPE_UPDATES, EventAttr.TEMP_COMP_SAMPLE_MAX, EventAttr.TEMP_COMP_SAMPLE_MAX, EventAttr.TEMP_COMP_DATA, EventAttr.TEMP_COMP_ENABLED, EventAttr.WIGGLE_TRIGGER_COUNT_TOTAL, EventAttr.FIRMWARE_REV_PREVIOUS, EventAttr.HARD_ACCEL_LEVEL, EventAttr.HARD_ACCEL_AVERAGE_CHANGE, EventAttr.HARD_ACCEL_DELTAV, EventAttr.HARD_VERT_DMM_TO_CRASH_RATIO_THRES,
            EventAttr.MSP_HARD_BUMP_PK2PKLEV, EventAttr.PRE_INSTALL_FULL_EVENTS_ENABLED, EventAttr.IGNITION_OFF_FULL_EVENTS_ENABLED),
    SMTOOLS_DEVICE_STATUS(148, EventAttr.MODE_OF_OPERATIONS, EventAttr.WEI_COUNT, EventAttr.ERROR_CODE, EventAttr.POWER_UP_COUNT, EventAttr.WATCHDOG_COUNT, EventAttr.DIGITAL_INPUT_STATUS, EventAttr.DIGITAL_OUTPUT_STATUS, EventAttr.CPU_PERCENTAGE, EventAttr.MAX_TIME,
            EventAttr.RECEIVED_MESSAGES_COUNT, EventAttr.VEHICLE_GALLONS, EventAttr.ERROR_DETAILS, EventAttr.SOFTWARE_RESET_COUNT, EventAttr.EMU_NAME_VERIFIED, EventAttr.EMU_NAME_DEVICE, EventAttr.EMU_NAME_TRANSFORM, EventAttr.SILICON_ID, EventAttr.VIN, EventAttr.SMTOOLS_TIMER_RUNNING,
            EventAttr.SMTOOLS_RESET_STATUS, EventAttr.SMTOOLS_FIRMWARE_REV, EventAttr.SMTOOLS_HARDWARE_REV),
    OBD_PARAMS_STATUS(149, EventAttr.RPM_COLLECTED, EventAttr.SPEED_COLLECTED, EventAttr.ODOMETER_COLLEDCTED, EventAttr.BRAKE_COLLECTED, EventAttr.SEATBELT_COLLECTED, EventAttr.RPM_REQUESTS, EventAttr.SPEED_REQUESTS, EventAttr.ODOMETER_REQUESTS, EventAttr.BRAKE_REQUESTS,
            EventAttr.SEATBELT_REQUESTS, EventAttr.RPM_RECEIVES, EventAttr.SPEED_RECEIVES, EventAttr.ODOMETER_RECEIVES, EventAttr.BRAKE_RECEIVES, EventAttr.SEATBELT_RECEIVES),


	/**
	 * Sent every time the device powers on <br/>
	 * Attributes ATTR_RESET_REASON, ATTR_MANUAL_RESET_REASON, ATTR_FIRMWARE_VERSION, ATTR_GPS_LOCK_TIME, [ATTR_DMM_VERSION], [ATTR_DMM_ORIENTATION], [ATTR_DMM_CAL_STATUS], ATTR_PRODUCT_VERSION, [ATTR_VIOLATION_FLAGS]
	 */
    POWER_ON(150),
    SMTOOLS_ERROR(151),    
    POTENTIAL_CRASH(152, EventAttr.PACKED_DELTAV),
    CANCEL_POTENTIAL_CRASH(153, EventAttr.PACKED_DELTAV),
    USB_REBOOT(154),
    SMTOOLS_DEVICE_STATUS_EX(155),
    OBD_PARAMS_STATUS_EX(156),
    CHECK_HOSMINUTES(157, EventAttr.DRIVER_ID_STR),
    WIRELINE_STATUS(158), // sent after every forward command received
    WIRELINE_ALARM(159), // sent when door alarm siren triggered
    
    MPG_VALUE__WIRELINE_IGN_TEST_NOT_RUN(160),
    WIRELINE_IGN_TEST_FAIL(161),
    WIRELINE_IGN_TEST_PASS(162),
    SHELL_OUTPUT(163),
    NO_MAPS_DRIVE(164),
    AUTO_DETECT(165),
    FUEL_STOP_EX(166, EventAttr.VEHICLE_GALLONS, EventAttr.ODOMETER, EventAttr.TRAILER_GALLONS, EventAttr.NO_GPS_LOCK_LOCATION),
    LOW_POWER_TAMPER_EVENT(167),
    FULL_EVENT_BELOW_THRESHOLD(168),
    ROLLOVER_WAYS(169, EventAttr.PACKED_DELTAV, EventAttr.DURATION),
    
    TRIAX_STATUS_WITNESSIII_TEMPCOMP(170),
    VERTICAL_EVENT(171, EventAttr.PACKED_DELTAV, EventAttr.DURATION),
    PARKING_BRAKE(172, EventAttr.HOS_STATE),
    UNIT_INFO(173, EventAttr.DATA),
    FULL_EVENT_CONFIDENCE_LEVEL(174, EventAttr.PACKED_DELTAV, EventAttr.CONFIDENCE_LEVEL),
    CRASH_DATA_HIRES(175),
    DSS_MICROSLEEP(176, EventAttr.DATA),
    DSS_STATISTICS(177, EventAttr.DATA),
    SECONDARY_NOTE_EVENT(178, EventAttr.PACKED_DELTAV),
    VERTICAL_EVENT_SECONDARY(179),
    
    WEEKLY_GPRS_USAGE(180, EventAttr.OPT_WEEKLY_WRITE_COUNT, EventAttr.UPLOAD_WEEKLY_TOTAL_WITNESSII_TRACE, EventAttr.DOWNLOAD_WEEKLY_TOTAL_FIRMWARE, EventAttr.DOWNLOAD_WEEKLY_TOTAL_MAPS, EventAttr.DOWNLOAD_WEEKLY_TOTAL_ZONES, EventAttr.DOWNLOAD_WEEKLY_TOTAL_BOOT_LOADER, EventAttr.DOWNLOAD_WEEKLY_TOTAL_QSI_FIRMWARE,
            EventAttr.DOWNLOAD_WEEKLY_TOTAL_WITNESSII_FIRMWARE, EventAttr.DOWNLOAD_WEEKLY_TOTAL_TRIAXII_FIRMWARE, EventAttr.DOWNLOAD_WEEKLY_TOTAL_BOUNDARY_DAT, EventAttr.DOWNLOAD_WEEKLY_TOTAL_BOUNDARY_DAT, EventAttr.DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_EMULATION, EventAttr.DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_FIRMWARE, EventAttr.DOWNLOAD_WEEKLY_TOTAL_SBS_EXMAPS_CHECK_BYTES,
            EventAttr.DOWNLOAD_WEEKLY_TOTAL_SBS_EXMAPS_DOWNLOAD_BYTES, EventAttr.DOWNLOAD_WEEKLY_TOTAL_SBS_EXMAPS_DOWNLOAD_COUNT, EventAttr.UPLOAD_WEEK_TOTAL_NOTIFICATION_BYTES, EventAttr.UPLOAD_WEEKLY_TOTAL_NOTIFICATION_COUNT),
    MCM_APP_FIRMWARE_UP_TO_DATE(181, EventAttr.UP_TO_DATE_STATUS),
    REMOTE_AUTO_MANDOWN(182), // automatic -no motion mandown
    REMOTE_MAN_MANDOWN(183), // manual mandown
    REMOTE_OK_MANDOWN(184), // mandown cancelled
    REMOTE_OFF_MANDOWN(185), // remote turned off
    REMOTE_LOW_BATT_MANDOWN(186), // low battery on remote
    REMOTE_ON_MANDOWN(187), // remote turned on
    REMOTE_AACK_MANDOWN(188),


	/**
	 * Sent when a speed by street map is updated.
	 * ATTR_SBS_UPDATE_TYPE denotes the type of update, ATTR_MAP_HASH is the map square expressed as an integer, and ATTR_BASE_VER
	 * will be the version of the map (base version for baseline updates, exception version for exception updates).
	 */
    SBS_UPDATE(189, EventAttr.SKIP_INT, EventAttr.FILE_NAME, EventAttr.BASELINE_VERSION, EventAttr.EXCEPTION_VERSION, EventAttr.TIME_LAST_VISITED, EventAttr.TIME_LAST_CHECKED, EventAttr.MAP_HASH, EventAttr.MAP_FILE_SIZE, EventAttr.MIN_LATITUDE, EventAttr.MIN_LONGITUDE, EventAttr.MAX_LATITUDE, EventAttr.MAX_LONGITUDE, EventAttr.SBS_EXMAP_UPDATE_RESULT, EventAttr.SBS_DB_UPDATE_RESULT), // remote turned on
    

    EMU_TARBALL_UP_TO_DATE(190),
    SPEEDING_EX4(191, EventAttr.TOP_SPEED, EventAttr.DISTANCE, EventAttr.MAX_RPM, EventAttr.SPEED_LIMIT, EventAttr.AVG_SPEED, EventAttr.AVG_RPM, EventAttr.SBS_LINK_ID, EventAttr.ZONE_ID, EventAttr.SPEEDING_TYPE, EventAttr.SEATBELT_ENGAGED, EventAttr.START_TIME, EventAttr.STOP_TIME, EventAttr.MAX_TIME, EventAttr.COURSE, EventAttr.MAX_SPEED_LIMIT, EventAttr.SBS_SPEED_LIMIT, EventAttr.ZONE_SPEED_LIMIT,
            EventAttr.WEATHER_SPEED_LIMIT_PERCENT, EventAttr.SEVERE_SPEED_THRESHOLD, EventAttr.SPEEDING_BUFFER, EventAttr.SPEEDING_GRACE_PERIOD, EventAttr.SEVERE_SPEED_SECONDS, EventAttr.SPEED_MODULE_ENABLED, EventAttr.SPEED_SOURCE),
    SPEEDING_LOG4(192),
    BLAST_ZONE_RECEIVED(193),
    SPEEDING_AV(194),
    UNIT_TEST_INFORMATION(195),
    UNIT_TEST_WARNING(196),
    UNIT_TEST_CRITICAL(197),
    LAST_STOP(198),


	/**
	 * Sent on internal errors <br/>
	 * Attributes ATTR_BAD_ERROR, [ATTR_VIOLATION_FLAGS]
	 */
    DIAGNOSTICS_REPORT(200), // reserved for use with the snitch product

	/**
	 * Sent at the beginning of a speeding violation - disabled by default <br/>
	 * Attributes ATTR_SPEED_LIMIT, ATTR_SPEED_ID, [ATTR_VIOLATION_FLAGS]
	 */
    SPEEDING_START(201),

	/**
	 * Sent when the device is unplugged <br/>
	 * Attributes ATTR_BACKUP_BATTERY (meaningless on tiwiPro), [ATTR_VIOLATION_FLAGS]
	 */
    UNPLUGGED(202),

	/**
	 * Sent at the beginning of a seatbelt violation - disabled by default <br/>
	 * Attributes [ATTR_VIOLATION_FLAGS]
	 */
    SEATBELT_START(203),
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
    LOW_BACKUP_BATTERY(207),

	/**
	 * Sent after an ignition off if idling data exists <br/>
	 * Attributes ATTR_LOW_IDLE, ATTR_HIGH_IDLE, [ATTR_VIOLATION_FLAGS]
	 */
    IDLING(208),

	/**
	 * Generated when a rollover is detected <br/>
	 * Attributes [ATTR_VIOLATION_FLAGS]
	 */
    ROLLOVER(209),


    /**
     * Sent if driver was speeding, but fixed behavior after audio is played and before a violation is generated <br/>
     * Attributes ATTR_SPEED_ID, [ATTR_VIOLATION_FLAGS]
     */
    COACH_SPEEDING(210),

    /**
     * Sent if driver wasn't wearing their seatbelt, but fixed behavior after audio is played and before a violation is generated <br/>
     * Attributes [ATTR_VIOLATION_FLAGS]
     */
    COACH_SEATBELT(211),

    /**
     * Generated if something goes over the hardware threshold for a crash, but doesn't make it past the software deltaV filter <br/>
     * Attributes ATTR_DELTA_VX, ATTR_DELTA_VY, ATTR_DELTA_VZ, [ATTR_VIOLATION_FLAGS]
     */
    SUB_THRESHOLD_CRASH(212),

    /**
     * Note to indicate tampering has occurred and been detected after the fact.  Unlike the unplugged note, this will not end a trip.
     */
    UNPLUGGED_WHILE_ASLEEP(213),


    /**
	 * Note that is not actually sent to the portal.  During initialize notification data it is modified to a normal idling, but the time
	 * is always: now() - 1.
	 */
    IDLE_FAKETIME(214),

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
	REQUEST_SETTINGS(218),
    RF_KILL(219), // reserved for tiwiPro
    
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
    STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246, EventAttr.FWDCMD_COMMAND, EventAttr.ACK_DATA, EventAttr.FWDCMD_ID),

	/**
	 * Internal event on portal
	 */
    STRIPPED_CONFIGURATOR(247),

	/**
	 * Internal event on portal
	 */
    ZONE_EXIT_ALERTED___WIFI_EVENT_STRIPPED_GET_WITNESSII_EVENT_TRACE(248, EventAttr.CRASH_TRACE),
    STRIPPED_GET_WITNESSII_EVENT_LIST_EX(249),


    STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250),
    STRIPPED_GET_SHORT_ID(251, EventAttr.EMPLOYEE_ID_STR),
    GET_OCCUPANTS_STRIPPED(252, EventAttr.EMPLOYEE_ID_STR),
    GET_OCCUPANT_INFO_STRIPPED(253, EventAttr.EMPLOYEE_ID_STR),
    STRIPPED_ACKNOWLEDGE(254),
    STRIPPED_UPPER_LIMIT(255),
    
    MAX_ID(300),

    ;

    

    private int code;
    private EventAttr[] attributes;

    private DeviceNoteTypes(int c, EventAttr ...attributes) {
        code = c;
        this.attributes = attributes;
    }
    
//    @Override
    public Integer getIndex() {
        return code;
    }
    
    public Integer getCode() {
    	return code;
    }
    
    public EventAttr[] getAttributes(){
        return attributes;
    }
    
    private final static HashMap<Integer, DeviceNoteTypes> lookupByCode = new HashMap<Integer, DeviceNoteTypes>();
    
    static {
        for (DeviceNoteTypes p : EnumSet.allOf(DeviceNoteTypes.class))
        {
            lookupByCode.put(p.getIndex(), p);
        }
    }
    
    /**
     * Only the key value is passed around, so with this method<br />
     * we can quickly get the right EventAttr and use it as a key
     * @param code
     * @return
     */
    public static DeviceNoteTypes valueOf(Integer code) {
        if (!(code>0)){
            return null;
//            throw new IllegalArgumentException("Code cannot be 0");
        }
        DeviceNoteTypes result = lookupByCode.get(code);
        if(result == null){
            Log.info("Unknown EventAttr.code: " + code);
            updateAttr(code);
        }
        return result;
    }
    
    
    /**
     * If we are missing a key then download the current<br />
     * firmware source code and find out what we should have.<br />
     * This also prints out a recommended addition in this enums<br />
     * current format, which is with the ATTR_ removed.
     * @param code
     */
    private static void updateAttr(int code){
        for (File file : downloadNotifications()){
            searchForKey(file, code);
        }

        printUnknownAttrs();    
        
    }
    
    /**
     * Loop through a file to see if we can find a matching Attr key
     * @param file
     * @param code
     */
    private static void searchForKey(File file, int code){
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                if (line.contains(code + "") && line.contains("SAT_EVENT_")){
                    Log.debug(formatNoteType(line));
                }
            }
        } catch (IOException e) {
            Log.wtf("%s", e);
        }
    }
    
    private static Object formatNoteType(String line) {
        String tiwiName = "SAT_EVENT_[A-Z_0-9]*(?=\\s+[=])";
        String waysName = "(?<=#define\\s)SAT_EVENT_[A-Z_0-9]*";

        Pattern tiwiPat = Pattern.compile(tiwiName);
        Pattern waysPat = Pattern.compile(waysName);
        
        Matcher tiwiMat, waysMat;
        
        tiwiMat = tiwiPat.matcher(line);
        waysMat = waysPat.matcher(line);
        
        String name = "";
        int value = 0;
        
        if (tiwiMat.find()){
            name = line.substring(tiwiMat.start(), tiwiMat.end());
            value = AutomationNumberManager.extract(line.substring(tiwiMat.end()));
        } else if (waysMat.find()){
            name = line.substring(waysMat.start(), waysMat.end());
            value = AutomationNumberManager.extract(line.substring(waysMat.end()));
        }
        
        return String.format("%s(%d),", name, value);
    }

    /**
     * Download the Firmware source code from SVN for both<br />
     * Waysmarts and TiwiPros.
     * @return
     */
    public static File[] downloadNotifications(){
       
        String base = "sat_events";
        String waysFile = base + "-ways.h";
        String tiwiFile = base + "-tiwi.h";
        String ways = "https://svn.iwiglobal.com/iwi/uClinux/trunk/user/iwi/src/common/";
        String tiwi = "https://svn.iwiglobal.com/iwi/snitch/Firmware/trunk/inc/";
        
        String directory = "target/test/resources/downloads/";

        File file = new File(directory);
        file.mkdirs();
        
        File[] files = {new File(directory + waysFile), new File(directory + tiwiFile)};
        
        AutomationFileHandler.downloadSvnDirectory(ways, "iwi.h", files[0]);

        AutomationFileHandler.downloadSvnDirectory(tiwi, "notifications.h", files[1]);
        
        return files;
    }
    
        
    /** 
     * Find ALL of the keys in source code and print them out<br />
     * so we can add them to the enum.
     * @param file
     */
    public static void searchForUnknowns(File file){
        String tiwiName = "SAT_EVENT_[A-Z_0-9]*(?=\\s+[=])";
        String waysName = "(?<=#define\\s)SAT_EVENT_[A-Z_0-9]*";
        
        Pattern tiwiPat = Pattern.compile(tiwiName);
        Pattern waysPat = Pattern.compile(waysName);
        Matcher tiwiMat, waysMat;
        
        String line;
        Log.info(file.getName());
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                if (line.contains("} NotificationType;")){
                    break;
                }
                if(Pattern.compile("^(?>\\s*)//").matcher(line).find() || 
                        line.startsWith("//")){ // Ignore the line if it is all commented.
                    continue;
                }
                
                tiwiMat = tiwiPat.matcher(line);
                waysMat = waysPat.matcher(line);
                if (tiwiMat.find()){
                    String eventName = line.substring(tiwiMat.start(), tiwiMat.end());
                    Integer value = AutomationNumberManager.extract(line.substring(tiwiMat.end()));
                    if (!lookupByCode.containsKey(value)){
                        Log.info("%s(%d),", eventName, value);
                    } else {
                        String ourName = "SAT_EVENT_" + lookupByCode.get(value).name();
                        if (!ourName.equals(eventName)){
                            printName(value, ourName, eventName);
                        }
                    }
                } else if (waysMat.find()){
                    String eventName = line.substring(waysMat.start(), waysMat.end());
                    Integer value = AutomationNumberManager.extract(line.substring(waysMat.end()));
                    if (!lookupByCode.containsKey(value)){
                        Log.info("%s(%d),", eventName, value);
                    } else {
                        String ourName = "SAT_EVENT_" + lookupByCode.get(value).name();
                        if (!ourName.equals(eventName)){
                            printName(value, ourName, eventName);
                        }
                    }
                }
            }
        }catch (IOException e) {
            Log.i("%s", e);
        }
    }
    
    private static void printName(int value, String portal, String firmware){
        Log.info("\n%d\n\nPortal:   %s\nFirmware: %s", value, portal, firmware);
    }
    
    /**
     * Loop through all of the source code files to find<br />
     * missing key values.
     */
    public static void printUnknownAttrs(){
        for (File file: downloadNotifications()){
            searchForUnknowns(file);
        }
    }
    
    @Override
    public String toString(){
        return String.format("%s(%d)", name(), code);
    }

    public boolean isEndOfTrip() {
		return (   this == CLEAR_DRIVER 
				|| this == LOW_BATTERY 
				|| this == IGNITION_OFF 
				|| this == RF_KILL 
				|| this == HOS_CHANGE_STATE_EX 
				|| this == HOS_CHANGE_STATE_NO_GPS_LOCK
				|| this == LOW_POWER_MODE
				|| this == LOW_POWER_TAMPER_EVENT
				);
	}
    
    public boolean  isTripStartNoteType() {
		return (this == NEWDRIVER || this == NEWDRIVER_HOSRULE || this == IGNITION_ON);
	}
    
    
	public boolean isStrippedNote()	{
		return (code > 245 && code < 255);
	}
	
	public boolean isIdleNoteType()	{
		return (this == IDLE_STATS || this == IDLING);
	}
}
