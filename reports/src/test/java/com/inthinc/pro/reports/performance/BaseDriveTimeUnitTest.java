package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Vehicle;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.reports.hos.testData.MockData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        public Long getEngineHoursAtDate(Vehicle vehicle, Date evDate) {
            return null;
        }

        @Override
        public Long getDriveTimeSum(Vehicle vehicle) {
            throw new NotImplementedException();
        }

        @Override
        public Long getDriveOdometerSum(Vehicle vehicle) {
            throw new NotImplementedException();
        }

        @Override
        public Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate) {
            throw new NotImplementedException();
        }

        @Override
        public Long getDriveTimeAtDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate) {
            throw new NotImplementedException();
        }

        @Override
        public Date getPrevEventDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate, Integer deviceId) {
            throw new NotImplementedException();
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
    
    class MockAccountDAO implements AccountDAO {

        @Override
        public Account findByID(Integer id) {
            return MockData.createMockAccount();
        }

        @Override
        public Integer create(Integer id, Account entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer update(Account entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer create(Account entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Account> getAllAcctIDs() {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public List<Long> getAllValidAcctIDs() {
            // TODO Auto-generated method stub
            return null;
        }

    }
    
    class MockHOSDAO implements HOSDAO {

        @Override
        public HOSRecord findByID(Long id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Long create(Long id, HOSRecord entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer update(HOSRecord entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer deleteByID(Long id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly) {
            List<HOSRecord> retval = new ArrayList<HOSRecord>();
            
            DateTime initialTime = new DateTime();
            DateTime createdDate = initialTime.minusDays(1);
            DateTime lastUpdatedDate = initialTime.minusDays(1);
            DateTime logTime = initialTime.minusDays(1);
            
            HOSRecord record = new HOSRecord();
            record.setStatus(HOSStatus.DRIVING);
            record.setCreated(createdDate.toDate());
            record.setDateLastUpdated(lastUpdatedDate.toDate());
            record.setLogTime(logTime.toDate());
            
            retval.add(record);
            
            return retval;
        }

        @Override
        public List<HOSRecord> getHOSRecordsFilteredByInterval(Integer driverID, Interval interval, Boolean driverStatusOnly) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin getDriverForEmpidLastName(String employeeId, String lastName) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Map<Integer, Long> fetchMileageForDayVehicle(DateTime day, Integer vehicleID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSOccupantInfo getOccupantInfo(Integer driverID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSRecordsForCommAddress(String address, List<HOSRecord> paramList) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin getDriverForEmpid(String commAddress, String employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String fetchIMEIForOccupant(Integer driverID, Integer startTime) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantHistory> getHOSOccupantHistory(HOSDriverLogin driverLogin) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void logoutDriverFromDevice(String commAddress, String employeeId, long logoutTime, int odometer) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public List<HOSRecord> getRecordsForVehicle(Integer vehicleID, Interval interval, Boolean driverStatusOnly) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getFuelStopRecordsForVehicle(Integer vehicleID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Long createFromNote(HOSRecord hosRecord) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public LatLng getVehicleHomeOfficeLocation(Integer vehicleID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSDeltaRecords(Integer driverID, Integer deviceID, Date deltaTime) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean otherHosRecordExistsForDriverTimestamp(Integer driverID, Date dateTime, Long hosLogID) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public List<HOSRecord> getHOSRecordAtSummaryTime(Integer driverID, Integer vehicleID, Date summaryTime, Date startTime, Date endTime) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
