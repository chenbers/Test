package com.inthinc.pro.dao;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.Interval;


import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.model.hos.HOSVehicleMileage;

public interface HOSDAO  {
    
    List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly) throws SQLException;
    List<HOSVehicleDayData> getHOSVehicleDataByDay(Integer driverID, Interval interval) throws SQLException;
    List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) throws SQLException;
    List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) throws SQLException;
    List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) throws SQLException;

}
