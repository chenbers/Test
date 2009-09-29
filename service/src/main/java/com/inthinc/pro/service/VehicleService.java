package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;

import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import org.springframework.context.annotation.Scope;



@Produces("application/xml")
@Path("/vehicles")
@Scope("request")
public interface VehicleService {


	@GET
	@Path("/")
	public List<Vehicle> getAll();

	@GET
	@Path("/id/{id}")
	public Vehicle get(@PathParam("id")Integer id);

	@GET
	@Path("/vin/{vin}")
	public Vehicle findByVIN(@PathParam("vin")String vin);

	@POST
	@Consumes("application/xml")
	@Path("/add")
	public Integer add(Vehicle vehicle);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public Integer update(Vehicle vehicle);

	@GET
	@Path("/delete/{id}")
	public Integer delete(@PathParam("id")Integer id);
	
	@POST
	@Consumes("application/xml")
	@Path("/add")
	public List<Integer> add(List<Vehicle> vehicles);

	@POST
	@Consumes("application/xml")
	@Path("/update")
	public List<Integer> update(List<Vehicle> vehicles);
	
	@POST
	@Consumes("application/xml")
	@Path("/delete")
	public List<Integer> delete(List<Integer> vehicleIDs);

	//TODO can we overload /id/ and remove assigndevice verb?
	@GET
	@Path("/assigndevice/{id}/deviceid/{deviceid}")
	public Vehicle assignDevice(@PathParam("id")Integer id, @PathParam("deviceid")Integer deviceid);

	//TODO can we overload /id/ and remove assigndriver verb?
	@GET
	@Path("/assigndriver/{id}/driverid/{driverid}")
	public Vehicle assignDriver(@PathParam("id")Integer id, @PathParam("driverid")Integer driverid);

	@GET
	@Path("/trips/{id}/{date}")
	//DATE is in YYYYMMDD format
	public List<Trip> getTrips(@PathParam("id")Integer id, @PathParam("date")String date);

	@GET
	@Path("/trips/{id}")
	public List<Trip> getTrips(@PathParam("id")Integer id);
	
	@GET
	@Path("/mpg/{id}")
	public List<MpgEntity> getVehicleMPG(@PathParam("id")Integer id);

}
