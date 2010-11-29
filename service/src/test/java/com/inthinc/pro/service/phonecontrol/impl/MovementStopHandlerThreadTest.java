package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.fail;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class MovementStopHandlerThreadTest {

    @Test
    public void testDisablePhone(final MovementEventHandler movementEventHandler) {
        final int driverId = 777;

        MovementStopHandlerThread handlerSUT = new MovementStopHandlerThread(movementEventHandler, driverId);
        handlerSUT.run();

        // Verification
        new Verifications() {
            {
                movementEventHandler.handleDriverStoppedMoving(driverId);
                times = 1;
            }
        };
    }

    @Test
    public void doesNotPropagateExceptions(final MovementEventHandler movementEventHandler) {
        final int driverId = 777;

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                movementEventHandler.handleDriverStoppedMoving(driverId);
                result = new RuntimeException("Dummy exception");
            }
        };

        MovementStopHandlerThread handlerSUT = new MovementStopHandlerThread(movementEventHandler, driverId);
        
        try {
            handlerSUT.run();
        } catch (Exception e) {
            fail("No exception was expected but got: " + e);
        }
    }
}
