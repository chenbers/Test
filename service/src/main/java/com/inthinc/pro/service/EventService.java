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
public interface EventService {
    @GET
    @Produces({"application/xml","application/json", "application/fastinfoset"})
    @Path("group/{groupID}/events/{noteTypes:all|.*}/{startDate}/{endDate}/count")
    public Response getEventCount(@PathParam("groupID")Integer groupID,
            @PathParam("noteTypes")String noteTypes,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
            @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);
    @GET
    @Produces({"application/xml","application/json", "application/fastinfoset"})
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{endDate}/{page}")
    public Response getEvents(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
            @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate,
            @PathParam("page") PathSegment page,
            @Context UriInfo uriInfo);
    @GET
    @Produces({"application/xml","application/json", "application/fastinfoset"})
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{page}")
    public Response getEvents(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
            @PathParam("page") PathSegment page,
            @Context UriInfo uriInfo);
}
