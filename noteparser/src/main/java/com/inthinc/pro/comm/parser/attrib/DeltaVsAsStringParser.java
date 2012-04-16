package com.inthinc.pro.comm.parser.attrib;

import java.text.DecimalFormat;
import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class DeltaVsAsStringParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, int code, Map attribMap) {

		int length = 0;
		
		assert data.length > (offset + 4);

		double DELTA_V_RESOLUTION = .1;
		int packedDeltaV = ReadUtil.read(data, offset, 4);
		double deltaVX = ((packedDeltaV / 1464100)-600)*DELTA_V_RESOLUTION;
		double deltaVY = (((packedDeltaV / 1210) % 1210)-600)*DELTA_V_RESOLUTION;
		double deltaVZ = ((packedDeltaV % 1210)-600)*DELTA_V_RESOLUTION;
		
		DecimalFormat decimalFormat = new DecimalFormat("###.##");

		String deltaVx = decimalFormat.format(deltaVX);
		String deltaVy = decimalFormat.format(deltaVY);
		String deltaVz = decimalFormat.format(deltaVZ);
		
		String value = "DeltaVX: " + deltaVx + " DeltaVY: " + deltaVy + " DeltaVZ: " + deltaVz;
			
		attribMap.put(String.valueOf(code), value);
		
		attribMap.put(String.valueOf(Attrib.DELTAVX.getCode()), deltaVx);
		attribMap.put(String.valueOf(Attrib.DELTAVY.getCode()), deltaVy);
		attribMap.put(String.valueOf(Attrib.DELTAVZ.getCode()), deltaVz);

		return offset+4;
	}

}
