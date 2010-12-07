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

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.reports.IFTAService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;


@Component
public class IFTAServiceImpl implements IFTAService {
    
    private final static Integer DAYS_BACK = -6;

    @Autowired ReportsFacade reportsFacade;

    @Autowired
    public IFTAServiceImpl(ReportsFacade reportsFacade){
        this.reportsFacade = reportsFacade;
    }
    
    public void setFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;   
    }
    
    @Override
    public Response getStateMileageByVehicleRoadStatus(Integer groupID, Date startDate, Date endDate, boolean dotOnly) {
        if(invalidParameters(groupID, startDate, endDate, dotOnly)) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        List<StateMileageByVehicleRoadStatus> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleRoadStatus(groupID, interval, dotOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageByVehicleRoadStatus>>(list) {}).build();
        
        return Response.status(Status.NOT_FOUND).build();
    }

    private boolean invalidParameters(Integer groupID, Date startDate, Date endDate, boolean ifta) {
        boolean res = false;

        if( groupID == null || startDate == null || endDate == null ){
            res = true;
        }
        else if(endDate.before(startDate)) {
            res = true;
        }
        return res;
    }

    public static String getSimpleDateFormat() {
        return IFTAService.DATE_FORMAT;
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyStatus(Integer groupID, boolean dotOnly) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatus(groupID, startDate.getTime() , today.getTime() , dotOnly);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatus(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyGroup(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatus(groupID, startDate.getTime() , today.getTime() , false);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicle(java.lang.Integer, java.util.Date, java.util.Date, java.lang.Boolean)
     */
    @Override
    public Response getMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly) {
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getMileageByVehicleReportCriteria(groupID, interval, iftaOnly);
        } catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }



    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithInterval(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getMileageByVehicleWithInterval(Integer groupID, Date startDate, Date endDate) {
        return getMileageByVehicle(groupID, startDate, endDate, false);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithFlag(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleWithFlag(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), true);
    }
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithoutParam(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleWithoutParam(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), false);
    }

    /*
     * Create the Date for today and set it to midnight.
     * @return
     */
    private Calendar getMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        today.set(Calendar.MINUTE, 0);                 // set minute in hour
        today.set(Calendar.SECOND, 0);                 // set second in minute
        today.set(Calendar.MILLISECOND, 0);            // set millis in second
        return today;
    }

    @Override
    public Response getStateMileageByVehicleStateComparaison(Integer groupID, Date startDate, Date endDate, boolean dotOnly) {
        if(invalidParameters(groupID, startDate, endDate, dotOnly)) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        List<StateMileageCompareByGroup> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleStateComparaison(groupID, interval, dotOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageCompareByGroup>>(list) {}).build();
        
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getStateMileageByVehicleStateComparaisonOnlyDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleStateComparaison(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleStateComparaisonOnlyGroup(Integer groupID) {
        Calendar today = getMidnight(); 
        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleStateComparaison(groupID, startDate.getTime() , today.getTime() , false);
    }

    @Override
    public Response getStateMileageByVehicleStateComparaisonOnlyStatus(Integer groupID, boolean dotOnly) {
        Calendar today = getMidnight(); 
        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleStateComparaison(groupID, startDate.getTime() , today.getTime() , dotOnly);

    }
}
