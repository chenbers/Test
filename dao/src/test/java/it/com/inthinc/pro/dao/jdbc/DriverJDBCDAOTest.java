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
    private static Integer personIdInv;


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

        //person id invented
        personIdInv= 21058;

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

        // created driver
        Driver driverToCreate = new Driver();
        driverToCreate.setGroupID(6808);
        driverToCreate.setCertifications("1234545");
        driverToCreate.setStatus(Status.valueOf(1));
        driverToCreate.setRfid1(Long.valueOf(300000001));
        driverToCreate.setRfid2(Long.valueOf(400000000));
        driverToCreate.setLicenseClass("C");
        driverToCreate.setBarcode("create_test");
        driverToCreate.setLicense("create");
        driverToCreate.setFobID("create_test");
        driverToCreate.setDot(RuleSetType.valueOf(2));
        driverToCreate.setPersonID(21763);
        State state = new State();
        state.setAbbrev("AL");
        state.setName("Alabama");
        state.setStateID(1);
        driverToCreate.setState(state);
        driverToCreate.setExpiration(new Date());
        driverToCreate.setModified(new Date());
        assertNotNull(driverToCreate);

        Integer driverIdTest = driverJDBCDAO.create(driverToCreate.getGroupID(), driverToCreate);

        //find  for create
        Driver driverTest = driverJDBCDAO.findByID(driverIdTest);
        assertEquals(6808, driverToCreate.getGroupID(), driverTest.getGroupID());
        assertEquals(1, driverToCreate.getStatus().getCode(), driverTest.getStatus().getCode());
        assertEquals("C", driverToCreate.getLicenseClass(), driverTest.getLicenseClass());
        assertEquals("create_test", driverToCreate.getBarcode(), driverTest.getBarcode());
        assertEquals("create", driverToCreate.getLicense(), driverTest.getLicense());
        assertEquals("create_test", driverToCreate.getFobID(), driverTest.getFobID());
        assertEquals(21763, driverToCreate.getPersonID(), driverTest.getPersonID());


        //update
        Driver driverUpdate = new Driver();
        driverUpdate.setGroupID(6808);
        driverUpdate.setPersonID(21763);
        driverUpdate.setCertifications("1234545");
        driverUpdate.setStatus(Status.valueOf(1));
        driverUpdate.setRfid1(Long.valueOf(1000000001));
        driverUpdate.setRfid2(Long.valueOf(200000000));
        driverUpdate.setLicenseClass("B");
        driverUpdate.setBarcode("barcode_test");
        driverUpdate.setLicense("license_test");
        driverUpdate.setFobID("fobid_test");
        driverUpdate.setDot(RuleSetType.valueOf(1));
        State stateUpdate = new State();
        stateUpdate.setAbbrev("UT");
        stateUpdate.setName("Utah");
        stateUpdate.setStateID(45);
        driverUpdate.setState(stateUpdate);
        driverUpdate.setExpiration(new Date());
        driverUpdate.setModified(new Date());
        driverUpdate.setDriverID(driverIdTest);

        //find for update
        Integer driverIdUpdate = driverJDBCDAO.update(driverUpdate);

        Driver driver1TestUpdate = driverJDBCDAO.findByID(driverIdUpdate);

        assertNotNull(driver1TestUpdate);
        assertEquals(6808, driverUpdate.getGroupID(), driver1TestUpdate.getGroupID());
        assertEquals(1, driverUpdate.getStatus().getCode(), driver1TestUpdate.getStatus().getCode());
        assertEquals(21763, driverUpdate.getPersonID(), driver1TestUpdate.getPersonID());
        assertEquals("1234545", driverUpdate.getCertifications(), driver1TestUpdate.getCertifications());
        assertEquals("barcode_test", driverUpdate.getBarcode(), driver1TestUpdate.getBarcode());
        assertEquals("license_test", driverUpdate.getLicense(), driver1TestUpdate.getLicense());
        assertEquals("B", driverUpdate.getLicenseClass(), driver1TestUpdate.getLicenseClass());

        //test delete
        driverJDBCDAO.deleteByID(driverIdTest);


    }

}
