package com.inthinc.pro.comm.parser.attrib;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.inthinc.pro.comm.parser.util.ReadUtil;


public enum Attrib {
	
	//////HEADER ATTRIBS/////////////
	NOTETYPE(10, AttribParserType.BYTE, "type"),
	NOTETIME(11, AttribParserType.INTEGER, "time"),
	NOTEGPSLOCKFLAG(12, AttribParserType.BYTE),
	NOTELATLONG(13, AttribParserType.LATLONG),
	NOTESPEED(15, AttribParserType.BYTE, "speed"),
	NOTEODOMETER(16, AttribParserType.ODOMETER, "odometer"),
	NOTEDURATION(17, AttribParserType.SHORT),
	NOTEFLAGS(18, AttribParserType.NOTEFLAGS),  //Version 3
	NOTESPEEDLIMIT(19, AttribParserType.BYTE, "speedLimit"),
	NOTEMAPREV(20, AttribParserType.BYTE, "maprev"),

    WITNESSVER(25, AttribParserType.INTEGER),
    FIRMWAREVER(150, AttribParserType.INTEGER),
	
	OBDPCT(49, AttribParserType.BYTE),
	GPSFILTER(166, AttribParserType.SHORT),
	
	TYPE_FWDCMD_ID(194, AttribParserType.LONG),
	TYPE_FWDCMD(195, AttribParserType.INTEGER),
	
	EMU_HASH_1(202, AttribParserType.INTEGER),
	EMU_HASH_2(203, AttribParserType.INTEGER),
	EMU_HASH_3(204, AttribParserType.INTEGER),
	EMU_HASH_4(205, AttribParserType.INTEGER),
	
	MPGODO(224, AttribParserType.INTEGER),
	
