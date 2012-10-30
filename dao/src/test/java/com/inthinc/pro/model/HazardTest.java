package com.inthinc.pro.model;

import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import org.junit.Test;


public class HazardTest {
    String testDataForVin = "003C000000360643FCE7CD3BFF1F6F509008085093FC88007400650073007400200066007700640063006D006400200066006F0072002000760069006E"; //"FF000003F1"; //"001A000000350448EE8CCA87023EDD508FE828509167200074006500730074";
    
    @Test
    public void ensureThatPacketSizeWasCalculatedCorrectly(){
        byte[] testArray = hexStringToByteArray(testDataForVin);
        ByteBuffer wrapped = ByteBuffer.wrap(testArray);
        short packetSize = wrapped.getShort();
        System.out.println("packetSize: "+packetSize);
        assertTrue(packetSize == testArray.length);
        
        Hazard testHazard = new Hazard();
        testHazard.setAccountID(1);
        testHazard.setDescription("Description String");
        testHazard.setDriverID(1);
        testHazard.setLatitude(1.1);
        testHazard.setLongitude(2.2);
        testHazard.setRadiusMeters(10);
        testArray = testHazard.toByteArray();
        wrapped = ByteBuffer.wrap(testArray);
        packetSize = wrapped.getShort();
        assertTrue(packetSize == testArray.length);
    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
