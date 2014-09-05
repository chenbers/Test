package com.inthinc.pro.comm.parser.note;

import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.attrib.ByteArrayParser;
import com.inthinc.pro.comm.parser.attrib.ByteArrayPrefacedParser;
import com.inthinc.pro.comm.parser.attrib.ByteParser;
import com.inthinc.pro.comm.parser.attrib.DeltaVsAsStringParser;
import com.inthinc.pro.comm.parser.attrib.DoubleParser;
import com.inthinc.pro.comm.parser.attrib.IntegerParser;
import com.inthinc.pro.comm.parser.attrib.LatLongParser;
import com.inthinc.pro.comm.parser.attrib.ShortParser;
import com.inthinc.pro.comm.parser.attrib.StringFixedLengthParser;
import com.inthinc.pro.comm.parser.attrib.StringVarLengthParser;
import com.inthinc.pro.comm.parser.attrib.ThreeByteParser;
import com.inthinc.pro.comm.parser.util.ReadUtil;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotebcParser4 extends NotebcParser implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotebcParser4.class);
	
	protected final static int ATTR_EXTENDED_2_BYTE_SENDING_1_BYTE = 246;
	protected final static int ATTR_EXTENDED_4_BYTE_SENDING_1_BYTE = 247;
	protected final static int ATTR_EXTENDED_4_BYTE_SENDING_2_BYTE = 248;
	protected final static int ATTR_EXTENDED_4_BYTE_SENDING_3_BYTE = 249;
	protected final static int ATTR_EXTENDED_1_BYTE = 250;
	protected final static int ATTR_EXTENDED_2_BYTE = 251;
	protected final static int ATTR_EXTENDED_4_BYTE = 252;
	protected final static int ATTR_EXTENDED_STRING = 253;
	protected final static int ATTR_EXTENDED_DATA = 254;
	protected final static int BYTE_OFFSET = 8192;
	protected final static int SHORT_OFFSET = 16384;
	protected final static int INTEGER_OFFSET = 32768;
	protected final static int STRING_OFFSET = 24576;
	protected final static int BINARY_OFFSET = 49152;

	
	public Map<String, Object> parseNote(byte[] data)
	{
        Map<String, Object> attribMap = new HashMap<String, Object>();
		try {
    		parseHeader(data, attribMap);
    //        logger.debug("attribMap: " + attribMap);
    		
    		int offset = getHeaderLength();
			int attribCode = 0;
			int attribType = 0;
			int attribOffset = 0;
    		while ((offset + 1) < data.length)
    		{
    			AttribParser parser = null;
    			attribType = ReadUtil.read(data, offset++, 1);
    			
    			if (attribType < 246) {
    				attribCode = attribType;
        			Attrib attrib = Attrib.get(attribCode);
        			if (attrib == null)
        				parser = Attrib.getAttribParser(attribCode);			
        			else {
        			    parser = AttribParserFactory.getParserForParserType(attrib.getAttribParserType());
        			}
    			}	
    			else {
        			attribOffset = ReadUtil.read(data, offset++, 1);
    				switch (attribType) {
	    				case ATTR_EXTENDED_2_BYTE_SENDING_1_BYTE:
	    					attribCode = SHORT_OFFSET + attribOffset;
	    					parser = new ByteParser();
	    					break;
	    				case ATTR_EXTENDED_4_BYTE_SENDING_1_BYTE:
	    					attribCode = INTEGER_OFFSET + attribOffset;
	    					parser = new ByteParser();
	    					break;
	    				case ATTR_EXTENDED_4_BYTE_SENDING_2_BYTE:
	    					attribCode = INTEGER_OFFSET + attribOffset;
	    					parser = new ShortParser();
	    					break;
	    				case ATTR_EXTENDED_4_BYTE_SENDING_3_BYTE:
	    					attribCode = INTEGER_OFFSET + attribOffset;
	    					parser = new ThreeByteParser();
	    					break;
	    				case ATTR_EXTENDED_1_BYTE:
	    					attribCode = BYTE_OFFSET + attribOffset;
	    					parser = new ByteParser();
	    					break;
	    				case ATTR_EXTENDED_2_BYTE:
	    					attribCode = SHORT_OFFSET + attribOffset;
	    					parser = new ShortParser();
	    					break;
	    				case ATTR_EXTENDED_4_BYTE:
	    					attribCode = INTEGER_OFFSET + attribOffset;
	    					parser = new IntegerParser();
	    					break;
	    				case ATTR_EXTENDED_STRING:
	    					attribCode = STRING_OFFSET + attribOffset;
	    					parser = new StringVarLengthParser();
	    					break;
	    				case ATTR_EXTENDED_DATA:
	    					attribCode = BINARY_OFFSET + attribOffset;
	    					if (attribCode == Attrib.DELTAVS.getCode()) {
	    						offset++; //skip the array length byte
	    						parser = new DeltaVsAsStringParser();
	    					}	
	    					else 	
	    						parser = new ByteArrayPrefacedParser();
	    					break;
    				}
    			}
    
    			Attrib attrib = Attrib.get(attribCode);
    			if (parser != null)
    			{
    				offset = parser.parseAttrib(data, offset, (attrib == null) ? String.valueOf(attribCode) : attrib.getFieldName(), attribMap);
    				if (attrib != null && attrib == Attrib.DISTANCE) {
    				    Attrib.adjustWaysmartDistance(attribMap); 
    				}
    			}
    			else
    			{
    				logger.info("Parser for code " + attribCode + " is not defined");
    				break;
    			}
    		}
		} catch (Throwable e){
            logger.error("Parser error: " + e);
		}
		return attribMap;
	}

	protected int getHeaderLength() {
		return 18;
	}

	protected Map<String, Object> parseHeader(byte[] data, Map<String, Object> attribMap)
	{

		AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
		attribParser.parseAttrib(data, 0, Attrib.NOTETYPE.getFieldName(), attribMap);

		attribParser = new ByteParser(); 
		attribParser.parseAttrib(data, 1, Attrib.NOTEFLAGS.getFieldName(), attribMap);
		
		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
		attribParser.parseAttrib(data, 2, Attrib.NOTETIME.getFieldName(), attribMap);
		

		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
		((LatLongParser)attribParser).parseAttrib(data, 6, Attrib.NOTELATLONG.getFieldName(), attribMap, 3);

		int odometer = ReadUtil.read(data, 12, 4);
		attribMap.put(Attrib.NOTEODOMETER.getFieldName(), odometer);

		attribParser = new ShortParser(); 
		attribParser.parseAttrib(data, 16, Attrib.NOTIFICATION_ENUM.getFieldName(), attribMap);

		return attribMap;
	}
}
