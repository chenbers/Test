package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class StringPrefacedLengthParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map attribMap) {

		int length = ReadUtil.read(data, offset, 2);
		assert(data.length > offset + 2 + length);
		
		attribMap.put(String.valueOf(code), new String(data, offset+2, (length-1)));

		return offset+2+length;
	}
}

