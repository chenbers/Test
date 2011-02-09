    package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface SiloService extends HessianService
{
//--------------------------------------------------------------------------------------------------------
    // --------------cust service -----------------------    

    // ------- Address  ----------
    Map<String, Object> createAddr(Integer acctID, Map<String, Object> addrMap) throws ProDAOException;

    Map<String, Object> getAddr(Integer addrID) throws ProDAOException;
   
    Map<String, Object> updateAddr(Integer addrID, Map<String, Object> addrMap) throws ProDAOException;

    // ------- Account  ----------
    Map<String, Object> createAcct(Integer siloID, Map<String, Object> acctMap) throws ProDAOException;

    Map<String, Object> deleteAcct(Integer acctID) throws ProDAOException;
    
    Map<String, Object> getAcct(Integer acctID) throws ProDAOException;
    
    Map<String, Object> updateAcct(Integer acctID, Map<String, Object> acctMap) throws ProDAOException;

    List<Map<String, Object>> getAccts(Integer siloID) throws ProDAOException;
    
    // ------- Group  ----------
    Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException;

    Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException;

    Map<String, Object> getGroup(Integer groupID) throws ProDAOException;

    Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException;

    List<Map<String, Object>> getGroupsByAcctID(Integer acctID) throws ProDAOException;
    
    List<Map<String, Object>> getGroupsByGroupIDDeep(Integer groupID) throws ProDAOException;
    

    // ------- Device  ----------
    Map<String, Object> createDevice(Integer acctID, Map<String, Object> deviceMap) throws ProDAOException;

    Map<String, Object> deleteDevice(Integer deviceID) throws ProDAOException;

    Map<String, Object> getDevice(Integer deviceID) throws ProDAOException;

    Map<String, Object> updateDevice(Integer deviceID, Map<String, Object> deviceMap) throws ProDAOException;

    List<Map<String, Object>> getDevicesByAcctID(Integer accountID) throws ProDAOException;

    // ------- Vehicle  ----------
    Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException;

    Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException;

    Map<String, Object> getVehicle(Integer vehicleID) throws ProDAOException;

    Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException;
    
    Map<String, Object> getVehicleByDriverID(Integer driverID) throws ProDAOException;
  
    List<Map<String, Object>> getVehiclesByGroupID(Integer groupID) throws ProDAOException;

    List<Map<String, Object>> getVehiclesByGroupIDDeep(Integer groupID) throws ProDAOException;

    Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException;

    Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException;

    Map<String, Object> clrVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException;


    // -!!!! WARNING !!!!  ----------
    // Methods are only available on the dev server to allow tests to generate event's in the past.
    Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID, Long assignTime ) throws ProDAOException;
    Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID, Long assignTime ) throws ProDAOException;
    // ----------------------------

    
    // ------- Person  ----------
    Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException;

 // ?? TODO: should backend support this one    
    Map<String, Object> deletePerson(Integer personID) throws ProDAOException;

    Map<String, Object> getPerson(Integer personID) throws ProDAOException;

    Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException;

    
    // ------- Driver  ----------
    Map<String, Object> createDriver(Integer personID, Map<String, Object> driverMap) throws ProDAOException;

    Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> getDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> getDriverByPersonID(Integer personID) throws ProDAOException;

    Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException;

    List<Map<String, Object>> getDriversByGroupID(Integer groupID) throws ProDAOException;
    
    List<Map<String, Object>> getDriversByGroupIDDeep(Integer groupID) throws ProDAOException;

    List<Long> getRfidsForBarcode(String barcode) throws ProDAOException;
    
    // ------- User  ----------
    Map<String, Object> createUser(Integer personID, Map<String, Object> userMap) throws ProDAOException;

    Map<String, Object> deleteUser(Integer userID) throws ProDAOException;

    Map<String, Object> getUser(Integer userID) throws ProDAOException;

    Map<String, Object> getUserByPersonID(Integer personID) throws ProDAOException;

    Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException;

    List<Map<String, Object>> getUsersByGroupID(Integer groupID);

    List<Map<String, Object>> getUsersByGroupIDDeep(Integer groupID);

    // ------- Forward Commands  ----------
    List<Map<String, Object>> getFwdCmds(Integer deviceID, Integer status);
    
    Map<String, Object> queueFwdCmd(Integer deviceID, Map<String, Object> fwdMap);
    
    Map<String, Object> updateFwdCmd(Integer fwdID,Integer status);
    
    
    // ------------------------- Locations  -----------------------------------------------
    /**
     * @param id 
     *       driverID or vehicleID
     * @param reqType  
     *          1=driver, 2=vehicle
     * @return
     *      map[driverID,vehicleID,time,lat,lng]
     */
    Map<String, Object> getLastLoc(Integer id, Integer reqType);
    /**
     * @param groupID
     * 
     * @param numof
     *          requested number of returned maps
     *          
     * @param lat
     *          latitude to look for
     *          
     * @param lng
     *          longitude to look for
     *       
     * @return
     *      map[driverID,groupID,vehicleID,vType,name,homePhone,workPhone,loc(lat,lng)]
     */
    
    List<Map<String, Object>> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng);

    // ------------------------- Events/Notes  -----------------------------------------------
    
    /**
     * @param driverID
     *          driverID to get notes for
     * @param startDate
     *          start of date range
     * @param endDate
     *          end of date range
     * @param types
     *          list of note/event types (see EventMapper)
     * @return
     */
    Map<String, Object> getNote(Long noteID);
    
    List<Map<String, Object>> getDriverNote(Integer driverID, Long startDate, Long endDate, Integer includeForgiven, Integer types[]);
    
    List<Map<String, Object>> getVehicleNote(Integer vehicleID, Long startDate, Long endDate, Integer includeForgiven, Integer types[]);
    
    // pagination methods
    Map<String, Object> getDriverEventCount(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, List<Map<String, Object>> filterList, Integer types[]);

    List<Map<String, Object>> getDriverEventPage(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, Object> pageParams, Integer types[]);

    /**
     * getMostRecentEvents
     * 
     * @param groupID --
     *            groupID to retrieve events for  (should be a team level group that contains only drivers)
     * @param eventCnt --
     *            max events to retrieve
     * @param types --
     *            valid event types to retrieve
     * @return
     *      List of event maps 
     * @throws ProDAOException
     */
    List<Map<String, Object>> getRecentNotes(Integer groupID, Integer eventCnt, Integer types[]) throws ProDAOException;

    Map<String, Object> forgive(Integer driverID, Long noteID) throws ProDAOException;
    
    Map<String, Object> unforgive(Integer driverID, Long noteID) throws ProDAOException;
    
    Map<String, Object> getNoteNearLoc(Integer driverID, Double lat, Double lng, Long startDT, Long stopDT);
    
    // -------------------------    Trips   -----------------------------------------------
    /**
     * @param id 
     *       driverID or vehicleID
     * @param reqType  
     *          1=driver, 2=vehicle
     * @param startDate  
     *          start of Date range (seconds)
     * @param endDate
     *          end of Date range (seconds)
     * @return
     *      list of trip map
     */
    List<Map<String, Object>> getTrips(Integer id, Integer reqType, Long startDate, Long endDate) throws ProDAOException;

    /**
     * @param id 
     *       driverID or vehicleID
     * @param reqType  
     *          1=driver, 2=vehicle
     * @return
     *      trip map
     */
    Map<String, Object> getLastTrip(Integer id, Integer reqType) throws ProDAOException;
    
    /**
     * @param id 
     *       driverID
     * @param startDate  
     *          start of Date range (seconds)
     * @param endDate
     *          end of Date range (seconds)
     * @return
     *      list of stop map
     */
    public List<Map<String, Object>> getStops(Integer driverID, Long startDate, Long endDate) throws ProDAOException;


    // -------------------------    Table Preferences   -----------------------------------------------
    List<Map<String, Object>> getTablePrefsByUserID(Integer userID) throws ProDAOException;

    public Map<String, Object> deleteTablePref(Integer tablePrefID) throws ProDAOException;

    public Map<String, Object> createTablePref(Integer userID, Map<String, Object> tablePrefMap) throws ProDAOException;

    public Map<String, Object> getTablePref(Integer tablePrefID) throws ProDAOException;

    public Map<String, Object> updateTablePref(Integer tablePrefID, Map<String, Object> tablePrefMap) throws ProDAOException;

    // -------------------------    Zones   -----------------------------------------------
    Map<String, Object> deleteZone(Integer zoneID) throws ProDAOException;

    Map<String, Object> getZone(Integer zoneID) throws ProDAOException;

    Map<String, Object> updateZone(Integer zoneID, Map<String, Object> zoneMap) throws ProDAOException;

    Map<String, Object> createZone(Integer acctID, Map<String, Object> zoneMap) throws ProDAOException;

    List<Map<String, Object>> getZonesByAcctID(Integer accountID);
    
    Map<String, Object> publishZones(Integer accountID);

    
    // -------------------------    Messages   -----------------------------------------------
    List<Map<String, Object>> getMessages(Integer siloID, Integer deliveryMethodType);
    
    // --------------central service -----------------------    
    
    List<Map<String, Object>> getAllAcctIDs();
    
    Map<String, Object> getNextSilo();
    