	/* byte */
	AVGSPEED(2, AttribParserType.BYTE, "avgSpeed"),
	POSTEDSPEED(3, AttribParserType.BYTE, "speedLimit"),
	AVGRPM(4, AttribParserType.BYTE, "avgRPM"),
	BASELINEVERSION(63, AttribParserType.BYTE),
	BOOTLOADERREV(8193, AttribParserType.BYTE),
	BRAKECOLLECTED(8194, AttribParserType.BYTE),
	CALVERSION(8195, AttribParserType.BYTE),
	CALIBRATIONSTATUS(8196, AttribParserType.BYTE),
	CALLINGFILE(8197, AttribParserType.BYTE),
	CLEARDRIVERFLAG(8198, AttribParserType.BYTE),
	CONFIDENCELEVEL(8199, AttribParserType.BYTE),
	DIAGNOSTIC(8200, AttribParserType.BYTE),
	DRIVERFLAG(8201, AttribParserType.BYTE),
	DRIVERSTATUS(8202, AttribParserType.BYTE),
	DVX(8203, AttribParserType.BYTE),
	DVY(8204, AttribParserType.BYTE),
	DVZ(8205, AttribParserType.BYTE),
	EVENTCODE(8206, AttribParserType.BYTE),
	EXCEPTIONVERSION(8207, AttribParserType.BYTE),
	FILTEREDSPEED(8208, AttribParserType.BYTE),
	FIRMWAREREVPREVIOUS(8209, AttribParserType.BYTE),
	FIRMWAREVERSION(8210, AttribParserType.BYTE),
	FLAG(8211, AttribParserType.BYTE),
	IGNITIONOFFFULLEVENTSENABLED(8212, AttribParserType.BYTE),
	GPSCONFIDENCEPERCENT(8213, AttribParserType.BYTE),
	GPSSPEED(42, AttribParserType.BYTE),
	GPSSTATE(8214, AttribParserType.BYTE),
	HARDACCELAVERAGECHANGE(8215, AttribParserType.BYTE),
	HARDACCELDELTAV(8216, AttribParserType.BYTE),
	HARDACCELLEVEL(8217, AttribParserType.BYTE),
	HARDVERTDMMTOCRASHRATIOTHRES(8218, AttribParserType.BYTE),
	IGNITIONONPERCENTAGE(8219, AttribParserType.BYTE),
	LOCKPERCENTAGE(8220, AttribParserType.BYTE),
	MAGICA(8221, AttribParserType.BYTE),
	MAPSMOUNTEDPERCENTAGE(8222, AttribParserType.BYTE),
	MAPSUNMOUNTEDCOUNT(8223, AttribParserType.BYTE),
	MAPHASH(32832, AttribParserType.INTEGER),
	MAXSPEEDLIMIT(8224, AttribParserType.BYTE),
	MCMRULESET(8225, AttribParserType.BYTE),
	NOSTRINGPERCENTAGE(8226, AttribParserType.BYTE),
	OBDCONFIDENCEPERCENT(8227, AttribParserType.BYTE),
	OBDSPEED(8228, AttribParserType.BYTE),
	ODOMETERCOLLECTED(8229, AttribParserType.BYTE),
	ORIENTATIONTRIAX(8230, AttribParserType.BYTE),
	PERCENTMEMORYAVAILABLE(8231, AttribParserType.BYTE),
	PREINSTALLFULLEVENTSENABLED(8232, AttribParserType.BYTE),
	REASONCODEDOT(8233, AttribParserType.BYTE),
	REASONCODEHOS(8234, AttribParserType.BYTE),
	REBOOTS(8235, AttribParserType.BYTE),
	RMSLEVEL(8236, AttribParserType.BYTE),
	RMSWINDOW(8237, AttribParserType.BYTE),
	RPMCOLLECTED(8238, AttribParserType.BYTE),
	SBSDBUPDATERESULT(8239, AttribParserType.BYTE),
	SBSEXMAPUPDATERESULT(8240, AttribParserType.BYTE),
	SBSSPEEDLIMIT(8241, AttribParserType.BYTE),
	SEATBELTENGAGED(8242, AttribParserType.BYTE),
	SEATBELTCOLLECTED(8243, AttribParserType.BYTE),
	SEATBELTINPERCENTAGE(8244, AttribParserType.BYTE),
	SEVERESPEEDTHRESHOLD(8246, AttribParserType.BYTE),
	SLOPE(8247, AttribParserType.BYTE),
	SPEEDCOLLECTED(8248, AttribParserType.BYTE),
	SPEEDMODULEENABLED(8249, AttribParserType.BYTE),
	SPEEDSOURCE(8250, AttribParserType.BYTE),
	SPEEDINGBUFFER(8251, AttribParserType.BYTE),
	SPEEDINGTYPE(8253, AttribParserType.BYTE),
	STATE(8254, AttribParserType.BYTE),
	STATUS(8255, AttribParserType.BYTE),
	TEMPCOMPENABLED(8256, AttribParserType.BYTE),
	TEMPCOMPSLOPESINBUFFER(8257, AttribParserType.BYTE),
	TOPSPEED(1, AttribParserType.BYTE, "topSpeed"),
	TRIPINSPECTIONFLAG(8258, AttribParserType.BYTE),
	TRIPREPORTFLAG(8259, AttribParserType.BYTE),
	UPTODATESTATUS(8260, AttribParserType.BYTE),
	VERSIONBASELINE1(8261, AttribParserType.BYTE),
	VERSIONBASELINE2(8262, AttribParserType.BYTE),
	VERSIONBASELINE3(8263, AttribParserType.BYTE),
	VERSIONBASELINE4(8264, AttribParserType.BYTE),
	VERSIONEXCEPTION1(8265, AttribParserType.BYTE),
	VERSIONEXCEPTION2(8266, AttribParserType.BYTE),
	VERSIONEXCEPTION3(8267, AttribParserType.BYTE),
	VERSIONEXCEPTION4(8268, AttribParserType.BYTE),
	WEATHERSPEEDLIMITPERCENT(8269, AttribParserType.BYTE),
	XACCEL(8270, AttribParserType.BYTE),
	XWINDOW(8271, AttribParserType.BYTE),
	XCATDATA(8272, AttribParserType.BYTE),
	XCATEVENT(8273, AttribParserType.BYTE),
	YLEVEL(28, AttribParserType.BYTE),
	YSLOPE(8274, AttribParserType.BYTE),
	YWINDOW(8275, AttribParserType.BYTE),
	ZSLOPE(8276, AttribParserType.BYTE),
	ZWINDOW(8277, AttribParserType.BYTE),
	ZLEVEL(29, AttribParserType.BYTE),
	ZONESPEEDLIMIT(8278, AttribParserType.BYTE),
	HAZMATFLAG(8279, AttribParserType.BYTE),
	STRUCTVERSION(8280, AttribParserType.BYTE),
	SENDER(8281, AttribParserType.BYTE),
	CONNECTTYPE(8282, AttribParserType.BYTE),
	INSPECTIONTYPE(8291, AttribParserType.BYTE),
	VEHICLESAFETOOPERATE(8292, AttribParserType.BYTE),
    RHA_TYPE(8293, AttribParserType.BYTE),
	
	
	
