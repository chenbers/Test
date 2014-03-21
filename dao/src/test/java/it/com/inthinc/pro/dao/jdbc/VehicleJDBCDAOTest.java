package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.cassandra.LocationCassandraDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.VehicleJDBCDAO;
import com.inthinc.pro.model.*;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;
import javax.swing.text.DateFormatter;
import javax.xml.stream.Location;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VehicleJDBCDAOTest extends SimpleJdbcDaoSupport {

    private LocationDAO locationDAO;
    private Interval interval;

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private static int randomInt = RandomUtils.nextInt(99999);
    private static Integer drivIDd= 10949;

    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

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

    private static void initApp() throws Exception {
    }


    @Test
    public void getVehiclesInGroupHierarchyTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Integer groupID = team.group.getGroupID();
            List<Vehicle> vehiclesList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);

        assertTrue(!vehiclesList.equals("0"));
      }

    }

    @Test
    public void getVehiclesInGroupTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
        GroupData team = itData.teamGroupData.get(teamIdx);
        Integer groupID = team.group.getGroupID();
        List<Vehicle> vehicleListInGroup = vehicleDAO.getVehiclesInGroup(groupID);

            assertTrue(vehicleListInGroup.size() > 0);

        }
    }

    @Test
    public void getMilesDrivenTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            team.vehicle.getVehicleID();
            Integer vehicle = vehicleDAO.getMilesDriven(team.vehicle.getVehicleID());

            assertTrue(!vehicle.equals("0"));
        }
    }


    @Test
    public void getVehicleNamesTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

            GroupData team = itData.teamGroupData.get(0);
            Integer groupID = team.group.getGroupID();
            List<VehicleName> vehicleNames = vehicleDAO.getVehicleNames(groupID);

            assertTrue(vehicleNames.size()>0);

    }

    @Test
    public void findByDriverInGroupTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);
        for (GroupData testGroupData : itData.teamGroupData) {
            Driver driverID = testGroupData.driver;
            Group groupID = testGroupData.group;

            Vehicle findByDriverInGroup = vehicleDAO.findByDriverInGroup(driverID.getDriverID() ,groupID.getGroupID());

            assertTrue(!findByDriverInGroup.equals("0"));

        }
    }

    @Test
    public void findByVINTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);
        for (GroupData testGroupData : itData.teamGroupData) {
            Vehicle vehicle = testGroupData.vehicle;

            Vehicle findByVIN = vehicleDAO.findByVIN(vehicle.getVIN());

            assertNotNull(findByVIN);

            assertTrue(!findByVIN.equals("0"));
        }

    }

    @Test
    public void findByDriverID() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);
        for (GroupData testGroupData : itData.teamGroupData) {
            Driver driver = testGroupData.driver;

            Vehicle findByDriverID = vehicleDAO.findByDriverID(driver.getDriverID());

            assertNotNull(findByDriverID);

            assertTrue(!findByDriverID.equals("0"));
        }

    }

    @Test
    public void createTest_updateTest() {

        //create  method  test
        boolean returnsVehicleID = false;
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

        Vehicle vehicle = new Vehicle();
        vehicle.setGroupID(2);
        vehicle.setColor("Black");
        vehicle.setMake("Toyota");
        vehicle.setModel("Tacoma");
        vehicle.setName("IT" + randomInt);

        State state = new State();
        state.setAbbrev("UT");
        state.setName("Utah");
        state.setStateID(45);

        vehicle.setState(state);
        vehicle.setStatus(Status.INACTIVE);
        vehicle.setVIN("VIN" + randomInt);
        vehicle.setVtype(VehicleType.LIGHT);
        vehicle.setYear(2007);
        vehicle.setOdometer(24526);


        Integer vehicleID = vehicleDAO.create(vehicle.getVehicleID(), vehicle);
        returnsVehicleID = (vehicleID != null);
        assertTrue(returnsVehicleID);

        //find by id test
        Vehicle createdVehicle = vehicleDAO.findByID(vehicleID);

        assertEquals("Toyota", vehicle.getMake(), createdVehicle.getMake());
        assertEquals("Tacoma", vehicle.getModel(), createdVehicle.getModel());
        assertEquals(2007, vehicle.getYear(), createdVehicle.getYear());
        assertEquals(2, vehicle.getGroupID(), createdVehicle.getGroupID());
        assertEquals("VIN" + randomInt, vehicle.getVIN(), createdVehicle.getVIN());
        assertEquals(2, vehicle.getStatus().getCode(), createdVehicle.getStatus().getCode());
        assertEquals(0, vehicle.getVtype().getCode(), createdVehicle.getVtype().getCode());


        //update  method  test
        Vehicle vehicleUpdate = new Vehicle();
        vehicleUpdate.setGroupID(2);
        vehicleUpdate.setColor("Red");
        vehicleUpdate.setMake("Toyota");
        vehicleUpdate.setModel("Celica");
        vehicleUpdate.setName("NAME" + randomInt);

        State stateUpdate = new State();
        stateUpdate.setAbbrev("GA");
        stateUpdate.setName("Georgia");
        stateUpdate.setStateID(11);

        vehicleUpdate.setState(stateUpdate);
        vehicleUpdate.setStatus(Status.ACTIVE);
        vehicleUpdate.setVIN("VIN" + randomInt);
        vehicleUpdate.setVtype(VehicleType.MEDIUM);
        vehicleUpdate.setYear(2008);
        vehicleUpdate.setVehicleID(vehicleID);

        vehicleDAO.update(vehicleUpdate);

        //find vehicle by ID  after update
        Vehicle updatedVehicle = vehicleDAO.findByID(vehicleID);

        assertEquals("Toyota", vehicleUpdate.getMake(), updatedVehicle.getMake());
        assertEquals("Celica", vehicleUpdate.getModel(), updatedVehicle.getModel());
        assertEquals(2008, vehicleUpdate.getYear(), updatedVehicle.getYear());
        assertEquals(2, vehicle.getGroupID(), updatedVehicle.getGroupID());
        assertEquals("VIN" + randomInt, vehicle.getVIN(), updatedVehicle.getVIN());
        assertEquals(1, vehicle.getStatus().getCode(), updatedVehicle.getStatus().getCode());


        //delete vehicle when finish
        vehicleDAO.deleteByID(vehicleID);

    }

     @Test
    public void latLngDistanceTest(){
         VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
         DataSource dataSource = new ITDataSource().getRealDataSource();
         vehicleDAO.setDataSource(dataSource);
         double distanceTest1=vehicleDAO.latLngDistance(44.433841, 26.137519, 44.438437, 26.118293);
         double distanceTest2=vehicleDAO.latLngDistance(44.379516, 25.986233, 44.438437, 26.118293);
         double dt1=Math.round(distanceTest1 * 100.0) / 100.0;
         double dt2=Math.round(distanceTest2 * 100.0) / 100.0;
         assertTrue(dt1==1.0);
         assertTrue(dt2==7.68);

     }


}
