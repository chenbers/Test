package com.inthinc.pro.device;

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
         * @param mcmID 
         * @param noteList - list of byte arrays - each contains one event 
         * @return Returns a List of Forward commands, or an integer error 
         */
        
		ArrayList<Map> note(String mcmID, List<byte[]> noteList);
}
