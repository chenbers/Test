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
public interface IFTAServiceStateMileageByVehicleMonth {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / month Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAService#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/monthMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleByMonthWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);
}
