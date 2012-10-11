package com.inthinc.pro.comm.parser.attrib;

import java.text.DecimalFormat;
import java.util.Map;

import com.inthinc.pro.comm.parser.util.ReadUtil;

public class DeltaVsAsStringParser implements AttribParser {

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {

		int length = 0;
		
		assert data.length > (offset + 4);

//		double DELTA_V_RESOLUTION = .1;
		double DELTA_V_RESOLUTION = 1.0;
		long packedDeltaV = ReadUtil.readLong(data, offset, 4);
		double deltaVX = ((packedDeltaV / 1464100)-600)*DELTA_V_RESOLUTION * -1;  //waysmart sends reverse deltaX, so need to correct with (* -1)
		double deltaVY = (((packedDeltaV / 1210) % 1210)-600)*DELTA_V_RESOLUTION;
		double deltaVZ = ((packedDeltaV % 1210)-600)*DELTA_V_RESOLUTION;
		
		DecimalFormat decimalFormat = new DecimalFormat("###.#");

		String deltaVx = decimalFormat.format(deltaVX);
		String deltaVy = decimalFormat.format(deltaVY);
		String deltaVz = decimalFormat.format(deltaVZ);
		
		String value = "DeltaVX: " + deltaVx + " DeltaVY: " + deltaVy + " DeltaVZ: " + deltaVz;
			
		attribMap.put(code, value);
		
		attribMap.put(Attrib.DELTAVX.getFieldName(), Integer.parseInt(deltaVx));
		attribMap.put(Attrib.DELTAVY.getFieldName(), Integer.parseInt(deltaVy));
		attribMap.put(Attrib.DELTAVZ.getFieldName(), Integer.parseInt(deltaVz));

		return offset+4;
	}

}
