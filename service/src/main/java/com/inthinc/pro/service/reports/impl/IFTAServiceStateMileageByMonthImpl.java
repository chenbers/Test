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
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByMonth;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;

@Component
public class IFTAServiceStateMileageByMonthImpl extends BaseIFTAServiceImpl implements IFTAServiceStateMileageByMonth {

    @Autowired
    public IFTAServiceStateMileageByMonthImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    /**
     * Service to retrieve the MileageByVehicle report data beans from the Facade.
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
    Response getStateMileageByVehicleByMonthWithFullParameters(Integer groupID, Date startDate, Date endDate,
             boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // Creating a GroupList with only one group ID.
        GroupList groupList = new GroupList();
        groupList.getValueList().add(groupID);
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueList(), 
                startDate, endDate, iftaOnly, locale, measurementType);
    }
    
    Response getStateMileageByVehicleByMonthWithFullParametersMultiGroup(List<Integer> groupList, 
            Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<MileageByVehicle> list = null;

        Interval interval = getInterval(startDate, endDate);
        try {
            list = reportsFacade.getStateMileageByVehicleByMonth(groupList, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            // No data found
            return Response.status(Status.NOT_FOUND).build();            
        }
        // Some data found
        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIfta(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, null, null, true, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIftaAndDates(Integer groupID, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate, endDate, true, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithDates(Integer groupID, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, startDate, endDate, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParameters(groupID, null, null, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthDefaultsMultiGroup(GroupList groupList, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueList(), 
                null, null, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithDatesMultiGroup(GroupList groupList, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueList(), 
                startDate, endDate, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(GroupList groupList, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueList(), 
                startDate, endDate, true, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIftaMultiGroup(GroupList groupList, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueList(), 
                null, null, true, locale, measurementType);
    }
}
