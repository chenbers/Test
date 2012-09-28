package com.inthinc.pro.comm.parser.attrib;

import java.text.DecimalFormat;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.inthinc.pro.comm.parser.util.ReadUtil;
//import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.comm.parser.util.LatLngUtil;

public class LatLongParser implements AttribParser {
	private static Logger logger = LoggerFactory.getLogger(LatLongParser.class);

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap) {
		return parseAttrib(data, offset, code, attribMap, 3);
	}

	public int parseAttrib(byte[] data, int offset, String code, Map<String, Object> attribMap, int dataLen) {

		
		assert data.length > (offset + 6);

		Double latitude = 0.0;
		Double longitude = 0.0;
		
		if (dataLen == 3)
		{
			int hexLatitude;
			int hexLongitude;
			hexLatitude = ReadUtil.read(data, offset, dataLen);
			hexLongitude = ReadUtil.read(data, offset + dataLen, dataLen);
			String hexValue = Integer.toHexString(hexLongitude);
			int longCode = Integer.parseInt(hexValue, 16);
			longitude = (longCode/((double)0xFFFFFF)) * 360.;
			if (longitude > 180.0) {
				longitude = longitude - 360.;
			}
	
			hexValue = Integer.toHexString(hexLatitude);
			int latCode = Integer.parseInt(hexValue, 16);
			latitude = 90. - (latCode/ ((double)0xFFFFFF)) * 180.;
		}
		else
		{
			
			
			long hexLatitude = 0;
			long hexLongitude = 0;
			hexLatitude = ReadUtil.read(data, offset, dataLen);
			hexLongitude = ReadUtil.read(data, offset+dataLen, dataLen);
			
			String hexValue = Integer.toHexString((int)hexLongitude);
			
			long longCode = Long.parseLong(hexValue, 16);
			
			longitude = (longCode/((double) 4294967295L)) * 360.;
			if (longitude > 180.0) {
				longitude = longitude - 360.;
			}

			hexValue = Integer.toHexString((int)hexLatitude);
			
			long latCode = Long.parseLong(hexValue, 16);
			
			latitude = 90. - (latCode/ ((double)4294967295L)) * 180.;
		}
			
		logger.debug("latitude: " + latitude);
		logger.debug("longitude: " + longitude);
		
		DecimalFormat df = new DecimalFormat("###.#####");
		latitude = Double.parseDouble(df.format(latitude));
		longitude = Double.parseDouble(df.format(longitude));
		
		logger.debug("latitude2: " + latitude);
		logger.debug("longitude2: " + longitude);
//		LatLng latLng = new LatLng(latitude, longitude);
		
		attribMap.put(Attrib.MAXLATITUDE.getFieldName(), latitude);
		attribMap.put(Attrib.MAXLONGITUDE.getFieldName(), longitude);

		return offset+6;
	}
	
}
