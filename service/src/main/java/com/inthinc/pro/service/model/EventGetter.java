package com.inthinc.pro.service.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class EventGetter{
    

    @Autowired
    private EventDAO eventDAO;
    private Map<String, EventGetterStrategy> eventGetterMap;
    
    public void init(){

        eventGetterMap = new HashMap<String, EventGetterStrategy>();
        eventGetterMap.put("driver", new DriverEventGetter());
        eventGetterMap.put("vehicle",  new VehicleEventGetter());
        eventGetterMap.put("group", new GroupEventGetter());
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
    
    public List<Event> getEvents(String entity, Integer entityID, List<NoteType> noteTypes, Date start, Date end, int firstRow, int pageCount){
       
        EventGetterStrategy eventGetter = eventGetterMap.get(entity);
        if (eventGetter!= null){
            return eventGetter.getEvents(entityID, noteTypes, start, end, firstRow, pageCount);
        }
        return Collections.emptyList();
    }
    public Integer getEventCount(String entity, Integer entityID, List<NoteType> noteTypes, Date start, Date end){
        
        EventGetterStrategy eventGetter = eventGetterMap.get(entity);
        if (eventGetter!= null){
            return eventGetter.getEventCount(entityID, noteTypes, start, end);
        }
        return null;
    }
    private interface EventGetterStrategy{
        
        public List<Event> getEvents(Integer entityID, List<NoteType> noteTypes, Date start, Date end, int firstRow, int pageCount);
        public Integer getEventCount(Integer entityID, List<NoteType> noteTypes, Date start, Date end);
    }
    public class DriverEventGetter implements EventGetterStrategy{
        
        private List<Event> events;
        public List<Event> getEvents(Integer entityID, List<NoteType> noteTypes, Date start, Date end, int firstRow, int pageCount){
            
            events = eventDAO.getEventsForDriver(entityID, start, end, noteTypes, new Integer(1));
            List<Event> pageOfEvents = new ArrayList<Event>(events.subList(firstRow, Math.min(firstRow+pageCount, events.size()-firstRow)));

            return pageOfEvents;
        }
        public Integer getEventCount(Integer entityID, List<NoteType> noteTypes, Date start, Date end){
            if (events == null) {
               events = eventDAO.getEventsForDriver(entityID, start, end, noteTypes, new Integer(1));
            }
            return events.size();
            
        }
    }
    private class VehicleEventGetter implements EventGetterStrategy{
        
       private List<Event> events;
       public List<Event> getEvents(Integer entityID, List<NoteType> noteTypes, Date start, Date end, int firstRow, int pageCount){
            events = eventDAO.getEventsForVehicle(entityID, start, end, noteTypes, new Integer(1));
            List<Event> pageOfEvents = new ArrayList<Event>(events.subList(firstRow, Math.min(firstRow+pageCount, events.size()-firstRow)));

            return pageOfEvents;
        }
        public Integer getEventCount(Integer entityID, List<NoteType> noteTypes, Date start, Date end){
            if (events == null) {
                events = eventDAO.getEventsForDriver(entityID, start, end, noteTypes, new Integer(1));
             }
             return events.size();
        }
   }
    private class GroupEventGetter implements EventGetterStrategy{
        
        public List<Event> getEvents(Integer entityID, List<NoteType> noteTypes, Date start, Date end, int firstRow, int pageCount){
            
            List<TableFilterField> filterList = new ArrayList<TableFilterField>();
//            filterList.add(new TableFilterField("type", noteTypes));  
            TableSortField sort = new TableSortField(SortOrder.ASCENDING,"time");
            PageParams pageParams = new PageParams(firstRow, firstRow+pageCount-1, sort, filterList);
            List<Event> events = eventDAO.getEventPage(entityID, start, end, EventDAO.INCLUDE_FORGIVEN, noteTypes, pageParams);

            return events;
        }
        public Integer getEventCount(Integer entityID, List<NoteType> noteTypes, Date start, Date end){
            List<TableFilterField> filterList = new ArrayList<TableFilterField>();
            filterList.add(new TableFilterField("type", noteTypes));  
            return eventDAO.getEventCount(entityID, start, end,
                    new Integer(1), noteTypes, filterList);
        }
    }
}
