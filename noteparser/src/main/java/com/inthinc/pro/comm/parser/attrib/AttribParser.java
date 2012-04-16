package com.inthinc.pro.comm.parser.attrib;

import java.util.Map;


public interface AttribParser {
	int parseAttrib(byte[] data, int offset, int code, Map attribMap);
}
