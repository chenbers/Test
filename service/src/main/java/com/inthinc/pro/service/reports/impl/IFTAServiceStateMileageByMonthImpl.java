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
import com.inthinc.pro.reports.ifta.model.StateMileageByMonth;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByMonth;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;

@Component
public class IFTAServiceStateMileageByMonthImpl extends BaseReportServiceImpl implements IFTAServiceStateMileageByMonth {

    @Autowired
    public IFTAServiceStateMileageByMonthImpl(ReportsFacade reportsFacade) {
        super(reportsFacade);
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

        // Creating a list with only one group ID.
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(groupID);
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(idList, 
                startDate, endDate, iftaOnly, locale, measurementType);
    }
    
    Response getStateMileageByVehicleByMonthWithFullParametersMultiGroup(List<Integer> groupList, 
            Date startDate, Date endDate, boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<StateMileageByMonth> list = null;

        Interval interval = getInterval(startDate, endDate);
        try {
            list = reportsFacade.getStateMileageByMonth(groupList, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            // No data found
            return Response.status(Status.NOT_FOUND).build();            
        }
        // Some data found
        return Response.ok(new GenericEntity<List<StateMileageByMonth>>(list) {}).build();
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
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), 
                null, null, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithDatesMultiGroup(GroupList groupList, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), 
                startDate, endDate, false, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(GroupList groupList, 
            Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), 
                startDate, endDate, true, locale, measurementType);
    }

    @Override
    @ValidParams
    public Response getStateMileageByVehicleByMonthWithIftaMultiGroup(GroupList groupList, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleByMonthWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), 
                null, null, true, locale, measurementType);
    }

    // TODO:  For Validation Testing. To be removed
	@Override
	@ValidParams
	public Response getDefaultsMultiGroup(GroupList groupList, Locale locale,
			MeasurementType measurementType) {
		return Response.ok().build();
	}

	@Override
	@ValidParams
	public Response getDefaultsValidationTest(Integer groupID, Locale locale,
			MeasurementType measurementType) {
		return Response.ok().build();
	}

	@Override
	@ValidParams
	public Response getWithDatesMultiGroup(GroupList groupList, Date startDate,
			Date endDate, Locale locale, MeasurementType measurementType) {
		return Response.ok().build();
	}

	@Override
	@ValidParams
	public Response getWithDatesValidationTest(Integer groupID, Date startDate,
			Date endDate, Locale locale, MeasurementType measurementType) {
		return Response.ok().build();
	}
}
