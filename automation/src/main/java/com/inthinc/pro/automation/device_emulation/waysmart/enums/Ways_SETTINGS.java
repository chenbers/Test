package com.inthinc.QA.waysmart.enums;

import java.util.EnumSet;
import java.util.HashMap;

public enum Ways_SETTINGS {
	
	
	
	/* Config Settings */

	KILL_COMMUNICATION(1000),
	MCM_ID(1001),
	VEHICLE_ID(1002),
	COMPANY_ID(1003),
	WITNESS_ID(1004),
	SEATBELT_IN_STATE(1005),
	IGNITION_ON_STATE(1006),
	INPUT3_ON_STATE(1007),
	INPUT4_ON_STATE(1008),
	OBD_TYPE(1009),
	SMTOOLS_FORCE_ON(1010),
	LOW_IMPACT_OBD(1012),
	LOW_POWER_MODE_HOURS(1013),
	SHUTDOWN_QSI(1014),
	ON_DUTY_IDLE_TIMEOUT(1015),
	ON_DUTY_TIMEOUT(1016),
	OFF_DUTY_TIMEOUT(1017),
	SLEEPER_BERTH_TIMEOUT(1018),
	CLEAR_DRIVER_TIMEOUT(1019),
	CLEAR_DRIVER_OFFDUTY(1020),
	THIRTY_MIN_CHECK(1021),
	TIMESTAMP_OFFSET(1022),
	NO_HOURS_CHECK(1023),
	DAILY_OFFDUTY_WARN(1024),
	HOS_HISTORY_SPECIFIC(1025),
	OFFROAD_AUTO(1026),
	OFFROAD_REMIND(1027),
	SPEED_LIMIT(1028),
	SEVERE_SPEED(1029),
	SPEED_BUFFER(1030),
	UNKNOWN_LIMIT(1031),
	IGNORE_RAMPS(1032),
	WEATHER_SPEED(1033),
	FORCE_SAT_SHUTDOWN(1034),
	RPM_LIMIT(1035),
	SEATBELT_GRACE(1036),
	SEATBELT_LIMIT(1037),
	NO_DRIVER_LIMIT(1038),
	SAT_SWITCH_LIMIT(1039),
	DRIVING_LIMIT(1040),
	SAFETY_TIME(1041),
	SAFETY_MILES(1042),
	MENTOR_SPEED_BUFFER(1043),
	SPEED2_BUFFER(1044),
	SPEEDLIMCHNG_GRACE_PERIOD(1045),
	SPEED_GRACE_PERIOD(1046),
	NOLOCK_BUFFER(1047),
	HOME_LATITUDE(1048),
	HOME_LONGITUDE(1049),
	GEOFENCE_LATITUDE(1050),
	GEOFENCE_LONGITUDE(1051),
	RECEIVE_ONLY(1052),
	EVENT_SEATBELT(1053),
	EVENT_HEADLIGHT(1054),
	EVENT_SPEEDING(1055),
	EVENT_SPEEDING2(1056),
	EVENT_RPM(1057),
	EVENT_WITNESS_NOTE(1058),
	EVENT_WITNESS_FULL(1059),
	SPEED_MODULE(1060),
	EVENT_THEFT(1061),
	EVENT_NEW_DRIVER(1062),
	EVENT_IGNITION_ON(1063),
	EVENT_IGNITION_OFF(1064),
	EVENT_GPS_MINUTES(1065),
	EVENT_HAZMAT_MINUTES(1066),
	EVENT_HOME_MILES(1067),
	EVENT_GEOFENCE_MILES(1068),
	EVENT_MONTHLY(1069),
	EVENT_BOUNDARY(1070),
	EVENT_HOS(1071),
	EVENT_CHECKUNIT(1072),
	IDLETIME_WITH_EVENTS(1073),
	LOCATION_UPDATE_ALWAYS(1074),
	LOC_SEVERITY(1075),
	CRASH_DATA(1076),
	CRASH_DATAEX_PANIC(1077),
	NO_DRIVER(1078),
	NO_TRAILER(1079),
	TRAILER_DATA(1080),
	LOW_POWER_EVENT(1081),
	WITNESS_HEARTBEAT(1082),
	EVENT_STATS(1083),
	IDLE_STATS(1084),
	INPUT3_TYPE(1085),
	INPUT4_TYPE(1086),
	BUZZER_MASTER(1087),
	BUZZER_SPEED(1088),
	BUZZER_SEATBELT(1089),
	BUZZER_HEADLIGHT(1090),
	BUZZER_RPM(1091),
	BUZZER_NO_DRIVER(1092),
	BUZZER_SAT_SWITCH(1093),
	BUZZER_MODEM_ON(1094),
	BUZZER_NOTE_EVENT(1095),
	BUZZER_FULL_EVENT(1096),
	CLUSTER_EVENTS(1097),
	NOTE_EVENT_FILTER(1098),
	TRIAX_EVENT_FILTER(1099),
	FULL_EVENT_FILTER(1100),
	NOTE_EVENT_SPEED_FILTER(1101),
	TRIAX_EVENT_SPEED_FILTER(1102),
	TRIAX_EVENT_LATENCY(1103),
	SUPPRESS_POP_EVENT(1104),
	WITNESS_BUF_TIME(1105),
	WITNESSII_MODULE(1106),
	CAL_CONST_U(1107),
	CAL_CONST_V(1108),
	CAL_CONST_W(1109),
	WITNESS_ROLL(1110),
	WITNESS_PITCH(1111),
	WITNESS_YAW(1112),
	MAPS_FOLDER(1113),
	MAPS_FOLDER_LOCAL(1114),
	SATELLITE_MODULE(1116),
	GPRS_MODULE(1117),
	XCAT_MODULE(1118),
	WIFI_MODULE(1119),
	SATELLITE_PORT(1120),
	GPRS_PORT(1121),
	XCAT_PORT(1122),
	APN(1123),
	SERVER_IP(1124),
	SERVER_CONTEXT(1125),
	WIFI_TIMEOUT(1126),
	GPRS_TIMEOUT(1127),
	SAT_IMEI(1128),
	HARDWARE_VERSION(1129),
	GPS_PORT(1130),
	WITNESS_MODULE(1131),
	TRIAX_MODULE(1132),
	PDA_MODULE(1133),
	WSZONES_MODULE(1134),
	WITNESS_PORT(1135),
	TRIAX_PORT(1136),
	OBD_PORT(1137),
	PDA_PORT(1138),
	SSID(1139),
	KEY(1140),
	OBD_GPS_CHECK(1141),
	INTRASTATE_BND_WARN(1142),
	FULL_EVENT_LATENCY(1143),
	POTENTIAL_CRASH_DISTANCE(1144),
	USB_ERROR_REBOOT(1145),
	CRASH_SAT(1146),
	USE_OPEN_DNS(1147),
	WIRELINE_PORT(1148),
	WIRELINE_DOOR_ALARM_PASSWD(1149),
	WIRELINE_KILL_MOTOR_PASSWD(1150),
	WIRELINE_AUTO_ARM_TIME(1151),
	CLEAR_DRIVER_ON_SLEEPER_BERTH(1152),
	CLEAR_DRIVER_ON_WELL_SITE(1153),
	SEND_NO_MAPS_DRIVE(1154),
	DASH_AUTODETECT(1155),
	MAP_SERVER_URL(1156),
	MAP_SERVER_PORT(1157),
	MAP_SERVER_DOWNLOAD(1159),
	CRASH_DATA_SECONDS(1160),
	EXTENDED_CRASH_DATA_SECONDS(1161),
	EVENT_ROLLOVER(1162),
	SEVERE_SPEED_SECONDS(1163),
	HARDVERT_SPEED_FILTER(1164),
	SEVERE_HARDVERT_LEVEL(1165),
	SPARE1_ON_STATE(1166),
	SPARE1_TYPE(1167),
	BRAKE_SET_WARN_TIME(1168),
	BRAKE_NOTSET_WARN_TIME(1169),
	BRAKE_SET_REPORT_TIME(1170),
	BRAKE_NOTSET_REPORT_TIME(1171),
	WIRELINE_MODULE(1172),
	FULL_EVENT_CONFIDENCE_LEVEL(1173),
	SEND_FULL_EVENT_CONFIDENCE(1174),
	NUM_STORED_WITNESS_EVENTS(1175),
	MAX_TIME_NON_ZERO_SPD(1176),
	WITNESSIII_AUTO_RECAL(1177),
	OBD_AUTO_RELOAD_EM(1178),
	HRD_ACCL_SCND_DV_LVL(1179),
	HRD_BRK_SCND_DV_LVL(1180),
	HRD_TRN_SCND_DV_LVL(1181),
	HRD_VRT_SCND_PP_LVL(1182),
	SCND_LVL_ENABLE(1183),
	SCND_LVL_NTS_ENBL(1184),
	WII_TRACE_UPLD_ATMPT_LIM(1185),
	DWNLD_ATMPT_LIM(1186),
	SBS_EXMP_DNLD_ATMPT_LIM(1187),
	SBS_EXMP_CHK_LIM(1188),
	SBS_EXMP_CHK_FREQ(1189),
	UNIT_ENABLED(1190),
	AUTO_WIT_UPDT_ATTMPTS(1191),
	REMOTE_MAN_DOWN_MODULE(1192),
	IWI_HH(1193),
	WIFI_TYPE(1194),
	CRASH_DATA_NOTE_EVENT(1195),
	PRE_INSTALL_FULLS_ENABLE(1196),
	GPS_FILT_ENABLED(1197),
	GPS_AVE_SPD_AGRMNT(1198),
	GPS_IND_SPD_AGRMNT(1199),
	AUTO_MANDOWN(1200),
	AUTO_MD_WARNING(1201),
	BUZZER_BATTERY(1202),
	DSS_MODULE(1203),
	LOW_BATTERY(1204),
	MAP_ACCURACY(1205),
	GPS_ACCURACY(1206),
	PANIC_ENABLE(1207),
	REMOTE_MAN_DOWN_PORT(1208),
	REMOTE_MD_AACK(1209),
	REMOTE_MD_AUTODOWN(1210),
	REMOTE_MD_LOW_BATT(1211),
	REMOTE_MD_MANOK(1212),
	REMOTE_MD_MANUALDOWN(1213),
	REMOTE_MD_OFF(1214),
	REMOTE_MD_ON(1215),
	SBS_UPDATE_RESULT_EVENT(1216),
	SMARTCARD_MODULE(1217),
	SMARTCARD_PORT(1218),
	TEMPERATURE_WITH_EVENTS(1219),
	TRACK_NON_DOT(1220),
	VEHICLE_VIN(1221),
	ORIENTATION(1222),
	Z_LEVEL(1223),
	RMS_LEVEL(1224),
	HARDVERT_DMM_PEAKTOPEAK(1225),
	Y_LEVEL(1226),
	Y_SLOPE(1227),
	DVY(1228),
	X_ACCEL(1229),
	X_SLOPE(1230),
	DVX(1231),
	HARD_ACCEL_LEVEL(1232),
	HARD_ACCEL_AVG_CHANGE(1233),
	HARD_ACCEL_DELTAV(1234),
	IGNITION_OFF_FULLEVENTS_ENABLED(1235),
	G_TRIGGER_LEVEL(1236),
	HARDVERT_DMMTOCRASH_RATIO(1237),
	X_DELTAV(1238),
	Y_DELTAV(1239),
	Z_DELTAV(1240),
	FIRMWARE_VERSION(1241),
	CALIBRATED(1242),
	NUMERATOR_TRIAX_0(1243),
	NUMERATOR_TRIAX_1(1244),
	NUMERATOR_TRIAX_2(1245),
	DENOMINATOR_TRIAX_0(1246),
	DENOMINATOR_TRIAX_1(1247),
	DENOMINATOR_TRIAX_2(1248),
	U_INTERCEPT(1249),
	V_INTERCEPT(1250),
	W_INTERCEPT(1251),
	TRIAX_DIAGNOSTIC(1252),
	DMM_BOOTLOADER_REV(1253),
	TIME_CALIBRATED(1254),
	TIME_RTC(1255),
	TRIAX_STATUS_STRUCTURE_VERSION(1256),
	TEMP_AT_CALIBRATION(1257),
	TEMP_SENSE_ADC(1258),
	U_TEMP_COMP_SLOPE(1259),
	V_TEMP_COMP_SLOPE(1260),
	W_TEMP_COMP_SLOPE(1261),
	TEMP_COMP_SLOPES_IN_BUFFER(1262),
	TEMP_COMP_SLOPES_MEASURED(1263),
	TOTAL_TEMP_COMP_SLOPES_ESTIMATES(1264),
	TOTAL_TEMP_COMP_SLOPES_UPDATES(1265),
	TEMP_COMP_DATA_U1(1266),
	TEMP_COMP_DATA_V1(1267),
	TEMP_COMP_DATA_W1(1268),
	TEMP_COMP_DATA_U2(1269),
	TEMP_COMP_DATA_V2(1270),
	TEMP_COMP_DATA_W2(1271),
	TEMP_COMP_DATA_U3(1272),
	TEMP_COMP_DATA_V3(1273),
	TEMP_COMP_DATA_W3(1274),
	TEMP_COMP_DATA_U4(1275),
	TEMP_COMP_DATA_V4(1276),
	TEMP_COMP_DATA_W4(1277),
	TEMP_COMP_DATA_U5(1278),
	TEMP_COMP_DATA_V5(1279),
	TEMP_COMP_DATA_W5(1280),
	TEMP_COMP_DATA_U6(1281),
	TEMP_COMP_DATA_V6(1282),
	TEMP_COMP_DATA_W6(1283),
	TEMP_COMP_DATA_U7(1284),
	TEMP_COMP_DATA_V7(1285),
	TEMP_COMP_DATA_W7(1286),
	TEMP_COMP_DATA_U8(1287),
	TEMP_COMP_DATA_V8(1288),
	TEMP_COMP_DATA_W8(1289),
	TEMP_COMP_DATA_U9(1290),
	TEMP_COMP_DATA_V9(1291),
	TEMP_COMP_DATA_W9(1292),
	TEMP_COMP_ENABLED(1293),
	WIGGLE_TRIGGER_COUNT_TOTAL(1294),
	FIRMWARE_REV_PREVIOUS(1295),
	WITNESSII_STATUS_STRUCTURE_VERSION(1296),
	FIRMWARE_REV(1297),
	PERCENT_MEMORY_AVAILABLE(1298),
	WITNESS_DIAGNOSTIC(1299),
	U_NUMERATOR(1300),
	V_NUMERATOR(1301),
	WITNESS_BOOTLOADER_REV(1302);

	

	 
	 

	 private int code;

	 private Ways_SETTINGS(int c) {
	   code = c;
	 }

	 public int getCode() {
	   return code;
	 }
	 
	 private static HashMap<Integer, Ways_SETTINGS> lookupByCode = new HashMap<Integer, Ways_SETTINGS>();
	    
	    static {
	        for (Ways_SETTINGS p : EnumSet.allOf(Ways_SETTINGS.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    
	    public static Ways_SETTINGS valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
}
