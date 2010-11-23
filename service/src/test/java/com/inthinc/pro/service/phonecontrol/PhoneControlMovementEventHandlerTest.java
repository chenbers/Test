package com.inthinc.pro.service.phonecontrol;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.phonecontrol.impl.PhoneControlMovementEventHandler;

public class PhoneControlMovementEventHandlerTest {

    @Test
    // TODO Update this test to add call to Zoomsafer/Cellcontrol service.
    public void testHandlesDriverStartMovingEvent(final DriverDAO driverDaoMock) {

        final Driver expectedDriver = new Driver();
        final Person expectedPerson = new Person();
        final Integer driverId = 666;

        expectedPerson.setPriPhone("5145555555");
        expectedDriver.setPerson(expectedPerson);

        // Expectations
        new NonStrictExpectations() {
            {
                driverDaoMock.findByID(driverId);
                result = expectedDriver;
            }
        };

        // Execution
        MovementEventHandler handler = new PhoneControlMovementEventHandler(driverDaoMock);
        handler.handleDriverStartedMoving(driverId);

        // Verification
        new Verifications() {
            {
                driverDaoMock.findByID(driverId);
                times = 1;
            }
        };
    }
}
