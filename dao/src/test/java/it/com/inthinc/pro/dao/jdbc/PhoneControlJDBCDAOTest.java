package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.PhoneControlJDBCDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PhoneControlJDBCDAOTest extends SimpleJdbcDaoSupport {

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }

    @Test
    public void CRUDTest() {
        PhoneControlJDBCDAO phoneControlJDBCDAO = new PhoneControlJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        phoneControlJDBCDAO.setDataSource(dataSource);
        boolean returnDriverID = false;

        Cellblock cellblock = new Cellblock();
        cellblock.setDriverID(39);
        cellblock.setAcctID(6);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        cellblock.setProvider(CellProviderType.CELL_CONTROL);
        cellblock.setProviderUser("luser");
        cellblock.setProviderPassword("666");
        cellblock.setCellPhone("07 noi doi");

        //create method
        Integer driverID = phoneControlJDBCDAO.create(cellblock.getDriverID(), cellblock);
        returnDriverID = (driverID != null);
        assertTrue(returnDriverID);

        //find by id method
        Cellblock cellblockFound = phoneControlJDBCDAO.findByID(cellblock.getDriverID());
        assertEquals(6, cellblock.getAcctID(), cellblockFound.getAcctID());
        assertEquals(0, cellblock.getCellStatus().getCode(), cellblockFound.getCellStatus().getCode());
        assertEquals("CELL_COTNROL", cellblock.getProvider().getName(), cellblockFound.getProvider().getName());
        assertEquals("luser", cellblock.getProviderUser(), cellblockFound.getProviderUser());
        assertEquals("666", cellblock.getProviderPassword(), cellblockFound.getProviderPassword());
        assertEquals("07 noi doi", cellblock.getCellPhone(), cellblockFound.getCellPhone());

        //delete method
        assertEquals(phoneControlJDBCDAO.deleteByID(cellblock.getDriverID()).intValue(), 1);

    }

    @Test
    public void updateTest() {
        PhoneControlJDBCDAO phoneControlJDBCDAO = new PhoneControlJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        phoneControlJDBCDAO.setDataSource(dataSource);
        boolean returnDriverID = false;

        Cellblock cellblock = new Cellblock();
        cellblock.setDriverID(39);
        cellblock.setAcctID(6);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        cellblock.setProvider(CellProviderType.CELL_CONTROL);
        cellblock.setProviderUser("luser");
        cellblock.setProviderPassword("666");
        cellblock.setCellPhone("07 noi doi");

        //create method
        Integer driverID = phoneControlJDBCDAO.create(cellblock.getDriverID(), cellblock);
        returnDriverID = (driverID != null);
        assertTrue(returnDriverID);

        //update method
        Cellblock cellblock2 = new Cellblock();
        cellblock2.setDriverID(39);
        cellblock2.setAcctID(4);
        cellblock2.setCellStatus(CellStatusType.ENABLED);
        cellblock2.setProvider(CellProviderType.CELL_CONTROL);
        cellblock2.setProviderUser("luser2");
        cellblock2.setProviderPassword("3333");
        cellblock2.setCellPhone("07 noi doi");

        Integer cellblockUpdate = phoneControlJDBCDAO.update(cellblock2);
        assertTrue(cellblockUpdate != null);

        // delete method
        assertEquals(phoneControlJDBCDAO.deleteByID(cellblock2.getDriverID()).intValue(), 1);
    }

    @Test
    public void getDriversWithDisabledPhonesTest() {

        PhoneControlJDBCDAO phoneControlJDBCDAO = new PhoneControlJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        phoneControlJDBCDAO.setDataSource(dataSource);
        boolean returnDriverID = false;

        Cellblock cellblock = new Cellblock();
        cellblock.setDriverID(41);
        cellblock.setAcctID(6);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        cellblock.setProvider(CellProviderType.CELL_CONTROL);
        cellblock.setProviderUser("luser");
        cellblock.setProviderPassword("666");
        cellblock.setCellPhone("07 noi doi");

        //create method
        Integer driverID = phoneControlJDBCDAO.create(cellblock.getDriverID(), cellblock);
        returnDriverID = (driverID != null);
        assertTrue(returnDriverID);

        //find drivers with disabled phones
        List<Cellblock> driversWithDisabledPhones = phoneControlJDBCDAO.getDriversWithDisabledPhones();

        assertTrue(driversWithDisabledPhones.size() > 0);

        //delete method
        assertEquals(phoneControlJDBCDAO.deleteByID(cellblock.getDriverID()).intValue(), 1);

    }

    @Test
    public void getCellblocksByAcctID() {
        PhoneControlJDBCDAO phoneControlJDBCDAO = new PhoneControlJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        phoneControlJDBCDAO.setDataSource(dataSource);
        boolean returnDriverID = false;

        Cellblock cellblock = new Cellblock();
        cellblock.setDriverID(42);
        cellblock.setAcctID(6);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        cellblock.setProvider(CellProviderType.CELL_CONTROL);
        cellblock.setProviderUser("luser");
        cellblock.setProviderPassword("666");
        cellblock.setCellPhone("07 noi doi");

        //create method
        Integer driverID = phoneControlJDBCDAO.create(cellblock.getDriverID(), cellblock);
        returnDriverID = (driverID != null);
        assertTrue(returnDriverID);

        //get cellblocks by account ID
        List<Cellblock> fountByAccID = phoneControlJDBCDAO.getCellblocksByAcctID(cellblock.getAcctID());

        assertTrue(fountByAccID.size() > 0);

        //delete method
        assertEquals(phoneControlJDBCDAO.deleteByID(cellblock.getDriverID()).intValue(), 1);


    }

    @Test
    public void findByPhoneID() {


    }


    private static void initApp() throws Exception {
    }
}