//    List<Map<String, Object>> getRoles();
    
    List<Map<String, Object>> getRolesByAcctID(Integer acctID) throws ProDAOException;
    
    Map<String,Object> createRole(Integer acctID,Map<String, Object> roleMap) throws ProDAOException;

    Map<String,Object> getRole(Integer roleID) throws ProDAOException;


    Map<String,Object> updateRole(Integer roleID,Map<String, Object> roleMap) throws ProDAOException;

    Map<String,Object> deleteRole(Integer roleID) throws ProDAOException;

    List<Map<String,Object>> getSiteAccessPts();
    
    List<Map<String,Object>> getUsersAccessPts(Integer userID);
    
    List<Map<String, Object>> getSensitivityMaps();
    
    List<Map<String, Object>> getStates();
    
    List<Map<String, Object>> getTimezones();
    
    
    //    used by Portal and custServer to see if the value for
    //    this named data has an ID associated with it.
    //    The names and IDs returned are listed below:
    //    username - userID
    //    email - personID
    //    mcmid - deviceID
    //    vin   - vehicleID
    Map<String, Object> getID(String name, String value);

    
    //--------------------------------------------------------------------------------------------------------

    
// Additional Methods we added    
    
    

    /**
     * getRedFlags -- retrieves all red flags for drivers in the groupID.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @param includeForgiven
     * @return
     * @throws ProDAOException
     */
    // pagination methods
    Map<String, Object> getRedFlagsCount(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, List<Map<String, Object>> filterList);

    List<Map<String, Object>> getRedFlagsPage(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, Object> pageParams);

    // Methods related to the RedFlagAlert type
    Map<String, Object> deleteAlert(Integer redFlagAlertID) throws ProDAOException;

    Map<String, Object> getAlert(Integer redFlagAlertID) throws ProDAOException;

    Map<String, Object> updateAlert(Integer redFlagAlertID, Map<String, Object> redFlagAlertMap) throws ProDAOException;

    Map<String, Object> createAlert(Integer acctID, Map<String, Object> redFlagAlertMap) throws ProDAOException;

    List<Map<String, Object>> getAlertsByAcctID(Integer accountID);
    List<Map<String, Object>> getAlertsByUserID(Integer userID);
    List<Map<String, Object>> getAlertsByUserIDDeep(Integer userID);
    Map<String, Object> deleteAlertsByZoneID(Integer zoneID);

    List<Map<String, Object>>getAlertsByTeamGroupID(Integer groupID);
   // Methods related to the ZoneAlert type
