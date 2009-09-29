package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Driver;

import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/drivers")
@Scope("request")
public interface DriverService {


	@GET
	@Path("/")
	public List<Driver> getAll();

	@GET
	@Path("/id/{driverID}")
	public Driver get(@PathParam("driverID")Integer driverID);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer add(Driver driver);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public Integer update(Driver driver);

	@GET
	@Path("/delete/{id}")
	public Integer delete(@PathParam("id")Integer id);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public List<Integer> add(List<Driver> drivers);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public List<Integer> update(List<Driver> drivers);
	
	@POST
	@Consumes("application/xml")
	@Path("/delete")
	public List<Integer> delete(List<Integer> vehicleIDs);

}
