package com.inthinc.pro.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.service.annotations.DateFormat;
@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
public interface EventService {

    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    @GET
    @Produces({"text/*"})
    @Path("group/{groupID}/events/{noteTypes:all|.*}/{startDate}/{endDate}/count")
    public Response getEventCount(@PathParam("groupID")Integer groupID,
            @PathParam("noteTypes")String noteTypes,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate);
    
    @GET
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}")
    public Response getEventsFirstPage(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @Context UriInfo uriInfo);
    @GET
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{endDate}")
    public Response getEventsFirstPage(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @Context UriInfo uriInfo);

    @GET
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{endDate}/{page}")
    public Response getEvents(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @PathParam("page") PathSegment page,
            @Context UriInfo uriInfo);
    
}
