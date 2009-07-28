package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/sbs")
public interface SpeedByStreetService {
	
	
	@GET
	@Path("/speedLimit/{lat}/{lng}")
	public String getSpeedLimit(@PathParam("lat")Double latitude,
				@PathParam("lng")Double longitude);
	
	@GET
	@Path("/requestSpeedChange/{linkId}/{speedLimit}/{address}/{commment}")
	public String requestSpeedLimitChange(@PathParam("linkId")Integer linkId,@PathParam("speedLimit")Integer speedLimit,@PathParam("address")String address,
			@PathParam("commment")String comment);
}
