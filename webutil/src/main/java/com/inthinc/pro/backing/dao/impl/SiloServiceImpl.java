package com.inthinc.pro.backing.dao.impl;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.DAODescription;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.validator.ValidatorType;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

@SuppressWarnings("serial")
public class SiloServiceImpl implements SiloService {

    public static final String ID_KEY = "id";
    public static final String COUNT_KEY = "count";
    
    @Override
    @MethodDescription(description = "Creates a new account.", crudType=CrudType.CREATE, populateMethod="getAcct")
    @DAODescription(daoID="accountDAO", returnValueName=ID_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> createAcct(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new address.", crudType=CrudType.CREATE, populateMethod="getAddr")
    @DAODescription(daoID="addressDAO", returnValueName=ID_KEY)
    public Map<String, Object> createAddr(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new device.", crudType=CrudType.CREATE, populateMethod="getDevice",
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilDeviceMapper.class)
    @DAODescription(daoID="deviceDAO", returnValueName=ID_KEY)
    public Map<String, Object> createDevice(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new driver.", crudType=CrudType.CREATE, populateMethod="getDriver")
    @DAODescription(daoID="driverDAO", returnValueName=ID_KEY)
    public Map<String, Object> createDriver(@DaoParam(name = "personID") Integer personID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new group.", crudType=CrudType.CREATE, populateMethod="getGroup")
    @DAODescription(daoID="groupDAO", returnValueName=ID_KEY)
    public Map<String, Object> createGroup(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new person.", crudType=CrudType.CREATE, populateMethod="getPerson")
    @DAODescription(daoID="personDAO", returnValueName=ID_KEY)
    public Map<String, Object> createPerson(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new red flag alert preferences.", crudType=CrudType.CREATE, populateMethod="getAlert")
    @DAODescription(daoID="redFlagAlertDAO", returnValueName=ID_KEY)
    public Map<String, Object> createAlert(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new table preference.", crudType=CrudType.CREATE, populateMethod="getTablePref")
    @DAODescription(daoID="tablePreferenceDAO", returnValueName=ID_KEY)
    public Map<String, Object> createTablePref(@DaoParam(name = "userID") Integer userID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new user.", crudType=CrudType.CREATE, populateMethod="getUser")
    @DAODescription(daoID="userDAO", returnValueName=ID_KEY)
    public Map<String, Object> createUser(@DaoParam(name = "personID") Integer personID,
            @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new vehicle.", crudType=CrudType.CREATE, populateMethod="getVehicle")
    @DAODescription(daoID="vehicleDAO", returnValueName=ID_KEY)
    public Map<String, Object> createVehicle(@DaoParam(name = "groupID") Integer groupID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Creates a new zone.", crudType=CrudType.CREATE, populateMethod="getZone")
    @DAODescription(daoID="zoneDAO", returnValueName=ID_KEY)
    public Map<String, Object> createZone(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete an account.", crudType=CrudType.DELETE)
    @DAODescription(daoID="accountDAO", returnValueName=COUNT_KEY)
    public Map<String, Object> deleteAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a device.", crudType=CrudType.DELETE)
    @DAODescription(daoID="deviceDAO", returnValueName=COUNT_KEY)
    public Map<String, Object> deleteDevice(@DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a driver.", crudType=CrudType.DELETE)
    @DAODescription(daoID="driverDAO", returnValueName=COUNT_KEY)
    public Map<String, Object> deleteDriver(@DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a group.", crudType=CrudType.DELETE)
    @DAODescription(daoID="groupDAO", returnValueName=COUNT_KEY)
    public Map<String, Object> deleteGroup(@DaoParam(name = "groupID") Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a person.", crudType=CrudType.DELETE)
    @DAODescription(daoID="personDAO", returnValueName=COUNT_KEY)
    public Map<String, Object> deletePerson(@DaoParam(name = "personID") Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a red flag alert preference.", crudType=CrudType.DELETE)
    @DAODescription(daoID="redFlagAlertDAO")
    public Map<String, Object> deleteAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a table preference.", crudType=CrudType.DELETE)
    @DAODescription(daoID="tablePreferenceDAO")
    public Map<String, Object> deleteTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a user.", crudType=CrudType.DELETE)
    @DAODescription(daoID="userDAO")
    public Map<String, Object> deleteUser(@DaoParam(name = "userID") Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a vehicle.", crudType=CrudType.DELETE)
    @DAODescription(daoID="vehicleDAO")
    public Map<String, Object> deleteVehicle(@DaoParam(name = "vehicleID") Integer vehicleID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a zone.", crudType=CrudType.DELETE)
    @DAODescription(daoID="zoneDAO")
    public Map<String, Object> deleteZone(@DaoParam(name = "zoneID") Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a zone alert preference.", crudType=CrudType.DELETE)
    @DAODescription(daoID="redFlagAlertDAO", daoMethod="deleteAlertsByZoneID")
    public Map<String, Object> deleteAlertsByZoneID(@DaoParam(name = "zoneID") Integer zoneID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches an account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Account.class)
    @DAODescription(daoID="accountDAO")
    public Map<String, Object> getAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all accounts.", crudType=CrudType.NOT_AVAILABLE)
    public List<Map<String, Object>> getAccts(@DaoParam(name = "siloID") Integer siloID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches an address.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Address.class)
    @DAODescription(daoID="addressDAO")
    public Map<String, Object> getAddr(@DaoParam(name = "addrID", validator=ValidatorType.ADDRESS) Integer addrID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all account IDs.", crudType=CrudType.READ_RESTRICTED, modelClass=com.inthinc.pro.model.Account.class)
    @DAODescription(daoID="accountDAO", daoMethod="getAllAcctIDs")
    public List<Map<String, Object>> getAllAcctIDs() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a device.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Device.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilDeviceMapper.class)
    @DAODescription(daoID="deviceDAO")
    public Map<String, Object> getDevice(@DaoParam(name = "deviceID", validator=ValidatorType.DEVICE) Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all devices in the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Device.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilDeviceMapper.class)
    @DAODescription(daoID="deviceDAO", daoMethod="getDevicesByAcctID")
    public List<Map<String, Object>> getDevicesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a driver.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    @DAODescription(daoID="driverDAO")
    public Map<String, Object> getDriver(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all drivers in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    @DAODescription(daoID="driverDAO", daoMethod="getDrivers")
    public List<Map<String, Object>> getDriversByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches (numof) vehicles from this group(deep) nearest to (lat, lng).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="getVehiclesNearLoc")
    public List<Map<String, Object>> getVehiclesNearLoc(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, @DaoParam(name = "count") Integer numof, @DaoParam(name = "lat") Double lat,
            @DaoParam(name = "lng") Double lng) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches forward commands for the specifed device and status.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ForwardCommand.class)
    @DAODescription(daoID="deviceDAO", daoMethod="getForwardCommands")
    public List<Map<String, Object>> getFwdCmds(@DaoParam(name = "deviceID", validator=ValidatorType.DEVICE) Integer deviceID,
            @DaoParam(name = "status", type=com.inthinc.pro.backing.dao.ui.FwdCmdStatusList.class) Integer status) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    @DAODescription(daoID="groupDAO")
    public Map<String, Object> getGroup(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all groups in the specifed account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    @DAODescription(daoID="groupDAO", daoMethod="getGroupsByAcctID")
    public List<Map<String, Object>> getGroupsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the id of the name/value pair.", crudType=CrudType.READ)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getID", useMapper=false)
    public Map<String, Object> getID(@DaoParam(name = "name", type = com.inthinc.pro.backing.dao.ui.IDTypeList.class) String name, @DaoParam(name = "value") String value) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last location for the specified ID and type (driver or vehicle).", 
    					crudType=CrudType.READ, modelClass=com.inthinc.pro.model.LastLocation.class)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getLastLoc", useMapper=false)
    public Map<String, Object> getLastLoc(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
            @DaoParam(name = "type",  type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last trip associated with this ID and type (driver or vehicle).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Trip.class)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getLastTrip", useMapper=false)
    public Map<String, Object> getLastTrip(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
                                           @DaoParam(name = "type", type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType) throws ProDAOException {
        return null;
    }


    @Override
    @MethodDescription(description = "Fetches the vehicle trips for group hierarchy.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSetting.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="getLastVehicleTripsByGroupIDDeep")
    public List<Map<String, Object>> getLastVehicleTripsByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the stops made by a driver.", 
			crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverStops.class)
    @DAODescription(daoID="locationDAO", daoMethod="getStops", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.StopsArgumentMapper.class)
    public List<Map<String, Object>> getStops(@DaoParam(name="driverID", validator=ValidatorType.DRIVER) Integer driverID,
                                              @DaoParam(name="startDate", type=java.util.Date.class) Long startDate,
                                              @DaoParam(name="endDate", type=java.util.Date.class) Long endDate) {                   
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches the next silo.", crudType=CrudType.READ_RESTRICTED)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getNextSilo", useMapper=false)
    public Map<String, Object> getNextSilo() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the notification or event by its ID.", 
    				crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
					mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    @DAODescription(daoID="eventDAO")
    public Map<String, Object> getNote(@DaoParam(name = "noteID", validator=ValidatorType.NOTE) Long noteID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the notification or event for the specified driver closest to the specifed location, in the timeframe.", 
    					crudType=CrudType.NOT_AVAILABLE, modelClass=com.inthinc.pro.model.event.Event.class,
						mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    @DAODescription(daoID="eventDAO", daoMethod="getEventNearLocation", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventNearLocArgumentMapper.class )
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
    @DAODescription(daoID="eventDAO", daoMethod="getEventsForDriver", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventArgumentMapper.class)
    public List<Map<String, Object>> getDriverNote(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) {
        return null;
    }


    @Override
    @MethodDescription(description = "Fetches a person.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Person.class)
    @DAODescription(daoID="personDAO")
    public Map<String, Object> getPerson(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the last 'N' most recent notes for this group. (optional)typeList would contain a list of note types to be fetched.", 
    					crudType=CrudType.NOT_AVAILABLE, modelClass=com.inthinc.pro.model.event.Event.class,
    					mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getRecentNotes", useMapper=false)
    public List<Map<String, Object>> getRecentNotes(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, @DaoParam(name = "count") Integer eventCnt,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Marks the specified note as 'forgiven'.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="eventDAO", daoMethod="forgive")
    public Map<String, Object> forgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified note.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="eventDAO", daoMethod="unforgive")
    public Map<String, Object> unforgive(@DaoParam(name = "driverID") Integer driverID, @DaoParam(name = "noteID") Long noteID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a red flag alert preference.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    @DAODescription(daoID="redFlagAlertDAO")
    public Map<String, Object> getAlert(@DaoParam(name = "redFlagAlertID", validator=ValidatorType.RED_FLAG_ALERT) Integer redFlagAlertID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all red flag alert preferences for the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    @DAODescription(daoID="redFlagAlertDAO", daoMethod="getRedFlagAlerts")
    public List<Map<String, Object>> getAlertsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }


    @Override
    @MethodDescription(description = "Fetches all device sensitivity maps.", crudType=CrudType.NOT_AVAILABLE)
    public List<Map<String, Object>> getSensitivityMaps() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all states.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.State.class)
    @DAODescription(daoID="stateDAO", daoMethod="getStates")
    public List<Map<String, Object>> getStates() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a table preference for the specifed ID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TablePreference.class, mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilTablePrefMapper.class)
    @DAODescription(daoID="tablePreferenceDAO")
    public Map<String, Object> getTablePref(@DaoParam(name = "tablePrefID", validator=ValidatorType.TABLE_PREF) Integer tablePrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all table preferences for the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TablePreference.class, mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilTablePrefMapper.class)
    @DAODescription(daoID="tablePreferenceDAO", daoMethod="getTablePreferencesByUserID")
    public List<Map<String, Object>> getTablePrefsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all timezones.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.SupportedTimeZone.class)
    @DAODescription(daoID="timeZoneDAO", daoMethod="getSupportedTimeZones")
    public List<Map<String, Object>> getTimezones() {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches trips associated with this ID (which: 1=driver,2=vehicle), within the timeframe specified (start, stop). ", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Trip.class)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getTrips", useMapper=false)
    public List<Map<String, Object>> getTrips(@DaoParam(name = "id", inputDesc = "driverID or vehicleID", validator=ValidatorType.DRIVER_OR_VEHICLE) Integer id,
            @DaoParam(name = "type",  type = com.inthinc.pro.backing.dao.ui.DVTypeList.class) Integer reqType,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    @DAODescription(daoID="userDAO")
    public Map<String, Object> getUser(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all users in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    @DAODescription(daoID="userDAO", daoMethod="getUsersInGroupHierarchy")
    public List<Map<String, Object>> getUsersByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO")
    public Map<String, Object> getVehicle(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE) Integer vehicleID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches a vehicle by its currently assigned driver.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="findByDriverID")
    public Map<String, Object> getVehicleByDriverID(@DaoParam(name = "driverID", validator=ValidatorType.DRIVER) Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a vehicle by its current name.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="findByName")
    public Map<String, Object> getVehicleByName( @DaoParam(name = "name") String name) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches notes associated with this vehicle, within the specified timeframe (start, stop). (optional)typeList would contain a list of note types to be fetched.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    @DAODescription(daoID="eventDAO", daoMethod="getEventsForVehicle", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventArgumentMapper.class)
    public List<Map<String, Object>> getVehicleNote(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE) Integer vehicleID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "types", type=com.inthinc.pro.backing.dao.ui.EventTypeList.class, inputDesc = "event types") Integer[] types) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all vehicles in the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="getVehiclesInGroup")
    public List<Map<String, Object>> getVehiclesByGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a zone.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Zone.class)
    @DAODescription(daoID="zoneDAO")
    public Map<String, Object> getZone(@DaoParam(name = "zoneID", validator=ValidatorType.ZONE) Integer zoneID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches all zones for an account.", crudType=CrudType.READ, 
            modelClass=com.inthinc.pro.model.Zone.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilZoneMapper.class)
    @DAODescription(daoID="zoneDAO", daoMethod="getZones")
    public List<Map<String, Object>> getZonesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Add a forward command to the specified device's queue.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="deviceDAO", daoMethod="queueForwardCommand")
    public Map<String, Object> queueFwdCmd(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "FowardCommand", type = com.inthinc.pro.model.ForwardCommand.class) Map<String, Object> fwdMap) {
        return null;
    }

    @Override
    @MethodDescription(description = "Assign a device to a vehicle.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="vehicleDAO", daoMethod="setVehicleDevice")
    public Map<String, Object> setVehicleDevice(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE) Integer vehicleID, @DaoParam(name = "deviceID", validator=ValidatorType.DEVICE_VEHICLE_ACCOUNTID) Integer deviceID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Do not use. Method only to be used by test data generators on development server.", crudType=CrudType.NOT_AVAILABLE)
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID, Long assignTime) throws ProDAOException {
        return null;
    }


    @Override
    @MethodDescription(description = "Clear a device's vehicle assignment.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="vehicleDAO", daoMethod="clearVehicleDevice")
    public Map<String, Object> clrVehicleDevice(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "deviceID") Integer deviceID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Assign a driver to a vehicle.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="vehicleDAO", daoMethod="setVehicleDriver")
    public Map<String, Object> setVehicleDriver(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "driverID") Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update an account.", crudType=CrudType.UPDATE, populateMethod="getAcct")
    @DAODescription(daoID="accountDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateAcct(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
            @DaoParam(name = "Account", type = com.inthinc.pro.model.Account.class) Map<String, Object> acctMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update an address.", crudType=CrudType.UPDATE, populateMethod="getAddr")
    @DAODescription(daoID="addressDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateAddr(@DaoParam(name = "addressID") Integer addrID,
            @DaoParam(name = "Address", type = com.inthinc.pro.model.Address.class) Map<String, Object> addrMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a device.", crudType=CrudType.UPDATE, populateMethod="getDevice",
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilDeviceMapper.class)
    @DAODescription(daoID="deviceDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateDevice(@DaoParam(name = "deviceID") Integer deviceID,
            @DaoParam(name = "Device", type = com.inthinc.pro.model.Device.class) Map<String, Object> deviceMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a driver.", crudType=CrudType.UPDATE, populateMethod="getDriver")
    @DAODescription(daoID="driverDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateDriver(@DaoParam(name = "driverID") Integer driverID,
            @DaoParam(name = "Driver", type = com.inthinc.pro.model.Driver.class) Map<String, Object> driverMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a queued forward command.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="siloServiceDAO", returnValueName=COUNT_KEY, daoMethod="updateFwdCmd", useMapper=false)
    public Map<String, Object> updateFwdCmd(@DaoParam(name = "fwdID") Integer fwdID, @DaoParam(name = "status") Integer status) {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a group.", crudType=CrudType.UPDATE, populateMethod="getGroup")
    @DAODescription(daoID="groupDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateGroup(@DaoParam(name = "groupID") Integer groupID,
            @DaoParam(name = "Group", type = com.inthinc.pro.model.Group.class) Map<String, Object> groupMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a person.", crudType=CrudType.UPDATE, populateMethod="getPerson")
    @DAODescription(daoID="personDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updatePerson(@DaoParam(name = "personID") Integer personID,
            @DaoParam(name = "Person", type = com.inthinc.pro.model.Person.class) Map<String, Object> personMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a red flag alert preference.", crudType=CrudType.UPDATE, populateMethod="getAlert")
    @DAODescription(daoID="redFlagAlertDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateAlert(@DaoParam(name = "redFlagAlertID") Integer redFlagAlertID,
            @DaoParam(name = "RedFlagAlert", type = com.inthinc.pro.model.RedFlagAlert.class) Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a table preference.", crudType=CrudType.UPDATE, populateMethod="getTablePref")
    @DAODescription(daoID="tablePreferenceDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateTablePref(@DaoParam(name = "tablePrefID") Integer tablePrefID,
            @DaoParam(name = "TablePreference", type = com.inthinc.pro.model.TablePreference.class) Map<String, Object> tablePrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a user.", crudType=CrudType.UPDATE, populateMethod="getUser")
    @DAODescription(daoID="userDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateUser(@DaoParam(name = "userID") Integer userID, @DaoParam(name = "User", type = com.inthinc.pro.model.User.class) Map<String, Object> userMap)
            throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a vehicle.", crudType=CrudType.UPDATE, populateMethod="getVehicle")
    @DAODescription(daoID="vehicleDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateVehicle(@DaoParam(name = "vehicleID") Integer vehicleID,
            @DaoParam(name = "Vehicle", type = com.inthinc.pro.model.Vehicle.class) Map<String, Object> vehicleMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a zone.", crudType=CrudType.UPDATE, populateMethod="getZone")
    @DAODescription(daoID="zoneDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateZone(@DaoParam(name = "zoneID") Integer zoneID, @DaoParam(name = "Zone", type = com.inthinc.pro.model.Zone.class) Map<String, Object> zoneMap)
            throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a driver by its associated personID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    @DAODescription(daoID="driverDAO", daoMethod="findByPersonID")
    public Map<String, Object> getDriverByPersonID(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

	@Override
    @MethodDescription(description = "Fetch a user by its associated personID.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    @DAODescription(daoID="userDAO", daoMethod="getUserByPersonID")
    public Map<String, Object> getUserByPersonID(@DaoParam(name = "personID", validator=ValidatorType.PERSON) Integer personID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all drivers in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Driver.class)
    @DAODescription(daoID="driverDAO", daoMethod="getAllDrivers")
    public List<Map<String, Object>> getDriversByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all groups in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Group.class)
    @DAODescription(daoID="siloServiceDAO", daoMethod="getGroupsByGroupIDDeep", useMapper=false)
    public List<Map<String, Object>> getGroupsByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all vehicles in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Vehicle.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="getVehiclesInGroupHierarchy")
    public List<Map<String, Object>> getVehiclesByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) throws ProDAOException {
        return null;
    }

    
    @Override
    @MethodDescription(description = "Fetch queued messages for the specified silo (Warning: messages will be removed from the queue and not sent out).", crudType=CrudType.READ_RESTRICTED)
    @DAODescription(daoID="alertMessageDAO", daoMethod="getMessages", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public List<Map<String, Object>> getMessages(@DaoParam(name = "siloID") Integer siloID,
            @DaoParam(name = "deliveryMethod", inputDesc = "1-PHONE,2-TEXT_MESSAGE,3-EMAIL", type=com.inthinc.pro.backing.dao.ui.MessageDeliveryTypeList.class) Integer deliveryMethodType) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all users in the group hierarchy of the specified group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.User.class)
    @DAODescription(daoID="userDAO", daoMethod="getUsersInGroupHierarchy")
    public List<Map<String, Object>> getUsersByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }
    @Override
    @MethodDescription(description = "Fetch all RFIDs for the specified barcode.", crudType=CrudType.READ)
    @DAODescription(daoID="driverDAO", daoMethod="getRfidsByBarcode")
    public List<Long> getRfidsForBarcode(@DaoParam(name = "barcode") String barcode) throws ProDAOException{
    	
    	return null;
    }

    @Override
    @MethodDescription(description = "Create a report scheduler preference in the specified account.", crudType=CrudType.CREATE)
    @DAODescription(daoID="reportScheduleDAO", returnValueName=ID_KEY)
    public Map<String, Object> createReportPref(@DaoParam(name = "accountID") Integer acctID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a report scheduler preference.", crudType=CrudType.DELETE)
    @DAODescription(daoID="reportScheduleDAO")
    public Map<String, Object> deleteReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a report scheduler preference.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    @DAODescription(daoID="reportScheduleDAO")
    public Map<String, Object> getReportPref(@DaoParam(name = "reportPrefID", validator=ValidatorType.REPORT_PREF) Integer reportPrefID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all report scheduler preference in the account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    @DAODescription(daoID="reportScheduleDAO", daoMethod="getReportSchedulesByAccountID")
    public List<Map<String, Object>> getReportPrefsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all report scheduler preference for the user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    @DAODescription(daoID="reportScheduleDAO", daoMethod="getReportSchedulesByUserID")
    public List<Map<String, Object>> getReportPrefsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update report scheduler preference.", crudType=CrudType.UPDATE, populateMethod="getReportPref")
    @DAODescription(daoID="reportScheduleDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateReportPref(@DaoParam(name = "reportPrefID") Integer reportPrefID,
            @DaoParam(name = "ReportSchedule", type = com.inthinc.pro.model.ReportSchedule.class) Map<String, Object> reportPrefMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Create a crash report.", crudType=CrudType.CREATE)
    @DAODescription(daoID="crashReportDAO", returnValueName=ID_KEY)
    public Map<String, Object> createCrash(@DaoParam(name = "accountID", isAccountID=true) Integer acctID, 
    		@DaoParam(name = "CrashReport", type = com.inthinc.pro.model.CrashReport.class) Map<String, Object> crashReportMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a crash report.", crudType=CrudType.DELETE)
    @DAODescription(daoID="crashReportDAO")
    public Map<String, Object> deleteCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch a crash report.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashReport.class)
    @DAODescription(daoID="crashReportDAO")
    public Map<String, Object> getCrash(@DaoParam(name = "crashReportID", validator=ValidatorType.CRASH_REPORT) Integer crashReportID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetch all the crash reports for the specifed group, time frame, and forgiven flag.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.CrashReport.class)
    @DAODescription(daoID="crashReportDAO", daoMethod="findByGroupID", daoParamMapper = com.inthinc.pro.backing.dao.argmapper.CrashesArgumentMapper.class)
    public List<Map<String, Object>> getCrashes(
            @DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a crash report.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="crashReportDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateCrash(@DaoParam(name = "crashReportID") Integer crashReportID, @DaoParam(name = "CrashReport", type = com.inthinc.pro.model.CrashReport.class)Map<String, Object> crashReportMap) throws ProDAOException {
        return null;
    }

    // TODO: method is not implemented in hessian backend or dao -- possibly remove
    @Override
    @MethodDescription(description = "Marks the specified crash as 'forgiven'.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="crashReportDAO", daoMethod="forgiveCrash")
    public Map<String, Object> forgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
        return null;
    }

    // TODO: method is not implemented in hessian backend or dao -- possibly remove
    @Override
    @MethodDescription(description = "Removes a forgiven mark from the specified crash.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="crashReportDAO", daoMethod="unforgiveCrash")
    public Map<String, Object> unforgiveCrash(@DaoParam(name = "crashReportID") Integer crashReportID) throws ProDAOException {
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
    @DAODescription(daoID="locationDAO", daoMethod="getDriverLocations")
	public List<Map<String, Object>> getDVLByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID)
			throws ProDAOException {
		return null;
	}


	@Override
    @MethodDescription(description = "Get a list of all portal access points.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.SiteAccessPoint.class)
    @DAODescription(daoID="roleDAO", daoMethod="getSiteAccessPts")
	public List<Map<String, Object>> getSiteAccessPts() {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a list of all roles for the specifed account.", crudType=CrudType.READ,modelClass=com.inthinc.pro.model.security.Role.class)
    @DAODescription(daoID="roleDAO", daoMethod="getRoles")
	public List<Map<String, Object>> getRolesByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) {
		return null;
	}
	@Override
    @MethodDescription(description = "Get a subset of driver Events for the specifed group and time frame.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.event.Event.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilEventMapper.class)
    @DAODescription(daoID="eventDAO", daoMethod="getEventPage", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventPaginationArgumentMapper.class)
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
    @DAODescription(daoID="eventDAO", daoMethod="getEventCount", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventPaginationArgumentMapper.class, returnValueName="count")
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
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlag.class,
			mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagMapper.class)
    @DAODescription(daoID="redFlagDAO", daoMethod="getRedFlagPage", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventPaginationArgumentMapper.class)
	public List<Map<String, Object>> getRedFlagsPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Get a count of Red Flags for the specifed group and time frame.", crudType=CrudType.READ) 
    @DAODescription(daoID="redFlagDAO", daoMethod="getRedFlagCount", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.EventPaginationArgumentMapper.class, returnValueName="count")
	public Map<String, Object> getRedFlagsCount(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID, 
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "showExcluded", type=Boolean.class, inputDesc = "show excluded events") Integer includeForgiven,
            @DaoParam(name = "filterList", inputDesc = "Not Implemented -- enter any value") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Create a Role", crudType=CrudType.CREATE)
    @DAODescription(daoID="roleDAO", returnValueName=ID_KEY)
	public Map<String, Object> createRole(@DaoParam(name = "accountID", isAccountID=true) Integer acctID,
			@DaoParam(name = "Role", type = com.inthinc.pro.model.security.Role.class) Map<String, Object> roleMap) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Delete a Role", crudType=CrudType.DELETE)
    @DAODescription(daoID="roleDAO")
	public Map<String, Object> deleteRole(@DaoParam(name = "roleID") Integer roleID)
			throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetch a Role", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.Role.class)
    @DAODescription(daoID="roleDAO")
	public Map<String, Object> getRole(@DaoParam(name = "roleID", validator=ValidatorType.ROLE) Integer roleID) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Update a Role", crudType=CrudType.UPDATE, populateMethod="getRole")
    @DAODescription(daoID="roleDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
	public Map<String, Object> updateRole(@DaoParam(name = "roleID") Integer roleID,
			@DaoParam(name = "Role", type = com.inthinc.pro.model.security.Role.class) Map<String, Object> roleMap) throws ProDAOException {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetch the list of access points for the specifed user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.security.AccessPoint.class)
    @DAODescription(daoID="roleDAO", daoMethod="getUsersAccessPts")
	public List<Map<String, Object>> getUsersAccessPts(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of devices in the specified group (deep).", crudType=CrudType.READ)
    @DAODescription(daoID="reportDAO", daoMethod="getDeviceReportCount", returnValueName="count")
	public Map<String, Object> getDeviceReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
	        @DaoParam(name = "filterList") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of devices in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DeviceReportItem.class)
    @DAODescription(daoID="reportDAO", daoMethod="getDeviceReportPage")
	public List<Map<String, Object>> getDeviceReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of driver report items in the specified group (deep).", crudType=CrudType.READ)
    @DAODescription(daoID="reportDAO", daoMethod="getDriverReportCount", returnValueName="count")
	public Map<String, Object> getDriverReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
	        @DaoParam(name = "filterList") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of driver report items in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverReportItem.class)
    @DAODescription(daoID="reportDAO", daoMethod="getDriverReportPage")
	public List<Map<String, Object>> getDriverReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the count of idling report items in the specified group (deep) and time frame.", crudType=CrudType.READ)
    @DAODescription(daoID="reportDAO", daoMethod="getIdlingReportCount", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.IdlingReportArgumentMapper.class, returnValueName="count")
	public Map<String, Object> getIdlingReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "filterList") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of idling report items in the specified group (deep) and time frame.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.IdlingReportItem.class)
    @DAODescription(daoID="reportDAO", daoMethod="getIdlingReportPage", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.IdlingReportArgumentMapper.class)
	public List<Map<String, Object>> getIdlingReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}
	@Override
    @MethodDescription(description = "Get the count of idling report items for Vehicles in the specified group (deep) and time frame.", crudType=CrudType.READ)
    @DAODescription(daoID="reportDAO", daoMethod="getIdlingVehicleReportCount", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.IdlingReportArgumentMapper.class, returnValueName="count")
	public Map<String, Object> getIdlingVehicleReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "filterList") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of idling report items for Vehicles in the specified group (deep) and time frame.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.IdlingReportItem.class)
    @DAODescription(daoID="reportDAO", daoMethod="getIdlingVehicleReportPage", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.IdlingReportArgumentMapper.class)
	public List<Map<String, Object>> getIdlingVehicleReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}
	@Override
    @MethodDescription(description = "Get the count of vehicle report items in the specified group (deep).", crudType=CrudType.READ)
    @DAODescription(daoID="reportDAO", daoMethod="getVehicleReportCount", returnValueName="count")
	public Map<String, Object> getVehicleReportCount(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
	        @DaoParam(name = "filterList") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Get the subset of vehicle report items in the specified group (deep).", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.VehicleReportItem.class)
    @DAODescription(daoID="reportDAO", daoMethod="getVehicleReportPage")
	public List<Map<String, Object>> getVehicleReportPage(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID,
			@DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) {
		return null;
	}

	@Override
    @MethodDescription(description = "Clear the superuser role from the user. Returns count=1 if successful.", crudType=CrudType.UPDATE)
	@DAODescription(daoID="superuserDAO", daoMethod="clearSuperuser")
	public Map<String, Object> clearSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Determine if the user is a superuser.  Returns count=1 if true, or no result if false.", crudType=CrudType.READ_RESTRICTED,
            modelClass=java.lang.Boolean.class, mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilBooleanMapper.class)
    @DAODescription(daoID="superuserDAO", daoMethod="isSuperuser")
	public Map<String, Object> isSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Makes the specified user a superuser. Returns count=1 if successful.", crudType=CrudType.UPDATE)
    @DAODescription(daoID="superuserDAO", daoMethod="setSuperuser")
	public Map<String, Object> setSuperuser(@DaoParam(name = "userID") Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Create a forward command definition.", crudType=CrudType.CREATE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
    @DAODescription(daoID="forwardCommandDefDAO", daoMethod="create")
	public Map<String, Object> createFwdCmdDef(@DaoParam(name = "ForwardCommandDef", type = com.inthinc.pro.model.ForwardCommandDef.class) Map<String, Object> fwdCmdDefMap) {
		return null;
	}

	@Override
    @MethodDescription(description = "Delete a forward command definition.", crudType=CrudType.DELETE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
    @DAODescription(daoID="forwardCommandDefDAO")
	public Map<String, Object> deleteFwdCmdDef(@DaoParam(name = "fwdCmd ID") Integer fwdCmd) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches forward command definitions.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
    @DAODescription(daoID="forwardCommandDefDAO", daoMethod="getFwdCmdDefs")
	public List<Map<String, Object>> getFwdCmdDefs() {
		return null;
	}

	@Override
    @MethodDescription(description = "Update a forward command definition.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.model.ForwardCommandDef.class)
    @DAODescription(daoID="forwardCommandDefDAO", daoMethod="update", returnValueName=COUNT_KEY)
	public Map<String, Object> updateFwdCmdDef(@DaoParam(name = "ForwardCommandDef", type = com.inthinc.pro.model.ForwardCommandDef.class) Map<String, Object> fwdCmdDefMap) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches red flag alerts for the group hierarchy of the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    @DAODescription(daoID="redFlagAlertDAO", daoMethod="getRedFlagAlertsByUserIDDeep")
	public List<Map<String, Object>> getAlertsByUserIDDeep(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches red flag alerts for the user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    @DAODescription(daoID="redFlagAlertDAO", daoMethod="getRedFlagAlertsByUserID")
	public List<Map<String, Object>> getAlertsByUserID(@DaoParam(name = "userID", validator=ValidatorType.USER)Integer userID) {
		return null;
	}
    @Override
    @MethodDescription(description = "Fetches red flag alerts for the group.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.RedFlagAlert.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilRedFlagAlertMapper.class)
    @DAODescription(daoID="redFlagAlertDAO", daoMethod="getAlertsByTeamGroupID")
    public List<Map<String, Object>> getAlertsByTeamGroupID(@DaoParam(name = "groupID", validator=ValidatorType.GROUP) Integer groupID) {
        return null;
    }

	@Override
    @MethodDescription(description = "Fetches report schedules for the group hierarchy of the specified user.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.ReportSchedule.class)
    @DAODescription(daoID="reportScheduleDAO", daoMethod="getReportSchedulesByUserIDDeep")
	public List<Map<String, Object>> getReportPrefsByUserIDDeep(@DaoParam(name = "userID", validator=ValidatorType.USER) Integer userID) {
		return null;
	}

    @Override
    @MethodDescription(description = "Fetches the device settings definitions", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.DeviceSettingDefinition.class,
    		mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="getDeviceSettingDefinitions")
    public List<Map<String, Object>> getSettingDefs() {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings for one vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSetting.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="getVehicleSettings")
    public Map<String, Object> getVehicleSettings(@DaoParam(name = "vehicleID") Integer vehicleID) {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings for group hierarchy.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSetting.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="getVehicleSettingsByGroupIDDeep")
    public List<Map<String, Object>> getVehicleSettingsByGroupIDDeep(@DaoParam(name = "groupID") Integer groupID) {

        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the vehicle settings history for one vehicle.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.configurator.VehicleSettingHistory.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="getVehicleSettingsHistory")
    public List<Map<String, Object>> getVehicleSettingsHistory(@DaoParam(name = "vehicleID", validator=ValidatorType.VEHICLE)Integer vehicleID, 
            @DaoParam(name = "startTime", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm")Long startTime, 
            @DaoParam(name = "endTime", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm")Long endTime) {

        return null;
    }

    
    // TODO this throws 304 in hessian implementation and does not change the database 
    @Override
    @MethodDescription(description = "Set the device settings for a vehicle.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.configurator.model.VehicleSettings.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="setVehicleSettings", daoParamMapper=com.inthinc.pro.backing.dao.argmapper.RawArgumentMapper.class)
    public Map<String, Object> setVehicleSettings(@DaoParam(name = "vehicleID") Integer vehicleID, @DaoParam(name = "setMap") Map<Integer, String> setMap, @DaoParam(name = "userID") Integer userID, @DaoParam(name = "reason") String reason) {

        return null;
    }

    // TODO this throws 304 in hessian implementation and does not change the database 
    @Override
    @MethodDescription(description = "Update the device settings for a vehicle.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.configurator.model.VehicleSettings.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="updateVehicleSettings")
    public Map<String, Object> updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

        return null;
    }

    @Override
    @MethodDescription(description = "Get a subset of text message alerts for the specifed group and time frame.", 
    		crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TextMsgAlert.class
			)
    @DAODescription(daoID="textMsgAlertDAO", daoMethod="getTextMsgPage")
	public List<Map<String, Object>> getTextMsgPage(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "filterList", inputDesc = "Not Implemented -- enter any value") List<Map<String, Object>> filterList,
            @DaoParam(name = "pageParams", type = com.inthinc.pro.model.pagination.PageParams.class) Map<String, Object> pageParams) throws ProDAOException {
		return null;
	}
    
    @Override
    @MethodDescription(description = "Get a subset of sent text message alerts for the specifed group and time frame.", 
            crudType=CrudType.READ, modelClass=com.inthinc.pro.model.TextMsgAlert.class
            )
    @DAODescription(daoID="textMsgAlertDAO", daoMethod="getSentTextMsgsByGroupID")
    public List<Map<String, Object>> getSentTextMsgsByGroupID(
            @DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate) throws ProDAOException {
        return null;
    }

	@Override
    @MethodDescription(description = "Get a count of Text messages for the specifed group and time frame.", crudType=CrudType.READ) 
    @DAODescription(daoID="textMsgAlertDAO", daoMethod="getTextMsgCount", returnValueName="count")
	public Map<String, Object> getTextMsgCount(
			@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID,
            @DaoParam(name = "startDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long startDate,
            @DaoParam(name = "endDate", type=java.util.Date.class, inputDesc = "MM/dd/yyyy hh:mm") Long endDate,
            @DaoParam(name = "filterList", inputDesc = "Not Implemented -- enter any value") List<Map<String, Object>> filterList) {
		return null;
	}

	@Override
    @MethodDescription(description = "Fetches the list of all the sensitivity slider values.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.SensitivitySliderValues.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilConfiguratorMapper.class)
    @DAODescription(daoID="configuratorDAO", daoMethod="getSensitivitySliderValues")
	public List<Map<String, Object>> getSensitivitySliderValues() {
		return null;
	}
	
	@Override
	@MethodDescription(description = "Sends an UPDATE_ZONES forward command to all devices.", crudType=CrudType.UPDATE)
	@DAODescription(daoID="zoneDAO", daoMethod="publishZones")
    public Map<String, Object> publishZones(@DaoParam(name = "accountID", isAccountID=true) Integer accountID) {
        return null;
    }
    @Override
    @MethodDescription(description = "Create a phone control record for a driver.", crudType=CrudType.CREATE, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO")
    public Map<String, Object> createCellblock(@DaoParam(name = "driverID")Integer driverID, 
            @DaoParam(name = "PhoneControl", type = com.inthinc.pro.model.Cellblock.class)Map<String, Object> cellblockMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Delete a phone control record for a driver.", crudType=CrudType.DELETE, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO")
    public Map<String, Object> deleteCellblock(@DaoParam(name = "driverID")Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches a phone control record for a driver.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO")
    public Map<String, Object> getCellblock(@DaoParam(name = "driverID")Integer driverID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Update a phone control record for a driver.", crudType=CrudType.UPDATE, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO", returnValueName=COUNT_KEY, daoParamMapper=com.inthinc.pro.backing.dao.argmapper.CrudUpdateArgumentMapper.class)
    public Map<String, Object> updateCellblock(@DaoParam(name = "driverID")Integer driverID, 
            @DaoParam(name = "PhoneControl", type = com.inthinc.pro.model.Cellblock.class)Map<String, Object> cellblockMap) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the phone control record for a phon number.", crudType=CrudType.NOT_AVAILABLE, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO", daoMethod="findByPhoneNumber")
    public Map<String, Object> findByPhoneNumber(@DaoParam(name = "phoneID")String phoneID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the list of all the phone control records for an account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO", daoMethod="getCellblocksByAcctID")
    public List<Map<String, Object>> getCellblocksByAcctID(@DaoParam(name = "accountID", isAccountID=true)Integer acctID) {
         return null;
    }

    @Override
    @MethodDescription(description = "Fetches the list of all the phone control records for drivers with disabled phones.", crudType=CrudType.NOT_AVAILABLE, modelClass=com.inthinc.pro.model.Cellblock.class,
            mapperClass=com.inthinc.pro.backing.dao.mapper.DaoUtilCellblockMapper.class)
    @DAODescription(daoID="phoneControlDAO", daoMethod="getDriversWithDisabledPhones")
    public List<Map<String, Object>> getDriversWithDisabledPhones(Integer siloID) {
        return null;
    }

    @Override
    @MethodDescription(description = "Fetches the list of all the drivers names for the drivers in a group hierarchy.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverName.class)
    @DAODescription(daoID="driverDAO", daoMethod="getDriverNames")
    public List<Map<String, Object>> getDriverNamesByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID) throws ProDAOException {
        return null;
    }
    
    @Override
    @MethodDescription(description = "Fetches the list of all the vehicle names for the vehicles in a group hierarchy.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriverName.class)
    @DAODescription(daoID="vehicleDAO", daoMethod="getVehicleNames")
   public List<Map<String, Object>> getVehicleNamesByGroupIDDeep(@DaoParam(name = "groupID", validator=ValidatorType.GROUP)Integer groupID) throws ProDAOException {
        return null;
    }

    @Override
    @MethodDescription(description = "Gets a list of persons in account.", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Person.class )
    @DAODescription(daoID="personDAO", daoMethod="getPeopleInAccount")
    public List<Map<String, Object>> getPersonsByAcctID(@DaoParam(name = "accountID", isAccountID=true) Integer acctID) throws ProDAOException {
        return null;
    }
    @Override
    @MethodDescription(description = "Fetch a driver by its associated empID", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.Person.class)
    @DAODescription(daoID="personDAO", daoMethod="findByEmpID")
	public Map<String, Object> getPersonByEmpid(@DaoParam(name = "accountID", isAccountID=true) Integer acctID, @DaoParam(name = "empID") String empID) throws ProDAOException {
		return null;
	}
}