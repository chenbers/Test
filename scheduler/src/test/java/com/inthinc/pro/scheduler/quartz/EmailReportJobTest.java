package com.inthinc.pro.scheduler.quartz;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.DriverPerformanceReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public class EmailReportJobTest
{
    private static final Integer MOCK_ACCOUNT_ID = 1;
    private static final Integer MOCK_USER_ID = 1;
    private static final Integer MOCK_GROUP_ID = 1;
    private static final Integer MOCK_REPORT_SCHEDULE_ID = 1;
    
    class MockReportCreator extends ReportCreator<MockReport>
    {
        MockReport mockReport;
        public MockReportCreator()
        {
            mockReport = new MockReport();
        }
        @Override
        public MockReport getReport(List<ReportCriteria> reportCriteriaList)
        {
            
            return mockReport;
        }

    }
    
    @Test
    public void dailyMSTTest()
    {
        ReportSchedule reportSchedule = buildReportSchedule(Occurrence.DAILY, TimeZone.getTimeZone("MST"));
        
        UserDAO userDAO = createMock(UserDAO.class);
        User user = new User();
        user.setUserID(1);
        user.setUsername("Hello");
        Person p = new Person();
        p.setTimeZone(TimeZone.getTimeZone("MST"));
        user.setPerson(p);
        expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
        replay(userDAO);
        
        EmailReportJob erj = new EmailReportJob();
        erj.setUserDAO(userDAO);
        assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
    }

    @Test
    public void weeklyMSTTest()
    {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.WEEKLY, TimeZone.getTimeZone("MST"));
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("MST"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
  }
    
    @Test
    public void monthlyMSTTest()
    {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.MONTHLY, TimeZone.getTimeZone("MST"));
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("MST"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
    }
    
    @Test
    public void nullOccurenceTest()
    {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.MONTHLY, TimeZone.getTimeZone("MST"));
      reportSchedule.setOccurrence(null);
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("MST"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(!erj.isTimeToEmailReport(reportSchedule,p));
    }

    
    @Test
    public void dailyRomaniaTest()
    {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.DAILY, TimeZone.getTimeZone("Europe/Bucharest"));
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
    }
    @Test
    public void weeklyRomaniaTest()
    {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.WEEKLY, TimeZone.getTimeZone("Europe/Bucharest"));
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
  }
  
  @Test
  public void monthlyRomaniaTest()
  {   
    ReportSchedule reportSchedule = buildReportSchedule(Occurrence.MONTHLY, TimeZone.getTimeZone("Europe/Bucharest"));
    
    UserDAO userDAO = createMock(UserDAO.class);
    User user = new User();
    user.setUserID(1);
    user.setUsername("Hello");
    Person p = new Person();
    p.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
    user.setPerson(p);
    expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
    replay(userDAO);
    
    EmailReportJob erj = new EmailReportJob();
    erj.setUserDAO(userDAO);
    assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
}
  @Test
  public void dailyGMTTest()
  {
    ReportSchedule reportSchedule = buildReportSchedule(Occurrence.DAILY, TimeZone.getTimeZone("UTC"));
    
    UserDAO userDAO = createMock(UserDAO.class);
    User user = new User();
    user.setUserID(1);
    user.setUsername("Hello");
    Person p = new Person();
    p.setTimeZone(TimeZone.getTimeZone("UTC"));
    user.setPerson(p);
    expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
    replay(userDAO);
    
    EmailReportJob erj = new EmailReportJob();
    erj.setUserDAO(userDAO);
    assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
}
  @Test
  public void weeklyGMTTest()
  {
    ReportSchedule reportSchedule = buildReportSchedule(Occurrence.WEEKLY, TimeZone.getTimeZone("UTC"));
    
    UserDAO userDAO = createMock(UserDAO.class);
    User user = new User();
    user.setUserID(1);
    user.setUsername("Hello");
    Person p = new Person();
    p.setTimeZone(TimeZone.getTimeZone("UTC"));
    user.setPerson(p);
    expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
    replay(userDAO);
    
    EmailReportJob erj = new EmailReportJob();
    erj.setUserDAO(userDAO);
    assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
  }

  @Test
  public void monthlyGMTTest()
  {
      ReportSchedule reportSchedule = buildReportSchedule(Occurrence.MONTHLY, TimeZone.getTimeZone("UTC"));
      
      UserDAO userDAO = createMock(UserDAO.class);
      User user = new User();
      user.setUserID(1);
      user.setUsername("Hello");
      Person p = new Person();
      p.setTimeZone(TimeZone.getTimeZone("UTC"));
      user.setPerson(p);
      expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
      replay(userDAO);
      
      EmailReportJob erj = new EmailReportJob();
      erj.setUserDAO(userDAO);
      assertTrue(erj.isTimeToEmailReport(reportSchedule,p));
    }
   
    private static final int SUN = 0;
    private static final int MON = 1;
    private static final int TUE = 2;
    private static final int WED = 3;
    private static final int THUR = 4;
    private static final int FRI = 5;
    private static final int SAT = 6;
    @Test
    public void dayOfWeekTest()
    {
        UserDAO userDAO = createMock(UserDAO.class);
        User user = new User();
        user.setUserID(1);
        user.setUsername("Hello");
        Person p = new Person();
        p.setTimeZone(TimeZone.getTimeZone("US/Mountain"));
        user.setPerson(p);
        expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
        replay(userDAO);

        // this a sunday 6/5/2011
        DateTime currentDateTime = new DateTime(2011, 6, 5, 0, 0, 0, 0, DateTimeZone.forID("US/Mountain"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss z");       
        String dateStr = dateTimeFormatter.print(currentDateTime);
        assertEquals("start date is correct", "06/05/2011 00:00:00 MDT", dateStr);
//        System.out.println(dateStr + " day of week is " + currentDateTime.getDayOfWeek() );
        assertEquals("start Date should be Sunday", DateTimeConstants.SUNDAY, currentDateTime.getDayOfWeek()); 

        
        for (int testDayOfWeek = SUN; testDayOfWeek <= SAT; testDayOfWeek++) {
            ReportSchedule reportSchedule = buildReportSchedule(Occurrence.WEEKLY, TimeZone.getTimeZone("US/Mountain"));
            
            List<Boolean> booleanList = new ArrayList<Boolean>();
            for (int scheduledDay = SUN; scheduledDay <= SAT; scheduledDay++) 
                booleanList.add(scheduledDay == testDayOfWeek);  
            reportSchedule.setDayOfWeek(booleanList);

            EmailReportJob erj = new EmailReportJob();
            erj.setUserDAO(userDAO);
            
            
            assertTrue(testDayOfWeek + " " + dateStr + " should be scheduled testDayOfWeek= " + testDayOfWeek, erj.isValidDayOfWeek(reportSchedule.getDayOfWeek(), currentDateTime.getDayOfWeek()));
            currentDateTime = currentDateTime.plusDays(1);
            dateStr = dateTimeFormatter.print(currentDateTime);
        }

        for (int testDayOfWeek = SUN; testDayOfWeek <= SAT; testDayOfWeek++) {
            ReportSchedule reportSchedule = buildReportSchedule(Occurrence.WEEKLY, TimeZone.getTimeZone("US/Mountain"));
            
            List<Boolean> booleanList = new ArrayList<Boolean>();
            for (int scheduledDay = SUN; scheduledDay <= SAT; scheduledDay++) 
                booleanList.add(scheduledDay != testDayOfWeek);  
            reportSchedule.setDayOfWeek(booleanList);

            EmailReportJob erj = new EmailReportJob();
            erj.setUserDAO(userDAO);
            
            
            assertTrue(testDayOfWeek + " " + dateStr + " should not be scheduled testDayOfWeek= " + testDayOfWeek, !erj.isValidDayOfWeek(reportSchedule.getDayOfWeek(), currentDateTime.getDayOfWeek()));
            currentDateTime = currentDateTime.plusDays(1);
            dateStr = dateTimeFormatter.print(currentDateTime);
        }
    }
    
    @Test
    public void jobInProgressTest()
    {
        // see DE6860 (individual driver reports were sending multiples because in the middle of running a report preference, the cron would start it again)
        AccountDAO accountDAO = initMockAccountDAO();
        UserDAO userDAO = initMockUserDAO();
        GroupDAO groupDAO = initMockGroupDAO();
        int numDrivers = 20;
        DriverDAO driverDAO = initMockDriverDAO(numDrivers);

        List<Integer> driverIDList = new ArrayList<Integer>();
        for (Driver driver : driverDAO.getAllDrivers(MOCK_GROUP_ID))
            driverIDList.add(driver.getDriverID());
        
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(MOCK_GROUP_ID);


        ReportSchedule reportSchedule = buildReportSchedule(Occurrence.DAILY, TimeZone.getTimeZone("MST"));
        reportSchedule.setReportScheduleID(MOCK_REPORT_SCHEDULE_ID);
        reportSchedule.setAccountID(MOCK_ACCOUNT_ID);
        reportSchedule.setReportID(ReportGroup.DRIVER_PERFORMANCE_INDIVIDUAL.getCode());
        reportSchedule.setGroupIDList(groupIDList);
        
        ReportScheduleDAO reportScheduleDAO = initReportScheduleDAO(reportSchedule);
        List<ReportSchedule> reportSchedules = new ArrayList<ReportSchedule>();
        reportSchedules.add(reportSchedule);
        
        List<ReportCriteria> criteriaList = new ArrayList<ReportCriteria>();
        for (Integer driverID : driverIDList) {
            ReportCriteria reportCriteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_INDIVIDUAL, Locale.ENGLISH);
            List<DriverPerformance> driverDataList = new ArrayList<DriverPerformance>();
            DriverPerformance dp = new DriverPerformance();
            dp.setDriverID(driverID);
            driverDataList.add(dp);
            reportCriteria.setMainDataset(driverDataList);
            criteriaList.add(reportCriteria);
        }

        ReportCriteriaService reportCriteriaService = EasyMock.createMock(ReportCriteriaService.class);
        EasyMock.makeThreadSafe(reportCriteriaService, true);
        expect(reportCriteriaService.getDriverPerformanceIndividualReportCriteria(EasyMock.isA(GroupHierarchy.class),
                EasyMock.isA(Integer.class), EasyMock.isA(List.class),
                EasyMock.isA(Interval.class),EasyMock.isA(Locale.class), EasyMock.isA(Boolean.class))).andReturn(criteriaList).anyTimes();
        replay(reportCriteriaService);

        MockReportCreator mockReportCreator = new MockReportCreator();
        EmailReportJob emailReportJob = new EmailReportJob();
        emailReportJob.setAccountDAO(accountDAO);
        emailReportJob.setGroupDAO(groupDAO);
        emailReportJob.setUserDAO(userDAO);
        emailReportJob.setDriverDAO(driverDAO);
        emailReportJob.setReportScheduleDAO(reportScheduleDAO);
        emailReportJob.setReportCriteriaService(reportCriteriaService);
        emailReportJob.setWebContextPath("Mock");
        emailReportJob.setEncryptPassword("mockPassword");
        emailReportJob.initTextEncryptor();
        emailReportJob.setReportCreator((ReportCreator)mockReportCreator);
        
        JobThread p1 = new JobThread(emailReportJob, reportSchedules, "thread 1", 1);
        p1.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobThread p2 = new JobThread(emailReportJob, reportSchedules, "thread 2", 0);
        p2.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobThread p3 = new JobThread(emailReportJob, reportSchedules, "thread 3", 0);
        p3.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            p1.join();
            p2.join();
            p3.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
      mockReportCreator = new MockReportCreator();
      reportSchedule.setLastDate(null);
      emailReportJob.setWebContextPath(null);
      emailReportJob.setReportCreator((ReportCreator)mockReportCreator);
      emailReportJob.dispatchReports(reportSchedules);
      assertEquals("expected reports to be sent should be 0 due to error", 0, mockReportCreator.mockReport.emailReportCnt);
        
    }

    
    class JobThread extends Thread {
        EmailReportJob emailReportJob;
        List<ReportSchedule> reportSchedules;
        int expectedReportCount;
        JobThread(EmailReportJob emailReportJob, List<ReportSchedule> reportSchedules, String name, int expectedReportCount) {
            this.emailReportJob = emailReportJob;
            this.reportSchedules = reportSchedules;
            this.setName(name);
            this.expectedReportCount = expectedReportCount;
        }
        public void run() {
            int reportCount = emailReportJob.dispatchReports(reportSchedules);
            
            assertEquals(getName() + " expected reports to be sent", expectedReportCount, reportCount);
        }
        
    }

    private ReportScheduleDAO initReportScheduleDAO(ReportSchedule reportSchedule) {
        ReportScheduleDAO reportScheduleDAO = createMock(ReportScheduleDAO.class);
        EasyMock.makeThreadSafe(reportScheduleDAO, true);
        expect(reportScheduleDAO.findByID(EasyMock.isA(Integer.class))).andReturn(reportSchedule).anyTimes();
        expect(reportScheduleDAO.update(EasyMock.isA(ReportSchedule.class))).andReturn(Integer.valueOf(1)).anyTimes();
        replay(reportScheduleDAO);
        return reportScheduleDAO;
    }
    
    private DriverDAO initMockDriverDAO(int numDrivers) {

        DriverDAO driverDAO = createMock(DriverDAO.class);
        Integer groupID = MOCK_GROUP_ID;
        List<Driver> driverList = new ArrayList<Driver>();
        for (int i = 0; i < numDrivers; i++) {
            Integer driverID = i+1;
            Driver driver = new Driver();
            driver.setDriverID(driverID);
            driver.setGroupID(groupID);
            Person driverPerson = new Person();
            driverPerson.setPriEmail("foo" + i + "@email.com");
            driverPerson.setLocale(Locale.ENGLISH);
            driverPerson.setTimeZone(TimeZone.getTimeZone("MST"));
            driver.setPerson(driverPerson);
            driverList.add(driver);
            expect(driverDAO.findByID(EasyMock.isA(Integer.class))).andReturn(driver).anyTimes();
        }
        EasyMock.makeThreadSafe(driverDAO, true);
        expect(driverDAO.getAllDrivers(EasyMock.isA(Integer.class))).andReturn(driverList).anyTimes();
        replay(driverDAO);
        return driverDAO;
    }

    private GroupDAO initMockGroupDAO() {
        GroupDAO groupDAO = createMock(GroupDAO.class);
        Group group = new Group();
        group.setGroupID(MOCK_GROUP_ID);
        group.setAccountID(MOCK_ACCOUNT_ID);
        group.setParentID(0);
        group.setName("group");
        expect(groupDAO.findByID(EasyMock.isA(Integer.class))).andReturn(group).anyTimes();
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        EasyMock.makeThreadSafe(groupDAO, true);
        expect(groupDAO.getGroupsByAcctID(EasyMock.isA(Integer.class))).andReturn(groupList).anyTimes();
        expect(groupDAO.getGroupHierarchy(EasyMock.isA(Integer.class),EasyMock.isA(Integer.class))).andReturn(groupList).anyTimes();
        replay(groupDAO);
        return groupDAO;
    }

    private UserDAO initMockUserDAO() {
        UserDAO userDAO = createMock(UserDAO.class);
        User user = new User();
        user.setUserID(MOCK_USER_ID);
        user.setUsername("Hello");
        user.setStatus(Status.ACTIVE);
        Person p = new Person();
        p.setTimeZone(TimeZone.getTimeZone("MST"));
        p.setLocale(Locale.ENGLISH);
        user.setPerson(p);
        EasyMock.makeThreadSafe(userDAO, true);
        expect(userDAO.findByID(EasyMock.isA(Integer.class))).andReturn(user).anyTimes();
        replay(userDAO);
        return userDAO;
    }

    private AccountDAO initMockAccountDAO() {
        AccountDAO accountDAO = createMock(AccountDAO.class);
        Account account = new Account();
        account.setAccountID(MOCK_ACCOUNT_ID);
        EasyMock.makeThreadSafe(accountDAO, true);
        expect(accountDAO.findByID(EasyMock.isA(Integer.class))).andReturn(account).anyTimes();
        replay(accountDAO);
        return accountDAO;
    }
    class MockReport implements Report
    {
        int emailReportCnt = 0;

        @Override
        public void exportReportToEmail(String email, FormatType formatType, String noReplyEmailAddress) {
            emailReportCnt++;
            try {
                Thread.sleep(250l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void exportReportToEmail(String email, FormatType formatType, String message, String subject, String noReplyEmailAddress) {
            emailReportCnt++;
            try {
                Thread.sleep(250l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void exportReportToStream(FormatType formatType, OutputStream outputStream) {
        }
        
    }
    
    private ReportSchedule buildReportSchedule(Occurrence occurrence, TimeZone userTimeZone) {
        
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setStatus(Status.ACTIVE);
        reportSchedule.setName("Report Schedule");
        reportSchedule.setOccurrence(occurrence);
        reportSchedule.setUserID(MOCK_USER_ID);
        reportSchedule.setGroupID(MOCK_GROUP_ID);
        reportSchedule.setReportID(1);
        reportSchedule.setAccountID(MOCK_ACCOUNT_ID);
        
        DateTime currentDayOfMonth = null;
        if (occurrence == Occurrence.MONTHLY) 
            currentDayOfMonth = new DateTime(new DateMidnight(new DateTime(),DateTimeZone.forID("UTC")));
        else currentDayOfMonth = new DateTime(new DateMidnight(new DateTime(),DateTimeZone.forID(userTimeZone.getID())));

        MutableDateTime dayOfMonth = new MutableDateTime(currentDayOfMonth);
        dayOfMonth.addDays(1);
        DateTime endDate = new DateTime(dayOfMonth);
        
//        reportSchedule.setLastDate(firstOfMonth.toDate());
        reportSchedule.setLastDate(new DateTime().minusWeeks(6).toDate());
        reportSchedule.setEndDate(endDate.toDate());
        reportSchedule.setStartDate(currentDayOfMonth.toDate());
        reportSchedule.setTimeOfDay(new DateTime(DateTimeZone.forID(userTimeZone.getID())).getMinuteOfDay());
        List<String> emailList = new ArrayList<String>();
        emailList.add("foo@inthinc.com");
        emailList.add("bar@inthinc.com");
        emailList.add("baz@inthinc.com");
        reportSchedule.setEmailTo(emailList);
        
        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        reportSchedule.setDayOfWeek(booleanList);
        
        return reportSchedule;
       
    }
}
