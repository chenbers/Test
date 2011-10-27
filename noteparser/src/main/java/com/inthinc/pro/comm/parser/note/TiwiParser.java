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

	public Map parseNote(byte[] data, int noteTypeCode)
	{
		HashMap attribMap = new HashMap();

		parseHeader(data, attribMap);
		int offset = 16;
		int attrID = 0;
		int attrVal = 0;
		while (offset < data.length)
		{

			attrID = ReadUtil.unsign(data[offset++]);
			
			if (attrID < 128) 
				attrVal = data[offset++];
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

			attribMap.put(new Integer(attrID), String.valueOf(attrVal));
		}
		return attribMap;
	}
	
	private static Map parseHeader(byte[] data, Map attribMap)
	{
		{
//			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
//			attribParser.parseAttrib(data, 2, Attrib.NOTETYPE, attribMap);
			
			attribMap.put(Attrib.NOTETYPE.getCode(), ReadUtil.unsign(data[0]));

//        	logger.info("Note Type: " + ReadUtil.unsign(data[0]));
			
			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
			attribParser.parseAttrib(data, 1, Attrib.NOTETIME, attribMap);
			
			attribMap.put(Attrib.NOTEFLAGS.getCode(), ReadUtil.unsign(data[5]));

			attribMap.put(Attrib.NOTEMAPREV.getCode(), ReadUtil.unsign(data[6]));
			
			//TO DO: KLUDGE here deciding between version 2 & 3 lat/lng.  Need to fix
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
			((LatLongParser)attribParser).parseAttrib(data, 7, Attrib.NOTELATLONG, attribMap, 3);
	
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
			attribParser.parseAttrib(data, 13, Attrib.NOTESPEED, attribMap);

			//Odometer size/value different between version 2 and 3 notes, so just read it raw
			short odometer = (short) ReadUtil.read(data, 14, 2);
			attribMap.put(Attrib.NOTEODOMETER.getCode(), new Short(odometer));
		}
		return attribMap;
	
	}
	
	
}
