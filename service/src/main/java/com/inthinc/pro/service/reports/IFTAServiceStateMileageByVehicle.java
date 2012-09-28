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
 * Interface for IFTA/DOT State Mileage By Vehicle Report Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceStateMileageByVehicle {
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    // Single Group
    /**
     * Service for State Mileage By Vehicle Report with only the group as a parameter.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage")
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
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/{startDate}/{endDate}")
    Response getStateMileageByVehicleWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly")
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
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly/{startDate}/{endDate}")
    Response getStateMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    // ----------------------------------------------------------------------
    // Multiple Groups
    /**
     * Service for State Mileage by Vehicle Report with default Date range.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/stateMileage")
    @Consumes("application/xml")
    Response getStateMileageByVehicleDefaultsMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle Report with explicit Date range.
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
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/stateMileage/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageByVehicleWithDatesMultiGroup(GroupList groupList,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage By Vehicle Report without Interval but with IFTA flag.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/group/{groupID}/report/ifta/stateMileage/iftaOnly")
    @Consumes("application/xml")
    Response getStateMileageByVehicleWithIftaMultiGroup(GroupList groupList, @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle Report with an explicit Interval and IFTA flag.
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
     *            the start date in format "yyyyMMdd" as Path Parameter
     * @param endDate
     *            the end date in format "yyyyMMdd" as Path Parameter
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/stateMileage/iftaOnly/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
}
