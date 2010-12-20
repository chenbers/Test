package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.annotations.DateFormat;
import com.inthinc.pro.util.GroupList;

/**
 * Interface for IFTA/DOT State Mileage by Vehicle / Road Status Report Services.
 */
@Produces("application/xml")
public interface IFTAServiceStateMileageByVehicleRoadStatus {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State Mileage by Vehicle / Road Status Report with the explicit Interval and IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/iftaOnly/{startDate}/{endDate}")
    Response getStateMileageRoadStatusWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with the explicit Interval and IFTA flag.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/iftaOnly/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageRoadStatusWithIftaAndDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with only IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/iftaOnly")
    Response getStateMileageRoadStatusWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with only IFTA flag.
     * 
     * @param groupList
     *            the Group ID List
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/iftaOnly")
    @Consumes("application/xml")
    Response getStateMileageRoadStatusWithIftaMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with default Interval and IFTA flag.
     * 
     * @param groupID
     *            the Group ID
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus")
    Response getStateMileageRoadStatusDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with default Interval and IFTA flag.
     * 
     * @param groupList
     *            the Group ID List
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus")
    @Consumes("application/xml")
    Response getStateMileageRoadStatusDefaultsMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @GET
    @Path("/group/{groupID}/report/ifta/roadStatus/{startDate}/{endDate}")
    Response getStateMileageRoadStatusWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Road Status Report with an explicit Interval.
     * 
     * @param groupList
     *            the Group ID List
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the required user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the required user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found
     */
    @POST
    @Path("/groups/report/ifta/roadStatus/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageRoadStatusWithDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
}
