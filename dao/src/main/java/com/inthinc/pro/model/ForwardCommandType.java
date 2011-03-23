package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ForwardCommandType
{
	
/*
 *  Comment From Snitch: iwi.h
 *  Out of all of the forward commands below only the ones listed here should ever be sent
 *  unless you have a really good idea about what you are doing:
 *  SEND_POWER_CYCLE = 11, // reboot the unit
 *  BUZZER_MASTER_ENABLE = 83,
 *  BUZZER_MASTER_DISABLE = 84,
 *  SET_NO_DRIVER_EVENTS_DISABLE = 241,		// see if on portal 
 *  SET_NO_DRIVER_EVENTS_ENABLE = 242,
 *  GET_MCM_STATS = 245,
 *  DOWNLOAD_NEW_FIRMWARE = 365,
 *  DOWNLOAD_NEW_ZONES = 367,
 *  DOWNLOAD_AUDIO_FILE = 368,
 *  DOWNLOAD_NEW_WITNESSII_FIRMWARE = 556,
 *  DOWNLOAD_NEW_SMTOOLS_EMULATION = 636,
 *  SET_PARENT_MODE_ENABLE = 2029,
 *  SET_PARENT_MODE_DISABLE = 2030,
 *  SET_AUTO_LOGOUT_SECONDS = 2146,		// see if on portal 
 *  SET_NO_DRIVER_VIOLATION_OVERRIDE_ENABLE = 2157, 
 *  SET_NO_DRIVER_VIOLATION_OVERRIDE_DISABLE = 2158,
 *  
 *  // forward events
enum forward_commands {
	SET_FORWARD_CMD_EX = 1,

	GET_GPS_GET_LOCATION = 10,
	SEND_POWER_CYCLE = 11, // reboot the unit
	SET_DISABLE_UNIT = 12, // unit is completely disabled in the vehicle - still looks for enable command only
	SET_ENABLE_UNIT = 13,
	SET_DISABLE_COMMUNICATION = 14, // unit won't send anymore messages to cell network, but it will still receive forward commands
	SET_ENABLE_COMMUNICATION = 15,

	SET_SEATBELT_ENABLE = 40,
	SET_SEATBELT_DISABLE = 41,

	SET_SPEEDLIMIT_ENABLE = 42,
	SET_SPEEDLIMIT_DISABLE = 43,

	SET_RPM_ENABLE = 44,
	SET_RPM_DISABLE = 45,

	SET_NOTE_EVENTS_ENABLE = 46,
	SET_NOTE_EVENTS_DISABLE = 47,

	SET_FULL_EVENTS_DISABLE = 48,
	SET_FULL_EVENTS_ENABLE = 49,

	SET_FULL_EVENTS_ECALL_DISABLE = 50, //add ecall
	SET_FULL_EVENTS_ECALL_ENABLE = 51,  //add ecall

	SET_IGNITION_OFF_ENABLE = 54,
	SET_IGNITION_OFF_DISABLE = 55,

	SET_IGNITION_ON_ENABLE = 56,
	SET_IGNITION_ON_DISABLE = 57,

	BUZZER_MASTER_ENABLE = 83,
	BUZZER_MASTER_DISABLE = 84,

	BUZZER_SPEEDLIMIT_ENABLE = 85,
	BUZZER_SPEEDLIMIT_DISABLE = 86,

	BUZZER_SEATBELT_ENABLE = 87,
	BUZZER_SEATBELT_DISABLE = 88,

	BUZZER_RPM_ENABLE = 89,
	BUZZER_RPM_DISABLE = 90,

	BUZZER_NOTE_EVENTS_ENABLE = 91,
	BUZZER_NOTE_EVENTS_DISABLE = 92,

	BUZZER_FULL_EVENTS_ENABLE = 93,
	BUZZER_FULL_EVENTS_DISABLE = 94,

	GET_VERSION_INFO = 174,

	IGNORE_RAMP_SPEED_ENABLE = 230,
	IGNORE_RAMP_SPEED_DISABLE = 231,

	SET_NO_DRIVER_EVENTS_DISABLE = 241,
	SET_NO_DRIVER_EVENTS_ENABLE = 242,

	GET_MCM_STATS = 245,

	SET_LOW_POWER_EVENT_DISABLE = 246,
	SET_LOW_POWER_EVENT_ENABLE = 247,

	SET_UPDATE_ALWAYS_DISABLE = 273,
	SET_UPDATE_ALWAYS_ENABLE = 274,

	SET_STATS_EVENT_ENABLE = 275,
	SET_STATS_EVENT_DISABLE = 276,

	SET_SPEED_MODULE_ENABLED = 299,
	SET_SPEED_MODULE_DISABLED = 300,

	DOWNLOAD_NEW_FIRMWARE = 365,
	DOWNLOAD_NEW_MAPS = 366,
	DOWNLOAD_NEW_ZONES = 367,
    DOWNLOAD_AUDIO_FILE = 368,

	SET_WSZONES_MODULE_ENABLED = 386,
	SET_WSZONES_MODULE_DISABLED = 387,

	SET_TRIAX_Z_LEVEL = 449, // next byte of data is sent to triax for the z level
	SET_TRIAX_Y_LEVEL = 453,  // next byte of data is sent to triax for the y level
	SET_TRIAX_DVX = 455,  // next byte of data is sent to triax for the dvx level
	GET_DMM_STATUS = 470,

    DOWNLOAD_NEW_WITNESSII_FIRMWARE = 556,
	SEND_TRIAX_CALIBRATE = 578,

	SET_SPEED_LIMIT_VARIABLE = 629,
	SET_SEVERE_SPEED_LIMIT_VARIABLE = 630,
	SET_SPEED_BUFFER_VARIABLE = 631,

	SET_SPEED_LIMIT_VARIABLE_KPH = 633,
	SET_SEVERE_SPEED_LIMIT_VARIABLE_KPH = 634,
	SET_SPEED_BUFFER_VARIABLE_KPH = 635,

	DOWNLOAD_NEW_SMTOOLS_EMULATION = 636,
	DOWNLOAD_NEW_SMTOOLS_FIRMWARE = 637,

	SET_007_SMTOOLS_RESET_STATE = 643,

	ENABLE_SMTOOLS_DEVICE = 645,

    SET_AGCTL_VALUE = 650,
    GET_VEHICLE_VOLTAGE = 651,

    LOG_XVAR_SHORT_FORMAT = 652,

    INIT_TIWIPRO_MFG_TEST = 653,
    RUN_TIWIPRO_MFG_TEST = 654,

	KILL_DOWNLOAD_NEW_FIRMWARE = 659,
	KILL_DOWNLOAD_NEW_MAPS = 660,
	KILL_DOWNLOAD_NEW_ZONES = 661,
	KILL_DOWNLOAD_NEW_WITNESSII_FIRMWARE = 664,
	KILL_DOWNLOAD_NEW_SMTOOLS_EMULATION = 668,
	KILL_DOWNLOAD_NEW_SMTOOLS_FIRMWARE = 669,

	SET_GPRS_APN = 688, // apn string follows the forward command

	SET_SERVER_URL = 692, // server string follows the forward command

    SEND_DMM_ERASE_ALL_TEMP_COMP_SLOPES = 775,
    SET_HARD_VERTICAL_SPEED_THRESH = 801,       //Same id as waysmart
    SET_HARD_VERTICAL_SEVERE_PKPK = 802,

	// added for the teen product
	SYSTEM_RESET            = 2000,
	GET_DIAGNOSTICS_REPORT	= 2001,
	RESET_DIAGNOSTICS		= 2002,
	SET_GPS_LOCATION_VARIABLE = 2003, // 0 for disabled, seconds otherwise
	SET_RPM_VIOLATION_VARIABLE = 2004, // 0 for disabled, rpm otherwise
	SET_SEATBELT_GRACE_PERIOD_VARIABLE = 2005, // seconds
	SET_RAMP_SPEED_LIMIT_VARIABLE = 2007, // 0 for same as unit, mph otherwise
	SET_RAMP_SPEED_LIMIT_VARIABLE_KPH = 2008, // 0 for same as unit, kph otherwise
	SET_SEATBELT_VIOLATION_VARIABLE = 2009, // 0 for disabled, mph otherwise
	SET_SEATBELT_VIOLATION_VARIABLE_KPH = 2010, // 0 for disabled, kph otherwise
	SET_LOW_POWER_VARIABLE = 2011, // seconds
	SET_SPEEDLIM_CHANGE_GRACE_PERIOD_VARIABLE = 2014, // seconds
	SET_SPEEDING_GRACE_PERIOD_VARIABLE = 2015, // seconds

	SMS_PING = 2016,			// added by Cheryl 11-30, will send sms back to sms sender with diag and version
	SMS_TOGGLE_PING = 2017,		// added by Cheryl 12-12, will turn on/off periodic SMS sending of diagnostics
	SET_CALL_NUMBER = 2018,		// added by Cheryl 1-18, will set phone number for unit to call
	MAKE_AUDIO_CALL = 2019,		// added by Cheryl 1-18, will have unit initialize call to its programmed phone number

	SET_SERVER_PORT = 2020,

	SEND_TRIAX_ORIENT = 2021,

	SET_MSGS_PER_NOTIFICATION = 2022, // next value specifies # of messages

	SET_SPEEDLIMIT_START_ENABLE = 2027,
	SET_SPEEDLIMIT_START_DISABLE = 2028,

	SET_PARENT_MODE_ENABLE = 2029,
	SET_PARENT_MODE_DISABLE = 2030,

    SET_SMTOOLS_SECURITY_URL = 2031,
    SET_SMTOOLS_SECURITY_CONTEXT = 2032,
    SET_SMTOOLS_EMU_URL = 2033,
    SET_SMTOOLS_EMU_CONTEXT = 2034,
    RESET_SMTLS_DIAGNOSTICS = 2037,

    DEPRECATED_SET_SPEED_BUFFER_SLOPE = 2038,
    DEPRECATED_SET_SPEED_BUFFER_MINIMUM = 2039,

    DEPRECATED_SET_SPEED_BUZZER_BUFFER = 2042,

	SET_SEATBELT_START_ENABLE = 2043,
	SET_SEATBELT_START_DISABLE = 2044,

	DEPRECATED_SPEED_BUFFER_SLOPE = 2045,

	SET_DEBUG_GPS_ENABLE = 2046,
	SET_DEBUG_GPS_DISABLE = 2047,

    SMTOOLS_DEVICE_ENABLE = 2049,
    SMTOOLS_DEVICE_DISABLE = 2050,
    SMTOOLS_DISABLE_SECURITY = 2051,
    SMTOOLS_DEACTIVATE_OVERRIDE_ENABLE = 2052,
    SMTOOLS_DEACTIVATE_OVERRIDE_DISABLE = 2053,

    DOWNLOAD_DOTA_FIRMWARE = 2054,	// does it the old, http way instead of Hessian
	ADD_VALID_CALLER = 2055,		// added by Cheryl 6-10
	SET_GPRS_APN2 = 2056, // apn string follows the forward command

 	DOWNLOAD_SMTOOLS_MANUFACTURING_TEST_SLAVE_EMU = 2057,
    DOWNLOAD_SMTOOLS_EMU_UNDER_DEVELOPMENT = 2058,

    SET_RAMP_SPEED_LIMIT_BUFFER = 2060,
    SET_GPS_MIN_SNR_FOR_BEST_TRHEE_SATS_THRESHOLD = 2061,

	GET_SMTOOLS_DEVICE_STATUS = 2062,
    SET_SPEAKER_GAIN = 2063,

    SET_SEVERE_SPEEDING_GRACE_PERIOD_VARIABLE = 2064,
    SET_SUPERTRACK_ENABLE = 2065,
    SET_SUPERTRACK_DISABLE = 2066,

    SET_SPEED_BUFFER_VALUES = 2067,

    DOWNLOAD_NEW_SMTOOLS_FIRMWARE_FROM_SD_CARD = 2068,

    SET_SPEEDING_TIME_TO_LED = 2071,
    SET_SPEEDING_TIME_TO_AUDIO = 2072,

    SET_NVP_LATENCY_CRITERIA = 2073,      // NVP stand for NewVerifiedPosition
    SET_NVP_MIN_DISTANCE_CHANGE = 2074,

    SET_DEBUGOBD_ENABLE = 2075,
    SET_DEBUGOBD_DISABLE = 2076,

    SET_CAR_BATTERY_VEHICLE_ON = 2077,
    SET_LOG_NMEA = 2078,

    SET_TIWI_FLASH = 2080,
    SET_SMTOOLS_FLASH = 2081,
    SET_WITNESS_FLASH = 2082,

    SET_SEND_RFID = 2083,
    SET_QA_BUZZER = 2084,
    SET_DESIRED_A_AND_D = 2085,

	SET_ROLLOVER_EVENTS_ENABLE = 2086,
	SET_ROLLOVER_EVENTS_DISABLE = 2087,
	SET_ROLLOVER_EVENTS_ECALL_ENABLE = 2088,
	SET_ROLLOVER_EVENTS_ECALL_DISABLE = 2089,
	SET_CRASH_ROLLOVER_RENOTIFICATION_DELAY = 2090,
    DMM_SET_HARD_ACCEL = 2091,
    DMM_SET_HARD_BRAKE = 2092,
    DMM_SET_HARD_TURN = 2093,
    DMM_SET_HARD_BUMP = 2094,

	SET_IDLING_ENABLE = 2095,
	SET_IDLING_DISABLE = 2096,

	SET_IDLE_TIMEOUT_SECONDS = 2097,
    GET_VEHICLE_MPG = 2098,
    SET_XLOGS_ENABLE  = 2099,

	BUZZER_ROLLOVER_ENABLE = 2100,
	BUZZER_ROLLOVER_DISABLE = 2101,

	SET_FACTORY_DEFAULTS = 2102,

	CLEAR_EVENT_HISTORY = 2103,

    SAVE_TRACES = 2106,

    SET_AUTOSAVE_EVENTHISTORY_ENABLE = 2107,
    SET_AUTOSAVE_EVENTHISTORY_DISABLE = 2108,

    SET_PHONE_GAIN = 2109,

    SET_GPRS_LOGIN = 2110,
    SET_GPRS_PASSWORD = 2111,
    SET_GPRS_PIN = 2112,
    GET_PROP_PING = 2113,
    GET_AIR_GAP_CONFIG = 2114,

    SET_FTP_URL = 2115,
    SET_FTP_USERID = 2116,
    SET_FTP_PASSWORD = 2117,
    FTP_UPLOAD = 2118,
    CLEAR_DIR = 2119,

    SET_SUB_THRESHOLD_CRASH_ENABLE = 2120,
    SET_SUB_THRESHOLD_CRASH_DISABLE = 2121,
    SET_CRASH_TRACE_ENABLE = 2122,
    SET_CRASH_TRACE_DISABLE = 2123,
    SET_FORCE_MSP_FOR_IGN_TRUE = 2124,
    SET_FORCE_MSP_FOR_IGN_FALSE = 2125,
    SET_EMU_INVALID_WAIT_COUNT = 2126,
    SET_AUDIO_FOLDER = 2127,
    SET_SERIAL_NUMBER = 2128,

    LOAD_DRIVER = 2129,
    //2130 - 2132 - Deprecated.
    DOWNLOAD_DRIVER_FILE = 2133,

    SET_CRASH_DATA_ENABLED = 2134,
    SET_CRASH_DATA_DISABLED = 2135,

    RESET_BULK_QUEUE_LOCKOUT = 2136,
    SET_GPS_FILTER_ENABLE = 2137,
    SET_GPS_FILTER_DISABLE = 2138,

    SET_STATISTICS_FREQUENCY = 2139,
    SET_AVG_SPD_AGMT = 2140,
    SET_IND_SPD_AGMT = 2141,
    INVALID_DRIVER = 2142,
    ASSIGN_DRIVER = 2143,
    UNASSIGN_DRIVER = 2144,
    RECOMPACT_AND_REFORMAT = 2145,
    SET_AUTO_LOGOUT_SECONDS = 2146,

    SET_STREET_ENVELOPE_PADDING = 2147,
    SET_STREET_MAX_ANGLE = 2148,
    SET_STREET_WINDOW = 2149,

    SET_AGPS_ENABLE = 2150,
    SET_AGPS_DISABLE = 2151,

    SET_AGPS_SERVER_URL = 2152,
    SET_AGPS_SERVER_PORT = 2153,

    SET_AGPS_RETRY_INTERVAL = 2154,
    SET_AGPS_NUM_DWNLD_RETRIES = 2155,
    SET_AGPS_NUM_CONN_RETRIES = 2156,

    SET_NO_DRIVER_VIOLATION_OVERRIDE_ENABLE = 2157,
    SET_NO_DRIVER_VIOLATION_OVERRIDE_DISABLE = 2158,

	SET_ROLLOVER_FILTER = 2159,
    SET_CRASH_THRESHOLDS = 2160,

    SET_ODO_TAMPERING_MILEAGE_THRESH = 2161,

    SET_WRITE_CRASH_DATA_TO_SD_OFF = 2162,
    SET_WRITE_CRASH_DATA_TO_SD_ON = 2163,

};


	
 */
    GET_GPS_GET_LOCATION(10, "Retrieve the location of the unit.  Parameter: NONE", Boolean.FALSE),
    SEND_POWER_CYCLE(11, "Reboot the unit.  Parameter: NONE", Boolean.FALSE),
    SET_DISABLE_UNIT(12, "Unit is completely disabled in the vehicle - still looks for enable command only.  Parameter: NONE"),
    SET_ENABLE_UNIT(13, "Reenable unit after it has been disabled.  Parameter: NONE"),
    SET_DISABLE_COMMUNICATION(14, "Unit won't send anymore messages to cell network, but it will still receive forward commands. Parameter: NONE"),
    SET_ENABLE_COMMUNICATION(15, "Reenable communications after a disable.  Parameter: NONE" ),
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
    BUZZER_MASTER_ENABLE(83, "Turn on in vehicle audio. Parameter: NONE", Boolean.FALSE),
    BUZZER_MASTER_DISABLE(84, "Turn off in vehicle audio. Parameter: NONE", Boolean.FALSE),
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
    GET_VERSION_INFO(174, "The same information can be retrieved from unit power on note."),
    IGNORE_RAMP_SPEED_ENABLE(230),
    IGNORE_RAMP_SPEED_DISABLE(231),
    SET_NO_DRIVER_EVENTS_DISABLE(241, "Turn off sending of NO_DRIVER events.  Parameter: NONE", Boolean.FALSE),
    SET_NO_DRIVER_EVENTS_ENABLE(242, "Turn on sending of NO_DRIVER events.  Parameter: NONE", Boolean.FALSE),
    GET_MCM_STATS(245, "???", Boolean.FALSE),
    SET_LOW_POWER_EVENT_DISABLE(246),
    SET_LOW_POWER_EVENT_ENABLE(247),
    SET_UPDATE_ALWAYS_DISABLE(273),
    SET_UPDATE_ALWAYS_ENABLE(274),
    SET_STATS_EVENT_ENABLE(275),
    SET_STATS_EVENT_DISABLE(276),
    SET_SPEED_MODULE_ENABLED(299),
    SET_SPEED_MODULE_DISABLED(300),
    DOWNLOAD_NEW_FIRMWARE(365, "Download new firmware.  Parameter(Integer): Firmware version number", Boolean.FALSE),
    DOWNLOAD_NEW_MAPS(366, "Download new maps.  Parameter(Integer): Map revision number"),
    DOWNLOAD_NEW_ZONES(367, "Download new zones.  Parameter: NONE", Boolean.FALSE),
    DOWNLOAD_AUDIO_FILE(368, "Download latest audio file from server.  Parameter: NONE", Boolean.FALSE),
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
    DOWNLOAD_NEW_WITNESSII_FIRMWARE(556, "Download new witness II firmware.  Parameter(Integer): Firmware version number", Boolean.FALSE),
    SEND_TRIAX_CALIBRATE(578),
    WIRELINE_LIGHT_DISARM(590),
    SET_SPEED_LIMIT_VARIABLE(629),
    SET_SEVERE_SPEED_LIMIT_VARIABLE(630),
    SET_SPEED_BUFFER_VARIABLE(631),
    SET_SPEED_LIMIT_VARIABLE_KPH(633),
    SET_SEVERE_SPEED_LIMIT_VARIABLE_KPH(634),
    SET_SPEED_BUFFER_VARIABLE_KPH(635),
    DOWNLOAD_NEW_SMTOOLS_EMULATION(636, "Download new sm tools emulation.  Parameters: NONE", Boolean.FALSE),
    DOWNLOAD_NEW_SMTOOLS_FIRMWARE(637, "Download new sm tools firmware.  Parameters(Integer): revision number"),
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
    SET_SERVER_URL(692, "Set URL of server that unit communicates with. Parameter(String): url of server unit should communicate with"),
//    SET_SERVER_CONTEXT(708, "context string follows the forward command"),	/ NOT SUPPORTED

    WIRELINE_ENABLE_DOOR_ALARM(728),
    WIRELINE_DISABLE_DOOR_ALARM(729),
    WIRELINE_SET_DOOR_ALARM_PASSCODE(730, "Enter a 4 Digit Passcode. Parameter(Integer): "),
    WIRELINE_ENABLE_KILL_MOTOR(731),
    WIRELINE_DISABLE_KILL_MOTOR(732),
    WIRELINE_SET_KILL_MOTOR_PASSCODE(733, "Enter a 4 Digit Passcode. Parameter(Integer): "),
    WIRELINE_SET_AUTO_ARM_TIME(734, "Time in minutes (0 to 255). Parameter(Integer): "),
    WIRELINE_GET_STATUS(735),
    WIRELINE_RUN_IGNITION_TEST(736),
    WIRELINE_LIGHT_ENABLE(752),
    WIRELINE_ENABLE_MODULE(753),
    WIRELINE_DISABLE_MODULE(754),

    SET_TRIAX_HARDACCEL_DELTAV(797,"next byte is the accel delta V thres in mph * 10"),
    SET_TRIAX_HARDACCEL_LEVEL(795,"next byte is the accel level thres in g * 10 (-0.05g)"),
    SET_TRIAX_DVY(772,"byte"),
    SET_TRIAX_HARDVERT_PEAK_TO_PEAK_LEVEL_THRESHOLD(800,"next two bytes is the hard vertical peak to peak threshold in g * 100"),
//  SET_TRIAX_SEVERE_HARDVERT_LEVEL = 802,                // next 4 bytes is Gs 100x
    SEVERE_PEAK_2_PEAK(802, "Severe Peak to Peak (sent in conjuction with 2091-2094).  Parameter(Integer): <severe peak to peak level>"),
    SYSTEM_RESET(2000, "Reboot the unit.  Parameter: NONE"),
//    GET_DIAGNOSTICS_REPORT(2001),   ? NOT SUPPORTED
    RESET_DIAGNOSTICS(2002, "Parameter: NONE"),
    SET_GPS_LOCATION_VARIABLE(2003, "Parameter(Integer): 0 for disabled seconds otherwise"),
    SET_RPM_VIOLATION_VARIABLE(2004, "Parameter(Integer): 0 for disabled rpm otherwise"),
    SET_SEATBELT_GRACE_PERIOD_VARIABLE(2005, "Grace period before sending seatbelt violation. Parameter(Integer): seconds"),
    // SET_NO_LOCK_BUFFER_VARIABLE (2006 // seconds
    SET_RAMP_SPEED_LIMIT_VARIABLE(2007, "Parameter(Integer): 0 for same as unit mph otherwise"),
    SET_RAMP_SPEED_LIMIT_VARIABLE_KPH(2008, "Parameter(Integer): 0 for same as unit kph otherwise"),
    SET_SEATBELT_VIOLATION_VARIABLE(2009, "Parameter(Integer): 0 for disabled mph otherwise mph"),
    SET_SEATBELT_VIOLATION_VARIABLE_KPH(2010, "Parameter(Integer): 0 for disabled kph otherwise kph"),
    SET_LOW_POWER_VARIABLE(2011, "Seconds to delay before switching to low power mode.  Parameter(Integer): seconds"),
//    SET_UNKNOWN_SPEED_LIMIT_VARIABLE(2012, "0 for same as mcm mph otherwise"),  ? NOT SUPPORTED
//    SET_UNKNOWN_SPEED_LIMIT_VARIABLE_KPH(2013, "0 for same as mcm kph otherwise"), ? NOT SUPPORTED
    SET_SPEEDLIM_CHANGE_GRACE_PERIOD_VARIABLE(2014, "Grace period before sending speeding violation when the speed limit changes. Parameter(Integer): seconds"),
    SET_SPEEDING_GRACE_PERIOD_VARIABLE(2015, "Grace period before sending speeding violation. Parameter(Integer): seconds"),
    SMS_PING(2016, "Will send sms back to sms sender with diag and version."),
    SMS_TOGGLE_PING(2017, "Will turn on/off periodic SMS sending of diagnostics"),
    SET_CALL_NUMBER(2018, "Will set phone number for unit to call primary e-call number -- expects string data with phone number (xxxxxxxxxx no dashes)"),
    MAKE_AUDIO_CALL(2019, "Will have unit initialize call to its programmed phone number"),
    SET_SERVER_PORT(2020, "Set port on server that unit communicates with.  Parameter(Integer): Port number"),
//    SEND_TRIAX_ORIENT(2021),   ? NOT SUPPORTED?
    SET_MSGS_PER_NOTIFICATION(2022, "Set number of messages per notification (default 4). Parameter(Integer): number of messages"),
    SET_PARENT_MODE_ENABLE(2029, "Put the unit in 'parent' mode so it doesn't communicate and the buzzers are off. Parameter: NONE", Boolean.FALSE),
    SET_PARENT_MODE_DISABLE(2030, "Put the unit back into regular mode. Parameter: NONE", Boolean.FALSE),
    SET_SPEED_BUFFER_SLOPE(2045, "Parameter(Integer): pass in the value * 100 in the int data (e.g. for a value of 1.04 pass in 104)"),
    ADD_VALID_CALLER(2055, "Addition e-call numbers. Parameter(String): string data with index and phone number (# xxxxxxxxxx) always leave index 0 for the primary (see SET_CALL_NUMBER)"),
    SET_SPEED_ALARMS(
            2067,
            "Sets list of the mph values for when speed alarm sounds.  Parameter(String): 15 values for speed limits 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 so for example if alarm should sound at 3 over the speed limit we would pass in 8 13 18 23 28 33 38 43 48 53 58 63 68 73 78"),
    DMM_SET_HARD_ACCEL(2091,"Adjust Hard Acceleration Thresholds.  Parameter(String): <Peak to Peak level> <RMS level> <Slider Position>"),
    DMM_SET_HARD_BRAKE(2092,"Adjust Hard Brake Thresholds.  Parameter(String): <Peak to Peak level> <RMS level> <Slider Position>"),
    DMM_SET_HARD_TURN(2093,"Adjust Hard Turn Thresholds.  Parameter(String): <Peak to Peak level> <RMS level> <Slider Position>"),
    DMM_SET_HARD_VERT(2094, "Adjust Hard Vertical Thresholds.  Parameter(String): <Peak to Peak level> <RMS level> <Slider Position>"),
    INVALID_DRIVER(2142, "Invalid Driver. Parameter(String): <rfid>"), 
    ASSIGN_DRIVER(2143, "Assign Driver. Parameter(String): <driverID> <rfid1> <rfid2> rfids are optional"), 
	UNASSIGN_DRIVER(2144, "Unassign Driver. Parameter(String): <driverID>"), 
    SET_AUTO_LOGOUT_SECONDS(2146, "Set auto logout seconds. Parameter(Integer): <seconds>", Boolean.FALSE),
    SET_NO_DRIVER_VIOLATION_OVERRIDE_ENABLE(2157, "Enable NO DRIVER violation override. Parameter: NONE", Boolean.FALSE),
    SET_NO_DRIVER_VIOLATION_OVERRIDE_DISABLE(2158, "Disable NO DRIVER violation override. Parameter: NONE", Boolean.FALSE),
    ;
    
    
    
    
    private String name;
    private Integer code;
    private String description;
    private Boolean advancedUse;

	private ForwardCommandType(String name, Integer code, String description)
    {
        this.name = name;
        this.code = code;
        this.description = description;
        this.advancedUse = true;
    }

    private ForwardCommandType(Integer code, String description)
    {
        this.name = "";
        this.code = code;
        this.description = description;
        this.advancedUse = true;
    }

    private ForwardCommandType(Integer code)
    {
        this.name = "";
        this.code = code;
        this.description = "";
        this.advancedUse = true;
    }
    private ForwardCommandType(Integer code, String description, Boolean advancedUse)
    {
        this.name = "";
        this.code = code;
        this.description = description;
        this.advancedUse = advancedUse;
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
    public Boolean getAdvancedUse() {
		return advancedUse;
	}


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();

    }
}
