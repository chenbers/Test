package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class ShortParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map attribMap) {

		assert data.length > (offset + 2);
		
		attribMap.put(String.valueOf(code), String.valueOf(ReadUtil.read(data, offset, 2)));

		return offset+2;
	}

}
