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
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.exceptionMappers.BadDateRangeExceptionMapper;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.reports.IFTAServiceStateMileageGroupComparison;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.DateUtil;
import com.inthinc.pro.util.GroupList;

import common.Logger;

@Component
public class IFTAServiceStateMileageGroupComparisonImpl extends BaseReportServiceImpl 
    implements IFTAServiceStateMileageGroupComparison {
    private static Logger logger = Logger.getLogger(IFTAServiceStateMileageGroupComparisonImpl.class); 

    /**
     * Default constructor.
     * @param reportsFacade
     * @param reportsUtil
     */
    @Autowired
    public IFTAServiceStateMileageGroupComparisonImpl(ReportsFacade reportsFacade) {
        super(reportsFacade);
    }

   
    Response getStateMileageByVehicleStateComparisonWithFullParameters(Integer groupID, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        // Creating a GroupList with only one group ID.
        GroupList groupList = new GroupList();
        groupList.getValueList().add(Integer.toString(groupID));
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), startDate, endDate, iftaOnly, locale, measurementType);
    }
    
    Response getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(List<Integer> groupList, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<StateMileageCompareByGroup> list = null;

        Interval interval = null;
        try {
            interval = DateUtil.getInterval(startDate, endDate);
            list = reportsFacade.getStateMileageGroupComparison(groupList, interval, iftaOnly, locale, measurementType);
        } catch(BadDateRangeException bdre){
            return BadDateRangeExceptionMapper.getResponse(bdre);
                
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
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonDefaultsMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), null, null, false, locale, measurementType);
    }


    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), startDate, endDate, false, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(com.inthinc.pro.util.GroupList, java.util.Date, java.util.Date, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(GroupList groupList, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), startDate, endDate, true, locale, measurementType);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison#getStateMileageByVehicleStateComparisonWithIftaMultiGroup(com.inthinc.pro.util.GroupList, java.util.Locale, com.inthinc.pro.model.MeasurementType)
     */
    @Override
    @ValidParams
    public Response getStateMileageByVehicleStateComparisonWithIftaMultiGroup(GroupList groupList, Locale locale, MeasurementType measurementType) {
        return getStateMileageByVehicleStateComparisonWithFullParametersMultiGroup(groupList.getValueListAsIntegers(), null, null, true, locale, measurementType);
    }
}
