package com.inthinc.pro.reports.hos;

import com.inthinc.hos.ddl.EditLog;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.hos.HOSRecord;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HosDriverDailyLogReportCriteriaMockTest {
    HosDailyDriverLogReportCriteria criteria = new HosDailyDriverLogReportCriteria(Locale.ENGLISH, false, DateTimeZone.UTC);
    Boolean checkedData = false;
    List<HOSRecord> hosRecordList;
    List<DateTime> testDays;
    Map<String, EditLog> expectedLogs;
    HOSRecord hosRecord1; // not edited, 01.01.2014
    HOSRecord hosRecord2; // 01.01.2014
    HOSRecord hosRecord3; // 01.02.2014
    HOSRecord hosRecord4; // not edited, 01.02.2014
    HOSRecord hosRecord5; // 01.03.2014 // not in date list
    DateTime dt01012014;
    DateTime dt01022014;
    DateTime dt01032014;
    User editor1; // id 1, user1
    User editor2; // id 2, user2
    EditLog editLog1;
    EditLog editLog2;
    @Mocked
    UserDAO mockUserDAO;


    @Before
    public void init() {
        criteria.setUserDAO(mockUserDAO);
        expectedLogs = new HashMap<String, EditLog>();

        editor1 = new User();
        editor2 = new User();

        editor1.setUserID(1);
        editor1.setUsername("user1");
        editor2.setUsername("user2");
        editor2.setUserID(2);

        dt01012014 = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        dt01022014 = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        dt01032014 = new DateTime(2014, 1, 3, 0, 0, 0, 0);

        hosRecord1 = new HOSRecord();
        hosRecord1.setLogTime(dt01012014.toDate());
        hosRecord1.setEditor(1);
        hosRecord1.setReason("test1");
        hosRecord1.setEdited(false);
        hosRecord1.setHosLogID(1l);
        hosRecord1.setStatus(HOSStatus.DRIVING);

        hosRecord2 = new HOSRecord();
        hosRecord2.setLogTime(dt01012014.toDate());
        hosRecord2.setEditor(1);
        hosRecord2.setReason("test2");
        hosRecord2.setApprovedBy("app2");
        hosRecord2.setTimeStamp(dt01012014.toDate());
        hosRecord2.setEdited(true);
        hosRecord2.setHosLogID(2l);
        hosRecord2.setStatus(HOSStatus.DRIVING);

        hosRecord3 = new HOSRecord();
        hosRecord3.setLogTime(dt01022014.toDate());
        hosRecord3.setEditor(2);
        hosRecord3.setReason("test3");
        hosRecord3.setApprovedBy("app3");
        hosRecord3.setTimeStamp(dt01022014.toDate());
        hosRecord3.setEdited(true);
        hosRecord3.setHosLogID(3l);
        hosRecord3.setStatus(HOSStatus.DRIVING);

        hosRecord4 = new HOSRecord();
        hosRecord4.setLogTime(dt01022014.toDate());
        hosRecord4.setEditor(1);
        hosRecord4.setReason("test4");
        hosRecord4.setEdited(false);
        hosRecord4.setHosLogID(4l);
        hosRecord4.setStatus(HOSStatus.DRIVING);

        hosRecord5 = new HOSRecord();
        hosRecord5.setLogTime(dt01032014.toDate());
        hosRecord5.setEditor(1);
        hosRecord5.setReason("test5");
        hosRecord5.setApprovedBy("app5");
        hosRecord5.setTimeStamp(dt01032014.toDate());
        hosRecord5.setEdited(true);
        hosRecord5.setHosLogID(5l);
        hosRecord5.setStatus(HOSStatus.DRIVING);

        editLog1 = new EditLog();
        editLog1.setApprovedBy("app2");
        editLog1.setEditor("user1");
        editLog1.setReason("test2");
        editLog1.setTimeStamp(dt01012014.toDate());

        editLog2 = new EditLog();
        editLog2.setApprovedBy("app3");
        editLog2.setEditor("user2");
        editLog2.setReason("test3");
        editLog2.setTimeStamp(dt01022014.toDate());

        hosRecordList = Arrays.asList(hosRecord1, hosRecord2, hosRecord3, hosRecord4, hosRecord5);
        testDays = Arrays.asList(dt01012014, dt01022014);
        expectedLogs.put("user1", editLog1);
        expectedLogs.put("user2", editLog2);
    }


    @Test
    public void testGetEditedList() {
        new NonStrictExpectations() {{
            mockUserDAO.findByID(1);
            result = editor1;
        }};

        new NonStrictExpectations() {{
            mockUserDAO.findByID(2);
            result = editor2;
        }};

        for (DateTime currentDay : testDays) {
            List<EditLog> editLogs = criteria.getEditListForDay(currentDay, hosRecordList);
            if (editLogs != null) {
                for (EditLog editLog : editLogs) {
                    String editor = editLog.getEditor();
                    EditLog expectedEditLog = expectedLogs.get(editor);
                    assertCompareEditLogs(expectedEditLog, editLog);
                }
            }
        }

        assertTrue(checkedData);
    }

    private void assertCompareEditLogs(EditLog expected, EditLog actual) {
        checkedData = true;
        assertEquals(expected.getApprovedBy(), actual.getApprovedBy());
        assertEquals(expected.getEditor(), actual.getEditor());
        assertEquals(expected.getReason(), actual.getReason());
        assertEquals(expected.getTimeStamp(), actual.getTimeStamp());
    }
}
