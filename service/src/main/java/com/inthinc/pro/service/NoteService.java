package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.security.annotation.Secured;

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
    @Path("/createZoneArrivalEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{zoneID}")
    public String createZoneArrivalEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("zoneID")Integer zoneID);
	
	@GET
    @Path("/createTamperingEvent/{imei}/{lat}/{lng}/{currentSpeed}/{speedLimit}/{bearing}/{backupBatteryValue}")
    public String createTamperingEvent(@PathParam("imei")String imei,
            @PathParam("lat")Double latitude,
            @PathParam("lng")Double longitude,
            @PathParam("currentSpeed")Integer speed,
            @PathParam("speedLimit")Integer speedLimit,
            @PathParam("bearing")Integer bearing,
            @PathParam("backupBatteryValue")Integer backupBatteryValue);
}
