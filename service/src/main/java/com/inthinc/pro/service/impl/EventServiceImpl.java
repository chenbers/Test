package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.service.annotations.DateFormat;
import jxl.write.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.EventService;
import com.inthinc.pro.service.exceptionMappers.BadDateRangeExceptionMapper;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.model.EventGetter;
import com.inthinc.pro.service.pagination.EventPage;
import com.inthinc.pro.service.pagination.Link;
import com.inthinc.pro.util.DateUtil;

public class EventServiceImpl implements EventService {
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";
    private EventGetter eventGetter;

    @Override
    public Response getEventCount(Integer groupID, String noteTypes, Date startDate, Date endDate) {
        try {
            Interval interval = DateUtil.getInterval(startDate,endDate);
            List<NoteType> noteTypesList = parseNoteTypes(noteTypes);
            Integer count = eventGetter.getEventCount("group", groupID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());
           
            return Response.ok(count.toString()).build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response getDriverEventCount(Integer driverID, String noteTypes, Date startDate, Date endDate) {
        try {
            Interval interval = DateUtil.getInterval(startDate,endDate);
            List<NoteType> noteTypesList = parseNoteTypes(noteTypes);
            Integer count = eventGetter.getEventCount("driver", driverID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());

            return Response.ok(count.toString()).build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response getVehicleEventCount(Integer vehicleID, String noteTypes, Date startDate, Date endDate) {
        try {
            Interval interval = DateUtil.getInterval(startDate,endDate);
            List<NoteType> noteTypesList = parseNoteTypes(noteTypes);
            Integer count = eventGetter.getEventCount("vehicle", vehicleID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());

            return Response.ok(count.toString()).build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response getEventCountByDuration(Integer driverID, @DateFormat(SIMPLE_DATE_FORMAT) String dateTime, Integer duration) {
        try {
            org.joda.time.DateTime endDate = new org.joda.time.DateTime(dateTime);
            org.joda.time.DateTime startDate = endDate.minusMinutes(duration);
            DateUtil.checkDateRange(startDate, endDate);
            List<NoteType> noteTypesList = parseNoteTypes("all");
            Integer count = eventGetter.getEventCount("driver", driverID,  noteTypesList, startDate.toDate(), endDate.toDate());
            return Response.ok(count.toString()).build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response getEventsFirstPage(String entity, Integer entityID, String eventTypes, Date startDate, Date endDate, UriInfo uriInfo) {
        try {
            Interval interval = DateUtil.getInterval(startDate,endDate);

            int pageStart = 0;
            int pageCount = 20;
            
            return getEventResponse(entity, entityID, eventTypes, interval, uriInfo, pageStart, pageCount );
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
    }
    @Override
    public Response getEventsFirstPage(String entity, Integer entityID, String eventTypes, Date startDate, UriInfo uriInfo) {
        try {
            Interval interval = DateUtil.getInterval(startDate,new Date());

            int pageStart = 0;
            int pageCount = 20;
            
            return getEventResponse(entity, entityID, eventTypes, interval, uriInfo, pageStart, pageCount );
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }
    }
    private Response getEventResponse(String entity, Integer entityID, String eventTypes, Interval interval, UriInfo uriInfo, int pageStart, int pageCount){
        try{
            List<NoteType> noteTypesList = parseNoteTypes(eventTypes);
            
            List<Event> pageOfEvents = eventGetter.getEvents(entity, entityID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate(), pageStart, pageCount);
            if (pageOfEvents==null){
                return Response.status(Status.BAD_REQUEST).build();
            }
            if(pageOfEvents.isEmpty()){
                return Response.status(Status.NOT_FOUND).build();
            }
                
            Integer totalCount = eventGetter.getEventCount(entity, entityID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());
    
            List<Link> links = getLinks(uriInfo,interval,pageStart,pageCount,totalCount);
    
            EventPage eventPage = createPage(pageOfEvents, pageStart, pageCount, totalCount, links);
            
            Response.ResponseBuilder builder = Response.ok(new GenericEntity<EventPage>(eventPage) {});
            
            return builder.build();
        }
        catch(NumberFormatException nfe){
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start and pageCount parameter values must be numeric.").build();
        }

    }

    @Override
    public Response getEvents(String entity, Integer entityID, String eventTypes, String startDateString, String endDateString,
            PathSegment page, UriInfo uriInfo) {
        Date startDate = DateUtil.buildDateTimeFromString(startDateString);
        Date endDate = DateUtil.buildDateTimeFromString(endDateString);
        try {
            Interval interval = DateUtil.getInterval(startDate,endDate);

            int start = validateStart(page.getMatrixParameters().getFirst("start"));
            int pageCount = validatePageCount(page.getMatrixParameters().getFirst("pageCount"));
            
            List<NoteType> noteTypesList = parseNoteTypes(eventTypes);
            
            List<Event> pageOfEvents = eventGetter.getEvents(entity, entityID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate(), start, pageCount);
            if (pageOfEvents==null){
                return Response.status(Status.BAD_REQUEST).build();
            }
            if(pageOfEvents.isEmpty()){
                return Response.status(Status.NOT_FOUND).build();
            }
                
            Integer totalCount = eventGetter.getEventCount(entity, entityID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());

            List<Link> links = getLinks(uriInfo,interval,start,pageCount,totalCount);

            EventPage eventPage = createPage(pageOfEvents, start, pageCount, totalCount, links);
            
            Response.ResponseBuilder builder = Response.ok(new GenericEntity<EventPage>(eventPage) {});

            return builder.build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
        }

        catch(NumberFormatException nfe){
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start and pageCount parameter values must be numeric.").build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Note types or start or pageCount are incorrect.").build();
        }
    }

    @Override
    public Response getEventsByDuration(Integer driverID, String dateTime, Integer duration, PathSegment page, @Context UriInfo uriInfo) {
        try {
            Date fromDate = DateUtil.buildDateTimeFromString(dateTime);
            org.joda.time.DateTime endDate = new org.joda.time.DateTime(fromDate);
            org.joda.time.DateTime startDate = endDate.minusMinutes(duration);
            Interval interval = DateUtil.getInterval(startDate.toDate(),endDate.toDate());

            int start = validateStart(page.getMatrixParameters().getFirst("start"));
            int pageCount = validatePageCount(page.getMatrixParameters().getFirst("pageCount"));

            List<NoteType> noteTypesList = parseNoteTypes("all");

            List<Event> pageOfEvents = eventGetter.getEvents("driver", driverID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate(), start, pageCount);
            if (pageOfEvents==null){
                return Response.status(Status.BAD_REQUEST).build();
            }
            if(pageOfEvents.isEmpty()){
                return Response.status(Status.NO_CONTENT).build();
            }

            Integer totalCount = eventGetter.getEventCount("driver", driverID,  noteTypesList, interval.getStart().toDate(), interval.getEnd().toDate());

            List<Link> links = getLinks(uriInfo,interval,start,pageCount,totalCount);

            EventPage eventPage = createPage(pageOfEvents, start, pageCount, totalCount, links);

            Response.ResponseBuilder builder = Response.ok(new GenericEntity<EventPage>(eventPage) {});

            return builder.build();
        }
        catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
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
    private List<Link> getLinks(UriInfo uriInfo, Interval interval, int start, int size, int recordCount){
        
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        
        if(hasNoEndDate(uriInfo)){
        	builder.path(DateUtil.getFormattedDate(interval.getEnd()));
        }
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
    private boolean hasNoEndDate(UriInfo uriInfo){
    	return uriInfo.getPathParameters().get("endDate") == null;
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
        
        String [] eventTypesArray = eventTypes.split(",");
        
        if("all".equals(eventTypesArray[0])){
            List<NoteType> noteTypes = new ArrayList<NoteType>(EnumSet.range(NoteType.FULLEVENT,NoteType.THEFT));
            noteTypes.addAll(EnumSet.range(NoteType.NEW_DRIVER,NoteType.STRIPPED_ACKNOWLEDGE));
            return noteTypes;
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
