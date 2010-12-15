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
public interface IFTAServiceStateMileageByVehicleGroupComparison {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & given dates.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaAndDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & default dates.
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIfta(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & default ifta (false) & default dates
     * 
     * @param groupID
     *            the Group ID
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonDefaults(@PathParam("groupID") Integer groupID);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & given dates & default ifta (false).
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#DATE_FORMAT}
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/stateComparison/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithDates(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate);

}
