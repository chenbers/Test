package com.inthinc.pro.dao.mock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.VehicleEventData;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.Interval;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;

public class MockDriveTimeDAO implements DriveTimeDAO {
    @Override
    public Long getDriveTimeSum(Vehicle vehicle) {
        throw new NotImplementedException();
    }

    @Override
    public Long getDriveOdometerSum(Vehicle vehicle) {
        throw new NotImplementedException();
    }

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

    @Override
    public Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate) {
        throw new NotImplementedException();
    }

    @Override
    public Date getPrevEventDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate, Integer deviceId) {
        throw new NotImplementedException();
    }

    @Override
    public Map<Integer, Date> getPrevEventDates(VehicleEventData vehicleEventData) {
        throw new NotImplementedException();
    }

    @Override
    public Map<Integer, String> getDriveOdometersAtDates(VehicleEventData vehicleEventData) {
        throw new NotImplementedException();
    }

    @Override
    public Long getDriveTimeAtDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate) {
        throw new NotImplementedException();
    }

    @Override
    public Long getEngineHoursAtDate(Vehicle vehicle, Date evDate) {
        throw new NotImplementedException();
    }
}
