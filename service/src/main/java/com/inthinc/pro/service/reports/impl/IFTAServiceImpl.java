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

import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.IFTAService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

@Component
public class IFTAServiceImpl implements IFTAService {
    
    private final static Integer DAYS_BACK = -6;

    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    
    @Autowired ReportsFacade reportsFacade;

    @Autowired
    public IFTAServiceImpl(ReportsFacade reportsFacade){
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
        return SIMPLE_DATE_FORMAT;
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyStatus(Integer groupID, boolean dotOnly) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        today.set(Calendar.MINUTE, 0);                 // set minute in hour
        today.set(Calendar.SECOND, 0);                 // set second in minute
        today.set(Calendar.MILLISECOND, 0);            // set millis in second

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        startDate.set(Calendar.MINUTE, 0);                 // set minute in hour
        startDate.set(Calendar.SECOND, 0);                 // set second in minute
        startDate.set(Calendar.MILLISECOND, 0);            // set millis in second
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatus(groupID, startDate.getTime() , today.getTime() , dotOnly);
    }
    
    public void setFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;   
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyDates(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatus(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusOnlyGroup(Integer groupID) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        today.set(Calendar.MINUTE, 0);                 // set minute in hour
        today.set(Calendar.SECOND, 0);                 // set second in minute
        today.set(Calendar.MILLISECOND, 0);            // set millis in second

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        startDate.set(Calendar.MINUTE, 0);                 // set minute in hour
        startDate.set(Calendar.SECOND, 0);                 // set second in minute
        startDate.set(Calendar.MILLISECOND, 0);            // set millis in second
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatus(groupID, startDate.getTime() , today.getTime() , false);
    }



}
