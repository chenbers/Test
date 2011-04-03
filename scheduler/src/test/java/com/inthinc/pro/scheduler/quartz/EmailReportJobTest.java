package com.inthinc.pro.scheduler.quartz;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.easymock.EasyMock;
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
    @Ignore
    public void validateTest()
    {
        ReportSchedule reportSchedule = buildReportSchedule();
        
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
        assertTrue(erj.emailReport(reportSchedule,p));
    }
    
    
    private ReportSchedule buildReportSchedule()
    {
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setStatus(Status.ACTIVE);
        reportSchedule.setReportID(0);
        reportSchedule.setName("Report Schedule");
        reportSchedule.setOccurrence(Occurrence.DAILY);
        reportSchedule.setUserID(1);
        reportSchedule.setGroupID(1);
        reportSchedule.setReportID(1);
        reportSchedule.setAccountID(1);
        
        
        Calendar firstOfMonth = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        firstOfMonth.set(Calendar.DATE, 1);
        firstOfMonth.set(Calendar.HOUR, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);
        firstOfMonth.set(Calendar.SECOND, 0);
        firstOfMonth.set(Calendar.MILLISECOND, 0);
        
        Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endDate.set(Calendar.HOUR, 0);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);
        
        reportSchedule.setLastDate(firstOfMonth.getTime());
        reportSchedule.setEndDate(endDate.getTime());
        reportSchedule.setStartDate(firstOfMonth.getTime());
        
        List<String> emailList = new ArrayList<String>();
        emailList.add("foo@inthinc.com");
        emailList.add("bar@inthinc.com");
        emailList.add("baz@inthinc.com");
        reportSchedule.setEmailTo(emailList);
        
        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        reportSchedule.setDayOfWeek(booleanList);
        
        return reportSchedule;
    }

}
