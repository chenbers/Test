package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class OdometerParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map<Integer, Object> attribMap) {
		//Called for version 2 notes only.
		assert data.length > (offset + 3);
		
		Long odometer = (long) ReadUtil.read(data, offset, 3);
		odometer = odometer * 100L; //Multiply by 100 to match Version 3 notes.
		attribMap.put(code, odometer);

		return offset+3;
	}

}
