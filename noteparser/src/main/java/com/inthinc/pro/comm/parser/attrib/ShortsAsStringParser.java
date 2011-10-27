package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.util.ReadUtil;

public class ShortsAsStringParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {

		int length = 0;
		if (attrib.getAttribParserType().equals(AttribParserType.THREE_SHORTS_AS_STRING))
			length = 3;

		if (attrib.getAttribParserType().equals(AttribParserType.FOUR_SHORTS_AS_STRING))
			length = 4;
		
		assert data.length > (offset + (2*length));

		String value = "";
		for(int i=0; i < 4; i++)
		{
			value = value + ReadUtil.readSigned(data, offset+(i*2), 2); 
			
			if (i < 3)
				value = value + ",";
		
		}
			
		attribMap.put(attrib.getCode(), value);

		return offset+(2*length);
	}

}
