package com.inthinc.pro.service.reports.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.reports.IFTAService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceImpl implements IFTAService {
    
    private final static Integer DAYS_BACK = -6;

    @Autowired ReportsFacade reportsFacade;
    @Autowired ReportsUtil reportsUtil;

    @Autowired
    public IFTAServiceImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil){
        this.reportsFacade = reportsFacade;
        this.reportsUtil = reportsUtil;
    }
    
    public void setFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade; 
    }
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road Status
    
    
    /**
     * Service to retrieve the StateMileageByVehicleRoadStatus report data beans from the backend via a facade.
     * @param gorupID   The ID of the group
     * @param startDate The start date of the interval
     * @param endDate   The end date of the interval
     * @param iftaOnly  Indicator expressing if only the ifta data should be returned
     * @return javax.ws.rs.core.Response to return the client
     */
    Response getStateMileageByVehicleRoadStatusWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
        
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        
        // Some validation errors found
        if (response != null){
            return response;
        }
        
        // No validation error found
        List<StateMileageByVehicleRoadStatus> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleRoadStatus(groupID, interval, iftaOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        // Some data found
        if(list != null && !list.isEmpty()) {
            return Response.ok(new GenericEntity<List<StateMileageByVehicleRoadStatus>>(list) {}).build();
        }
        // No data found
        else {
            return Response.status(Status.NOT_FOUND).build();
        }          

    }

    public static String getSimpleDateFormat() {
        return IFTAService.DATE_FORMAT;
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusWithIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime() , today.getTime() , true);
    }
    
    @Override
    public Response getStateMileageByVehicleRoadStatusWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate , endDate , true);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusDefaults(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime() , today.getTime() , false);
    }
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Group Comparison by State-Province
    
    Response getStateMileageByVehicleStateComparisonWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
 
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        
        // Some validation errors found
        if (response != null){
            return response;
        }
        
        List<StateMileageCompareByGroup> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleStateComparison(groupID, interval, iftaOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageCompareByGroup>>(list) {}).build();
        
        return Response.status(Status.NOT_FOUND).build();
    }
    
    @Override
    public Response getStateMileageByVehicleStateComparisonWithIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate.getTime() , today.getTime() , true);
    }
    
    @Override
    public Response getStateMileageByVehicleStateComparisonWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate , endDate , true);
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleStateComparisonDefaults(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate.getTime() , today.getTime() , false);
    }

    /* Mileage by Vehicle ------------------------------------------------------------- */
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getMileageByVehicle(groupID, startDate, endDate, true);
    }
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate) {
        return getMileageByVehicle(groupID, startDate, endDate, false);
    }
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithIfta(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleWithIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), true);
    }
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleDefaults(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleDefaults(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), false);
    }

    /** Service implementation for Mileage by Vehicle report */
    Response getMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly) {
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        if (response !=null) return response;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getMileageByVehicle(groupID, interval, iftaOnly);
        } catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }

    /**
     * Create the Date for today and set it to midnight.
     * @return the date as Calendar
     */
    static Calendar getMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        today.set(Calendar.MINUTE, 0);                 // set minute in hour
        today.set(Calendar.SECOND, 0);                 // set second in minute
        today.set(Calendar.MILLISECOND, 0);            // set millis in second
        return today;
    }

    /* State Mileage by Vehicle ------------------------------------------------------------- */

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleDefaults(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByVehicleDefaults(Integer groupID) {
        Calendar today = getMidnight();
        
        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleWithFullParameters(groupID, startDate.getTime(), today.getTime(), false);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getStateMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, false);
    }
    
    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicleWithIfta(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByVehicleWithIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleWithFullParameters(groupID, startDate.getTime(), today.getTime(), true);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByVehicle(java.lang.Integer, java.util.Date, java.util.Date, java.lang.Boolean)
     */
    @Override
    public Response getStateMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, true);
    }

    private Response getStateMileageByVehicleWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
        
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        
        // Some validation errors found
        if (response != null){
            return response;
        }
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getStateMileageByVehicle(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Month
    
    
    /**
     * Service to retrieve the MileageByVehicle report data beans from the backend via a facade.
     * @param gorupID   The ID of the group
     * @param startDate The start date of the interval
     * @param endDate   The end date of the interval
     * @param iftaOnly  Indicator expressing if only the ifta data should be returned
     * @return javax.ws.rs.core.Response to return the client
     */
    Response getStateMileageByVehicleByMonthWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
        
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        
        // Some validation errors found
        if (response != null){
            return response;
        }
        
        // No validation error found
        List<MileageByVehicle> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleByMonth(groupID, interval, iftaOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        // Some data found
        if(list != null && !list.isEmpty()) {
            return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
        }
        // No data found
        else {
            return Response.status(Status.NOT_FOUND).build();
        }          

    }

    @Override
    public Response getStateMileageByVehicleByMonthWithIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate.getTime() , today.getTime() , true);
    }
    
    @Override
    public Response getStateMileageByVehicleByMonthWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate , endDate , true);
    }

    @Override
    public Response getStateMileageByVehicleByMonthWithDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleByMonthDefaults(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate.getTime() , today.getTime() , false);
    }

    public ReportsUtil getReportsUtil() {
        return reportsUtil;
    }

    public void setReportsUtil(ReportsUtil reportsUtil) {
        this.reportsUtil = reportsUtil;
    }
    
    /* State Mileage by Month ------------------------------------------------------------- */

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByMonthDefaults(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByMonthDefaults(Integer groupId) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByMonthWithFullParameters(groupId, startDate.getTime(), today.getTime(), false);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByMonthWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getStateMileageByMonthWithDates(Integer groupId, Date startDate, Date endDate) {
        return getStateMileageByMonthWithFullParameters(groupId, startDate, endDate, false);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByMonthWithIfta(java.lang.Integer)
     */
    @Override
    public Response getStateMileageByMonthWithIfta(Integer groupId) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByMonthWithFullParameters(groupId, startDate.getTime(), today.getTime(), true);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAService#getStateMileageByMonthWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getStateMileageByMonthWithIftaAndDates(Integer groupId, Date startDate, Date endDate) {
        return getStateMileageByMonthWithFullParameters(groupId, startDate, endDate, true);
    }

    private Response getStateMileageByMonthWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {

        Response errorResponse = reportsUtil.checkParameters(groupID, startDate, endDate);

        if (errorResponse != null) {
            return errorResponse;
        }

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        List<MileageByVehicle> list = null;
        
        try {
            list = reportsFacade.getStateMileageByMonth(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }
}
