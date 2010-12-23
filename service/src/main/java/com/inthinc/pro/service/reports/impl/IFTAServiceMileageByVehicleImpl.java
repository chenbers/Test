package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
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
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;
import common.Logger;

@Component
public class IFTAServiceMileageByVehicleImpl extends BaseReportServiceImpl implements IFTAServiceMileageByVehicle {
    private static Logger logger = Logger.getLogger(IFTAServiceMileageByVehicleImpl.class);

    @Autowired
    public IFTAServiceMileageByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleDefaults(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicle(groupID, null, null, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicle(groupID, startDate, endDate, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIfta(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithIfta(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicle(groupID, null, null, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicle(groupID, startDate, endDate, true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleDefaultsMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleDefaultsMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicleMultiGroup(groupList.getValueList(), null, null, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date,
     *      java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicleMultiGroup(groupList.getValueList(), startDate, endDate, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIftaMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale,
     *      com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicleMultiGroup(groupList.getValueList(), null, null, true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceMileageByVehicle#getMileageByVehicleWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date,
     *      java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getMileageByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getMileageByVehicleMultiGroup(groupList.getValueList(), startDate, endDate, true, locale, measurementType);
    }

    /** Service implementation for Mileage by Vehicle report */
    Response getMileageByVehicle(Integer groupID, Date startDate, Date endDate, Boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(groupID);

        return getMileageByVehicleMultiGroup(ids, startDate, endDate, iftaOnly, locale, measurementType);
    }

    /** Service implementation for Mileage by Vehicle report */
    Response getMileageByVehicleMultiGroup(List<Integer> idlist, Date startDate, Date endDate, Boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        Interval interval = getInterval(startDate, endDate);

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getMileageByVehicle(idlist, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            logger.error(e.toString() + " for groupIDs:" + idlist + ", interval:" + interval + ", iftaOnly:" + iftaOnly + ", locale:" + locale + ", measurementType: " + measurementType);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }

}
