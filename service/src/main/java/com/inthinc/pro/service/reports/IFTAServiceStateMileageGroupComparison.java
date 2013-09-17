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
 * Interface for IFTA/DOT Group Comparison by State-Province Report Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceStateMileageGroupComparison {
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

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
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateComparison/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & given dates.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @POST
    @Path("/groups/report/ifta/stateComparison/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(
            GroupList groupList, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & default dates.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */ 
    @GET
    @Path("/group/{groupID}/report/ifta/stateComparison/iftaOnly")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & IFTA only & default dates.
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
     * 
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @POST
    @Path("/groups/report/ifta/stateComparison/iftaOnly")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageByVehicleStateComparisonWithIftaMultiGroup(
            GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & default ifta (false) & default dates
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateComparison")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonDefaults(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    
    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & default ifta (false) & default dates
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
     * 
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @POST
    @Path("/groups/report/ifta/stateComparison")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageByVehicleStateComparisonDefaultsMultiGroup(
            GroupList groupList,
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
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @param measurementType 
     *            Overrides the Measurement Type associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>  
     *
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @GET
    @Path("/group/{groupID}/report/ifta/stateComparison/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getStateMileageByVehicleStateComparisonWithDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for State mileage by vehicle / Group Comparison by State-Province Report with given group & given dates & default ifta (false).
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
     * 
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup> the list of beans
     * @HTTP HTTP 200 - OK if valid request - can return empty list
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @POST
    @Path("/groups/report/ifta/stateComparison/{startDate}/{endDate}")
    @Produces("application/xml")
    @Consumes("application/xml")
    Response getStateMileageByVehicleStateComparisonWithDatesMultiGroup(
            GroupList groupList, 
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

}
