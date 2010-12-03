package com.inthinc.pro.service.reports.impl;

import java.text.DateFormat;
import com.inthinc.pro.dao.StateMileageDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.AssetService;
import com.inthinc.pro.service.reports.IFTAService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

@Component
public class IFTAServiceImpl implements IFTAService {
    
    /**
     * @deprecated Use {@link ReportsFacade#DAYS_BACK} instead
     */
    private final static int DAYS_BACK = ReportsFacade.DAYS_BACK;

    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    
    @Autowired ReportsFacade reportsFacade;

    @Autowired
    public IFTAServiceImpl(ReportsFacade reportsFacade){
        this.reportsFacade = reportsFacade;
    }
    
    @Override
    public Response getStateMileageByVehicleRoadStatus(Integer groupID, String strStartDate, String strEndDate, boolean dotOnly) {
        if(invalidParameters(groupID, strStartDate, strEndDate, dotOnly)) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        Date startDate = buildDateFromString(strStartDate);
        Date endDate = buildDateFromString(strEndDate);
        
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

    private boolean invalidParameters(Integer groupID, String strStartDate, String strEndDate, boolean ifta) {
        boolean res = false;

        if( groupID == null || strStartDate == null || strEndDate == null )
            res = true;
        
        Date startDate = buildDateFromString(strStartDate);
        Date endDate = buildDateFromString(strEndDate);

        if(startDate == null || endDate == null || endDate.before(startDate))
            res = true;

        return res;
    }

    private Date buildDateFromString(String strDate) {
        if(strDate == null)
            return null;

        DateFormat df = new SimpleDateFormat(getSimpleDateFormat()); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSimpleDateFormat() {
        return SIMPLE_DATE_FORMAT;
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusDefaultRange(Integer groupID, boolean dotOnly) {
        return getStateMileageByVehicleRoadStatus(groupID, daysAgoAsString(ReportsFacade.DAYS_BACK) , todayAsString(), dotOnly);
    }

    private String todayAsString() {
        DateFormat df = new SimpleDateFormat(getSimpleDateFormat()); 
        Calendar c = Calendar.getInstance();     
        return df.format(c.getTime());
    }

    private String daysAgoAsString(Integer daysBack) {
        DateFormat df = new SimpleDateFormat(getSimpleDateFormat()); 
        Calendar c = Calendar.getInstance(); 
        c.add(Calendar.DATE, -daysBack);      
        return df.format(c.getTime());
    }

    public void setFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;   
    }



}
