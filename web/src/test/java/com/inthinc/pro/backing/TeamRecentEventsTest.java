package com.inthinc.pro.backing;


import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

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
        List<NoteType> validEventTypes = EventCategory.VIOLATION.getNoteTypesInCategory();

        for (EventDisplay eventDisplay : eventList)
        {
            Event event = eventDisplay.getEvent();
            Driver driver = event.getDriver();
            
            assertEquals(eventDisplay.getDriverName(), driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
            
            assertTrue(validEventTypes.contains(eventDisplay.getEvent().getType()));

        }

        Event event = new AggressiveDrivingEvent();
        event.setType(NoteType.NOTEEVENT);
        event.setTime(new Date());
        EventDisplay eventDisplay = new EventDisplay(event);
        teamRecentEventsBean.setSelectedEvent(eventDisplay);
        assertEquals("go_reportDriverStyle", teamRecentEventsBean.detailsAction());
        
        event = new SeatBeltEvent();
        event.setType(NoteType.SEATBELT);
        event.setTime(new Date());
        eventDisplay = new EventDisplay(event);
        teamRecentEventsBean.setSelectedEvent(eventDisplay);
        assertEquals("go_reportDriverSeatBelt", teamRecentEventsBean.detailsAction());
        
        event = new SpeedingEvent();
        event.setType(NoteType.SPEEDING_EX3);
        event.setTime(new Date());
        eventDisplay = new EventDisplay(event);
        teamRecentEventsBean.setSelectedEvent(eventDisplay);
        assertEquals("go_reportDriverSpeed", teamRecentEventsBean.detailsAction());
        
        teamRecentEventsBean.setSelectedEvent(null);
        assertEquals("", teamRecentEventsBean.detailsAction());
    }
}
