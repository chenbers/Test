package com.inthinc.pro.backing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Vehicle;

public class TeamWarningsBeanTest extends BaseBeanTest
{
    @Test
    public void bean()
    {
        // team level login
        loginUser("custom114");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TeamWarningsBean teamWarningsBean = (TeamWarningsBean)applicationContext.getBean("teamWarningsBean");
        assertNotNull(teamWarningsBean.getEventDAO());
        assertNotNull(teamWarningsBean.getNavigation());
        assertEquals(EventCategory.WARNING, teamWarningsBean.getCategory());
        
        
        List<EventDisplay> eventList = teamWarningsBean.getWarnings();
        
        assertTrue(eventList.size() <= 5);
        
        List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(EventCategory.WARNING);
        for (EventDisplay eventDisplay : eventList)
        {
            Event event = eventDisplay.getEvent();
            assertNotNull(event);
            
            Driver driver = event.getDriver();
            
            assertEquals(eventDisplay.getDriverName(), driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
            
            Vehicle vehicle = event.getVehicle();
            assertEquals(eventDisplay.getVehicleName(), vehicle.getName());
            
            assertTrue(validEventTypes.contains(eventDisplay.getEvent().getType()));
        }
        
        
    }

}
