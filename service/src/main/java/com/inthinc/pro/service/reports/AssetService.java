package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for Asset Reports Services.
 */
@Path("/group/{groupID}")
public interface AssetService {

    /**
     * TODO Complete javadoc.
     * 
     * @param groupID
     * @return
     */
    @GET
    @Path("/redflags")
    public Response getRedFlags(@PathParam("groupID") Integer groupID);

    /**
     * TODO Complete javadoc.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/redflags/{startDate}/{endDate}")
    public Response getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate, @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);
}
