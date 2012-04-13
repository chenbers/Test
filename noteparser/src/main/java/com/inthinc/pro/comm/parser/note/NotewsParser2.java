package com.inthinc.pro.comm.parser.note;

import java.util.Map;
import java.util.HashMap;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.util.ReadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotewsParser2 implements NoteParser{

	private static Logger logger = LoggerFactory.getLogger(NotewsParser2.class);

	public Map parseNote(byte[] data)
	{
		HashMap attribMap = new HashMap();
		
		int noteTypeCode = ReadUtil.read(data, 1, 1);
		NoteType noteType = NoteType.get(noteTypeCode);

		if (noteType != null)
		{
			int offset = noteType.isStrippedNote() ? 2 : 20;
			parseHeader(data, noteType, attribMap);
			
			Attrib[] attribs = noteType.getAttribs();
	
			if (attribs != null) 
			{
				for(int i = 0; i < attribs.length; i++)
				{
					AttribParser parser = AttribParserFactory.getParserForParserType(attribs[i].getAttribParserType());
					offset = parser.parseAttrib(data, offset, attribs[i], attribMap);
				}
				
				parseExtraData(data, offset, noteType, attribMap);
			}
		}
		else
			logger.info("Note parser for type " + noteTypeCode + " is not defined.");

		return attribMap;
	}
	
	private static Map parseHeader(byte[] data, NoteType noteType, Map attribMap)
	{
		if (noteType != null)
		{
//			AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETYPE.getAttribParserType()); 
//			attribParser.parseAttrib(data, 1, Attrib.NOTETYPE, attribMap);
			attribMap.put(String.valueOf(Attrib.NOTETYPE.getCode()), String.valueOf(noteType.getCode()));
		
			if (!noteType.isStrippedNote())
			{
				AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTETIME.getAttribParserType()); 
				attribParser.parseAttrib(data, 3, Attrib.NOTETIME, attribMap);
				
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTELATLONG.getAttribParserType()); 

				attribParser.parseAttrib(data, 8, Attrib.NOTELATLONG, attribMap);
		
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTESPEED.getAttribParserType()); 
				attribParser.parseAttrib(data, 14, Attrib.NOTESPEED, attribMap);
				
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTEODOMETER.getAttribParserType()); 
				attribParser.parseAttrib(data, 15, Attrib.NOTEODOMETER, attribMap);
				
				attribParser = AttribParserFactory.getParserForParserType(Attrib.NOTEDURATION.getAttribParserType()); 
				attribParser.parseAttrib(data, 18, Attrib.NOTEDURATION, attribMap);
			}

		}
		return attribMap;
	}
	

	private static Map parseExtraData(byte[] data, int offset, NoteType noteType, Map attribMap)
	{
		final int EXTRA_DATA_TEMPERATURE = 0x01;
		final int EXTRA_DATA_IDLETIME = 0x02;
		final int EXTRA_DATA_DRIVERID = 0x04;

		byte extraData;
		
		try {
			if (offset < data.length && !noteType.isStrippedNote()) {
				extraData = data[offset];
	
				offset++;
	
				if ((extraData & EXTRA_DATA_IDLETIME) == EXTRA_DATA_IDLETIME) {
	
					AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.LOWIDLE2.getAttribParserType()); 
					attribParser.parseAttrib(data, offset, Attrib.LOWIDLE2, attribMap);
					offset += 2;
					logger.info("EXTRADATA_LOWIDLE: " + attribMap.get(Attrib.LOWIDLE2.getCodeString()));
					
					
					attribParser = AttribParserFactory.getParserForParserType(Attrib.HIGHIDLE2.getAttribParserType()); 
					attribParser.parseAttrib(data, offset, Attrib.HIGHIDLE2, attribMap);
					offset += 2;
					logger.info("EXTRADATA_HIGHIDLE: " + attribMap.get(Attrib.HIGHIDLE2.getCodeString()));
				}
	
				if ((extraData & EXTRA_DATA_TEMPERATURE) == EXTRA_DATA_TEMPERATURE)
				{
					//Temperature shouldn't be getting set on devices any longer
					offset += 2;
				}
	
				if ((extraData & EXTRA_DATA_DRIVERID) == EXTRA_DATA_DRIVERID)
				{
					AttribParser attribParser = AttribParserFactory.getParserForParserType(Attrib.DRIVERSTR.getAttribParserType()); 
					attribParser.parseAttrib(data, offset, Attrib.DRIVERSTR, attribMap);
					logger.info("EXTRADATA_DRIVERID: " + attribMap.get(Attrib.DRIVERSTR.getCodeString()));
				}
			}
		} catch(Throwable e)
		{
			logger.error("Error parsing extra data: " + e);
			e.printStackTrace();
		}
		
		return attribMap;
	}

}
