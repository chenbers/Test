package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;
import java.util.Arrays;

import com.inthinc.pro.comm.parser.util.ReadUtil;


public class ByteArrayPrefacedParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

        Integer length = new Integer(ReadUtil.read(data, offset, 1));
        offset++;

        if (length == 0)
        	length = data.length - offset;
        	
	    assert(data.length >= (offset + length));
		
		attribMap.put(code, Arrays.copyOfRange(data, offset, offset+length));
		
		return data.length;
	}

	public Object parseString(String val){
		return val.getBytes();
	}
}
