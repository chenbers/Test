package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

    @POST
    @Consumes("application/xml")
    @Path("/driver")
    public Response create(Driver driver);

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
    public Response create(List<Driver> drivers);

    @PUT
    @Consumes("application/xml")
    @Path("/drivers")
    public Response update(List<Driver> drivers);

    @DELETE
    @Consumes("application/xml")
    @Path("/drivers")
    public Response delete(List<Integer> vehicleIDs);

}
