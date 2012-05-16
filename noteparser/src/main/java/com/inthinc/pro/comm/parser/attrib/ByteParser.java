package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class ByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map<Integer, Object> attribMap) {
		assert(data.length > offset);
		
		attribMap.put(code, new Byte((byte)ReadUtil.read(data, offset, 1)));
		
		return offset+1;
	}

}
