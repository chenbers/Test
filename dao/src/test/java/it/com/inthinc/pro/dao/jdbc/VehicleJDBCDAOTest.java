package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.VehicleJDBCDAO;
import com.inthinc.pro.model.*;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import javax.swing.text.DateFormatter;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

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
    private static final String ALERT_TEST_XML = "AlertTest.xml";
    private static ITData itData = new ITData();

//    static int VEHICLE_ID = 483;
//    static int DRIVER_ID = 492;
//    static int GROUP_ID = 101;
//    static String VIN = "JTDJT923785171463";
//
//    String str = "2014-01-22";
//
//    Date startDate = new Date(2014-01-22);
//    Date endDate = new Date (2014-01-22);

//    @BeforeClass
//    public static void setupOnce() {
//        vehicleJDBCDAO = new VehicleJDBCDAO();
//        vehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
//    }
@Before
public void setUpBeforeTest() throws Exception {
    IntegrationConfig config = new IntegrationConfig();
    String host = config.get(IntegrationConfig.SILO_HOST).toString();
    Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
    siloService = new SiloServiceCreator(host, port).getService();

    itData = new ITData();

    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ALERT_TEST_XML);

    if (itData.parseTestData(stream, siloService, false, false)) {
        throw new Exception("Error parsing Test data xml file");
    }

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
        List<Vehicle> vehicleListByAccount = vehicleDAO.getVehiclesInGroup(groupID);

            assertTrue(vehicleListByAccount.size() > 0);

        }
    }

//    @Test
//    public void getMilesDrivenTest() throws Exception {
//        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        vehicleDAO.setDataSource(dataSource);
//
//        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
//            GroupData team = itData.teamGroupData.get(teamIdx);
//            team.vehicle.getVehicleID();
//            Integer vehicle = vehicleDAO.getMilesDriven(team.vehicle.getVehicleID());
//
//        }
//
////              assertTrue(!vehicle.equals("0"));
//
//
//    }



    @Test
    public void getVehicleNamesTest() throws Exception {
        VehicleJDBCDAO vehicleDAO = new VehicleJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        vehicleDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Integer groupID = team.group.getGroupID();
            List<VehicleName> vehicleNames = vehicleDAO.getVehicleNames(groupID);

            assertTrue(vehicleNames.size() > 0);

        }

    }
//
//    @Test
//    public void findByIDTest() throws Exception {
//        Vehicle findByID = vehicleJDBCDAO.findByID(VEHICLE_ID);
//
//        assertNotNull(findByID);
//
//    }
//
//    @Test
//    public void findByDriverInGroupTest() throws Exception {
//        Vehicle findByDriverInGroup = vehicleJDBCDAO.findByDriverInGroup(DRIVER_ID, GROUP_ID);
//
//        assertNotNull(findByDriverInGroup);
//    }
//
//    @Test
//    public void findByVINTest() throws Exception {
//        Vehicle findByVIN = vehicleJDBCDAO.findByVIN(VIN);
//        assertNotNull(findByVIN);
//    }
//        @Test
//    public void findByDriverID() throws Exception {
//        Vehicle findByDriverID = vehicleJDBCDAO.findByDriverID(DRIVER_ID);
//        assertNotNull(findByDriverID);
//    }

//    @Test
//    public void getVehiclesNearLocTest() throws Exception {
//        List<DriverLocation> getTripsList = vehicleJDBCDAO.getVehiclesNearLoc(5276, 10, 32.960300, -117.210678);
//
//        assertNotNull(getTripsList);
//
//    }

}
