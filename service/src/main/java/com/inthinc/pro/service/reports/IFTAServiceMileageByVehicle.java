package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.Locale;

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
 * Interface for IFTA/DOT MileageByVehicle Report Services.
 */
@Produces("application/xml")
@Path("")
public interface IFTAServiceMileageByVehicle {
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for Mileage By Vehicle Report without any other params.
     * 
     * @param groupID
     *            the Group ID as Path Parameter
     * @param locale 
     * A <a href="http://download.oracle.com/javase/6/docs/api/java/util/Locale.html">Locale</a> object represents a specific geographical, political, or cultural region.</br>
     * By default, the system uses the locale associated with the authenticated user.
     * The optional <code>locale</code> query string parameter allows overriding this default.
     * <p>
     * Example:<code>http://server:8080/service?locale=fr_CA </code></br>
     * <p>
     * The value of the parameter must be a string in the following format:<br/>
     * <code>
     * {ISO 639 language code}_{ISO 3166 region code}_{variant} 
     * </code>
     * <ul>
     * <li><strong>ISO 639 language code:</strong> Lower-case, two-letter codes as defined by <a href="http://www.loc.gov/standards/iso639-2/php/English_list.php">ISO-639<a></li>
     * <li><strong>ISO 3166 region code:</strong> Upper-case, two-letter codes as defined by <a href="http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html">ISO-3166</a></li>
     * <li><strong>Variant:</strong> This is an optional element, representing a vendor or browser-specific code. For example, use WIN for Windows, MAC for Macintosh, and POSIX for POSIX.</li>
     * </ul></br>
     * Examples: "fr_CA", "de_DE", "en_US_WIN"</br>
     * <p>
     * If the string does not follow the specified format, an HTTP 400 (Bad Request) will be returned.
     *  
     * @param measurementType 
     * The Measurement Type defines the Unit of Measure to be used and the valid fuel efficiency types.</br>
     * By default, the system uses the measurement type associated with the authenticated user.
     * The optional <code> measurementType </code> query string parameter allows overriding this default.
     * <p>
     * Example:</br> <code>http://server:8080/service?measurementType=METRIC</code></br>
     * <p>
     * The value of the parameter must be a value from the {@link com.inthinc.pro.model.MeasurementType} enumeration. 
     * Currently two settings are supported: <code> METRIC </code> and <code> ENGLISH </code>.
     * <p>
     * If the string does not correspond to a {@link com.inthinc.pro.model.MeasurementType} element, an HTTP 400 (Bad Request) will be returned. 
     *  
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid
     */
    @GET
    @Path("/group/{groupID}/report/ifta/mileage")
    @Produces("application/xml")
    Response getMileageByVehicleDefaults(@PathParam("groupID") Integer groupID,
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
    @Path("/group/{groupID}/report/ifta/mileage/{startDate}/{endDate}")
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
    @Path("/group/{groupID}/report/ifta/mileage/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleWithIfta(@PathParam("groupID") Integer groupID,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);

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
    @Path("/group/{groupID}/report/ifta/mileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithIftaAndDates(@PathParam("groupID") Integer groupID, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    /**
     * Service for Mileage By Vehicle Report without any other params.
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
    @Path("/groups/report/ifta/mileage")
    @Produces("application/xml")
    Response getMileageByVehicleDefaultsMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval only.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid  
     */
    @POST
    @Path("/groups/report/ifta/mileage/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithDatesMultiGroup(GroupList groupList, 
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval. Service for Mileage By Vehicle Report without Interval but with IFTA flag.
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
    @Path("/groups/report/ifta/mileage/iftaOnly")
    @Produces("application/xml")
    Response getMileageByVehicleWithIftaMultiGroup(GroupList groupList,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    /**
     * Service for Mileage By Vehicle Report with an explicit Interval and iftaOnly flag.
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
     * @returnWrapped java.util.List<com.inthinc.pro.reports.ifta.model.MileageByVehicle> the list of MileageByVehicle
     * @HTTP HTTP 200 - OK if any MileageByVehicle found
     * @HTTP HTTP 404 - NOT FOUND if no MileageByVehicle found
     * @HTTP HTTP 400 - BAD REQUEST if locale or measurement type are invalid  
     */
    @POST
    @Path("/groups/report/ifta/mileage/iftaOnly/{startDate}/{endDate}")
    @Produces("application/xml")
    Response getMileageByVehicleWithIftaAndDatesMultiGroup(GroupList groupList,
            @PathParam("startDate") @DateFormat(DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(DATE_FORMAT) Date endDate,
            @QueryParam("locale") Locale locale,
            @QueryParam("measurementType") MeasurementType measurementType);
    
    
}
