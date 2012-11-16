package com.inthinc.pro.model;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;


public class HazardTest {
    String testDataForVin = "003C000000360643FCE7CD3BFF1F6F509008085093FC88007400650073007400200066007700640063006D006400200066006F0072002000760069006E"; //"FF000003F1"; //"001A000000350448EE8CCA87023EDD508FE828509167200074006500730074";
    
    @Test
    public void ensureThatPacketSizeWasCalculatedCorrectly(){
        Hazard testHazard = new Hazard();
        testHazard.setHazardID(12);
        testHazard.setAccountID(1);
        testHazard.setType(HazardType.ROADRESTRICTIONS_HEIGHT.getCode());
        testHazard.setDescription("Description String si the laksdjf");
        testHazard.setDriverID(1);
        testHazard.setLatitude(1.1);
        testHazard.setLongitude(2.2);
        testHazard.setRadiusMeters(10);
        testHazard.setStartTime(new Date());
        testHazard.setEndTime(new Date());
        byte [] testArray2 = testHazard.toByteArray();
        ByteBuffer wrapped = ByteBuffer.wrap(testArray2);
        short packetSize2 = wrapped.getShort();
        System.out.println("packetSize2: "+packetSize2);
        System.out.println("testArray2.length: "+testArray2.length);
        assertTrue(packetSize2 == testArray2.length);
    }
    
    public void dateTest(){
        long m_timeStart = 1351636020;
        long m_timeEnd= 1383172020;
        System.out.println("m_timeStart: "+new Date(m_timeStart));
        System.out.println("m_timeEnd: "+new Date(m_timeEnd));
    }
    private int daysInAYear = 365;
    private int hoursInADay = 24;
    private int minutesInAnHour = 60;
    private int secondsInAMinute = 60;
    private int secondsInOneYear = 1  * daysInAYear * hoursInADay * minutesInAnHour * secondsInAMinute;
    private int secondsInTwoYears = 2 * daysInAYear * hoursInADay * minutesInAnHour * secondsInAMinute;
    @Test
    public void periodTest_howManySecondsInAYear(){
        DateTime startTime = new DateTime();
        Date newEndTime = startTime.plus(Period.years(1)).toDate();
        System.out.println("startTime: "+startTime);
        System.out.println("endTime: "+newEndTime);
        System.out.println(newEndTime.getTime() - startTime.getMillis());

        assertEquals((newEndTime.getTime() - startTime.getMillis())/1000 , secondsInOneYear);
        newEndTime = startTime.plus(Period.years(2)).toDate();
        assertEquals((newEndTime.getTime() - startTime.getMillis())/1000, secondsInTwoYears);
    }
    
    @Test
    public void periodTest_howManySecondsInALeapYear(){
        DateTime firstDayOfLeapYear = new DateTime(2012, 1, 1, 0, 0, 0, 0);
        Date newEndTime = firstDayOfLeapYear.plus(Period.years(1)).toDate();
        System.out.println("startTime: "+firstDayOfLeapYear);
        System.out.println("endTime: "+newEndTime);
        System.out.println(newEndTime.getTime() - firstDayOfLeapYear.getMillis());

        assertEquals((newEndTime.getTime() - firstDayOfLeapYear.getMillis())/1000 , secondsInOneYear);
        newEndTime = firstDayOfLeapYear.plus(Period.years(2)).toDate();
        assertEquals((newEndTime.getTime() - firstDayOfLeapYear.getMillis()) , secondsInTwoYears);
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
