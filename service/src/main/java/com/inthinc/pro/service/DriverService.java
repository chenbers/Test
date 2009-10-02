package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Driver;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Scope("request")
public interface DriverService {


	@GET
	@Path("/drivers")
	public List<Driver> getAll();

	@GET
	@Path("/driver/{driverID}")
	public Driver get(@PathParam("driverID")Integer driverID);

	@POST
	@Consumes("application/xml")
	@Path("/driver")
	public Integer add(Driver driver);

	@PUT
	@Consumes("application/xml")
	@Path("/driver")
	public Integer update(Driver driver);

	@DELETE
	@Path("/driver/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/drivers")
	public List<Integer> add(List<Driver> drivers);

	@PUT
	@Consumes("application/xml")
	@Path("/drivers")
	public List<Integer> update(List<Driver> drivers);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/drivers")
	public List<Integer> delete(List<Integer> vehicleIDs);

}
