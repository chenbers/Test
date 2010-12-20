package com.inthinc.pro.service.note;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class CrashDataTest {
    
    Logger logger = Logger.getLogger(CrashDataTest.class);
    
    @Test
    public void testCrashData(){
        double latitude = 40.711785;
        double longitude = -111.9771521;
        CrashData crashData = new CrashData(new Date(), latitude,longitude,35, 35, 5000);
        byte[] bytes = crashData.getBytes();
        
        double retrievedLat = decodeLat(bytes);
        double retrievedLng = decodeLng(bytes);
        
        Assert.assertEquals(retrievedLat, latitude,0.00003);
        Assert.assertEquals(retrievedLng, longitude,0.00003);
        
        logger.info("Retrieved Latitude = " + retrievedLat);
        logger.info("Retrieved Longitude = " + retrievedLng);
        
        
    }
    
    public double decodeLat(byte[] data){
        long b32,b24,b16,b08,b;
        
        b32 = (long)unsignedByteToInt(data[4]);
        b24 = (long)unsignedByteToInt(data[5]);
        b16 = (long)unsignedByteToInt(data[6]);
        b08 = (long)unsignedByteToInt(data[7]);
        b = (b32 << 24) | (b24 << 16) | (b16 << 8) | 155;
        
        double latitude = 90 - (b / (new BigDecimal(new BigInteger("FFFFFFFF",16))).doubleValue()) * 180.0;
        
        return latitude;
    }
    
    public double decodeLng(byte[] data){
        long b32,b24,b16,b08,b;
        
        b32 = (long)unsignedByteToInt(data[8]);
        b24 = (long)unsignedByteToInt(data[9]);
        b16 = (long)unsignedByteToInt(data[10]);
        b08 = (long)unsignedByteToInt(data[11]);
        b = (b32 << 24) | (b24 << 16) | (b16 << 8) | b08;
        double longitude = (b / (new BigDecimal(new BigInteger("FFFFFFFF",16))).doubleValue()) * 360.0;
        if (longitude > 180.0)
            longitude = longitude - 360.0;
        
        return longitude;
    }
    
    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

    
}
