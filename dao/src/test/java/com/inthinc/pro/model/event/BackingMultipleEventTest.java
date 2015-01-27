package com.inthinc.pro.model.event;

import java.util.HashMap;
import java.util.Map;

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
        assertEquals(EventType.BACKING,backingMultipleEventUnknown.getEventType());
        
        BackingMultipleEvent backingMultipleEventBackingWithAttrMap = new BackingMultipleEvent();
        Map<Object, Object> attrMap = new HashMap<Object, Object>();
        
        String[] attribsList = "80=1".split(";");
        for (String s : attribsList) {
            if (!s.trim().equals("")) {
                attrMap.put(s.split("=")[0], s.split("=")[1]);
            }
        }
        backingMultipleEventBackingWithAttrMap.setAttrMap(attrMap);
        assertEquals(EventType.BACKING,backingMultipleEventBackingWithAttrMap.getEventType());
    }
}
