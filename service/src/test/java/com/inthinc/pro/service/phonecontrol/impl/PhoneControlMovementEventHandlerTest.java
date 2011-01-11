package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;

import java.util.HashMap;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

public class PhoneControlMovementEventHandlerTest {

    private final static String EXPECTED_CELL_PHONE_NUMBER = "5145555555";

    @Test
    public void testHandlesDriverStartMovingEvent(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(expectedCellProvider);
        expectedDriver.getCellProviderInfo().setProviderUsername("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                times = 1;

                phoneControlAdapterMock.disablePhone(EXPECTED_CELL_PHONE_NUMBER);
                times = 1;
            }
        };
    }

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testHandlesDriverStopsMovingEvent(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(expectedCellProvider);
        expectedDriver.getCellProviderInfo().setProviderUsername("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                times = 1;

                phoneControlAdapterMock.enablePhone(EXPECTED_CELL_PHONE_NUMBER);
                times = 1;
            }
        };
    }

    @Test
    public void testPropagatesExceptionsFromTiwiproBackend(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final DriverPhoneDAO phoneDaoMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
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
    public void testPropagatesExceptionsFromPhoneControlEndpoint(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        final Driver driver = new Driver();
        driver.getCellProviderInfo().setProvider(CellProviderType.CELL_CONTROL);
        driver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        driver.getCellProviderInfo().setProviderUsername("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(anyInt);
                result = driver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;

                phoneControlAdapterMock.disablePhone(anyString);
                result = expectedException;

                phoneControlAdapterMock.enablePhone(anyString);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);

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
            final PhoneControlAdapter phoneControlAdapterMock, final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.disablePhone((String) any);
                times = 0;
            }
        };
    }

    @Test
    public void testDoesNotEnablePhoneIfProviderInformationIsNotAvailable(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock,
            final PhoneControlAdapter phoneControlAdapterMock, final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.enablePhone((String) any);
                times = 0;
            }
        };
    }

    @Test
    public void testHandlesEmptyUsername(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(CellProviderType.CELL_CONTROL);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);

        handler.handleDriverStartedMoving(expectedDriverId);
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(expectedDriverId);
                times = 2;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.enablePhone((String) any);
                times = 0;
            }
        };
    }

    @SuppressWarnings("serial")
    @Test
    public void testDoesNotUpdatePhoneStatusOnAsynchonousCalls(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.getCellProviderInfo().setProviderUsername("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.ASYNCHRONOUS);
            }
        });

        assertNull(expectedDriver.getCellProviderInfo().getCellStatus());
        handler.handleDriverStartedMoving(expectedDriverId);
        assertNull(expectedDriver.getCellProviderInfo().getCellStatus());
        handler.handleDriverStoppedMoving(expectedDriverId);
        assertNull(expectedDriver.getCellProviderInfo().getCellStatus());

        // Verification
        new Verifications() {
            {
                driverDaoMock.update(expectedDriver);
                times = 0;
            }
        };
    }

    @SuppressWarnings("serial")
    @Test
    public void testDisablePhoneStatusOnSynchonousCalls(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {

        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.getCellProviderInfo().setProviderUsername("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.SYNCHRONOUS);
            }
        });

        assertNull(expectedDriver.getCellProviderInfo().getCellStatus());
        handler.handleDriverStartedMoving(expectedDriverId);
        assertEquals(CellStatusType.DISABLED, expectedDriver.getCellProviderInfo().getCellStatus());

        // Verification
        new Verifications() {
            {
                driverDaoMock.update(expectedDriver);
                times = 1;

                phoneDaoMock.addDriverToDisabledPhoneList(expectedDriver.getDriverID());
                times = 1;
            }
        };
    }

    @SuppressWarnings("serial")
    @Test
    public void testEnablePhoneStatusOnSynchonousCalls(final DriverDAO driverDaoMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final DriverPhoneDAO phoneDaoMock) {
        
        final Driver expectedDriver = new Driver();
        final Integer expectedDriverId = 666;
        
        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.getCellProviderInfo().setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.getCellProviderInfo().setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.getCellProviderInfo().setProviderUsername("");
        
        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(expectedDriverId);
                result = expectedDriver;
                
                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };
        
        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock, adapterFactoryMock, phoneDaoMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.SYNCHRONOUS);
            }
        });
        
        assertNull(expectedDriver.getCellProviderInfo().getCellStatus());
        handler.handleDriverStoppedMoving(expectedDriverId);
        assertEquals(CellStatusType.ENABLED, expectedDriver.getCellProviderInfo().getCellStatus());
        
        // Verification
        new Verifications() {
            {
                driverDaoMock.update(expectedDriver);
                times = 1;
                
                phoneDaoMock.removeDriverFromDisabledPhoneList(expectedDriver.getDriverID());
                times = 1;
            }
        };
    }
}
