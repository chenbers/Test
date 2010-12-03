package com.inthinc.pro.service.reports;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Interface for IFTA/DOT Reports Services.
 */
@Produces("{application/xml}")
@Path("/group/{groupID}/report/IFTA")
public interface IFTAService {
    
    String DATE_FORMAT = "yyyyMMdd";

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date 
     * @param endDate the end date
     * @param dotOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped List<StateMileageByVehicleRoadStatus> the list of StateMileageByVehicleRoadStatus
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/{startDate}/{endDate}/{dotOnly}")
    @Produces("{application/xml")
    Response getStateMileageByVehicleRoadStatus(@PathParam("groupID") Integer groupID,
                                  @PathParam("startDate") String startDate,
                                  @PathParam("endDate") String endDate,
                                  @PathParam("dotOnly")  @DefaultValue("false") boolean dotOnly ); 

    /**
     * Service for State mileage by vehicle / road status Report with an explicit Interval.
     * @param groupID the Group ID
     * @param startDate the start date 
     * @param endDate the end date
     * @param dotOnly the DOT indicator. If set to true, only DOT data will be returned. 
     *                                   Defaulted to false.
     * @returnWrapped List<StateMileageByVehicleRoadStatus> the list of StateMileageByVehicleRoadStatus
     * @HTTP HTTP 200 - OK if any StateMileageByVehicleRoadStatus found
     * @HTTP HTTP 404 - NOT FOUND if no StateMileageByVehicleRoadStatus found 
     */
    @GET
    @Path("/roadStatus/{dotOnly}")
    @Produces("{application/xml")
    Response getStateMileageByVehicleRoadStatusDefaultRange(@PathParam("groupID") Integer groupID,
                                                            @PathParam("dotOnly")  @DefaultValue("false") boolean dotOnly );
    
    

}
