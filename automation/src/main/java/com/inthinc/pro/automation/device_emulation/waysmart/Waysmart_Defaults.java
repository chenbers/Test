package com.inthinc.pro.automation.device_emulation.waysmart;

import java.util.HashMap;

import com.inthinc.pro.automation.device_emulation.waysmart.enums.Ways_SETTINGS;

public class Waysmart_Defaults {
	public static HashMap<Integer, String> get_defaults(){
	
		HashMap<Integer, String> DEFAULT = new HashMap<Integer, String>();
		
		DEFAULT.put(  Ways_SETTINGS.LOCATION_UPDATE_ALWAYS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPS_AVE_SPD_AGRMNT.getCode(), "20");
		DEFAULT.put(  Ways_SETTINGS.SERVER_CONTEXT.getCode(), "asip");
		DEFAULT.put(  Ways_SETTINGS.SLEEPER_BERTH_TIMEOUT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_IGNITION_OFF.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WIRELINE_PORT.getCode(), "/dev/ttyE2");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_ID.getCode(), "WW100343");
		DEFAULT.put(  Ways_SETTINGS.TIMESTAMP_OFFSET.getCode(), "99");
		DEFAULT.put(  Ways_SETTINGS.SATELLITE_PORT.getCode(), "/dev/ttyE4");
		DEFAULT.put(  Ways_SETTINGS.BRAKE_SET_WARN_TIME.getCode(), "5");
		DEFAULT.put(  Ways_SETTINGS.SPEED2_BUFFER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SPARE1_ON_STATE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_HAZMAT_MINUTES.getCode(), "1440");
		DEFAULT.put(  Ways_SETTINGS.HARDVERT_SPEED_FILTER.getCode(), "25");
		DEFAULT.put(  Ways_SETTINGS.WIRELINE_AUTO_ARM_TIME.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.NOTE_EVENT_SPEED_FILTER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_SPEEDING2.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_HEARTBEAT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WIFI_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.THIRTY_MIN_CHECK.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.KEY.getCode(), "IOtrus6Ow+fiGonbwp#OatEFp]L");
		DEFAULT.put(  Ways_SETTINGS.SMTOOLS_FORCE_ON.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_MONTHLY.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.NO_DRIVER.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.HOME_LONGITUDE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_MODEM_ON.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.DASH_AUTODETECT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.MCM_ID.getCode(), "MCM100343");
		DEFAULT.put(  Ways_SETTINGS.CRASH_DATA_NOTE_EVENT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WITNESSII_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EXTENDED_CRASH_DATA_SECONDS.getCode(), "180");
		DEFAULT.put(  Ways_SETTINGS.SEVERE_SPEED_SECONDS.getCode(), "8");
		DEFAULT.put(  Ways_SETTINGS.UNIT_ENABLED.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EVENT_STATS.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.SCND_LVL_ENABLE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_SPEEDING.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EVENT_CHECKUNIT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EVENT_SEATBELT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EVENT_RPM.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_MANOK.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.MAPS_FOLDER_LOCAL.getCode(), "/dash_usb/maps");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_ON.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.MAP_ACCURACY.getCode(), "30");
		DEFAULT.put(  Ways_SETTINGS.EVENT_WITNESS_NOTE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.PRE_INSTALL_FULLS_ENABLE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.XCAT_PORT.getCode(), "/dev/ttyE2");
		DEFAULT.put(  Ways_SETTINGS.VEHICLE_ID.getCode(), "12578");
		DEFAULT.put(  Ways_SETTINGS.SBS_EXMP_CHK_FREQ.getCode(), "10");
		DEFAULT.put(  Ways_SETTINGS.GPS_PORT.getCode(), "/dev/ttyS1");
		DEFAULT.put(  Ways_SETTINGS.WII_TRACE_UPLD_ATMPT_LIM.getCode(), "3");
		DEFAULT.put(  Ways_SETTINGS.CLEAR_DRIVER_TIMEOUT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SMARTCARD_PORT.getCode(), "/dev/ttyE5");
		DEFAULT.put(  Ways_SETTINGS.NO_HOURS_CHECK.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GEOFENCE_LONGITUDE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.OBD_TYPE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.NOTE_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_FULL_EVENT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.BRAKE_SET_REPORT_TIME.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.EVENT_ROLLOVER.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.PDA_PORT.getCode(), "/dev/ttyE3");
		DEFAULT.put(  Ways_SETTINGS.SPEED_GRACE_PERIOD.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.SPEED_LIMIT.getCode(), "75");
		DEFAULT.put(  Ways_SETTINGS.MAP_SERVER_PORT.getCode(), "8090");
		DEFAULT.put(  Ways_SETTINGS.SPEED_BUFFER.getCode(), "5");
		DEFAULT.put(  Ways_SETTINGS.EVENT_HOME_MILES.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SAT_IMEI.getCode(), "");
		DEFAULT.put(  Ways_SETTINGS.SHUTDOWN_QSI.getCode(), "300");
		DEFAULT.put(  Ways_SETTINGS.IGNORE_RAMPS.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.FORCE_SAT_SHUTDOWN.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.FULL_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.HARDWARE_VERSION.getCode(), "7");
		DEFAULT.put(  Ways_SETTINGS.AUTO_WIT_UPDT_ATTMPTS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SBS_EXMP_CHK_LIM.getCode(), "500");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_PITCH.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.INTRASTATE_BND_WARN.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.DSS_MODULE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.INPUT3_TYPE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WEATHER_SPEED.getCode(), "100");
		DEFAULT.put(  Ways_SETTINGS.DWNLD_ATMPT_LIM.getCode(), "3");
		DEFAULT.put(  Ways_SETTINGS.OFF_DUTY_TIMEOUT.getCode(), "0.05");
		DEFAULT.put(  Ways_SETTINGS.DRIVING_LIMIT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.TRAILER_DATA.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SPEEDLIMCHNG_GRACE_PERIOD.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_AUTODOWN.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.OBD_GPS_CHECK.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.GEOFENCE_LATITUDE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_THEFT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.INPUT4_ON_STATE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPS_FILT_ENABLED.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.SCND_LVL_NTS_ENBL.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_NOTE_EVENT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.LOW_POWER_EVENT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.TRACK_NON_DOT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SAFETY_MILES.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.TEMPERATURE_WITH_EVENTS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPRS_PORT.getCode(), "/dev/ttyE1");
		DEFAULT.put(  Ways_SETTINGS.DAILY_OFFDUTY_WARN.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.FULL_EVENT_CONFIDENCE_LEVEL.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CAL_CONST_V.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPS_ACCURACY.getCode(), "2.5");
		DEFAULT.put(  Ways_SETTINGS.SATELLITE_MODULE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.PDA_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.POTENTIAL_CRASH_DISTANCE.getCode(), "60");
		DEFAULT.put(  Ways_SETTINGS.WIRELINE_KILL_MOTOR_PASSWD.getCode(), "");
		DEFAULT.put(  Ways_SETTINGS.APN.getCode(), "_auto_");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_PORT.getCode(), "/dev/ttyE6");
		DEFAULT.put(  Ways_SETTINGS.EVENT_HOS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WSZONES_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_SEATBELT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CRASH_SAT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.NO_DRIVER_LIMIT.getCode(), "10");
		DEFAULT.put(  Ways_SETTINGS.LOC_SEVERITY.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SAFETY_TIME.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SAT_SWITCH_LIMIT.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.GPRS_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.LOW_BATTERY.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CLUSTER_EVENTS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.CAL_CONST_U.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.HOME_LATITUDE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_BUF_TIME.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.CLEAR_DRIVER_ON_WELL_SITE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MAN_DOWN_PORT.getCode(), "/dev/ttyE3");
		DEFAULT.put(  Ways_SETTINGS.EVENT_IGNITION_ON.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.UNKNOWN_LIMIT.getCode(), "73");
		DEFAULT.put(  Ways_SETTINGS.FULL_EVENT_LATENCY.getCode(), "10");
		DEFAULT.put(  Ways_SETTINGS.EVENT_HEADLIGHT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.HRD_TRN_SCND_DV_LVL.getCode(), "75");
		DEFAULT.put(  Ways_SETTINGS.ON_DUTY_IDLE_TIMEOUT.getCode(), "0.05");
		DEFAULT.put(  Ways_SETTINGS.AUTO_MD_WARNING.getCode(), "30");
		DEFAULT.put(  Ways_SETTINGS.EVENT_GPS_MINUTES.getCode(), "1440");
		DEFAULT.put(  Ways_SETTINGS.SEND_FULL_EVENT_CONFIDENCE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.MENTOR_SPEED_BUFFER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SEVERE_SPEED.getCode(), "20");
		DEFAULT.put(  Ways_SETTINGS.EVENT_BOUNDARY.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.RPM_LIMIT.getCode(), "5000");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_SPEED.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CLEAR_DRIVER_ON_SLEEPER_BERTH.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.EVENT_WITNESS_FULL.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.EVENT_NEW_DRIVER.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_SAT_SWITCH.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WIRELINE_MODULE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BRAKE_NOTSET_WARN_TIME.getCode(), "2");
		DEFAULT.put(  Ways_SETTINGS.SMARTCARD_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.TRIAX_EVENT_LATENCY.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.COMPANY_ID.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.LOW_IMPACT_OBD.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SBS_EXMP_DNLD_ATMPT_LIM.getCode(), "50");
		DEFAULT.put(  Ways_SETTINGS.IGNITION_ON_STATE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.SEATBELT_LIMIT.getCode(), "5");
		DEFAULT.put(  Ways_SETTINGS.EVENT_GEOFENCE_MILES.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.PANIC_ENABLE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.SUPPRESS_POP_EVENT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.MAP_SERVER_DOWNLOAD.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.MAX_TIME_NON_ZERO_SPD.getCode(), "170");
		DEFAULT.put(  Ways_SETTINGS.SPEED_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CRASH_DATAEX_PANIC.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_MASTER.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.TRIAX_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.RECEIVE_ONLY.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WITNESSIII_AUTO_RECAL.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.NO_TRAILER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.CRASH_DATA_SECONDS.getCode(), "30");
		DEFAULT.put(  Ways_SETTINGS.MAPS_FOLDER.getCode(), "http://www.iwiwitness.com/download/media/iwi/maps");
		DEFAULT.put(  Ways_SETTINGS.HRD_VRT_SCND_PP_LVL.getCode(), "90");
		DEFAULT.put(  Ways_SETTINGS.WIRELINE_DOOR_ALARM_PASSWD.getCode(), "");
		DEFAULT.put(  Ways_SETTINGS.INPUT3_ON_STATE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.HRD_ACCL_SCND_DV_LVL.getCode(), "60");
		DEFAULT.put(  Ways_SETTINGS.TRIAX_EVENT_SPEED_FILTER.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_BATTERY.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.IDLETIME_WITH_EVENTS.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.INPUT4_TYPE.getCode(), "2");
		DEFAULT.put(  Ways_SETTINGS.SERVER_IP.getCode(), "qa.tiwipro.com:8988");
		DEFAULT.put(  Ways_SETTINGS.SBS_UPDATE_RESULT_EVENT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.TRIAX_MODULE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.KILL_COMMUNICATION.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPS_IND_SPD_AGRMNT.getCode(), "10");
		DEFAULT.put(  Ways_SETTINGS.WIFI_TIMEOUT.getCode(), "300");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_RPM.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.WIFI_TYPE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_NO_DRIVER.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.LOW_POWER_MODE_HOURS.getCode(), "0.01667");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_ROLL.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.USE_OPEN_DNS.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.OBD_PORT.getCode(), "/dev/ttyS2");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_OFF.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_LOW_BATT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MAN_DOWN_MODULE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.MAP_SERVER_URL.getCode(), "qa.tiwipro.com");
		DEFAULT.put(  Ways_SETTINGS.ON_DUTY_TIMEOUT.getCode(), "0.03333");
		DEFAULT.put(  Ways_SETTINGS.IDLE_STATS.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.BUZZER_HEADLIGHT.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CLEAR_DRIVER_OFFDUTY.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.USB_ERROR_REBOOT.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.OFFROAD_AUTO.getCode(), "50");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_AACK.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.CAL_CONST_W.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.GPRS_TIMEOUT.getCode(), "7200");
		DEFAULT.put(  Ways_SETTINGS.BRAKE_NOTSET_REPORT_TIME.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.SEATBELT_IN_STATE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SEATBELT_GRACE.getCode(), "15");
		DEFAULT.put(  Ways_SETTINGS.HOS_HISTORY_SPECIFIC.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.SEVERE_HARDVERT_LEVEL.getCode(), "300");
		DEFAULT.put(  Ways_SETTINGS.NUM_STORED_WITNESS_EVENTS.getCode(), "50");
		DEFAULT.put(  Ways_SETTINGS.NOLOCK_BUFFER.getCode(), "5");
		DEFAULT.put(  Ways_SETTINGS.CRASH_DATA.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.SPARE1_TYPE.getCode(), "2");
		DEFAULT.put(  Ways_SETTINGS.IWI_HH.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.TRIAX_PORT.getCode(), "/dev/ttyE6");
		DEFAULT.put(  Ways_SETTINGS.SSID.getCode(), "ZEUS2");
		DEFAULT.put(  Ways_SETTINGS.OFFROAD_REMIND.getCode(), "1800");
		DEFAULT.put(  Ways_SETTINGS.XCAT_MODULE.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.WITNESS_YAW.getCode(), "270");
		DEFAULT.put(  Ways_SETTINGS.AUTO_MANDOWN.getCode(), "0");
		DEFAULT.put(  Ways_SETTINGS.VEHICLE_VIN.getCode(), "");
		DEFAULT.put(  Ways_SETTINGS.SEND_NO_MAPS_DRIVE.getCode(), "1");
		DEFAULT.put(  Ways_SETTINGS.HRD_BRK_SCND_DV_LVL.getCode(), "60");
		DEFAULT.put(  Ways_SETTINGS.REMOTE_MD_MANUALDOWN.getCode(), "1");

		
		return DEFAULT;
	}
}
