package com.inthinc.device.emulation.interfaces;

import java.util.List;
import java.util.Map;

import com.inthinc.device.hessian.tcp.ProDAOException;



public interface MCMService extends HessianService
{
	Integer crash(String mcmID, List<byte[]> crashDataList) throws ProDAOException;

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
    
    
    /**
     * request: "audioUpdate", IMEI, HessianMap:<br />
     * "hardwareVersion"   integer Application version (This is really the firmware version)<br />
     * "fileVersion"   integer File number (1-8) from forward command<br />
     * "formatVersion" integer unused<br />
     * "moreInformation"   unused<br />
     * "productVersion" int unused but 1=Tiwi, 2=WaySmart, 3=TiwiPro R71, 4=unused, 5=TiwiPro R74<br />
     * "locale" string  (e.g. en_US, en_CA, ro_RO)<br />
     * "startByte" integer starting byte for incremental download 0=first byte.<br />
     * "endByte" integer ending byte for incremental downloads (eg. 4095 if the file is 4096 bytes).<br />
     * response: <br />
     * error: Integer error number - error if version is current or newer than what is on portal<br />
     * OR<br />
     * success: HessianMap handled by audioFileHandler<br />
     * v=<int audio index><br />
     * f=Binary <audio file><br />
     * l=<int size of the file in bytes> <br />
     * 
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> audioUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     * request: "tiwiUpdate", IMEI, HessianMap:<br />
     * "hardwareVersion"   Application version<br />
     * "fileVersion"   Firmware version from forward command<br />
     * "formatVersion" unused<br />
     * "moreInformation"   unused<br />
     * response: <br />
     * error: Integer error number - error if version is current or newer than what is on portal<br />
     * OR<br />
     * success: HessianMap handled by dotaFileHandler<br />
     * f=Binary <firmware file><br />
     * 
     * 
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> tiwiproUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     *   request: "witnessUpdate", IMEI, HessianMap:<br />
     *   "hardwareVersion"   Application version<br />
     *   "fileVersion"   Witness firmware version from forward command<br />
     *   "formatVersion" unused<br />
     *   "moreInformation"   unused<br />
     *   response: <br />
     *   error: Integer error number<br />
     *   OR<br />
     *   success: HessianMap handled by witnessFileHandler<br />
     *   f=Binary for tiwi.hex<br />
     *
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> witnessUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     * request: "emuUpdate", IMEI, HessianMap:<br />
     * "hardwareVersion"   Application version<br />
     * "fileVersion"   EMU file version<br />
     * "formatVersion" unused<br />
     * "moreInformation"   MD5 hash of existing EMU file (32 digit hexadecimal)<br />
     * response: <br />
     * error: Integer error number<br />
     * OR<br />
     * success: HessianMap handled by emuFileHandler<br />
     * n=String Filename of EMU file<br />
     * v=String MD5 hash of EMU file (32 digit hexadecimal)<br />
     * =String Vin rule used to select the EMU file<br />
     * m=String Make of vehicle used to select the EMU file<br />
     * d=String Model of vehicle used to select the EMU file<br />
     * y=Integer Year of vehicle used to select the EMU file<br />
     * f=Binary <EMU file><br />
     * 
     * 
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> emuUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     *   request: "zoneUpdate", IMEI, HessianMap:<br />
     *   "hardwareVersion"   Application version<br />
     *   "fileVersion"   Zones version<br />
     *   "formatVersion" zone file format version (1)<br />
     *   "moreInformation"   unused<br />
     *   response: <br />
     *   error: Integer error number<br />
     *   OR<br />
     *   success: HessianMap handled by zonesFileHandler<br />
     *   f=Binary zone data<br />
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> zoneUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     * sbsUpdate(mcmID, map{)<br />
     * Fetches the latest exceptions for the specified map file.<br />
     * Returns a mapfile map with the updated mapfile <br />
     * as a binary object, the filename as a string, and the version<br />
     * as an integer<br />
     * or an integer error.<br />
     * <br />
     * request: "sbsUpdate", IMEI, HessianMap:<br />
     * "hardwareVersion"   Application version<br />
     * "fileVersion"   exception map identifier from forward command<br />
     * "formatVersion" Speed file version number (3 as of June 2008)<br />
                        This is the baselineID of the baseline maps.<br />
                        The format version is controlled at the server level.<br />
                        For a forward command with exception map identifier==0,<br />
                        we send the sbsUpdate request to make sure the server has the <br />
                        baselineID of the maps on this particular Tiwi. <br />
     * "moreInformation"   unused<br />
     * response: <br />
     * error: Integer error number<br />
     * OR<br />
     * success: HessianMap handled by speedFileHandler<br />
     * v=<int file version>, <br />
     * d1=String <dir1 name>, <br />
     * d2=String <dir2 name>, <br />
     * n=String <filename>, <br />
     * f=Binary <map file><br />
     * 
     * @param mcmID
     * @param map
     * @return
     */
    Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map);
    
    
    /**
     * sbsCheck(mcmid, baselineID, list[ map{ f=filename, v=version }])<br />
     * Check the version of each specified map vs latest<br />
     * and return a list of mapID's that need updates<br />
     * or an integer error code.<br />
     *                        
     * @param mcmID
     * @param baselineID
     * @param mapList
     * @return
     */
    List<Map<String, Object>> sbsCheck(String mcmID, int baselineID, List<Map<String, Object>> mapList);
    
    List<Map<String, Object>> checkSbsSubscribed(String mcmID, Map<String, Object> map);
    
    Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map);
    
    Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map);
    
    List<Map<String, Object>> checkSbsEdit(String mcmID, List<Map<String, Object>> map);
}
