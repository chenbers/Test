package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.util.ReadUtil;

public class LongParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {

		assert data.length > (offset + 8);
		
		attribMap.put(attrib.getCode(), new Long(ReadUtil.readLong(data, offset, 8)));

		return offset+8;
		
	}

}
