package it.com.inthinc.pro.dao.jdbc;

import it.config.ITDataSource;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.jdbc.DVIRViolationReportJDBCDAO;
import com.inthinc.pro.model.dvir.DVIRViolationReport;

public class DVIRViolationReportJDBCDAOTest extends BaseJDBCTest {
    private static List<Integer> groupIds;
    private static Interval interval;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        groupIds = new ArrayList<Integer>();
        groupIds.add(6);
        groupIds.add(46);
        groupIds.add(4216);
        groupIds.add(7823);
        groupIds.add(7845);
        
        DateTime startDate = new DateTime();
        startDate = startDate.minus(Months.months(2));
        DateTime endDate = new DateTime();
        interval = new Interval(startDate, endDate);
    }
    
    @Test
    public void testBuildViolationSQL() {
        
        String SQL = DVIRViolationReportJDBCDAO.buildViolationSQL(groupIds, interval);
        
        System.out.println(SQL);
        
        Assert.assertNotNull(SQL);
        Assert.assertTrue(SQL.length() > 0);
    }
    
    @Test
    public void testGetDVIRViolationsForGroup() {
        DVIRViolationReportJDBCDAO jdbc = new DVIRViolationReportJDBCDAO();
        jdbc.setDataSource(new ITDataSource().getRealDataSource());
        
        List<DVIRViolationReport> reportList = (List<DVIRViolationReport>) jdbc.getDVIRViolationsForGroup(groupIds, interval);
        
        Assert.assertNotNull(reportList);
    }
    
}