//    Map<String, Object> deleteZoneAlert(Integer zoneAlertID) throws ProDAOException;
//
//    Map<String, Object> getZoneAlert(Integer zoneAlertID) throws ProDAOException;
//
//    Map<String, Object> updateZoneAlert(Integer zoneAlertID, Map<String, Object> zoneAlertMap) throws ProDAOException;
//
//    Map<String, Object> createZoneAlert(Integer acctID, Map<String, Object> zoneAlertMap) throws ProDAOException;
//
//    List<Map<String, Object>> getZoneAlertsByAcctID(Integer accountID);
//    List<Map<String, Object>> getZoneAlertsByUserID(Integer userID);
//    List<Map<String, Object>> getZoneAlertsByUserIDDeep(Integer userID);
//
//    Map<String, Object> deleteZoneAlertsByZoneID(Integer zoneID);
//    
//    List<Map<String,Object>> getTextMsgAlertsByAcctID(Integer acctID);
    List<Map<String,Object>> getTextMsgPage(Integer groupID, Long startDate, Long endDate,  List<Map<String, Object>> filterList, Map<String, Object> pageParams );
    List<Map<String,Object>> getSentTextMsgsByGroupID(Integer groupID, Long startDate, Long endDate);
    Map<String, Object> getTextMsgCount(Integer groupID, Long startDate, Long endDate,  List<Map<String, Object>> filterList);
