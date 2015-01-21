package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Test for {@link com.inthinc.pro.dao.jdbc.ConfiguratorJDBCDAO}.
 */
public class ConfiguratorJDBCDAOTest {
    static ConfiguratorJDBCDAO configuratorJDBCDAO;
    static VehicleHessianDAO vehicleHessianDAO;

    static Vehicle vehicle1;
    static Vehicle vehicle2;

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(new com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator(host, port).getService());
        configuratorJDBCDAO = new ConfiguratorJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        configuratorJDBCDAO.setDataSource(dataSource);
        createTestDeviceVehicles();
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            deleteTestVehicles();
        } catch (Throwable t) {/*ignore*/}
    }

    /**
     * Tests that count and list work.
     */
    @Test
    public void testGetAllSettings() {
        Map<Integer, VehicleSetting> vehicleSettingMap = configuratorJDBCDAO.getVehicleSettingsForAll(Arrays.asList(vehicle1.getVehicleID(), vehicle2.getVehicleID()));
        assertNotNull(vehicleSettingMap);
        assertEquals(2, vehicleSettingMap.size());
    }

    private static void createTestDeviceVehicles() {
        vehicle1 = new Vehicle();
        vehicle2 = new Vehicle();

        vehicle1.setGroupID(1);
        vehicle1.setVtype(VehicleType.LIGHT);

        vehicle2.setGroupID(1);
        vehicle2.setVtype(VehicleType.LIGHT);

        vehicle1.setVehicleID(vehicleHessianDAO.create(1, vehicle1));
        vehicle2.setVehicleID(vehicleHessianDAO.create(1, vehicle2));
    }

    private static void deleteTestVehicles() {
        vehicleHessianDAO.deleteByID(vehicle1.getVehicleID());
        vehicleHessianDAO.deleteByID(vehicle2.getVehicleID());
    }
}

