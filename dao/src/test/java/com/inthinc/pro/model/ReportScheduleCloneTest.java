package com.inthinc.pro.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests correct cloning of {@link ReportSchedule}.
 */
public class ReportScheduleCloneTest {

    /**
     * Tests cloning of lists within the object.
     * You only need to test one list.
     */
    @Test
    public void listCloneTest() {

        // Mock a report schedule with a list
        ReportSchedule original = new ReportSchedule();
        List<String> emailList = new ArrayList<String>();
        String[] emailArr = {"abc@gmail.com", "cde@gamil.com"};
        emailList.addAll(Arrays.asList(emailArr));
        original.setEmailTo(emailList);

        // Clone the object
        ReportSchedule clone = original.clone();

        // modify the list of the clone
        clone.getEmailTo().set(1, "changed@gmail.com");

        // check if the original is not affected
        assertFalse(original.getEmailTo().get(1).equals(clone.getEmailTo().get(1)));
    }

    /**
     * Tests cloning of enums.
     */
    @Test
    public void enumeCloneTest() {
        // Mock a report schedule
        ReportSchedule original = new ReportSchedule();
        original.setManagerDeliveryType(ReportManagerDeliveryType.EXCLUDE_TEAMS);
        original.setParamType(ReportParamType.DRIVER);
        original.setOccurrence(Occurrence.DAILY);

        // Clone the object
        ReportSchedule clone = original.clone();

        // modify the clone
        clone.setManagerDeliveryType(ReportManagerDeliveryType.EXCLUDE_DIVISIONS);
        clone.setParamType(ReportParamType.GROUPS);
        clone.setOccurrence(Occurrence.MONTHLY);

        // check if the original is not affected
        assertFalse(original.getManagerDeliveryType().equals(clone.getManagerDeliveryType()));
        assertFalse(original.getParamType().equals(clone.getParamType()));
        assertFalse(original.getOccurrence().equals(clone.getOccurrence()));
    }

    /**
     * Tests cloning of dates.
     */
    @Test
    public void dateCloneTest() {
        DateTime today = new DateTime();

        // Mock a report schedule
        ReportSchedule original = new ReportSchedule();
        original.setLastDate(today.minusDays(1).toDate());
        original.setEndDate(today.minusDays(2).toDate());
        original.setStartDate(today.minusDays(3).toDate());

        // Clone the object
        ReportSchedule clone = original.clone();

        // modify the clone
        clone.setLastDate(today.plusDays(3).toDate());
        clone.setStartDate(today.plusDays(2).toDate());
        clone.setEndDate(today.plusDays(1).toDate());

        // check if the original is not affected
        assertFalse(original.getStartDate().getTime() == clone.getStartDate().getTime());
        assertFalse(original.getEndDate().getTime() == clone.getEndDate().getTime());
        assertFalse(original.getLastDate().getTime() == clone.getLastDate().getTime());
    }
    @Test
    public void validateEmailsTest(){
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setStatus(Status.ACTIVE);
        reportSchedule.setName("Report Schedule");
        reportSchedule.setUserID(1);
        reportSchedule.setGroupID(1);
        reportSchedule.setReportID(1);
        reportSchedule.setAccountID(1);
        List<String> emailList = new ArrayList<String>();
        emailList.add("foo@inthinc.com");
        emailList.add("  bar@inthinc.com");
        emailList.add("baz@inthinc.com");
        emailList.add("bazinthinc.com");
        reportSchedule.setEmailTo(emailList);
        assertTrue(reportSchedule.getEmailTo().size()==3);
        for(String email : reportSchedule.getEmailTo()) {
            assertTrue("email address contains white spaces",!email.contains(" "));
        }
    }

}
