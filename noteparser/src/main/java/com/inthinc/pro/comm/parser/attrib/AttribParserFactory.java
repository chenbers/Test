package com.inthinc.pro.comm.parser.attrib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttribParserFactory {

	private static AttribParser BYTE_PARSER = new ByteParser();
	private static AttribParser SHORT_PARSER = new ShortParser();
	private static AttribParser INTEGER_PARSER = new IntegerParser();
	private static AttribParser INTEGER_SKIP_PARSER = new IntegerSkipParser();
	private static AttribParser LONG_PARSER = new LongParser();
	private static AttribParser DOUBLE_PARSER = new DoubleParser();
	private static AttribParser SHORTS_AS_STRING_PARSER = new ShortsAsStringParser();
	private static AttribParser DELTAVS_AS_STRING_PARSER = new DeltaVsAsStringParser();
	private static AttribParser STRING_PREFACED_LENGTH_PARSER = new StringPrefacedLengthParser();
	private static AttribParser STRING_FIXED_LENGTH_PARSER = new StringFixedLengthParser();
	private static AttribParser STRING_VAR_LENGTH_PARSER = new StringVarLengthParser();
	private static AttribParser ODOMETER_PARSER = new OdometerParser();
	private static AttribParser NOTESFLAGS_PARSER = new NoteFlagsParser();
	private static AttribParser LATLONG_PARSER = new LatLongParser();
	private static AttribParser GPS_LOCK_FLAG_PARSER = new GpsLockFlagParser();
	private static AttribParser ACKDATA_PARSER = new AckDataParser();
    private static AttribParser BYTE_ARRAY_PARSER = new ByteArrayParser();
    private static AttribParser DELTAV_PARSER = new DeltaVParser();

	private static Logger logger = LoggerFactory.getLogger(AttribParserFactory.class.getName());
	
    public static AttribParser getParserForParserType(AttribParserType parserType)
    {
        if (parserType.equals(AttribParserType.BYTE))
            return BYTE_PARSER;
        
        if (parserType.equals(AttribParserType.SHORT))
            return SHORT_PARSER;

        if (parserType.equals(AttribParserType.ODOMETER))
            return ODOMETER_PARSER;

        if (parserType.equals(AttribParserType.INTEGER))
                return INTEGER_PARSER;
        if (parserType.equals(AttribParserType.INTEGER_SKIP))
            return INTEGER_SKIP_PARSER;
        
        if (parserType.equals(AttribParserType.LONG))
            return LONG_PARSER;

        if (parserType.equals(AttribParserType.DOUBLE))
            return DOUBLE_PARSER;
        
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH4))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH9))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH10))
            return STRING_VAR_LENGTH_PARSER;            
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH11))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH20))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH26))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH30))
        	return STRING_VAR_LENGTH_PARSER;        	
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH32))
            return STRING_VAR_LENGTH_PARSER;            
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH60))
            return STRING_VAR_LENGTH_PARSER;            

        
        if (parserType.equals(AttribParserType.STRING_PREFACED_LENGTH))
        	return STRING_PREFACED_LENGTH_PARSER;
        
        if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH4))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH9))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH10))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH16))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH17))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH18))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH29))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH30))	
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH32))
        	return STRING_FIXED_LENGTH_PARSER;        	
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH36))
        	return STRING_FIXED_LENGTH_PARSER;        	
		    	
		    	
		if (parserType.equals(AttribParserType.THREE_SHORTS_AS_STRING))	
			return SHORTS_AS_STRING_PARSER;
		
		if (parserType.equals(AttribParserType.FOUR_SHORTS_AS_STRING))
			return SHORTS_AS_STRING_PARSER;

		if (parserType.equals(AttribParserType.DELTAVS_AS_STRING))	
			return DELTAVS_AS_STRING_PARSER;
		
		if (parserType.equals(AttribParserType.LATLONG))
			return LATLONG_PARSER;
		if (parserType.equals(AttribParserType.GPS_LOCK_FLAG))
			return GPS_LOCK_FLAG_PARSER;
		
		if (parserType.equals(AttribParserType.ACKDATA))
			return ACKDATA_PARSER;
        
		if (parserType.equals(AttribParserType.NOTEFLAGS))
			return NOTESFLAGS_PARSER;

        if (parserType.equals(AttribParserType.BYTEARRAY))
            return BYTE_ARRAY_PARSER;
		
        if (parserType.equals(AttribParserType.DELTAV))
            return DELTAV_PARSER;

        logger.info("Unrecognized parser type: " + parserType);
        return null;
    }

}
