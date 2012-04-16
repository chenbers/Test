package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class LongParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map attribMap) {

		assert data.length > (offset + 8);
		
		attribMap.put(String.valueOf(code), String.valueOf(ReadUtil.readLong(data, offset, 8)));

		return offset+8;
		
	}

}
