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

import com.inthinc.pro.model.Driver;

@Path("/")
@Produces("application/xml")
public interface DriverService {

    @GET
    @Path("/drivers")
    public Response getAll();

    @GET
    @Path("/driver/{driverID}")
    public Response get(@PathParam("driverID") Integer driverID);

    @GET
    @Path("/driver/{driverID}/events/speeding")
    public Response getSpeedingEvents(@PathParam("driverID") Integer driverID);

    @GET
    @Path("/driver/{driverID}/score/{numberOfDays}")
    public Response getScore(@PathParam("driverID") Integer driverID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    @GET
    @Path("/driver/{driverID}/trend/{numberOfDays}")
    public Response getTrend(@PathParam("driverID") Integer driverID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);
    
    @POST
    @Consumes("application/xml")
    @Path("/driver")
    public Response create(Driver driver, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/driver")
    public Response update(Driver driver);

    @DELETE
    @Path("/driver/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/drivers")
    public Response create(List<Driver> drivers, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/drivers")
    public Response update(List<Driver> drivers);

    @DELETE
    @Consumes("application/xml")
    @Path("/drivers")
    public Response delete(List<Integer> vehicleIDs);
    
    /**
     * Service to get the last trip of given driver.
     * @param driverID the Driver ID
     * @returnWrapped Trip the last driver trip
     * @HTTP HTTP 200 - OK if last trip found
     * @HTTP HTTP 404 - NOT FOUND if no trip found 
     */
    @GET
    @Path("/driver/{driverID}/trip")
    public Response getLastTrip(@PathParam("driverID") Integer driverID);
    
    /**
     * Service to get the trips of given driver that has been performed between a given date and today.
     * @param driverID the Driver ID
     * @param date the startDate of the interval to seach. This date must be max 30 days before today. 
     * @returnWrapped List<Trip> the driver trips performed between a given date and today
     * @HTTP HTTP 200 - OK if trips performed between a given date and today were found
     * @HTTP HTTP 404 - NOT FOUND if no trips found 
     * @HTTP HTTP 400 - BAD REQUEST if date is more than 30 days back from today 
     */
    @GET
    @Path("/driver/{driverID}/trips/{date}")
    public Response getLastTrips(@PathParam("driverID") Integer driverID, 
                                 @PathParam("date") String date );
    
    /**
     * Service to get the trips of given driver that has been performed between 30 days ago and today.
     * @param driverID the Driver ID
     * @returnWrapped List<Trip> the driver trips performed between 30 days ago and today
     * @HTTP HTTP 200 - OK if trips performed between 30 days and today were found
     * @HTTP HTTP 404 - NOT FOUND if no trips found 
     */
    @GET
    @Path("/driver/{driverID}/trips")
    public Response getLastTrips(@PathParam("driverID") Integer driverID);
    
    /**
     * Service to get the last location of given driverID.
     * @param driverID the Driver ID
     * @returnWrapped LastLocation the last driver location
     * @HTTP HTTP 200 - OK if last location found
     * @HTTP HTTP 404 - NOT FOUND if no location found 
     */
    @GET
    @Path("/driver/{driverID}/location")
    public Response getLastLocation(@PathParam("driverID") Integer driverID);
    
   

}
