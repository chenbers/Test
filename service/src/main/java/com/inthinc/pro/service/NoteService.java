package com.inthinc.pro.service;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/eventService")
public interface NoteService {
	
	
	@GET
	@Path("/createSpeedEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{averageSpeed}/{distance}")
	public String createSpeedEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit,
			@PathParam("averageSpeed")Integer averageSpeed,
			@PathParam("distance")Integer distance);
	

    @GET
    @Path("/createIdlingEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{averageSpeed}/{distance}")
    public String createIdlingEvent(
            @PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("averageSpeed")Integer averageSpeed,
            @PathParam("distance")Integer distance);
       
    @GET
    @Path("/createHardAccelerationEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{deltaX}/{deltaY}/{deltaZ}")
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
	@Path("/createIgnitionOffEvent/{imei}/{lat}/{lng}/{currentSpeed}")
	public String createEndTripEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed);
	
	@GET
	@Path("/createCurrentLocationEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}")
	public String createCurrentLocationEvent(@PathParam("imei")String imei,
			@PathParam("lat")Double latitude,
			@PathParam("lng")Double longitude,
			@PathParam("currentSpeed")Integer speed,
			@PathParam("speedLimit")Integer speedLimit,
			@PathParam("bearing")Integer bearing);
	
	@GET
    @Path("/createCrashEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{deltaX}/{deltaY}/{deltaZ}")
    public String createCrashEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("deltaX")Integer deltaX,
            @PathParam("deltaY")Integer deltaY,
            @PathParam("deltaZ")Integer deltaZ);
}
