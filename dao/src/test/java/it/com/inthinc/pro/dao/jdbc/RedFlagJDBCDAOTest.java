package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.RedFlagJDBCDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.pagination.Range;
import it.com.inthinc.pro.dao.model.GroupListData;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.util.DataGenForReportPaginationTesting;
import jxl.write.DateTime;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Date;

public class RedFlagJDBCDAOTest extends BaseJDBCTest{

    private static final Logger logger = Logger.getLogger(ReportPaginationJDBCTest.class);
    private static SiloService siloService;
    private static ITDataExt itData;

    private static final String PAGINATION_BASE_DATA_XML = "ReportPageTest.xml";
    private static Integer countPerGroup;
    private static Integer goodGroupID;
    private static String goodGroupName;
    private static String badGroupName;

    TestFilterParams[] redFlagFilterTestList = {
            new TestFilterParams("groupID", "XXX", 0),
            new TestFilterParams("groupID", goodGroupID.toString(), countPerGroup),
            new TestFilterParams("vehicleID", "XXX", 0),
            new TestFilterParams("vehicleID", "e", countPerGroup),
            new TestFilterParams("driverID", "XXX", 0),
            new TestFilterParams("driverID", "e", countPerGroup),
    };

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITDataExt();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGINATION_BASE_DATA_XML);
        countPerGroup = DataGenForReportPaginationTesting.NUM_DRIVERS_VEHICLES_DEVICES;
        if (!itData.parseTestDataExt(stream, siloService, countPerGroup)) {
            throw new Exception("Error parsing Test data xml file");
        }

        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account account = accountDAO.findByID(itData.account.getAccountID());
        itData.account.setUnkDriverID(account.getUnkDriverID());

        goodGroupID = itData.teamGroupListData.get(ITData.GOOD).group.getGroupID();
        goodGroupName = itData.teamGroupListData.get(ITData.GOOD).group.getName();
        badGroupName = itData.teamGroupListData.get(ITData.BAD).group.getName();

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

//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();

    }
     @Test
    public void pageAndCountRedFlagTest(){
         RedFlagJDBCDAO redFlagJDBCDAO = new RedFlagJDBCDAO();
         redFlagJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());

         EventHessianDAO eventDAO = new EventHessianDAO();
         eventDAO.setSiloService(siloService);
         redFlagJDBCDAO.setEventDAO(eventDAO);

         org.joda.time.DateTime today = new org.joda.time.DateTime();

         // no filters
         for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {

        GroupListData team = itData.teamGroupListData.get(teamIdx);
        Integer redFlagCount = redFlagJDBCDAO.getRedFlagCount(team.group.getGroupID(), today.minusYears(2).toDate() , today.toDate(), 0, null);
             assertTrue(redFlagCount>0);


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
