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
    
    @GET
    @Path("/driver/{driverID}/trip")
    public Response getLastTrip(@PathParam("driverID") Integer driverID);
    
    //trips that have occurred from date until today. Can only go 30 days back
    @GET
    @Path("/driver/{driverID}/trips/{date}")
    public Response getLastTrips(@PathParam("driverID") Integer driverID, @PathParam("date") String date );
    
    @GET
    @Path("/driver/{driverID}/trips")
    public Response getLastTrips(@PathParam("driverID") Integer driverID);
    
    @GET
    @Path(" /driver/{driverID}/location")
    public Response getLastLocation(@PathParam("driverID") Integer driverID);
    
   

}
