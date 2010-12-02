package com.inthinc.pro.device.tiwiPro;


import java.util.*;


public class TiwiPro_Defaults{
    
    public static HashMap<Integer, String> get_defaults(){
        
        
        HashMap<Integer, String> DEFAULT = new HashMap<Integer, String>();

        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_MASTER.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_SERVER_URL.getCode(), "qa.tiwipro.com");
        DEFAULT.put( TiwiPro.PROPERTY_GPRS_APN.getCode(), "safety.t-mobile.com");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_LOCATION_SECONDS.getCode(), "15");
        DEFAULT.put( TiwiPro.PROPERTY_LOCATION_UPDATE_ALWAYS.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_ZONE_MODULE.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_SPEEDING.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_SPEED.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_SPEED_MODULE.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_SPEED_LIMIT.getCode(), "78.0");
        DEFAULT.put( TiwiPro.PROPERTY_SPEEDING_GRACE_PERIOD.getCode(), "15");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_RPM.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_RPM.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_RPM_LIMIT.getCode(), "5000");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_SEATBELT.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_SEATBELT.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_SEATBELT_GRACE_PERIOD.getCode(), "10");
        DEFAULT.put( TiwiPro.PROPERTY_SEATBELT_SPEED_LIMIT.getCode(), "10.0");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_WITNESS_NOTE.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_NOTEEVENT.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_WITNESS_FULL.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_FULLEVENT.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_LOW_POWER_MODE_SECONDS.getCode(), "900");
        DEFAULT.put( TiwiPro.PROPERTY_SERVER_PORT.getCode(), "8190");
        DEFAULT.put( TiwiPro.PROPERTY_SET_MSGS_PER_NOTIFICATION.getCode(), "4");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_START_SPEEDING.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_SMTOOLS_SECURITY_URL.getCode(), "http://www.iwiglobal.com");
        DEFAULT.put( TiwiPro.PROPERTY_SMTOOLS_SECURITY_CONTEXT.getCode(), "asip");
        DEFAULT.put( TiwiPro.PROPERTY_CAR_BATTERY_LOW_POWER.getCode(), "11000");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_START_SEATBELT.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_PARENT_MODE.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_ECALL_PHONE_NUMBER.getCode(), "8014175665");
        DEFAULT.put( TiwiPro.PROPERTY_DEBUG_GPS.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_SMTOOLS_DEACTIVATE_OVERRIDE.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_SPEAKER_GAIN.getCode(), "-1000");
        DEFAULT.put( TiwiPro.PROPERTY_SEVERE_SPEEDING_GRACE_PERIOD.getCode(), "15");
        DEFAULT.put( TiwiPro.PROPERTY_VARIABLE_SPEED_LIMITS.getCode(), "5 10 15 20 25 30 35 40 45 50 55 60 65 70 75");
        DEFAULT.put( TiwiPro.PROPERTY_QA_BUZZER.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_WITNESS_FULL_ECALL.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_ROLLOVER.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_ROLLOVER_ECALL.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_CRASH_ROLLOVER_RENOTIFICATION_DELAY.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_EVENT_IDLING.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_IDLE_TIMEOUT.getCode(), "60");
        DEFAULT.put( TiwiPro.PROPERTY_XLOGS_ENABLED.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_BUZZER_ROLLOVER.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_HARD_VERT_SPEED_THRESH.getCode(), "25");
        DEFAULT.put( TiwiPro.PROPERTY_PHONE_GAIN.getCode(), "-500");
        DEFAULT.put( TiwiPro.PROPERTY_GPRS_LOGIN.getCode(), "");
        DEFAULT.put( TiwiPro.PROPERTY_GPRS_PASSWORD.getCode(), "");
        DEFAULT.put( TiwiPro.PROPERTY_GPRS_PIN.getCode(), "");
        DEFAULT.put( TiwiPro.PROPERTY_FTP_UPLOAD_URL.getCode(), "tiwiftp.tiwi.com");
        DEFAULT.put( TiwiPro.PROPERTY_FTP_USER_ID.getCode(), "tiwibox");
        DEFAULT.put( TiwiPro.PROPERTY_FTP_PASSWORD.getCode(), "Phohthed8sheighoh");
        DEFAULT.put( TiwiPro.PROPERTY_SEND_SUB_THRESH_CRASH.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_FORCE_MSP_FOR_IGN.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_AUDIO_FOLDER.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_NO_DRIVER_ENABLED.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_CRASH_DATA.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_STAT_FREQ.getCode(), "7");
        DEFAULT.put( TiwiPro.PROPERTY_AVG_SPD_AGMT.getCode(), "20");
        DEFAULT.put( TiwiPro.PROPERTY_IND_SPD_AGMT.getCode(), "10");
        DEFAULT.put( TiwiPro.PROPERTY_AUTOLOGOUT_SECONDS.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_ST_ENV_PADDING.getCode(), "300");
        DEFAULT.put( TiwiPro.PROPERTY_ST_MAX_ANGLE.getCode(), "60");
        DEFAULT.put( TiwiPro.PROPERTY_ST_WINDOW.getCode(), "250");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_ENABLE.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_PORT.getCode(), "37433");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_URL.getCode(), "symphony-usa.eride.com");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_REQ_INT.getCode(), "10800");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_NUM_DLD.getCode(), "2");
        DEFAULT.put( TiwiPro.PROPERTY_AGPS_NUM_CONN.getCode(), "3");
        DEFAULT.put( TiwiPro.PROPERTY_NODRIVER_AUDIO_OVERRIDE.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_ROLLOVER_FILTER.getCode(), "60");
        DEFAULT.put( TiwiPro.PROPERTY_DTC_THRESHOLD.getCode(), "2");
        DEFAULT.put( TiwiPro.PROPERTY_GPS_DBG_INVL.getCode(), "5");
        DEFAULT.put( TiwiPro.PROPERTY_FILTER_MAX_SPEED.getCode(), "140");
        DEFAULT.put( TiwiPro.PROPERTY_RFID_ENABLE.getCode(), "1");
        DEFAULT.put( TiwiPro.PROPERTY_RFID_SPEED_THRESH.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_HARDACCEL_SLIDER.getCode(), "875 40 99");
        DEFAULT.put( TiwiPro.PROPERTY_HARDBRAKE_SLIDER.getCode(), "2250 100 99");
        DEFAULT.put( TiwiPro.PROPERTY_HARDTURN_SLIDER.getCode(), "999 52 99");
        DEFAULT.put( TiwiPro.PROPERTY_HARDBUMP_SLIDER.getCode(), "1000 50 99 200");
        DEFAULT.put( TiwiPro.PROPERTY_CRASH_DELTAX.getCode(), "314");
        DEFAULT.put( TiwiPro.PROPERTY_CRASH_DELTAY.getCode(), "311");
        DEFAULT.put( TiwiPro.PROPERTY_CRASH_DELTAZ.getCode(), "450");
        DEFAULT.put( TiwiPro.PROPERTY_CLEAR_SBSCHECK.getCode(), "48");
        DEFAULT.put( TiwiPro.PROPERTY_INPUT_1_FUNCTION.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_INPUT_2_FUNCTION.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_INPUT_3_FUNCTION.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_GEN_INPUT_EVENTS.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_WHITELIST_ENABLED.getCode(), "0");
        DEFAULT.put( TiwiPro.PROPERTY_SMTLS_STATE_DIAG.getCode(), "0");
        
        
        return DEFAULT;
        
    }
        
}


