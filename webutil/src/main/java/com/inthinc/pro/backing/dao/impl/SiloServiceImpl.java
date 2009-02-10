package com.inthinc.pro.backing.dao.impl;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class SiloServiceImpl implements SiloService
{

    @Override
    public Map<String, Object> createAcct(Integer siloID, Map<String, Object> acctMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createAddr(Integer acctID, Map<String, Object> addrMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createDevice(Integer acctID, Map<String, Object> deviceMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createDriver(Integer acctID, Map<String, Object> driverMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createRedFlagAlert(Integer acctID, Map<String, Object> redFlagAlertMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createTablePref(Integer userID, Map<String, Object> tablePrefMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createZone(Integer acctID, Map<String, Object> zoneMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createZoneAlert(Integer acctID, Map<String, Object> zoneAlertMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteAcct(Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteDevice(Integer deviceID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deletePerson(Integer personID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteRedFlagAlert(Integer redFlagAlertID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteTablePref(Integer tablePrefID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteUser(Integer userID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteZone(Integer zoneID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteZoneAlert(Integer zoneAlertID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteZoneAlertsByZoneID(Integer zoneID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getAcct(@DaoParam(name="accountID")Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getAccts(@DaoParam(name="siloID")Integer siloID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getAddr(@DaoParam(name="addrID")Integer addrID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllAcctIDs()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getDevice(@DaoParam(name="deviceID")Integer deviceID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDevicesByAcctID(@DaoParam(name="accountID")Integer accountID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getDriver(@DaoParam(name="driverID")Integer driverID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupID(@DaoParam(name="groupID")Integer groupID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesNearLoc(@DaoParam(name="groupID")Integer groupID, @DaoParam(name="count")Integer numof, @DaoParam(name="lat")Double lat, @DaoParam(name="lng")Double lng)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Map<String, Object>> getFwdCmds(@DaoParam(name="deviceID")Integer deviceID, @DaoParam(name="status", inputDesc="ALL = 0,QUEUED = 1,SENT = 2,RECEIVED=3,ACKNOWLEDGED = 4,UNSUPPORTED = 5,BAD_DATA = 6,REMOVED = 7")Integer status)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGroup(@DaoParam(name="groupID")Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getGroupsByAcctID(@DaoParam(name="accountID")Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getID(@DaoParam(name="name", inputDesc="username, email, mcmid, or vin")String name, @DaoParam(name="value")String value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getLastLoc(@DaoParam(name="id", inputDesc="driverID or vehicleID")Integer id, @DaoParam(name="type", inputDesc="1 - DRIVER, 2 - VEHICLE")Integer reqType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getLastTrip(@DaoParam(name="id", inputDesc="driverID or vehicleID")Integer id, @DaoParam(name="type", inputDesc="1 - DRIVER, 2 - VEHICLE")Integer reqType) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getNextSilo()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriverNote(@DaoParam(name="driverID")Integer driverID, 
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long endDate,
            @DaoParam(name="includeForgiven", inputDesc="1 - include forgiven, 0 - exclude forgiven")Integer includeForgiven,
            @DaoParam(name="types[]", inputDesc="comma sep list of event types")Integer[] types)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getNoteByMiles(@DaoParam(name="driverID")Integer driverID, @DaoParam(name="milesBack")Integer milesBack, @DaoParam(name="types[]", inputDesc="comma sep list of event types")Integer[] types)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getPerson(@DaoParam(name="personID")Integer personID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Map<String, Object>> getRecentNotes(@DaoParam(name="groupID")Integer groupID, @DaoParam(name="count")Integer eventCnt, 
            @DaoParam(name="types[]", inputDesc="comma sep list of event types")Integer[] types) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, Object> forgive(@DaoParam(name="driverID")Integer driverID, @DaoParam(name="noteID")Long noteID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, Object> unforgive(@DaoParam(name="driverID")Integer driverID, @DaoParam(name="noteID")Long noteID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    } 

    @Override
    public Map<String, Object> getRedFlagAlert(@DaoParam(name="redFlagAlertID")Integer redFlagAlertID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getRedFlagAlertsByAcctID(@DaoParam(name="accountID")Integer accountID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getRedFlags(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long endDate)
            throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getRoles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getSensitivityMaps()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getStates()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getTablePref(@DaoParam(name="tablePrefID")Integer tablePrefID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getTablePrefsByUserID(@DaoParam(name="userID")Integer userID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getTimezones()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getTrips(@DaoParam(name="id", inputDesc="driverID or vehicleID")Integer id, 
                                        @DaoParam(name="type", inputDesc="1 - DRIVER, 2 - VEHICLE")Integer reqType, 
                                        @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long startDate,
                                        @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long endDate
                                        ) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUser(@DaoParam(name="userID")Integer userID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupID(@DaoParam(name="groupID")Integer groupID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getVehicle(@DaoParam(name="vehicleID")Integer vehicleID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleNote(@DaoParam(name="vehicleID")Integer vehicleID, 
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Long endDate,
            @DaoParam(name="includeForgiven", inputDesc="1 - include forgiven, 0 - exclude forgiven")Integer includeForgiven,
            @DaoParam(name="types", inputDesc="comma sep list of event types")Integer[] types)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleNoteByMiles(@DaoParam(name="vehicleID")Integer vehicleID, Integer milesBack, @DaoParam(name="types", inputDesc="comma sep list of event types")Integer[] types)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupID(@DaoParam(name="groupID")Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getZone(@DaoParam(name="zoneID")Integer zoneID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getZoneAlert(@DaoParam(name="zoneAlertID")Integer zoneAlertID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getZoneAlertsByAcctID(@DaoParam(name="accountID")Integer accountID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getZonesByAcctID(@DaoParam(name="accountID")Integer accountID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> queueFwdCmd(Integer deviceID, Map<String, Object> fwdMap)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> clrVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateAcct(Integer acctID, Map<String, Object> acctMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateAddr(Integer addrID, Map<String, Object> addrMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateDevice(Integer deviceID, Map<String, Object> deviceMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateFwdCmd(Integer fwdID, Integer status)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateRedFlagAlert(Integer redFlagAlertID, Map<String, Object> redFlagAlertMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateTablePref(Integer tablePrefID, Map<String, Object> tablePrefMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateZone(Integer zoneID, Map<String, Object> zoneMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateZoneAlert(Integer zoneAlertID, Map<String, Object> zoneAlertMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getDriverByPersonID(@DaoParam(name="personID")Integer personID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUserByPersonID(@DaoParam(name="personID")Integer personID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupIDDeep(@DaoParam(name="groupID")Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getGroupsByGroupIDDeep(@DaoParam(name="groupID")Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupIDDeep(@DaoParam(name="groupID")Integer groupID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getMessages(@DaoParam(name="groupID")Integer siloID, @DaoParam(name="deliveryMethod",inputDesc="1-PHONE,2-TEXT_MESSAGE,3-EMAIL")Integer deliveryMethodType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupIDDeep(@DaoParam(name="groupID")Integer groupID)
    {
        // TODO Auto-generated method stub
        return null;
    }
//rfid - driverID
    @Override
    public Map<String, Object> getIDLong(@DaoParam(name="name", inputDesc="rfid")String name, @DaoParam(name="value")Long value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createReportPref(Integer acctID, Map<String, Object> reportPrefMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteReportPref(Integer reportPrefID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getReportPref(@DaoParam(name="reportPrefID")Integer reportPrefID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByAcctID(@DaoParam(name="acctID")Integer acctID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByUserID(@DaoParam(name="userID")Integer userID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateReportPref(Integer reportPrefID, Map<String, Object> reportPrefMap) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }
}