	// SHORT 
	ACCELERATION(16384, AttribParserType.SHORT),
	ACKNOWLEDGEDCOMMANDID(16385, AttribParserType.SHORT),
	BELOWTHRESHOLDFULLEVENTCOUNT(16386, AttribParserType.SHORT),
	BOUNDARYID(148, AttribParserType.SHORT),
	CONFIRMEDNOTEEVENTCOUNT(16387, AttribParserType.SHORT),
	COURSE(140, AttribParserType.SHORT),
	CPUPERCENTAGE(16388, AttribParserType.SHORT),
	DATALENGTH(16389, AttribParserType.SHORT),
	DELTAVX(131, AttribParserType.SHORT, "deltaX"),
	DELTAVY(132, AttribParserType.SHORT, "deltaY"),
	DELTAVZ(133, AttribParserType.SHORT, "deltaZ"),
	DIGITALINPUTSTATUS(16390, AttribParserType.SHORT),
	DIGITALOUTPUTSTATUS(16391, AttribParserType.SHORT),
	DISTANCE(129, AttribParserType.SHORT, "distance"),
	DURATION(16392, AttribParserType.SHORT),
	ERRORCODE(16393, AttribParserType.SHORT),
	ERRORDETAILS(16394, AttribParserType.SHORT),
	FILTEREDNOTEEVENTCOUNT(16395, AttribParserType.SHORT),
	FORWARDCOMMANDID(16396, AttribParserType.SHORT),
	GTRIGGERLEVEL(16397, AttribParserType.SHORT),
	GPSDISTANCE(16398, AttribParserType.SHORT),
	HARDVERTDMMPEAKTOPEAKLEVEL(161, AttribParserType.SHORT),
	LATLONGDIST(16399, AttribParserType.SHORT),
	LOGGEDMESSAGECOUNT(16400, AttribParserType.SHORT),
	MAXRPM(130, AttribParserType.SHORT, "maxRPM"),
	MAXTIME(16400, AttribParserType.SHORT),
	MESSAGESLATENT(16401, AttribParserType.SHORT),
	MODEOFOPERATIONS(16402, AttribParserType.SHORT),
	MSP430REBOOTS(16403, AttribParserType.SHORT),
	OBDDISTANCE(16404, AttribParserType.SHORT),
	POWERRECYCLEATTEMPTS(16405, AttribParserType.SHORT),
	POWERUPCOUNT(16406, AttribParserType.SHORT),
	RPM(141, AttribParserType.SHORT),
	SMTOOLSHARDWAREREV(16407, AttribParserType.SHORT),
	SOFTWARERESETCOUNT(16408, AttribParserType.SHORT),
	STATUSDELTAVX(16409, AttribParserType.SHORT),
	STATUSDELTAVY(16410, AttribParserType.SHORT),
	STATUSDELTAVZ(16411, AttribParserType.SHORT),
	STOPTIME(16412, AttribParserType.SHORT),
	TEMPATCALIBRATION(16413, AttribParserType.SHORT),
	TEMPCOMPSLOPESMEASURED(16414, AttribParserType.SHORT),
	TEMPSENSEADC_ATCAL(16415, AttribParserType.SHORT),
	TEXTLENGTH(16416, AttribParserType.SHORT),
	TOTALTEMPCOMPSLOPEESTIMATES(16417, AttribParserType.SHORT),
	TOTALTEMPCOMPSLOPEUPDATES(16418, AttribParserType.SHORT),
	TRAILERGALLONS(16419, AttribParserType.SHORT),
	TRANSMITTEDMESSAGECOUNT(16420, AttribParserType.SHORT),
	VEHICLEGALLONS(16420, AttribParserType.SHORT),
	WATCHDOGCOUNT(16421, AttribParserType.SHORT),
	WEICOUNT(16422, AttribParserType.SHORT),
	WIGGLETRIGGERCOUNTTOTAL(16423, AttribParserType.SHORT),
	ANALOGSENSOR(16424, AttribParserType.SHORT),
	LOWIDLE2(16425, AttribParserType.SHORT, "LOW_IDLE"),
	HIGHIDLE2(16426, AttribParserType.SHORT, "HIGH_IDLE"),

	
	MILESUNDER30(16442, AttribParserType.SHORT),
	MILES31TO40(16443, AttribParserType.SHORT),
	MILES41TO54(16444, AttribParserType.SHORT),
	MILES55TO64(16445, AttribParserType.SHORT),
	MILESOVER64(16446, AttribParserType.SHORT),
	
