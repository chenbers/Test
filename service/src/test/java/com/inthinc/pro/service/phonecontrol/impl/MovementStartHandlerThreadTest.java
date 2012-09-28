package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.fail;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class MovementStartHandlerThreadTest {

    @Test
    public void testDisablePhone(final MovementEventHandler movementEventHandler) {
        final int driverId = 777;

        MovementStartHandlerThread handlerSUT = new MovementStartHandlerThread(movementEventHandler, driverId);
        handlerSUT.run();

        // Verification
        new Verifications() {
            {
                movementEventHandler.handleDriverStartedMoving(driverId);
                times = 1;
            }
        };
    }
//    @Ignore
    @Test
    public void doesNotPropagateExceptions(final MovementEventHandler movementEventHandler) {
        final int driverId = 777;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                movementEventHandler.handleDriverStartedMoving(driverId);
                result = new RuntimeException("Dummy exception");
            }
        };

        MovementStartHandlerThread handlerSUT = new MovementStartHandlerThread(movementEventHandler, driverId);
        try {
            handlerSUT.run();
        } catch (Exception e) {
            fail("No exception was expected but got: " + e);
        }
    }
}
