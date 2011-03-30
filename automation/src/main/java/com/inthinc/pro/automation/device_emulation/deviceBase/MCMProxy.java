package com.inthinc.pro.automation.device_emulation.deviceBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MCMProxy{
    
        /**
         *  This method is only called by the device.
         *  
         *  Inserts the notes from the NoteList into the database.
         *  Certain notes are then forwarded to the alertServer.
         * 
         * @param mcmID or imei as a String 
         * @param version device product version
         * @param settings device settings to send
         * @param noteList - list of byte arrays - each contains one event 
         * @return Returns a List of Forward commands, an integer error, or a list of new settings 
         */
        
		ArrayList<Map<String, Object>> note(String mcmID, List<byte[]> noteList);
		ArrayList<Map<String, Object>> dumpSet(String mcmID, Integer version, Map<Integer, String> settings);
		ArrayList<Map<Integer, String>> reqSet(String imei);
}
