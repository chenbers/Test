package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.EventService;
import com.inthinc.pro.service.model.EventGetter;
import com.inthinc.pro.service.pagination.EventPage;
import com.inthinc.pro.service.pagination.Link;
import com.inthinc.pro.util.dateUtil.DateUtil;

public class EventServiceImpl implements EventService {
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";
    private EventGetter eventGetter;

    @Override
    public Response getEventCount(Integer groupID, String noteTypes, Date startDate, Date endDate) {
        Date normalizedStartDate = DateUtil.getBeginningOfDay(startDate);
        Date normalizedEndDate = DateUtil.getEndOfDay(endDate);
        
        if (!DateUtil.validDateRange(normalizedStartDate, normalizedEndDate)) {
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be greater than end date (" + endDate + ").").build();
        }
        try {
            List<NoteType> noteTypesList = parseNoteTypes(noteTypes);
            Integer count = eventGetter.getEventCount("group", groupID,  noteTypesList, normalizedStartDate, normalizedEndDate);
            return Response.ok(new GenericEntity<Integer>(new Integer(count)) {}).build();

        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
    @Override
    public Response getEvents(String entity, Integer entityID, String eventTypes, Date startDate,
            PathSegment page, UriInfo uriInfo) {
        return getEvents(entity, entityID, eventTypes,startDate, new Date(), page, uriInfo);
    }

    @Override
    public Response getEvents(String entity, Integer entityID, String eventTypes, Date startDate, Date endDate,
            PathSegment page, UriInfo uriInfo) {
        Date normalizedStartDate = DateUtil.getBeginningOfDay(startDate);
        Date normalizedEndDate = DateUtil.getEndOfDay(endDate);
        
        if (normalizedStartDate.after(normalizedEndDate)) {
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be greater than end date (" + endDate + ").").build();
        }
        try {
            int start = validateStart(page.getMatrixParameters().getFirst("start"));
            int pageCount = validatePageCount(page.getMatrixParameters().getFirst("pageCount"));
            
            List<NoteType> noteTypesList = parseNoteTypes(eventTypes);
            
            List<Event> pageOfEvents = eventGetter.getEvents(entity, entityID,  noteTypesList, normalizedStartDate, normalizedEndDate, start, pageCount);
            if (pageOfEvents==null){
                return Response.status(Status.BAD_REQUEST).build();
            }
            if(pageOfEvents.isEmpty()){
                return Response.status(Status.NOT_FOUND).build();
            }
                
            Integer totalCount = eventGetter.getEventCount(entity, entityID,  noteTypesList, normalizedStartDate, normalizedEndDate);

            List<Link> links = getLinks(uriInfo,start,pageCount,totalCount);

            EventPage eventPage = createPage(pageOfEvents, start, pageCount, totalCount, links);
            
            Response.ResponseBuilder builder = Response.ok(new GenericEntity<EventPage>(eventPage) {});
            
            return builder.build();
        }
        catch(NumberFormatException nfe){
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start and pageCount parameter values must be numeric.").build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Note types or start or pageCount are incorrect.").build();
        }
    }
    private int validateStart(String startParameter) throws IllegalArgumentException{
        
        if (startParameter == null) return 0;
        int start = Integer.parseInt(startParameter);
        if(start < 0) throw new IllegalArgumentException();
        
        return start;
    }
    private int validatePageCount(String pageCountParameter) throws IllegalArgumentException{
        
        if(pageCountParameter == null) return 20;
        int pageCount = Integer.parseInt(pageCountParameter);
        if(pageCount <= 0) throw new IllegalArgumentException();
        
        return pageCount;
    }
    private EventPage createPage(List<Event> pageOfEvents, int start, int pageCount, int totalCount, List<Link> links){
        
        EventPage eventPage = new EventPage();
        eventPage.setStart(start);
        eventPage.setPageCount(pageOfEvents.size());
        eventPage.setTotalCount(totalCount);
        eventPage.setEvents(pageOfEvents);
        eventPage.setLinks(links);

        return eventPage;
    }
    private List<Link> getLinks(UriInfo uriInfo, int start, int size, int recordCount){
        
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        
        if(!hasPage(uriInfo)){
           builder.path("page");
        }
        List<Link> links = new ArrayList<Link>();
        links.add(getNextLink(start,size,recordCount, builder));
        links.add(getPrevLink(start,size,recordCount, builder));
        
        return links;
    }
    private boolean hasPage(UriInfo uriInfo){
        List<PathSegment> pathSegments = uriInfo.getPathSegments();
        for(PathSegment ps : pathSegments){
            if("page".equals(ps.getPath())){
                return true;
            }
        }
        return false;
    }
    private Link getNextLink(int start, int size, int recordCount, UriBuilder builder){

        if (start + size < recordCount)
        {
           int next = start + size;
           URI nextUri = builder.clone().replaceMatrixParam("start", ""+next).replaceMatrixParam("pageCount", ""+size).build();
           Link nextLink = new Link("next",
                        nextUri.toString(), "application/xml");
           return nextLink;
        }
        return null;
    }
    private Link getPrevLink(int start, int size, int recordCount, UriBuilder builder){

        if (start > 0)
        {
           int previous = start - size;
           if (previous < 0) previous = 0;
           URI previousUri = builder.clone().replaceMatrixParam("start", ""+previous).replaceMatrixParam("pageCount", ""+size).build();
           Link previousLink = new Link("previous", previousUri.toString(), "application/xml");
           
           return previousLink;
        };
        return null;
    }
    private List<NoteType> parseNoteTypes(String eventTypes) throws IllegalArgumentException{
        
        String [] eventTypesArray = eventTypes.split("/");
        
        if("all".equals(eventTypesArray[0])){
            return new ArrayList<NoteType>(EnumSet.range(NoteType.FULLEVENT,NoteType.STRIPPED_ACKNOWLEDGE));
        }
        return getRequestedNoteTypes(eventTypesArray);
    }
    
    private List<NoteType> getRequestedNoteTypes(String[] eventTypesArray){
        
        List<NoteType> noteTypesList = new ArrayList<NoteType>();
        for(int i=0;i<eventTypesArray.length;i++){
            
            List<NoteType> noteTypes = EventType.valueOf(eventTypesArray[i].toUpperCase()).getNoteTypeList();
            if (noteTypes==null) throw new IllegalArgumentException();
            noteTypesList.addAll(noteTypes);
        }
        return noteTypesList;

    }
    public void setEventGetter(EventGetter eventGetter) {
        this.eventGetter = eventGetter;
    }

}
