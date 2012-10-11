/**
 * 
 */
package com.inthinc.pro.service.security.aspects.it;

import junit.framework.Assert;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.adapters.DeviceDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.test.mock.DeviceDaoStub;
import com.inthinc.pro.service.test.mock.TiwiproPrincipalStub;

/**
 * @author dfreitas
 * 
 */
public class DeviceAuthorizationAdviceTest {

    private static ApplicationContext applicationContext;
    private DeviceDaoStub deviceDaoStub;
    private DeviceDAOAdapter adapter;
    private TiwiproPrincipalStub principal;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/testAspects-security.xml");
    }

    @Before
    public void setUpTests() throws Exception {
        deviceDaoStub = new DeviceDaoStub();
        adapter = (DeviceDAOAdapter) BeanFactoryUtils.beanOfType(applicationContext, DeviceDAOAdapter.class);
        principal = (TiwiproPrincipalStub) BeanFactoryUtils.beanOfType(applicationContext, TiwiproPrincipal.class);
        adapter.setDeviceDAO(deviceDaoStub);
    }

    @Test
    public void testGrantsAccessToCreate() {

        // Test against same accountId
        principal.setAccountID(10);
        principal.setInthincUser(false);
        Device device = new Device();
        device.setAccountID(10); // Set account id the same as the principal.

        adapter.create(device);

        // Test against Inthinc user
        principal.setAccountID(12);
        device.setAccountID(13); // Set account id different than the principal's.
        principal.setInthincUser(true); // But flags user as Inthinc

        adapter.create(device);
    }

    @Test
    public void testDeniesAccessToCreate() {
        try {
            principal.setAccountID(12);

            Device device = new Device();
            device.setAccountID(13); // Set account id different than the principal's.
            principal.setInthincUser(false); // And user is not Inthinc

            adapter.create(device);
            Assert.fail("Should have thrown exception " + AccessDeniedException.class + ". Ids should not match. Principal account id = " + principal.getAccountID() + " resource account id = "
                    + device.getAccountID());
        } catch (AccessDeniedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsAccessToDelete() {

        try {
            adapter.delete(11);
            Assert.fail("Should have thrown exception " + NotImplementedException.class + ".");
        } catch (NotImplementedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testDeniesAccessToDelete() {
        try {
            adapter.delete(11);
            Assert.fail("Should have thrown exception " + NotImplementedException.class + ".");
        } catch (NotImplementedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testGrantsFindById() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test acceptance by same account ID.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(10); // Make address account id same as principal account id.

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            device.setAccountID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findByID(23);
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindById() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(11); // Make address account id same as principal account id.

            adapter.findByID(23);
            Assert.fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsFindByImei() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test acceptance by same account ID.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(10); // Make address account id same as principal account id.

            adapter.findByIMEI("");
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            device.setAccountID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findByIMEI("");
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindByImei() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(11); // Make address account id same as principal account id.

            adapter.findByIMEI("");
            Assert.fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsFindBySerialNum() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test acceptance by same account ID.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(10); // Make address account id same as principal account id.

            adapter.findBySerialNum("");
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }

        // Test acceptance by Inthinc user.
        try {
            principal.setAccountID(99);
            device.setAccountID(88); // Make address account id different than principal account id.
            principal.setInthincUser(true); // But flags principal as Inthinc user

            adapter.findBySerialNum("");
        } catch (AccessDeniedException e) {
            Assert.fail("Method delete should not have thrown exception " + AccessDeniedException.class);
        }
    }

    @Test
    public void testDeniesFindBySerialNum() {
        Device device = new Device();
        deviceDaoStub.setExpectedDevice(device);

        // Test rejects if different ID and not Inthinc user.
        try {
            principal.setAccountID(10);
            principal.setInthincUser(false);
            device.setAccountID(11); // Make address account id same as principal account id.

            adapter.findBySerialNum("");
            Assert.fail("Test should have thrown exception " + AccessDeniedException.class);
        } catch (AccessDeniedException e) {
            // Ok. Exception is expected.
        }
    }

    @Test
    public void testGrantsAccessToUpdate() {

        try {
            adapter.update(null);
            Assert.fail("Should have thrown exception " + NotImplementedException.class + ".");
        } catch (NotImplementedException e) {
            // Ok, exception should have been raised.
        }
    }

    @Test
    public void testDeniesAccessToUpdate() {
        try {
            adapter.update(null);
            Assert.fail("Should have thrown exception " + NotImplementedException.class + ".");
        } catch (NotImplementedException e) {
            // Ok, exception should have been raised.
        }
    }
}
