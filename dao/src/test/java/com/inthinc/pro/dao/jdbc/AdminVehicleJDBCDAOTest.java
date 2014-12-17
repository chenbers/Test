package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdminVehicleJDBCDAOTest {
    private static final String XML_DATA_FILE = "IntegrationTest.xml";

    private static AdminVehicleJDBCDAO adminVehicleJDBCDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static IntegrationConfig config;
    private static String mapServerURL;
    private static String host;
    private static Integer port;
    private static SiloService siloService;

    @BeforeClass
    public static void setupOnce() throws Exception {
        config = new IntegrationConfig();
        vehicleDAO = new VehicleHessianDAO();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        vehicleDAO.setSiloService(new com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator(host, port).getService());
        adminVehicleJDBCDAO = new AdminVehicleJDBCDAO();
        adminVehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }

    @Test
    public void odometerTest() {
        Vehicle v = new Vehicle();
        v.setGroupID(1);
        v.setStatus(Status.ACTIVE);
        v.setModified(new Date());
        v.setVtype(VehicleType.LIGHT);
        v.setOdometer(100);
        v.setName("test1234567");

        Integer id = vehicleDAO.create(1, v);
        PageParams pageParams = new PageParams();
        pageParams.setStartRow(0);
        pageParams.setEndRow(1000);
        TableFilterField tableFilterField = new TableFilterField("name", v.getName(), FilterOp.EQUALS);
        pageParams.setFilterList(Arrays.asList(tableFilterField));
        List<Vehicle> vehicleList = adminVehicleJDBCDAO.getVehicles(Arrays.asList(1), pageParams);

        Assert.assertNotNull(vehicleList);
        Assert.assertFalse(vehicleList.size() == 0);

        Vehicle found = null;
        for (Vehicle vehicle: vehicleList){
            if (vehicle.getVehicleID().equals(id))
                found = vehicle;
        }

        Assert.assertNotNull(found);
        Assert.assertEquals(100l, found.getOdometer().longValue());

        vehicleDAO.deleteByID(id);
    }
}
