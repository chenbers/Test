/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.service.adapters.DeviceDAOAdapter;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.DeviceDaoStub;
import com.inthinc.pro.service.test.mock.DriverDaoStub;
import com.inthinc.pro.service.test.mock.GroupDaoStub;
import com.inthinc.pro.service.test.mock.PersonDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;
import com.inthinc.pro.service.test.mock.VehicleDaoStub;

public class VehicleAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private VehicleDaoStub vehicleDaoStub;
    private VehicleDAOAdapter vehicleDaoAdapter;
    private TiwiproPrincipalStub principal;
    private GroupDAOAdapter groupDaoAdapter;
    private DeviceDAOAdapter deviceDaoAdapter;
    private DriverDAOAdapter driverDaoAdapter;
    private PersonDAOAdapter personDaoAdapter;
    private GroupDaoStub groupDaoStub;
    private DeviceDaoStub deviceDaoStub;
    private DriverDaoStub driverDaoStub;
    private PersonDaoStub personDaoStub;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Before
    public void setUpTests() throws Exception {
        vehicleDaoStub = new VehicleDaoStub();
        vehicleDaoAdapter = (VehicleDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, VehicleDAOAdapter.class);
        groupDaoAdapter = (GroupDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, GroupDAOAdapter.class);
        deviceDaoAdapter = (DeviceDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, DeviceDAOAdapter.class);
        driverDaoAdapter = (DriverDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, DriverDAOAdapter.class);
        personDaoAdapter = (PersonDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, PersonDAOAdapter.class);
        groupDaoStub = (GroupDaoStub) BeanFactoryUtils.beanOfType(applicationContext, GroupDaoStub.class);
        deviceDaoStub = (DeviceDaoStub) BeanFactoryUtils.beanOfType(applicationContext, DeviceDaoStub.class);
        driverDaoStub = (DriverDaoStub) BeanFactoryUtils.beanOfType(applicationContext, DriverDaoStub.class);
        personDaoStub = (PersonDaoStub) BeanFactoryUtils.beanOfType(applicationContext, PersonDaoStub.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        vehicleDaoAdapter.setVehicleDAO(vehicleDaoStub);
    }

    @Test
    public void testJoinoints() {
        Group group = new Group();

        groupDaoStub.setExpectedGroup(group);

        Device device = new Device();

        deviceDaoStub.setExpectedDevice(device);

        driverDaoStub.setExpectedDriver(new Driver());

        Person person = new Person();
        Address address = new Address();
        person.setAddress(address);

        personDaoStub.setExpectedPerson(person);

        groupDaoAdapter.setGroupDAO(groupDaoStub);
        deviceDaoAdapter.setDeviceDAO(deviceDaoStub);
        driverDaoAdapter.setDriverDAO(driverDaoStub);
        personDaoAdapter.setPersonDAO(personDaoStub);
        vehicleDaoAdapter.setVehicleReportDAO(new VehicleReportDAO() {

            @Override
            public List<Trend> getTrend(Integer vehicleID, Duration duration) {
                // TODO Auto-generated method stub
                return new ArrayList<Trend>();
            }

            @Override
            public Score getScore(Integer vehicleID, Duration duration) {
                // TODO Auto-generated method stub
                return new Score();
            }
        });

        Vehicle vehicle = new Vehicle();
        group.setAccountID(10);
        device.setAccountID(10);
        person.setAcctID(10);
        address.setAccountID(10);
        principal.setAccountID(10);

        // Manual test of the aspects. These calls should all route through the DriverAuthorizationAdvice.
        vehicleDaoAdapter.assignDevice(1, 1);
        vehicleDaoAdapter.assignDriver(1, 1);
        vehicleDaoAdapter.create(vehicle);
        vehicleDaoAdapter.delete(1);
        vehicleDaoAdapter.findByID(1);
        vehicleDaoAdapter.findByVIN("");
        vehicleDaoAdapter.getLastLocation(1);
        vehicleDaoAdapter.getScore(1, null);
        vehicleDaoAdapter.getTrend(1, null);
        vehicleDaoAdapter.getTrips(1, null, null);
        vehicleDaoAdapter.update(vehicle);
    }
}
