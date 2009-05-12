package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
public enum ForwardCommandType
{
    GET_GPS_GET_LOCATION(10),
    SEND_POWER_CYCLE(11, "reboot the unit"),
    SET_DISABLE_UNIT(12, "unit is completely disabled in the vehicle - still looks for enable command only"),
    SET_ENABLE_UNIT(13),
    SET_DISABLE_COMMUNICATION(14, "unit won't send anymore messages to cell network, but it will still receive forward commands"),
    SET_ENABLE_COMMUNICATION(15),
    SET_SEATBELT_ENABLE(40),
    SET_SEATBELT_DISABLE(41),
    SET_SPEEDLIMIT_ENABLE(42),
    SET_SPEEDLIMIT_DISABLE(43),
    SET_RPM_ENABLE(44),
    SET_RPM_DISABLE(45),
    SET_NOTE_EVENTS_ENABLE(46),
    SET_NOTE_EVENTS_DISABLE(47),
    SET_FULL_EVENTS_DISABLE(48),
    SET_FULL_EVENTS_ENABLE(49),
    SET_IGNITION_OFF_ENABLE(54),
    SET_IGNITION_OFF_DISABLE(55),
    SET_IGNITION_ON_ENABLE(56),
    SET_IGNITION_ON_DISABLE(57),
    BUZZER_MASTER_ENABLE(83),
    BUZZER_MASTER_DISABLE(84),
    BUZZER_SPEEDLIMIT_ENABLE(85),
    BUZZER_SPEEDLIMIT_DISABLE(86),
    BUZZER_SEATBELT_ENABLE(87),
    BUZZER_SEATBELT_DISABLE(88),
    BUZZER_RPM_ENABLE(89),
    BUZZER_RPM_DISABLE(90),
    BUZZER_NOTE_EVENTS_ENABLE(91),
    BUZZER_NOTE_EVENTS_DISABLE(92),
    BUZZER_FULL_EVENTS_ENABLE(93),
    BUZZER_FULL_EVENTS_DISABLE(94),
    GET_VERSION_INFO(174),
    IGNORE_RAMP_SPEED_ENABLE(230),
    IGNORE_RAMP_SPEED_DISABLE(231),
    GET_MCM_STATS(245),
    SET_LOW_POWER_EVENT_DISABLE(246),
    SET_LOW_POWER_EVENT_ENABLE(247),
    SET_UPDATE_ALWAYS_DISABLE(273),
    SET_UPDATE_ALWAYS_ENABLE(274),
    SET_STATS_EVENT_ENABLE(275),
    SET_STATS_EVENT_DISABLE(276),
    SET_SPEED_MODULE_ENABLED(299),
    SET_SPEED_MODULE_DISABLED(300),
    DOWNLOAD_NEW_FIRMWARE(365),
    DOWNLOAD_NEW_MAPS(366),
    DOWNLOAD_NEW_ZONES(367),
    SET_WSZONES_MODULE_ENABLED(386),
    SET_WSZONES_MODULE_DISABLED(387),
    SET_TRIAX_Z_LEVEL(449, "next byte of data is sent to triax for the z level"),
    SET_TRIAX_RMS_LEVEL(450, "next byte of data is sent to triax for the rms level"),
    SET_TRIAX_RMS_WINDOW(451, "next byte of data is sent to triax for the rms window"),
    SET_TRIAX_Y_WINDOW(452, "next byte of data is sent to triax for the y window"),
    SET_TRIAX_Y_LEVEL(453, "next byte of data is sent to triax for the y level"),
    SET_TRIAX_X_ACCEL(454, "next byte of data is sent to triax for the x accel"),
    SET_TRIAX_DVX(455, "next byte of data is sent to triax for the dvx level"),
    GET_TRIAX_STATUS(470),
    SET_TRIAX_SLOPE(490, "next byte of data is sent to triax to set the slope"),
    SET_TRIAX_ORIENTATION(554),
    SEND_TRIAX_CALIBRATE(578),
    SET_SPEED_LIMIT_VARIABLE(629),
    SET_SEVERE_SPEED_LIMIT_VARIABLE(630),
    SET_SPEED_BUFFER_VARIABLE(631),
    SET_SPEED_LIMIT_VARIABLE_KPH(633),
    SET_SEVERE_SPEED_LIMIT_VARIABLE_KPH(634),
    SET_SPEED_BUFFER_VARIABLE_KPH(635),
    DOWNLOAD_NEW_SMTOOLS_EMULATION(636),
    DOWNLOAD_NEW_SMTOOLS_FIRMWARE(637),
    SET_007_MODEM_PWR_STATE(638),
    SET_007_GPS_PWR_STATE(639),
    SET_007_BATT_PWR_STATE(640),
    SET_007_HH_PWR_STATE(641),
    SET_007_DASH_PWR_STATE(642),
    SET_007_SMTOOLS_RESET_STATE(643),
    SET_007_SMTOOLS_BUS_TYPE(644),
    ENABLE_SMTOOLS_DEVICE(645),
    KILL_DOWNLOAD_NEW_FIRMWARE(659),
    KILL_DOWNLOAD_NEW_MAPS(660),
    KILL_DOWNLOAD_NEW_ZONES(661),
    KILL_DOWNLOAD_NEW_WITNESSII_FIRMWARE(664),
    KILL_DOWNLOAD_NEW_SMTOOLS_EMULATION(668),
    KILL_DOWNLOAD_NEW_SMTOOLS_FIRMWARE(669),
    SET_GPRS_APN(688, "apn string follows the forward command"),
    SET_SERVER_URL(692, "server string follows the forward command"),
    SET_SERVER_CONTEXT(708, "context string follows the forward command"),
    SYSTEM_RESET(2000),
    GET_DIAGNOSTICS_REPORT(2001),
    RESET_DIAGNOSTICS(2002),
    SET_GPS_LOCATION_VARIABLE(2003, "0 for disabled seconds otherwise"),
    SET_RPM_VIOLATION_VARIABLE(2004, "0 for disabled rpm otherwise"),
    SET_SEATBELT_GRACE_PERIOD_VARIABLE(2005, "seconds"),
    // SET_NO_LOCK_BUFFER_VARIABLE (2006 // seconds
    SET_RAMP_SPEED_LIMIT_VARIABLE(2007, "0 for same as unit mph otherwise"),
    SET_RAMP_SPEED_LIMIT_VARIABLE_KPH(2008, "0 for same as unit kph otherwise"),
    SET_SEATBELT_VIOLATION_VARIABLE(2009, "0 for disabled mph otherwise"),
    SET_SEATBELT_VIOLATION_VARIABLE_KPH(2010, "0 for disabled kph otherwise"),
    SET_LOW_POWER_VARIABLE(2011, "seconds"),
    SET_UNKNOWN_SPEED_LIMIT_VARIABLE(2012, "0 for same as mcm mph otherwise"),
    SET_UNKNOWN_SPEED_LIMIT_VARIABLE_KPH(2013, "0 for same as mcm kph otherwise"),
    SET_SPEEDLIM_CHANGE_GRACE_PERIOD_VARIABLE(2014, "seconds"),
    SET_SPEEDING_GRACE_PERIOD_VARIABLE(2015, "seconds"),
    SMS_PING(2016, "added by Cheryl 11-30 will send sms back to sms sender with diag and version"),
    SMS_TOGGLE_PING(2017, "added by Cheryl 12-12 will turn on/off periodic SMS sending of diagnostics"),
    SET_CALL_NUMBER(2018, "added by Cheryl 1-18 will set phone number for unit to call primary e-call number -- expects string data with phone number (xxxxxxxxxx no dashes)"),
    MAKE_AUDIO_CALL(2019, "added by Cheryl 1-18 will have unit initialize call to its programmed phone number"),
    SET_SERVER_PORT(2020),
    SEND_TRIAX_ORIENT(2021),
    SET_MSGS_PER_NOTIFICATION(2022, "next value specifies # of messages"),
    SET_PARENT_MODE_ENABLE(2029, "put the unit in 'parent' mode so it doesn't communicate and the buzzers are off"),
    SET_PARENT_MODE_DISABLE(2030, "put the unit back into regular mode"),
    SET_SPEED_BUFFER_SLOPE(2045, "pass in the value * 100 in the int data (e.g. for a value of 1.04 pass in 104)"),
    ADD_VALID_CALLER(2055, "addition e-call number -- expects string data with index and phone number (# xxxxxxxxxx) always leave index 0 for the primary (see SET_CALL_NUMBER)"),
    SET_SPEED_ALARMS(
            2067,
            "value is a space delimited list of the mph values for when speed alarm sounds expects 15 values for speed limits 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 so for example if alarm should sound at 3 over the speed limit we would pass in 8 13 18 23 28 33 38 43 48 53 58 63 68 73 78"),
    DMM_SET_HARD_ACCEL(2091),
    DMM_SET_HARD_BRAKE(2092),
    DMM_SET_HARD_TURN(2093),
    DMM_SET_HARD_VERT(2094);
    private String name;
    private Integer code;
    private String description;

    private ForwardCommandType(String name, Integer code, String description)
    {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    private ForwardCommandType(Integer code, String description)
    {
        this.name = "";
        this.code = code;
        this.description = description;
    }

    private ForwardCommandType(Integer code)
    {
        this.name = "";
        this.code = code;
        this.description = "";
    }

    private static final Map<Integer, ForwardCommandType> lookup = new HashMap<Integer, ForwardCommandType>();
    static
    {
        for (ForwardCommandType p : EnumSet.allOf(ForwardCommandType.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static ForwardCommandType getForwardCommandType(Integer code)
    {
        return lookup.get(code);
    }

    public String getName()
    {
        return this.name();
    }

    public Integer getCode()
    {
        return this.code;
    }

    public String getDescription()
    {
        return this.description;
    }
}
