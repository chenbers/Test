package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jboss.resteasy.specimpl.PathSegmentImpl;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.NoteService;
import com.inthinc.pro.service.model.EventGetter;
import com.inthinc.pro.service.note.Attribute;
import com.inthinc.pro.service.note.AttributeType;
import com.inthinc.pro.service.note.CrashData;
import com.inthinc.pro.service.note.Heading;
import com.inthinc.pro.service.note.Note;
import com.inthinc.pro.service.pagination.EventPage;
import com.inthinc.pro.service.pagination.Link;
import com.inthinc.pro.util.dateUtil.DateUtil;
@SuppressWarnings("unchecked")
public class NoteServiceImpl implements NoteService {
    private MCMService mcmService;
    private CrashReportDAO crashReportDAO;
    private EventGetter eventGetter;
    private static final Integer BOUNDARY = 146;
    private static final Logger logger = Logger.getLogger(NoteService.class);
    
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";

    public String createSpeedEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer averageSpeed, Integer distance) {
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, averageSpeed);
        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
        Note note = new Note(NoteType.SPEEDING, new Date(), latitude, longitude, speed, 10, 9, 1, speedLimitAttribute, averageSpeedAttribute, topSpeedAttribute, distanceAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createIdlingEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer averageSpeed, Integer distance) {
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, averageSpeed);
        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
        Note note = new Note(NoteType.SPEEDING, new Date(), latitude, longitude, speed, 10, 9, 1, speedLimitAttribute, averageSpeedAttribute, topSpeedAttribute, distanceAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createHardAccelerationEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer deltaX, Integer deltaY,
            Integer deltaZ) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, BOUNDARY); // /146 = Utah
        Attribute deltaXAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVX, deltaX);
        Attribute deltaYAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVY, deltaY);
        Attribute deltaZAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVZ, deltaZ);
        Note note = new Note(NoteType.NOTEEVENT, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundaryAttribute, deltaXAttribute, deltaYAttribute,
                deltaZAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createBeginTripEvent(String imei, Double latitude, Double longitude, Integer speed) {
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Note note = new Note(NoteType.IGNITION_ON, new Date(), latitude, longitude, speed, 10, 9, 1, boundaryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createEndTripEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed) {
        Attribute mpgAttribute = new Attribute(AttributeType.ATTR_TYPE_MPG, 25);
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Note note = new Note(NoteType.IGNITION_OFF, new Date(), latitude, longitude, speed, 10, 9, 1, boundaryAttribute, mpgAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createCurrentLocationEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Note note = new Note(NoteType.LOCATION, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundaryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @RolesAllowed("ROLE_NORMALUSER")
    public String createCrashEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer deltaX,
            Integer deltaY, Integer deltaZ) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146); // /146 = Utah
        Attribute deltaXAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVX, deltaX);
        Attribute deltaYAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVY, deltaY);
        Attribute deltaZAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVZ, deltaZ);
        Note note = new Note(NoteType.FULLEVENT, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundaryAttribute, deltaXAttribute, deltaYAttribute,
                deltaZAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            createCrashDataPoints(imei, latitude, longitude, speed, speedLimit);
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    private void createCrashDataPoints(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit) {
        List<byte[]> crashDataPointList = new ArrayList<byte[]>();
        int interate_count = 1;
        double latIncrement = 0.02;
        double lngIncrement = 0.04;
        double lat = latitude - .2;
        double lng = longitude - .4;
        int s = speed + interate_count;
        Date date = new Date();
        date.setTime(date.getTime() - 20000);
        for (int i = 0; i < interate_count; i++) {
            CrashData crashData = new CrashData(date, lat += latIncrement, lng += lngIncrement, s--, s--, 4000);
            crashDataPointList.add(crashData.getBytes());
        }        
        Integer response = mcmService.crash(imei, crashDataPointList);
        logger.debug("MCMProxy.crash() response = " + response);
    }

    public void setMcmService(MCMService mcmService) {
        this.mcmService = mcmService;
    }

    public MCMService getMcmService() {
        return mcmService;
    }

    @Override
    public String createZoneArrivalEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer zoneID) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, BOUNDARY); // /146 = Utah
        Attribute zoneIdAttribute = new Attribute(AttributeType.ATTR_TYPE_ZONE_ID, zoneID);
        Note note = new Note(NoteType.WSZONES_ARRIVAL_EX, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundaryAttribute, zoneIdAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createTamperingEvent(String imei, Double latitude, Double longitude, Integer speed, Integer bearing) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Attribute backupBatteryAttribute = new Attribute(AttributeType.ATTR_TYPE_BACKUP_BATTERY, 1);
        Note note = new Note(NoteType.UNPLUGGED, new Date(), latitude, longitude, speed, 10, 9, heading, boundaryAttribute, backupBatteryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createVehicleLowBatteryEvent(String imei, Double latitude, Double longitude, Integer speed, Integer bearing) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Note note = new Note(NoteType.LOW_BATTERY, new Date(), latitude, longitude, speed, 10, 9, heading, boundryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createSeatBeltEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer distance) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute boundaryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDARY, 146);// /146 = Utah
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, speed);
        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
        Note note = new Note(NoteType.SEATBELT, new Date(), latitude, longitude, speed, 10, 9, heading, boundaryAttribute, speedLimitAttribute, distanceAttribute,
                averageSpeedAttribute, topSpeedAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

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
    public CrashReportDAO getCrashReportDAO() {
        return crashReportDAO;
    }

    public void setCrashReportDAO(CrashReportDAO crashReportDAO) {
        this.crashReportDAO = crashReportDAO;
    }

    public void setEventGetter(EventGetter eventGetter) {
        this.eventGetter = eventGetter;
    }


}
