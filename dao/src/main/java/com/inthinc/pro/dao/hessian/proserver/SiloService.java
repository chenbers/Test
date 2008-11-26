package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface SiloService extends HessianService
{

    // Methods related to the Account type
    Map<String, Object> createAcct(Integer siloID, Map<String, Object> acctMap) throws ProDAOException;

    Map<String, Object> getAcct(Integer acctID) throws ProDAOException;

    Map<String, Object> updateAcct(Integer acctID, Map<String, Object> acctMap) throws ProDAOException;

    List<Map<String, Object>> getAccts(Integer siloID) throws ProDAOException;
    
    // Methods related to the Address type
    Map<String, Object> createAddr(Integer acctID, Map<String, Object> addrMap) throws ProDAOException;

    Map<String, Object> getAddr(Integer addrID) throws ProDAOException;
   
    Map<String, Object> updateAddr(Integer addrID, Map<String, Object> addrMap) throws ProDAOException;

    // Methods related to the User type
    Map<String, Object> deleteUser(Integer userID) throws ProDAOException;

    Map<String, Object> getUser(Integer userID) throws ProDAOException;

    Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException;

    Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException;

    Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException;

    Map<String, Object> getUserIDByName(String username) throws ProDAOException;

    Map<String, Object> getUserIDByEmail(String email) throws ProDAOException;

    // Methods related to the Person type
    Map<String, Object> deletePerson(Integer personID) throws ProDAOException;

    Map<String, Object> getPerson(Integer personID) throws ProDAOException;

    Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException;

    Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException;

    /**
     * Retrieves the IDs of all people in the hierarchy for the given group ID--that is, the matching group plus all groups beneath it.
     * 
     * @param groupID
     *            The ID of the group at the top of the hierarchy.
     * @return A list of maps of personIDs.
     */
    List<Map<String, Object>> getPersonIDsInGroupHierarchy(Integer groupID);

    // Methods related to the Vehicle type
    List<Map<String, Object>> getVehiclesByAcctID(Integer acctID) throws ProDAOException;

    List<Map<String, Object>> getVehiclesInGroupHierarchy(Integer groupID) throws ProDAOException;

    Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException;

    Map<String, Object> getVehicle(Integer vehicleID) throws ProDAOException;

    Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException;

    Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException;

    // Methods related to the Group type
    Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException;

    Map<String, Object> getGroup(Integer groupID) throws ProDAOException;

    Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException;

    Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException;

    List<Map<String, Object>> getGroupsByAcctID(Integer acctID) throws ProDAOException;


    // Methods related to the Device type
    List<Map<String, Object>> getDevicesByAcctID(Integer accountID) throws ProDAOException;

    Map<String, Object> deleteDevice(Integer deviceID) throws ProDAOException;

    Map<String, Object> getDevice(Integer deviceID) throws ProDAOException;

    Map<String, Object> updateDevice(Integer deviceID, Map<String, Object> deviceMap) throws ProDAOException;

    Map<String, Object> createDevice(Integer acctID, Map<String, Object> deviceMap) throws ProDAOException;

    // Methods related to Trips
    List<Map<String, Object>> getTrips(Integer driverID, Integer startDate, Integer endDate) throws ProDAOException;

    Map<String, Object> getLastTrip(Integer driverID) throws ProDAOException;
    
    // Methods related to the Driver type
    /**
     * getDrivers -- retrieves all drivers associated to a groupID.
     * 
     * @param groupID
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getAllDrivers(Integer groupID);
    
    Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> getDriver(Integer driverID) throws ProDAOException;

    Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException;

    Map<String, Object> createDriver(Integer acctID, Map<String, Object> driverMap) throws ProDAOException;

    Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException;

    Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException;
    
    /**
     * 
     * @param driverID
     * @return
     * @throws ProDAOException
     */
    
    Map<String, Object> getDriverByID(Integer driverID) throws ProDAOException;

    /**
     * getRedFlags -- retrieves all red flags for drivers in the groupID.
     * 
     * @param groupID
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getRedFlags(Integer groupID) throws ProDAOException;

    Map<String, Object> getVehicleByID(Integer vehicleID) throws ProDAOException;
    
    
    List<Map<String, Object>> getTablePreferencesByUserID(Integer userID) throws ProDAOException;

    public Map<String, Object> deleteTablePreference(Integer tablePrefID) throws ProDAOException;

    public Map<String, Object> createTablePreference(Integer acctID, Map<String, Object> tablePreferenceMap) throws ProDAOException;

    public Map<String, Object> getTablePreference(Integer tablePrefID) throws ProDAOException;

    public Map<String, Object> updateTablePreference(Integer tablePrefID, Map<String, Object> tablePreferenceMap) throws ProDAOException;

    // Methods related to the Zone type
    Map<String, Object> deleteZone(Integer zoneID) throws ProDAOException;

    Map<String, Object> getZone(Integer zoneID) throws ProDAOException;

    Map<String, Object> updateZone(Integer zoneID, Map<String, Object> zoneMap) throws ProDAOException;

    Map<String, Object> createZone(Integer acctID, Map<String, Object> zoneMap) throws ProDAOException;

    /**
     * Retrieves the IDs of all people in the hierarchy for the given group ID--that is, the matching group plus all groups beneath it.
     * 
     * @param groupID
     *            The ID of the group at the top of the hierarchy.
     * @return A list of maps of zoneIDs.
     */
    List<Map<String, Object>> getZoneIDsInGroupHierarchy(Integer groupID);



    // Methods related to the Event type
    /**
     * getMostRecentEvents
     * 
     * @param groupID --
     *            groupID to retrieve events for
     * @param eventCnt --
     *            max events to retrieve
     * @param types --
     *            valid event types to retrieve
     * @return
     * @throws ProDAOException
     */
    List<Map<String, Object>> getMostRecentEvents(Integer groupID, Integer eventCnt, Integer types[]) throws ProDAOException;
    
    
    
    /**
     * Get list of states.
     * Bean: State
     * @return List of Maps Map key/values:
     */
    List<Map<String, Object>> getStates();



}
