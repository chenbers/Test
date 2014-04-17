package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.TablePreferenceJDBCDAO;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import it.com.inthinc.pro.dao.model.GroupData;
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

/**
 * Test class for TablePreferenceJDBCDAO
 */
public class TablePreferenceJDBCDAOTest extends SimpleJdbcDaoSupport {

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
    public void createTableTest() {
        TablePreferenceJDBCDAO tablePreferenceJDBCDAO = new TablePreferenceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        tablePreferenceJDBCDAO.setDataSource(dataSource);
        boolean returnTableID = false;

        TablePreference tablePreference = new TablePreference();
        tablePreference.setUserID(51);
        tablePreference.setTableType(TableType.RED_FLAG);
        tablePreference.setFlags("1904");


        // create method
        Integer tableID = tablePreferenceJDBCDAO.create(tablePreference.getTablePrefID(), tablePreference);
        returnTableID = (tableID != null);
        assertTrue(returnTableID);

        //find by ID method
        TablePreference tablePreferenceFound = tablePreferenceJDBCDAO.findByID(tableID);
        assertEquals(51, tablePreference.getUserID(), tablePreferenceFound.getUserID());
        assertEquals(1, tablePreference.getTableType().getCode(), tablePreferenceFound.getTableType().getCode());
        assertEquals("1904", tablePreference.getFlags(), tablePreferenceFound.getFlags());

        assertTrue(tablePreference != null);

        //delete method
        assertEquals(tablePreferenceJDBCDAO.deleteByID(tableID).intValue(), 1);
    }

    @Test
    public void getTablePrefByIDTest() {
        TablePreferenceJDBCDAO tablePreferenceJDBCDAO = new TablePreferenceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        tablePreferenceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer userID = 1;
        List<TablePreference> userNames = tablePreferenceJDBCDAO.getTablePreferencesByUserID(userID);

        assertTrue(userNames.size() > 0);
    }

    @Test
    public void findByIDTest() {
        TablePreferenceJDBCDAO tablePreferenceJDBCDAO = new TablePreferenceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        tablePreferenceJDBCDAO.setDataSource(dataSource);


        Integer tablePrefID = 77;
        TablePreference tablePreference = tablePreferenceJDBCDAO.findByID(tablePrefID);

        assertNotNull(tablePreference);
    }

    @Test
    public void updateTest() {
        TablePreferenceJDBCDAO tablePreferenceJDBCDAO = new TablePreferenceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        tablePreferenceJDBCDAO.setDataSource(dataSource);
        boolean returnTableID = false;

        //tablePref to insert
        TablePreference tablePreferenceToInsert = new TablePreference();
        tablePreferenceToInsert.setUserID(51);
        tablePreferenceToInsert.setTableType(TableType.RED_FLAG);
        tablePreferenceToInsert.setFlags("1111");

        //create method
        Integer tableID = tablePreferenceJDBCDAO.create(tablePreferenceToInsert.getTablePrefID(), tablePreferenceToInsert);
        returnTableID = (tableID != null);
        assertTrue(returnTableID);

        //tablePref to update
        TablePreference tablePreferenceToUpdate = new TablePreference();
        tablePreferenceToUpdate.setUserID(2);
        tablePreferenceToUpdate.setTableType(TableType.CRASH_HISTORY);
        tablePreferenceToUpdate.setFlags("11");
        tablePreferenceToUpdate.setTablePrefID(tableID);

        //update method
        Integer tablePrefID = tablePreferenceJDBCDAO.update(tablePreferenceToUpdate);

        assertTrue(tablePrefID > 0);

        //delete method
        assertEquals(tablePreferenceJDBCDAO.deleteByID(tableID).intValue(), 1);
    }

    private static void initApp() throws Exception {
    }
}
