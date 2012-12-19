package com.inthinc.pro.comm.parser.note;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum NoteType {
	FULLEVENT(1, new Attrib[]{Attrib.DELTAVS}),
	NOTEEVENT(2, new Attrib[]{Attrib.DELTAVS}),
	SEATBELT(3, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
	SPEEDING(4, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM, Attrib.POSTEDSPEED, Attrib.AVGSPEED, Attrib.AVGRPM}),
	THEFT(5, new Attrib[]{}),
	LOCATION(6, new Attrib[]{}),
	NEWDRIVER(7, new Attrib[]{Attrib.DRIVERSTR, Attrib.MCMRULESET}),
	MANDOWN(8, new Attrib[]{}),
	PANIC(9, new Attrib[]{}),
	HOME(11, new Attrib[]{}),
	CHECKUNIT(12, new Attrib[]{Attrib.BATTERYVOLTAGE}),
//	GEOFENCE(13, new Attrib[]{}),
	RPM(14, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
	MAN_OK(15, new Attrib[]{}),
	TEMPERATURE(18, new Attrib[]{}),
	IGNITION_ON(19, new Attrib[]{}),
	IGNITION_OFF(20, new Attrib[]{}),
	MODEM_OFF(21, new Attrib[]{}),
	LOW_BATTERY(22, new Attrib[]{}),
	ACCELERATION (23, new Attrib[]{Attrib.ACCELERATION}),
	DECELERATION (24, new Attrib[]{Attrib.ACCELERATION}),
//	WATCHDOG(25, new Attrib[]{}),
//	REBOOT(26, new Attrib[]{}),
	VERSION(27, new Attrib[]{Attrib.VERSIONTIME}),
	GPS_STATE(28, new Attrib[]{Attrib.GPSSTATE}),
//	DATA(29, new Attrib[]{}),
	DESCRIPTOR(30, new Attrib[]{}),  
	CONF(31, new Attrib[]{}),
	ACKNOWLEDGE(33, new Attrib[]{Attrib.FORWARDCOMMANDID}),
//	TRACE(34, new Attrib[]{}),
	INSTALL(35, new Attrib[]{}),
	BASE_VERSION(36, new Attrib[]{Attrib.VERSIONTIME}),
	HEADLIGHT(38, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
	BOUNDARY_CHANGE(39, new Attrib[]{Attrib.BOUNDARYID}),
	HOS_CHANGE_STATE(40, new Attrib[]{Attrib.DRIVERFLAG, Attrib.TRIPREPORTFLAG, Attrib.TRIPINSPECTIONFLAG, Attrib.CLEARDRIVERFLAG, Attrib.DRIVERSTR, Attrib.LOCATION, Attrib.DRIVERSTATUS}),
	MONTHLY_UPDATE(41, new Attrib[]{}),
	TIMESTAMP(42, new Attrib[]{}),
//	HOS_RECEIVED(43, new Attrib[]{}),
	LOCATION_DEBUG(44, new Attrib[]{}),
//	CRASH_DATA(45, new Attrib[]{}),
	NO_DRIVER(46, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
	ON_ROAD(47, new Attrib[]{}),
	OFF_ROAD(48, new Attrib[]{}),
	SPEEDING_EX(49, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM, Attrib.POSTEDSPEED}),
//	ERROR(50, new Attrib[]{}),
	STATS(51, new Attrib[]{Attrib.LOCKPERCENTAGE, Attrib.NOSTRINGPERCENTAGE, Attrib.IGNITIONONPERCENTAGE, Attrib.SEATBELTINPERCENTAGE, Attrib.FILESIZES, Attrib.REBOOTS, Attrib.AVERAGELOCKTIME, Attrib.LOOPS, Attrib.MESSAGESLATENT, Attrib.MESSAGESSIZE, Attrib.ODOMETERODBCOUNT, Attrib.ODOMETERGPSCOUNT, Attrib.MAPSMOUNTEDPERCENTAGE, Attrib.MAPSUNMOUNTEDCOUNT}),
	LOW_POWER_MODE(52, new Attrib[]{}),
	SATELLITE_SWITCH(53, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
	WITNESS_HEARTBEAT_VIOLATION(54, new Attrib[]{}),
	DRIVER_HOURS(55, new Attrib[]{Attrib.DRIVERSTR}),
	DOT_STOPPED(56, new Attrib[]{Attrib.REASONCODEDOT}),
	ODOMETER(57, new Attrib[]{}),
	SPEEDING_EX2(58, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM, Attrib.POSTEDSPEED, Attrib.AVGSPEED}),
	NO_TRAILER(59, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM}),
//	24HR_REBOOT(60, new Attrib[]{}),
//	FIRMWARE_REBOOT(61, new Attrib[]{}),
//	KEYPAD_REBOOT(62, new Attrib[]{}),
//	CMD_REBOOT(63, new Attrib[]{}),
	TRAILER_DATA(64, new Attrib[]{Attrib.DATALENGTH, Attrib.TRAILERID, Attrib.SERVICEID, Attrib.HAZMATFLAG}),
	CLEAR_DRIVER(66, new Attrib[]{}),
	HOS_NO_HOURS(67, new Attrib[]{Attrib.REASONCODEHOS}),
	TEXT_MSG(72, new Attrib[]{Attrib.TEXTMESSAGE}),
	FUEL_STOP(73, new Attrib[]{Attrib.VEHICLEGALLONS, Attrib.ODOMETER, Attrib.TRAILERGALLONS, Attrib.LOCATION}),
	FIRMWARE_UP_TO_DATE(74, new Attrib[]{Attrib.UPTODATESTATUS}),
	MAPS_UP_TO_DATE(75, new Attrib[]{Attrib.UPTODATESTATUS}),
	ZONES_UP_TO_DATE(76, new Attrib[]{Attrib.UPTODATESTATUS}),
	BOOT_LOADER_UP_TO_DATE(77, new Attrib[]{Attrib.UPTODATESTATUS}),
	WSZONES_ARRIVAL(78, new Attrib[]{}),
	TEXT_MSG_CANNED(80, new Attrib[]{Attrib.EVENTCODE}),
	QUEUE_FULL(81, new Attrib[]{}),
	WAYPOINT_EX(83, new Attrib[]{}),
	WSZONES_DEPARTURE (87, new Attrib[]{}),
	HOS_30_MIN(88, new Attrib[]{}),
	HOS_MINUS_15(89, new Attrib[]{}),
	HOS_MINUS_30(90, new Attrib[]{}),
	XCAT(91, new Attrib[]{Attrib.XCATEVENT, Attrib.XCATDATA}),
	DMR (92, new Attrib[]{Attrib.EVENTCODEINT}),
	SPEEDING_EX3(93, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM, Attrib.POSTEDSPEED, Attrib.AVGSPEED, Attrib.AVGRPM}),
	TRIAX_STATUS(95, new Attrib[]{Attrib.MAGICA, Attrib.ORIENTATIONTRIAX, Attrib.ZLEVEL, Attrib.RMSLEVEL, Attrib.RMSWINDOW, Attrib.YWINDOW, Attrib.YLEVEL, Attrib.XACCEL, Attrib.SLOPE, Attrib.DVX, Attrib.CALVERSION, Attrib.NUMERATOR, Attrib.DENOMINATOR, Attrib.INTERCEPTS, Attrib.GTRIGGERLEVEL, Attrib.DIAGNOSTIC}),
///////////////////////////////////////////////
	HOS_CHANGE_STATE_EX(96, new Attrib[]{Attrib.DRIVERSTATUS, Attrib.DRIVERFLAG, Attrib.DRIVERSTR}),
	LIGHT_DUTY(97, new Attrib[]{}),
	HEAVY_DUTY(98, new Attrib[]{}),
	MISSING_IWI_CONF(99, new Attrib[]{}),
	TRIAX_STATUS_EX(110, new Attrib[]{Attrib.MAGICA, Attrib.ORIENTATIONTRIAX, Attrib.ZLEVEL, Attrib.RMSLEVEL, Attrib.RMSWINDOW, Attrib.YWINDOW, Attrib.YLEVEL, Attrib.XACCEL, Attrib.SLOPE, Attrib.DVX, Attrib.CALVERSION, Attrib.NUMERATOR, Attrib.DENOMINATOR, Attrib.INTERCEPTS, Attrib.GTRIGGERLEVEL, Attrib.DIAGNOSTIC}),
	
	CRASH_DATA_EXTENDED(112, new Attrib[]{}),
	
	HOS_CHANGE_STATE_NO_GPS_LOCK(113, new Attrib[]{Attrib.DRIVERSTATUS, Attrib.DRIVERFLAG, Attrib.DRIVERSTR, Attrib.LOCATION}),
	NEWDRIVER_HOSRULE(116, new Attrib[]{Attrib.DRIVERSTR, Attrib.MCMRULESET}),
	WSZONES_ARRIVAL_EX(117, new Attrib[]{Attrib.ZONEID}),
	WSZONES_DEPARTURE_EX(118, new Attrib[]{Attrib.ZONEID}),
//	REQUEST_SPECIFIC_DETAIL_RECORDS(119, new Attrib[]{}),
//	WITNESSII_STATUS(120, new Attrib[]{}),
//	WITNESSII_LIST(121, new Attrib[]{}),
	QSI_UP_TO_DATE(122, new Attrib[]{Attrib.UPTODATESTATUS}),
	WITNESS_UP_TO_DATE(123, new Attrib[]{Attrib.UPTODATESTATUS}),
	TRIAX_UP_TO_DATE(124, new Attrib[]{Attrib.UPTODATESTATUS}),
	TRIAX_STATUS_DIAGNOSTIC(125, new Attrib[]{Attrib.MAGICA, Attrib.ORIENTATIONTRIAX, Attrib.ZLEVEL, Attrib.RMSLEVEL, Attrib.RMSWINDOW, Attrib.YWINDOW, Attrib.YLEVEL, Attrib.XACCEL, Attrib.SLOPE, Attrib.DVX, Attrib.CALVERSION, Attrib.NUMERATOR, Attrib.DENOMINATOR, Attrib.INTERCEPTS, Attrib.GTRIGGERLEVEL, Attrib.DIAGNOSTIC}),
	IWICONF_LOAD_ERROR(126, new Attrib[]{}),
	INSTALLATION_CHECKLIST(127, new Attrib[]{}),
	WITNESSII_CHANGE_CONFIG_ACK(128, new Attrib[]{Attrib.FORWARDCOMMANDID, Attrib.ERRORCODE, Attrib.DATAEXPECTED, Attrib.DATACURRENT}),
	GET_WITNESSII_STATUS(129, new Attrib[]{Attrib.ERROR, Attrib.STRUCTVERSION, Attrib.FIRMWAREVERSION, Attrib.ORIENTATION, Attrib.CALIBRATIONSTATUS, Attrib.PERCENTMEMORYAVAILABLE, Attrib.DIAGNOSTIC, Attrib.WITNESSID,
			Attrib.STATUSDELTAVX, Attrib.STATUSDELTAVY, Attrib.STATUSDELTAVZ, Attrib.NUMERATOR, Attrib.DENOMINATOR, Attrib.INTERCEPTS, Attrib.TIMECALIBRATED, Attrib.TIMERTC,
			Attrib.BOOTLOADERREV}),
	WITNESSII_TRACE(130, new Attrib[]{Attrib.CRASHTRACE}),
//	WITNESSII_TRACE_OVERWRITTEN(131, new Attrib[]{Attrib.TIME, Attrib.WITNESSIIARCHIVE}),
	WITNESSII_TRACE_OVERWRITTEN(131, new Attrib[]{Attrib.TIME}),	
	WITNESSII_ARCHIVE_HEADER(132, new Attrib[]{}),
	CLEAR_OCCUPANT(133, new Attrib[]{Attrib.OCCUPANTSTR}),
//	WITNESSII_ARCHIVED_TRACE_UPLOADED(134, new Attrib[]{Attrib.WITNESSIIARCHIVEHEADER, Attrib.WITNESSIIARCHIVE}),
	WITNESSII_ARCHIVED_TRACE_UPLOADED(134, new Attrib[]{}),	
	BOUNDARYDAT_UP_TO_DATE(136, new Attrib[]{}),
	PLACES2DAT_UP_TO_DATE(137, new Attrib[]{}),
//	SBD_RING_NULL(138, new Attrib[]{}),
//	SBD_RING(139, new Attrib[]{}),
	IDLE_STATS(140, new Attrib[]{Attrib.DURATION, Attrib.LOWIDLE2, Attrib.HIGHIDLE2}),
//	WITNESSII_DIAGNOSTIC(141, new Attrib[]{}),
	AUTOMANDOWN(142, new Attrib[]{}),
	AUTOMANOK(143, new Attrib[]{}),

	SMTOOLS_EMULATION_UP_TO_DATE(144, new Attrib[]{Attrib.UPTODATESTATUS}),
	SMTOOLS_FIRMWARE_UP_TO_DATE(145, new Attrib[]{Attrib.UPTODATESTATUS}),
	SMTOOLS_SILICON_ID(146, new Attrib[]{Attrib.SENDER, Attrib.SILICONID}),
	TRIAX_STATUS_WITNESSIII(147, new Attrib[]{Attrib.ERROR, Attrib.MAGICA, Attrib.ORIENTATIONTRIAX, Attrib.ZLEVEL, Attrib.RMSLEVEL, Attrib.RMSWINDOW, Attrib.YWINDOW, Attrib.YLEVEL, Attrib.XACCEL, Attrib.SLOPE, Attrib.DVX, Attrib.CALVERSION, Attrib.NUMERATOR, Attrib.DENOMINATOR, Attrib.INTERCEPTS, Attrib.GTRIGGERLEVEL, Attrib.DIAGNOSTIC,
			Attrib.BOOTLOADERREV, Attrib.TIMECALIBRATED, Attrib.TIMERTC, Attrib.YSLOPE, Attrib.ZSLOPE, Attrib.XWINDOW, Attrib.STRUCTVERSION, Attrib.ZWINDOW, Attrib.TEMPATCALIBRATION, Attrib.DVY, Attrib.DVZ, Attrib.TEMPSENSEADC_ATCAL, Attrib.TEMPCOMPSLOPE, Attrib.TEMPCOMPSLOPESINBUFFER, Attrib.TEMPCOMPSLOPESMEASURED, Attrib.TOTALTEMPCOMPSLOPEESTIMATES,
			Attrib.TOTALTEMPCOMPSLOPEUPDATES, Attrib.TEMPCOMPSAMPLEMAX, Attrib.TEMPCOMPSAMPLEMIN, Attrib.TEMPCOMPDATA, Attrib.TEMPCOMPENABLED, Attrib.WIGGLETRIGGERCOUNTTOTAL, Attrib.FIRMWAREREVPREVIOUS, Attrib.HARDACCELLEVEL, Attrib.HARDACCELAVERAGECHANGE, Attrib.HARDACCELDELTAV, Attrib.HARDVERTDMMTOCRASHRATIOTHRES,
			Attrib.HARDVERTDMMPEAKTOPEAKLEVEL, Attrib.PREINSTALLFULLEVENTSENABLED, Attrib.IGNITIONOFFFULLEVENTSENABLED}),
	SMTOOLS_DEVICE_STATUS(148, new Attrib[]{Attrib.MODEOFOPERATIONS, Attrib.WEICOUNT, Attrib.ERRORCODE, Attrib.POWERUPCOUNT, Attrib.WATCHDOGCOUNT, Attrib.DIGITALINPUTSTATUS, Attrib.DIGITALOUTPUTSTATUS, Attrib.CPUPERCENTAGE, Attrib.LOGGEDMESSAGECOUNT,
			Attrib.RECEIVEDMESSAGECOUNT, Attrib.TRANSMITTEDMESSAGECOUNT, Attrib.ERRORDETAILS, Attrib.SOFTWARERESETCOUNT, Attrib.EMUNAMEVERIFIED, Attrib.EMUNAMEDEVICE, Attrib.EMUNAMETRANSFORM, Attrib.SILICONID, Attrib.VIN, Attrib.SMTOOLSTIMERRUNNING,
			Attrib.SMTOOLSRESETSTATUS, Attrib.SMTOOLSFIRMWAREREV, Attrib.SMTOOLSHARDWAREREV}),
	OBD_PARAMS_STATUS(149, new Attrib[]{Attrib.RPMCOLLECTED, Attrib.SPEEDCOLLECTED, Attrib.ODOMETERCOLLECTED, Attrib.BRAKECOLLECTED, Attrib.SEATBELTCOLLECTED, Attrib.RPMREQUESTS, Attrib.SPEEDREQUESTS, Attrib.ODOMETERREQUESTS, Attrib.BRAKEREQUESTS,
			Attrib.SEATBELTREQUESTS, Attrib.RPMRECEIVES, Attrib.SPEEDRECEIVES, Attrib.ODOMETERRECEIVES, Attrib.BRAKERECEIVES, Attrib.SEATBELTRECEIVES}),
//	POWER_ON(150, new Attrib[]{}),
	INTRASTATE_BND_CROSSED(151, new Attrib[]{}),
	POTENTIAL_CRASH(152, new Attrib[]{Attrib.DELTAVS}),
	CANCEL_POTENTIAL_CRASH(153, new Attrib[]{Attrib.DELTAVS}),
	CHECK_HOURS(157, new Attrib[]{Attrib.DRIVERSTR}),
	WIRELINE_STATUS(158, new Attrib[]{}),
	WIRELINE_ALARM(159, new Attrib[]{}),
	WIRELINE_IGN_TEST_NOT_RUN(160, new Attrib[]{}),
	WIRELINE_IGN_TEST_FAIL(161, new Attrib[]{}),
	WIRELINE_IGN_TEST_PASS(162, new Attrib[]{}),
	NO_MAPS_DRIVE(164, new Attrib[]{}),
	AUTODETECT(165, new Attrib[]{}),
	FUEL_STOP_EX(166, new Attrib[]{Attrib.VEHICLEGALLONS, Attrib.ODOMETER, Attrib.TRAILERGALLONS, Attrib.LOCATION}),
	LOW_BATTERY_POTENTIAL_TAMPERING(167, new Attrib[]{}),
	ROLLOVER(169, new Attrib[]{Attrib.DELTAVS, Attrib.DURATION}),
	VERTICALEVENT(171, new Attrib[]{Attrib.DELTAVS, Attrib.DURATION}),
	PARKING_BRAKE(172, new Attrib[]{Attrib.STATE}),
	UNIT_INFO(173, new Attrib[]{Attrib.DATA}),
	FULLCONFIDENCE_LEVEL(174, new Attrib[]{Attrib.DELTAVS, Attrib.CONFIDENCELEVEL}),
//	CRASH_DATA_HI_RES(175, new Attrib[]{Attrib.SKIPPARSING}),
	DSS_MICROSLEEP(176, new Attrib[]{Attrib.DATA}),
	DSS_DISTRACTION_STATS(177, new Attrib[]{Attrib.DATA}),
	SECONDARY_NOTEEVENT(178, new Attrib[]{Attrib.DELTAVS}),
	WEEKLY_GPRS_USAGE(180, new Attrib[]{Attrib.NOPTWEEKLYWRITECOUNT, Attrib.UPLOADWEEKTOTALWITTNESSIITRACE, Attrib.DOWNLOADWEEKLYTOTALFIRMWARE, Attrib.DOWNLOADWEEKLYTOTALMAPS, Attrib.DOWNLOADWEEKLYTOTALZONES, Attrib.DOWNLOADWEEKLYTOTALBOOTLOADER, Attrib.DOWNLOADWEEKLYTOTALQSIFIRMWARE,
			Attrib.DOWNLOADWEEKLYTOTALWITNESSIIFIRMWARE, Attrib.DOWNLOADWEEKLYTOTALTRIAXIIFIRMWARE, Attrib.DOWNLOADWEEKLYTOTALBOUNDARYDAT, Attrib.DOWNLOADWEEKLYTOTALBOUNDARYDAT, Attrib.DOWNLOADWEEKLYTOTALSMTOOLSEMULATION, Attrib.DOWNLOADWEEKLYTOTALSMTOOLSFIRMWARE, Attrib.DOWNLOADWEEKLYTOTALSBSEXMAPSCHECKBYTES,
			Attrib.DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADBYTES, Attrib.DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADCOUNT, Attrib.UPLOADWEEKTOTALNOTIFICATIONBYTES, Attrib.UPLOADWEEKTOTALNOTIFICATIONCOUNT}),
	MCM_APP_FIRMWARE_UP_TO_DATE(181, new Attrib[]{Attrib.UPTODATESTATUS}),
	REMOTE_AUTO_MANDOWN(182, new Attrib[]{}),
	REMOTE_MAN_MANDOWN(183, new Attrib[]{}),
	REMOTE_OK_MANDOWN(184, new Attrib[]{}),
	REMOTE_OFF_MANDOWN(185, new Attrib[]{}),
	REMOTE_LOW_BATT_MANDOWN(186, new Attrib[]{}),
	REMOTE_ON_MANDOWN(187, new Attrib[]{}),
	REMOTE_AACK_MANDOWN(188, new Attrib[]{}),
	SBS_UPDATE(189, new Attrib[]{Attrib.SKIPINT, Attrib.FILENAME, Attrib.BASELINEVERSION, Attrib.EXCEPTIONVERSION, Attrib.TIMELASTVISTED, Attrib.TIMELASTCHECKED, Attrib.MAPHASH, Attrib.MAPFILESIZE, Attrib.MINLATITUDE, Attrib.MINLONGITUDE, Attrib.MAXLATITUDE, Attrib.MAXLONGITUDE, Attrib.SBSEXMAPUPDATERESULT, Attrib.SBSDBUPDATERESULT}),
//	EMU_TARBALL_UP_TO_DATE 190, new Attrib[]{}),
	SPEEDING_EX4(191, new Attrib[]{Attrib.TOPSPEED, Attrib.DISTANCE, Attrib.MAXRPM, Attrib.POSTEDSPEED, Attrib.AVGSPEED, Attrib.AVGRPM, Attrib.LINKID, Attrib.ZONEID, Attrib.SPEEDINGTYPE, Attrib.SEATBELTENGAGED, Attrib.STARTTIME, Attrib.STOPTIME, Attrib.MAXTIME, Attrib.COURSE, Attrib.MAXSPEEDLIMIT, Attrib.SBSSPEEDLIMIT, Attrib.ZONESPEEDLIMIT,
			Attrib.WEATHERSPEEDLIMITPERCENT, Attrib.SEVERESPEEDTHRESHOLD, Attrib.SPEEDINGBUFFER, Attrib.SPEEDINGGRACEPERIOD, Attrib.SEVERESPEEDSECONDS, Attrib.SPEEDMODULEENABLED, Attrib.SPEEDSOURCE}),      
				//, Attrib.SPEEDDATAHIRES, Attrib.OBDCONFIDENCEPERCENT, Attrib.GPSCONFIDENCEPERCENT, Attrib.OBDSPEED, Attrib.GPSSPEED, Attrib.FILTEREDSPEED,
				//Attrib.GPSDISTANCE, Attrib.OBDDISTANCE, Attrib.LATLONGDIST, Attrib.VERSIONBASELINE1, Attrib.VERSIONBASELINE2, Attrib.VERSIONBASELINE3, Attrib.VERSIONBASELINE4, Attrib.VERSIONEXCEPTION1, Attrib.VERSIONEXCEPTION2, Attrib.VERSIONEXCEPTION3, Attrib.VERSIONEXCEPTION4}),
	
//	SPEEDING_LOG4(192, new Attrib[]{}),

//	SNITCH_DIAGNOSTICS(200, new Attrib[]{}),
	IDLING(208, new Attrib[]{}),
	CRASH(209, new Attrib[]{}),
	SPEED_COACHING(210, new Attrib[]{}),
	RF_KILL(218, new Attrib[]{}),
    CREATE_ROAD_HAZARD(226, new Attrib[]{}),

	//Stripped
	STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246, new Attrib[]{Attrib.TYPE_FWDCMD, Attrib.ACKDATA, Attrib.TYPE_FWDCMD_ID}),

	WIFI_STRIPPED_GET_WITNESSII_TRACE(248, new Attrib[]{Attrib.CRASHTRACE}),
	STRIPPED_GET_WITNESSII_LIST_EX(249, new Attrib[]{}),
	STRIPPED_GET_SPECIFIC_DETAIL_RECORDS(250, new Attrib[]{}),
	STRIPPED_GET_SHORT_ID(251, new Attrib[]{Attrib.EMPID}),
	STRIPPED_GET_OCCUPANTS(252, new Attrib[]{Attrib.EMPID}),
	STRIPPED_GET_OCCUPANT_INFO(253, new Attrib[]{Attrib.DRIVERID}),
	STRIPPED_ACKNOWLEDGE(254, new Attrib[]{});
	
	private Attrib[] attribs;
	private Integer code;
	private static final Map<Integer,NoteType> lookup = new HashMap<Integer,NoteType>();
    private static Logger logger = LoggerFactory.getLogger(NoteType.class);
	
    private NoteType(Integer code, Attrib[] attribs)
    {
    	this.code = code;
    	this.attribs = attribs;
    }


	static {
	     for(NoteType nt : EnumSet.allOf(NoteType.class))
	          lookup.put(nt.getCode(), nt);
	}
	
	public Integer getCode() { return code; }
	
	public static  NoteType get(Integer code) { 
	     return lookup.get(code); 
	}
	
	public Attrib[] getAttribs()
	{
		return attribs;
	}

	public boolean isStrippedNote()
	{
		return (code.intValue() > 245 && code.intValue() < 255);
	}

	public static boolean isStrippedNote(int code)
	{
		return (code > 245 && code < 255);
	}
	
	public boolean isTripStartNoteType()
	{
		return  isTripStartNoteType(getCode());
	}

	public static boolean  isTripStartNoteType(int code)
	{
		return (get(code) == NEWDRIVER || get(code) == NEWDRIVER_HOSRULE || get(code) == IGNITION_ON);
	}

	public boolean isTripEndNoteType()
	{
		return isTripEndNoteType(getCode());
	}

	public static boolean isTripEndNoteType(int code)
	{
		return (get(code) == CLEAR_DRIVER 
				|| get(code) == LOW_BATTERY 
				|| get(code) == IGNITION_OFF 
				|| get(code) == RF_KILL 
				|| get(code) == HOS_CHANGE_STATE_EX 
				|| get(code) == HOS_CHANGE_STATE_NO_GPS_LOCK
//				|| get(code) == LOW_POWER_MODE
				);
	}

	public boolean isIdleNoteType()
	{
		return isIdleNoteType(getCode());
	}

	public static boolean isIdleNoteType(int code)
	{
		return (get(code) == IDLE_STATS || get(code) == IDLING);
	}

	
}
