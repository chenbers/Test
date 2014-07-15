package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.hos.testData.MockData;
import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.performance.model.PayrollHOSRec;
import com.inthinc.pro.reports.util.DateTimeUtil;

//public class DriverHoursReportCriteriaTest extends BaseUnitTest {
public class DriverHoursReportCriteriaTest {
    
    // Constant values
    private static Integer GROUP_ID = new Integer(2);
    private static Integer ACCOUNT_ID = new Integer(1);
    private static Integer DRIVER_ID = new Integer(1);
    private final String GROUP_FULL_NAME = "Group Full Name";
    private final Locale LOCALE = Locale.US;
    private final Boolean showDecimalHours = false;
    
    // Static Variables
    private static Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    
    private static DateTime INITIAL_DATE = new DateTime();
    private static DateTime CREATED_DATE;
    private static DateTime LAST_UPDATED_DATE;
    private static DateTime LOG_TIME;
    
    private static HOSRecord HOS_RECORD;
    private static Driver DRIVER;
    
    private static List<DateTime> DAY_LIST;
    
    static {
        CREATED_DATE = INITIAL_DATE.minusDays(1);
        LAST_UPDATED_DATE = INITIAL_DATE.minusDays(1);
        LOG_TIME = INITIAL_DATE.minusDays(1);
        
        HOS_RECORD = new HOSRecord();
        HOS_RECORD.setStatus(HOSStatus.DRIVING);
        HOS_RECORD.setCreated(CREATED_DATE.toDate());
        HOS_RECORD.setDateLastUpdated(LAST_UPDATED_DATE.toDate());
        HOS_RECORD.setLogTime(LOG_TIME.toDate());
        
        DRIVER = MockData.createMockDriver(ACCOUNT_ID, DRIVER_ID, GROUP_ID, "Test_FN_" + DRIVER_ID, "Test_LN_" + DRIVER_ID, Status.ACTIVE);
        
        DAY_LIST = DateTimeUtil.getDayList(INTERVAL, DateTimeZone.forTimeZone(DRIVER.getPerson().getTimeZone()));
    }
    
    // JMockit mocks
    @Mocked
    private AccountDAO accountDAOMock;
    @Mocked
    private HOSDAO hosDAOMock;
    @Mocked
    private DriverDAO driverDAOMock;
    
    private GroupHierarchy groupHierarchyMock;
    
    // The System Under Test
    private DriverHoursReportCriteria reportCriteriaSUT = new DriverHoursReportCriteria(LOCALE);
    
    /**
     * Tests the init method of the DriverHoursReportCriteria class.
     */
    @Test
    public void testInit() {
        
        // General initializations
        reportCriteriaSUT.setAccountDAO(accountDAOMock);
        reportCriteriaSUT.setHosDAO(hosDAOMock);
        reportCriteriaSUT.setDriverDAO(driverDAOMock);
        
        // JMockit Documentation:
        // http://jmockit.googlecode.com/svn/trunk/www/tutorial/BehaviorBasedTesting.html
        //
        // ------------------------------------------------------------------
        // 1. First declare what we expect from the mocks during the test
        new Expectations() {
            {
                groupHierarchyMock = getMockGroupHierarchy();
                
                Account mockAccount = getMockAccount();
                accountDAOMock.findByID(ACCOUNT_ID);
                returns(mockAccount);
                
                driverDAOMock.getDrivers(GROUP_ID);
                returns(getMockDriverList());
                
                driverDAOMock.findByID(DRIVER_ID);
                returns(DRIVER);
                
                List<HOSRecord> mockHosRecordList = getMockHosRecords();
                hosDAOMock.getHOSRecords(DRIVER_ID, (Interval) any, true);
                returns(mockHosRecordList);
            }
            
            private Group buildGroup(Integer groupID, Integer parentID, String groupName) {
                return new Group(groupID, ACCOUNT_ID, groupName, 1, GroupType.TEAM, 0, GROUP_FULL_NAME, 0, new LatLng(0, 0));
            }
            
            private GroupHierarchy getMockGroupHierarchy() {
                List<Group> groupList = new ArrayList<Group>();
                groupList.add(buildGroup(GROUP_ID, 1, GROUP_FULL_NAME));
                GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
                
                return groupHierarchy;
            }
            
            private List<HOSRecord> getMockHosRecords() {
                List<HOSRecord> retval = new ArrayList<HOSRecord>();
                retval.add(HOS_RECORD);
                
                return retval;
            }
            
            private Account getMockAccount() {
                Account retVal = MockData.createMockAccount();
                
                return retVal;
            }
            
            private List<Driver> getMockDriverList() {
                List<Driver> retVal = new ArrayList<Driver>();
                retVal.add(DRIVER);
                
                return retVal;
            }
        };
        
        // ------------------------------------------------------------------
        // 2. Second, we run the actual method to be tested
        List<Integer> groupIdList = new ArrayList<Integer>();
        groupIdList.add(GROUP_ID);
//        vali
        reportCriteriaSUT.init(groupHierarchyMock, groupIdList, INTERVAL,showDecimalHours);

        // ------------------------------------------------------------------
        // 3. Third we verify the results
        
        // We can also perform regular JUnit assertions
        @SuppressWarnings("unchecked")
        List<DriverHours> driverHoursList = reportCriteriaSUT.getMainDataset();
        assertNotNull(driverHoursList);
        assertTrue(driverHoursList.size() == 1);
        
        DriverHours driverHours = driverHoursList.get(0);
        assertEquals(driverHours.getGroupName(), GROUP_FULL_NAME);
        
        Double driverPayRollHours = this.getDriverPayrollData().get(0).getTotalAdjustedMinutes() / 60d;
        assertEquals(driverPayRollHours, driverHours.getHours());
        
    }
    
