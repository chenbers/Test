package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.com.inthinc.pro.dao.jdbc.ReportPaginationJDBCTest;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RedFlagJDBCDAOTest extends BaseJDBCTest {

    private static final Logger logger = Logger.getLogger(ReportPaginationJDBCTest.class);
    private static SiloService siloService;
    private static ITData itData;

    private static final String INTEGRATION_TEST_XML = "IntegrationTest.xml";
    private static Integer levelCount = 11;
    private static Integer goodGroupID;
    private static Integer goodVehicleID;
    private static String goodGroupName;
    private static String badGroupName;

    private static DateTime startDate = new DateTime().withYear(2014).withMonthOfYear(11).withDayOfMonth(1);
    private static DateTime endDate = startDate.plusMonths(1);

    TestFilterParams[] redFlagFilterTestListFlag = {
            new TestFilterParams("level", 6, 0),
            new TestFilterParams("driverName", "xxx", 0),
            new TestFilterParams("vehicleName", "xxx", 0),
    };

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INTEGRATION_TEST_XML);
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }

        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account account = accountDAO.findByID(itData.account.getAccountID());
        itData.account.setUnkDriverID(account.getUnkDriverID());
        goodGroupID = itData.fleetGroup.getGroupID();

        initApp();
    }

    private static void initApp() throws Exception {
        //StateJDBCDAO is not implemented
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        //DeviceJdbcDao is not implemented
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
    }

    @Test
    public void pageAndCountRedFlagTest() {
        RedFlagJDBCDAO redFlagJDBCDAO = new RedFlagJDBCDAO();
        redFlagJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());

        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        redFlagJDBCDAO.setEventDAO(eventDAO);


        // no filters
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Integer redFlagCount = redFlagJDBCDAO.getRedFlagCount(goodGroupID, startDate.toDate(), endDate.toDate(), 0, null);
            assertTrue(redFlagCount > 0);
            PageParams pageParams = new PageParams();
            pageParams.setStartRow(0);
            pageParams.setEndRow(redFlagCount - 1);

            List<RedFlag> redFlagList = redFlagJDBCDAO.getRedFlagPage(goodGroupID, startDate.toDate(), endDate.toDate(), 0, pageParams);
            assertTrue(!redFlagList.isEmpty());


        }
        //filters
        //Since there is no actual datagen for this i only tested for filters that return empty list otherwise a datagen with more data is required i think
        GroupData team = itData.teamGroupData.get(ITData.GOOD);
        Integer allDriverCount = redFlagJDBCDAO.getRedFlagCount(goodGroupID, startDate.toDate(), endDate.toDate(), 0, null);
        for (TestFilterParams testFilterParams : redFlagFilterTestListFlag) {
            PageParams pageParams = new PageParams();
            pageParams.setStartRow(0);
            pageParams.setEndRow(allDriverCount - 1);
            List<TableFilterField> filterList = new ArrayList<TableFilterField>();
            filterList.add(new TableFilterField(testFilterParams.field, testFilterParams.value));
            Integer redFlagCount = redFlagJDBCDAO.getRedFlagCount(goodGroupID, startDate.toDate(), endDate.toDate(), 0, filterList);

            assertTrue(redFlagCount == 0);
            pageParams.setFilterList(filterList);
            List<RedFlag> list = redFlagJDBCDAO.getRedFlagPage(goodGroupID, startDate.toDate(), endDate.toDate(), 0, pageParams);
            assertTrue(list.isEmpty());
        }
    }
}

class TestFilterParams {
    String field;
    Object value;
    Integer expectedCount;

    public TestFilterParams(String field, Object value, Integer expectedCount) {
        super();
        this.field = field;
        this.value = value;
        this.expectedCount = expectedCount;
    }
}



