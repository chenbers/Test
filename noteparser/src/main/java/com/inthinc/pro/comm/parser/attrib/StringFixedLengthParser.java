package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class StringFixedLengthParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, Attrib attrib, Map attribMap) {

		int length = 0;
		AttribParserType parserType = attrib.getAttribParserType();
			
        if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH4))
			length = 4;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH9))
			length = 9;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH10))
			length = 10;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH16))
			length = 16;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH17))
			length = 17;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH18))
			length = 18;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH29))
			length = 29;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH30))	
			length = 30;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH32))
			length = 32;
		if (parserType.equals(AttribParserType.STRING_FIXED_LENGTH36))
			length = 36;
			
		assert(data.length > offset + 2 + length);

		attribMap.put(String.valueOf(attrib.getCode()), ReadUtil.createString(data, offset, length));

		return offset+length;
	}
}

