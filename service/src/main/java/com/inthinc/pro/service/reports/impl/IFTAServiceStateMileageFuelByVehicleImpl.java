package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
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
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageFuelByVehicleImpl extends BaseIFTAServiceImpl implements IFTAServiceStateMileageFuelByVehicle {

    @Autowired
    public IFTAServiceStateMileageFuelByVehicleImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * Retrieve the StateMileageFuelByVehicle report data beans from the backend.
     * 
     * @param gorupID
     *            The ID of the group
     * @param startDate
     *            The start date of the interval
     * @param endDate
     *            The end date of the interval
     * @param iftaOnly
     *            Indicator expressing if only the ifta data should be returned
     * @return javax.ws.rs.core.Response to return the client
     */
    Response getStateMileageByVehicleRoadStatusWithFullParameters(Integer groupID, Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // Creating a GroupList with only one group ID.
        List<Integer> groupList = new ArrayList<Integer>();
        groupList.add(groupID);
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList, startDate, endDate, iftaOnly, locale, measurementType);
    }

    /**
     * Retrieve the StateMileageFuelByVehicle report data beans from the backend.
     * 
     * @param groupList
     *            The ID of the group
     * @param startDate
     *            The start date of the interval
     * @param endDate
     *            The end date of the interval
     * @param iftaOnly
     *            Indicator expressing if only the ifta data should be returned
     * @return javax.ws.rs.core.Response to return the client.
     */
    Response getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(List<Integer> groupList, Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<StateMileageFuelByVehicle> list = null;
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        try {
            list = reportsFacade.getStateMileageFuelByVehicle(groupList, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        // Some data found
        if (list != null && !list.isEmpty()) {
            return Response.ok(new GenericEntity<List<StateMileageFuelByVehicle>>(list) {}).build();
        }
        // No data found
        else {
            return Response.status(Status.NOT_FOUND).build();
        }

    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleDefaults(java.lang.Integer)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime(), today.getTime(), false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate, endDate, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIfta(java.lang.Integer)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIfta(Integer groupID, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate.getTime(), today.getTime(), true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIftaAndDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, startDate, endDate, true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleDefaultsMultiGroup(com.inthinc.pro.util.GroupList)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleDefaultsMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate.getTime(), today.getTime(), false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date,
     *      java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIftaMultiGroup(com.inthinc.pro.util.GroupList)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate.getTime(), today.getTime(), true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList,
     *      java.util.Date, java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate, true, locale, measurementType);
    }
}
