package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class NoteFlagsParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

		assert data.length > (offset + 2);
		
		short flags = (short) ReadUtil.read(data, offset, 2);
		
			
		if ((flags & 0x0F) > 0)
		{
			int head = (flags >> 4) & 0x0F;
			attribMap.put("heading", new Integer(head));
		}
		
		attribMap.put(code, new Integer(flags));

		return offset+2;
	}

}
