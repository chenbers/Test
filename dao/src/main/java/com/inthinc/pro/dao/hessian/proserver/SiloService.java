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
  
    List<Map<String, Object>> getVehiclesByGroupID(Integer groupID) throws ProDAOException;

    List<Map<String, Object>> getVehiclesByGroupIDDeep(Integer groupID) throws ProDAOException;

    Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException;

    Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException;

    Map<String, Object> clrVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException;

    
    // ------- Person  ----------
    Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException;

 // ?? TODO: should backend support this one    
    Map<String, Object> deletePerson(Integer personID) throws ProDAOException;

    Map<String, Object> getPerson(Integer personID) throws ProDAOException;

    Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException;


    // ------- AlertContact  ----------
    
    Map<String, Object> createAlertCon(Integer acctID, Map<String, Object> alertContactMap) throws ProDAOException;

    Map<String, Object> getAlertCon(Integer userID) throws ProDAOException;

    Map<String, Object> updateAlertCon(Integer userID, Map<String, Object> alertContactMap) throws ProDAOException;

    
    // ------- Driver  ----------
    Map<String, Object> createDriver(Integer personID, Map<String, Object> driverMap) throws ProDAOException;

    Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> getDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> getDriverByPersonID(Integer personID) throws ProDAOException;

    Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException;

    List<Map<String, Object>> getDriversByGroupID(Integer groupID) throws ProDAOException;
    
    List<Map<String, Object>> getDriversByGroupIDDeep(Integer groupID) throws ProDAOException;

    
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
    List<Map<String, Object>> getDriversNearLoc(Integer groupID, Integer numof, Double lat, Double lng);

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
    List<Map<String, Object>> getDriverNote(Integer driverID, Integer startDate, Integer endDate, Integer includeForgiven, Integer types[]);
    
    List<Map<String, Object>> getVehicleNote(Integer vehicleID, Integer startDate, Integer endDate, Integer includeForgiven, Integer types[]);
    
    List<Map<String, Object>> getNoteByMiles(Integer driverID, Integer milesBack, Integer types[]);
    
    List<Map<String, Object>> getVehicleNoteByMiles(Integer vehicleID, Integer milesBack, Integer types[]);

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
    List<Map<String, Object>> getTrips(Integer id, Integer reqType, Integer startDate, Integer endDate) throws ProDAOException;

    /**
     * @param id 
     *       driverID or vehicleID
     * @param reqType  
     *          1=driver, 2=vehicle
     * @return
     *      trip map
     */
    Map<String, Object> getLastTrip(Integer id, Integer reqType) throws ProDAOException;


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

    
    // -------------------------    Messages   -----------------------------------------------
    List<Map<String, Object>> getMessages(Integer deliveryMethodType);
    
    // --------------central service -----------------------    
    
    List<Map<String, Object>> getAllAcctIDs();
    
    Map<String, Object> getNextSilo();
    
    List<Map<String, Object>> getRoles();
    
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
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getRedFlags(Integer groupID) throws ProDAOException;

    


    // Methods related to the RedFlagAlert type
    Map<String, Object> deleteRedFlagAlert(Integer redFlagAlertID) throws ProDAOException;

    Map<String, Object> getRedFlagAlert(Integer redFlagAlertID) throws ProDAOException;

    Map<String, Object> updateRedFlagAlert(Integer redFlagAlertID, Map<String, Object> redFlagAlertMap) throws ProDAOException;

    Map<String, Object> createRedFlagAlert(Integer acctID, Map<String, Object> redFlagAlertMap) throws ProDAOException;

    List<Map<String, Object>> getRedFlagAlertsByAcctID(Integer accountID);

    // Methods related to the ZoneAlert type
    Map<String, Object> deleteZoneAlert(Integer zoneAlertID) throws ProDAOException;

    Map<String, Object> getZoneAlert(Integer zoneAlertID) throws ProDAOException;

    Map<String, Object> updateZoneAlert(Integer zoneAlertID, Map<String, Object> zoneAlertMap) throws ProDAOException;

    Map<String, Object> createZoneAlert(Integer acctID, Map<String, Object> zoneAlertMap) throws ProDAOException;

    List<Map<String, Object>> getZoneAlertsByAcctID(Integer accountID);

    Map<String, Object> deleteZoneAlertsByZoneID(Integer zoneID);
}
