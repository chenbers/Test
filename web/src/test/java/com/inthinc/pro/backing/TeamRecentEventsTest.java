package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;

public class TeamRecentEventsTest extends BaseBeanTest
{
    @Test
    public void bean()
    {
        // team level login
        loginUser("custom114");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TeamRecentEventsBean teamRecentEventsBean = (TeamRecentEventsBean)applicationContext.getBean("teamRecentEventsBean");
        assertNotNull(teamRecentEventsBean.getEventDAO());
        assertNotNull(teamRecentEventsBean.getNavigation());
        
        
        List<EventDisplay> eventList = teamRecentEventsBean.getRecentEvents();
        
        assertTrue(eventList.size() <= 5);
        
        for (EventDisplay eventDisplay : eventList)
        {
            Event event = eventDisplay.getEvent();
            Driver driver = event.getDriver();
            
            assertEquals(eventDisplay.getDriverName(), driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
        }
        
        
    }
}
