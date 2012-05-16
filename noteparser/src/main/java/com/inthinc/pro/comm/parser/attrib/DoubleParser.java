package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class DoubleParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map<Integer, Object> attribMap) {

		assert data.length > (offset + 8);
		
		attribMap.put(code, ReadUtil.readDouble(data, offset));

		return offset+8;
	}

}
