package com.inthinc.pro.service;

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

import com.inthinc.pro.model.Account;

@Path("/")
@Produces({"application/xml","application/json", "application/fastinfoset"})
public interface AccountService extends GenericService<Account> {

    @GET
    @Path("/account")
    public Response get();

    @GET
    @Path("/account/{accountID}")
    @Override
    public Response get(@PathParam("accountID") Integer id);

    @GET
    @Path("/accounts")
    @Override
    public Response getAll();

    @POST
    @Path("/account")
    @Override
    public Response create(Account account, @Context UriInfo uriInfo);

    @PUT
    @Path("/account")
    @Override
    public Response update(Account account);

    @DELETE
    @Path("/account/{accountID}")
    @Override
    public Response delete(@PathParam("accountID")Integer id);

}
