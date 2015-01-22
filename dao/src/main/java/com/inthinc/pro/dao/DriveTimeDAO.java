package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.VehicleEventData;
import org.joda.time.Interval;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;


public interface DriveTimeDAO  {
    
    List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval);
    List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval);
    Long getDriveTimeSum(Vehicle vehicle);
    Long getDriveOdometerSum(Vehicle vehicle);
    Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate);
    Long getEngineHoursAtDate(Vehicle vehicle, Date evDate);
    Long getDriveTimeAtDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate);
    Date getPrevEventDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate, Integer deviceId);
    Map<Integer, Date> getPrevEventDates(VehicleEventData vehicleEventData);
    Map<Integer, String> getDriveOdometersAtDates(VehicleEventData vehicleEventData);
}
