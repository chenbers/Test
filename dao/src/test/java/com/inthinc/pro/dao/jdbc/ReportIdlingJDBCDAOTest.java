package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.IdlingReportItem;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vali_enache on 2/21/14.
 */
public class ReportIdlingJDBCDAOTest {
    static ReportIdlingJDBCDAO reportIdlingJDBCDAO;
    static int TEST_GROUP_ID = 999999999;
    Interval interval = new Interval(null);
    // DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
    // Interval interval = new Interval(null, null);


    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        reportIdlingJDBCDAO = new ReportIdlingJDBCDAO();
        reportIdlingJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        //create
        reportIdlingJDBCDAO.createTest(TEST_GROUP_ID, new org.joda.time.DateTime().minusHours(1).toDate());
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            //delete
            reportIdlingJDBCDAO.deleteTest(TEST_GROUP_ID);
        } catch (Throwable t) {/*ignore*/}
    }
    /*
    * Test getIdlingReportData
    * */
    @Test
    public void testGetIdlingReportData (){
        List<IdlingReportItem> reportList = reportIdlingJDBCDAO.getIdlingReportData(TEST_GROUP_ID,interval);
        assertEquals(reportList.size(), 1);


    }

}
