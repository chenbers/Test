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

public class NotebcParser implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotebcParser.class);

	public Map parseNote(byte[] data)
	{
		
		HashMap attribMap = new HashMap();
		parseHeader(data, attribMap);
		
		int offset = 32;
		while ((offset + 2) < data.length)
		{
			AttribParser parser = null;
			int attribCode = ReadUtil.read(data, offset, 2);
			logger.info("attribCode: " + attribCode);
			offset += 2;
			
			Attrib attrib = Attrib.get(attribCode);

			
			if (attrib == null)
				parser = getAttribParser(attribCode);			
			else
				parser = AttribParserFactory.getParserForParserType(attrib.getAttribParserType());
			
				
			if (parser != null)
			{
				offset = parser.parseAttrib(data, offset, attribCode, attribMap);
			}
			else
			{
				logger.error("Parser for code " + attribCode + " is not defined");
				break;
			}
		}

		return attribMap;
	}
	
	private static Map parseHeader(byte[] data, Map attribMap)
	{
		{
			
			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
			attribParser.parseAttrib(data, 0, Attrib.NOTETYPE.getCode(), attribMap);

			//Skip version at data[1]
			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
			attribParser.parseAttrib(data, 2, Attrib.NOTETIME.getCode(), attribMap);
			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTEFLAGS.getAttribParserType()); 
			attribParser.parseAttrib(data, 6, Attrib.NOTEFLAGS.getCode(), attribMap);

			//TO DO: KLUDGE here deciding between version 2 & 3 lat/lng.  Need to fix
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 
			((LatLongParser)attribParser).parseAttrib(data, 8, Attrib.NOTELATLONG.getCode(), attribMap, 4);
	
			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
			attribParser.parseAttrib(data, 16, Attrib.NOTESPEED.getCode(), attribMap);

			attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEEDLIMIT.getAttribParserType()); 
			attribParser.parseAttrib(data, 17, Attrib.NOTESPEEDLIMIT.getCode(), attribMap);

			
			attribParser = AttribParserFactory.getParserForParserType(Attrib.LINKID.getAttribParserType()); 
			attribParser.parseAttrib(data, 18, Attrib.LINKID.getCode(), attribMap);

			//Odometer size/value different between version 2 and 3 notes, so just read it raw
			int odometer = ReadUtil.read(data, 22, 4);
			attribMap.put(String.valueOf(Attrib.NOTEODOMETER.getCode()), String.valueOf(odometer));

			attribParser = AttribParserFactory.getParserForParserType(Attrib.BOUNDARYID.getAttribParserType()); 
			attribParser.parseAttrib(data, 26, Attrib.BOUNDARYID.getCode(), attribMap);

			attribParser = AttribParserFactory.getParserForParserType(Attrib.DRIVERID.getAttribParserType()); 
			attribParser.parseAttrib(data, 28, Attrib.DRIVERID.getCode(), attribMap);
		}
		return attribMap;
	
	}
	

	private AttribParser getAttribParser(int code)
	{
		AttribParser attribParser = null;

        if (code <= 127)                     
        	attribParser = new ByteParser();
        
        if (code >= 128 && 191 >= code) 
        	attribParser = new ShortParser();
        
        if (code >= 192 && 254 >= code) 
        	attribParser = new IntegerParser();
		
		if (code >= 8000 && code < 9000)
			attribParser = new ByteParser();
		
		if (code >= 16000 && code < 17000)
			attribParser = new ShortParser();
		
		if (code >= 32000 && code < 33000)
			attribParser = new IntegerParser();

		if (code >= 40960 && code < 40964)
			attribParser = new DoubleParser();
		
		return attribParser;
		
	}
}
