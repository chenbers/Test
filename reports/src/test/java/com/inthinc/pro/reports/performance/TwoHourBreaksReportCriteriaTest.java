package com.inthinc.pro.reports.performance;

import com.inthinc.hos.model.DayData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.model.BreakDataSummary;
import com.inthinc.pro.reports.util.DateTimeUtil;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class TwoHourBreaksReportCriteriaTest {
    
    @Mocked Interval mockInterval;
    @Mocked GroupHierarchy mockGroupHierarchy;
    @Mocked Driver mockDriver;
    @Mocked DayData mockDayData;
    @Mocked Locale mockLocale;
    @Mocked HOSUtil mockHOSUtil;
    @Mocked ReportType mockReportType;
    @Mocked GroupListReportCriteria mockGroupListReportCriteria;
    @Mocked BreakDataSummary mockBreakDataSummary;
    @Mocked Account mockAccount;
    @Mocked HOSRec mockHOSRec;
    @Mocked DateTimeFormatter mockDateTimeFormatter;
    @Mocked RuleSetFactory mockRuleSetFactory;
    @Mocked HOSRules mockHOSRules;
    @Mocked Person mockPerson;
    @Mocked TimeZone mockTimeZone;
    @Mocked DateTimeZone mockDateTimeZone;
    @Mocked Address mockAddress;
    @Mocked AccountDAO mockAccountDAO;
    @Mocked Group mockGroup;
    @Mocked DriverDAO mockDriverDAO;
    @Mocked DateTimeUtil mockDateTimeUtil;
    @Mocked HOSDAO mockHosDAO;
    @Mocked DateTime mockDateTime;
    @Mocked Date mockDate;
    
    @Test
    public void testInit() {
        
        final TwoHourBreaksReportCriteria sut = new TwoHourBreaksReportCriteria(mockLocale);
        
        final List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(1);
        groupIDList.add(2);
        groupIDList.add(3);
        
        final List<Group> mockGroupList = new ArrayList<Group>();
        
        final List<Driver> mockDriverList = new ArrayList<Driver>();
        mockDriverList.add(mockDriver);
        
        final Map<Driver, List<HOSRec>> mockHOSMap = new HashMap<Driver, List<HOSRec>> ();
        
        final Interval testInterval = new Interval(mockDateTime);
        
        new NonStrictExpectations(sut) {{
            mockGroupHierarchy.getTopGroup(); result = mockGroup;
            mockGroup.getAccountID(); result = 1;
            new HashMap<Driver, List<HOSRec>> (); result = mockHOSMap;
            invoke(sut, "getReportGroupList", groupIDList, mockGroupHierarchy); result = mockGroupList;
            invoke(sut, "getReportDriverList", mockGroupList); result = mockDriverList;
            invoke(sut, "getDriverDAO"); result = mockDriverDAO;
            mockDriver.getDriverID(); result = 1;
            
            // includeDriver needs to return true so we can run the if statement
            invoke(sut, "includeDriver", mockDriverDAO, 1, mockInterval); result = true;
            
            // getDot() needs to return something so we run the entire if statement
            mockDriver.getDot(); result = RuleSetType.US_7DAY;
            
            mockDriver.getPerson(); result = mockPerson;
            DateTimeUtil.getExpandedInterval((Interval)any, (DateTimeZone)any, anyInt, anyInt); result = mockInterval;
            mockInterval.getEnd(); result = mockDateTime;
            
            // Mock the initDataSet() method so we don't execute it
            invoke(sut, "initDataSet", withAny(Interval.class), withAny(Account.class), withAny(GroupHierarchy.class), withAny(Map.class));
        }};
        
        sut.setAccountDAO(mockAccountDAO);
        sut.setHosDAO(mockHosDAO);
        

        sut.setDateTimeFormatter(mockDateTimeFormatter);
        
        sut.init(mockGroupHierarchy, groupIDList, mockInterval);
        
        new Verifications() {{
            testInterval.getEnd(); times = 1;
            sut.initDataSet((Interval)any, (Account)any, (GroupHierarchy)any, (Map)any); times = 1;
        }};
        
        
    }
//    
    @Test
    public void testInitDataSet() {
        
        final List <DayData> testList = new ArrayList<DayData>();
        
        new Expectations() {{
            mockAccount.getAcctName(); result = "Test Account";
            mockAccount.getAddress(); result = mockAddress;
            mockAccount.getAddress(); result = mockAddress;
            mockAddress.getDisplayString(); result = "Test Account Address";
            new ArrayList<DayData>(); result = testList;
            mockDriver.getDot(); result = RuleSetType.US_7DAY;
            RuleSetFactory.getRulesForRuleSetType(RuleSetType.US_7DAY); result = mockHOSRules;
            mockDriver.getPerson(); result = mockPerson;
            mockPerson.getTimeZone(); result = mockTimeZone;
            DateTimeZone.forTimeZone(mockTimeZone); result = mockDateTimeZone;
        }};
        
        final TwoHourBreaksReportCriteria sut = new TwoHourBreaksReportCriteria(mockLocale);
        sut.setDateTimeFormatter(mockDateTimeFormatter);
        
        List<HOSRec> mockHOSRecList = new ArrayList<HOSRec>();
        mockHOSRecList.add(mockHOSRec);
        
        Map<Driver, List<HOSRec>> mockDriverHOSRecMap = new HashMap<Driver, List<HOSRec>>();
        mockDriverHOSRecMap.put(mockDriver, mockHOSRecList);
        
        sut.initDataSet(mockInterval, mockAccount, mockGroupHierarchy, mockDriverHOSRecMap);
        
        new Verifications() {{
            sut.addParameter("REPORT_START_DATE", any); times = 1;
            sut.addParameter("REPORT_END_DATE", any); times = 1;
            sut.addParameter("CUSTOMER", any); times = 1;
            sut.addParameter("CUSTOMER_NAME", any); times = 1;
            sut.addParameter("CUSTOMER_ADDRESS", any); times = 1;
            new ArrayList<BreakDataSummary>(); times = 1;
            sut.compileBreakData(mockInterval, mockGroupHierarchy, mockDriver, (List)any); times = 1;
            testList.add((DayData) any); times = 1;
            sut.setMainDataset((List) any); times = 1;
        }};
    }
    
    @Test
    public void testCompileBreakData() {
        final List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        DayData mockData1 = new DayData();
        DayData mockData2 = new DayData();
        mockDayDataList.add(mockData1);
        mockDayDataList.add(mockData2);

        TwoHourBreaksReportCriteria sut = new TwoHourBreaksReportCriteria(mockLocale);
        sut.compileBreakData(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);

        new Verifications() {{
            new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList); times = 1;
        }};
        
    }
    
}
