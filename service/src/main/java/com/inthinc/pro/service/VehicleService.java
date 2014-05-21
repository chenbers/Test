package com.inthinc.pro.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Vehicle;

@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
public interface VehicleService {

    @GET
    @Path("/vehicles")
    public Response getAll();

    @GET
    @Path("/vehicle/{id}")
    public Response get(@PathParam("id") Integer id);

	/**
	 * Get Vehicle by VIN
	 * @param vin The VIN of the vehicle to find.
     * @returnWrapped Vehicle Returns a vehicle found by VIN.
	 * @HTTP HTTP 404 - if the vehicle is not found by VIN.
	 */
    @GET
    @Path("/vehicle/vin/{vin}")
    public Response findByVIN(@PathParam("vin") String vin);

    @GET
    @Path("/vehicle/{vehicleID}/score/{numberOfDays}")
    public Response getScore(@PathParam("vehicleID") Integer vehicleID, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    @GET
    @Path("/vehicle/{vehicleID}/trend/{numberOfDays}")
    public Response getTrend(@PathParam("vehicleID") Integer vehicleID, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    @GET
    @Path("/vehicle/{id}/trips/{date}")
    // DATE is in YYYYMMDD format
    public Response getTrips(@PathParam("id") Integer id, @PathParam("date") String date);

    @GET
    @Path("/vehicle/{id}/trips")
    public Response getTrips(@PathParam("id") Integer id);
    @GET
    @Path("/vehicle/{vehicleID}/trip")
    Response getLastTrip(@PathParam("vehicleID") Integer vehicleID);
    /**
     * Service to get the trips of given driver that has been performed between a given date and today.
     * @param driverID the Driver ID
     * @param fromDateTime the startDate of the interval to search. This date must be max 30 days before today. 
     * @param toDateTime the endDate of the interval to search. This date must be after the fromDateTime. 
     * @returnWrapped List<Trip> the driver trips performed between a given date and today
     * @HTTP HTTP 200 - OK if trips performed between a given date and today were found - can be empty result
     * @HTTP HTTP 400 - BAD REQUEST if date is more than 30 days back from today 
     */
    @GET
    @Path("/vehicle/{vehicleID}/trips/{fromDateTime}/{toDateTime}")
    Response getTrips(@PathParam("vehicleID") Integer vehicleID, 
                                 @PathParam("fromDateTime") String fromDateTime,
                                 @PathParam("toDateTime") String toDateTime);

//    @GET
//    @Path("/vehicle/{id}/events/{date}")
//    // DATE is in YYYYMMDD format
//    public Response getEvents(@PathParam("id") Integer id, @PathParam("date") String date);
//
//    @GET
//    @Path("/vehicle/{id}/events")
//    public Response getEvents(@PathParam("id") Integer id);

    @GET
    @Path("/vehicle/{id}/lastlocation")
    public Response getLastLocation(@PathParam("id") Integer vehicleID);

    @GET
    @Path("/vehicle/{id}/lastLocationExtraInfo")
    public Response getLastLocationExtraInfo(@PathParam("id") Integer vehicleID);

    @POST
    @Consumes("application/xml")
    @Path("/vehicle")
    public Response create(Vehicle vehicle, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/vehicle")
    public Response update(Vehicle vehicle);

    @DELETE
    @Path("/vehicle/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/vehicles")
    public Response create(List<Vehicle> vehicles, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/vehicles")
    public Response update(List<Vehicle> vehicles);

    @DELETE
    @Consumes("application/xml")
    @Path("/vehicles")
    public Response delete(List<Integer> vehicleIDs);

    @PUT
    @Path("/vehicle/{id}/deviceid/{deviceid}")
    public Response assignDevice(@PathParam("id") Integer id, @PathParam("deviceid") Integer deviceid);

    @PUT
    @Path("/vehicle/{id}/driverid/{driverid}")
    public Response assignDriver(@PathParam("id") Integer id, @PathParam("driverid") Integer driverid);

    // @GET
    // @Path("/vehicle/{id}/mpg")
    // public Response getVehicleMPG(@PathParam("id") Integer id);

}
