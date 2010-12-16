package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.annotations.DateFormat;

/**
 * Interface for IFTA/DOT Group Comparison by State-Province Report Services.
 */
@Produces("application/xml")
@Path("/group/{groupID}/report/ifta/stateComparison")
public interface IFTAServiceStateMileageByVehicleGroupComparison {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & given dates.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & default dates.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & default ifta (false) & default dates
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & given dates & default ifta (false).
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageCompareByGroup found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageCompareByGroup found
     */
    @GET
    @Path("/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

}
