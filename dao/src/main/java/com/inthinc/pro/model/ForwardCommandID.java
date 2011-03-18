package com.inthinc.pro.model;

/**
 * List of forward command IDs. These must match the list for the device (snitch). Only a subset of these will be sent by the portal. This is a list of constants instead of an
 * enum, so that it can be extended without having to change portal code.
 * 
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface ForwardCommandID
{
    public static Integer GET_GPS_GET_LOCATION = 10;
    public static Integer SEND_POWER_CYCLE = 11; // reboot the unit
    public static Integer SET_DISABLE_UNIT = 12; // unit is completely disabled in the vehicle - still looks for enable command only
    public static Integer SET_ENABLE_UNIT = 13;
    public static Integer SET_DISABLE_COMMUNICATION = 14; // unit won't send anymore messages to cell network, but it will still receive forward commands
    public static Integer SET_ENABLE_COMMUNICATION = 15;

    public static Integer SET_SEATBELT_ENABLE = 40;
    public static Integer SET_SEATBELT_DISABLE = 41;

    public static Integer SET_SPEEDLIMIT_ENABLE = 42;
    public static Integer SET_SPEEDLIMIT_DISABLE = 43;

    public static Integer SET_RPM_ENABLE = 44;
    public static Integer SET_RPM_DISABLE = 45;

    public static Integer SET_NOTE_EVENTS_ENABLE = 46;
    public static Integer SET_NOTE_EVENTS_DISABLE = 47;

    public static Integer SET_FULL_EVENTS_DISABLE = 48;
    public static Integer SET_FULL_EVENTS_ENABLE = 49;

    public static Integer SET_IGNITION_OFF_ENABLE = 54;
    public static Integer SET_IGNITION_OFF_DISABLE = 55;

    public static Integer SET_IGNITION_ON_ENABLE = 56;
    public static Integer SET_IGNITION_ON_DISABLE = 57;

    public static Integer BUZZER_MASTER_ENABLE = 83;
    public static Integer BUZZER_MASTER_DISABLE = 84;

    public static Integer BUZZER_SPEEDLIMIT_ENABLE = 85;
    public static Integer BUZZER_SPEEDLIMIT_DISABLE = 86;

    public static Integer BUZZER_SEATBELT_ENABLE = 87;
    public static Integer BUZZER_SEATBELT_DISABLE = 88;

    public static Integer BUZZER_RPM_ENABLE = 89;
    public static Integer BUZZER_RPM_DISABLE = 90;

    public static Integer BUZZER_NOTE_EVENTS_ENABLE = 91;
    public static Integer BUZZER_NOTE_EVENTS_DISABLE = 92;

    public static Integer BUZZER_FULL_EVENTS_ENABLE = 93;
    public static Integer BUZZER_FULL_EVENTS_DISABLE = 94;

    public static Integer GET_VERSION_INFO = 174;

    public static Integer DOWNLOAD_HOS_LOGS = 217;
    public static Integer IGNORE_RAMP_SPEED_ENABLE = 230;
    public static Integer IGNORE_RAMP_SPEED_DISABLE = 231;

    public static Integer GET_MCM_STATS = 245;

    public static Integer SET_LOW_POWER_EVENT_DISABLE = 246;
    public static Integer SET_LOW_POWER_EVENT_ENABLE = 247;

    public static Integer SET_UPDATE_ALWAYS_DISABLE = 273;
    public static Integer SET_UPDATE_ALWAYS_ENABLE = 274;

    public static Integer SET_STATS_EVENT_ENABLE = 275;
    public static Integer SET_STATS_EVENT_DISABLE = 276;

    public static Integer SET_SPEED_MODULE_ENABLED = 299;
    public static Integer SET_SPEED_MODULE_DISABLED = 300;
    
    public static Integer HOSLOG_SUMMARY = 333;

    public static Integer SEND_TEXT_MESSAGE = 355;

    public static Integer DOWNLOAD_NEW_FIRMWARE = 365;
    public static Integer DOWNLOAD_NEW_MAPS = 366;
    public static Integer DOWNLOAD_NEW_ZONES = 367;

    public static Integer SET_WSZONES_MODULE_ENABLED = 386;
    public static Integer SET_WSZONES_MODULE_DISABLED = 387;

    public static Integer SET_TRIAX_Z_LEVEL = 449; // next byte of data is sent to triax for the z level
    public static Integer SET_TRIAX_RMS_LEVEL = 450; // next byte of data is sent to triax for the rms level
    public static Integer SET_TRIAX_RMS_WINDOW = 451; // next byte of data is sent to triax for the rms window
    public static Integer SET_TRIAX_Y_WINDOW = 452; // next byte of data is sent to triax for the y window
    public static Integer SET_TRIAX_Y_LEVEL = 453; // next byte of data is sent to triax for the y level
    public static Integer SET_TRIAX_X_ACCEL = 454; // next byte of data is sent to triax for the x accel
    public static Integer SET_TRIAX_DVX = 455; // next byte of data is sent to triax for the dvx level

    public static Integer GET_TRIAX_STATUS = 470;
    public static Integer SET_TRIAX_SLOPE = 490; // next byte of data is sent to triax to set the slope

    public static Integer SET_TRIAX_ORIENTATION = 554;
    public static Integer SEND_TRIAX_CALIBRATE = 578;

    public static Integer SET_SPEED_LIMIT_VARIABLE = 629;
    public static Integer SET_SEVERE_SPEED_LIMIT_VARIABLE = 630;
    public static Integer SET_SPEED_BUFFER_VARIABLE = 631;

    public static Integer SET_SPEED_LIMIT_VARIABLE_KPH = 633;
    public static Integer SET_SEVERE_SPEED_LIMIT_VARIABLE_KPH = 634;
    public static Integer SET_SPEED_BUFFER_VARIABLE_KPH = 635;

    public static Integer DOWNLOAD_NEW_SMTOOLS_EMULATION = 636;
    public static Integer DOWNLOAD_NEW_SMTOOLS_FIRMWARE = 637;

    public static Integer SET_007_MODEM_PWR_STATE = 638;
    public static Integer SET_007_GPS_PWR_STATE = 639;
    public static Integer SET_007_BATT_PWR_STATE = 640;
    public static Integer SET_007_HH_PWR_STATE = 641;
    public static Integer SET_007_DASH_PWR_STATE = 642;
    public static Integer SET_007_SMTOOLS_RESET_STATE = 643;
    public static Integer SET_007_SMTOOLS_BUS_TYPE = 644;

    public static Integer ENABLE_SMTOOLS_DEVICE = 645;

    public static Integer KILL_DOWNLOAD_NEW_FIRMWARE = 659;
    public static Integer KILL_DOWNLOAD_NEW_MAPS = 660;
    public static Integer KILL_DOWNLOAD_NEW_ZONES = 661;
    public static Integer KILL_DOWNLOAD_NEW_WITNESSII_FIRMWARE = 664;
    public static Integer KILL_DOWNLOAD_NEW_SMTOOLS_EMULATION = 668;
    public static Integer KILL_DOWNLOAD_NEW_SMTOOLS_FIRMWARE = 669;

    public static Integer SET_GPRS_APN = 688; // apn string follows the forward command

    public static Integer SET_SERVER_URL = 692; // server string follows the forward command
    public static Integer SET_SERVER_CONTEXT = 708; // context string follows the forward command

    // added for the teen product
    public static Integer SYSTEM_RESET = 2000;
    public static Integer GET_DIAGNOSTICS_REPORT = 2001;
    public static Integer RESET_DIAGNOSTICS = 2002;
    public static Integer SET_GPS_LOCATION_VARIABLE = 2003; // 0 for disabled; seconds otherwise
    public static Integer SET_RPM_VIOLATION_VARIABLE = 2004; // 0 for disabled; rpm otherwise
    public static Integer SET_SEATBELT_GRACE_PERIOD_VARIABLE = 2005; // seconds
    // SET_NO_LOCK_BUFFER_VARIABLE = 2006; // seconds
    public static Integer SET_RAMP_SPEED_LIMIT_VARIABLE = 2007; // 0 for same as unit; mph otherwise
    public static Integer SET_RAMP_SPEED_LIMIT_VARIABLE_KPH = 2008; // 0 for same as unit; kph otherwise
    public static Integer SET_SEATBELT_VIOLATION_VARIABLE = 2009; // 0 for disabled; mph otherwise
    public static Integer SET_SEATBELT_VIOLATION_VARIABLE_KPH = 2010; // 0 for disabled; kph otherwise
    public static Integer SET_LOW_POWER_VARIABLE = 2011; // seconds
    public static Integer SET_UNKNOWN_SPEED_LIMIT_VARIABLE = 2012; // 0 for same as mcm; mph otherwise
    public static Integer SET_UNKNOWN_SPEED_LIMIT_VARIABLE_KPH = 2013; // 0 for same as mcm; kph otherwise
    public static Integer SET_SPEEDLIM_CHANGE_GRACE_PERIOD_VARIABLE = 2014; // seconds
    public static Integer SET_SPEEDING_GRACE_PERIOD_VARIABLE = 2015; // seconds

    public static Integer SMS_PING = 2016; // added by Cheryl 11-30; will send sms back to sms sender with diag and version
    public static Integer SMS_TOGGLE_PING = 2017; // added by Cheryl 12-12; will turn on/off periodic SMS sending of diagnostics
    public static Integer SET_CALL_NUMBER = 2018; // added by Cheryl 1-18; will set phone number for unit to call
    // primary e-call number -- expects string data with phone number (xxxxxxxxxx no dashes)
    public static Integer MAKE_AUDIO_CALL = 2019; // added by Cheryl 1-18; will have unit initialize call to its programmed phone number

    public static Integer SET_SERVER_PORT = 2020;

    public static Integer SEND_TRIAX_ORIENT = 2021;

    public static Integer SET_MSGS_PER_NOTIFICATION = 2022; // next value specifies # of messages

    public static Integer SET_PARENT_MODE_ENABLE = 2029; // put the unit in 'parent' mode so it doesn't communicate and the buzzers are off
    public static Integer SET_PARENT_MODE_DISABLE = 2030; // put the unit back into regular mode

    public static Integer SET_SPEED_BUFFER_SLOPE = 2045; // pass in the value * 100 in the int data (e.g. for a value of 1.04 pass in 104)

    public static Integer ADD_VALID_CALLER = 2055;
    // addition e-call number -- expects string data with index and phone number (# xxxxxxxxxx)
    // always leave index 0 for the primary (see SET_CALL_NUMBER)

    public static Integer SET_SPEED_ALARMS = 2067; // value is a space delimited list of the mph values for when speed alarm sounds
    // expects 15 values for speed limits 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75
    // so for example if alarm should sound at 3 over the speed limit we would pass in
    // 8 13 18 23 28 33 38 43 48 53 58 63 68 73 78

    public static Integer DMM_SET_HARD_ACCEL = 2091;
    public static Integer DMM_SET_HARD_BRAKE = 2092;
    public static Integer DMM_SET_HARD_TURN = 2093;
    public static Integer DMM_SET_HARD_VERT = 2094;

    public static Integer AUTO_LOGOFF = 2146;		// set integer data to 900 for on and 0 for off
}
