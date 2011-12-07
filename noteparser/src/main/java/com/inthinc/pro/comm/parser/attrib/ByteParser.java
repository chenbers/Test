package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

public class ByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {
		assert(data.length > offset);
		
		attribMap.put(String.valueOf(attrib.getCode()), String.valueOf(data[offset]));
		
		return offset+1;
	}

}
