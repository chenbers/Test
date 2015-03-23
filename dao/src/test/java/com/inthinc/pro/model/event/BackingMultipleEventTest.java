package com.inthinc.pro.model.event;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.model.BackingEventType;

import static junit.framework.Assert.assertEquals;

public class BackingMultipleEventTest {
    private Map<Object, Object> attrMapFromList(String attribs) {
        Map<Object, Object> attrMap = new HashMap<Object, Object>();
        
        String[] attribsList = attribs.split(";");
        for (String s : attribsList) {
            if (!s.trim().equals("")) {
                attrMap.put(s.split("=")[0], s.split("=")[1]);
            }
        }
        return attrMap;
    }
   
    @Test
    public void getEventType_backingAttrib_returnBacking() {
        BackingMultipleEvent backingMultipleEventBacking = new BackingMultipleEvent();
        backingMultipleEventBacking.setAttribs("80=1");
        assertEquals(EventType.BACKING,backingMultipleEventBacking.getEventType());
    }
    
    @Test
    public void getEventType_fmfAttrib_returnFMF(){
        BackingMultipleEvent backingMultipleEventFirstMoveForward = new BackingMultipleEvent();
        backingMultipleEventFirstMoveForward.setAttribs("80=2");
        assertEquals(EventType.FIRST_MOVE_FORWARD,backingMultipleEventFirstMoveForward.getEventType());
    }
    
    @Test
    public void getEventType_unknownAttrib_returnBacking(){
        BackingMultipleEvent backingMultipleEventUnknown = new BackingMultipleEvent();
        backingMultipleEventUnknown.setAttribs("80=3");
        assertEquals(EventType.BACKING,backingMultipleEventUnknown.getEventType());
    }
    
    @Test
    public void getEventType_noAttrib_returnBacking(){
        BackingMultipleEvent backingMultipleEventUnknown = new BackingMultipleEvent();
        assertEquals(EventType.BACKING,backingMultipleEventUnknown.getEventType());
    }
    
    @Test
    public void getEventType_backingAttrMap_returnBacking(){
        BackingMultipleEvent backingMultipleEventBackingWithAttrMap = new BackingMultipleEvent();

        backingMultipleEventBackingWithAttrMap.setAttrMap(attrMapFromList("80=1"));
        assertEquals(EventType.BACKING,backingMultipleEventBackingWithAttrMap.getEventType());
    }
    
    @Test
    public void getEventType_fmfAttrMap_returnFMF(){
        BackingMultipleEvent backingMultipleEventBackingWithAttrMap = new BackingMultipleEvent();
        backingMultipleEventBackingWithAttrMap.setAttrMap(attrMapFromList("80=2"));
        assertEquals(EventType.FIRST_MOVE_FORWARD,backingMultipleEventBackingWithAttrMap.getEventType());
    }
    
    @Test
    public void getEventType_unknownAttrMap_returnBacking(){
        BackingMultipleEvent backingMultipleEventBackingWithAttrMap = new BackingMultipleEvent();
        backingMultipleEventBackingWithAttrMap.setAttrMap(attrMapFromList("80=3"));
        assertEquals(EventType.BACKING,backingMultipleEventBackingWithAttrMap.getEventType());
    }
    
    @Test
    public void getEventType_noAttrMap_returnBacking(){
        BackingMultipleEvent backingMultipleEventBackingWithAttrMap = new BackingMultipleEvent();
        backingMultipleEventBackingWithAttrMap.setAttrMap(attrMapFromList(""));
        assertEquals(EventType.BACKING,backingMultipleEventBackingWithAttrMap.getEventType());
    }
}
