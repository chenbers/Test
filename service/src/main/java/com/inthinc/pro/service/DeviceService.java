package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/deviceService")
public interface DeviceService {
	


	@GET
	@Path("/devices/{userName}")
	public String getDevices(@PathParam("userName")String userName);


}
