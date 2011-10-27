package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.util.ReadUtil;

public class IntegerParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {

		assert data.length > (offset + 4);
		
		attribMap.put(attrib.getCode(), new Integer(ReadUtil.read(data, offset, 4)));

		return offset+4;
	}

}
