package com.inthinc.pro.service.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.inthinc.pro.service.model.EventGetter.DriverEventGetter;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;

public class DriverEventGetterTest {
    
    Logger logger = Logger.getLogger(DriverEventGetterTest.class);
    
    @Mocked EventDAO mockEventDAO;
    
    @Test
    public void testGetEvents() {
        
        final Integer entityID = 1;
        final List<NoteType> noteTypes = new ArrayList<NoteType>();
        noteTypes.add(NoteType.IDLE);
        final Date start = new Date();
        final Date end = new Date();
        Integer firstRow = 0;
        Integer pageCount = 10;
        final List<Event> eventList = new ArrayList<Event>();
        for(Integer i = 0; i < 15; i++){
            eventList.add(new Event());
        }
        
        new Expectations(){{
            mockEventDAO.getEventsForDriver(entityID, start, end, noteTypes, 1); returns(eventList);
        }};
        
        EventGetter eventGetter = new EventGetter();
        eventGetter.setEventDAO(mockEventDAO);
        DriverEventGetter driverEventGetter = eventGetter.new DriverEventGetter();
        List<Event> events = driverEventGetter.getEvents(entityID, noteTypes, start, end, firstRow, pageCount);
        List<Event> paginatedEventList = eventList.subList(firstRow, Math.min(firstRow + pageCount, eventList.size()));
        
        assertEquals(events, paginatedEventList);
        
    }
    
    @Test
    public void testGetEventCount() {
        
        final Integer entityID = 1;
        final List<NoteType> noteTypes = new ArrayList<NoteType>();
        noteTypes.add(NoteType.IDLE);
        final Date start = new Date();
        final Date end = new Date();
        final List<Event> eventList = new ArrayList<Event>();
        for(Integer i = 0; i < 15; i++){
            eventList.add(new Event());
        }
        
        new Expectations(){{
            mockEventDAO.getEventsForDriver(entityID, start, end, noteTypes, 1); returns(eventList);
        }};
        
        EventGetter eventGetter = new EventGetter();
        eventGetter.setEventDAO(mockEventDAO);
        DriverEventGetter driverEventGetter = eventGetter.new DriverEventGetter();
        Integer count = driverEventGetter.getEventCount(entityID, noteTypes, start, end);
        
        assertTrue(count == eventList.size());
    }
    
    @Test
    public void testGetEventCountWithPreviousEvents() {
        
        final Integer entityID = 1;
        final List<NoteType> noteTypes = new ArrayList<NoteType>();
        noteTypes.add(NoteType.IDLE);
        final Date start = new Date();
        final Date end = new Date();
        Integer firstRow = 0;
        Integer pageCount = 10;
        final List<Event> eventList = new ArrayList<Event>();
        for(Integer i = 0; i < 15; i++){
            eventList.add(new Event());
        }
        final List<Event> eventList2 = new ArrayList<Event>();
        for(Integer i = 0; i < 10; i++){
            eventList2.add(new Event());
        }
        
        new Expectations(){{
            
            // First call happens in getEvents()
            mockEventDAO.getEventsForDriver(entityID, start, end, noteTypes, 1); returns(eventList);
            
            // Second call SHOULD happen in getEventCount()
            mockEventDAO.getEventsForDriver(entityID, start, end, noteTypes, 1); returns(eventList2);
        }};
        
        EventGetter eventGetter = new EventGetter();
        eventGetter.setEventDAO(mockEventDAO);
        DriverEventGetter driverEventGetter = eventGetter.new DriverEventGetter();
        
        // Run getEvents first so we have an extant set of events
        driverEventGetter.getEvents(entityID, noteTypes, start, end, firstRow, pageCount);
        
        Integer count = driverEventGetter.getEventCount(entityID, noteTypes, start, end);
        
        assertTrue(count == eventList2.size());
    }
    
}
