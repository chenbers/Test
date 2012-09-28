package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Vehicle;

public class VehicleHessianDAOTest
{
    VehicleHessianDAO vehicleHessianDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void hierarchy() throws Exception
    {
//        final List<Vehicle> vehicleList = vehicleHessianDAO.getVehiclesByAcctID(MockData.NUM_ACCOUNTS);
//        assertTrue("No vehicles were found", vehicleList.size() > 0);

        final List<Vehicle> groupVehicles = vehicleHessianDAO.getVehiclesInGroupHierarchy(MockData.TOP_GROUP_ID);
        assertTrue("No group vehicles were found", groupVehicles.size() > 0);
//        assertTrue("Too many group vehicles were found", groupVehicles.size() < vehicleList.size());

        /**
         * <pre>
         *         setVehicleDriver(Integer, Integer)
         *         setVehicleDevice(Integer, Integer)
         * </pre>
         */
    }

    @Test
    public void assign() throws Exception
    {
//TODO:        
//        final List<Vehicle> vehicleList = vehicleHessianDAO.getVehiclesByAcctID(MockData.NUM_ACCOUNTS);
//        assertTrue("No vehicles were found", vehicleList.size() > 0);
//
//        // TODO: once the return values are nailed down, test for 'em
//        vehicleHessianDAO.setVehicleDriver(vehicleList.get(0).getVehicleID(), 1);
//        vehicleHessianDAO.setVehicleDevice(vehicleList.get(0).getVehicleID(), 5);
//
//        final Vehicle vehicle = vehicleHessianDAO.findByID(vehicleList.get(0).getVehicleID());
//        assertNotNull(vehicle);
//        assertEquals(vehicle.getDriverID(), new Integer(1));
//        assertEquals(vehicle.getDeviceID(), new Integer(5));
    }
}
