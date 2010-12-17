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
 * Interface for IFTA/DOT State Mileage By Vehicle Report Services.
 */
public interface IFTAServiceStateMileageByVehicle {
    String DATE_FORMAT = "yyyyMMdd";

    // ----------------------------------------------------------------------
    // State Mileage By Vehicle
    /**
     * Service for State Mileage By Vehicle Report with only the group as a parameter.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage")
    @Produces("application/xml")
    Response getStateMileageByVehicleDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval only.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage By Vehicle Report with an explicit Interval and IFTA flag.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
}
