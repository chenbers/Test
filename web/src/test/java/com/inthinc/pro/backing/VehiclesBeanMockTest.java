package com.inthinc.pro.backing;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.Vehicle;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.easymock.classextension.EasyMock.createMock;

public class VehiclesBeanMockTest {

    @Test
    public void testOdometer() throws NoSuchMethodException {
        // create bean, set dao
        VehiclesBean vehiclesBean = new VehiclesBean();

        GroupDAO mockGroupDAO = createMock(GroupDAO.class);
        DriverJDBCDAO mockDriverJDBCDAO = createMock(DriverJDBCDAO.class);
        vehiclesBean.setGroupDAO(mockGroupDAO);
        vehiclesBean.setDriverJDBCDAO(mockDriverJDBCDAO);

        // create items
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVehicleID(1);
        vehicle1.setOdometer(1000);
        vehicle1.setMaxVehicleEndingOdometer(500);
        VehicleView item1 = vehiclesBean.createVehicleView(vehicle1);
        item1.setSelected(true);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setVehicleID(2);
        vehicle2.setOdometer(1000);
        VehicleView item2 = vehiclesBean.createVehicleView(vehicle2);
        item2.setSelected(true);

        Vehicle vehicle3 = new Vehicle();
        vehicle3.setVehicleID(3);
        vehicle3.setMaxVehicleEndingOdometer(500);
        VehicleView item3 = vehiclesBean.createVehicleView(vehicle3);
        item3.setSelected(true);

        Vehicle vehicle4 = new Vehicle();
        vehicle4.setVehicleID(4);
        VehicleView item4 = vehiclesBean.createVehicleView(vehicle4);
        item4.setSelected(true);


        // set each item and test
        vehiclesBean.setItems(Arrays.asList(item1));
        Assert.assertEquals(1500,vehiclesBean.getItem().getOdometerAndMaxVehicleEndingOdometer().intValue());

        vehiclesBean.refreshItems();
        vehiclesBean.setItems(Arrays.asList(item2));
        Assert.assertEquals(1000,vehiclesBean.getItem().getOdometerAndMaxVehicleEndingOdometer().intValue());

        vehiclesBean.refreshItems();
        vehiclesBean.setItems(Arrays.asList(item3));
        Assert.assertEquals(500,vehiclesBean.getItem().getOdometerAndMaxVehicleEndingOdometer().intValue());

        vehiclesBean.refreshItems();
        vehiclesBean.setItems(Arrays.asList(item4));
        Assert.assertNull(vehiclesBean.getItem().getOdometerAndMaxVehicleEndingOdometer());
    }
}
