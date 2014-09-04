package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class ThreeByteParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {
		assert data.length >= (offset + 3);
		Integer value = ReadUtil.read(data, offset, 3);
		attribMap.put(code, value);

		return offset+3;
	}

   public Object parseString(String val){
       return Integer.parseInt(val);
   }

}
