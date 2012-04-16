package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.forwardcommand.ForwardCommand;
import com.inthinc.pro.comm.parser.util.ReadUtil;

public class AckDataParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int attribCode, Map attribMap) {
		final int FORWARD_COMMAND_ID_LEN = 8;
		
		//Subtract FORWARD_COMMAND_ID_LEN from data.length and then subtract the ofset to get length of
		//ack data.  Based on Ackdata length use the appropriate parser.
		
		Integer code = (Integer) attribMap.get(Attrib.TYPE_FWDCMD.getCode());
		
		if (code != null)
		{
			ForwardCommand forwardCommand = ForwardCommand.get(code);
			
			if (forwardCommand != null)
			{
				//Look up the forward command and if found use its parser
				AttribParserType parserType = forwardCommand.getParserType();   
				AttribParser parser = AttribParserFactory.getParserForParserType(parserType);

				offset = parser.parseAttrib(data, offset, code, attribMap);
			}
			else
			{
				//Otherwise calculate Ackdata len and parse appropriately
				int ackDataLen = data.length - FORWARD_COMMAND_ID_LEN - offset;
				assert(ackDataLen > 0);
				
				Object value = null;
				switch (ackDataLen)
				{
					case 1:
						value = new Byte(data[offset]);
						offset+=1;
						break;
					case 2:
						value = new Short((short)ReadUtil.read(data, offset, 2));
						offset+=2;
						break;
					case 4:
						value = new Integer(ReadUtil.read(data, offset, 4));
						offset+=4;
						break;
					case 8:
						value = new Long(ReadUtil.readLong(data, offset, 8));
						offset+=8;
						break;
					case 32:
						value = ReadUtil.createString(data, offset, 32);;
						offset+=32;
						break;
					default:
				}
				
				if (value != null)
					attribMap.put(String.valueOf(attribCode), String.valueOf(value));
			}
		}
		

		return offset;
	}

}
