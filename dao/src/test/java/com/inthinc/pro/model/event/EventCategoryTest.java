package com.inthinc.pro.model.event;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


public class EventCategoryTest {

    Integer[] expectedCounts = {
            10,
            16,
            0,
            11,
            2,
            2
    };
    
    @Test
    public void noteTypes(){
        int i = 0;
        for (EventCategory cat : EventCategory.values()) {
            List<NoteType> noteTypeList = cat.getNoteTypesInCategory();
            assertEquals(cat + "", expectedCounts[i++].intValue(), noteTypeList.size());
        }
    }
}
