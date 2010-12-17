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
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageGroupComparison;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;
import common.Logger;

@Component
public class IFTAServiceStateMileageGroupComparisonImpl extends BaseIFTAServiceImpl 
    implements IFTAServiceStateMileageGroupComparison {
    private static Logger logger = Logger.getLogger(IFTAServiceStateMileageGroupComparisonImpl.class); 

    /**
     * @param reportsFacade
     * @param reportsUtil
     */
    @Autowired
    public IFTAServiceStateMileageGroupComparisonImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

   
    /**
     * @param groupID
     * @param startDate
     * @param endDate
     * @param iftaOnly
     * @param locale
     * @param measurementType
     * @return
     */
    Response getStateMileageByVehicleStateComparisonWithFullParameters(Integer groupID, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // Creating a GroupList with only one group ID.
        GroupList groupList = new GroupList();
        groupList.getValueList().add(groupID);
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate, iftaOnly, locale, measurementType);
    }
    
    /**
     * @param groupList
     * @param startDate
     * @param endDate
     * @param iftaOnly
     * @param locale
     * @param measurementType
     * @return
     */
    Response getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(List<Integer> groupList, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<StateMileageCompareByGroup> list = null;

        Interval interval = getInterval(startDate, endDate);
        try {
            list = reportsFacade.getStateMileageByVehicleStateComparison(groupList, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            logger.error(e.toString() + ", interval:" + interval 
                    + ", iftaOnly:" + iftaOnly + ", locale:" + locale + ", measurementType: " + measurementType);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list != null && !list.isEmpty())
            return Response.ok(new GenericEntity<List<StateMileageCompareByGroup>>(list) {}).build();

        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIfta(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithIfta(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, null, null, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIftaAndDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithIftaAndDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate, endDate, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithDates(java.lang.Integer, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithDates(Integer groupID, Date startDate, Date endDate, 
            Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, startDate, endDate, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonDefaults(java.lang.Integer, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonDefaults(Integer groupID, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParameters(groupID, null, null, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonDefaultsMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getStateMileageByVehicleStateComparisonDefaultsMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueList(), startDate.getTime(), today.getTime(), false, locale, measurementType);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getStateMileageByVehicleStateComparisonWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueList(), startDate, endDate, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIftaMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    public Response getStateMileageByVehicleStateComparisonWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        Calendar today = reportsUtil.getMidnight();

        Calendar startDate = reportsUtil.getMidnight();
        startDate.add(Calendar.DAY_OF_MONTH, DAYS_BACK);

        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueList(), startDate.getTime(), today.getTime(), true, locale, measurementType);
    }
}
