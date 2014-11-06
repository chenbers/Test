package com.inthinc.pro.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Service for vehicles that uses the user defined vehicle id (name) to get vehicle data (not the internal database id).
 */
@Path("/")
@Produces({"application/xml", "application/json", "application/fastinfoset"})
public interface VehicleServiceExt {

    /**
     * Gets all vehicles.
     *
     * @return A {@link java.util.List} of type {@link com.inthinc.pro.model.Vehicle}
     */
    @GET
    @Path("/vehiclesext")
    public Response getAll();

    /**
     * Gets a vehicle by it's user defined id (name).
     *
     * @param name user defined name of the vehicle
     * @return a {@link com.inthinc.pro.model.Vehicle}
     */
    @GET
    @Path("/vehicleext/{name}")
    public Response get(@PathParam("name") String name);

    /**
     * Gets a vehicle and additional last trip date by the vehicle's user defined id (name).
     *
     * @param name user defined name of the vehicle
     * @return a {@link com.inthinc.pro.model.VehicleTripView}
     */
    @GET
    @Path("/vehicleext/{name}/lasttrip")
    public Response getVehicleAndLastTripDate(@PathParam("name") String name);

    /**
     * Gets a vehicle by it's VIN.
     *
     * @param vin vehicle's vin
     * @return a {@link com.inthinc.pro.model.Vehicle}
     */
    @GET
    @Path("/vehicleext/vin/{vin}")
    public Response findByVIN(@PathParam("vin") String vin);

    /**
     * Gets the score of a vehicle given by name, for a given number of days.
     *
     * @param name vehicle name
     * @param numOfDays number of days
     * @return a {@link com.inthinc.pro.model.Vehicle}
     */
    @GET
    @Path("/vehicleext/{name}/score/{numberOfDays}")
    public Response getScore(@PathParam("name") String name, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    /**
     * Gets the trend of a vehicle given by name, for a given number of days.
     *
     * @param name vehicle name
     * @param numOfDays number of days
     * @return a {@link com.inthinc.pro.model.aggregation.Trend}
     */
    @GET
    @Path("/vehicleext/{name}/trend/{numberOfDays}")
    public Response getTrend(@PathParam("name") String name, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    /**
     * Gets the trips for a vehicle given by name, for a given date.
     *
     * @param name vehicle name
     * @param date date
     * @return list of {@link com.inthinc.pro.model.Trip}
     */
    @GET
    @Path("/vehicleext/{name}/trips/{date}")
    // DATE is in YYYYMMDD format
    public Response getTrips(@PathParam("name") String name, @PathParam("date") String date);

    /**
     * Gets the trips for a vehicle given by name.
     *
     * @param name vehicle name
     * @return list of {@link com.inthinc.pro.model.Trip}
     */
    @GET
    @Path("/vehicleext/{name}/trips")
    public Response getTrips(@PathParam("name") String name);

    /**
     * Gets the last trip of a vehicle given by name.
     *
     * @param name vehicle name
     * @return a {@link com.inthinc.pro.model.Trip}
     */
    @GET
    @Path("/vehicleext/{name}/trip")
    Response getLastTrip(@PathParam("name") String name);

    /**
     * Gets the trips for a vehicle given by name, for a given start and end date.
     *
     * @param name vehicle name
     * @param fromDateTime start date time
     * @param toDateTime start date time
     * @return list of {@link com.inthinc.pro.model.Trip}
     */
    @GET
    @Path("/vehicleext/{name}/trips/{fromDateTime}/{toDateTime}")
    Response getTrips(@PathParam("name") String name,
                      @PathParam("fromDateTime") String fromDateTime,
                      @PathParam("toDateTime") String toDateTime);

    /**
     * Gets the last location of a vehicle given by name.
     *
     * @param name vehicle name
     * @return a {@link com.inthinc.pro.model.LastLocation}
     */
    @GET
    @Path("/vehicleext/{name}/lastlocation")
    public Response getLastLocation(@PathParam("name") String name);

    /**
     * Gets the last location (with more detailed information) of a vehicle given by name.
     *
     * @param name vehicle name
     * @return a {@link com.inthinc.pro.model.LastLocation}
     */
    @GET
    @Path("/vehicleext/{name}/lastLocationExtraInfo")
    public Response getLastLocationExtraInfo(@PathParam("name") String name);
}
