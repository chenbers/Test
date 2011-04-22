package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;

import java.util.HashMap;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;

public class PhoneControlMovementEventHandlerTest {

    private final static String EXPECTED_CELL_PHONE_NUMBER = "5145555555";

    @Test
    public void testHandlesDriverStartMovingEvent(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(expectedCellProvider);
        expectedDriver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
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
    public void testHandlesDriverStopsMovingEvent(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;
        final CellProviderType expectedCellProvider = CellProviderType.CELL_CONTROL;

        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(expectedCellProvider);
        expectedDriver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter(expectedCellProvider, (String) any, (String) any);
                times = 1;

                phoneControlAdapterMock.enablePhone(EXPECTED_CELL_PHONE_NUMBER);
                times = 1;
            }
        };
    }

    @Test
    public void testPropagatesExceptionsFromTiwiproBackend(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneStatusController phoneStatusControllerMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(anyInt);
                result = expectedException;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
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
    public void testPropagatesExceptionsFromPhoneControlEndpoint(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final RuntimeException expectedException = new RuntimeException("Dummy exception");

        final Cellblock driver = new Cellblock();
//        driver.setCellblock(new Cellblock());
        driver.setProvider(CellProviderType.CELL_CONTROL);
        driver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        driver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(anyInt);
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
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);

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
    public void testDoesNotDisablePhoneIfProviderInformationIsNotAvailable(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock,
            final PhoneControlAdapter phoneControlAdapterMock, final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
        final Integer expectedDriverId = 666;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.disablePhone((String) any);
                times = 0;
            }
        };
    }

    @Test
    public void testDoesNotEnablePhoneIfProviderInformationIsNotAvailable(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock,
            final PhoneControlAdapter phoneControlAdapterMock, final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
        final Integer expectedDriverId = 666;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                times = 1;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.enablePhone((String) any);
                times = 0;
            }
        };
    }

    @Test
    public void testHandlesEmptyUsername(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(CellProviderType.CELL_CONTROL);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);

        handler.handleDriverStartedMoving(expectedDriverId);
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                times = 2;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                times = 0;

                phoneControlAdapterMock.enablePhone((String) any);
                times = 0;
            }
        };
    }

    @SuppressWarnings("serial")
    @Ignore
    @Test
    public void testDoesNotUpdatePhoneStatusOnAsynchonousCalls(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.ASYNCHRONOUS);
            }
        });

        assertNull(expectedDriver.getCellStatus());
        handler.handleDriverStartedMoving(expectedDriverId);
        assertNull(expectedDriver.getCellStatus());
        handler.handleDriverStoppedMoving(expectedDriverId);
        assertNull(expectedDriver.getCellStatus());

        // Verification
        new Verifications() {
            {
                phoneControlDAOMock.update(expectedDriver);
                times = 0;
            }
        };
    }

    @SuppressWarnings("serial")
    @Ignore
    @Test
    public void testDisablePhoneStatusOnSynchonousCalls(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.SYNCHRONOUS);
            }
        });

        assertNull(expectedDriver.getCellStatus());
        handler.handleDriverStartedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneStatusControllerMock.setPhoneStatusDisabled(expectedDriver);
                times = 1;
            }
        };
    }

    @SuppressWarnings("serial")
    @Ignore
    @Test
    public void testEnablePhoneStatusOnSynchonousCalls(final PhoneControlDAO phoneControlDAOMock, final PhoneControlAdapterFactory adapterFactoryMock, final PhoneControlAdapter phoneControlAdapterMock,
            final PhoneStatusController phoneStatusControllerMock) {

        final Cellblock expectedDriver = new Cellblock();
//        expectedDriver.setCellblock(new Cellblock());
        final Integer expectedDriverId = 666;

        expectedDriver.setDriverID(expectedDriverId);
        expectedDriver.setCellPhone(EXPECTED_CELL_PHONE_NUMBER);
        expectedDriver.setProvider(CellProviderType.CELL_CONTROL);
        expectedDriver.setProviderUser("");

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                phoneControlDAOMock.findByID(expectedDriverId);
                result = expectedDriver;

                adapterFactoryMock.createAdapter((CellProviderType) any, (String) any, (String) any);
                result = phoneControlAdapterMock;
            }
        };

        // Execution
        PhoneControlMovementEventHandler handler = new PhoneControlMovementEventHandler(phoneControlDAOMock, adapterFactoryMock, phoneStatusControllerMock);
        handler.setStatusUpdateStrategyMap(new HashMap<CellProviderType, UpdateStrategy>() {
            {
                put(CellProviderType.CELL_CONTROL, UpdateStrategy.SYNCHRONOUS);
            }
        });

        assertNull(expectedDriver.getCellStatus());
        handler.handleDriverStoppedMoving(expectedDriverId);

        // Verification
        new Verifications() {
            {
                phoneStatusControllerMock.setPhoneStatusEnabled(expectedDriver);
                times = 1;
            }
        };
    }
}
