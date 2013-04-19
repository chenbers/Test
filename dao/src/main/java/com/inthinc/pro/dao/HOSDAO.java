package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;

public interface HOSDAO extends GenericDAO<HOSRecord, Long> {
    
    List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly);
    List<HOSRecord> getHOSRecordsFilteredByInterval(Integer driverID, Interval interval, Boolean driverStatusOnly);
    List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval);
    List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver);
    List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver);
    
    HOSDriverLogin getDriverForEmpidLastName(String employeeId, String lastName);
    Map<Integer, Long> fetchMileageForDayVehicle(DateTime day, Integer vehicleID);
    
    HOSOccupantInfo getOccupantInfo(Integer driverID);
    List<HOSRecord> getHOSRecordsForCommAddress(String address, List<HOSRecord> paramList);
    HOSDriverLogin getDriverForEmpid(String commAddress, String employeeId);
    HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer);
    String fetchIMEIForOccupant(Integer driverID, Integer startTime);
    List<HOSOccupantHistory> getHOSOccupantHistory(HOSDriverLogin driverLogin);
    List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId);
    void logoutDriverFromDevice(String commAddress, String employeeId, long logoutTime,  int odometer);
    
    List<HOSRecord> getRecordsForVehicle(Integer vehicleID, Interval interval, Boolean driverStatusOnly);
    List<HOSRecord> getFuelStopRecordsForVehicle(Integer vehicleID, Interval interval);
    
    Long createFromNote(HOSRecord hosRecord);
    
    LatLng getVehicleHomeOfficeLocation(Integer vehicleID);

}
