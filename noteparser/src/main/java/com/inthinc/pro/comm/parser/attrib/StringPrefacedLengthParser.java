package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class StringPrefacedLengthParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

	    Integer length = (Integer) attribMap.get(Attrib.TEXTLENGTH.getFieldName());
	    if (length == null) {
	        length = ReadUtil.read(data, offset, 2);
	        offset += 2;
	    }    
	    assert(data.length > offset + length);
		
		attribMap.put(code, new String(data, offset, length));

		return offset+length;
	}

    public Object parseString(String val){
        return val;
    }
}

