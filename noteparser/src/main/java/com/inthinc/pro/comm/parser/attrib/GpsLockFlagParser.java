package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;


public class GpsLockFlagParser implements AttribParser {
	public static final int SAT_FLAG_GPSLOCK 						= 0x01;

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {
		assert(data.length > offset);
		
		Boolean gpsLockFlag = new Boolean((data[offset] & SAT_FLAG_GPSLOCK) == SAT_FLAG_GPSLOCK);

		attribMap.put(attrib.getCode(), new Byte(data[offset]));
		
		return offset+1;
	}

}
