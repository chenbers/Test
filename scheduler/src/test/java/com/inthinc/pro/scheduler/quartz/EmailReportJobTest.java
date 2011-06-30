package com.inthinc.pro.scheduler.quartz;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;

public class EmailReportJobTest
{
    @Test
//    @Ignore
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
//  @Ignore
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
//  @Ignore
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
//  @Ignore
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
//  @Ignore
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
//@Ignore
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
//@Ignore
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
//@Ignore
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
//@Ignore
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
/*
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
 */
        
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
    }
    
    
    private ReportSchedule buildReportSchedule(Occurrence occurrence, TimeZone userTimeZone) {
        
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setStatus(Status.ACTIVE);
        reportSchedule.setReportID(0);
        reportSchedule.setName("Report Schedule");
        reportSchedule.setOccurrence(occurrence);
        reportSchedule.setUserID(1);
        reportSchedule.setGroupID(1);
        reportSchedule.setReportID(1);
        reportSchedule.setAccountID(1);
        
        DateTime firstOfMonth = new DateTime(new DateMidnight(new DateTime(),DateTimeZone.forID(userTimeZone.getID())));

        MutableDateTime dayOfMonth = new MutableDateTime(firstOfMonth);
        dayOfMonth.addDays(1);
        DateTime endDate = new DateTime(dayOfMonth);
        
        reportSchedule.setLastDate(firstOfMonth.toDate());
        reportSchedule.setEndDate(endDate.toDate());
        reportSchedule.setStartDate(firstOfMonth.toDate());
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
