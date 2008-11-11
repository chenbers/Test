package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;

public class EventHessianDAOTest
{
    private static final Integer BOGUS_GROUP_ID = 1;
    
    EventHessianDAO eventHessianDAO;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        eventHessianDAO = new EventHessianDAO();
        eventHessianDAO.setSiloServiceCreator(new SiloServiceCreator());

    }

    @Test
    public void recentEvents() throws Exception
    {
        List<Event> eventList = eventHessianDAO.getMostRecentEvents(MockData.TOP_GROUP_ID, 5);
        
        assertNotNull(eventList);
        assertEquals(5, eventList.size());
        
        List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(EventCategory.VIOLATION);
        // make sure they are in decending order by date
        for (int i = 0 ; i < 4; i++)
        {
            assertTrue("Time Compare failed for event " + i  + " " + eventList.get(i).getTime() + " and " + (i+1) + " " + eventList.get(i+1).getTime(), 
                    eventList.get(i).getTime().compareTo(eventList.get(i+1).getTime()) >= 0);
            
        }

        for (int i = 0 ; i < 5; i++)
        {
            assertTrue("Event type is not valid", validEventTypes.contains(eventList.get(i).getType()));
        }

        eventList = eventHessianDAO.getMostRecentEvents(MockData.EMPTY_GROUP_ID, 5);
        assertEquals(0, eventList.size());
    }

    @Test
    public void recentWarnings() throws Exception
    {
        List<Event> eventList = eventHessianDAO.getMostRecentWarnings(MockData.TOP_GROUP_ID, 5);
        
        assertNotNull(eventList);
        assertEquals(5, eventList.size());

        List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(EventCategory.WARNING);
        
        // make sure they are in decending order by date
        for (int i = 0 ; i < 4; i++)
        {
            assertTrue("Time Compare failed for event " + i  + " " + eventList.get(i).getTime() + " and " + (i+1) + " " + eventList.get(i+1).getTime(), 
                    eventList.get(i).getTime().compareTo(eventList.get(i+1).getTime()) >= 0);
        }
        for (int i = 0 ; i < 5; i++)
        {
            assertTrue("Event type is not valid", validEventTypes.contains(eventList.get(i).getType()));
        }

        eventList = eventHessianDAO.getMostRecentWarnings(MockData.EMPTY_GROUP_ID, 5);
        assertEquals(0, eventList.size());

    }



}