    /**
     * Tests that the sort order produced by the Comparator is correct.
     */
    @Test
    public void testComparatorSort() {
        DriverHours[] hoursArray = new DriverHours[3];
        hoursArray[0] = getHours("GroupB", "DriverX");
        hoursArray[1] = getHours("GroupA", "DriverZ");
        hoursArray[2] = getHours("GroupA", "DriverY");
        
        List<DriverHours> hoursList = Arrays.asList(hoursArray.clone());
        Collections.sort(hoursList, reportCriteriaSUT.new DriverHoursComparator());
        
        // verify the correct order
        assertTrue(EqualsBuilder.reflectionEquals(hoursArray[2], hoursList.get(0))); // AY
        assertTrue(EqualsBuilder.reflectionEquals(hoursArray[1], hoursList.get(1))); // AZ
        assertTrue(EqualsBuilder.reflectionEquals(hoursArray[0], hoursList.get(2))); // BX
    }
    
    private DriverHours getHours(String groupName, String driverName) {
        DriverHours hours = new DriverHours();
        hours.setGroupName(groupName);
        hours.setDriverName(driverName);
        
        return hours;
    }
    
    private List<PayrollHOSRec> getCompensatedRecList() {
        List<PayrollHOSRec> retVal = new ArrayList<PayrollHOSRec>();
        Interval driverInterval = DateTimeUtil.getStartEndIntervalInTimeZone(INTERVAL, DateTimeZone.forTimeZone(DRIVER.getPerson().getTimeZone()));
        Date endDate = driverInterval.getEnd().toDate();
        
        retVal.add(new PayrollHOSRec(HOS_RECORD.getStatus(), HOS_RECORD.getLogTime(), 0l));
        
        if (endDate.after(INITIAL_DATE.toDate()))
            endDate = INITIAL_DATE.toDate();
        
        for (PayrollHOSRec rec : retVal) {
            rec.setTotalSeconds(deltaSeconds(endDate, rec.getLogTimeDate()));
            endDate = rec.getLogTimeDate();
        }
        
        return retVal;
    }
    
    private List<PayrollHOSRec> getListForDay() {
        List<PayrollHOSRec> hosList = this.getCompensatedRecList();
        List<PayrollHOSRec> retVal = new ArrayList<PayrollHOSRec>();
        
        for (DateTime day : DAY_LIST) {
            
            for (PayrollHOSRec log : hosList) {
                Date logTime = log.getLogTimeDate();
                Date logEndTime = new Date(logTime.getTime() + (log.getTotalSeconds() * 1000));
                Date startTime;
                Date endTime;
                long seconds;
                
                if ((logTime.before(day.toDate()) || logTime.equals(day.toDate())) && logEndTime.after(day.toDate())) {
                    startTime = day.toDate();
                    endTime = (logEndTime.after(day.plusDays(1).toDate())) ? day.plusDays(1).toDate() : logEndTime;
                    seconds = (endTime.getTime() - startTime.getTime()) / 1000;
                    retVal.add(new PayrollHOSRec(log.getStatus(), startTime, seconds));
                }
            }
        }
        
        return retVal;
    }
    
    private List<PayrollData> getDriverPayrollData() {
        List<PayrollData> retVal = new ArrayList<PayrollData>();
        
        for (DateTime day : DAY_LIST) {
            
            Map<HOSStatus, Long> dayMap = new HashMap<HOSStatus, Long>();
            Long dayCompTotal = 0l;
            
            for (PayrollHOSRec log : this.getListForDay()) {
                Long seconds = 0l;
                
                seconds += log.getTotalSeconds();
                dayMap.put(log.getStatus(), seconds);
                
                dayCompTotal += log.getTotalSeconds();
            }
            
            int totalCompMinutes = 0;
            
            for (HOSStatus status : dayMap.keySet()) {
                long minutes = dayMap.get(status) / 60l;
                
                if (dayMap.get(status) % 60 >= 30)
                    minutes++;
                
                totalCompMinutes += minutes;
                DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(DRIVER.getPerson().getTimeZone());
                PayrollData item = new PayrollData(DRIVER.getGroupID(), GROUP_FULL_NAME, "", DRIVER.getDriverID(), DRIVER.getPerson().getFullName(), null, day.toDate(), status, totalCompMinutes, day, driverTimeZone,true);
                
                retVal.add(item);
            }
            
        }
        
        return retVal;
    }
    
    private Long deltaSeconds(Date endTime, Date startTime) {
        Long retVal = (endTime.getTime() - startTime.getTime()) / 1000l;
        if (retVal < 0) {
            return 0l;
        }
        
        return retVal;
    }
    
}