    RHA_RADIUS_METERS(16451, AttribParserType.SHORT),
	
	// INT 
	SKIPINT(32000, AttribParserType.INTEGER_SKIP),
	AVERAGELOCKTIME(32768, AttribParserType.INTEGER),
	BATTERYVOLTAGE(32769, AttribParserType.INTEGER),
	BRAKERECEIVES(32770, AttribParserType.INTEGER),
	BRAKEREQUESTS(32771, AttribParserType.INTEGER),
	DATACURRENT(32772, AttribParserType.INTEGER),
	DATAEXPECTED(32773, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALBOOTLOADER(32774, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALBOUNDARYDAT(32775, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALFIRMWARE(32776, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALMAPS(32777, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALPLACES2DAT(32778, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALQSIFIRMWARE(32779, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALSBSEXMAPSCHECKBYTES(32780, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADBYTES(32781, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALSBSEXMAPSDOWNLOADCOUNT(32782, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALSMTOOLSEMULATION(32783, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALSMTOOLSFIRMWARE(32784, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALTRIAXIIFIRMWARE(32785, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALWITNESSIIFIRMWARE(32786, AttribParserType.INTEGER),
	DOWNLOADWEEKLYTOTALZONES(32787, AttribParserType.INTEGER),
	DRIVERID(227, AttribParserType.INTEGER, "driverid"),
	EMUNAMEVERIFIED(32789, AttribParserType.INTEGER),
	ERROR(32790, AttribParserType.INTEGER),
	ERRORCODECONF(32790, AttribParserType.INTEGER),
	FILESIZES(32791, AttribParserType.INTEGER),
	FLAGS(32792, AttribParserType.INTEGER),
	HIGHIDLE(220, AttribParserType.INTEGER, "HIGH_IDLE"),
	LINKID(231, AttribParserType.INTEGER),
	LOOPS(32794, AttribParserType.INTEGER),
	LOWIDLE(219, AttribParserType.INTEGER, "LOW_IDLE"),
	MAPFILESIZE(32795, AttribParserType.INTEGER),
	MAPID(32796, AttribParserType.INTEGER),
	MAXPOSGS(32797, AttribParserType.INTEGER),
	MAXNEGGS(32798, AttribParserType.INTEGER),
	MESSAGESSIZE(32799, AttribParserType.INTEGER),
	NOPTWEEKLYWRITECOUNT(32800, AttribParserType.INTEGER),
	ODOMETER(32801, AttribParserType.INTEGER),
	ODOMETERGPSCOUNT(32802, AttribParserType.INTEGER),
	ODOMETERODBCOUNT(32803, AttribParserType.INTEGER),
	ODOMETERRECEIVES(32804, AttribParserType.INTEGER),
	ODOMETERREQUESTS(32805, AttribParserType.INTEGER),
	RECEIVEDMESSAGECOUNT(32806, AttribParserType.INTEGER),
	RPMRECEIVES(32807, AttribParserType.INTEGER),
	RPMREQUESTS(32808, AttribParserType.INTEGER),
	SEATBELTRECEIVES(32809, AttribParserType.INTEGER),
	SEATBELTREQUESTS(32810, AttribParserType.INTEGER),
	//SENDER(32811 
	SMTOOLSRESETSTATUS(32812, AttribParserType.INTEGER),
	SMTOOLSTIMERRUNNING(32813, AttribParserType.INTEGER),
	SPEEDRECEIVES(32814, AttribParserType.INTEGER),
	SPEEDREQUESTS(32815, AttribParserType.INTEGER),
	STARTTIME(32816, AttribParserType.INTEGER),
	SUBMITTEDTIME(32817, AttribParserType.INTEGER),
	TIME(32818, AttribParserType.INTEGER),
	TIMECALIBRATED(32819, AttribParserType.INTEGER),
	TIMELASTCHECKED(32821, AttribParserType.INTEGER),
	TIMELASTVISTED(32822, AttribParserType.INTEGER),
	TIMERTC(32823, AttribParserType.INTEGER),
	UPLOADWEEKTOTALNOTIFICATIONBYTES(32824, AttribParserType.INTEGER),
	UPLOADWEEKTOTALNOTIFICATIONCOUNT(32825, AttribParserType.INTEGER),
	UPLOADWEEKTOTALWITTNESSIITRACE(32826, AttribParserType.INTEGER),
	VERSIONTIME(193, AttribParserType.INTEGER),
	WIRELINEFLAGS(32827, AttribParserType.INTEGER),
	ZONEID(192, AttribParserType.INTEGER),
	SEVERESPEEDSECONDS(32828, AttribParserType.INTEGER),
	SPEEDINGGRACEPERIOD(32829, AttribParserType.INTEGER),
	COMPANYID(32830, AttribParserType.INTEGER),
	EVENTCODEINT(32831, AttribParserType.INTEGER),
    RHA_ENDTIME(32858, AttribParserType.INTEGER),

	DEVICEID(32900, AttribParserType.INTEGER, "deviceid"),
	VEHICLEID(32901, AttribParserType.INTEGER, "vehicleid"),
	ACCOUNTID(32902, AttribParserType.INTEGER),
	
	SEATBELTTOPSPEED(8285, AttribParserType.BYTE),
	SEATBELTOUTDISTANCE(16437, AttribParserType.SHORT), //(distance miles x 100)
	HEADLIGHTOFFDISTANCE(16438, AttribParserType.SHORT), // (distance miles x 100)
	NODRIVERDISTANCE(16439, AttribParserType.SHORT), // (distance miles x 100)
	NOTRAILERDISTANCE(16440, AttribParserType.SHORT), // (distance miles x 100)
	RFOFFDISTANCE(16441, AttribParserType.SHORT), // (distance miles x 100)
	
	// DOUBLE 
	MAXLATITUDE(40960, AttribParserType.DOUBLE, "latitude"),
	MAXLONGITUDE(40961, AttribParserType.DOUBLE, "longitude"),
	MINLATITUDE(40962, AttribParserType.DOUBLE),
	MINLONGITUDE(40963, AttribParserType.DOUBLE),
	
	// STRING 
	DATA(24576, AttribParserType.STRING_PREFACED_LENGTH),
	TEXTMESSAGE(24577, AttribParserType.STRING_PREFACED_LENGTH),

	DRIVERSTR(24578, AttribParserType.STRING_FIXED_LENGTH10),
//    DRIVERSTR(24578, AttribParserType.STRING_VAR_LENGTH10),	
	EMUNAMEDEVICE(24579, AttribParserType.STRING_FIXED_LENGTH29),
	EMUNAMETRANSFORM(24580, AttribParserType.STRING_FIXED_LENGTH29),
	FILENAME(24581, AttribParserType.STRING_FIXED_LENGTH32),
//	LOCATION(24582, AttribParserType.STRING_FIXED_LENGTH30),
    LOCATION(24582, AttribParserType.STRING_VAR_LENGTH32),
	REPORTID(24583, AttribParserType.STRING_FIXED_LENGTH4),
	SERVICEID(24584, AttribParserType.STRING_VAR_LENGTH),
	SILICONID(24585, AttribParserType.STRING_FIXED_LENGTH16),
	SMTOOLSFIRMWAREREV(24586, AttribParserType.STRING_FIXED_LENGTH36),
	TRAILERID(24587, AttribParserType.STRING_VAR_LENGTH),
	VIN(24588, AttribParserType.STRING_FIXED_LENGTH18),
	WITNESSID(24589, AttribParserType.STRING_FIXED_LENGTH9),
	OCCUPANTSTR(24590, AttribParserType.STRING_FIXED_LENGTH10),
	EMPID(24591, AttribParserType.STRING_VAR_LENGTH),
	VEHICLENAME(24592, AttribParserType.STRING_VAR_LENGTH30),
	IMEI(24593, AttribParserType.STRING_VAR_LENGTH26),
	MCMID(24594, AttribParserType.STRING_VAR_LENGTH26),
    RHA_DESCRIPTION(24595, AttribParserType.STRING_VAR_LENGTH32), 
    FOB_ID(24597, AttribParserType.STRING_VAR_LENGTH32), 
	
	
	// BINARY 
	DELTAVS(49152, AttribParserType.DELTAVS_AS_STRING),
	DENOMINATOR(49153, AttribParserType.THREE_SHORTS_AS_STRING),
//	IDLETIMES(49154, AttribParserType.BYTE),
	INTERCEPTS(49155, AttribParserType.THREE_SHORTS_AS_STRING),
//	LATLNG3(49156, AttribParserType.BYTE),
//	LATLNG4(49157, AttribParserType.BYTE),
	NUMERATOR(49158, AttribParserType.THREE_SHORTS_AS_STRING),
	ORIENTATION(49159, AttribParserType.THREE_SHORTS_AS_STRING),
//	SENSORAGREEMENT(49160, AttribParserType.BYTE),
	SPEEDDATAHIRES(49161, AttribParserType.BYTE),
	TEMPCOMPDATA(49162, AttribParserType.THREE_SHORTS_AS_STRING),
	TEMPCOMPSAMPLEMAX(49163, AttribParserType.FOUR_SHORTS_AS_STRING),
	TEMPCOMPSAMPLEMIN(49163, AttribParserType.FOUR_SHORTS_AS_STRING),
	TEMPCOMPSLOPE(49164, AttribParserType.THREE_SHORTS_AS_STRING),
//	WITNESSIIARCHIVE(49165, AttribParserType.BYTE),
//	WITNESSIIARCHIVEHEADER(49166, AttribParserType.BYTE),
	ACKDATA(49167, AttribParserType.ACKDATA),
	CRASHTRACE(49168, AttribParserType.BYTEARRAY),
    VSETTINGS(49169, AttribParserType.BYTEARRAY),
    CRASHDATA(49170, AttribParserType.BYTEARRAY);
	
	private static final Map<Integer,Attrib> lookup = new HashMap<Integer,Attrib>();
    private static final Map<String,Attrib> lookupName = new HashMap<String,Attrib>();

	static {
	     for(Attrib a : EnumSet.allOf(Attrib.class)) {
	          lookup.put(a.getCode(), a);
              lookupName.put(a.getFieldName(), a);
	     }
	}

	private int code;
	private AttribParserType attribParserType;
	private String fieldName = "";
	
	private Attrib(int code, AttribParserType attribParserType, String fieldName) {
	     this.code = code;
	     this.attribParserType = attribParserType;
	     this.fieldName = fieldName;
	}

	private Attrib(int code, AttribParserType attribParserType) 
	{
		this(code, attribParserType, "");
	}
	
	public int getCode() { return code; }
	
	public String getCodeString() { return String.valueOf(code); }

	public String getFieldName() 
	{ 
		if (!fieldName.equalsIgnoreCase(""))
			return fieldName;
		return getCodeString();
	}

	
	public static Attrib get(int code) { 
	     return lookup.get(code); 
	}

    public static Attrib getForName(String name) { 
         return lookupName.get(name); 
    }
    
	public AttribParserType getAttribParserType()
	{
		return attribParserType;
	}
	
    /**
     * Converts a HashMap.toString() back to a HashMap
     * @param text
     * @return HashMap<String, String>
     */
	public static Map<String,Object> convertToHashMap(String text){
        Map<String,Object> map = new HashMap<String,Object>();
        Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
        String[] split = p.split(text);
        String strCode = "";
        String strVal = "";
        Object val = null;
        for ( int i=1; i+2 <= split.length; i+=2 ){
            strCode = split[i];
            strVal = split[i+1];

            val = Attrib.parseStringValue(strCode, strVal);
            map.put(strCode,  val);

        }

        return map;
    }

	public static Object parseStringValue(String attribName, String strVal) {
    	Object val = null;
        AttribParser parser = null;
    	Attrib attrib = getForName(attribName);
        if (attrib == null) {
            int code = 0;
            try {
                code = Integer.parseInt(attribName);
            } catch (NumberFormatException e)
            {}
            if (code != 0)    
                parser = getAttribParser(code);
        }    
        else
            parser = AttribParserFactory.getParserForParserType(attrib.getAttribParserType());
    
        if (parser != null) {
            val = parser.parseString(strVal);
        }
        return val;
	}
        
    public static AttribParser getAttribParser(int code)
    {
        AttribParser attribParser = null;

        if (code <= 127)                     
            attribParser = new ByteParser();
        
        if (code >= 128 && 191 >= code) 
            attribParser = new ShortParser();
        
        if (code >= 192 && 254 >= code) 
            attribParser = new IntegerParser();
        
        if (code >= 8000 && code < 9000)
            attribParser = new ByteParser();
        
        if (code >= 16000 && code < 17000)
            attribParser = new ShortParser();
        
        if (code >= 32000 && code < 33000)
            attribParser = new IntegerParser();

        if (code >= 40960 && code < 40964)
            attribParser = new DoubleParser();
        
        return attribParser;
    }

};
