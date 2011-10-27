package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class IntegerSkipParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {

		assert data.length > (offset + 4);

		return offset+4;
	}

}
