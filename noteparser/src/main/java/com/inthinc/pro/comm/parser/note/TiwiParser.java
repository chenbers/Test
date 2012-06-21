package com.inthinc.pro.comm.parser.note;

import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.attrib.LatLongParser;
import com.inthinc.pro.comm.parser.util.ReadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiwiParser implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(TiwiParser.class);

	public Map<String, Object> parseNote(byte[] data)
	{
		HashMap<String, Object> attribMap = new HashMap<String, Object>();

		parseHeader(data, attribMap);
		int offset = 16;
		int attrID = 0;
		int attrVal = 0;
		while (offset < data.length)
		{
			attrID = ReadUtil.unsign(data[offset++]);
			
			if (attrID < 128)
			{
				attrVal = ReadUtil.unsign(data[offset++]); 
			}
			else if(attrID < 192)
			{
				attrVal =  ReadUtil.read(data, offset, 2);
				offset += 2;
			}
			else if(attrID < 255)
			{
				attrVal =  ReadUtil.read(data, offset, 4);
				offset += 4;
			}
			else
			{
				attrVal = ReadUtil.unsign(data[offset++]);
				while ((offset < data.length) && (data[offset] != '\0'))
					offset++;
			}
			Attrib attrib = Attrib.get(attrID);
			attribMap.put((attrib == null) ? String.valueOf(attrID) : attrib.getFieldName(), attrVal);
		}
		return attribMap;
	}
	
	private static Map<String, Object> parseHeader(byte[] data, Map<String, Object> attribMap)
	{
//			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
//			attribParser.parseAttrib(data, 2, Attrib.NOTETYPE, attribMap);
		attribMap.put(String.valueOf(Attrib.NOTETYPE.getFieldName()), ReadUtil.unsign(data[0]));

//        	logger.info("Note Type: " + ReadUtil.unsign(data[0]));
		
		AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
		attribParser.parseAttrib(data, 1, Attrib.NOTETIME.getFieldName(), attribMap);
		
		attribMap.put(Attrib.NOTEFLAGS.getFieldName(), ReadUtil.unsign(data[5]));

		attribMap.put(Attrib.NOTEMAPREV.getFieldName(), ReadUtil.unsign(data[6]));
		
		//TO DO: KLUDGE here deciding between version 2 & 3 lat/lng.  Need to fix
		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
		((LatLongParser)attribParser).parseAttrib(data, 7, Attrib.NOTELATLONG.getFieldName(), attribMap, 3);

		attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
		attribParser.parseAttrib(data, 13, Attrib.NOTESPEED.getFieldName(), attribMap);

		//Odometer size/value different between version 2 and 3 notes, so just read it raw
		int odometer =  ReadUtil.read(data, 14, 2);
		attribMap.put(Attrib.NOTEODOMETER.getFieldName(), odometer);
		return attribMap;
	}
}
