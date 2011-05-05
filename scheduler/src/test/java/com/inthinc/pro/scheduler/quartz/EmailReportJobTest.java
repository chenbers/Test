package com.inthinc.pro.scheduler.quartz;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
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
   
    private ReportSchedule buildReportSchedule(Occurrence occurrence, TimeZone userTimeZone){
        
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
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        reportSchedule.setDayOfWeek(booleanList);
        
        return reportSchedule;
       
    }
}
