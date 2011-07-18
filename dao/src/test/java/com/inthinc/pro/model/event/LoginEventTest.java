package com.inthinc.pro.model.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoginEventTest {
    private LoginEvent invalidDriver = new InvalidDriverEvent();
    private LoginEvent invalidOccupant = new InvalidOccupantEvent();
    private LoginEvent validDriver = new ValidDriverEvent();
    private LoginEvent validOccupant = new ValidOccupantEvent();
    
    @Test
    public void testGetEventType_invalidDriver(){
        assertEquals(EventType.INVALID_DRIVER, invalidDriver.getEventType());
    }
    @Test
    public void testGetEventType_invalidOccupant(){
        assertEquals(EventType.INVALID_OCCUPANT, invalidOccupant.getEventType());
    }
    @Test
    public void testGetEventType_validDriver(){
        assertEquals(EventType.NEW_DRIVER, validDriver.getEventType());
    }
    @Test
    public void testGetEventType_validOccupant(){
        assertEquals(EventType.NEW_OCCUPANT, validOccupant.getEventType());
    }
}
