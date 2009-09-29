package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Device;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/devices")
@Scope("request")
public interface DeviceService {


	@GET
	@Path("/")
	public List<Device> getAll();
//TODO findBy imei, serialnum, sim, group.......
	@GET
	@Path("/id/{deviceID}")
	public Device get(@PathParam("deviceID")Integer deviceID);

//TODO do we disallow delete and add?
//TODO do we allow update on limited fields like ecall?
}
