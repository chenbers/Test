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
    public Response getStateMileageByVehicleRoadStatus(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
        if(invalidParameters(groupID, startDate, endDate, iftaOnly)) {
            return Response.status(Status.BAD_REQUEST).build();
        }
//        ReportsUtil.checkParameters(groupID, startDate, endDate, iftaOnly);
        
        List<StateMileageByVehicleRoadStatus> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleRoadStatus(groupID, interval, iftaOnly);
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
    public Response getStateMileageByVehicleRoadStatusIfta(Integer groupID, boolean iftaOnly) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleRoadStatus(groupID, startDate.getTime() , today.getTime() , iftaOnly);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusInterval(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleRoadStatus(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleRoadStatusGroup(Integer groupID) {
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
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleInterval(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getMileageByVehicleInterval(Integer groupID, Date startDate, Date endDate) {
        return getMileageByVehicle(groupID, startDate, endDate, false);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleIfta(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleIfta(Integer groupID) {
        Calendar today = getMidnight();

        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), true);
    }
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleGroup(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleGroup(Integer groupID) {
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
    public Response getStateMileageByVehicleGroupComparison(Integer groupID, Date startDate, Date endDate, boolean iftaOnly) {
        if(invalidParameters(groupID, startDate, endDate, iftaOnly)) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        List<StateMileageCompareByGroup> list = null;
        
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        try{
            list = reportsFacade.getStateMileageByVehicleStateComparaison(groupID, interval, iftaOnly);
        }
        catch(Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if(list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageCompareByGroup>>(list) {}).build();
        
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getStateMileageByVehicleGroupComparaisonInterval(Integer groupID, Date startDate, Date endDate) {
        return getStateMileageByVehicleGroupComparison(groupID, startDate ,endDate , false);
    }

    @Override
    public Response getStateMileageByVehicleGroupComparisonGroup(Integer groupID) {
        Calendar today = getMidnight(); 
        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleGroupComparison(groupID, startDate.getTime() , today.getTime() , false);
    }

    @Override
    public Response getStateMileageByVehicleGroupComparisonIfta(Integer groupID, boolean iftaOnly) {
        Calendar today = getMidnight(); 
        Calendar startDate = getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);
        
        return getStateMileageByVehicleGroupComparison(groupID, startDate.getTime() , today.getTime() , iftaOnly);

    }

	@Override
	public Response getStateMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getStateMileageByVehicleGroup(Integer groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getStateMileageByVehicleIfta(Integer groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getStateMileageByVehicleInterval(Integer groupID, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
