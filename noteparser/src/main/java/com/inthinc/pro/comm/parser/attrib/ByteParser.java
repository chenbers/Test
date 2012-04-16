package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class ByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map attribMap) {
		assert(data.length > offset);
		
		attribMap.put(String.valueOf(code), String.valueOf(ReadUtil.read(data, offset, 1)));
		
		return offset+1;
	}

}
