package com.inthinc.pro.model.event;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BackingMultipleEventTest {

    @Test
    public void testGetEventType(){
        BackingMultipleEvent backingMultipleEventBacking = new BackingMultipleEvent();
        backingMultipleEventBacking.setAttribs("80=1");
        assertEquals(EventType.BACKING,backingMultipleEventBacking.getEventType());

        BackingMultipleEvent backingMultipleEventFirstMoveForward = new BackingMultipleEvent();
        backingMultipleEventFirstMoveForward.setAttribs("80=2");
        assertEquals(EventType.FIRST_MOVE_FORWARD,backingMultipleEventFirstMoveForward.getEventType());

        BackingMultipleEvent backingMultipleEventUnknown = new BackingMultipleEvent();
        backingMultipleEventUnknown.setAttribs("80=3");
        assertEquals(EventType.UNKNOWN,backingMultipleEventUnknown.getEventType());

    }
}
