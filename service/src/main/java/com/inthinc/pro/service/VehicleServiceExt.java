package com.inthinc.pro.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
@Produces({"application/xml", "application/json", "application/fastinfoset"})
public interface VehicleServiceExt {

    @GET
    @Path("/vehiclesext")
    public Response getAll();

    @GET
    @Path("/vehicleext/{name}")
    public Response get(@PathParam("name") String name);

    @GET
    @Path("/vehicleext/vin/{vin}")
    public Response findByVIN(@PathParam("vin") String vin);

    @GET
    @Path("/vehicleext/{name}/score/{numberOfDays}")
    public Response getScore(@PathParam("name") String name, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    @GET
    @Path("/vehicleext/{name}/trend/{numberOfDays}")
    public Response getTrend(@PathParam("name") String name, @PathParam("numberOfDays") @DefaultValue("30") Integer numOfDays);

    @GET
    @Path("/vehicleext/{name}/trips/{date}")
    // DATE is in YYYYMMDD format
    public Response getTrips(@PathParam("name") String name, @PathParam("date") String date);

    @GET
    @Path("/vehicleext/{name}/trips")
    public Response getTrips(@PathParam("name") String name);

    @GET
    @Path("/vehicleext/{name}/trip")
    Response getLastTrip(@PathParam("name") String name);

    @GET
    @Path("/vehicleext/{name}/trips/{fromDateTime}/{toDateTime}")
    Response getTrips(@PathParam("name") String name,
                      @PathParam("fromDateTime") String fromDateTime,
                      @PathParam("toDateTime") String toDateTime);

    @GET
    @Path("/vehicleext/{name}/lastlocation")
    public Response getLastLocation(@PathParam("name") String name);

    @GET
    @Path("/vehicleext/{name}/lastLocationExtraInfo")
    public Response getLastLocationExtraInfo(@PathParam("name") String name);
}
