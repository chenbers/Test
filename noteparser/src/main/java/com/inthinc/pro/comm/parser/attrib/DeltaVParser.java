package com.inthinc.pro.comm.parser.attrib;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.comm.parser.util.ReadUtil;


public class DeltaVParser implements AttribParser {
    private static Logger logger = LoggerFactory.getLogger(DeltaVParser.class);


	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {


	    assert data.length >= (offset + 2);

	    int intVal = ReadUtil.read(data, offset, 2);

		if (intVal > 60000) {
            intVal--;
		    intVal = ~intVal;
		    intVal = intVal & 0x0000FFFF;
		    intVal = intVal*-1;
		}
		attribMap.put(code, new Integer(intVal));

		return offset+2;
	}

   public Object parseString(String val){
       return Integer.parseInt(val);
   }
}
