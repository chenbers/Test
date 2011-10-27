package com.inthinc.pro.automation.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

public class AutomationNumberManager {
	private final static Logger logger = Logger.getLogger(AutomationNumberManager.class);
	
	public static Integer extract(String stringWithIntegers) {
		
	    
	    if (stringWithIntegers == null) {
	        return null;
	    }

	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    
	    for (int i = 0; i < stringWithIntegers.length() ; i++) {
	        c = stringWithIntegers.charAt(i);
	        
	        if (Character.isDigit(c)) {
	            strBuff.append(c);
	        }
	    }
	    Integer justTheNumbers = Integer.parseInt(strBuff.toString());
	    logger.debug("String: " + stringWithIntegers + " to Integer: " + justTheNumbers);
	    return justTheNumbers;
	}
	
	public static Integer byteToInt(ByteArrayInputStream bais, int numOfBytes) {
        return byteToLong(bais, numOfBytes).intValue();
    }

    public static Long byteToLong(ByteArrayInputStream bais, int numOfBytes) {
        Long number = 0l;
        for (int shift = 0; numOfBytes-- > 0 && shift < 64; shift += 8) {
            number |= (bais.read() & 0xFF) << shift;
        }
        return number;
    }
    

    public static void intToByte(ByteArrayOutputStream baos, int toAdd, int numOfBytes){
        int size = Byte.SIZE;
        int offset = numOfBytes * size - size;
        for (;offset>=0;offset-=size){
            baos.write((toAdd >> offset) & 0xFF);
        }
    }
    
    public static int encodeLat(double lat){
        double latitude = ( 90.0 - lat ) / 180.0;
        int val  = (int)( latitude  * 0x00FFFFFF );
        return val;
    }
    
    public static int encodeLng(double lng){
        if (lng < 0.0 ){
            lng += 360.0;
        }
        double longitude = ( lng / 360.0 );
        int val = (int)( longitude * 0x00FFFFFF );
        return val;
    }
    
    public static int concatenateTwoInts(int one, int two){
        int value = (int)(  one << 4)  & 0xF0 | two & 0x0F;
        return value;
    }

}
