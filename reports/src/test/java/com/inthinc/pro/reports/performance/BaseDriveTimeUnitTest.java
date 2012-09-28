package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.reports.BaseUnitTest;

public class BaseDriveTimeUnitTest extends BasePerformanceUnitTest {
    
    protected static final Integer GROUP_ID = Integer.valueOf(1);
    protected static final String GROUP_NAME = "TEST GROUP";


    class MockDriveTimeDAO implements DriveTimeDAO{

        Interval interval;
        int numHours;
        
        public MockDriveTimeDAO(Interval interval, int numHours)
        {
            this.interval = interval;
            this.numHours = numHours;
        }
        @Override
        public List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval) {
            List<DriveTimeRecord> list = new ArrayList<DriveTimeRecord>();
            LocalDate localDate = new LocalDate(interval.getStart());
            DateTime day = localDate.toDateTimeAtStartOfDay();

            for (int i = 1; i < 5; i++) {
                  list.add(new DriveTimeRecord(day, i, "V" + i, i, (long)numHours * 3600l));
                  list.add(new DriveTimeRecord(interval.getEnd(), i, "V" + i, i, (long)numHours * 3600l)); // should ignore this one
            }
            return list;
        }
    }
}
