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
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;

public class PhoneControlMovementEventHandlerTest {

    private final static String EXPECTED_CELL_PHONE_NUMBER = "5145555555";

    @Test
    public void testHandlesDriverStartMovingEvent(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(expectedCellProvider);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter(expectedCellProvider);
                times = 1;

                phoneControlAdapterMock.disablePhone(EXPECTED_CELL_PHONE_NUMBER);
                times = 1;
            }
        };
    }

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testHandlesDriverStopsMovingEvent(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(expectedCellProvider);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter(expectedCellProvider);
                times = 1;

                phoneControlAdapterMock.enablePhone(EXPECTED_CELL_PHONE_NUMBER);
                times = 1;
            }
        };
    }

    @Test
    public void testPropagatesExceptionsFromTiwiproBackend(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);
        try {
            handler.handleDriverStartedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }
        try {
            handler.handleDriverStoppedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }
    }

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testPropagatesExceptionsFromPhoneControlEndpoint(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");
        
        final Driver driver = new Driver();
        driver.setProvider(CellProviderType.CELL_CONTROL);
        driver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = driver;

                adapterFactoryMock.createAdapter((CellProviderType) any);
                result = phoneControlAdapterMock;

                phoneControlAdapterMock.disablePhone(anyString);
                result = expectedException;

                phoneControlAdapterMock.enablePhone(anyString);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);

        try {
            handler.handleDriverStartedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }

        try {
            handler.handleDriverStoppedMoving(1);
            fail("Exception should have been raised but got none");
        } catch (Throwable e) {
            // Verification
            assertSame(expectedException, e);
        }
    }

    @Test
    public void testDoesNotDisablePhoneIfProviderInformationIsNotAvailable(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock,
            final PhoneControlAdapter phoneControlAdapterMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter((CellProviderType) any);
                times = 0;

                phoneControlAdapterMock.disablePhone((String) any);
                times = 0;
            }
        };
    }

    @Test
    public void testDoesNotEnablePhoneIfProviderInformationIsNotAvailable(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock,
            final PhoneControlAdapter phoneControlAdapterMock) {
        
        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        
        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;
                
                adapterFactoryMock.createAdapter((CellProviderType) any);
                result = phoneControlAdapterMock;
            }
        };
        
        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock);
        handler.handleDriverStartedMoving(expectedDriverId);
        
        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;
                
                adapterFactoryMock.createAdapter((CellProviderType) any);
                times = 0;
                
                phoneControlAdapterMock.enablePhone((String) any);
                times = 0;
            }
        };
    }
}
