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

import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/")
@Scope("request")
public interface VehicleService {

	@GET
	@Path("vehicles")
	public List<Vehicle> getAll();

	@GET
	@Path("vehicle/{id}")
	public Vehicle get(@PathParam("id")Integer id);

	@GET
	@Path("vehicle/vin/{vin}")
	public Vehicle findByVIN(@PathParam("vin")String vin);

	@POST
	@Consumes("application/xml")
	@Path("vehicle")
	public Integer add(Vehicle vehicle);

	@PUT
	@Consumes("application/xml")
	@Path("vehicle")
	public Integer update(Vehicle vehicle);

	@DELETE
	@Path("vehicle/{id}")
	public Integer delete(@PathParam("id")Integer id);
	
	@POST
	@Consumes("application/xml")
	@Path("vehicles")
	public List<Integer> add(List<Vehicle> vehicles);

	@PUT
	@Consumes("application/xml")
	@Path("vehicles")
	public List<Integer> update(List<Vehicle> vehicles);
	
	@DELETE
	@Consumes("application/xml")
	@Path("/vehicles")
	public List<Integer> delete(List<Integer> vehicleIDs);

	@PUT
	@Path("vehicle/{id}/deviceid/{deviceid}")
	public Vehicle assignDevice(@PathParam("id")Integer id, @PathParam("deviceid")Integer deviceid);

	@PUT
	@Path("vehicle/{id}/driverid/{driverid}")
	public Vehicle assignDriver(@PathParam("id")Integer id, @PathParam("driverid")Integer driverid);

	@GET
	@Path("vehicle/{id}/trips/{date}")
	//DATE is in YYYYMMDD format
	public List<Trip> getTrips(@PathParam("id")Integer id, @PathParam("date")String date);

	@GET
	@Path("vehicle/{id}/trips")
	public List<Trip> getTrips(@PathParam("id")Integer id);
	
	@GET
	@Path("vehicle/{id}/mpg")
	public List<MpgEntity> getVehicleMPG(@PathParam("id")Integer id);

}
