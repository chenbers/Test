package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Device;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/deviceService")
@Scope("request")
public interface DeviceService {


	@GET
	@Path("/devices/{userName}")
	public List<Device> getDevices(@PathParam("userName")String userName);
//TODO findBy imei, serialnum, sim, group.......
	@GET
	@Path("/device/{deviceID}")
	public Device getDevice(@PathParam("deviceID")Integer deviceID);

}
