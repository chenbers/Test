package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.TrailerReportJDBCDAO;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TrailerAssignedStatus;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class TrailerReportJDBCDAOTest extends SimpleJdbcDaoSupport {

    private static int NUM_OF_VEHICLE_PERFORMANCE_INSERTS = 2;
    private static int PAGE_1_START = 0;
    private static int PAGE_1_END = 9;
    private static int PAGE_2_START = 10;
    private static int PAGE_2_END = 19;

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
        groupIDs.add(itData.teamGroupData.get(ITData.INTERMEDIATE).group.getGroupID());
        List<TrailerReportItem> trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        int expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_1_END - PAGE_1_START) + 1) <= 0 ? NUM_OF_VEHICLE_PERFORMANCE_INSERTS : NUM_OF_VEHICLE_PERFORMANCE_INSERTS
                - ((PAGE_1_END - PAGE_1_START) + 1);
        int pageCount = (PAGE_1_END - PAGE_1_START) + 1;
        int numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS > (PAGE_1_END + 1) ? pageCount : expected;
        assertEquals(numOfResults, trailerReportItems.size());
        int pageOneFirstTrailerID = trailerReportItems.get(0).getTrailerID().intValue();

        // Test Paging count (page 2) ascending
        filterMap = new HashMap<String, Object>();
        filterMap.put("trailerName", "");
        params = new PageParams(PAGE_2_START, PAGE_2_END, this.getTableSortField(SortOrder.ASCENDING, "trailerName"), this.getFilters(filterMap));
        trailerReportItems = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        expected = NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1) <= 0 ? 0 : NUM_OF_VEHICLE_PERFORMANCE_INSERTS - ((PAGE_2_END - PAGE_2_START) + 1);
        pageCount = (PAGE_2_END - PAGE_2_START) + 1;
        numOfResults = NUM_OF_VEHICLE_PERFORMANCE_INSERTS > (PAGE_2_END + 1) ? pageCount : expected;
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
        groupIDs.add(itData.teamGroupData.get(ITData.INTERMEDIATE).group.getGroupID());
        int count = dao.getTrailerReportCount(groupIDs, this.getFilters(filterMap));
        assertEquals(NUM_OF_VEHICLE_PERFORMANCE_INSERTS, count);
    }

    @Test
    public void getTrailerReportItem_filteredByAssignedStatus_filtered() {
        TrailerReportJDBCDAO dao = new TrailerReportJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        dao.setDataSource(dataSource);
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(itData.teamGroupData.get(ITData.GOOD).group.getGroupID());
        groupIDs.add(itData.teamGroupData.get(ITData.INTERMEDIATE).group.getGroupID());
        Map<String, Object> filterMapAssigned = new HashMap<String, Object>();
        filterMapAssigned.put("assignedStatus", "1");
        PageParams params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.DESCENDING, "trailerName"), this.getFilters(filterMapAssigned));
        List<TrailerReportItem> results = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        for (TrailerReportItem item : results) {
            assertEquals(TrailerAssignedStatus.ASSIGNED, item.getAssignedStatus());
        }

        Map<String, Object> filterMapNotAssigned = new HashMap<String, Object>();
        filterMapNotAssigned.put("assignedStatus", "0");
        params = new PageParams(PAGE_1_START, PAGE_1_END, this.getTableSortField(SortOrder.DESCENDING, "trailerName"), this.getFilters(filterMapNotAssigned));
        results = dao.getTrailerReportItemByGroupPaging(groupIDs, params);
        for (TrailerReportItem item : results) {
            assertEquals(TrailerAssignedStatus.NOT_ASSIGNED, item.getAssignedStatus());
        }
    }

    private List<TableFilterField> getFilters(Map<String, Object> filterMap) {
        List<TableFilterField> retVal = new ArrayList<TableFilterField>();

        for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
            TableFilterField filter = new TableFilterField(entry.getKey(), entry.getValue(), FilterOp.LIKE);
            retVal.add(filter);
        }
        return retVal;
    }

    private TableSortField getTableSortField(SortOrder sortOrder, String fieldName) {
        return new TableSortField(sortOrder, fieldName);
    }

    private void updateTrailerReportItem() {
        String sql = "insert into "
                + TRAILER_TABLE
                + " "
                + " ( acctID, status, odometer, absOdometer, weight, year, name, make, model, color, vin, license, stateID, deviceID, pairingDate, entryDate, modified ) "
                + " values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            retVal.add(new Object[] {
                    itData.account.getAccountID(), Status.ACTIVE.getCode(), TEST_ODO, TEST_ODO, TEST_WEIGHT, TEST_YEAR, TEST_TRAILER_NAME + i, "testMake", "testmodel", "testcolor",
                    "testvin", "testlicnse", TEST_STATEID_UTAH, itData.teamGroupData.get(i).device.getDeviceID(), new Date(), new Date(), new Date() });
        }

        return retVal;
    }

    private void deletTestVehiclePerformanceRows() {
        DataSource dataSource = new ITDataSource().getRealDataSource();
        this.setDataSource(dataSource);
        SimpleJdbcTemplate template = getSimpleJdbcTemplate();
        String sql = "delete from " + TRAILER_TABLE + " where acctID = " + itData.account.getAccountID();
        int rows = template.update(sql);
        System.out.println("Rows Deleted: " + rows);
    }
}
