package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.jdbc.VehicleJDBCDAO;
import com.inthinc.pro.model.*;
import it.config.ITDataSource;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VehicleJDBCDAOTest extends SimpleJdbcDaoSupport {

    static VehicleJDBCDAO vehicleJDBCDAO;
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


    static int VEHICLE_ID = 483;
    static int DRIVER_ID = 492;
    static int GROUP_ID = 101;
    static String VIN = "JTDJT923785171463";

    String str = "2014-01-22";

    Date startDate = new Date(2014-01-22);
    Date endDate = new Date (2014-01-22);

    @BeforeClass
    public static void setupOnce() {
        vehicleJDBCDAO = new VehicleJDBCDAO();
        vehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }


//    @Test
//    public void getVehiclesInGroupHierarchyTest() throws Exception {
//        List<Vehicle> vehiclesList = vehicleJDBCDAO.getVehiclesInGroupHierarchy(GROUP_ID);
//        boolean found = false;
//        for (Vehicle vh : vehiclesList) {
//            if (vh.getGroupID().equals(GROUP_ID)) {
//                found = true;
//                break;
//            }
//        }
//        assertTrue(found);
//
//    }
//
//    @Test
//    public void getVehiclesInGroupTest() throws Exception {
//        List<Vehicle> vehicleListByAccount = vehicleJDBCDAO.getVehiclesInGroup(GROUP_ID);
//        assertTrue(vehicleListByAccount.size() > 0);
//        boolean found = false;
//        for (Vehicle gr : vehicleListByAccount) {
//            if (gr.getGroupID().equals(GROUP_ID)) {
//                found = true;
//                break;
//            }
//        }
//        assertTrue(found);
//    }
//
//    @Test
//    public void getMilesDrivenTest() throws Exception {
//        Integer milesdriven = vehicleJDBCDAO.getMilesDriven(VEHICLE_ID);
//
//        assertTrue(milesdriven >= 0);
//    }
//
//    @Test
//    public void getVehicleNamesTest() throws Exception {
//        List<VehicleName> vehiclesList = vehicleJDBCDAO.getVehicleNames(GROUP_ID);
//
//        assertTrue(vehiclesList.size() > 0);
//
//    }
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

    @Test
    public void getVehiclesNearLocTest() throws Exception {
        List<DriverLocation> getTripsList = vehicleJDBCDAO.getVehiclesNearLoc(5276, 10, 32.960300, -117.210678);

        assertNotNull(getTripsList);

    }

}
