package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Driver;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/driverService")
@Scope("request")
public interface DriverService {


	@GET
	@Path("/drivers/{userName}")
	public List<Driver> getDrivers(@PathParam("userName")String userName);

	@GET
	@Path("/driver/{driverID}")
	public Driver getDriver(@PathParam("driverID")Integer driverID);

}
