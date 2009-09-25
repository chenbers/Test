package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Device;

import javax.xml.bind.annotation.XmlRootElement;



@Produces("application/xml")
@Path("/deviceService")
public interface DeviceService {


	@Path("/devices/{userName}")
	public List<Device> getDevices(@PathParam("userName")String userName);

	@Path("/device/{userName}")
	public Device getDevice(@PathParam("userName")String userName);

}
