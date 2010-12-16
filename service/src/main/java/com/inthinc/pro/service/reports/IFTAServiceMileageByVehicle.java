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
 * Interface for IFTA/DOT MileageByVehicle Report Services.
 */
@Produces("application/xml")
@Path("/group/{groupID}/report/ifta/mileage")
public interface IFTAServiceMileageByVehicle {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval and iftaOnly flag.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the desired user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the desired user measurementType from Query string, ex: measurementType=METRIC
     *            
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval only.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param startDate
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
     * @param locale 
     *            the desired user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the desired user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for Mileage By Vehicle Report with an explicit Interval. Service for Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the desired user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the desired user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for Mileage By Vehicle Report without any other params.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     *            the desired user locale from Query string, ex: locale=fr_CA
     * @param measurementType 
     *            the desired user measurementType from Query string, ex: measurementType=METRIC
     * 
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     */
    @GET
    @Path("/")
    @Produces("application/xml")
    Response getMileageByVehicleDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    // TODO: For Validation Testing. To be removed
    @GET
    @Path("/validation")
    @Produces("application/xml")
    Response getMileageByVehicleDefaultsValidationTest(@PathParam("groupID") Integer groupID,
    		@QueryParam("locale") Locale locale, @QueryParam("measurementType") MeasurementType measurementType);    

    @GET
    @Path("/validation/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithDatesValidationTest(@PathParam("groupID") Integer groupID, @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
    		@QueryParam("locale") Locale locale, @QueryParam("measurementType") MeasurementType measurementType);            
    
    
}
