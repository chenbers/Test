package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.app.States;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DriverJDBCDAOTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;
    DriverJDBCDAO driverJDBCDAO = new DriverJDBCDAO();
    private static Integer personIdForCreate;
    private static Integer personIdForUpdate;
    private static Integer personId;


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
        personIdForCreate = itData.teamGroupData.get(ITData.BAD).user.getPersonID();
        personIdForUpdate = itData.teamGroupData.get(ITData.GOOD).user.getPersonID();
        personId = itData.fleetUser.getPersonID();


    }

    @Test
     public void testGetDriversByGroupId(){
        DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            List<Driver> driverList = driverJDBCDAO.getDrivers(team.group.getGroupID());
            assertEquals("driver count - expected to be the same : " + teamIdx, 1, driverList.size());
        }

    }

    @Test
    public void testfindByPersonID(){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Person expectedPerson= team.driver.getPerson() ;
            Driver driver = driverJDBCDAO.findByID(team.driver.getDriverID());
            assertEquals("Driver Id - expected to be the same :  ",expectedPerson.getDriverID(), driver.getPerson().getDriverID());
            assertEquals("Account Id - expected to be the same :  ",expectedPerson.getAccountID(), driver.getPerson().getAccountID());
            assertEquals("Address Id - expected to be the same :  ",expectedPerson.getAddressID(), driver.getPerson().getAddressID());
            assertEquals("Crit - expected to be the same :  ",expectedPerson.getCrit(), driver.getPerson().getCrit());

        }

    }
    @Test
    @Ignore //TODO after insert barcode in xml
    public void testGetRfidsByBarcode (){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            //I don't find barcode in xml
            List<Long> getRifds = driverJDBCDAO.getRfidsByBarcode("xxx");


        }
    }

    @Test
    @Ignore //TODO after insert barcode in xml
    public void testGetDriverIDByBarcode (){
    }

        @Test
    public void testGetDriversName (){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            List<DriverName> driverList = driverJDBCDAO.getDriverNames(team.group.getGroupID());
            assertEquals("driver count - expected to be the same :" + teamIdx, 1, driverList.size());

        }
    }

    @Test
    public void testFindByID(){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Driver driver = driverJDBCDAO.findByID(team.driver.getDriverID());
            Person expectedPerson= team.driver.getPerson() ;
            assertEquals("Driver Id  - expected to be the same :  ",expectedPerson.getDriverID(), driver.getPerson().getDriverID());
            assertEquals("Account Id - expected to be the same :  ",expectedPerson.getAccountID(), driver.getPerson().getAccountID());
            assertEquals("Address Id - expected to be the same :  ",expectedPerson.getAddressID(), driver.getPerson().getAddressID());
            assertEquals("Crit  - expected to be the same :  ",expectedPerson.getCrit(), driver.getPerson().getCrit());

            assertEquals("FobId  - expected to be the same :  ",team.driver.getFobID(), driver.getFobID());
            assertEquals("License  - expected to be the same :  ",team.driver.getLicense(), driver.getLicense());
            assertEquals("LicenseClass  - expected to be the same :  ",team.driver.getLicenseClass(), driver.getLicenseClass());
            assertEquals("Rfid1  - expected to be the same :  ",team.driver.getRfid1(), driver.getRfid1());
            assertEquals("Rfid2  - expected to be the same :  ",team.driver.getRfid2(), driver.getRfid2());

        }

    }

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
    //create , find, delete
    @Test
    public void testCRUD() {
        DriverJDBCDAO driverJDBCDAO = new DriverJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        // create driver
        Driver driver = new Driver();
        driver.setGroupID(6030);
        driver.setCertifications("1234545");
        driver.setStatus(Status.valueOf(1));
        driver.setRfid1(Long.valueOf(300000001));
        driver.setRfid2(Long.valueOf(400000000));
        driver.setLicenseClass("C");
        driver.setBarcode("update_test");
        driver.setLicense("update_test");
        driver.setFobID("update_test");
        driver.setDot(RuleSetType.valueOf(2));
        driver.setPersonID(33333333);
        State state = new State();
        state.setAbbrev("AL");
        state.setName("Alabama");
        state.setStateID(1);
        driver.setState(state);
        driver.setExpiration(new Date());
        driver.setModified(new Date());
        driver.setPersonID(personId);
        assertNotNull(driver);

        driver.setPersonID(personIdForCreate);
        assertNotNull(driver);

        Integer driverId = driverJDBCDAO.create(driver.getGroupID(), driver);

        //find  for create
        Driver driver1Test = driverJDBCDAO.findByID(driverId);
        assertNotNull(driver1Test);
        assertEquals(" groupid expected to be the same ", driver.getGroupID(), driver1Test.getGroupID());
        assertEquals(" State expected to be the same ", driver.getStatus().getCode(), driver1Test.getStatus().getCode());
        assertEquals(" Driver Id expected to be the same ", driverId, driver1Test.getDriverID());
        assertEquals(" Person Id expected to be the same ", driver.getPersonID(), driver1Test.getPersonID());
        assertEquals(" Certification expected to be the same ", driver.getCertifications(), driver1Test.getCertifications());
        assertEquals(" Barcode expected to be the same ", driver.getBarcode(), driver1Test.getBarcode());
        assertEquals(" Rfid1 expected to be the same ", driver.getRfid1(), driver1Test.getRfid1());
        assertEquals(" Rfid2 expected to be the same ", driver.getRfid2(), driver1Test.getRfid2());

        //update
        Driver driverUpdate = new Driver();
        driverUpdate.setGroupID(6030);
        driverUpdate.setCertifications("1234545");
        driverUpdate.setStatus(Status.valueOf(1));
        driverUpdate.setRfid1(Long.valueOf(1000000001));
        driverUpdate.setRfid2(Long.valueOf(200000000));
        driverUpdate.setLicenseClass("B");
        driverUpdate.setBarcode("barcode_test");
        driverUpdate.setLicense("license_test");
        driverUpdate.setFobID("fobid_test");
        driverUpdate.setDot(RuleSetType.valueOf(1));
        driverUpdate.setPersonID(22222222);
        State stateUpdate = new State();
        stateUpdate.setAbbrev("UT");
        stateUpdate.setName("Utah");
        stateUpdate.setStateID(45);
        driverUpdate.setState(stateUpdate);
        driverUpdate.setExpiration(new Date());
        driverUpdate.setModified(new Date());
        driverUpdate.setPersonID(personIdForCreate);
        driverUpdate.setDriverID(driverId);

        //find for update
        Integer driverIdUpdate = driverJDBCDAO.update(driverUpdate);
        Driver driver1TestUpdate = driverJDBCDAO.findByID(driverIdUpdate);
        assertNotNull(driver1TestUpdate);
        assertEquals(" groupid expected to be the same ", driverUpdate.getGroupID(), driver1TestUpdate.getGroupID());
        assertEquals(" State expected to be the same ", driverUpdate.getStatus().getCode(), driver1TestUpdate.getStatus().getCode());
        assertEquals(" Driver Id expected to be the same ", driverIdUpdate, driver1TestUpdate.getDriverID());
        assertEquals(" Person Id expected to be the same ", driverUpdate.getPersonID(), driver1TestUpdate.getPersonID());
        assertEquals(" Certification expected to be the same ", driverUpdate.getCertifications(), driver1TestUpdate.getCertifications());
        assertEquals(" Barcode expected to be the same ", driverUpdate.getBarcode(), driver1TestUpdate.getBarcode());
        assertEquals(" Rfid1 expected to be the same ", driverUpdate.getRfid1(), driver1TestUpdate.getRfid1());
        assertEquals(" Rfid2 expected to be the same ", driverUpdate.getRfid2(), driver1TestUpdate.getRfid2());

        //test delete
        driverJDBCDAO.deleteByID(driverId);


    }

}
