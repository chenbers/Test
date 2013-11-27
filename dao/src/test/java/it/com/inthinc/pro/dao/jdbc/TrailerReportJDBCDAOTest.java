package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.*;
import it.config.ITDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.inthinc.pro.dao.jdbc.TrailerReportJDBCDAO;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class TrailerReportJDBCDAOTest extends SimpleJdbcDaoSupport {
    
    private static int NUM_OF_VEHICLE_PERFORMANCE_INSERTS = 2;
    private static int NUM_OF_VEHICLE_PERFORMANCE_INSERTS_WITH_OVERALL_RANGE = NUM_OF_VEHICLE_PERFORMANCE_INSERTS % 2;
    private static int OVERALL_RANGE_HIT = 14;
    private static int PAGE_1_START = 0;
    private static int PAGE_1_END = 9;
    private static int PAGE_2_START = 10;
    private static int PAGE_2_END = 19;
    
    private static String GROUP_NAME = "JDBCDAOTestGroupName";
    private static String TRAILER_PERFORMANCE_TABLE = "trailerPerformance";
    
    private static String TEST_TRAILER_NAME = "testTrailer_";
    
    private int lastVehiclePerformanceID;
    private int lastGroupID;
    
    @Before
    public void setUpBeforeTest() throws Exception {
        lastVehiclePerformanceID = this.getLastVehiclePerformanceID() + 1;
        lastGroupID = this.getLastGroupID() + 1;
        this.updateTrailerReportItem(lastVehiclePerformanceID);
    }
    
    @After
    public void cleanUpAfterTest() throws Exception {
        this.deletTestVehiclePerformanceRows();
    }
    
    @Test
    public void testGetTrailerReportItemByGroupPaging() {
        TrailerReportJDBCDAO dao = new TrailerReportJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        dao.setDataSource(dataSource);
        
        // Test Paging count ascending
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        filterMap.put("overallScore", "");
        filterMap.put("speedScore", "");
        filterMap.put("styleScore", "");
        PageParams params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(lastGroupID);
        List<TrailerReportItem> trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        int expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1) <= 0 ? NUM_OF_VEHICLE_PERFORMANCE_INSERTS : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1);
        int pageCount = (PAGE_1_END - PAGE_1_START) + 1;
        int numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS  > (PAGE_1_END + 1) ? pageCount : expected;
        assertEquals(numOfResults, trailerReportItems.size());
        assertEquals(lastVehiclePerformanceID, trailerReportItems.get(0).getTrailerID().intValue());
        
        // Test Paging count (page 2) ascending
        filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        filterMap.put("overallScore", "");
        filterMap.put("speedScore", "");
        filterMap.put("styleScore", "");
        params = new PageParams(PAGE_2_START, PAGE_2_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1) <= 0 ? 0 : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1);
        pageCount = (PAGE_2_END - PAGE_2_START) + 1;
        numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS  > (PAGE_2_END + 1) ? pageCount : expected;
        int size = trailerReportItems == null ? 0 : trailerReportItems.size();
        assertEquals(numOfResults, trailerReportItems.size());
        int trailerID = -1;
        
        if (size != 0) {
            trailerID = trailerReportItems.get(0).getTrailerID().intValue();
        }
        
        assertNotSame(lastVehiclePerformanceID, trailerID);
        
        // Test with overall Score range
        filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        filterMap.put("overallScore", new Range(OVERALL_RANGE_HIT - 1, OVERALL_RANGE_HIT + 1));
        filterMap.put("speedScore", "");
        filterMap.put("styleScore", "");
        params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        assertEquals(NUM_OF_VEHICLE_PERFORMANCE_INSERTS_WITH_OVERALL_RANGE, trailerReportItems.size());
        
        // Test Paging with trailerName desc
        filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        filterMap.put("overallScore", "");
        filterMap.put("speedScore", "");
        filterMap.put("styleScore", "");
        params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.DESCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1) <= 0 ? NUM_OF_VEHICLE_PERFORMANCE_INSERTS : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1);
        pageCount = (PAGE_1_END - PAGE_1_START) + 1;
        numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS  > (PAGE_1_END + 1) ? pageCount : expected;
        assertEquals(numOfResults, trailerReportItems.size());
        assertNotSame(lastVehiclePerformanceID, trailerReportItems.get(0).getTrailerID().intValue());
    }
    
    @Test
    public void testGetTrailerReportCount() {
        TrailerReportJDBCDAO dao = new TrailerReportJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        dao.setDataSource(dataSource);
        
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        filterMap.put("overallScore", "");
        filterMap.put("speedScore", "");
        filterMap.put("styleScore", "");
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(lastGroupID);
        int count = dao.getTrailerReportCount(groupIDs, this.getFilters(filterMap));
        assertEquals(NUM_OF_VEHICLE_PERFORMANCE_INSERTS, count);
    }
    
    
    private List<TableFilterField> getFilters(Map<String, Object> filterMap) {
        List<TableFilterField> retVal = new ArrayList<TableFilterField>();
        
        for(Map.Entry<String, Object> entry : filterMap.entrySet()) {
            TableFilterField filter = new TableFilterField(entry.getKey(), entry.getValue(), FilterOp.LIKE);
            retVal.add(filter); 
        }
        return retVal;
    }
    
    private TableSortField getTableSortField(SortOrder sortOrder, String fieldName) {
        return new TableSortField(sortOrder, fieldName);
    }
    
    private int getLastVehiclePerformanceID() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "select max(trailerID) from trailerPerformance";
        return template.queryForObject(sql, Integer.class);
    }
    
    private int getLastGroupID() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "select max(groupID) from groups";
        return template.queryForObject(sql, Integer.class);
    }
    
    private void updateTrailerReportItem(int lastID) {
        String sql = "insert into " + TRAILER_PERFORMANCE_TABLE + " " + "(trailerID, " + "trailerName, " + "trailerYMM, " + "vehicleID, " + "vehicleName, " + "driverID, " + "driverName, "
                        + "groupID, " + "groupName, " + "milesDriven, " + "odometer, " + "overallScore, " + "speedScore, " + "styleScore) " + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            
            DataSource dataSource = new ITDataSource().getRealDataSource();
            this.setDataSource(dataSource);
            SimpleJdbcTemplate template = getSimpleJdbcTemplate();
            int[] rows = template.batchUpdate(sql, this.getBatchArgs(lastID));
            
            System.out.println("Rows Inserted: " + rows.length);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private List<Object[]> getBatchArgs(int lastID) {
        List<Object[]> retVal = new ArrayList<Object[]>();
        
        int trailerID = lastID;
        for (int i = 0; i < NUM_OF_VEHICLE_PERFORMANCE_INSERTS; i++) {
            int overall = 36;
            if (i < NUM_OF_VEHICLE_PERFORMANCE_INSERTS_WITH_OVERALL_RANGE){
                overall = OVERALL_RANGE_HIT;
            }
            retVal.add(new Object[] { trailerID, TEST_TRAILER_NAME + i, "test_Year/Make/Model", i, "testVehicle", i, "testDriver", lastGroupID, GROUP_NAME, 100, 100000, overall, 35, 33 });
            trailerID += 1;
        }
        
        return retVal;
    }
    
    private void deletTestVehiclePerformanceRows() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "delete from " + TRAILER_PERFORMANCE_TABLE + " where groupID = ? and groupName = ?";
        int rows = template.update(sql, new Object[] { lastGroupID, GROUP_NAME });
        System.out.println("Rows Deleted: " + rows);
    }
}
