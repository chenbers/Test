package com.inthinc.pro.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.security.annotation.Secured;

import com.inthinc.pro.service.annotations.DateFormat;

@Path("/")
public interface NoteService {
	
	
	@GET
	@Path("eventService/createSpeedEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{averageSpeed}/{distance}")
	public String createSpeedEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit,
			@PathParam("averageSpeed")Integer averageSpeed,
			@PathParam("distance")Integer distance);
	

    @GET
    @Path("eventService/createIdlingEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{averageSpeed}/{distance}")
    public String createIdlingEvent(
            @PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("averageSpeed")Integer averageSpeed,
            @PathParam("distance")Integer distance);
       
    @GET
    @Path("eventService/createHardAccelerationEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{deltaX}/{deltaY}/{deltaZ}")
    public String createHardAccelerationEvent(
            @PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("deltaX")Integer deltaX,
            @PathParam("deltaY")Integer deltaY,
            @PathParam("deltaZ")Integer deltaZ);
	
	@GET
	@Path("/createIgnitionOnEvent/{imei}/{lat}/{lng}/{currentSpeed}")
	public String createBeginTripEvent(
        	@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed);
	
	@GET
	@Path("eventService/createIgnitionOffEvent/{imei}/{lat}/{lng}/{currentSpeed}")
	public String createEndTripEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed);
	
	@GET
	@Path("eventService/createCurrentLocationEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}")
	public String createCurrentLocationEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit,
			@PathParam("bearing")Integer bearing);
	
	@GET
    @Path("/createCrashEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{deltaX}/{deltaY}/{deltaZ}")
    @Secured("ROLE_SUPERUSER")
    public String createCrashEvent(
    		@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("deltaX")Integer deltaX,
            @PathParam("deltaY")Integer deltaY,
            @PathParam("deltaZ")Integer deltaZ);
	
	@GET
    @Path("eventService/createZoneArrivalEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{zoneID}")
    public String createZoneArrivalEvent(
            @PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("zoneID")Integer zoneID);
	
	@GET
    @Path("eventService/createTamperingEvent/{imei}/{lat}/{lng}/{currentSpeed}/{bearing}")
    public String createTamperingEvent(
            @PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("bearing")Integer bearing);
	
	@GET
    @Path("eventService/createVehicleLowBatteryEvent/{imei}/{lat}/{lng}/{currentSpeed}/{bearing}")
    public String createVehicleLowBatteryEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("bearing")Integer bearing);
	
	@GET
    @Path("eventService/createSeatBeltEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{distance}")
    public String createSeatBeltEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("distance")Integer distance);
	
    @GET
    @Path("group/{groupID}/events/{noteTypes:all|.*}/{startDate}/{endDate}/count")
    public Response getEventCount(@PathParam("groupID")Integer groupID,
            @PathParam("noteTypes")String noteTypes,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
            @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);
	@GET
	@Produces("application/xml")
	@Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{endDate}/{page}")
	public Response getEvents(@PathParam("entity") String entity,
	        @PathParam("entityID")Integer entityID,
	        @PathParam("eventTypes")String eventTypes,
	        @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
	        @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate,
	        @PathParam("page") PathSegment page,
	        @Context UriInfo uriInfo);
    @GET
    @Produces("application/xml")
    @Path("{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{page}")
    public Response getEvents(@PathParam("entity") String entity,
            @PathParam("entityID")Integer entityID,
            @PathParam("eventTypes")String eventTypes,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate,
            @PathParam("page") PathSegment page,
            @Context UriInfo uriInfo);
}
