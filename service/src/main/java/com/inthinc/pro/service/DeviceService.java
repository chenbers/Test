package com.inthinc.pro.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



@Path("/")
@Produces("application/xml")
public interface DeviceService {

	@GET
	@Path("/devices")
	public Response getAll();

	//TODO findBy group.......
	
	@GET
	@Path("/device/{deviceID}")
	public Response get(@PathParam("deviceID")Integer deviceID);

	@GET
	@Path("/device/imei/{imei}")
	public Response findByIMEI(@PathParam("imei")String imei);

	@GET
	@Path("/device/serialnum/{serialnum}")
	public Response findBySerialNum(@PathParam("serialnum")String serialnum);
	
//TODO do we disallow delete and add?
//TODO do we allow update on limited fields like ecall?
}
