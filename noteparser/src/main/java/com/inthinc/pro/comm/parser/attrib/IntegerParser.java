package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class IntegerParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

		assert data.length > (offset + 4);
		
		attribMap.put(code, ReadUtil.read(data, offset, 4));

		return offset+4;
	}

	public Object parseString(String val){
       return Integer.parseInt(val);
	}
}
