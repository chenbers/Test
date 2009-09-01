package com.inthinc.pro.backing.dao.impl;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class SiloServiceImpl implements SiloService {
    @Override
    public Map<String, Object> createAcct(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createAddr(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createDevice(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createDriver(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createGroup(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createPerson(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createRedFlagAlert(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createTablePref(@DaoParam(name = "accountID") Integer userID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createUser(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createVehicle(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createZone(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createZoneAlert(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "ZoneAlert", type = com.inthinc.pro.model.ZoneAlert.class) Map<String, Object> zoneAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteAcct(@DaoParam(name = "accountID") Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteDevice(@DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteDriver(@DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteGroup(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deletePerson(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteRedFlagAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteUser(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteVehicle(@DaoParam(name = "vehicleID") Integer vehicleID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteZone(@DaoParam(name = "zoneID") Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteZoneAlert(@DaoParam(name = "zoneAlertID") Integer zoneAlertID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> deleteZoneAlertsByZoneID(@DaoParam(name = "zoneID") Integer zoneID) {
        return null;
    }

    @Override
    public Map<String, Object> getAcct(@DaoParam(name = "accountID") Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAccts(@DaoParam(name = "siloID") Integer siloID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getAddr(@DaoParam(name = "addrID") Integer addrID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllAcctIDs() {
        return null;
    }

    @Override
    public Map<String, Object> getDevice(@DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getDevicesByAcctID(@DaoParam(name = "accountID") Integer accountID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getDriver(@DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupID(@DaoParam(name = "groupID") Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches (numof) vehicles from this group(deep) nearest to (lat, lng). Returns a list of VehicleLocMap, or an Integer error.")
    public List<Map<String, Object>> getVehiclesNearLoc(@DaoParam(name = "groupID") Integer groupID, @DaoParam(name = "count") Integer numof, @DaoParam(name = "lat") Double lat,
            @DaoParam(name = "lng") Double lng) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getFwdCmds(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "status", inputDesc = "ALL = 0,QUEUED = 1,SENT = 2,RECEIVED=3,ACKNOWLEDGED = 4,UNSUPPORTED = 5,BAD_DATA = 6,REMOVED = 7") Integer status) {
        return null;
    }

    @Override
    public Map<String, Object> getGroup(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getGroupsByAcctID(@DaoParam(name = "accountID") Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getID(@DaoParam(name = "name", inputDesc = "username, email, mcmid, or vin") String name, @DaoParam(name = "value") String value) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last location for the specified ID. (which: 1=driver,2=vehicle) Returns a locMap, or an Integer error.")
    public Map<String, Object> getLastLoc(@DaoParam(name = "id", inputDesc = "driverID or vehicleID") Integer id,
            @DaoParam(name = "type", inputDesc = "1 - DRIVER, 2 - VEHICLE") Integer reqType) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last trip associated with this ID (which: 1=driver,2=vehicle). Returns a tripMap, or an Integer error.")
    public Map<String, Object> getLastTrip(@DaoParam(name = "id", inputDesc = "driverID or vehicleID") Integer id,
            @DaoParam(name = "type", inputDesc = "1 - DRIVER, 2 - VEHICLE") Integer reqType) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "")
    public Map<String, Object> getNextSilo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getNote(Long noteID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getNoteNearLoc(Integer driverID, Double lat, Double lng, Long startDT, Long stopDT) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches notes associated with this driver, within the specified timeframe (start, stop). (optional)typeList would contain a list of note types to be fetched. Returns a list of noteMap, or an Integer error.")
    public List<Map<String, Object>> getDriverNote(@DaoParam(name = "driverID") Integer driverID,
            @DaoParam(name = "startDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "includeForgiven", inputDesc = "1 - include forgiven, 0 - exclude forgiven") Integer includeForgiven,
            @DaoParam(name = "types[]", inputDesc = "comma sep list of event types") Integer[] types) {
        return null;
    }

    @Override
    @MethodDescription(description = "")
    public List<Map<String, Object>> getNoteByMiles(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "milesBack") Integer milesBack,
            @DaoParam(name = "types[]", inputDesc = "comma sep list of event types") Integer[] types) {
        return null;
    }

    @Override
    @MethodDescription(description = "")
    public Map<String, Object> getPerson(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last 'N' most recent notes for this group. (optional)typeList would contain a list of note types to be fetched. Returns a list of noteMap, or an Integer error.")
    public List<Map<String, Object>> getRecentNotes(@DaoParam(name = "groupID") Integer groupID, @DaoParam(name = "count") Integer eventCnt,
            @DaoParam(name = "types[]", inputDesc = "comma sep list of event types") Integer[] types) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Marks the specified note as 'forgiven'. Returns a map[count], or an Integer error.")
    public Map<String, Object> forgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified note. Returns a map[count], or an Integer error.")
    public Map<String, Object> unforgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "")
    public Map<String, Object> getRedFlagAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getRedFlagAlertsByAcctID(@DaoParam(name = "accountID") Integer accountID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches notes from this group(deep) that have been 'flagged', within the specified timeframe (start, stop). Returns a list of redFlagMap, or an Integer error.")
    public List<Map<String, Object>> getRedFlags(@DaoParam(name = "groupID") Integer groupID,
            @DaoParam(name = "startDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "includeForgiven", inputDesc = "1 - include forgiven, 0 - exclude forgiven") Integer includeForgiven) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getRoles() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getSensitivityMaps() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getStates() {
        return null;
    }

    @Override
    public Map<String, Object> getTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getTablePrefsByUserID(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getTimezones() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches trips associated with this ID (which: 1=driver,2=vehicle), within the timeframe specified (start, stop). Returns a list of tripMap, or an Integer error.")
    public List<Map<String, Object>> getTrips(@DaoParam(name = "id", inputDesc = "driverID or vehicleID") Integer id,
            @DaoParam(name = "type", inputDesc = "1 - DRIVER, 2 - VEHICLE") Integer reqType,
            @DaoParam(name = "startDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getUser(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupID(@DaoParam(name = "groupID") Integer groupID) {
        return null;
    }

    @Override
    public Map<String, Object> getVehicle(@DaoParam(name = "vehicleID") Integer vehicleID) throws ProDAOException {
        return null;
    }
    


    @Override
    public Map<String, Object> getVehicleByDriverID(@DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }     

    @Override
    @MethodDescription(description = "Fetches notes associated with this vehicle, within the specified timeframe (start, stop). (optional)typeList would contain a list of note types to be fetched. Returns a list of noteMap, or an Integer error.")
    public List<Map<String, Object>> getVehicleNote(@DaoParam(name = "vehicleID") Integer vehicleID,
            @DaoParam(name = "startDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", isDate = true, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "includeForgiven", inputDesc = "1 - include forgiven, 0 - exclude forgiven") Integer includeForgiven,
            @DaoParam(name = "types", inputDesc = "comma sep list of event types") Integer[] types) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleNoteByMiles(@DaoParam(name = "vehicleID") Integer vehicleID, Integer milesBack,
            @DaoParam(name = "types", inputDesc = "comma sep list of event types") Integer[] types) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupID(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getZone(@DaoParam(name = "zoneID") Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getZoneAlert(@DaoParam(name = "zoneAlertID") Integer zoneAlertID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getZoneAlertsByAcctID(@DaoParam(name = "accountID") Integer accountID) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getZonesByAcctID(@DaoParam(name = "accountID") Integer accountID) {
        return null;
    }

    @Override
    public Map<String, Object> queueFwdCmd(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "FowardCommand", type = com.inthinc.pro.model.ForwardCommand.class) Map<String, Object> fwdMap) {
        return null;
    }

    @Override
    public Map<String, Object> setVehicleDevice(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> clrVehicleDevice(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> setVehicleDriver(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateAcct(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateAddr(@DaoParam(name = "addressID") Integer addrID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateDevice(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateDriver(@DaoParam(name = "driverID") Integer driverID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateFwdCmd(@DaoParam(name = "fwdID") Integer fwdID, @DaoParam(name = "status") Integer status) {
        return null;
    }

    @Override
    public Map<String, Object> updateGroup(@DaoParam(name = "groupID") Integer groupID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updatePerson(@DaoParam(name = "personID") Integer personID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateRedFlagAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateUser(@DaoParam(name = "userID") Integer userID, @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap)
            throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateVehicle(@DaoParam(name = "vehicleID") Integer vehicleID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateZone(@DaoParam(name = "zoneID") Integer zoneID, @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap)
            throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateZoneAlert(@DaoParam(name = "zoneAlertID") Integer zoneAlertID,
            @DaoParam(name = "ZoneAlert", type = com.inthinc.pro.model.ZoneAlert.class) Map<String, Object> zoneAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getDriverByPersonID(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getUserByPersonID(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getGroupsByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getMessages(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "deliveryMethod", inputDesc = "1-PHONE,2-TEXT_MESSAGE,3-EMAIL") Integer deliveryMethodType) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) {
        return null;
    }

    // rfid - driverID
    @Override
    public Map<String, Object> getIDLong(@DaoParam(name = "name", inputDesc = "rfid") String name, @DaoParam(name = "value") Long value) {
        return null;
    }

    @Override
    public Map<String, Object> createReportPref(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> getReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByAcctID(@DaoParam(name = "accountID") Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByUserID(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> updateReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        return null;
    }

    @Override
    public Map<String, Object> createCrash(Integer acctID, Map<String, Object> crashReportMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteCrash(Integer crashReportID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getCrash(Integer crashReportID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getCrashes(Integer groupID, Long startDT, Long stopDT, Integer incForgiven) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateCrash(Integer reportPrefID, Map<String, Object> crashReportMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description = "Marks the specified crash as 'forgiven'. Returns a map[count], or an Integer error.")
    public Map<String, Object> forgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified crash. Returns a map[count], or an Integer error.")
    public Map<String, Object> unforgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }
}
