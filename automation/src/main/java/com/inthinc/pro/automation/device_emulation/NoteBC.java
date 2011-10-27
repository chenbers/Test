package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.utils.AutomationNumberManager;

public class NoteBC {
    
    
    
    private class ShardNote{
        private int nType;
        private int nVersion;
        private int nTime;
        private int heading;
        private int sats;
        private float fLongitude;
        private float fLatitude;
        private int nSpeed;
        private int nOdometer;
        private int nSpeedLimit;
        private int nLinkID;
        private int nBoundaryID;
        private int nDriverID;
        
        public Map<DeviceTypes, Integer> attrs;
        
        /***
         * Pack the values into a note.
         * 1-type
         * 1-version
         * 4-time
         * 2-flags
         * 4-lat
         * 4-lng
         * 1-speed
         * 1-speedLimit
         * 4-linkID
         * 4-odometer
         * 2-boundaryID
         * 4-driverID
         * ---  
         * 32 byte header, followed by attribute ID/value pairs...
         ***/
        private byte[] Package(){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            //Headers  Convert the value to an integer, then pack it as a byte in the stream
            AutomationNumberManager.intToByte(bos, nType, 1);
            AutomationNumberManager.intToByte(bos, nVersion, 1);
            AutomationNumberManager.intToByte(bos, nTime, 4);
            AutomationNumberManager.intToByte(bos, AutomationNumberManager.concatenateTwoInts(heading, sats), 1);
            AutomationNumberManager.intToByte(bos, AutomationNumberManager.encodeLat(fLatitude), 3);
            AutomationNumberManager.intToByte(bos, AutomationNumberManager.encodeLng(fLongitude), 3);
            AutomationNumberManager.intToByte(bos, nSpeed, 1);
            AutomationNumberManager.intToByte(bos, nSpeedLimit, 1);
            AutomationNumberManager.intToByte(bos, nLinkID, 4);
            AutomationNumberManager.intToByte(bos, nOdometer, 4);
            AutomationNumberManager.intToByte(bos, nBoundaryID, 2);
            AutomationNumberManager.intToByte(bos, nDriverID, 4);
            
            NoteManager.encodeAttributes(bos, attrs);
            
            return bos.toByteArray();
        }
    }
    
}
