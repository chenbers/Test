package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.automation.interfaces.DeviceProperties;


public enum TiwiProps implements DeviceProperties {

    /* Properties */

    PROPERTY_UNIT_ENABLED(1),
    PROPERTY_NOTIFICATIONS_ENABLED(2),
    PROPERTY_BUZZER_MASTER(3, "1"),
    PROPERTY_MCM_ID(4),
    PROPERTY_DEPRECATED_SERVER_CONTEXT(5),
    PROPERTY_SERVER_URL(6, "qa.tiwipro.com"),
    PROPERTY_GPRS_APN(7, "safety.t-mobile.com"),
    PROPERTY_EVENT_LOCATION_SECONDS(8, "15"),
    PROPERTY_LOCATION_UPDATE_ALWAYS(9, "0"),
    PROPERTY_ZONE_MODULE(10, "1"),
    PROPERTY_EVENT_SPEEDING(11, "1"),
    PROPERTY_BUZZER_SPEED(12, "1"),
    PROPERTY_SPEED_MODULE(13, "1"),
    PROPERTY_SPEED_LIMIT(14, "78.0"),
    PROPERTY_SEVERE_SPEED_THRESHOLD(15),
    PROPERTY_SPEEDING_BUFFER(16),
    PROPERTY_DEPRECATED_SPEED_UNKNOWN_LIMIT(17),
    PROPERTY_RAMP_SPEED(18),
    PROPERTY_RAMP_SPEED_LIMIT(19),
    PROPERTY_SPDLIMCHNG_GRACE_PERIOD(20),
    PROPERTY_SPEEDING_GRACE_PERIOD(21, "15"),
    PROPERTY_EVENT_RPM(22, "1"),
    PROPERTY_BUZZER_RPM(23, "1"),
    PROPERTY_RPM_LIMIT(24, "5000"),
    PROPERTY_EVENT_SEATBELT(25, "1"),
    PROPERTY_BUZZER_SEATBELT(26, "1"),
    PROPERTY_SEATBELT_GRACE_PERIOD(27, "10"),
    PROPERTY_SEATBELT_SPEED_LIMIT(28, "10.0"),
    PROPERTY_EVENT_WITNESS_NOTE(29, "1"),
    PROPERTY_BUZZER_NOTEEVENT(30, "1"),
    PROPERTY_EVENT_WITNESS_FULL(31, "1"),
    PROPERTY_BUZZER_FULLEVENT(32, "0"),
    PROPERTY_EVENT_IGNITION_ON(33),
    PROPERTY_EVENT_IGNITION_OFF(34),
    PROPERTY_EVENT_LOW_POWER(35),
    PROPERTY_LOW_POWER_MODE_SECONDS(36, "900"),
    PROPERTY_REBOOTS(37),
    PROPERTY_LAST_ZONE_ID(38),
    PROPERTY_CURRENT_ZONE_ID(39),
    PROPERTY_ZONES_HIT(40),
    PROPERTY_SERVER_PORT(41, "8190"),
    PROPERTY_SET_MSGS_PER_NOTIFICATION(42, "4"),
    PROPERTY_DEPRECATED_DOWNLOAD_SERVER_URL(43),
    PROPERTY_DEPRECATED_DOWNLOAD_SERVER_PORT(44),
    PROPERTY_DEPRECATED_SERVER_URL(45),
    PROPERTY_DEPRECATED_SERVER_PORT(46),
    PROPERTY_EVENT_START_SPEEDING(47, "0"),
    PROPERTY_SMTOOLS_SECURITY_URL(48, "http://www.iwiglobal.com"),
    PROPERTY_SMTOOLS_SECURITY_CONTEXT(49, "asip"),
    PROPERTY_SMTOOLS_EMU_URL(50),
    PROPERTY_SMTOOLS_EMU_CONTEXT(51),
    PROPERTY_DEPRECATED_SMTOOLS_FIRMWARE_URL(52),
    PROPERTY_DEPRECATED_SMTOOLS_FIRMWARE_CONTEXT(53),
    PROPERTY_CAR_BATTERY_LOW_POWER(54, "11000"),
    PROPERTY_BACKUP_BATTERY_LOW_POWER(55),
    OBSOLETE_PROPERTY_SPEEDING_BUZZER_BUFFER(56),
    OBSOLETE_PROPERTY_SPEEDING_BUFFER_SLOPE(57),
    PROPERTY_EVENT_START_SEATBELT(58, "1"),
    PROPERTY_PARENT_MODE(59, "0"),
    PROPERTY_ECALL_PHONE_NUMBER(60, "8014175665"),
    PROPERTY_DEBUG_GPS(61, "0"),
    OBSOLETE_PROPERTY_SPEEDING_BUFFER_MINIMUM(62),
    PROPERTY_DMM_MONITOR_TIMEOUT(63),
    PROPERTY_SMTOOLS_SILICON_ID(64),
    PROPERTY_SMTOOLS_SECURITY_CODE(65),
    PROPERTY_SMTOOLS_SERIAL_NUMBER(66),
    PROPERTY_SMTOOLS_SECURITY_STATUS(67),
    PROPERTY_SMTOOLS_DEVICE_ENABLED(68),
    PROPERTY_SMTOOLS_EMU_DOWNLOADED(69),
    PROPERTY_SMTOOLS_DEACTIVATE_OVERRIDE(70, "0"),
    PROPERTY_GPRS_APN2(71),
    PROPERTY_SMTOOLS_EMU_NAME_VERIFICATION_OVERRIDE(72),
    PROPERTY_SMTOOLS_TEST_EMU_NAME(73),
    PROPERTY_RAMP_SPEED_BUFFER(74),
    PROPERTY_MIN_SNR_FOR_BEST_THREE_SATS_CRITERIA(75),
    PROPERTY_SPEAKER_GAIN(76, "-1000"),
    PROPERTY_SMTOOLS_EMU_HASH(77),
    PROPERTY_SMTOOLS_EMU_FILENAME(78),
    PROPERTY_SMTOOLS_EMU_VINRULE(79),
    PROPERTY_SMTOOLS_EMU_MAKE(80),
    PROPERTY_SMTOOLS_EMU_MODEL(81),
    PROPERTY_SMTOOLS_EMU_YEAR(82),
    PROPERTY_SEVERE_SPEEDING_GRACE_PERIOD(83, "15"),
    PROPERTY_SUPERTRACK(84),
    PROPERTY_VARIABLE_SPEED_LIMITS(85, "5 10 15 20 25 30 35 40 45 50 55 60 65 70 75"),
    PROPERTY_SPEEDING_TIME_TO_LED(86),
    PROPERTY_SPEEDING_TIME_TO_AUDIO(87),
    PROPERTY_HDOP_CRITERIA(88),
    PROPERTY_NVP_LATENCY_CRITERIA(89),
    PROPERTY_NVP_MIN_DISTANCE_CHANGE(90),
    PROPERTY_DEBUGOBD(91),
    PROPERTY_CAR_BATTERY_VEHICLE_ON(92),
    PROPERTY_LOG_NMEA(93),
    PROPERTY_TIWI_FLASH(94),
    PROPERTY_SMTOOLS_FLASH(95),
    PROPERTY_WITNESS_FLASH(96),
    PROPERTY_SEND_RFID(97),
    PROPERTY_QA_BUZZER(98, "0"),
    PROPERTY_DESIRED_A_AND_D(99),
    PROPERTY_EVENT_WITNESS_FULL_ECALL(100, "0"),
    PROPERTY_EVENT_ROLLOVER(101, "1"),
    PROPERTY_EVENT_ROLLOVER_ECALL(102, "0"),
    PROPERTY_CRASH_ROLLOVER_RENOTIFICATION_DELAY(103, "1"),
    PROPERTY_RFID8(104),
    PROPERTY_EVENT_IDLING(105, "1"),
    PROPERTY_IDLE_TIMEOUT(106, "60"),
    PROPERTY_VEHICLE_MPG(107),
    PROPERTY_MPG_DISTANCE(108),
    PROPERTY_XLOGS_ENABLED(109, "0"),
    PROPERTY_BUZZER_ROLLOVER(110, "0"),
    PROPERTY_IGN_AVAILABLE_FROM_EMU(111),
    PROPERTY_RUN_TEST_ON_BOOT(112),
    PROPERTY_HARD_VERT_SPEED_THRESH(113, "25"),
    PROPERTY_HARD_VERT_SEVERE_PK2PK_THRESH(114),
    PROPERTY_EVENT_HISTORY(115),
    PROPERTY_AUTOSAVE_EVENT_HISTORY(116),
    PROPERTY_PHONE_GAIN(117, "-500"),
    PROPERTY_GPRS_LOGIN(118, ""),
    PROPERTY_GPRS_PASSWORD(119, ""),
    PROPERTY_GPRS_PIN(120, ""),
    PROPERTY_FTP_UPLOAD_URL(121, "tiwiftp.tiwi.com"),
    PROPERTY_FTP_USER_ID(122, "tiwibox"),
    PROPERTY_FTP_PASSWORD(123, "Phohthed8sheighoh"),
    PROPERTY_SEND_SUB_THRESH_CRASH(124, "1"),
    PROPERTY_ENABLE_CRASH_TRACE(125),
    PROPERTY_FORCE_MSP_FOR_IGN(126, "0"),
    PROPERTY_EMU_UNAVAIL_WAIT_COUNT(127),
    PROPERTY_AUDIO_FOLDER(128, "0"),
    PROPERTY_SERIAL_NUMBER(129),
    PROPERTY_CRASH_DATA_ENABLED(130),
    PROPERTY_NO_DRIVER_ENABLED(132, "0"),
    PROPERTY_CRASH_DATA(133, "1"),
    PROPERTY_STAT_FREQ(135, "7"),
    PROPERTY_AVG_SPD_AGMT(136, "20"),
    PROPERTY_IND_SPD_AGMT(137, "10"),
    PROPERTY_AUTOLOGOUT_SECONDS(138, "0"),
    PROPERTY_ST_ENV_PADDING(139, "300"),
    PROPERTY_ST_MAX_ANGLE(140, "60"),
    PROPERTY_ST_WINDOW(141, "250"),
    PROPERTY_AGPS_ENABLE(142, "1"),
    PROPERTY_AGPS_PORT(143, "37433"),
    PROPERTY_AGPS_URL(144, "symphony-usa.eride.com"),
    PROPERTY_AGPS_REQ_INT(145, "10800"),
    PROPERTY_AGPS_NUM_DLD(146, "2"),
    PROPERTY_AGPS_NUM_CONN(147, "3"),
    PROPERTY_NODRIVER_AUDIO_OVERRIDE(148, "0"),
    PROPERTY_ROLLOVER_FILTER(149, "60"),
    PROPERTY_DTC_THRESHOLD(150, "2"),
    PROPERTY_GPS_DBG_INVL(152, "5"),
    PROPERTY_FILTER_MAX_SPEED(154, "140"),
    PROPERTY_RFID_ENABLE(155, "1"),
    PROPERTY_RFID_SPEED_THRESH(156, "0"),
    PROPERTY_HARDACCEL_SLIDER(157, "875 40 99"),
    PROPERTY_HARDBRAKE_SLIDER(158, "2250 100 99"),
    PROPERTY_HARDTURN_SLIDER(159, "999 52 99"),
    PROPERTY_HARDBUMP_SLIDER(160, "1000 50 99 200"),
    PROPERTY_CRASH_DELTAX(161, "314"),
    PROPERTY_CRASH_DELTAY(162, "311"),
    PROPERTY_CRASH_DELTAZ(163, "450"),
    PROPERTY_CLEAR_SBSCHECK(164, "48"),
    PROPERTY_DMM_ORIENT_STATUS(165),
    PROPERTY_DMM_TEMP_U(166),
    PROPERTY_DMM_TEMP_V(167),
    PROPERTY_DMM_TEMP_W(168),
    PROPERTY_INPUT_1_FUNCTION(169, "0"),
    PROPERTY_INPUT_2_FUNCTION(170, "0"),
    PROPERTY_INPUT_3_FUNCTION(171, "0"),
    PROPERTY_GEN_INPUT_EVENTS(172, "0"),
    PROPERTY_SIM_CCID(174),
    PROPERTY_WHITELIST_ENABLED(175, "0"),
    PROPERTY_SMTLS_STATE_DIAG(176, "0"),
    STATIC;

    ;

    private int code;
    private String setting;

    private static HashMap<Integer, TiwiProps> lookupByCode = new HashMap<Integer, TiwiProps>();

    static {
        for (TiwiProps p : EnumSet.allOf(TiwiProps.class)) {
            lookupByCode.put(p.getValue(), p);
        }
    }

    private static Map<TiwiProps, String> propertiesFile = new HashMap<TiwiProps, String>();

    static {
        for (TiwiProps p : EnumSet.allOf(TiwiProps.class)) {
            if (p.getDefaultSetting()!=null){
                propertiesFile.put(p, p.getDefaultSetting());
            }
        }
    }

    private TiwiProps(){
        
    }
    private TiwiProps(int c) {
        code = c;
    }

    private TiwiProps(Integer c, String setting) {
        code = c;
        this.setting = setting;
    }

    public Map<TiwiProps, String> getDefaultProps() {
        return propertiesFile;
    }

    public String getDefaultSetting() {
        return setting;
    }

    public Integer getValue() {
        return code;
    }
    
    public TiwiProps valueOf(Integer code){
        return lookupByCode.get(code);
    }

}
