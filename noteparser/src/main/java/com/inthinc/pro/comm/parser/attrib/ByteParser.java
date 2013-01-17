package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class ByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {
		assert(data.length > offset);
		
		attribMap.put(code, new Integer(ReadUtil.read(data, offset, 1)));
		
		return offset+1;
	}

	   public Object parseString(String val){
	       return Integer.parseInt(val);
	   }
}
