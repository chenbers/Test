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

public class NotewsParser3 implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotewsParser3.class);

	public Map parseNote(byte[] data, int noteTypeCode)
	{
		HashMap attribMap = new HashMap();
		NoteType noteType = NoteType.get(noteTypeCode);

		if (noteType != null)
		{
			int offset = noteType.isStrippedNote() ? 2 : 33;
			parseHeader(data, noteType, attribMap);
			
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
		}
		else
			logger.info("Note parser for type " + noteTypeCode + " is not defined.");

		return attribMap;
	}
	
	private static Map parseHeader(byte[] data, NoteType noteType, Map attribMap)
	{
		{
//			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
//			attribParser.parseAttrib(data, 2, Attrib.NOTETYPE, attribMap);
			
			attribMap.put(Attrib.NOTETYPE.getCode(), noteType.getCode());
			
			if (!noteType.isStrippedNote())
			{
				AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
				attribParser.parseAttrib(data, 3, Attrib.NOTETIME, attribMap);
				
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTEFLAGS.getAttribParserType()); 
				attribParser.parseAttrib(data, 7, Attrib.NOTEFLAGS, attribMap);

				//TO DO: KLUDGE here deciding between version 2 & 3 lat/lng.  Need to fix
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
				((LatLongParser)attribParser).parseAttrib(data, 9, Attrib.NOTELATLONG, attribMap, 4);
		
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
				attribParser.parseAttrib(data, 17, Attrib.NOTESPEED, attribMap);

				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEEDLIMIT.getAttribParserType()); 
				attribParser.parseAttrib(data, 18, Attrib.NOTESPEEDLIMIT, attribMap);

				
				attribParser = AttribParserFactory.getParserForParserType(Attrib.LINKID.getAttribParserType()); 
				attribParser.parseAttrib(data, 19, Attrib.LINKID, attribMap);

				//Odometer size/value different between version 2 and 3 notes, so just read it raw
				int odometer = ReadUtil.read(data, 23, 4);
				attribMap.put(Attrib.NOTEODOMETER.getCode(), new Long(odometer));

				attribParser = AttribParserFactory.getParserForParserType(Attrib.BOUNDARYID.getAttribParserType()); 
				attribParser.parseAttrib(data, 27, Attrib.BOUNDARYID, attribMap);

				attribParser = AttribParserFactory.getParserForParserType(Attrib.DRIVERID.getAttribParserType()); 
				attribParser.parseAttrib(data, 29, Attrib.DRIVERID, attribMap);
			}
		}
		return attribMap;
	
	}
	

}
