package com.inthinc.pro.automation.interfaces;

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
        
        List<Map<String, Object>> note(String mcmID, List<byte[]> noteList);
        List<Map<String, Object>> notebc(String mcmID, int connectType, List<byte[]> noteList);
        List<Map<String, Object>> notews(String mcmID, int connectType, List<byte[]> noteList);
		List<Map<String, Object>> dumpSet(String mcmID, Integer version, Map<Integer, String> settings);
		List<Map<Integer, String>> reqSet(String imei);
		Map<String, Object> audioUpdate(String mcmID, Map<String, Object> map);
		Map<String, Object> tiwiproUpdate(String mcmID, Map<String, Object> map);
		Map<String, Object> witnessUpdate(String mcmID, Map<String, Object> map);
		Map<String, Object> emuUpdate(String mcmID, Map<String, Object> map);
		Map<String, Object> zoneUpdate(String mcmID, Map<String, Object> map);
		Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map);
		List<Map<String, Object>> sbsCheck(String mcmID, int baselineID, List<Map<String, Object>> mapList);
//		Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map);//Obsolete
		List<Map<String, Object>> checkSbsSubscribed(String mcmID, Map<String, Object> map);
		Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map);
        Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map);
        List<Map<String, Object>> checkSbsEdit(String mcmID, List<Map<String, Object>> map);
}
