package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Vehicle;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/vehicles")
@Scope("request")
public interface VehicleService {


	@GET
	@Path("/")
	public List<Vehicle> getVehicles();

	@GET
	@Path("/id/{vehicleID}")
	public Vehicle getVehicle(@PathParam("vehicleID")Integer vehicleID);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer addVehicle(Vehicle vehicle);
}
