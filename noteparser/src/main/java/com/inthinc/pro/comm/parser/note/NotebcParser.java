package com.inthinc.pro.comm.parser.note;

import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.attrib.LatLongParser;
import com.inthinc.pro.comm.util.ReadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotebcParser implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotebcParser.class);

	public Map parseNote(byte[] data, int noteTypeCode)
	{
		logger.debug("NotebcParser.parseNote called:");
		
		HashMap attribMap = new HashMap();

		int offset = 24;
		parseHeader(data, attribMap);
		
		while ((offset + 2) < data.length)
		{
			int attribCode = ReadUtil.read(data, offset, 2);
			offset += 2;
			
			Attrib attrib = Attrib.get(attribCode);
			
			if (attrib != null)
			{
				AttribParser parser = AttribParserFactory.getParserForParserType(attrib.getAttribParserType());
				offset = parser.parseAttrib(data, offset, attrib, attribMap);
			}
			else
			{
				logger.error("Attribute for code " + attribCode + " is not defined");
				break;
			}
		}

		return attribMap;
	}
	
	private static Map parseHeader(byte[] data, Map attribMap)
	{
		{
			
			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
			attribParser.parseAttrib(data, 0, Attrib.NOTETYPE, attribMap);

			//Skip version at data[1]
			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
			attribParser.parseAttrib(data, 2, Attrib.NOTETIME, attribMap);
			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTEFLAGS.getAttribParserType()); 
			attribParser.parseAttrib(data, 6, Attrib.NOTEFLAGS, attribMap);

			//TO DO: KLUDGE here deciding between version 2 & 3 lat/lng.  Need to fix
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
			((LatLongParser)attribParser).parseAttrib(data, 8, Attrib.NOTELATLONG, attribMap, 4);
	
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
			attribParser.parseAttrib(data, 16, Attrib.NOTESPEED, attribMap);

			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEEDLIMIT.getAttribParserType()); 
			attribParser.parseAttrib(data, 17, Attrib.NOTESPEEDLIMIT, attribMap);

			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.LINKID.getAttribParserType()); 
			attribParser.parseAttrib(data, 18, Attrib.LINKID, attribMap);

			//Odometer size/value different between version 2 and 3 notes, so just read it raw
			int odometer = ReadUtil.read(data, 22, 4);
			attribMap.put(Attrib.NOTEODOMETER.getCode(), new Long(odometer));

			attribParser = AttribParserFactory.getParserForParserType(Attrib.BOUNDARYID.getAttribParserType()); 
			attribParser.parseAttrib(data, 26, Attrib.BOUNDARYID, attribMap);

			attribParser = AttribParserFactory.getParserForParserType(Attrib.DRIVERID.getAttribParserType()); 
			attribParser.parseAttrib(data, 28, Attrib.DRIVERID, attribMap);
		}
		return attribMap;
	
	}
	

}
