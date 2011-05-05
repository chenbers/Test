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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Address;

@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
@Consumes("application/xml")
public interface AddressService extends GenericService<Address> {

    @GET
    @Path("/addresses")
    public Response getAll();

    @GET
    @Path("/address/{addressID}")
    @Override
    public Response get(@PathParam("addressID") Integer addressID);

    @POST
    @Consumes("application/xml")
    @Path("/address")
    @Override
    public Response create(Address address, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/address")
    @Override
    public Response update(Address address);

    @DELETE
    @Consumes("text/plain")
    @Path("/address/{id}")
    public Response delete(@PathParam("id") Integer id);

    @POST
    @Consumes("application/xml")
    @Path("/addresses")
    public Response create(List<Address> addresses, @Context UriInfo uriInfo);

    @PUT
    @Consumes("application/xml")
    @Path("/addresses")
    public Response update(List<Address> addresses);

    @DELETE
    @Consumes("application/xml")
    @Path("/addresses")
    public Response delete(List<Integer> addressIDs);

}
