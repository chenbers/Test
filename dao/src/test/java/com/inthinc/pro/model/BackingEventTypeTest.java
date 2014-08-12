package com.inthinc.pro.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class BackingEventTypeTest {
    @Test
    public void testGetCode() {
        String expectationBacking = "BACKING";
        BackingEventType testBacking = BackingEventType.getEventType(1);
        assertEquals(testBacking.toString(), expectationBacking);
        assertEquals(testBacking.getCode(), Integer.valueOf(1));

        String expectationFirstMoveForward = "FIRST_MOVE_FORWARD";
        BackingEventType testFirstMoveForward = BackingEventType.getEventType(2);
        assertEquals(testFirstMoveForward.toString(), expectationFirstMoveForward);
        assertEquals(testFirstMoveForward.getCode(), Integer.valueOf(2));
    }
}

