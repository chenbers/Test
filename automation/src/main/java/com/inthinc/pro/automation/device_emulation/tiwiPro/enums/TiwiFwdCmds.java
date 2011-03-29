package com.inthinc.QA.tiwiPro.enums;

import java.util.EnumSet;
import java.util.HashMap;

public enum TiwiFwdCmds {

	 /* Forward Commands */

    FWD_CMD_SET_FORWARD_CMD_EX(1),
    FWD_CMD_GET_GPS_GET_LOCATION(10),
    FWD_CMD_SEND_POWER_CYCLE(11),
    FWD_CMD_SET_DISABLE_UNIT(12),
    FWD_CMD_SET_ENABLE_UNIT(13),
    FWD_CMD_SET_DISABLE_COMMUNICATION(14),
    FWD_CMD_SET_ENABLE_COMMUNICATION(15),
    FWD_CMD_SET_SEATBELT_ENABLE(40),
    FWD_CMD_SET_SEATBELT_DISABLE(41),
    FWD_CMD_SET_SPEEDLIMIT_ENABLE(42),
    FWD_CMD_SET_SPEEDLIMIT_DISABLE(43),
    FWD_CMD_SET_RPM_ENABLE(44),
    FWD_CMD_SET_RPM_DISABLE(45),
    FWD_CMD_SET_NOTE_EVENTS_ENABLE(46),
    FWD_CMD_SET_NOTE_EVENTS_DISABLE(47),
    FWD_CMD_SET_FULL_EVENTS_DISABLE(48),
    FWD_CMD_SET_FULL_EVENTS_ENABLE(49),
    FWD_CMD_SET_FULL_EVENTS_ECALL_DISABLE(50),
    FWD_CMD_SET_FULL_EVENTS_ECALL_ENABLE(51),
    FWD_CMD_SET_IGNITION_OFF_ENABLE(54),
    FWD_CMD_SET_IGNITION_OFF_DISABLE(55),
    FWD_CMD_SET_IGNITION_ON_ENABLE(56),
    FWD_CMD_SET_IGNITION_ON_DISABLE(57),
    FWD_CMD_BUZZER_MASTER_ENABLE(83),
    FWD_CMD_BUZZER_MASTER_DISABLE(84),
    FWD_CMD_BUZZER_SPEEDLIMIT_ENABLE(85),
    FWD_CMD_BUZZER_SPEEDLIMIT_DISABLE(86),
    FWD_CMD_BUZZER_SEATBELT_ENABLE(87),
    FWD_CMD_BUZZER_SEATBELT_DISABLE(88),
    FWD_CMD_BUZZER_RPM_ENABLE(89),
    FWD_CMD_BUZZER_RPM_DISABLE(90),
    FWD_CMD_BUZZER_NOTE_EVENTS_ENABLE(91),
    FWD_CMD_BUZZER_NOTE_EVENTS_DISABLE(92),
    FWD_CMD_BUZZER_FULL_EVENTS_ENABLE(93),
    FWD_CMD_BUZZER_FULL_EVENTS_DISABLE(94),
    FWD_CMD_GET_VERSION_INFO(174),
    FWD_CMD_IGNORE_RAMP_SPEED_ENABLE(230),
    FWD_CMD_IGNORE_RAMP_SPEED_DISABLE(231),
    FWD_CMD_DISABLE_NO_DRIVER_RULE(241),
    FWD_CMD_ENABLE_NO_DRIVER_RULE(242),
    FWD_CMD_GET_MCM_STATS(245),
    FWD_CMD_SET_LOW_POWER_EVENT_DISABLE(246),
    FWD_CMD_SET_LOW_POWER_EVENT_ENABLE(247),
    FWD_CMD_SET_UPDATE_ALWAYS_DISABLE(273),
    FWD_CMD_SET_UPDATE_ALWAYS_ENABLE(274),
    FWD_CMD_SET_STATS_EVENT_ENABLE(275),
    FWD_CMD_SET_STATS_EVENT_DISABLE(276),
    FWD_CMD_SET_SPEED_MODULE_ENABLED(299),
    FWD_CMD_SET_SPEED_MODULE_DISABLED(300),
    FWD_CMD_DOWNLOAD_NEW_FIRMWARE(365),
    FWD_CMD_DOWNLOAD_NEW_MAPS(366),
    FWD_CMD_DOWNLOAD_NEW_ZONES(367),
    FWD_CMD_DOWNLOAD_AUDIO_FILE(368),
    FWD_CMD_SET_WSZONES_MODULE_ENABLED(386),
    FWD_CMD_SET_WSZONES_MODULE_DISABLED(387),
    FWD_CMD_SET_TRIAX_Z_LEVEL(449),
    FWD_CMD_SET_TRIAX_RMS_LEVEL(450),
    FWD_CMD_SET_TRIAX_RMS_WINDOW(451),
    FWD_CMD_SET_TRIAX_Y_WINDOW(452),
    FWD_CMD_SET_TRIAX_Y_LEVEL(453),
    FWD_CMD_SET_TRIAX_X_ACCEL(454),
    FWD_CMD_SET_TRIAX_DVX(455),
    FWD_CMD_GET_DMM_STATUS(470),
    FWD_CMD_SET_TRIAX_SLOPE(490),
    FWD_CMD_DOWNLOAD_NEW_WITNESSII_FIRMWARE(556),
    FWD_CMD_SEND_TRIAX_CALIBRATE(578),
    FWD_CMD_SET_SPEED_LIMIT_VARIABLE(629),
    FWD_CMD_SET_SEVERE_SPEED_LIMIT_VARIABLE(630),
    FWD_CMD_SET_SPEED_BUFFER_VARIABLE(631),
    FWD_CMD_SET_SPEED_LIMIT_VARIABLE_KPH(633),
    FWD_CMD_SET_SEVERE_SPEED_LIMIT_VARIABLE_KPH(634),
    FWD_CMD_SET_SPEED_BUFFER_VARIABLE_KPH(635),
    FWD_CMD_DOWNLOAD_NEW_EMU(636),
    FWD_CMD_DOWNLOAD_NEW_SMTOOLS_FIRMWARE(637),
    FWD_CMD_SET_007_MODEM_PWR_STATE(638),
    FWD_CMD_SET_007_GPS_PWR_STATE(639),
    FWD_CMD_SET_007_BATT_PWR_STATE(640),
    FWD_CMD_SET_007_HH_PWR_STATE(641),
    FWD_CMD_SET_007_DASH_PWR_STATE(642),
    FWD_CMD_SET_007_SMTOOLS_RESET_STATE(643),
    FWD_CMD_SET_007_SMTOOLS_BUS_TYPE(644),
    FWD_CMD_ENABLE_SMTOOLS_DEVICE(645),
    FWD_CMD_SET_AGCTL_VALUE(650),
    FWD_CMD_GET_VEHICLE_VOLTAGE(651),
    FWD_CMD_LOG_XVAR_SHORT_FORMAT(652),
    FWD_CMD_INIT_TIWIPRO_MFG_TEST(653),
    FWD_CMD_RUN_TIWIPRO_MFG_TEST(654),
    FWD_CMD_KILL_DOWNLOAD_NEW_FIRMWARE(659),
    FWD_CMD_KILL_DOWNLOAD_NEW_MAPS(660),
    FWD_CMD_KILL_DOWNLOAD_NEW_ZONES(661),
    FWD_CMD_KILL_DOWNLOAD_NEW_WITNESSII_FIRMWARE(664),
    FWD_CMD_KILL_DOWNLOAD_NEW_SMTOOLS_EMULATION(668),
    FWD_CMD_KILL_DOWNLOAD_NEW_SMTOOLS_FIRMWARE(669),
    FWD_CMD_SET_GPRS_APN(688),
    FWD_CMD_SET_SERVER_URL(692),
    FWD_CMD_SEND_DMM_ERASE_ALL_TEMP_COMP_SLOPES(775),
    FWD_CMD_SET_HARD_VERTICAL_SPEED_THRESH(801),
    FWD_CMD_SET_HARD_VERTICAL_SEVERE_PKPK(802),
    FWD_CMD_SYSTEM_RESET(2000),
    FWD_CMD_GET_DIAGNOSTICS_REPORT(2001),
    FWD_CMD_RESET_DIAGNOSTICS(2002),
    FWD_CMD_SET_GPS_LOCATION_VARIABLE(2003),
    FWD_CMD_SET_RPM_VIOLATION_VARIABLE(2004),
    FWD_CMD_SET_SEATBELT_GRACE_PERIOD_VARIABLE(2005),
    FWD_CMD_SET_NO_LOCK_BUFFER_VARIABLE(2006),
    FWD_CMD_SET_RAMP_SPEED_LIMIT_VARIABLE(2007),
    FWD_CMD_SET_RAMP_SPEED_LIMIT_VARIABLE_KPH(2008),
    FWD_CMD_SET_SEATBELT_VIOLATION_VARIABLE(2009),
    FWD_CMD_SET_SEATBELT_VIOLATION_VARIABLE_KPH(2010),
    FWD_CMD_SET_LOW_POWER_VARIABLE(2011),
    FWD_CMD_SET_SPEEDLIM_CHANGE_GRACE_PERIOD_VARIABLE(2014),
    FWD_CMD_SET_SPEEDING_GRACE_PERIOD_VARIABLE(2015),
    FWD_CMD_SMS_PING(2016),
    FWD_CMD_SMS_TOGGLE_PING(2017),
    FWD_CMD_SET_CALL_NUMBER(2018),
    FWD_CMD_MAKE_AUDIO_CALL(2019),
    FWD_CMD_SET_SERVER_PORT(2020),
    FWD_CMD_SEND_TRIAX_ORIENT(2021),
    FWD_CMD_SET_MSGS_PER_NOTIFICATION(2022),
    FWD_CMD_SET_SPEEDLIMIT_START_ENABLE(2027),
    FWD_CMD_SET_SPEEDLIMIT_START_DISABLE(2028),
    FWD_CMD_SET_PARENT_MODE_ENABLE(2029),
    FWD_CMD_SET_PARENT_MODE_DISABLE(2030),
    FWD_CMD_SET_SMTOOLS_SECURITY_URL(2031),
    FWD_CMD_SET_SMTOOLS_SECURITY_CONTEXT(2032),
    FWD_CMD_SET_SMTOOLS_EMU_URL(2033),
    FWD_CMD_SET_SMTOOLS_EMU_CONTEXT(2034),
    FWD_CMD_RESET_SMTLS_DIAGNOSTICS(2037),
    FWD_CMD_DEPRECATED_SET_SPEED_BUFFER_SLOPE(2038),
    FWD_CMD_DEPRECATED_SET_SPEED_BUFFER_MINIMUM(2039),
    FWD_CMD_DEPRECATED_SET_SPEED_BUZZER_BUFFER(2042),
    FWD_CMD_SET_SEATBELT_START_ENABLE(2043),
    FWD_CMD_SET_SEATBELT_START_DISABLE(2044),
    FWD_CMD_DEPRECATED_SPEED_BUFFER_SLOPE(2045),
    FWD_CMD_SET_DEBUG_GPS_ENABLE(2046),
    FWD_CMD_SET_DEBUG_GPS_DISABLE(2047),
    FWD_CMD_SET_DMM_MONITOR_TIMEOUT(2048),
    FWD_CMD_SMTOOLS_DEVICE_ENABLE(2049),
    FWD_CMD_SMTOOLS_DEVICE_DISABLE(2050),
    FWD_CMD_SMTOOLS_DISABLE_SECURITY(2051),
    FWD_CMD_SMTOOLS_DEACTIVATE_OVERRIDE_ENABLE(2052),
    FWD_CMD_SMTOOLS_DEACTIVATE_OVERRIDE_DISABLE(2053),
    FWD_CMD_DOWNLOAD_DOTA_FIRMWARE(2054),
    FWD_CMD_ADD_VALID_CALLER(2055),
    FWD_CMD_SET_GPRS_APN2(2056),
    FWD_CMD_DOWNLOAD_SMTOOLS_MANUFACTURING_TEST_SLAVE_EMU(2057),
    FWD_CMD_DOWNLOAD_SMTOOLS_EMU_UNDER_DEVELOPMENT(2058),
    FWD_CMD_SET_RAMP_SPEED_LIMIT_BUFFER(2060),
    FWD_CMD_SET_GPS_MIN_SNR_FOR_BEST_TRHEE_SATS_THRESHOLD(2061),
    FWD_CMD_GET_SMTOOLS_DEVICE_STATUS(2062),
    FWD_CMD_SET_SPEAKER_GAIN_AUDIO_VOLUME_LEVEL(2063),
    FWD_CMD_SET_SEVERE_SPEEDING_GRACE_PERIOD_VARIABLE(2064),
    FWD_CMD_SET_SUPERTRACK_ENABLE(2065),
    FWD_CMD_SET_SUPERTRACK_DISABLE(2066),
    FWD_CMD_SET_SPEED_BUFFER_VALUES(2067),
    FWD_CMD_DOWNLOAD_NEW_SMTOOLS_FIRMWARE_FROM_SD_CARD(2068),
    FWD_CMD_SET_SPEEDING_TIME_TO_LED(2071),
    FWD_CMD_SET_SPEEDING_TIME_TO_AUDIO(2072),
    FWD_CMD_SET_NVP_LATENCY_CRITERIA(2073),
    FWD_CMD_SET_NVP_MIN_DISTANCE_CHANGE(2074),
    FWD_CMD_SET_DEBUGOBD_ENABLE(2075),
    FWD_CMD_SET_DEBUGOBD_DISABLE(2076),
    FWD_CMD_SET_CAR_BATTERY_VEHICLE_ON(2077),
    FWD_CMD_SET_LOG_NMEA(2078),
    FWD_CMD_SET_TIWI_FLASH(2080),
    FWD_CMD_SET_SMTOOLS_FLASH(2081),
    FWD_CMD_SET_WITNESS_FLASH(2082),
    FWD_CMD_SET_SEND_RFID(2083),
    FWD_CMD_SET_QA_BUZZER(2084),
    FWD_CMD_SET_DESIRED_A_AND_D(2085),
    FWD_CMD_SET_ROLLOVER_EVENTS_ENABLE(2086),
    FWD_CMD_SET_ROLLOVER_EVENTS_DISABLE(2087),
    FWD_CMD_SET_ROLLOVER_EVENTS_ECALL_ENABLE(2088),
    FWD_CMD_SET_ROLLOVER_EVENTS_ECALL_DISABLE(2089),
    FWD_CMD_SET_CRASH_ROLLOVER_RENOTIFICATION_DELAY(2090),
    FWD_CMD_DMM_SET_HARD_ACCEL(2091),
    FWD_CMD_DMM_SET_HARD_BRAKE(2092),
    FWD_CMD_DMM_SET_HARD_TURN(2093),
    FWD_CMD_DMM_SET_HARD_BUMP(2094),
    FWD_CMD_SET_IDLING_ENABLE(2095),
    FWD_CMD_SET_IDLING_DISABLE(2096),
    FWD_CMD_SET_IDLE_TIMEOUT_SECONDS(2097),
    FWD_CMD_GET_VEHICLE_MPG(2098),
    FWD_CMD_SET_XLOGS_ENABLE(2099),
    FWD_CMD_BUZZER_ROLLOVER_ENABLE(2100),
    FWD_CMD_BUZZER_ROLLOVER_DISABLE(2101),
    FWD_CMD_SET_FACTORY_DEFAULTS(2102),
    FWD_CMD_CLEAR_EVENT_HISTORY(2103),
    FWD_CMD_SET_EVENTHISTORY_ENABLE(2104),
    FWD_CMD_SET_EVENTHISTORY_DISABLE(2105),
    FWD_CMD_SAVE_TRACES(2106),
    FWD_CMD_SET_AUTOSAVE_EVENTHISTORY_ENABLE(2107),
    FWD_CMD_SET_AUTOSAVE_EVENTHISTORY_DISABLE(2108),
    FWD_CMD_SET_PHONE_GAIN(2109),
    FWD_CMD_SET_GPRS_LOGIN(2110),
    FWD_CMD_SET_GPRS_PASSWORD(2111),
    FWD_CMD_SET_GPRS_PIN(2112),
    FWD_CMD_GET_PROP_PING(2113),
    FWD_CMD_GET_AIR_GAP_CONFIG(2114),
    FWD_CMD_SET_FTP_URL(2115),
    FWD_CMD_SET_FTP_USERID(2116),
    FWD_CMD_SET_FTP_PASSWORD(2117),
    FWD_CMD_FTP_UPLOAD(2118),
    FWD_CMD_CLEAR_DIR(2119),
    FWD_CMD_SET_SUB_THRESHOLD_CRASH_ENABLE(2120),
    FWD_CMD_SET_SUB_THRESHOLD_CRASH_DISABLE(2121),
    FWD_CMD_SET_CRASH_TRACE_ENABLE(2122),
    FWD_CMD_SET_CRASH_TRACE_DISABLE(2123),
    FWD_CMD_SET_FORCE_MSP_FOR_IGN_TRUE(2124),
    FWD_CMD_SET_FORCE_MSP_FOR_IGN_FALSE(2125),
    FWD_CMD_SET_EMU_INVALID_WAIT_COUNT(2126),
    FWD_CMD_SET_AUDIO_FOLDER(2127),
    FWD_CMD_SET_SERIAL_NUMBER(2128),
    FWD_CMD_LOAD_DRIVER(2129),
    FWD_CMD_DOWNLOAD_DRIVER_FILE(2133),
    FWD_CMD_SET_CRASH_DATA_ENABLED(2134),
    FWD_CMD_SET_CRASH_DATA_DISABLED(2135),
    FWD_CMD_RESET_BULK_QUEUE_LOCKOUT(2136),
    FWD_CMD_ENABLE_GPS_FILTER(2137),
    FWD_CMD_DISABLE_GPS_FILTER(2138),
    FWD_CMD_MODIFY_AVERAGE_THRESHOLD(2140),
    FWD_CMD_MODIFY_INDIVIDUAL_THRESHOLD(2141),
    FWD_CMD_INVALID_DRIVER(2142),
    FWD_CMD_ASSIGN_DRIVER(2143),
    FWD_CMD_UNASSIGN_DRIVER(2144),
    FWD_CMD_RECOMPACT_AND_REFORMAT(2145),
    FWD_CMD_SET_AUTO_LOGOUT_SECONDS(2146),
    FWD_CMD_SET_STREET_ENVELOPE_PADDING(2147),
    FWD_CMD_SET_STREET_MAX_ANGLE(2148),
    FWD_CMD_SET_STREET_WINDOW(2149),
    FWD_CMD_SET_AGPS_ENABLE(2150),
    FWD_CMD_SET_AGPS_DISABLE(2151),
    FWD_CMD_SET_AGPS_SERVER_URL(2152),
    FWD_CMD_SET_AGPS_SERVER_PORT(2153),
    FWD_CMD_SET_AGPS_RETRY_INTERVAL(2154),
    FWD_CMD_SET_AGPS_NUM_DWNLD_RETRIES(2155),
    FWD_CMD_SET_AGPS_NUM_CONN_RETRIES(2156),
    FWD_CMD_SET_NO_DRIVER_VIOLATION_OVERRIDE_ENABLE(2157),
    FWD_CMD_SET_NO_DRIVER_VIOLATION_OVERRIDE_DISABLE(2158),
    FWD_CMD_SET_ROLLOVER_FILTER(2159),
    FWD_CMD_SET_CRASH_THRESHOLDS(2160),
    FWD_CMD_SET_ODO_TAMPERING_MILEAGE_THRESH(2161),
    FWD_CMD_SET_WRITE_CRASH_DATA_TO_SD_OFF(2162),
    FWD_CMD_SET_WRITE_CRASH_DATA_TO_SD_ON(2163),
    FWD_CMD_RESTART_FIX_SESSION(2164),
    FWD_CMD_SET_GPS_DEBUG_INTERVAL(2165),
    FWD_CMD_RESET_ERIDE_TO_FACTORY_DEFAULTS(2166),
    FWD_CMD_SET_GPS_FILTER_MAXIMUM_SPEED_(2170),
    FWD_CMD_PERFORM_ONE_G_VALIDATION(2171),
    FWD_CMD_SET_RFID_DISABLE(2172),
    FWD_CMD_SET_RFID_ENABLE(2173),
    FWD_CMD_SET_RFID_SPEED_THRESHOLD(2174),
    FWD_CMD_ENABLE_DISABLE_CELL_SERVICE(2175),
    FWD_CMD_UPDATE_CONFIGURATION(2178),
    FWD_CMD_DUMP_CONFIGURATION(2179),
    FWD_CMD_SET_HOURS_TILL_CLEAR(2180),
    FWD_CMD_CLEAR_SBS_CHECK_LIST(2181),
    FWD_CMD_EXTERNAL_SEAT_BELT_SENSOR(2182),
    FWD_CMD_SEAT_BELT_SENSOR_INSTALLATION_MODE(2186);

    private int code;

    private TiwiFwdCmds(int c) {
    	code = c;
    }
    public int getCode() {
    	return code;
    } 
    
    private static HashMap<Integer, TiwiFwdCmds> lookupByCode = new HashMap<Integer, TiwiFwdCmds>();
    
    static {
        for (TiwiFwdCmds p : EnumSet.allOf(TiwiFwdCmds.class))
        {
            lookupByCode.put(p.getCode(), p);
        }
    }
    
    public static TiwiFwdCmds valueOf(Integer code){
    	return lookupByCode.get(code);
    }
    
    public static TiwiFwdCmds valueOf(Object code){
    	return lookupByCode.get((Integer) code);
    }
}
