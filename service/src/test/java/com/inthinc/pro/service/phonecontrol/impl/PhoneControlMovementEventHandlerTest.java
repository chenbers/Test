package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlServiceFactory;
import com.inthinc.pro.service.phonecontrol.client.PhoneControlService;

public class PhoneControlMovementEventHandlerTest {

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testHandlesDriverStartMovingEvent(final DriverDAO driverDaoMock, final PhoneControlServiceFactory serviceFactory, final PhoneControlService phoneControlService) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        final String expectedCellPhoneNumber = "5145555555";
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.setCellPhone(expectedCellPhoneNumber);
        expectedDriver.setProvider(expectedCellProvider);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                serviceFactory.createServiceEndpoint(expectedCellProvider);
                result = phoneControlService;

                phoneControlService.disablePhone(expectedCellPhoneNumber);
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, serviceFactory);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                serviceFactory.createServiceEndpoint(expectedCellProvider);
                times = 1;

                phoneControlService.disablePhone(expectedCellPhoneNumber);
                times = 1;
            }
        };
    }

    @Test
    public void testPropagatesExceptionsFromTiwiproBackend(final DriverDAO driverDaoMock, final PhoneControlServiceFactory serviceFactory) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, serviceFactory);
        try {
            handler.handleDriverStartedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }
    }

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testPropagatesExceptionsFromPhoneControlEndpoint(final DriverDAO driverDaoMock, final PhoneControlServiceFactory serviceFactory, final PhoneControlService phoneControlService) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = new Driver();

                serviceFactory.createServiceEndpoint((CellProviderType) any);
                result = phoneControlService;

                phoneControlService.disablePhone(anyString);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, serviceFactory);

        try {
            handler.handleDriverStartedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }
    }
}
