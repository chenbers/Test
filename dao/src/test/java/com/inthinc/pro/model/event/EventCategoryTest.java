package com.inthinc.pro.model.event;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

public class EventCategoryTest {

    Integer[] expectedNoteInCatCounts = {
            10,
            16,
            0,
            11,
            2,
            3,
            4,
            4
    };
    
    @Test
    public void noteTypes(){
        int i = 0;
        for (EventCategory cat : EventCategory.values()) {
            List<NoteType> noteTypeList = cat.getNoteTypesInCategory();
            assertEquals(cat + "", expectedNoteInCatCounts[i++].intValue(), noteTypeList.size());
        }
    }
    
    Integer[] expectedTypeInCatCounts = {
            4,  //  DRIVER
    		4,	//  EMERGENCY
    		3,	//  HOS
    		3,	//  NONE
    		1,	//  TEXT
    		9,	//  VIOLATION
    		15,	//  WARNING
    		2	//  ZONE
    };
    
    @Test
    public void getCategoryForEventType() {
    	TreeMap<String, HashMap<Integer,EventType>> map = new TreeMap<String, HashMap<Integer,EventType>>();
    	Integer count = 0;
    	for(EventType type :EventType.values()) {
    		EventCategory cat = EventCategory.getCategoryForEventType(type);

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
