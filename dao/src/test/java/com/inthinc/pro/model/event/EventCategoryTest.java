package com.inthinc.pro.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

public class EventCategoryTest {

    Integer[] expectedNoteInCatCounts = {
            14,
            22,
            0,
            11,
            2,
            2,
            4,
            4,
            5,
            2,
            28
    };
    
    @Test
    public void noteTypes(){
        int i = 0;
        for (EventCategory cat : EventCategory.values()) {
            List<NoteType> noteTypeList = cat.getNoteTypesInCategory();
            //System.out.println("category: " + cat +" Category count index: "  + i);
            assertEquals(cat + "", expectedNoteInCatCounts[i++].intValue(), noteTypeList.size());
        }
    }
    @Test 
    public void speedingNoteTypes(){
        
        List<NoteType> noteTypes = EventType.SPEEDING.getNoteTypeList();
        assertNotNull(noteTypes);
        
    }
    @Test
    public void speedingEventType(){
        EventType eventType = NoteType.SPEEDING_EX3.getEventType();
        assertEquals(EventType.SPEEDING, eventType);
    }
    @Test
    public void dvirEventType(){
        EventType eventType = NoteType.HOS_CHANGE_STATE_NO_GPS_LOCK.getEventType();
        assertEquals(EventType.DVIR, eventType);
    }
    @Test
    public void maintenanceEventType(){
        EventType eventType = NoteType.IGNITION_OFF.getEventType();
        assertEquals(EventType.UNKNOWN_MAINTENANCE,eventType);
    }

    Integer[] expectedTypeInCatCounts = {
            4,  //  DRIVER
            5,  //  DVIR
    		4,	//  EMERGENCY
    		2,	//  HOS
            // 10, //   MAINTENANCE // all maint events are violations
            10,	//  NONE
    		1,	//  TEXT
    		23,	//  VIOLATION
    		20,	//  WARNING
    		2	//  ZONE
    };
    
    @Test
    public void getCategoryForEventType() {
    	TreeMap<String, HashMap<Integer,EventType>> map = new TreeMap<String, HashMap<Integer,EventType>>();
    	Integer count = 0;
    	for(EventType type :EventType.values()) {
    		EventCategory cat = EventCategory.getCategoryForEventType(type);
            if (cat == EventCategory.NONE) {
                System.out.println(cat + " " + type);
            }

            if (!map.containsKey(cat.toString())) {
                map.put(cat.toString(), new HashMap<Integer, EventType>());
            }
            map.get(cat.toString()).put(count, type);
            count++;
    	}
    	int i = 0;
    	for(String cat: map.keySet()){
    		System.out.println(map.get(cat).size()+" "+cat);
            assertEquals(cat, expectedTypeInCatCounts[i++].intValue(), map.get(cat).size());
    	}
    }
}
