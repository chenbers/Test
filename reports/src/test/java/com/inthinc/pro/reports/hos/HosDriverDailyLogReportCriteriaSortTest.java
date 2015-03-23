package com.inthinc.pro.reports.hos;

import com.inthinc.hos.ddl.HosDailyDriverLog;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HosDriverDailyLogReportCriteriaSortTest {
    HosDailyDriverLogReportCriteria criteria = new HosDailyDriverLogReportCriteria(Locale.ENGLISH, false, DateTimeZone.UTC);

    private Driver driver1;
    private Driver driver2;
    private Driver driver3;
    private Person person1;
    private Person person2;
    private Person person3;
    private Account account;
    private List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
    private Group group;

    @Mocked
    UserDAO mockUserDAO;

    @Mocked
    GroupDAO mockGroupDAO;

    @Mocked
    DriverDAO mockDriverDAO;

    @Mocked
    AccountDAO mockAccountDAO;

    @Mocked
    HOSDAO mockHosDAO;


    @Before
    public void init() {
        criteria.setUserDAO(mockUserDAO);
        criteria.setGroupDAO(mockGroupDAO);
        criteria.setDriverDAO(mockDriverDAO);
        criteria.setAccountDAO(mockAccountDAO);
        criteria.setHosDAO(mockHosDAO);

        driver1 = new Driver();
        driver2 = new Driver();
        driver3 = new Driver();
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();

        person1.setLast("abc");
        person2.setLast("bce");
        person3.setLast("cde");

        // set first names to prove that it sorts lexicographically and not just by last
        person3.setFirst("abc");
        person2.setFirst("bce");
        person1.setFirst("cde");

        person1.setAcctID(1);
        person2.setAcctID(1);
        person3.setAcctID(1);

        driver1.setPerson(person1);
        driver2.setPerson(person2);
        driver3.setPerson(person3);
        driver1.setStatus(Status.ACTIVE);
        driver2.setStatus(Status.ACTIVE);
        driver3.setStatus(Status.ACTIVE);
        driver1.setGroupID(1);
        driver2.setGroupID(1);
        driver3.setGroupID(1);

        group = new Group();
        group.setGroupID(1);
        group.setParentID(0);

        account = new Account();
        account.setAddress(new Address());

    }


    @Test
    public void testGetEditedList() {

        final List<Driver> unsortedDrivers = new LinkedList<Driver>();
        unsortedDrivers.add(driver2);
        unsortedDrivers.add(driver1);
        unsortedDrivers.add(driver3);

        new NonStrictExpectations() {{
            mockDriverDAO.getDrivers(1);
            result = unsortedDrivers;
        }};

        new NonStrictExpectations() {{
            mockAccountDAO.findByID(1);
            result = account;
        }};

        new NonStrictExpectations() {{
            mockHosDAO.getHOSRecords(anyInt, (Interval) any, false);
            result = hosRecordList;
        }};

        GroupHierarchy groupHierarchy = new GroupHierarchy();
        groupHierarchy.setGroupList(Arrays.asList(group));
        Interval interval = new Interval(new DateTime(), new DateTime());

        criteria.init(groupHierarchy, Arrays.asList(1), interval);
        List<ReportCriteria> reportCriterias = criteria.getCriteriaList();
        assertNotNull(reportCriterias);
        assertTrue(reportCriterias.size() > 0);
        assertTrue(reportCriterias.size() == 3);

        Map<Integer, String> sortedDriverNames = new HashMap<Integer, String>();
        sortedDriverNames.put(1, "abc cde");
        sortedDriverNames.put(2, "bce bce");
        sortedDriverNames.put(3, "cde abc");

        int i = 1;
        for (ReportCriteria reportCriteria: reportCriterias){
            HosDailyDriverLog mainDataset = (HosDailyDriverLog) reportCriteria.getMainDataset().get(0);
            String driverName = mainDataset.getDriverName();

            assertEquals(sortedDriverNames.get(i), driverName);

            i++;
        }
    }
}
