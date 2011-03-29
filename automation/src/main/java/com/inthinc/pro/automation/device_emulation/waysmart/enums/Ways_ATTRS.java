package com.inthinc.QA.waysmart.enums;

import java.util.EnumSet;
import java.util.HashMap;


public enum Ways_ATTRS {
	//Attributes
	 
	 ATTR_TOPSPEED(1),
	 ATTR_AVGSPEED(2),
	 ATTR_POSTEDSPEED(3),
	 ATTR_AVGRPM(4),
	 ATTR_YLEVEL(28),
	 ATTR_ZLEVEL(29),
	 ATTR_GPSSPEED(42),
	 ATTR_DISTANCE(129),
	 ATTR_MAXRPM(130),
	 ATTR_DELTAVX(131),
	 ATTR_DELTAVY(132),
	 ATTR_DELTAVZ(133),
	 ATTR_COURSE(140),
	 ATTR_RPM(141),
	 ATTR_BOUNDARYID(148),
	 ATTR_HARDVERTDMMPEAKTOPEAKLEVEL(161),
	 ATTR_ZONEID(192),
	 ATTR_VERSIONTIME(193),
	 ATTR_LOWIDLE(219),
	 ATTR_HIGHIDLE(220),
	 ATTR_DRIVERID(227),
	 ATTR_LINKID(231),
	 ATTR_BASELINEVERSION(8192),
	 ATTR_BOOTLOADERREV(8193),
	 ATTR_BRAKECOLLECTED(8194),
	 ATTR_CALVERSION(8195),
	 ATTR_CALIBRATIONSTATUS(8196),
	 ATTR_CALLINGFILE(8197),
	 ATTR_CLEARDRIVERFLAG(8198),
	 ATTR_CONFIDENCELEVEL(8199),
	 ATTR_DIAGNOSTIC(8200),
	 ATTR_DRIVERFLAG(8201),
	 ATTR_DRIVERSTATUS(8202),
	 ATTR_DVX(8203),
	 ATTR_DVY(8204),
	 ATTR_DVZ(8205),
	 ATTR_EVENTCODE(8206),
	 ATTR_EXCEPTIONVERSION(8207),
	 ATTR_FILTEREDSPEED(8208),
	 ATTR_FIRMWAREREVPREVIOUS(8209),
	 ATTR_FIRMWAREVERSION(8210),
	 ATTR_FLAG(8211),
	 ATTR_GNITIONOFFFULLEVENTSENABLED(8212),
	 ATTR_GPSCONFIDENCEPERCENT(8213),
	 ATTR_GPSSTATE(8214),
	 ATTR_HARDACCELAVERAGECHANGE(8215),
	 ATTR_HARDACCELDELTAV(8216),
	 ATTR_HARDACCELLEVEL(8217),
	 ATTR_HARDVERTDMMTOCRASHRATIOTHRES(8218),
	 ATTR_IGNITIONONPERCENTAGE(8219),
	 ATTR_LOCKPERCENTAGE(8220),
	 ATTR_MAGICA(8221),
	 ATTR_MAPSMOUNTEDPERCENTAGE(8222),
	 ATTR_MAPSUNMOUNTEDCOUNT(8223),
	 ATTR_MAXSPEEDLIMIT(8224),
	 ATTR_MCMRULESET(8225),
	 ATTR_NOSTRINGPERCENTAGE(8226),
	 ATTR_OBDCONFIDENCEPERCENT(8227),
	 ATTR_OBDSPEED(8228),
	 ATTR_ODOMETERCOLLECTED(8229),
	 ATTR_ORIENTATIONTRIAX(8230),
	 ATTR_PERCENTMEMORYAVAILABLE(8231),
	 ATTR_PREINSTALLFULLEVENTSENABLED(8232),
	 ATTR_REASONCODEDOT(8233),
	 ATTR_REASONCODEHOS(8234),
	 ATTR_REBOOTS(8235),
	 ATTR_RMSLEVEL(8236),
	 ATTR_RMSWINDOW(8237),
	 ATTR_RPMCOLLECTED(8238),
	 ATTR_SBSDBUPDATERESULT(8239),
	 ATTR_SBSEXMAPUPDATERESULT(8240),
	 ATTR_SBSSPEEDLIMIT(8241),
	 ATTR_SEATBELTENGAGED(8242),
	 ATTR_SEATBELTCOLLECTED(8243),
	 ATTR_SEATBELTINPERCENTAGE(8244),
	 ATTR_SEVERESPEEDTHRESHOLD(8246),
	 ATTR_SLOPE(8247),
	 ATTR_SPEEDCOLLECTED(8248),
	 ATTR_SPEEDMODULEENABLED(8249),
	 ATTR_SPEEDSOURCE(8250),
	 ATTR_SPEEDINGBUFFER(8251),
	 ATTR_SPEEDINGTYPE(8253),
	 ATTR_STATE(8254),
	 ATTR_STATUS(8255),
	 ATTR_TEMPCOMPENABLED(8256),
	 ATTR_TEMPCOMPSLOPESINBUFFER(8257),
	 ATTR_TRIPINSPECTIONFLAG(8258),
	 ATTR_TRIPREPORTFLAG(8259),
	 ATTR_UPTODATESTATUS(8260),
	 ATTR_VERSIONBASELINE1(8261),
	 ATTR_VERSIONBASELINE2(8262),
	 ATTR_VERSIONBASELINE3(8263),
	 ATTR_VERSIONBASELINE4(8264),
	 ATTR_VERSIONEXCEPTION1(8265),
	 ATTR_VERSIONEXCEPTION2(8266),
	 ATTR_VERSIONEXCEPTION3(8267),
	 ATTR_VERSIONEXCEPTION4(8268),
	 ATTR_WEATHERSPEEDLIMITPERCENT(8269),
	 ATTR_XACCEL(8270),
	 ATTR_XWINDOW(8271),
	 ATTR_XCATDATA(8272),
	 ATTR_XCATEVENT(8273),
	 ATTR_YSLOPE(8274),
	 ATTR_YWINDOW(8275),
	 ATTR_ZSLOPE(8276),
	 ATTR_ZWINDOW(8277),
	 ATTR_ZONESPEEDLIMIT(8278),
	 ATTR_HAZMATFLAG(8279),
	 ATTR_STRUCTVERSION(8280),
	 ATTR_SENDER(8281),
	 ATTR_CONNECTTYPE(8282),
	 ATTR_ACCELERATION(16384),
	 ATTR_ACKNOWLEDGEDCOMMANDID(16385),
	 ATTR_BELOWTHRESHOLDFULLEVENTCOUNT(16386),
	 ATTR_CONFIRMEDNOTEEVENTCOUNT(16387),
	 ATTR_CPUPERCENTAGE(16388),
	 ATTR_DATALENGTH(16389),
	 ATTR_DIGITALINPUTSTATUS(16390),
	 ATTR_DIGITALOUTPUTSTATUS(16391),
	 ATTR_DURATION(16392),
	 ATTR_ERRORCODE(16393),
	 ATTR_ERRORDETAILS(16394),
	 ATTR_FILTEREDNOTEEVENTCOUNT(16395),
	 ATTR_FORWARDCOMMANDID(16396),
	 ATTR_GTRIGGERLEVEL(16397),
	 ATTR_GPSDISTANCE(16398),
	 ATTR_LATLONGDIST(16399),
	 ATTR_LOGGEDMESSAGECOUNT(16400),
	 ATTR_MAXTIME(16400),
	 ATTR_MESSAGESLATENT(16401),
	 ATTR_MODEOFOPERATIONS(16402),
	 ATTR_MSP430REBOOTS(16403),
	 ATTR_OBDDISTANCE(16404),
	 ATTR_POWERRECYCLEATTEMPTS(16405),
	 ATTR_POWERUPCOUNT(16406),
	 ATTR_SMTOOLSHARDWAREREV(16407),
	 ATTR_SOFTWARERESETCOUNT(16408),
	 ATTR_STATUSDELTAVX(16409),
	 ATTR_STATUSDELTAVY(16410),
	 ATTR_STATUSDELTAVZ(16411),
	 ATTR_STOPTIME(16412),
	 ATTR_TEMPATCALIBRATION(16413),
	 ATTR_TEMPCOMPSLOPESMEASURED(16414),
	 ATTR_TEMPSENSEADC_ATCAL(16415),
	 ATTR_TEXTLENGTH(16416),
	 ATTR_TOTALTEMPCOMPSLOPEESTIMATES(16417),
	 ATTR_TOTALTEMPCOMPSLOPEUPDATES(16418),
	 ATTR_TRAILERGALLONS(16419),
	 ATTR_TRANSMITTEDMESSAGECOUNT(16420),
	 ATTR_VEHICLEGALLONS(16420),
	 ATTR_WATCHDOGCOUNT(16421),
	 ATTR_WEICOUNT(16422),
	 ATTR_WIGGLETRIGGERCOUNTTOTAL(16423),
	 ATTR_ANALOGSENSOR(16424),
	 ATTR_LOWIDLE2(16425),
	 ATTR_HIGHIDLE2(16426),
	 ATTR_DATA(24576),
	 ATTR_TEXTMESSAGE(24577),
	 ATTR_DRIVERSTR(24578),
	 ATTR_EMUNAMEDEVICE(24579),
	 ATTR_EMUNAMETRANSFORM(24580),
	 ATTR_FILENAME(24581),
	 ATTR_LOCATION(24582),
	 ATTR_REPORTID(24583),
	 ATTR_SERVICEID(24584),
	 ATTR_SILICONID(24585),
	 ATTR_SMTOOLSFIRMWAREREV(24586),
	 ATTR_TRAILERID(24587),
	 ATTR_VIN(24588),
	 ATTR_WITNESSID(24589),
	 ATTR_OCCUPANTSTR(24590),
	 ATTR_EMPID(24591),
	 ATTR_VEHICLENAME(24592),
	 ATTR_IMEI(24593),
	 ATTR_MCMID(24594),
	 ATTR_AVERAGELOCKTIME(32768),
	 ATTR_BATTERYVOLTAGE(32769),
	 ATTR_BRAKERECEIVES(32770),
	 ATTR_BRAKEREQUESTS(32771),
	 ATTR_DATACURRENT(32772),
	 ATTR_DATAEXPECTED(32773),
	 ATTR_DOWNLOADWEEKLYTOTALBOOTLOADER(32774),
	 ATTR_DOWNLOADWEEKLYTOTALBOUNDARYDAT(32775),
	 ATTR_DOWNLOADWEEKLYTOTALFIRMWARE(32776),
	 ATTR_DOWNLOADWEEKLYTOTALMAPS(32777),
	 ATTR_DOWNLOADWEEKLYTOTALPLACES2DAT(32778),
	 ATTR_DOWNLOADWEEKLYTOTALQSIFIRMWARE(32779),
	 ATTR_DOWNLOADWEEKLYTOTALSBSEXMAPSCHECKBYTES(32780),
	 ATTR_DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADBYTES(32781),
	 ATTR_DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADCOUNT(32782),
	 ATTR_DOWNLOADWEEKLYTOTALSMTOOLSEMULATION(32783),
	 ATTR_DOWNLOADWEEKLYTOTALSMTOOLSFIRMWARE(32784),
	 ATTR_DOWNLOADWEEKLYTOTALTRIAXIIFIRMWARE(32785),
	 ATTR_DOWNLOADWEEKLYTOTALWITNESSIIFIRMWARE(32786),
	 ATTR_DOWNLOADWEEKLYTOTALZONES(32787),
	 ATTR_EMUNAMEVERIFIED(32789),
	 ATTR_ERROR(32790),
	 ATTR_ERRORCODECONF(32790),
	 ATTR_FILESIZES(32791),
	 ATTR_FLAGS(32792),
	 ATTR_LOOPS(32794),
	 ATTR_MAPFILESIZE(32795),
	 ATTR_MAPID(32796),
	 ATTR_MAXPOSGS(32797),
	 ATTR_MAXNEGGS(32798),
	 ATTR_MESSAGESSIZE(32799),
	 ATTR_NOPTWEEKLYWRITECOUNT(32800),
	 ATTR_ODOMETER(32801),
	 ATTR_ODOMETERGPSCOUNT(32802),
	 ATTR_ODOMETERODBCOUNT(32803),
	 ATTR_ODOMETERRECEIVES(32804),
	 ATTR_ODOMETERREQUESTS(32805),
	 ATTR_RECEIVEDMESSAGECOUNT(32806),
	 ATTR_RPMRECEIVES(32807),
	 ATTR_RPMREQUESTS(32808),
	 ATTR_SEATBELTRECEIVES(32809),
	 ATTR_SEATBELTREQUESTS(32810),
	 ATTR_SMTOOLSRESETSTATUS(32812),
	 ATTR_SMTOOLSTIMERRUNNING(32813),
	 ATTR_SPEEDRECEIVES(32814),
	 ATTR_SPEEDREQUESTS(32815),
	 ATTR_STARTTIME(32816),
	 ATTR_SUBMITTEDTIME(32817),
	 ATTR_TIME(32818),
	 ATTR_TIMECALIBRATED(32819),
	 ATTR_TIMELASTCHECKED(32821),
	 ATTR_TIMELASTVISTED(32822),
	 ATTR_TIMERTC(32823),
	 ATTR_UPLOADWEEKTOTALNOTIFICATIONBYTES(32824),
	 ATTR_UPLOADWEEKTOTALNOTIFICATIONCOUNT(32825),
	 ATTR_UPLOADWEEKTOTALWITTNESSIITRACE(32826),
	 ATTR_WIRELINEFLAGS(32827),
	 ATTR_SEVERESPEEDSECONDS(32828),
	 ATTR_SPEEDINGGRACEPERIOD(32829),
	 ATTR_COMPANYID(32830),
	 ATTR_EVENTCODEINT(32831),
	 ATTR_MAXLATITUDE(40960),
	 ATTR_MAXLONGITUDE(40961),
	 ATTR_MINLATITUDE(40962),
	 ATTR_MINLONGITUDE(40963),
	 ATTR_DELTAVS(49152),
	 ATTR_DENOMINATOR(49153),
	 ATTR_IDLETIMES(49154),
	 ATTR_INTERCEPTS(49155),
	 ATTR_LATLNG3(49156),
	 ATTR_LATLNG4(49157),
	 ATTR_NUMERATOR(49158),
	 ATTR_ORIENTATION(49159),
	 ATTR_SENSORAGREEMENT(49160),
	 ATTR_SPEEDDATAHIRES(49161),
	 ATTR_TEMPCOMPDATA(49162),
	 ATTR_TEMPCOMPSAMPLEMAX(49163),
	 ATTR_TEMPCOMPSAMPLEMIN(49163),
	 ATTR_TEMPCOMPSLOPE(49164),
	 ATTR_WITNESSIIARCHIVE(49165),
	 ATTR_WITNESSIIARCHIVEHEADER(49166),
	 ATTR_ACKDATA(49167),
	 ATTR_CRASHTRACE(49168);

	 
	 private int code;

	 private Ways_ATTRS(int c) {
	   code = c;
	 }

	 public int getCode() {
	   return code;
	 }
	 
	 private static HashMap<Integer, Ways_ATTRS> lookupByCode = new HashMap<Integer, Ways_ATTRS>();
	    
	    static {
	        for (Ways_ATTRS p : EnumSet.allOf(Ways_ATTRS.class))
	        {
	            lookupByCode.put(p.getCode(), p);
	        }
	    }
	    
	    public static Ways_ATTRS valueOf(Integer code){
	    	return lookupByCode.get(code);
	    }
}
