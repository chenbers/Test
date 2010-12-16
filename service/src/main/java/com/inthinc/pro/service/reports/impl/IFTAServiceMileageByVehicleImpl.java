package com.inthinc.pro.service.reports.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;
import common.Logger;

@Component
public class IFTAServiceMileageByVehicleImpl extends BaseIFTAServiceImpl implements IFTAServiceMileageByVehicle {
    private static Logger logger = Logger.getLogger(IFTAServiceMileageByVehicleImpl.class); 
    
    @Autowired
    public IFTAServiceMileageByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measureType) {
        return getMileageByVehicle(groupID, startDate, endDate, true, locale, measureType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate,
            Locale locale, MeasurementType measureType) {
        return getMileageByVehicle(groupID, startDate, endDate, false, locale, measureType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIfta(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getMileageByVehicleWithIfta(Integer groupID, Locale locale, MeasurementType measureType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), true, locale, measureType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleDefaults(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getMileageByVehicleDefaults(Integer groupID, Locale locale, MeasurementType measureType) {
        Calendar today = reportsUtil.getMidnight();
        
        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getMileageByVehicle(groupID, startDate.getTime(), today.getTime(), false, locale, measureType);
    }

    /** Service implementation for Mileage by Vehicle report */
    Response getMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly, Locale locale, MeasurementType measureType) {
        Response response = reportsUtil.checkParameters(groupID, startDate, endDate, locale, measureType);
        if (response != null) return response;

        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        List<MileageByVehicle> list = null;
        
        try {
            list = reportsFacade.getMileageByVehicle(groupID, interval, iftaOnly, locale, measureType);
        } catch (Exception e) {
            logger.error(e.toString() + " for groupID:" + groupID + ", interval:" + interval 
                    + ", iftaOnly:" + iftaOnly + ", locale:" + locale + ", measureType: " + measureType);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }
	
}
