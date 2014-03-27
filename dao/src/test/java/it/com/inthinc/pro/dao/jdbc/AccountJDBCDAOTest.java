package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.app.States;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
import it.config.ITDataSource;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import it.config.IntegrationConfig;
import it.util.DataGenForReportPaginationTesting;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.jdbc.AccountJDBCDAO;

public class AccountJDBCDAOTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static ITDataExt itData;

    private static final String PAGINATION_BASE_DATA_XML = "ReportPageTest.xml";
    private static Integer countPerGroup;
    private static AccountJDBCDAO accountJDBCDAO;
    private static Integer AcctId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        itData = new ITDataExt();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGINATION_BASE_DATA_XML);
        countPerGroup = DataGenForReportPaginationTesting.NUM_DRIVERS_VEHICLES_DEVICES;
        if (!itData.parseTestDataExt(stream, siloService, countPerGroup)) {
            throw new Exception("Error parsing Test data xml file");
        }

        accountJDBCDAO = new AccountJDBCDAO();
        accountJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Account account = accountJDBCDAO.findByID(itData.account.getAccountID());
        itData.account.setUnkDriverID(account.getUnkDriverID());
    }

    @Test
    public void getAllActiveAccountIDsTest() {
        List<Long> acctIDs = accountJDBCDAO.getAllValidAcctIDs();
        assertNotNull(acctIDs);
    }

    @Test
    public void getAllAccountIDsTest() {
        List<Account> acctIDs = accountJDBCDAO.getAllAcctIDs();
        assertNotNull(acctIDs);
        assertTrue(acctIDs.size() >= 1);

    }

    @Test
    public void testFindById() {
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            Account account = accountJDBCDAO.findByID(itData.account.getAccountID());
            assertNotNull(account);
            assertEquals("name expect to be the same ", account.getAcctName(), itData.account.getAcctName());
            assertEquals("unkDriverId expect to be the same ", account.getUnkDriverID(), itData.account.getUnkDriverID());
            assertEquals("status expect to be the same ", account.getStatus(), itData.account.getStatus());
            assertEquals("hos expect to be the same ", account.getHos(), itData.account.getHos());

        }

    }
    @Test
    public void testUpdate(){
        Account accountUpdate = new Account();

        accountUpdate.setZonePublishDate(new DateTime(1969, 1, 1, 1, 1, 1, 1).toDate());
        accountUpdate.setStatus(Status.valueOf(1));
        accountUpdate.setBillID(2);
        accountUpdate.setAddressID(2);
        accountUpdate.setAcctName("test_update");
        accountUpdate.setHos(AccountHOSType.valueOf(2));
        accountUpdate.setUnkDriverID(2);
        AccountAttributes accountAttributes= new AccountAttributes();

        accountAttributes.setSupportContact1("");
        accountAttributes.setSupportContact2("");
        accountAttributes.setSupportContact3("");
        accountAttributes.setSupportContact4("");
        accountAttributes.setSupportContact5("");
        accountUpdate.setProps(accountAttributes);

        accountUpdate.setAccountID(itData.account.getAccountID());
        Integer acctIdUpdate = accountJDBCDAO.update(accountUpdate);
        Account accountFindUpdate = accountJDBCDAO.findByID(acctIdUpdate);
        assertNotNull(accountFindUpdate);
        assertNotNull(acctIdUpdate);
     }
}
