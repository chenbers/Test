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
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByVehicleRoadStatusImpl extends BaseReportServiceImpl 
                                implements IFTAServiceStateMileageByVehicleRoadStatus {

    @Autowired
    public IFTAServiceStateMileageByVehicleRoadStatusImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * Retrieve the StateMileageByVehicleRoadStatus report data beans from the Facade.
     * 
     * @param groupID
     *            The ID of the group
     * @param startDate
     *            The start date of the interval
     * @param endDate
     *            The end date of the interval
     * @param iftaOnly
     *            Indicator expressing if only the IFTA data should be returned
     * @return javax.ws.rs.core.Response to return to the client
     */
    Response getStateMileageRoadStatusWithFullParameters(Integer groupID, Date startDate, Date endDate,
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // Creating a GroupList with only one group ID.
        GroupList groupList = new GroupList();
        groupList.getValueList().add(groupID);
        return getStateMileageRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate,
                iftaOnly, locale, measurementType);
    }

    /**
     * Retrieve the StateMileageByVehicleRoadStatus report data beans from the Facade.
     * 
     * @param groupList
     *            A List of GroupIDs
     * @param startDate
     *            The start date of the interval
     * @param endDate
     *            The end date of the interval
     * @param iftaOnly
     *            Indicator expressing if only the IFTA data should be returned
     * @return javax.ws.rs.core.Response to return to the client
     */
    Response getStateMileageRoadStatusWithFullParametersMultiGroup(List<Integer> groupList, 
            Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // No validation error found
        List<StateMileageByVehicleRoadStatus> list = null;

        Interval interval = getInterval(startDate, endDate);
        try {
            list = reportsFacade.getStateMileageByVehicleRoadStatus(groupList, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        // Some data found
        if (list != null && !list.isEmpty()) {
            return Response.ok(new GenericEntity<List<StateMileageByVehicleRoadStatus>>(list) {}).build();
        }
        // No data found
        else {
            return Response.status(Status.NOT_FOUND).build();
        }

    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithIfta(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithIfta(Integer groupID, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParameters(groupID, null, null, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithIftaAndDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParameters(groupID, startDate, endDate, 
                true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParameters(groupID, startDate, endDate, 
                false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusDefaults(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusDefaults(Integer groupID, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParameters(groupID, null, null, 
                false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusDefaultsMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusDefaultsMultiGroup(GroupList groupList, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), null, null, 
                false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate,
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate,
                false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate,
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate,
                true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleRoadStatus#getStateMileageRoadStatusWithIftaMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageRoadStatusWithIftaMultiGroup(GroupList groupList,
            Locale locale, MeasurementType measurementType) {
        return getStateMileageRoadStatusWithFullParametersMultiGroup(groupList.getValueList(), null, null,
                true, locale, measurementType);
    }
}
