package com.inthinc.pro.model;

import java.util.HashMap;
import java.util.Map;

public class EventAttr
{
    public static final Map<Integer, String> mapping = new HashMap<Integer, String>();
    static
    {
        // Attribute Id (128->191 have one byte values)
        mapping.put(1, "topSpeed");
        mapping.put(2, "avgSpeed");
        mapping.put(3, "speedLimit");
        mapping.put(4, "avgRPM");           // 100x
        mapping.put(5, "resetReason");
        mapping.put(6, "manualResetReason");
        mapping.put(7, "fwdCmdStatus");
        mapping.put(8, "violationFlags");
        mapping.put(9, "engineTemp");
        mapping.put(10, "fuelLevel");
        mapping.put(11, "throttle");
        mapping.put(12, "GPSSatsSNRCount");
        mapping.put(13, "GPSSatsSNRMin");
        mapping.put(14, "GPSSatsSNRMax");
        mapping.put(15, "DMMMonitorX30");
        mapping.put(16, "DMMMonitorX50");
        mapping.put(17, "DMMMonitorX67");
        mapping.put(18, "DMMMonitorY30");
        mapping.put(19, "DMMMonitorY50");
        mapping.put(20, "DMMMonitorY67");
        mapping.put(24, "severity");
        mapping.put(25, "DMMVersion");
        
        // Attribute Id (128->191 have two byte values)
        mapping.put(129, "distance");
        mapping.put(130, "maxRPM");
        mapping.put(131, "deltaVx");        // 10x
        mapping.put(132, "deltaVy");        // 10x
        mapping.put(133, "deltaVz");        // 10x
        mapping.put(134, "vehicleBattery");
        mapping.put(135, "backupBattery");
        mapping.put(136, "GPSSatsSNRMean100X");
        mapping.put(137, "GPSSatsSNRStddev100X");
        mapping.put(138, "GPSLockTime");
        mapping.put(148, "boundaryId");
        mapping.put(148, "MPG");            // 10x

        // Attribute id (192->254) have four byte values)
        mapping.put(192, "zoneID");        // 10x
        mapping.put(193, "firmwareVersion");
        mapping.put(194, "fwdCmdId");
        mapping.put(195, "fwdCmdCommand");
        mapping.put(196, "fwdCmdError");

        mapping.put(219, "lowIdle");
        mapping.put(220, "highIdle");
        mapping.put(222, "RFID0");
        mapping.put(223, "RFID1");

        // Attribute Id (255) has a stringId byte,  followed by a null terminate string.
        mapping.put(255, "string");
        

    }
    
    public static String getFieldName(Integer key)
    {
        return mapping.get(key);
    }
    
    public static Integer getKey(String fieldName)
    {
        for (Integer key : mapping.keySet())
        {
            if (mapping.get(key).equals(fieldName))
                return key;
        }
        
        return 0;
    }
    
}
