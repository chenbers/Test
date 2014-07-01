package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Vehicle;
import org.joda.time.Interval;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;


public interface DriveTimeDAO  {
    
    List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval);
    List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval);
    Long getDriveTimeSum(Vehicle vehicle);
    Long getDriveOdometerSum(Vehicle vehicle);
}
