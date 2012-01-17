package com.inthinc.pro.backing;


import java.util.List;

import org.junit.Test;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;

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
        
        List<NoteType> validEventTypes = EventCategory.WARNING.getNoteTypesInCategory();
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
