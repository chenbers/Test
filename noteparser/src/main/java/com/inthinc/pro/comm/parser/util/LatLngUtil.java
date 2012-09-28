package com.inthinc.pro.comm.parser.util;

//import com.inthinc.pro.model.LatLng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LatLngUtil {
	private static Logger logger = LoggerFactory.getLogger(LatLngUtil.class);
	
	
	public static byte[] encodeLng(double lng)
	{
		byte[] position_encoded_bytes = new byte[3];
		long position_encoded;
		double position;
		if (lng < 0.0) { // west
			position = (lng + 360.0) / 360.0;
		} else { // east
			position = lng / 360.0;
		}

		position_encoded = (long)(position * 0x00ffffff) & 0x00ffffff;
		position_encoded = position_encoded & 0x00ffffff;
		position_encoded_bytes[0] = (byte)((position_encoded & 0x00ff0000) >> 16);
		position_encoded_bytes[1] = (byte)((position_encoded & 0x0000ff00) >> 8);
		position_encoded_bytes[2] = (byte)(position_encoded & 0x000000ff);
		
		return position_encoded_bytes;
	}

	public static byte[] encodeLat(double lat)
	{
		byte[] position_encoded_bytes = new byte[3];
		long position_encoded;
		double position;
		position = -(lat - 90.0) / 180.0;
		position_encoded = (long) (position * 0x00ffffff) & 0x00ffffff;
		position_encoded = position_encoded & 0x00ffffff;
		position_encoded_bytes[0] = (byte)((position_encoded & 0x00ff0000) >> 16);
		position_encoded_bytes[1] = (byte)((position_encoded & 0x0000ff00) >> 8);
		position_encoded_bytes[2] = (byte)(position_encoded & 0x000000ff);
		
		return position_encoded_bytes;
	}
	
/*	
	public static LatLng readLatLng(byte[] data)
	{
		double latitude = 0.0;
		double longitude = 0.0;
		int version = data[2];
		
		if (version == 2)
		{
			int hexLatitude;
			int hexLongitude;
			hexLatitude = ReadUtil.read(data, 8, 3);
			hexLongitude = ReadUtil.read(data, 11, 3);
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
			hexLatitude = ReadUtil.read(data, 9, 4);
			hexLongitude = ReadUtil.read(data, 13, 4);
			
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
		LatLng latLng = new LatLng(latitude, longitude);
		
		return latLng;
	}
*/
}
	
