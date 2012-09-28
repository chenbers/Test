package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.security.Roles;

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
        eventHessianDAO.setSiloService(new SiloServiceCreator().getService());

        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(new SiloServiceCreator().getService());
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

//        RoleHessianDAO roleDAO = new RoleHessianDAO();
//        roleDAO.setSiloService(new SiloServiceCreator().getService());
//
//        Roles roles = new Roles();
//        roles.setRoleDAO(roleDAO);
//        roles.init();
    }

    @Test
    public void recentEvents() throws Exception
    {
        List<Event> eventList = eventHessianDAO.getMostRecentEvents(MockData.TOP_GROUP_ID, 5);
        
        assertNotNull(eventList);
        // One speeding event will be invalid so not counted, but should be compensated for
        assertEquals(5, eventList.size());
        
        List<NoteType> validEventTypes = EventCategory.VIOLATION.getNoteTypesInCategory();
        // make sure they are in descending order by date
        for (int i = 0 ; i < 4; i++)
        {
            assertTrue("Time Compare failed for event " + i  + " " + eventList.get(i).getTime() + " and " + (i+1) + " " + eventList.get(i+1).getTime(), 
                    eventList.get(i).getTime().compareTo(eventList.get(i+1).getTime()) >= 0);
            
        }

        for (int i = 0 ; i < 5; i++)
        {
            assertTrue("Event type is not valid", validEventTypes.contains(eventList.get(i).getType()));
        }
        
        for(Event event : eventList)
        {
            Class<?> eventType = event.getType().getEventClass();
            assertTrue("The Event was not properly constructed as the " + eventType.getSimpleName() + " subclass", eventType.isAssignableFrom(event.getClass()));
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

        List<NoteType> validEventTypes = EventCategory.WARNING.getNoteTypesInCategory();
        
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
    @Test
    public void events() throws Exception
    {
        Date startTime = new Date(MockData.timeNow);
        Date endTime = new  Date();
        List<Event> eventList = eventHessianDAO.getViolationEventsForDriver(UnitTestStats.UNIT_TEST_DRIVER_ID, startTime, endTime, EventDAO.INCLUDE_FORGIVEN);
        
        assertNotNull(eventList);
        
        List<NoteType> validEventTypes = EventCategory.VIOLATION.getNoteTypesInCategory();
        // make sure they are in decending order by date
        for (Event event : eventList)
        {
            assertTrue("Event type is not valid", validEventTypes.contains(event.getType()));
        }
        
        for(Event event : eventList)
        {
            Class<?> eventType = event.getType().getEventClass();
            assertTrue("The Event was not properly constructed as the " + eventType.getSimpleName() + " subclass", eventType.isAssignableFrom(event.getClass()));
        }

    }



}
