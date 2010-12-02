package com.inthinc.pro.device.waysmart;

import java.util.HashMap;
import com.inthinc.pro.device.waysmart.Waysmart;

public class Waysmart_Defaults {
	public static HashMap<Integer, String> get_defaults(){
	
		HashMap<Integer, String> DEFAULT = new HashMap<Integer, String>();
		
		DEFAULT.put(  Waysmart.LOCATION_UPDATE_ALWAYS.getCode(), "0");
		DEFAULT.put(  Waysmart.GPS_AVE_SPD_AGRMNT.getCode(), "20");
		DEFAULT.put(  Waysmart.SERVER_CONTEXT.getCode(), "asip");
		DEFAULT.put(  Waysmart.SLEEPER_BERTH_TIMEOUT.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_IGNITION_OFF.getCode(), "1");
		DEFAULT.put(  Waysmart.WIRELINE_PORT.getCode(), "/dev/ttyE2");
		DEFAULT.put(  Waysmart.WITNESS_ID.getCode(), "WW100343");
		DEFAULT.put(  Waysmart.TIMESTAMP_OFFSET.getCode(), "99");
		DEFAULT.put(  Waysmart.SATELLITE_PORT.getCode(), "/dev/ttyE4");
		DEFAULT.put(  Waysmart.BRAKE_SET_WARN_TIME.getCode(), "5");
		DEFAULT.put(  Waysmart.SPEED2_BUFFER.getCode(), "0");
		DEFAULT.put(  Waysmart.SPARE1_ON_STATE.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_HAZMAT_MINUTES.getCode(), "1440");
		DEFAULT.put(  Waysmart.HARDVERT_SPEED_FILTER.getCode(), "25");
		DEFAULT.put(  Waysmart.WIRELINE_AUTO_ARM_TIME.getCode(), "15");
		DEFAULT.put(  Waysmart.NOTE_EVENT_SPEED_FILTER.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_SPEEDING2.getCode(), "0");
		DEFAULT.put(  Waysmart.WITNESS_HEARTBEAT.getCode(), "1");
		DEFAULT.put(  Waysmart.WIFI_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.THIRTY_MIN_CHECK.getCode(), "0");
		DEFAULT.put(  Waysmart.KEY.getCode(), "IOtrus6Ow+fiGonbwp#OatEFp]L");
		DEFAULT.put(  Waysmart.SMTOOLS_FORCE_ON.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_MONTHLY.getCode(), "1");
		DEFAULT.put(  Waysmart.NO_DRIVER.getCode(), "1");
		DEFAULT.put(  Waysmart.HOME_LONGITUDE.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_MODEM_ON.getCode(), "0");
		DEFAULT.put(  Waysmart.DASH_AUTODETECT.getCode(), "1");
		DEFAULT.put(  Waysmart.MCM_ID.getCode(), "MCM100343");
		DEFAULT.put(  Waysmart.CRASH_DATA_NOTE_EVENT.getCode(), "0");
		DEFAULT.put(  Waysmart.WITNESSII_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.EXTENDED_CRASH_DATA_SECONDS.getCode(), "180");
		DEFAULT.put(  Waysmart.SEVERE_SPEED_SECONDS.getCode(), "8");
		DEFAULT.put(  Waysmart.UNIT_ENABLED.getCode(), "1");
		DEFAULT.put(  Waysmart.EVENT_STATS.getCode(), "1");
		DEFAULT.put(  Waysmart.SCND_LVL_ENABLE.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_SPEEDING.getCode(), "1");
		DEFAULT.put(  Waysmart.WITNESS_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.EVENT_CHECKUNIT.getCode(), "1");
		DEFAULT.put(  Waysmart.EVENT_SEATBELT.getCode(), "1");
		DEFAULT.put(  Waysmart.EVENT_RPM.getCode(), "1");
		DEFAULT.put(  Waysmart.REMOTE_MD_MANOK.getCode(), "1");
		DEFAULT.put(  Waysmart.MAPS_FOLDER_LOCAL.getCode(), "/dash_usb/maps");
		DEFAULT.put(  Waysmart.REMOTE_MD_ON.getCode(), "1");
		DEFAULT.put(  Waysmart.MAP_ACCURACY.getCode(), "30");
		DEFAULT.put(  Waysmart.EVENT_WITNESS_NOTE.getCode(), "1");
		DEFAULT.put(  Waysmart.PRE_INSTALL_FULLS_ENABLE.getCode(), "1");
		DEFAULT.put(  Waysmart.XCAT_PORT.getCode(), "/dev/ttyE2");
		DEFAULT.put(  Waysmart.VEHICLE_ID.getCode(), "12578");
		DEFAULT.put(  Waysmart.SBS_EXMP_CHK_FREQ.getCode(), "10");
		DEFAULT.put(  Waysmart.GPS_PORT.getCode(), "/dev/ttyS1");
		DEFAULT.put(  Waysmart.WII_TRACE_UPLD_ATMPT_LIM.getCode(), "3");
		DEFAULT.put(  Waysmart.CLEAR_DRIVER_TIMEOUT.getCode(), "0");
		DEFAULT.put(  Waysmart.SMARTCARD_PORT.getCode(), "/dev/ttyE5");
		DEFAULT.put(  Waysmart.NO_HOURS_CHECK.getCode(), "0");
		DEFAULT.put(  Waysmart.GEOFENCE_LONGITUDE.getCode(), "0");
		DEFAULT.put(  Waysmart.OBD_TYPE.getCode(), "1");
		DEFAULT.put(  Waysmart.NOTE_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_FULL_EVENT.getCode(), "1");
		DEFAULT.put(  Waysmart.BRAKE_SET_REPORT_TIME.getCode(), "15");
		DEFAULT.put(  Waysmart.EVENT_ROLLOVER.getCode(), "1");
		DEFAULT.put(  Waysmart.PDA_PORT.getCode(), "/dev/ttyE3");
		DEFAULT.put(  Waysmart.SPEED_GRACE_PERIOD.getCode(), "15");
		DEFAULT.put(  Waysmart.SPEED_LIMIT.getCode(), "75");
		DEFAULT.put(  Waysmart.MAP_SERVER_PORT.getCode(), "8090");
		DEFAULT.put(  Waysmart.SPEED_BUFFER.getCode(), "5");
		DEFAULT.put(  Waysmart.EVENT_HOME_MILES.getCode(), "0");
		DEFAULT.put(  Waysmart.SAT_IMEI.getCode(), "");
		DEFAULT.put(  Waysmart.SHUTDOWN_QSI.getCode(), "300");
		DEFAULT.put(  Waysmart.IGNORE_RAMPS.getCode(), "1");
		DEFAULT.put(  Waysmart.FORCE_SAT_SHUTDOWN.getCode(), "0");
		DEFAULT.put(  Waysmart.FULL_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Waysmart.HARDWARE_VERSION.getCode(), "7");
		DEFAULT.put(  Waysmart.AUTO_WIT_UPDT_ATTMPTS.getCode(), "0");
		DEFAULT.put(  Waysmart.SBS_EXMP_CHK_LIM.getCode(), "500");
		DEFAULT.put(  Waysmart.WITNESS_PITCH.getCode(), "0");
		DEFAULT.put(  Waysmart.INTRASTATE_BND_WARN.getCode(), "0");
		DEFAULT.put(  Waysmart.DSS_MODULE.getCode(), "0");
		DEFAULT.put(  Waysmart.INPUT3_TYPE.getCode(), "1");
		DEFAULT.put(  Waysmart.WEATHER_SPEED.getCode(), "100");
		DEFAULT.put(  Waysmart.DWNLD_ATMPT_LIM.getCode(), "3");
		DEFAULT.put(  Waysmart.OFF_DUTY_TIMEOUT.getCode(), "0.05");
		DEFAULT.put(  Waysmart.DRIVING_LIMIT.getCode(), "0");
		DEFAULT.put(  Waysmart.TRAILER_DATA.getCode(), "0");
		DEFAULT.put(  Waysmart.SPEEDLIMCHNG_GRACE_PERIOD.getCode(), "0");
		DEFAULT.put(  Waysmart.REMOTE_MD_AUTODOWN.getCode(), "1");
		DEFAULT.put(  Waysmart.OBD_GPS_CHECK.getCode(), "1");
		DEFAULT.put(  Waysmart.GEOFENCE_LATITUDE.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_THEFT.getCode(), "0");
		DEFAULT.put(  Waysmart.INPUT4_ON_STATE.getCode(), "0");
		DEFAULT.put(  Waysmart.GPS_FILT_ENABLED.getCode(), "1");
		DEFAULT.put(  Waysmart.SCND_LVL_NTS_ENBL.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_NOTE_EVENT.getCode(), "1");
		DEFAULT.put(  Waysmart.LOW_POWER_EVENT.getCode(), "1");
		DEFAULT.put(  Waysmart.TRACK_NON_DOT.getCode(), "0");
		DEFAULT.put(  Waysmart.SAFETY_MILES.getCode(), "0");
		DEFAULT.put(  Waysmart.TEMPERATURE_WITH_EVENTS.getCode(), "0");
		DEFAULT.put(  Waysmart.GPRS_PORT.getCode(), "/dev/ttyE1");
		DEFAULT.put(  Waysmart.DAILY_OFFDUTY_WARN.getCode(), "0");
		DEFAULT.put(  Waysmart.FULL_EVENT_CONFIDENCE_LEVEL.getCode(), "1");
		DEFAULT.put(  Waysmart.CAL_CONST_V.getCode(), "0");
		DEFAULT.put(  Waysmart.GPS_ACCURACY.getCode(), "2.5");
		DEFAULT.put(  Waysmart.SATELLITE_MODULE.getCode(), "0");
		DEFAULT.put(  Waysmart.PDA_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.POTENTIAL_CRASH_DISTANCE.getCode(), "60");
		DEFAULT.put(  Waysmart.WIRELINE_KILL_MOTOR_PASSWD.getCode(), "");
		DEFAULT.put(  Waysmart.APN.getCode(), "_auto_");
		DEFAULT.put(  Waysmart.WITNESS_PORT.getCode(), "/dev/ttyE6");
		DEFAULT.put(  Waysmart.EVENT_HOS.getCode(), "0");
		DEFAULT.put(  Waysmart.WSZONES_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.BUZZER_SEATBELT.getCode(), "1");
		DEFAULT.put(  Waysmart.CRASH_SAT.getCode(), "1");
		DEFAULT.put(  Waysmart.NO_DRIVER_LIMIT.getCode(), "10");
		DEFAULT.put(  Waysmart.LOC_SEVERITY.getCode(), "0");
		DEFAULT.put(  Waysmart.SAFETY_TIME.getCode(), "0");
		DEFAULT.put(  Waysmart.SAT_SWITCH_LIMIT.getCode(), "15");
		DEFAULT.put(  Waysmart.GPRS_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.LOW_BATTERY.getCode(), "1");
		DEFAULT.put(  Waysmart.CLUSTER_EVENTS.getCode(), "0");
		DEFAULT.put(  Waysmart.CAL_CONST_U.getCode(), "0");
		DEFAULT.put(  Waysmart.HOME_LATITUDE.getCode(), "0");
		DEFAULT.put(  Waysmart.WITNESS_BUF_TIME.getCode(), "0");
		DEFAULT.put(  Waysmart.CLEAR_DRIVER_ON_WELL_SITE.getCode(), "0");
		DEFAULT.put(  Waysmart.REMOTE_MAN_DOWN_PORT.getCode(), "/dev/ttyE3");
		DEFAULT.put(  Waysmart.EVENT_IGNITION_ON.getCode(), "1");
		DEFAULT.put(  Waysmart.UNKNOWN_LIMIT.getCode(), "73");
		DEFAULT.put(  Waysmart.FULL_EVENT_LATENCY.getCode(), "10");
		DEFAULT.put(  Waysmart.EVENT_HEADLIGHT.getCode(), "0");
		DEFAULT.put(  Waysmart.HRD_TRN_SCND_DV_LVL.getCode(), "75");
		DEFAULT.put(  Waysmart.ON_DUTY_IDLE_TIMEOUT.getCode(), "0.05");
		DEFAULT.put(  Waysmart.AUTO_MD_WARNING.getCode(), "30");
		DEFAULT.put(  Waysmart.EVENT_GPS_MINUTES.getCode(), "1440");
		DEFAULT.put(  Waysmart.SEND_FULL_EVENT_CONFIDENCE.getCode(), "1");
		DEFAULT.put(  Waysmart.MENTOR_SPEED_BUFFER.getCode(), "0");
		DEFAULT.put(  Waysmart.SEVERE_SPEED.getCode(), "20");
		DEFAULT.put(  Waysmart.EVENT_BOUNDARY.getCode(), "1");
		DEFAULT.put(  Waysmart.RPM_LIMIT.getCode(), "5000");
		DEFAULT.put(  Waysmart.BUZZER_SPEED.getCode(), "1");
		DEFAULT.put(  Waysmart.CLEAR_DRIVER_ON_SLEEPER_BERTH.getCode(), "0");
		DEFAULT.put(  Waysmart.EVENT_WITNESS_FULL.getCode(), "1");
		DEFAULT.put(  Waysmart.EVENT_NEW_DRIVER.getCode(), "1");
		DEFAULT.put(  Waysmart.BUZZER_SAT_SWITCH.getCode(), "1");
		DEFAULT.put(  Waysmart.WIRELINE_MODULE.getCode(), "0");
		DEFAULT.put(  Waysmart.BRAKE_NOTSET_WARN_TIME.getCode(), "2");
		DEFAULT.put(  Waysmart.SMARTCARD_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.TRIAX_EVENT_LATENCY.getCode(), "0");
		DEFAULT.put(  Waysmart.COMPANY_ID.getCode(), "1");
		DEFAULT.put(  Waysmart.LOW_IMPACT_OBD.getCode(), "0");
		DEFAULT.put(  Waysmart.SBS_EXMP_DNLD_ATMPT_LIM.getCode(), "50");
		DEFAULT.put(  Waysmart.IGNITION_ON_STATE.getCode(), "1");
		DEFAULT.put(  Waysmart.SEATBELT_LIMIT.getCode(), "5");
		DEFAULT.put(  Waysmart.EVENT_GEOFENCE_MILES.getCode(), "0");
		DEFAULT.put(  Waysmart.PANIC_ENABLE.getCode(), "1");
		DEFAULT.put(  Waysmart.SUPPRESS_POP_EVENT.getCode(), "0");
		DEFAULT.put(  Waysmart.MAP_SERVER_DOWNLOAD.getCode(), "1");
		DEFAULT.put(  Waysmart.MAX_TIME_NON_ZERO_SPD.getCode(), "170");
		DEFAULT.put(  Waysmart.SPEED_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.CRASH_DATAEX_PANIC.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_MASTER.getCode(), "1");
		DEFAULT.put(  Waysmart.TRIAX_EVENT_FILTER.getCode(), "0");
		DEFAULT.put(  Waysmart.RECEIVE_ONLY.getCode(), "0");
		DEFAULT.put(  Waysmart.WITNESSIII_AUTO_RECAL.getCode(), "1");
		DEFAULT.put(  Waysmart.NO_TRAILER.getCode(), "0");
		DEFAULT.put(  Waysmart.CRASH_DATA_SECONDS.getCode(), "30");
		DEFAULT.put(  Waysmart.MAPS_FOLDER.getCode(), "http://www.iwiwitness.com/download/media/iwi/maps");
		DEFAULT.put(  Waysmart.HRD_VRT_SCND_PP_LVL.getCode(), "90");
		DEFAULT.put(  Waysmart.WIRELINE_DOOR_ALARM_PASSWD.getCode(), "");
		DEFAULT.put(  Waysmart.INPUT3_ON_STATE.getCode(), "1");
		DEFAULT.put(  Waysmart.HRD_ACCL_SCND_DV_LVL.getCode(), "60");
		DEFAULT.put(  Waysmart.TRIAX_EVENT_SPEED_FILTER.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_BATTERY.getCode(), "1");
		DEFAULT.put(  Waysmart.IDLETIME_WITH_EVENTS.getCode(), "0");
		DEFAULT.put(  Waysmart.INPUT4_TYPE.getCode(), "2");
		DEFAULT.put(  Waysmart.SERVER_IP.getCode(), "qa.tiwipro.com:8988");
		DEFAULT.put(  Waysmart.SBS_UPDATE_RESULT_EVENT.getCode(), "1");
		DEFAULT.put(  Waysmart.TRIAX_MODULE.getCode(), "1");
		DEFAULT.put(  Waysmart.KILL_COMMUNICATION.getCode(), "0");
		DEFAULT.put(  Waysmart.GPS_IND_SPD_AGRMNT.getCode(), "10");
		DEFAULT.put(  Waysmart.WIFI_TIMEOUT.getCode(), "300");
		DEFAULT.put(  Waysmart.BUZZER_RPM.getCode(), "1");
		DEFAULT.put(  Waysmart.WIFI_TYPE.getCode(), "0");
		DEFAULT.put(  Waysmart.BUZZER_NO_DRIVER.getCode(), "1");
		DEFAULT.put(  Waysmart.LOW_POWER_MODE_HOURS.getCode(), "0.01667");
		DEFAULT.put(  Waysmart.WITNESS_ROLL.getCode(), "0");
		DEFAULT.put(  Waysmart.USE_OPEN_DNS.getCode(), "1");
		DEFAULT.put(  Waysmart.OBD_PORT.getCode(), "/dev/ttyS2");
		DEFAULT.put(  Waysmart.REMOTE_MD_OFF.getCode(), "1");
		DEFAULT.put(  Waysmart.REMOTE_MD_LOW_BATT.getCode(), "1");
		DEFAULT.put(  Waysmart.REMOTE_MAN_DOWN_MODULE.getCode(), "0");
		DEFAULT.put(  Waysmart.MAP_SERVER_URL.getCode(), "qa.tiwipro.com");
		DEFAULT.put(  Waysmart.ON_DUTY_TIMEOUT.getCode(), "0.03333");
		DEFAULT.put(  Waysmart.IDLE_STATS.getCode(), "1");
		DEFAULT.put(  Waysmart.BUZZER_HEADLIGHT.getCode(), "1");
		DEFAULT.put(  Waysmart.CLEAR_DRIVER_OFFDUTY.getCode(), "0");
		DEFAULT.put(  Waysmart.USB_ERROR_REBOOT.getCode(), "0");
		DEFAULT.put(  Waysmart.OFFROAD_AUTO.getCode(), "50");
		DEFAULT.put(  Waysmart.REMOTE_MD_AACK.getCode(), "1");
		DEFAULT.put(  Waysmart.CAL_CONST_W.getCode(), "0");
		DEFAULT.put(  Waysmart.GPRS_TIMEOUT.getCode(), "7200");
		DEFAULT.put(  Waysmart.BRAKE_NOTSET_REPORT_TIME.getCode(), "15");
		DEFAULT.put(  Waysmart.SEATBELT_IN_STATE.getCode(), "0");
		DEFAULT.put(  Waysmart.SEATBELT_GRACE.getCode(), "15");
		DEFAULT.put(  Waysmart.HOS_HISTORY_SPECIFIC.getCode(), "1");
		DEFAULT.put(  Waysmart.SEVERE_HARDVERT_LEVEL.getCode(), "300");
		DEFAULT.put(  Waysmart.NUM_STORED_WITNESS_EVENTS.getCode(), "50");
		DEFAULT.put(  Waysmart.NOLOCK_BUFFER.getCode(), "5");
		DEFAULT.put(  Waysmart.CRASH_DATA.getCode(), "0");
		DEFAULT.put(  Waysmart.SPARE1_TYPE.getCode(), "2");
		DEFAULT.put(  Waysmart.IWI_HH.getCode(), "1");
		DEFAULT.put(  Waysmart.TRIAX_PORT.getCode(), "/dev/ttyE6");
		DEFAULT.put(  Waysmart.SSID.getCode(), "ZEUS2");
		DEFAULT.put(  Waysmart.OFFROAD_REMIND.getCode(), "1800");
		DEFAULT.put(  Waysmart.XCAT_MODULE.getCode(), "0");
		DEFAULT.put(  Waysmart.WITNESS_YAW.getCode(), "270");
		DEFAULT.put(  Waysmart.AUTO_MANDOWN.getCode(), "0");
		DEFAULT.put(  Waysmart.VEHICLE_VIN.getCode(), "");
		DEFAULT.put(  Waysmart.SEND_NO_MAPS_DRIVE.getCode(), "1");
		DEFAULT.put(  Waysmart.HRD_BRK_SCND_DV_LVL.getCode(), "60");
		DEFAULT.put(  Waysmart.REMOTE_MD_MANUALDOWN.getCode(), "1");

		
		return DEFAULT;
	}
}
