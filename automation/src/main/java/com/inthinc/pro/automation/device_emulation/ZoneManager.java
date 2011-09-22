package com.inthinc.pro.automation.device_emulation;

import java.util.Arrays;

public class ZoneManager {

    public ZoneManager(byte[] zone) {
        int test = unsignedShortToInt(Arrays.copyOfRange(zone, 0, 32));
        System.out.println(test);
        test = unsignedShortToInt(Arrays.copyOfRange(zone, 32, 64));
        System.out.println(test);
    }
    
    public int nextInt(){
        
        return 0;
    }

    /**
     * int File Format version 
     * int File Version (per tiwipro group; incremented with each publish) 
     * int nZones 
     * for each Zone 
     *      u32 zoneID 
     *      u16 number of Vertices 
     *          4*3 minLongitude, minLatitude, maxLongitude, maxLatitude (bounding box) 
     *          nVertices*6 Vertices (3 byte longitude, 3 byte latitude) for each vertex 
     *          u16 number of Attributes 
     *              for each Attribute of this zone 
     *              u8 AttributeID (0-127 for u8, 128-191 for u16, 192-255 for u32) 
     *              u8 AttributeValue (e.g. maxSpeed) 
     *              or u16 
     *              or u32
     * @param attrs
     */
    private void parseAttributes(byte[] attrs) {

    }

    /**
     * Converts a 4 byte array of unsigned bytes to an long
     * 
     * @param b
     *            an array of 4 unsigned bytes
     * @return a long representing the unsigned int
     */
    public static final long unsignedIntToLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        l <<= 8;
        l |= b[3] & 0xFF;
        return l;
    }

    /**
     * Converts a two byte array to an integer
     * 
     * @param b
     *            a byte array of length 2
     * @return an int representing the unsigned short
     */
    public static final int unsignedShortToInt(byte[] b) {
        int i = 0;
        i |= b[0] & 0xFF;
        i <<= 8;
        i |= b[1] & 0xFF;
        return i;
    }

}
