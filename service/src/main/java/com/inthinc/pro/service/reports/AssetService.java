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
     * Gets the group's redflags count for the current day (today).
     * 
     * @param groupID
     *            The id of the group to check for.
     * 
     * @return The amount of redflags the group has for the current day, embedded in a {@link Response} object.
     */
    @GET
    @Path("/redflags/count")
    public Response getRedFlagCount(@PathParam("groupID") Integer groupID);

    /**
     * Gets the group's amount of redflags from the specified date to the current day (today).
     * 
     * @param groupID
     *            The id of the group to check for.
     * @param startDate
     *            The date interval range start.
     * 
     * @return The amount of redflags the group has from the specified date to the current day, embedded in a {@link Response} object.
     */
    @GET
    @Path("/redflags/count/{startDate}")
    public Response getRedFlagCount(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate);

    /**
     * Gets the group's amount of redflags from the specified start date to the end date.
     * 
     * @param groupID
     *            The id of the group to check for.
     * @param startDate
     *            The date interval range start.
     * @param endDate
     *            The date interval range end.
     * 
     * @return The amount of redflags the group has from the specified start date to the end date, embedded in a {@link Response} object.
     */
    @GET
    @Path("/redflags/count/{startDate}/{endDate}")
    public Response getRedFlagCount(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate, @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);

    /**
     * Gets a list of today's red flags for the specified group. Only the records specified in the firstRecord-lastRecord range will be returned.
     * 
     * @param groupID
     *            The id of the group to get the red flags from.
     * @param firstRecord
     *            The record count range start.
     * @param lastRecord
     *            The record count range end.
     * @return A list of red flags for the specified group, respecting the filter criteria.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}")
    public Response getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord);

    /**
     * Gets a list of red flags for the specified group generated from startDate to today. Only the records specified in the firstRecord-lastRecord range will be returned.
     * 
     * @param groupID
     *            The id of the group to get the red flags from.
     * @param firstRecord
     *            The record count range start.
     * @param lastRecord
     *            The record count range end. The date interval range start.
     * @param startDate
     *            The date interval range start.
     * @return A list of red flags for the specified group, respecting the filter criteria.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}")
    public Response getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate);

    /**
     * Gets a list of red flags for the specified group generated from startDate to endDate. Only the records specified in the firstRecord-lastRecord range will be returned.
     * 
     * @param groupID
     *            The id of the group to get the red flags from.
     * @param firstRecord
     *            The record count range start.
     * @param lastRecord
     *            The record count range end. The date interval range start.
     * @param startDate
     *            The date interval range start.
     * @param endDate
     *            The date interval range end.
     * @return Gets a list of red flags for the specified group, respecting the filter criteria.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}/{endDate}")
    public Response getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate, @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);

}
