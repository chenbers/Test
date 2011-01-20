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
 * Interface for IFTA/DOT State Mileage by Vehicle / Month Report Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceStateMileageByMonth {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State Mileage by Vehicle / Month Report with an explicit Interval.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/iftaOnly/{startDate}/{endDate}")
    Response getStateMileageByVehicleByMonthWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State Mileage by Vehicle / Month Report with an explicit Interval.
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
    @Path("/groups/report/ifta/monthMileage/iftaOnly/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Month Report with IFTA flag.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/iftaOnly")
    Response getStateMileageByVehicleByMonthWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State Mileage by Vehicle / Month Report with IFTA flag.
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
    @Path("/groups/report/ifta/monthMileage/iftaOnly")
    @Consumes("application/xml")
    Response getStateMileageByVehicleByMonthWithIftaMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);


    /**
     * Service for State Mileage by Vehicle / Month Report with default Date range.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of beans
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage")
    Response getStateMileageByVehicleByMonthDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State Mileage by Vehicle / Month Report with default Date range.
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
    @Path("/groups/report/ifta/monthMileage")
    @Consumes("application/xml")
    Response getStateMileageByVehicleByMonthDefaultsMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State Mileage by Vehicle / Month Report with explicit Date range.
     * 
     * @param groupID
     *            the Group ID
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
    @GET
    @Path("/group/{groupID}/report/ifta/monthMileage/{startDate}/{endDate}")
    Response getStateMileageByVehicleByMonthWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State Mileage by Vehicle / Month Report with explicit Date range.
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
    @Path("/groups/report/ifta/monthMileage/{startDate}/{endDate}")
    @Consumes("application/xml")
    Response getStateMileageByVehicleByMonthWithDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

}
