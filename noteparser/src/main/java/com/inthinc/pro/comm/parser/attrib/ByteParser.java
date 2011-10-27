package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

public class ByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {
		assert(data.length > offset);
		
		attribMap.put(attrib.getCode(), new Byte(data[offset]));
		
		return offset+1;
	}

}
