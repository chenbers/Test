package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;
import java.util.Arrays;


public class ByteArrayParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {
		assert(data.length > offset);
		
		attribMap.put(code, Arrays.copyOfRange(data, offset, data.length));
		
		return offset+1;
	}

	   public Object parseString(String val){
	       return val.getBytes();
	   }

}
