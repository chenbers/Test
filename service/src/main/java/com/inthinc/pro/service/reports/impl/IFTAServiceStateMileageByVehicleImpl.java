package com.inthinc.pro.service.reports.impl;

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
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByVehicleImpl extends BaseReportServiceImpl implements IFTAServiceStateMileageByVehicle {

    @Autowired
    public IFTAServiceStateMileageByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleDefaults(
     * java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParameters(groupID, null, null, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithDates(
     * java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithIfta(
     * java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithIfta(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParameters(groupID, null, null, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithIftaAndDates(
     * java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParameters(groupID, startDate, endDate, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleDefaultsMultiGroup(
     * com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleDefaultsMultiGroup(GroupList groupList, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParamsMultiGroup(groupList, null, null, false, locale, measurementType);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithDatesMultiGroup(
     * com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParamsMultiGroup(groupList, startDate, endDate, 
                false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithIftaAndDatesMultiGroup(
     * com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParamsMultiGroup(groupList, startDate, endDate, 
                true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicle#getStateMileageByVehicleWithIftaMultiGroup(
     * com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleWithFullParamsMultiGroup(groupList, null, null, 
                true, locale, measurementType);
    }

    private Response getStateMileageByVehicleWithFullParameters(Integer groupID, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {
        
        GroupList groupList = new GroupList();
        groupList.getValueList().add(groupID);
        
        return getStateMileageByVehicleWithFullParamsMultiGroup(groupList, startDate, endDate, 
                iftaOnly, locale, measurementType);
    }

    private Response getStateMileageByVehicleWithFullParamsMultiGroup(GroupList groupList, 
            Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {
        
        Interval interval = getInterval(startDate, endDate);

        List<MileageByVehicle> list = null;
        try {
            list = reportsFacade.getStateMileageByVehicle(groupList.getValueList(), interval, 
                    iftaOnly, locale, measurementType);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }

}
