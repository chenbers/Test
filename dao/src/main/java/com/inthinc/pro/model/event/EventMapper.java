package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.AggressiveDrivingEventType;
import com.inthinc.pro.model.pagination.EventCategoryFilter;

@XmlRootElement
public class EventMapper
{
    
    private static final Logger logger = Logger.getLogger(EventMapper.class);
    
    
    
	private static final Map<EventCategory, List<EventCategoryFilter>> categoryFilterMap = new HashMap<EventCategory, List<EventCategoryFilter>>();
    static {
    	List<EventCategoryFilter> eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.SEATBELT, new NoteType[] {NoteType.SEATBELT}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.SPEEDING, new NoteType[] {NoteType.SPEEDING_EX3}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_BRAKE, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_BRAKE.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_ACCEL, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_ACCEL.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_VERT, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_VERT.getCode()}));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.HARD_TURN, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_TURN.getCode()}));
    	categoryFilterMap.put(EventCategory.VIOLATION, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.TAMPERING, new NoteType[] {NoteType.UNPLUGGED, NoteType.UNPLUGGED_ASLEEP}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.LOW_BATTERY, new NoteType[] {NoteType.LOW_BATTERY}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.IDLING, new NoteType[] {NoteType.IDLE}, null));
    	categoryFilterMap.put(EventCategory.WARNING, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.CRASH, new NoteType[] {NoteType.FULLEVENT}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ROLLOVER, new NoteType[] {NoteType.ROLLOVER}, null));
    	categoryFilterMap.put(EventCategory.EMERGENCY, eventCategoryFilterList);

    	
    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ZONES_ARRIVAL, new NoteType[] {NoteType.WSZONES_ARRIVAL_EX, NoteType.ZONE_ENTER_ALERTED}, null));
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.ZONES_DEPARTURE, new NoteType[] {NoteType.WSZONES_DEPARTURE_EX, NoteType.ZONE_EXIT_ALERTED}, null));
    	categoryFilterMap.put(EventCategory.DRIVER, eventCategoryFilterList);

    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.NO_DRIVER, new NoteType[] {NoteType.NO_DRIVER}, null));
    	categoryFilterMap.put(EventCategory.NO_DRIVER, eventCategoryFilterList);

    	eventCategoryFilterList = new ArrayList<EventCategoryFilter>();
    	eventCategoryFilterList.add(new EventCategoryFilter(EventType.UNKNOWN, (List<NoteType>)null, null));
    	categoryFilterMap.put(EventCategory.NONE, eventCategoryFilterList);

    }
    
    @SuppressWarnings("unchecked")
    public static Class getEventType(Integer proEventType)
    {
        NoteType noteType = NoteType.valueOf(proEventType);
        if (noteType.getEventClass() == null)
        {
            logger.error("Unsupported Event Type requested type = [" + proEventType + "] returning Base Event");
            return NoteType.BASE.getEventClass();
        }
        
        return noteType.getEventClass();
    }

    public static List<EventCategoryFilter> getEventCategoryFilter(EventCategory category)
    {
        return categoryFilterMap.get(category);
    }
}