//    Map<String,Object> createTextMsgAlert(Integer acctID, Map<String,Object> textMsgAlertMap) throws ProDAOException;
    
    // Report Schedules
    
    Map<String, Object> createReportPref(Integer acctID, Map<String, Object> reportPrefMap) throws ProDAOException;

    Map<String, Object> deleteReportPref(Integer reportPrefID) throws ProDAOException;

    Map<String, Object> getReportPref(Integer reportPrefID) throws ProDAOException;
    
    List<Map<String, Object>> getReportPrefsByAcctID(Integer acctID) throws ProDAOException;

    Map<String, Object> updateReportPref(Integer reportPrefID, Map<String, Object> reportPrefMap) throws ProDAOException;
    
    List<Map<String, Object>> getReportPrefsByUserID(Integer userID) throws ProDAOException;
    
    List<Map<String, Object>> getReportPrefsByUserIDDeep(Integer userID) throws ProDAOException;
    
    // Crash Reports
    
    Map<String, Object> createCrash(Integer vehicleID, Map<String, Object> crashReportMap) throws ProDAOException;

    Map<String, Object> deleteCrash(Integer crashReportID) throws ProDAOException;

    Map<String, Object> getCrash(Integer crashReportID) throws ProDAOException;
    
    Map<String, Object> updateCrash(Integer crashID, Map<String, Object> crashReportMap) throws ProDAOException;
    
    List<Map<String, Object>> getCrashes(Integer groupID, Long startDate, Long stopDate, Integer includeForgiven) throws ProDAOException;

    Map<String, Object> forgiveCrash(Integer groupID) throws ProDAOException;
    
    Map<String, Object> unforgiveCrash(Integer groupID) throws ProDAOException;    
    
    // Live fleet map on executive dashboard
    
    List<Map <String, Object>> getDVLByGroupIDDeep(Integer groupID) throws ProDAOException;
    
    // Reports -- pagination methods
    Map<String, Object> getDriverReportCount(Integer groupID, List<Map<String, Object>> filterList);
    List<Map<String, Object>> getDriverReportPage(Integer groupID, Map<String, Object> pageParams);
    
    Map<String, Object> getVehicleReportCount(Integer groupID, List<Map<String, Object>> filterList);
    List<Map<String, Object>> getVehicleReportPage(Integer groupID, Map<String, Object> pageParams);

    Map<String, Object> getDeviceReportCount(Integer groupID, List<Map<String, Object>> filterList);
    List<Map<String, Object>> getDeviceReportPage(Integer groupID, Map<String, Object> pageParams);
    
    Map<String, Object> getIdlingReportCount(Integer groupID, Long startDate, Long endDate, List<Map<String, Object>> filterList);
    List<Map<String, Object>> getIdlingReportPage(Integer groupID, Long startDate, Long endDate, Map<String, Object> pageParams);


    
    // super user methods
    Map<String, Object> setSuperuser(Integer userID);
    Map<String, Object> isSuperuser(Integer userID);
    Map<String, Object> clearSuperuser(Integer userID);
    
    // forward command defs
    List<Map<String, Object>> getFwdCmdDefs();
    Map<String, Object> createFwdCmdDef(Map<String, Object> fwdCmdDefMap);
    Map<String, Object> deleteFwdCmdDef(Integer fwdCmd);
    Map<String, Object> updateFwdCmdDef(Map<String, Object> fwdCmdDefMap);
    
    // Configurator
    List<Map<String, Object>> getSettingDefs();
    Map<String,Object> getVehicleSettings(Integer vehicleID);
    List<Map<String, Object>> getVehicleSettingsByGroupIDDeep(Integer groupID);
    List<Map<String, Object>> getVehicleSettingsHistory(Integer vehicleID, Long startTime, Long endTime);
    Map<String, Object> setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason);
    Map<String, Object> updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason);
    List<Map<String, Object>> getSensitivitySliderValues();
}
