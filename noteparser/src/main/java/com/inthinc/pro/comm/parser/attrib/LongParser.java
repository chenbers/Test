package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class LongParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

		assert data.length > (offset + 8);
		
		attribMap.put(code, ReadUtil.readLong(data, offset, 8));

		return offset+8;
		
	}

	public Object parseString(String val){
       return Long.parseLong(val);
   }
}
