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
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.service.exceptionMappers.BadDateRangeExceptionMapper;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.DateUtil;
import com.inthinc.pro.util.GroupList;
import common.Logger;

@Component
public class IFTAServiceStateMileageFuelByVehicleImpl extends BaseReportServiceImpl implements IFTAServiceStateMileageFuelByVehicle {
    private static Logger logger = Logger.getLogger(IFTAServiceStateMileageFuelByVehicleImpl.class);

    @Autowired
    public IFTAServiceStateMileageFuelByVehicleImpl(ReportsFacade reportsFacade) {
        super(reportsFacade);
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
        Interval interval = null;
        try {
            interval = DateUtil.getInterval(startDate, endDate);

            list = reportsFacade.getStateMileageFuelByVehicle(groupList, interval, iftaOnly, locale, measurementType);
        } catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
            
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        if (list == null) {
            list = new ArrayList<StateMileageFuelByVehicle>();
        }

        return Response.ok(new GenericEntity<List<StateMileageFuelByVehicle>>(list) {}).build();

    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleDefaults(java.lang.Integer)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, null, null, false, locale, measurementType);
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
        return getStateMileageByVehicleRoadStatusWithFullParameters(groupID, null, null, true, locale, measurementType);
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
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), null, null, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date,
     *      java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), startDate, endDate, false, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIftaMultiGroup(com.inthinc.pro.util.GroupList)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), null, null, true, locale, measurementType);
    }

    /**
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageFuelByVehicle#getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList,
     *      java.util.Date, java.util.Date)
     */
    @Override
    @ValidParams
    public Response getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleRoadStatusWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), startDate, endDate, true, locale, measurementType);
    }
}
