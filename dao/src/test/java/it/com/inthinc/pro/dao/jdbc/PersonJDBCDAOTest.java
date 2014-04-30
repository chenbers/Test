package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.dao.jdbc.PersonJDBCDAO;
import com.inthinc.pro.dao.jdbc.UserJDBCDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.AccessPoint;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test personJDBCDAO.
 */
public class PersonJDBCDAOTest extends SimpleJdbcDaoSupport {

    //AddressJDBCDAOTest

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private String userName;
    private Integer groupId;
    private Integer personId;
    private Integer userId;
    private List<Integer> roleId;
    private List<AccessPoint> accessPoints;
    private List<Integer> mapLayers;
    private Integer accountID;


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

        userName = itData.districtUser.getUsername();
        groupId = itData.districtGroup.getGroupID();
        personId = itData.districtUser.getPersonID();
        userId = itData.districtUser.getUserID();
        roleId = itData.districtUser.getRoles();
        accessPoints = itData.districtUser.getAccessPoints();
        mapLayers = itData.districtUser.getSelectedMapLayerIDs();
        accountID = itData.districtGroup.getAccountID();



    }
    private static void initApp() throws Exception {
    }

     @Ignore
    @Test
    public void findTest() {
        PersonJDBCDAO personDAO = new PersonJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();

        UserJDBCDAO userDAO = new UserJDBCDAO();

        DriverJDBCDAO driverDAO = new DriverJDBCDAO();

        userDAO.setDataSource(dataSource);
        driverDAO.setDataSource(dataSource);

        personDAO.setDataSource(dataSource);
        personDAO.setUserDAO(userDAO);
        personDAO.setDriverDAO(driverDAO);

        //findByID method
        Person personByID = personDAO.findByID(personId);
        assertNotNull(personByID);
//        assertEquals(personByID.getPriEmail(), "PersonFleetemail650962342@example.com");
//        assertEquals(personByID.getPriPhone(),"5555555555");
//        assertEquals(personByID.getFirst(),"PersonFleet");
//        assertEquals(personByID.getLast(),"Last6509");
        //findByEmail method
        Person personByEmail = personDAO.findByEmail(personByID.getPriEmail());
        assertNotNull(personByEmail);
//        assertEquals(personByEmail.getPriEmail(),"PersonFleetemail650962342@example.com");
//        assertEquals(personByEmail.getPriPhone(),"5555555555");
//        assertEquals(personByEmail.getFirst(),"PersonFleet");
//        assertEquals(personByEmail.getLast(),"Last6509");

        //findByEmpID method
        Person personByEmpID = personDAO.findByEmpID(personByID.getAcctID(), personByID.getEmpid());
        assertNotNull(personByEmpID);
        assertEquals(personByEmpID.getPriEmail(),"PersonFleetemail650962342@example.com");
        assertEquals(personByEmpID.getPriPhone(),"5555555555");
        assertEquals(personByEmpID.getFirst(),"PersonFleet");
        assertEquals(personByEmpID.getLast(),"Last6509");

    }
    @Ignore
    @Test
    public void getPeopleInAccountTest() {
        PersonJDBCDAO personDAO = new PersonJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();

        UserJDBCDAO userDAO = new UserJDBCDAO();

        DriverJDBCDAO driverDAO = new DriverJDBCDAO();

        userDAO.setDataSource(dataSource);
        driverDAO.setDataSource(dataSource);

        personDAO.setDataSource(dataSource);
        personDAO.setUserDAO(userDAO);
        personDAO.setDriverDAO(driverDAO);

        //getPeopleInAccount method
        List<Person> personList = personDAO.getPeopleInAccount(accountID);
        assertNotNull(personList);
        for(int i=0;i<personList.size();i++){
        assertEquals(personList.get(i).getAcctID(), accountID);
        }
    }
    @Ignore
    @Test
    public void getPeopleInGroupHierarchyTest() {
        PersonJDBCDAO personDAO = new PersonJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();

        UserJDBCDAO userDAO = new UserJDBCDAO();

        DriverJDBCDAO driverDAO = new DriverJDBCDAO();

        userDAO.setDataSource(dataSource);
        driverDAO.setDataSource(dataSource);

        personDAO.setDataSource(dataSource);
        personDAO.setUserDAO(userDAO);
        personDAO.setDriverDAO(driverDAO);

        //getPeopleInGroupHierarchy method
        List<Person> personList = personDAO.getPeopleInGroupHierarchy(groupId);
        assertNotNull(personList);
        List<Integer> groupIds = driverDAO.getGroupIdDeep(groupId);

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getUser() != null) {

                assertTrue(groupIds.contains(personList.get(i).getUser().getGroupID()));
            }
            if (personList.get(i).getDriver() != null) {
                assertTrue(groupIds.contains(personList.get(i).getDriver().getGroupID()));
            }
        }
    }



    @Test
    public void createUpdateDeleteTest(){

        PersonJDBCDAO personDAO = new PersonJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();

        UserJDBCDAO userDAO = new UserJDBCDAO();
        DriverJDBCDAO driverDAO = new DriverJDBCDAO();
        AddressJDBCDAO addressDAO = new AddressJDBCDAO();

        userDAO.setDataSource(dataSource);
        driverDAO.setDataSource(dataSource);
        addressDAO.setDataSource(dataSource);
        personDAO.setDataSource(dataSource);

        personDAO.setUserDAO(userDAO);
        personDAO.setDriverDAO(driverDAO);
        personDAO.setAddressDAO(addressDAO);

        // created user
        User userToCreate = new User();
        userToCreate.setGroupID(6510);
        userToCreate.setUsername("TestCreate");
        userToCreate.setPassword("create");
        userToCreate.setStatus(Status.INACTIVE);
        userToCreate.setRoles(roleId);
        userToCreate.setAccessPoints(accessPoints);
        userToCreate.setSelectedMapLayerIDs(mapLayers);

        // created driver
        Driver driverToCreate = new Driver();
        driverToCreate.setGroupID(6510);
        driverToCreate.setCertifications("1234545");
        driverToCreate.setStatus(Status.valueOf(1));
        driverToCreate.setRfid1(Long.valueOf(300000001));
        driverToCreate.setRfid2(Long.valueOf(400000000));
        driverToCreate.setLicenseClass("C");
        driverToCreate.setBarcode("create_test");
        driverToCreate.setLicense("create");
        driverToCreate.setFobID("create_test");
        driverToCreate.setDot(RuleSetType.valueOf(2));
        driverToCreate.setPersonID(33333333);
        State state = new State();
        state.setAbbrev("AL");
        state.setName("Alabama");
        state.setStateID(1);
        driverToCreate.setState(state);
        driverToCreate.setExpiration(new Date());
        driverToCreate.setModified(new Date());

        //update address
        Address addressToInsert = new Address();
        addressToInsert.setAccountID(1);
        addressToInsert.setAddr1("832 Street");
        addressToInsert.setCity("City 71");
        state.setAbbrev("UT");
        state.setName("Utah");
        state.setStateID(45);
        addressToInsert.setState(state);
        addressToInsert.setZip("10021");

        // updated person
        Person personToCreate = new Person();
        personToCreate.setUser(userToCreate);
        personToCreate.setDriver(driverToCreate);

        personToCreate.setAcctID(accountID);
        personToCreate.setAddressID(addressToInsert.getAddrID());
        personToCreate.setAddress(addressToInsert);
        personToCreate.setCrit(5);
        personToCreate.setDept("testCreate");
        personToCreate.setEmpid("Z666666");
        personToCreate.setDob(new Date());
        personToCreate.setFirst("testCreate");
        personToCreate.setFuelEfficiencyType(FuelEfficiencyType.KMPL);
        personToCreate.setGender(Gender.MALE);
        personToCreate.setHeight(200);
        personToCreate.setInfo(1);
        personToCreate.setLast("testCreate");
        personToCreate.setLocale(Locale.CHINA);
        personToCreate.setWarn(1);
        personToCreate.setPriEmail("testCreate@inthinc.com");
        personToCreate.setPriPhone("666666");
        personToCreate.setPriText("testCreate");
        personToCreate.setReportsTo("testCreate");
        personToCreate.setStatus(Status.INACTIVE);
        personToCreate.setSecEmail("testCreateSec@inthinc.com");
        personToCreate.setSecPhone("999999");
        personToCreate.setSecText("testCreateSec");
        personToCreate.setMeasurementType(MeasurementType.ENGLISH);
        personToCreate.setTitle("dude");
        personToCreate.setMiddle("m");
        personToCreate.setSuffix("s");
        personToCreate.setWeight(100);

        personToCreate.setTimeZone(TimeZone.getTimeZone("Europe/Copenhagen"));

        Integer createdPersonId = personDAO.create(accountID, personToCreate);

        //find by id test
        Person createdPerson = personDAO.findByID(createdPersonId);

        assertNotNull(createdPerson);
        assertEquals(createdPerson.getFullNameWithPriEmail(),personToCreate.getFullNameWithPriEmail());
        assertEquals(createdPerson.getUser().getUsername(), personToCreate.getUser().getUsername());

//        ----------------------------------------------------------------------------------------


        // updated user
        User userToUpdate = new User();
        userToUpdate.setUserID(createdPerson.getUserID());
        userToUpdate.setGroupID(6509);
        userToUpdate.setPersonID(createdPersonId);
        userToUpdate.setUsername("TestUpdate");
        userToUpdate.setPassword("update");
        userToUpdate.setStatus(Status.INACTIVE);
        userToUpdate.setRoles(roleId);
        userToUpdate.setAccessPoints(accessPoints);
        userToUpdate.setSelectedMapLayerIDs(mapLayers);

        // updated driver
        Driver driverToUpdate = new Driver();
        driverToUpdate.setPersonID(createdPersonId);
        driverToUpdate.setDriverID(createdPerson.getDriverID());
        driverToUpdate.setGroupID(6509);
        driverToUpdate.setCertifications("1234545");
        driverToUpdate.setStatus(Status.valueOf(1));
        driverToUpdate.setRfid1(Long.valueOf(300000001));
        driverToUpdate.setRfid2(Long.valueOf(400000000));
        driverToUpdate.setLicenseClass("C");
        driverToUpdate.setBarcode("update_test");
        driverToUpdate.setLicense("update_test");
        driverToUpdate.setFobID("update_test");
        driverToUpdate.setDot(RuleSetType.valueOf(2));
        State stateUpdate = new State();
        stateUpdate.setAbbrev("AL");
        stateUpdate.setName("Alabama");
        stateUpdate.setStateID(1);
        driverToUpdate.setState(state);
        driverToUpdate.setExpiration(new Date());
        driverToUpdate.setModified(new Date());

        //update address
        Address addressToUpdate = new Address();
        addressToUpdate.setAccountID(1);
        addressToUpdate.setAddr1("832 Street1");
        addressToUpdate.setCity("City 711");
        state.setAbbrev("UT");
        state.setName("Utah");
        state.setStateID(45);
        addressToInsert.setState(state);
        addressToInsert.setZip("100212");

        // updated person
        Person personToUpdate = new Person();
        personToUpdate.setUser(userToUpdate);
        personToUpdate.setDriver(driverToUpdate);

        personToUpdate.setPersonID(createdPersonId);
        personToUpdate.setAcctID(accountID);
        personToUpdate.setAddressID(null);
        personToUpdate.setAddress(addressToInsert);
        personToUpdate.setCrit(5);
        personToUpdate.setDept("testUpdate");
        personToUpdate.setEmpid("Z666666");
        personToUpdate.setDob(new Date());
        personToUpdate.setFirst("testUpdate");
        personToUpdate.setFuelEfficiencyType(FuelEfficiencyType.KMPL);
        personToUpdate.setGender(Gender.MALE);
        personToUpdate.setHeight(200);
        personToUpdate.setInfo(1);
        personToUpdate.setLast("testUpdate");
        personToUpdate.setLocale(Locale.CHINA);
        personToUpdate.setWarn(1);
        personToUpdate.setPriEmail("testUpdate@inthinc.com");
        personToUpdate.setPriPhone("666666");
        personToUpdate.setPriText("testUpdate");
        personToUpdate.setReportsTo("testUpdate");
        personToUpdate.setStatus(Status.INACTIVE);
        personToUpdate.setSecEmail("testUpdateSec@inthinc.com");
        personToUpdate.setSecPhone("999999");
        personToUpdate.setSecText("testUpdateSec");
        personToUpdate.setMeasurementType(MeasurementType.ENGLISH);
        personToUpdate.setTitle("dude");
        personToUpdate.setMiddle("m");
        personToUpdate.setSuffix("s");
        personToUpdate.setWeight(100);

        personToUpdate.setTimeZone(TimeZone.getTimeZone("Europe/Copenhagen"));

        Integer updatedPersonId = personDAO.update(personToUpdate);

        //find by id test
        Person updatedPerson = personDAO.findByID(updatedPersonId);

        assertNotNull(updatedPerson);
        assertEquals(updatedPerson.getFullNameWithPriEmail(),personToUpdate.getFullNameWithPriEmail());
        assertEquals(updatedPerson.getUser().getUsername(), personToUpdate.getUser().getUsername());

        //delete vehicle when finish
        personDAO.delete(createdPerson);
    }

}
