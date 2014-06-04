package com.inthinc.pro.service;

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
@Produces({"application/xml","application/json", "application/fastinfoset", "text/csv"})
public interface EmployeeService {

    @GET
    @Path("/employees")
    Response getAll();

    @GET
    @Path("/employee/{employeeID}")
    Response get(@PathParam("employeeID") String employeeID);

    @GET
    @Path("/employee/{employeeID}/events/speeding")
    Response getSpeedingEvents(@PathParam("employeeID") String employeeID);

    @GET
    @Path("/employee/{employeeID}/score/{numberOfDays}")
    Response getScore(@PathParam("employeeID") String employeeID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    @GET
    @Path("/employee/{employeeID}/score/month/{month}")
    Response getScore(@PathParam("employeeID") String employeeID, @PathParam("month") String month);

    @GET
    @Path("/employee/{employeeID}/score/month")
    Response getScore(@PathParam("employeeID") String employeeID);
    @GET
    @Path("/employee/{employeeID}/trend/{numberOfDays}")
    Response getTrend(@PathParam("employeeID") String employeeID, @PathParam("numberOfDays") @DefaultValue("30") Integer numberOfDays);

    @POST
    @Consumes("application/xml")
    @Path("/employee")
    Response create(Driver driver, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/employee")
    Response update(Driver driver);

    @DELETE
    @Path("/employee/{id}")
    Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/employees")
    Response create(List<Driver> drivers, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/employees")
    Response update(List<Driver> drivers);

    @DELETE
    @Consumes("application/xml")
    @Path("/employees")
    Response delete(List<Integer> vehicleIDs);

    /**
     * Service to get the last trip of given employee.
     * @param employeeID the Employee ID
     * @returnWrapped Trip the last employee trip
     * @HTTP HTTP 200 - OK if last trip found
     * @HTTP HTTP 404 - NOT FOUND if no trip found 
     */
    @GET
    @Path("/employee/{employeeID}/trip")
    Response getLastTrip(@PathParam("employeeID") String employeeID);

    /**
     * Service to get the trips of given employee that has been performed between a given date and today.
     * @param employeeID the Employee ID
     * @param date the startDate of the interval to search. This date must be max 30 days before today. 
     * @returnWrapped List<Trip> the employee trips performed between a given date and today
     * @HTTP HTTP 200 - OK if trips performed between a given date and today were found - can be empty result
     * @HTTP HTTP 400 - BAD REQUEST if date is more than 30 days back from today 
     */
    @GET
    @Path("/employee/{employeeID}/trips/{date}")
    Response getLastTrips(@PathParam("employeeID") String employeeID,
                          @PathParam("date") String date );

    /**
     * Service to get the trips of given employee that has been performed between 30 days ago and today.
     * @param employeeID the Employee ID
     * @returnWrapped List<Trip> the employee trips performed between 30 days ago and today
     * @HTTP HTTP 200 - OK if trips performed between 30 days and today were found - can be empty result
     */
    @GET
    @Path("/employee/{employeeID}/trips")
    Response getLastTrips(@PathParam("employeeID") String employeeID);

    /**
     * Service to get the trips of given employee that has been performed between a given date and today.
     * @param employeeID the Employee ID
     * @param fromDateTime the startDate of the interval to search. This date must be max 30 days before today. 
     * @param toDateTime the endDate of the interval to search. This date must be after the fromDateTime. 
     * @returnWrapped List<Trip> the employee trips performed between a given date and today
     * @HTTP HTTP 200 - OK if trips performed between a given date and today were found - can be empty result
     * @HTTP HTTP 400 - BAD REQUEST if date is more than 30 days back from today 
     */
    @GET
    @Path("/employee/{employeeID}/trips/{fromDateTime}/{toDateTime}")
    Response getTrips(@PathParam("employeeID") String employeeID,
                      @PathParam("fromDateTime") String fromDateTime,
                      @PathParam("toDateTime") String toDateTime);
    /**
     * Service to get the last location of given employeeID.
     * @param employeeID the Employee ID
     * @returnWrapped LastLocation the last employee location
     * @HTTP HTTP 200 - OK if last location found
     * @HTTP HTTP 404 - NOT FOUND if no location found 
     */
    @GET
    @Path("/employee/{employeeID}/location")
    Response getLastLocation(@PathParam("employeeID") String employeeID);
}
