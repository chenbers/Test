package com.inthinc.pro.comm.parser.attrib;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AttribParserType {
	BYTE(1),
	SHORT(2),
	INTEGER(3),
	LONG(4),
	DOUBLE(5),

	STRING_VAR_LENGTH(6),	
	STRING_VAR_LENGTH4(7),	
	STRING_VAR_LENGTH9(8),	
    STRING_VAR_LENGTH10(31),
	STRING_VAR_LENGTH11(9),	
	STRING_VAR_LENGTH20(10),	
	STRING_VAR_LENGTH26(11),	
	STRING_VAR_LENGTH30(12),	
    STRING_VAR_LENGTH32(32),    
	
	STRING_PREFACED_LENGTH(20),
	STRING_FIXED_LENGTH4(21),
	STRING_FIXED_LENGTH9(22),
	STRING_FIXED_LENGTH10(23),
	STRING_FIXED_LENGTH16(24),
	STRING_FIXED_LENGTH17(25),
	STRING_FIXED_LENGTH18(26),
	STRING_FIXED_LENGTH29(27),
	STRING_FIXED_LENGTH30(28),	
	STRING_FIXED_LENGTH32(29),
	STRING_FIXED_LENGTH36(30),
	
	
	THREE_SHORTS_AS_STRING(35),	
	FOUR_SHORTS_AS_STRING(36),	
	
	DELTAVS_AS_STRING(40),	
	
	LATLONG(41),
	
	GPS_LOCK_FLAG(42),
	ODOMETER(43),

	ACKDATA(44),
	
	INTEGER_SKIP(45),
	NOTEFLAGS(46),
    BYTEARRAY(47);
	
	private static final Map<Integer,AttribParserType> lookup = new HashMap<Integer,AttribParserType>();

	static {
	     for(AttribParserType pt : EnumSet.allOf(AttribParserType.class))
	          lookup.put(pt.getCode(), pt);
	}

	private int code;
	
	private AttribParserType(int code) {
	     this.code = code;
	}
	
	public int getCode() { return code; }
	
	public static AttribParserType get(int code) { 
	     return lookup.get(code); 
	}
	

}
