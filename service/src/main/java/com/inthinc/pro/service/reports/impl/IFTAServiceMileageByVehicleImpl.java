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
import com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceMileageByVehicleImpl extends BaseIFTAServiceImpl implements IFTAServiceMileageByVehicle {

    @Autowired
    public IFTAServiceMileageByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate) {
        return getMileageByVehicle(groupID, startDate, endDate, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
//    @ValidParams
    public Response getMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate) {
//    	return Response.ok().build();
    	//TODO: un-comment when validation test is done    	
        return getMileageByVehicle(groupID, startDate, endDate, false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleWithIfta(java.lang.Integer)
     */
    @Override
    public Response getMileageByVehicleWithIfta(Integer groupID) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAService#getMileageByVehicleDefaults(java.lang.Integer)
     */
    @Override
//    @ValidParams    
    public Response getMileageByVehicleDefaults(Integer groupID) {
///    	return Response.status(Status.OK).build();
    	//TODO: un-comment when validation test is done
        Calendar today = reportsUtil.getMidnight();
        
        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), false);
    }

    /** Service implementation for Mileage by Vehicle report */
    Response getMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly) {
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate);
        if (response != null)
            return response;

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        
        try {
            list = reportsFacade.getMileageByVehicle(groupID, interval, iftaOnly);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }
	
}
