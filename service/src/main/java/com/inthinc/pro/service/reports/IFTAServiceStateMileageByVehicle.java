package com.inthinc.pro.service.reports;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for IFTA/DOT Reports Services.
 */
@Produces("application/xml")
@Path("/group/{groupID}/report/ifta")
public interface IFTAServiceStateMileageByVehicle {
    String DATE_FORMAT = "yyyyMMdd";

    // ----------------------------------------------------------------------
    // State Mileage By Vehicle
    /**
     * Service for State Mileage By Vehicle Report with only the group as a parameter.
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval only.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval and IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
}
