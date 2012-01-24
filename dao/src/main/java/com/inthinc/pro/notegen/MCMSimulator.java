package com.inthinc.pro.notegen;

import java.util.List;
import java.util.Map;

public interface MCMSimulator
{
        /**
         *  This method is generally only called by the device.   It is included here
         *  for testing purposes so that we can simulate a device adding notifications (events).
         *  
         *  Inserts the notes from the NoteList into the database.
         *  Certain notes are then forwarded to the alertServer.
         * 
         * @param mcmID 
         * @param noteList - list of byte arrays - each contains one event 
         * @return Returns a List of Forward commands, or an integer error 
         */
        
        List<Map> note(String mcmID, List<byte[]> noteList);
        List<Map> notews(String imei, Integer type, List<byte[]> noteList);
}
