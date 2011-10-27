package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.DataConstants;

public class GpsLockFlagParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {
		assert(data.length > offset);
		
		Boolean gpsLockFlag = new Boolean((data[offset] & DataConstants.SAT_FLAG_GPSLOCK) == DataConstants.SAT_FLAG_GPSLOCK);

		attribMap.put(attrib.getCode(), new Byte(data[offset]));
		
		return offset+1;
	}

}
