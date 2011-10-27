package com.inthinc.pro.comm.parser.util;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadUtil {
	private static Logger logger = LoggerFactory.getLogger(ReadUtil.class);
	
	public static short toShort(byte byte1, byte byte2)
	{
	    return (short) ((byte2 << 8) | (byte1 << 0));
	}
	
	public static int read(byte[] data, int startPos, int length)
	{
		int intVal = 0x00000000;
		for (int i = 0; i < length; i++)
		{
			intVal = intVal << 8;
			intVal = intVal | (data[startPos + i] & 0x000000FF);
		}
		return intVal;
	}

	public static int readSigned(byte[] data, int startPos, int length)
	{
		int intVal = 0x00000000;
		for (int i = 0; i < length; i++)
		{
			intVal = intVal << 8;
			if (i == 0)
				intVal = intVal | (data[startPos + i]);
			else
				intVal = intVal | (data[startPos + i] & 0x000000FF);
		}
		return intVal;
	}
	
	public static long readLong(byte[] data, int startPos, int length)
	{
		long longVal = 0x0000000000000000;
		for (int i = 0; i < length; i++)
		{
			longVal = longVal << 8;
			longVal = longVal | (data[startPos + i] & 0x00000000000000FF);
		}
		return longVal;
	}

	protected static String readTime(byte[] data, int startPos, int length)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		long time = readSigned(data, startPos, length);
		time = time * 1000;
		return df.format(new Time((long)(time)));
	}

	
	public static double readDouble (byte[] arr, int start) {
		long longVal = readLong(arr, start, 8);
		return Double.longBitsToDouble(longVal);
	}	
	
	public static int unsign(byte b)
	{
		
		return (0x000000FF & ((int)b));
	}


	/**
	 * Convert a byte array into a string.  This is a helper function since the
	 * new String(data, offset, length) function doesn't work exactly like I would
	 * expect in the way it deals with the null terminator.
	 *
	 * @param data
	 * @param offset
	 * @param length
	 * @return String referenced by data
	 */
	public static String createString(byte data[], int offset, int length)
	{
		// create a new string, but chop off the string on the first null
		if (data.length < offset + length) {
			length = data.length - offset;
		}
		for (int i = offset; i < offset + length; i++) {
			if (data[i] == 0) {
				// end of string
				length = i - offset + 1;
				break;
			}
		}
		String str = new String();
		if (length > 0) {
			try {
				str = new String(data, offset, length, "UTF-8");
				str = str.trim();
			}
			catch ( UnsupportedEncodingException e)
			{
			   logger.info("Convert a byte array into a string: " + e);
			}
		}

		return str;
	}
	
	public static byte[] convertIntToBytes(int intData)
	{
        byte[] dataBytes = new byte[4];
        dataBytes[3] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[2] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[1] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[0] = (byte) (intData & 0x000000FF);
        return dataBytes;
	}

	public static byte[] convertLongToBytes(long data) {
		return new byte[] {
			(byte)((data >> 56) & 0xff),
			(byte)((data >> 48) & 0xff),
			(byte)((data >> 40) & 0xff),
			(byte)((data >> 32) & 0xff),
			(byte)((data >> 24) & 0xff),
			(byte)((data >> 16) & 0xff),
			(byte)((data >> 8) & 0xff),
			(byte)((data >> 0) & 0xff),
		};
	}
	
}
