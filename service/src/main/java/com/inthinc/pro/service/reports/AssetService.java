package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for Asset Reports Services.
 */
@Path("/group/{groupID}")
public interface AssetService {

    @GET
    @Path("/redflags")
    public Response getRedFlagCount(@PathParam("groupID") Integer groupID);

    @GET
    @Path("/redflags/{startDate}/{endDate}/{optionalParams}")
    public Response getRedFlagCount(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate, @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate,
            @PathParam("optionalParams") PathSegment optionalParams);
}
