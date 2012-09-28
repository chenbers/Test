package com.inthinc.pro.service.reports;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.inthinc.pro.service.annotations.DateFormat;

@Path("/group/{groupID}/hos")
@Produces("application/xml")
public interface HOSService {
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";

  
    /**
     * Service for HOS Violations Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date in format "YYYYMMDD"
     * @param endDate the end date in format "YYYYMMDD"
     * @param locale  
     *            Overrides the Locale associated with the authenticated user. </br>
     *            Example:</br> <code>http://server:8080/service?locale=fr_CA </code></br>
     *            More information in the <a href="javascript:{var apiUrl = document.URL.substring(0,document.URL.indexOf('jaxrsdocs')) + 'jaxrsdocs/group/{groupID}/report/ifta/mileage/index.html'; window.location = apiUrl;}">mileage Web Service documentation</a></br><p>   
     * @returnWrapped java.util.List<com.inthinc.pro.reports.hos.model.ViolationDetails> the list of violations
     * @HTTP HTTP 200 - OK if any record found
     * @HTTP HTTP 404 - NOT FOUND if no record found
     * @HTTP HTTP 400 - BAD REQUEST if locale is invalid
     */ 
    @GET
    @Path("/violations/{startDate}/{endDate}")
    Response getHOSViolationDetails(@PathParam("groupID") Integer groupID,
            @PathParam("startDate") @DateFormat(SIMPLE_DATE_FORMAT) Date startDate,
            @PathParam("endDate") @DateFormat(SIMPLE_DATE_FORMAT) Date endDate, 
            @QueryParam("locale") Locale locale);
    
    

}
