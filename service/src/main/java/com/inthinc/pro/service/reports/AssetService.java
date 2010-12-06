package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
     * @returnWrapped {@link Integer} The amount of redflags the group has for the current day.
     * @HTTP HTTP 200 OK
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
     * @returnWrapped {@link Integer} The amount of redflags the group has from the specified date to the current day.
     * @HTTP HTTP 200 OK
     * @HTTP HTTP 400 If start date is greater than today's date or if the date is not in the yyyyMMdd format.
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
     * @returnWrapped {@link Integer} The amount of redflags the group has from the specified start date to the end date.
     * @HTTP HTTP 200 OK
     * @HTTP HTTP 400 If start date is greater than end date or if the date is not in the yyyyMMdd format.
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
     * @returnWrapped {@link List} A list of red flags for the specified group, matching the filter criteria.
     * @HTTP HTTP 200 OK
     * @HTTP HTTP 400 If first record is greater than last record.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}")
    @Produces("application/xml")
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
     * @returnWrapped {@link List} A list of red flags for the specified group, matching the filter criteria.
     * @HTTP HTTP 200 OK
     * @HTTP HTTP 400 If start date is greater than today or if the date is not in the yyyyMMdd format.
     * @HTTP HTTP 400 If first record is greater than last record.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}")
    @Produces("application/xml")
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
     * @returnWrapped {@link List} A list of red flags for the specified group, matching the filter criteria.
     * @HTTP HTTP 200 OK
     * @HTTP HTTP 400 If start date is greater than end date or if the date is not in the yyyyMMdd format.
     * @HTTP HTTP 400 If first record is greater than last record.
     */
    @GET
    @Path("/redflags/{firstRecord}/{lastRecord}/{startDate}/{endDate}")
    @Produces("application/xml")
    public Response getRedFlags(@PathParam("groupID") Integer groupID, @PathParam("firstRecord") Integer firstRecord, @PathParam("lastRecord") Integer lastRecord,
            @PathParam("startDate") @DateFormat("yyyyMMdd") Date startDate, @PathParam("endDate") @DateFormat("yyyyMMdd") Date endDate);

}
