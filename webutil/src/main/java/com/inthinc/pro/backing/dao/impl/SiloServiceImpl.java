package com.inthinc.pro.backing.dao.impl;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.validator.ValidatorType;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

@SuppressWarnings("serial")
public class SiloServiceImpl implements SiloService {
    @Override
    @MethodDescription(description = "Creates a new account.", crudType=CrudType.CREATE)
    public Map<String, Object> createAcct(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new address.", crudType=CrudType.CREATE)
    public Map<String, Object> createAddr(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new device.", crudType=CrudType.CREATE)
    public Map<String, Object> createDevice(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new driver.", crudType=CrudType.CREATE)
    public Map<String, Object> createDriver(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new group.", crudType=CrudType.CREATE)
    public Map<String, Object> createGroup(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new person.", crudType=CrudType.CREATE)
    public Map<String, Object> createPerson(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new red flag alert preferences.", crudType=CrudType.CREATE)
    public Map<String, Object> createRedFlagAlert(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new table preference.", crudType=CrudType.CREATE)
    public Map<String, Object> createTablePref(@DaoParam(name = "userID") Integer userID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new user.", crudType=CrudType.CREATE)
    public Map<String, Object> createUser(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new vehicle.", crudType=CrudType.CREATE)
    public Map<String, Object> createVehicle(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new zone.", crudType=CrudType.CREATE)
    public Map<String, Object> createZone(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new zone alert preference.", crudType=CrudType.CREATE)
    public Map<String, Object> createZoneAlert(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "ZoneAlert", type = com.inthinc.pro.model.ZoneAlert.class) Map<String, Object> zoneAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete an account.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a device.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteDevice(@DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a driver.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteDriver(@DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a group.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteGroup(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a person.", crudType=CrudType.DELETE)
    public Map<String, Object> deletePerson(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a red flag alert preference.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteRedFlagAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a table preference.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a user.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteUser(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a vehicle.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteVehicle(@DaoParam(name = "vehicleID") Integer vehicleID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a zone.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteZone(@DaoParam(name = "zoneID") Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a zone alert preference.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteZoneAlert(@DaoParam(name = "zoneAlertID") Integer zoneAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a zone alert preference.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteZoneAlertsByZoneID(@DaoParam(name = "zoneID") Integer zoneID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches an account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Account.class)
    public Map<String, Object> getAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all accounts.", crudType=CrudType.READ_RESTRICTED)
    public List<Map<String, Object>> getAccts(@DaoParam(name = "siloID") Integer siloID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches an address.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Address.class)
    public Map<String, Object> getAddr(@DaoParam(name = "addrID", validator=ValidatorType.ADDRESS) Integer addrID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all account IDs.", crudType=CrudType.READ_RESTRICTED)
    public List<Map<String, Object>> getAllAcctIDs() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a device.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Device.class)
    public Map<String, Object> getDevice(@DaoParam(name = "deviceID", validator=ValidatorType.DEVICE) Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all devices in the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Device.class)
    public List<Map<String, Object>> getDevicesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a driver.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    public Map<String, Object> getDriver(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all drivers in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    public List<Map<String, Object>> getDriversByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches (numof) vehicles from this group(deep) nearest to (lat, lng).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    public List<Map<String, Object>> getVehiclesNearLoc(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, @DaoParam(name = "count") Integer numof, @DaoParam(name = "lat") Double lat,
            @DaoParam(name = "lng") Double lng) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches forward commands for the specifed device and status.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ForwardCommand.class)
    public List<Map<String, Object>> getFwdCmds(@DaoParam(name = "deviceID", validator=ValidatorType.DEVICE) Integer deviceID,
            @DaoParam(name = "status", type=com.inthinc.pro.backing.dao.ui.FwdCmdStatusList.class) Integer status) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    public Map<String, Object> getGroup(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all groups in the specifed account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    public List<Map<String, Object>> getGroupsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the id of the name/value pair.", crudType=CrudType.READ)
    public Map<String, Object> getID(@DaoParam(name = "name", type = com.inthinc.pro.backing.dao.ui.IDTypeList.class) String name, @DaoParam(name = "value") String value) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last location for the specified ID and type (driver or vehicle).", 
    					crudType=CrudType.READ, modelClass=com.inthinc.pro.model.LastLocation.class)
    public Map<String, Object> getLastLoc(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
            @DaoParam(name = "type",  type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last trip associated with this ID and type (driver or vehicle).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Trip.class)
    public Map<String, Object> getLastTrip(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
            @DaoParam(name = "type", type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the stops made by a driver.", 
			crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverStops.class)
    public List<Map<String, Object>> getStops(@DaoParam(name="driverID", validator=ValidatorType.DRIVER) Integer driverID,
                                              @DaoParam(name="startDate", type=java.util.Date.class) Long startDate,
                                              @DaoParam(name="endDate", type=java.util.Date.class) Long endDate) {                   
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches the next silo.", crudType=CrudType.READ_RESTRICTED)
    public Map<String, Object> getNextSilo() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the notification or event by its ID.", 
    				crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
					mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    public Map<String, Object> getNote(@DaoParam(name = "noteID", validator=ValidatorType.NOTE) Long noteID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the notification or event for the specified driver closest to the specifed location, in the timeframe.", 
    					crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
						mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    public Map<String, Object> getNoteNearLoc(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID, 
    		@DaoParam(name = "latitude") Double lat, 
    		@DaoParam(name = "longitude") Double lng, 
    		@DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
    		@DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches notes associated with this driver, within the specified timeframe (start, stop)and note types to be fetched.", 
    						crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
    						mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    public List<Map<String, Object>> getDriverNote(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) {
        return null;
    }


    @Override
    @MethodDescription(description = "Fetches a person.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Person.class)
    public Map<String, Object> getPerson(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last 'N' most recent notes for this group. (optional)typeList would contain a list of note types to be fetched.", 
    					crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
    					mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    public List<Map<String, Object>> getRecentNotes(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, @DaoParam(name = "count") Integer eventCnt,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Marks the specified note as 'forgiven'.", crudType=CrudType.UPDATE)
    public Map<String, Object> forgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified note.", crudType=CrudType.UPDATE)
    public Map<String, Object> unforgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a red flag alert preference.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    public Map<String, Object> getRedFlagAlert(@DaoParam(name = "redFlagAlertID", validator=ValidatorType.RED_FLAG_ALERT) Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all red flag alert preferences for the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    public List<Map<String, Object>> getRedFlagAlertsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }


    @Override
    @MethodDescription(description = "Fetches all device sensitivity maps.", crudType=CrudType.READ)
    public List<Map<String, Object>> getSensitivityMaps() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all states.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.State.class)
    public List<Map<String, Object>> getStates() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a table preference for the specifed ID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TablePreference.class)
    public Map<String, Object> getTablePref(@DaoParam(name = "tablePrefID", validator=ValidatorType.TABLE_PREF) Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all table preferences for the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TablePreference.class)
    public List<Map<String, Object>> getTablePrefsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all timezones.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.SupportedTimeZone.class)
    public List<Map<String, Object>> getTimezones() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches trips associated with this ID (which: 1=driver,2=vehicle), within the timeframe specified (start, stop). ", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Trip.class)
    public List<Map<String, Object>> getTrips(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
            @DaoParam(name = "type",  type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    public Map<String, Object> getUser(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all users in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    public List<Map<String, Object>> getUsersByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    public Map<String, Object> getVehicle(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE) Integer vehicleID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches a vehicle by its currently assigned driver.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    public Map<String, Object> getVehicleByDriverID(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID) throws ProDAOException {
        return null;
    }     

    @Override
    @MethodDescription(description = "Fetches notes associated with this vehicle, within the specified timeframe (start, stop). (optional)typeList would contain a list of note types to be fetched.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    public List<Map<String, Object>> getVehicleNote(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE) Integer vehicleID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all vehicles in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    public List<Map<String, Object>> getVehiclesByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a zone.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Zone.class)
    public Map<String, Object> getZone(@DaoParam(name = "zoneID", validator=ValidatorType.ZONE) Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a zone alert preference.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ZoneAlert.class)
    public Map<String, Object> getZoneAlert(@DaoParam(name = "zoneAlertID", validator=ValidatorType.ZONE_ALERT) Integer zoneAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all zone alert preferences for an account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ZoneAlert.class)
    public List<Map<String, Object>> getZoneAlertsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all zones for an account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Zone.class)
    public List<Map<String, Object>> getZonesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Add a forward command to the specified device's queue.", crudType=CrudType.UPDATE)
    public Map<String, Object> queueFwdCmd(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "FowardCommand", type = com.inthinc.pro.model.ForwardCommand.class) Map<String, Object> fwdMap) {
        return null;
    }

    @Override
    @MethodDescription(description = "Assign a device to a vehicle.", crudType=CrudType.UPDATE)
    public Map<String, Object> setVehicleDevice(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Do not use. Method only to be used by test data generators on development server.", crudType=CrudType.NOT_AVAILABLE)
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID, Long assignTime) throws ProDAOException {
        return null;
    }


    @Override
    @MethodDescription(description = "Clear a device's vehicle assignment.", crudType=CrudType.UPDATE)
    public Map<String, Object> clrVehicleDevice(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Assign a driver to a vehicle.", crudType=CrudType.UPDATE)
    public Map<String, Object> setVehicleDriver(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update an account.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update an address.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateAddr(@DaoParam(name = "addressID") Integer addrID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a device.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateDevice(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a driver.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateDriver(@DaoParam(name = "driverID") Integer driverID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a queued forward command.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateFwdCmd(@DaoParam(name = "fwdID") Integer fwdID, @DaoParam(name = "status") Integer status) {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a group.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateGroup(@DaoParam(name = "groupID") Integer groupID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a person.", crudType=CrudType.UPDATE)
    public Map<String, Object> updatePerson(@DaoParam(name = "personID") Integer personID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a red flag alert preference.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateRedFlagAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a table preference.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a user.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateUser(@DaoParam(name = "userID") Integer userID, @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap)
            throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a vehicle.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateVehicle(@DaoParam(name = "vehicleID") Integer vehicleID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a zone.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateZone(@DaoParam(name = "zoneID") Integer zoneID, @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap)
            throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a zone alert prefreference.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateZoneAlert(@DaoParam(name = "zoneAlertID") Integer zoneAlertID,
            @DaoParam(name = "ZoneAlert", type = com.inthinc.pro.model.ZoneAlert.class) Map<String, Object> zoneAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a driver by its associated personID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    public Map<String, Object> getDriverByPersonID(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a user by its associated personID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    public Map<String, Object> getUserByPersonID(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all drivers in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    public List<Map<String, Object>> getDriversByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all groups in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    public List<Map<String, Object>> getGroupsByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all vehicles in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    public List<Map<String, Object>> getVehiclesByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch queued messages for the specified silo (Warning: messages will be removed from the queue and not sent out).", crudType=CrudType.READ_RESTRICTED)
    public List<Map<String, Object>> getMessages(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "deliveryMethod", inputDesc = "1-PHONE,2-TEXT_MESSAGE,3-EMAIL") Integer deliveryMethodType) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all users in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    public List<Map<String, Object>> getUsersByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }
    @Override
    @MethodDescription(description = "Fetch all RFIDs for the specified barcode.", crudType=CrudType.READ)
    public List<Long> getRfidsForBarcode(@DaoParam(name = "barcode") String barcode) throws ProDAOException{
    	
    	return null;
    }

    @Override
    @MethodDescription(description = "Create a report scheduler preference in the specified account.", crudType=CrudType.CREATE)
    public Map<String, Object> createReportPref(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a report scheduler preference.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a report scheduler preference.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    public Map<String, Object> getReportPref(@DaoParam(name = "reportPrefID", validator=ValidatorType.REPORT_PREF) Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all report scheduler preference in the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    public List<Map<String, Object>> getReportPrefsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all report scheduler preference for the user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    public List<Map<String, Object>> getReportPrefsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update report scheduler preference.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Create a crash report.", crudType=CrudType.CREATE)
    public Map<String, Object> createCrash(@DaoParam(name = "accountID", isAccountID=true) Integer acctID, 
    		@DaoParam(name = "CrashReport", type = com.inthinc.pro.model.CrashReport.class) Map<String, Object> crashReportMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a crash report.", crudType=CrudType.DELETE)
    public Map<String, Object> deleteCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a crash report.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashReport.class)
    public Map<String, Object> getCrash(@DaoParam(name = "crashReportID", validator=ValidatorType.CRASH_REPORT) Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all the crash reports for the specifed group, time frame, and forgiven flag.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashReport.class)
    public List<Map<String, Object>> getCrashes(
            @DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a crash report.", crudType=CrudType.UPDATE)
    public Map<String, Object> updateCrash(@DaoParam(name = "crashReportID") Integer crashReportID, @DaoParam(name = "CrashReport", type = com.inthinc.pro.model.CrashReport.class)Map<String, Object> crashReportMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Marks the specified crash as 'forgiven'.", crudType=CrudType.UPDATE)
    public Map<String, Object> forgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified crash.", crudType=CrudType.UPDATE)
    public Map<String, Object> unforgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Retrieves crash traces for an eventID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashTrace.class)
    public List<Map<String, Object>> getCrashTraces(@DaoParam(name = "eventID") String eventID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Retrieves a count of crash traces for an eventID and date range.", crudType=CrudType.READ, modelClass=java.lang.Integer.class)
    public Map<String, Object> getCrashTraceCount(
            @DaoParam(name = "eventID")String groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            List<Map<String, Object>> filterList) throws ProDAOException{
        return null;
    }
   
    @Override
    @MethodDescription(description = "Get a list of stack traces for the specified eventID and date range.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashTrace.class)
    public List<Map<String, Object>> getCrashTracePage(
            @DaoParam(name = "eventID") String eventID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate, 
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm") Long endDate, 
            List<Map<String, Object>> filterList, 
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) throws ProDAOException {
        return null;
    }
    
	@Override
    @MethodDescription(description = "Do not use. Method only to be used by test data generators on development server.", crudType=CrudType.NOT_AVAILABLE)
	public Map<String, Object> setVehicleDriver(Integer vehicleID,
			Integer driverID, Long assignTime) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a list of current Driver/Vehicle locations for the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverLocation.class)
	public List<Map<String, Object>> getDVLByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID)
			throws ProDAOException {
		return null;
	}


	@Override
    @MethodDescription(description = "Get a list of all portal access points.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.SiteAccessPoint.class)
	public List<Map<String, Object>> getSiteAccessPts() {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a list of all roles for the specifed account.", crudType=CrudType.READ,modelClass=com.inthinc.pro.model.security.Role.class)
	public List<Map<String, Object>> getRolesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) {
		return null;
	}
	@Override
    @MethodDescription(description = "Get a subset of driver Events for the specifed group and time frame.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
	public List<Map<String, Object>> getDriverEventPage(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "note types") Integer[] types) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a count of driver Events for the specifed group and time frame.", crudType=CrudType.READ)
	public Map<String, Object> getDriverEventCount(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "filterList", inputDesc = "Not Implemented -- enter any value") List<Map<String, Object>> filterList, 
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "note types") Integer[] types) { 
		return null;
	}

	@Override
    @MethodDescription(description = "Get a subset of Red flags for the specifed group and time frame.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
	public List<Map<String, Object>> getRedFlagsPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a count of Red Flags for the specifed group and time frame.", crudType=CrudType.READ) 
	public Map<String, Object> getRedFlagsCount(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
			List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Create a Role", crudType=CrudType.CREATE)
	public Map<String, Object> createRole(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
			@DaoParam(name = "Role", type = com.inthinc.pro.model.security.Role.class) Map<String, Object> roleMap) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Delete a Role", crudType=CrudType.DELETE)
	public Map<String, Object> deleteRole(@DaoParam(name = "roleID") Integer roleID)
			throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetch a Role", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.Role.class)
	public Map<String, Object> getRole(@DaoParam(name = "roleID", validator=ValidatorType.ROLE) Integer roleID) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Update a Role", crudType=CrudType.UPDATE)
	public Map<String, Object> updateRole(@DaoParam(name = "roleID") Integer roleID,
			@DaoParam(name = "Role", type = com.inthinc.pro.model.security.Role.class) Map<String, Object> roleMap) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetch the list of access points for the specifed user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.AccessPoint.class)
	public List<Map<String, Object>> getUsersAccessPts(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of devices in the specified group (deep).", crudType=CrudType.READ)
	public Map<String, Object> getDeviceReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of devices in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DeviceReportItem.class)
	public List<Map<String, Object>> getDeviceReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of driver report items in the specified group (deep).", crudType=CrudType.READ)
	public Map<String, Object> getDriverReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of driver report items in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverReportItem.class)
	public List<Map<String, Object>> getDriverReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of idling report items in the specified group (deep) and time frame.", crudType=CrudType.READ)
	public Map<String, Object> getIdlingReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
			List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of idling report items in the specified group (deep) and time frame.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.IdlingReportItem.class)
	public List<Map<String, Object>> getIdlingReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of vehicle report items in the specified group (deep).", crudType=CrudType.READ)
	public Map<String, Object> getVehicleReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of vehicle report items in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.VehicleReportItem.class)
	public List<Map<String, Object>> getVehicleReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Clear the superuser role from the user. Returns count=1 if successful.", crudType=CrudType.UPDATE)
	public Map<String, Object> clearSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Determine if the user is a superuser.  Returns count=1 if true, or no result if false.", crudType=CrudType.READ_RESTRICTED)
	public Map<String, Object> isSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Makes the specified user a superuser. Returns count=1 if successful.", crudType=CrudType.UPDATE)
	public Map<String, Object> setSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Create a forward command definition.", crudType=CrudType.CREATE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
	public Map<String, Object> createFwdCmdDef(@DaoParam(name = "ForwardCommandDef", type = com.inthinc.pro.model.ForwardCommandDef.class) Map<String, Object> fwdCmdDefMap) {
		return null;
	}

	@Override
    @MethodDescription(description = "Delete a forward command definition.", crudType=CrudType.DELETE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
	public Map<String, Object> deleteFwdCmdDef(@DaoParam(name = "fwdCmd ID") Integer fwdCmd) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches forward command definitions.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
	public List<Map<String, Object>> getFwdCmdDefs() {
		return null;
	}

	@Override
    @MethodDescription(description = "Update a forward command definition.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
	public Map<String, Object> updateFwdCmdDef(@DaoParam(name = "ForwardCommandDef", type = com.inthinc.pro.model.ForwardCommandDef.class) Map<String, Object> fwdCmdDefMap) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches red flag alerts for the group hierarchy of the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
	public List<Map<String, Object>> getRedFlagAlertsByUserIDDeep(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches red flag alerts for the user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
	public List<Map<String, Object>> getRedFlagAlertsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER)Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches zone alerts for the group hierarchy of the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ZoneAlert.class)
	public List<Map<String, Object>> getZoneAlertsByUserIDDeep(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches zone alerts for the user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ZoneAlert.class)
	public List<Map<String, Object>> getZoneAlertsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER)Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches report schedules for the group hierarchy of the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
	public List<Map<String, Object>> getReportPrefsByUserIDDeep(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

    @Override
    @MethodDescription(description = "Fetches the device settings definitions", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.DeviceSettingDefinition.class,
    		mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public List<Map<String, Object>> getSettingDefs() {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings for one vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSetting.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public Map<String, Object> getVehicleSettings(@DaoParam(name = "vehicleID") Integer vehicleID) {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings for group hierarchy.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSetting.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public List<Map<String, Object>> getVehicleSettingsByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings history for one vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSettingHistory.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public List<Map<String, Object>> getVehicleSettingsHistory(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE)Integer vehicleID, 
            @DaoParam(name = "startTime", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm")Long startTime, 
            @DaoParam(name = "endTime", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm")Long endTime) {

        return null;
    }

    @Override
    @MethodDescription(description = "Set the device settings for a vehicle.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.configurator.model.VehicleSettings.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public Map<String, Object> setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

        return null;
    }

    @Override
    @MethodDescription(description = "Update the device settings for a vehicle.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.configurator.model.VehicleSettings.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    public Map<String, Object> updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

        return null;
    }
    
    @Override
    @MethodDescription(description = "Create a new text message alert.", crudType=CrudType.CREATE, modelClass=com.inthinc.pro.model.TextMsgAlert.class)    
    public Map<String,Object> createTextMsgAlert(Integer acctID, Map<String,Object> textMsgAlertMap) {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches a text message alert.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TextMsgAlert.class) 
    public List<Map<String,Object>> getTextMsgAlertsByAcctID(@DaoParam(name = "acctID") Integer acctID) {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Get a subset of text message alerts for the specifed group and time frame.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TextMsgAlert.class
			)
	public List<Map<String, Object>> getTextMsgPage(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            List<Map<String, Object>> filterList,
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) throws ProDAOException {
		return null;
	}
    
    @Override
    @MethodDescription(description = "Get a subset of sent text message alerts for the specifed group and time frame.", 
            crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TextMsgAlert.class
            )
    public List<Map<String, Object>> getSentTextMsgsByGroupID(
            @DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) throws ProDAOException {
        return null;
    }

	@Override
    @MethodDescription(description = "Get a count of Text messages for the specifed group and time frame.", crudType=CrudType.READ) 
	public Map<String, Object> getTextMsgCount(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches the list of all the sensitivity slider values.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.SensitivitySliderValues.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
	public List<Map<String, Object>> getSensitivitySliderValues() {
		return null;
	}
    @Override
    @MethodDescription(description = "Fetches the list of escalation items for an alert.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.AlertEscalationItem.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    public List<Map<String, Object>> getAlertEscalationItemsByAlert(Integer alertID) {
        return null;
    }

    @Override
   @MethodDescription(description = "Sends an UPDATE_ZONES forward command to all devices.", crudType=CrudType.UPDATE)
   public Map<String, Object> publishZones(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }
    
	
}