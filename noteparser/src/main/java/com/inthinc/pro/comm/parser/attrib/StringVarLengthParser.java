package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

public class StringVarLengthParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

		int maxLength = 500;
		AttribParserType parserType = Attrib.get(Integer.parseInt(code)).getAttribParserType();
			
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH4))
        	maxLength = 4;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH9))
        	maxLength = 9;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH10))
            maxLength = 10;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH11))
        	maxLength = 11;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH20))
        	maxLength = 20;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH26))
        	maxLength = 26;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH30))
        	maxLength = 30;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH32))
            maxLength = 32;
        if (parserType.equals(AttribParserType.STRING_VAR_LENGTH60))
            maxLength = 60;

        int length = 0;
		for (length = 0; data[offset + length] != 0 && length < maxLength; length++);
		
		assert(data.length > offset + length);
		
		attribMap.put(code, new String(data, offset, length));

		return offset+(length+1);
	}

	public Object parseString(String val){
        return val;
    }
}


