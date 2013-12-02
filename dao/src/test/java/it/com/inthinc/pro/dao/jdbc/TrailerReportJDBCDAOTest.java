package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.*;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.TrailerReportJDBCDAO;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
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
    private static String TRAILER_TABLE = "trailer";
    
    private static String TEST_TRAILER_NAME = "testTrailer_";
    private static int TEST_STATEID_UTAH = 45;
    private static int TEST_ODO = 100;
    private static int TEST_YEAR = 1984;
    private static int TEST_WEIGHT = 1234;
    private static String FAKEGROUPPATH = "fakeGroupPath";
    
    private static SiloService siloService;
    private static final String BASE_DATA_XML = "TeamStops.xml";
    private static ITData itData = new ITData();
    
    @Before
    public void setUpBeforeTest() throws Exception {

        
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BASE_DATA_XML);
        if (!itData.parseTestData(stream, siloService, false, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        this.updateTrailerReportItem();
        
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
        PageParams params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(itData.teamGroupData.get(ITData.GOOD).group.getGroupID());
        List<TrailerReportItem> trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        int expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1) <= 0 ? NUM_OF_VEHICLE_PERFORMANCE_INSERTS : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1);
        int pageCount = (PAGE_1_END - PAGE_1_START) + 1;
        int numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS  > (PAGE_1_END + 1) ? pageCount : expected;
        assertEquals(numOfResults, trailerReportItems.size());
        int pageOneFirstTrailerID = trailerReportItems.get(0).getTrailerID().intValue();
        
        // Test Paging count (page 2) ascending
        filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        params = new PageParams(PAGE_2_START, PAGE_2_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1) <= 0 ? 0 : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1);
        pageCount = (PAGE_2_END - PAGE_2_START) + 1;
        numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS  > (PAGE_2_END + 1) ? pageCount : expected;
        int size = trailerReportItems == null ? 0 : trailerReportItems.size();
        assertEquals(numOfResults, trailerReportItems.size());
        int trailerID = pageOneFirstTrailerID;
        
        if (size != 0) {
            trailerID = trailerReportItems.get(0).getTrailerID().intValue();
        }
        
        assertNotSame(pageOneFirstTrailerID, trailerID);
        
        
        // Test Paging with trailerName desc
        filterMap = new HashMap<String, Object>();
        String trailerNameFilter = "testTrailer_1";
        filterMap.put("trailerName", trailerNameFilter);
        params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.DESCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        assertEquals(1, trailerReportItems.size());
        assertEquals(trailerNameFilter, trailerReportItems.get(0).getTrailerName());
    }
    
    @Test
    public void testGetTrailerReportCount() {
        TrailerReportJDBCDAO dao = new TrailerReportJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        dao.setDataSource(dataSource);
        
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("name", "");
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(itData.teamGroupData.get(ITData.GOOD).group.getGroupID());
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
    
    private void updateTrailerReportItem() {
        String sql = "insert into " + TRAILER_TABLE + " " 
                        +" ( groupID, acctID, status, groupPath, odometer, absOdometer, weight, year, name, make, model, color, vin, license, stateID, warrantyStart, warrantyStop, aggDate, newAggDate, deviceID, driverID, vehicleID, pairingDate, entryDate, modified ) "
                        + " values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            
            DataSource dataSource = new ITDataSource().getRealDataSource();
            this.setDataSource(dataSource);
            SimpleJdbcTemplate template = getSimpleJdbcTemplate();
            int[] rows = template.batchUpdate(sql, this.getBatchArgs());
            
            System.out.println("Rows Inserted: " + rows.length);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private List<Object[]> getBatchArgs() {
        List<Object[]> retVal = new ArrayList<Object[]>();
        
        for (int i = 0; i < NUM_OF_VEHICLE_PERFORMANCE_INSERTS; i++) {
            System.out.println("1: "+itData);
            System.out.println("2: "+itData.teamGroupData);
            System.out.println("3: "+itData.teamGroupData.get(ITData.GOOD));
            System.out.println("4: "+itData.teamGroupData.get(ITData.GOOD).driver);
            System.out.println("5: "+itData.teamGroupData.get(ITData.GOOD).driver.getDriverID());
            System.out.println("6: "+itData.teamGroupData.get(ITData.GOOD).driver.getDriverID());    
            retVal.add(new Object[] { itData.teamGroupData.get(ITData.GOOD).group.getGroupID(), itData.account.getAccountID(), Status.ACTIVE.getCode(), "/"+FAKEGROUPPATH+"/"+itData.teamGroupData.get(ITData.GOOD).group.getGroupID()
                            , TEST_ODO,TEST_ODO,TEST_WEIGHT,TEST_YEAR,TEST_TRAILER_NAME + i,"testMake","testmodel","testcolor","testvin","testlicnse"
                            ,TEST_STATEID_UTAH,new Date(),new Date(),new Date(),new Date()
                            ,itData.noDriverDevice
                            ,itData.teamGroupData.get(ITData.GOOD).driver.getDriverID()
                            ,null
                            ,new Date(),new Date(), new Date()});
        }
        
        return retVal;
    }
    
    private void deletTestVehiclePerformanceRows() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "delete from " + TRAILER_TABLE + " where groupPath like '%"+FAKEGROUPPATH+"%'";
        int rows = template.update(sql);
        System.out.println("Rows Deleted: " + rows);
    }
}
