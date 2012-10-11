package com.inthinc.device.emulation.interfaces;

import java.util.List;
import java.util.Map;

import com.inthinc.device.hessian.tcp.HessianException;

public interface SbsHessianInterface extends HessianService {


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
    Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map) throws HessianException;
    
    
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
    List<Map<String, Object>> sbsCheck(String mcmID, int baselineID, List<Map<String, Object>> mapList) throws HessianException;
    
    List<Map<String, Object>> checkSbsSubscribed(String mcmID, Map<String, Object> map) throws HessianException;
    
    Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map) throws HessianException;
    
    Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map) throws HessianException;
    
    List<Map<String, Object>> checkSbsEdit(String mcmID, List<Map<String, Object>> map) throws HessianException;


    List<Map<String, Object>> checkSbsEditNG(String mcmid, List<Map<String, Object>> maps);


    Map<String, Object> getSbsEditNG(String mcmid, Map<String, Object> map);


    List<Map<String, Object>> checkSbsSubscribedNG(String mcmid, Map<String, Object> map); 
}
