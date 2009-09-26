package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import com.inthinc.pro.model.Vehicle;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/vehicleService")
@Scope("request")
public interface VehicleService {


	@GET
	@Path("/vehicles/{userName}")
	public List<Vehicle> getVehicles(@PathParam("userName")String userName);

	@GET
	@Path("/vehicle/{vehicleID}")
	public Vehicle getVehicle(@PathParam("vehicleID")Integer vehicleID);

}
