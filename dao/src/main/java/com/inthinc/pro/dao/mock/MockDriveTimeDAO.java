package com.inthinc.pro.dao.mock;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;

public class MockDriveTimeDAO implements DriveTimeDAO {

    @Override
    public List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval) {
        // TODO Auto-generated method stub
        return null;
    }

}
