package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.inthinc.pro.model.Device;



@Path("/")
@Produces("application/xml")
public interface DeviceService {


	@GET
	@Path("/devices")
	public List<Device> getAll();

	//TODO findBy group.......
	
	@GET
	@Path("/device/{deviceID}")
	public Device get(@PathParam("deviceID")Integer deviceID);

	@GET
	@Path("/device/imei/{imei}")
	public Device findByIMEI(@PathParam("imei")String imei);

	@GET
	@Path("/device/serialnum/{serialnum}")
	public Device findBySerialNum(@PathParam("serialnum")String serialnum);
	
//TODO do we disallow delete and add?
//TODO do we allow update on limited fields like ecall?
}
