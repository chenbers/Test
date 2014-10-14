package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

public class LocationParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {
		//DE9833 This special parser deals with and issue on 820/850 where user entered location attrib LOCATION(24582)
		//is defined as var32 on 820 and fixed 30 on 850
		int maxLength = 32;

        int length = 0;
		for (length = 0; data[offset + length] != 0 && length < maxLength; length++);
		
		assert(data.length >= (offset + length));
		
		attribMap.put(code, new String(data, offset, length));

		//We are checking to see if this is a fixed 30.
		if ((data.length >= (offset + length) + 2)  //if we have more data 
				&& data[offset + length + 1] == 0  //and the next 2 bytes=0
				&& data[offset + length + 2] == 0  
				&& data.length >= (offset + 30))   //and there is enough room for this to be a fixed 30
			length = 29;

		return offset+(length+1);
	}

	public Object parseString(String val){
        return val;
    }
}


