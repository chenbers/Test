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

public class EventHessianDAOTest
{

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
        eventHessianDAO.setServiceCreator(new CentralServiceCreator());
        eventHessianDAO.setSiloServiceCreator(new SiloServiceCreator());

    }

    @Test
    public void recentEvents() throws Exception
    {
        List<Event> eventList = eventHessianDAO.getMostRecentEvents(MockData.TOP_GROUP_ID, 5);
        
        assertNotNull(eventList);
        assertEquals(5, eventList.size());
        
        // make sure they are in decending order by date
        for (int i = 0 ; i < 4; i++)
        {
            assertTrue("Time Compare failed for event " + i  + " " + eventList.get(i).getTime() + " and " + (i+1) + " " + eventList.get(i+1).getTime(), 
                    eventList.get(i).getTime().compareTo(eventList.get(i+1).getTime()) >= 0);
        }
    }

}
