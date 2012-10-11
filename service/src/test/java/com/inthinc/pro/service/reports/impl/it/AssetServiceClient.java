package com.inthinc.pro.service.reports.impl.it;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.client.ClientResponse;

import com.inthinc.pro.model.RedFlag;

/**
 * Interface for Asset Reports Services.
 */
@Path("/group/{groupID}")
public interface AssetServiceClient {

    @GET
    @Path("/redflags/count")
    public ClientResponse<Integer> getRedFlagCount(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/redflags/count/{startDate}")
    public ClientResponse<Integer> getRedFlagCount(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate);

    @GET
    @Path("/redflags/count/{startDate}/{endDate}")
    public ClientResponse<Integer> getRedFlagCount(@PathParam("groupID") Integer groupID, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate);

    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}")
    public ClientResponse<List<RedFlag>> getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord);

    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}")
    public ClientResponse<List<RedFlag>> getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord,
            @PathParam("startDate") String startDate);

    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}/{endDate}")
    public ClientResponse<List<RedFlag>> getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord,
            @PathParam("startDate") String startDate, @PathParam("endDate") String endDate);

}
