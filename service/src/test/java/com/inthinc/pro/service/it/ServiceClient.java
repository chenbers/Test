package com.inthinc.pro.service.it;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientResponse;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;

@Path("/service/api")
@Consumes("application/xml")
public interface ServiceClient {
    @GET
    @Path("/accounts")
    public ClientResponse<List<Account>> getAllAccounts();
    
    @POST
    @Path("/account")
    public ClientResponse<Account> create(Account account);
    
    @DELETE
    @Path("/account/{accountID}")
    public ClientResponse<Account> delete(@PathParam("accountID")Integer id);

    @GET
    @Path("/devices")
    public ClientResponse<List<Device>> getAllDevices();
}
