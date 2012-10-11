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
 * Interface for IFTA/DOT Reports Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceStateMileageFuelByVehicle {
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *         
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleDefaults(
            @PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithDates(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *          
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIfta(
            @PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupID
     *            the Group ID
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaAndDates(
            @PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList 
     * It is possible to specify a list of groups in the request body using the following XML format:<p>
     * <p>
     * <code>
     *   &lt;groupList&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;1&lt;/groupID&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;2&lt;/groupID&gt;</br>
     *   ...</br>
     *   &lt;/groupList&gt;</br>
     * </code>  
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/fuelConsumption")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageFuelByVehicleDefaultsMultiGroup(
            GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList 
     * It is possible to specify a list of groups in the request body using the following XML format:<p>
     * <p>
     * <code>
     *   &lt;groupList&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;1&lt;/groupID&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;2&lt;/groupID&gt;</br>
     *   ...</br>
     *   &lt;/groupList&gt;</br>
     * </code>  
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/fuelConsumption/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithDatesMultiGroup(
            GroupList groupList,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList 
     * It is possible to specify a list of groups in the request body using the following XML format:<p>
     * <p>
     * <code>
     *   &lt;groupList&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;1&lt;/groupID&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;2&lt;/groupID&gt;</br>
     *   ...</br>
     *   &lt;/groupList&gt;</br>
     * </code>  
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaMultiGroup(
            GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage fuel by vehicle Report with an explicit Interval.
     * 
     * @param groupList 
     * It is possible to specify a list of groups in the request body using the following XML format:<p>
     * <p>
     * <code>
     *   &lt;groupList&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;1&lt;/groupID&gt;</br>
     *   &nbsp;&nbsp;&lt;groupID&gt;2&lt;/groupID&gt;</br>
     *   ...</br>
     *   &lt;/groupList&gt;</br>
     * </code>  
     * @param startDate
     *            the start date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param endDate
     *            the end date in format {@value com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#SIMPLE_DATE_FORMAT}
     * @param iftaOnly
     *            the DOT indicator. If set to true, only DOT data will be returned. Defaulted to false.
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any StateMileageFuelByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageFuelByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/fuelConsumption/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(
            GroupList groupList,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
}
