package com.inthinc.pro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Produces("application/xml")
@Consumes("application/xml")
public interface GenericService<T> {

    @Context
    @GET
    public void setUriInfo(UriInfo uriInfo);

    @GET
    public Response get(Integer id);

    @GET
    public Response getAll();

    @POST
    public Response create(T object);

    @PUT
    public Response update(T object);

    @DELETE
    public Response delete(Integer id);

    @POST
    public Response create(List<T> list);

    @PUT
    public Response update(List<T> list);

    @DELETE
    public Response delete(List<Integer> list);
}
