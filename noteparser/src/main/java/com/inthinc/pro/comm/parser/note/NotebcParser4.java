package com.inthinc.pro.comm.parser.note;

import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.attrib.ByteParser;
import com.inthinc.pro.comm.parser.attrib.DoubleParser;
import com.inthinc.pro.comm.parser.attrib.IntegerParser;
import com.inthinc.pro.comm.parser.attrib.LatLongParser;
import com.inthinc.pro.comm.parser.attrib.ShortParser;
import com.inthinc.pro.comm.parser.util.ReadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotebcParser4 extends NotebcParser implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotebcParser4.class);

	private static int getHeaderLength() {
		return 19;
	}

	private static Map<String, Object> parseHeader(byte[] data, Map<String, Object> attribMap)
	{
		AttribParser attribParser = new ByteParser(); 
		attribParser.parseAttrib(data, 0, Attrib.NOTEFLAGS.getFieldName(), attribMap);

		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
		attribParser.parseAttrib(data, 1, Attrib.NOTETYPE.getFieldName(), attribMap);

		//Skip version at data[2]
		
		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
		attribParser.parseAttrib(data, 3, Attrib.NOTETIME.getFieldName(), attribMap);
		

		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
		((LatLongParser)attribParser).parseAttrib(data, 7, Attrib.NOTELATLONG.getFieldName(), attribMap, 3);

		int odometer = ReadUtil.read(data, 13, 4);
		attribMap.put(Attrib.NOTEODOMETER.getFieldName(), odometer);

		attribParser = new ShortParser(); 
		attribParser.parseAttrib(data, 17, Attrib.NOTIFICATION_ENUM.getFieldName(), attribMap);

		return attribMap;
	}
}
