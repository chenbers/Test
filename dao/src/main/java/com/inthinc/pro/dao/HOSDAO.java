package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;

public interface HOSDAO extends GenericDAO<HOSRecord, Integer> {
    
    List<HOSRecord> getHOSRecords(Integer driverID, Interval interval);
    List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, List<HOSStatus> statusFilterList);
    
    List<HOSVehicleDayData> getHOSVehicleDataByDay(Integer driverID, Interval interval);
    List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval);

}
