package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;


public interface DriveTimeDAO  {
    
    List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval);
    List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval);
    Map<Integer, Integer> getDriverLoginCountsForGroup(Integer groupID, Interval interval);



}
