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
        mapping.put(24, "severity");

        // Attribute Id (128->191 have two byte values)
        mapping.put(129, "distance");
        mapping.put(130, "maxRPM");
        mapping.put(131, "deltaVx");        // 10x
        mapping.put(132, "deltaVy");        // 10x
        mapping.put(133, "deltaVz");        // 10x

        // Attribute id (192->254) have four byte values)
        mapping.put(192, "zoneID");        // 10x


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
