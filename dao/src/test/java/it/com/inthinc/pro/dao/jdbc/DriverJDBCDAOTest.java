package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Person;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DriverJDBCDAOTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;
    DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        itData = new ITData();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }
//
//    @Test
//     public void testGetDriversByGroupId(){
//        DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        driverJDBCDAO.setDataSource(dataSource);
//
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            GroupData team = itData.teamGroupData.get(teamIdx);
//            List<Driver> driverList = driverJDBCDAO.getDrivers(team.group.getGroupID());
//            assertEquals("driver count - expected to be the same : " + teamIdx, 1, driverList.size());
//        }
//
//    }
//
//    @Test
//    public void testfindByPersonID(){
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        driverJDBCDAO.setDataSource(dataSource);
//
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            GroupData team = itData.teamGroupData.get(teamIdx);
//            Person expectedPerson= team.driver.getPerson() ;
//            Driver driver = driverJDBCDAO.findByID(team.driver.getDriverID());
//            assertEquals("Driver Id - expected to be the same :  ",expectedPerson.getDriverID(), driver.getPerson().getDriverID());
//            assertEquals("Account Id - expected to be the same :  ",expectedPerson.getAccountID(), driver.getPerson().getAccountID());
//            assertEquals("Address Id - expected to be the same :  ",expectedPerson.getAddressID(), driver.getPerson().getAddressID());
//            assertEquals("Crit - expected to be the same :  ",expectedPerson.getCrit(), driver.getPerson().getCrit());
//
//        }
//
//    }
//    @Test
//    @Ignore //TODO after insert barcode in xml
//    public void testGetRfidsByBarcode (){
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        driverJDBCDAO.setDataSource(dataSource);
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            //I don't find barcode in xml
//            List<Long> getRifds = driverJDBCDAO.getRfidsByBarcode("xxx");
//
//
//        }
//    }
//
//    @Test
//    @Ignore //TODO after insert barcode in xml
//    public void testGetDriverIDByBarcode (){
//    }

//        @Test
//    public void testGetDriversName (){
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        driverJDBCDAO.setDataSource(dataSource);
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            GroupData team = itData.teamGroupData.get(teamIdx);
//            List<DriverName> driverList = driverJDBCDAO.getDriverNames(team.group.getGroupID());
//            assertEquals("driver count - expected to be the same :" + teamIdx, 1, driverList.size());
//
//        }
//    }
//
//    @Test
//    public void testFindByID(){
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        driverJDBCDAO.setDataSource(dataSource);
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            GroupData team = itData.teamGroupData.get(teamIdx);
//            Driver driver = driverJDBCDAO.findByID(team.driver.getDriverID());
//            Person expectedPerson= team.driver.getPerson() ;
//            assertEquals("Driver Id  - expected to be the same :  ",expectedPerson.getDriverID(), driver.getPerson().getDriverID());
//            assertEquals("Account Id - expected to be the same :  ",expectedPerson.getAccountID(), driver.getPerson().getAccountID());
//            assertEquals("Address Id - expected to be the same :  ",expectedPerson.getAddressID(), driver.getPerson().getAddressID());
//            assertEquals("Crit  - expected to be the same :  ",expectedPerson.getCrit(), driver.getPerson().getCrit());
//
//            assertEquals("FobId  - expected to be the same :  ",team.driver.getFobID(), driver.getFobID());
//            assertEquals("License  - expected to be the same :  ",team.driver.getLicense(), driver.getLicense());
//            assertEquals("LicenseClass  - expected to be the same :  ",team.driver.getLicenseClass(), driver.getLicenseClass());
//            assertEquals("Rfid1  - expected to be the same :  ",team.driver.getRfid1(), driver.getRfid1());
//            assertEquals("Rfid2  - expected to be the same :  ",team.driver.getRfid2(), driver.getRfid2());
//
//        }
//
//    }

    @Test
    public void testAllDrivers(){
        DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            List<Driver> driverList = driverJDBCDAO.getAllDrivers(team.group.getGroupID());
            assertTrue(driverList.size() >= 1);

        }

    }

}
