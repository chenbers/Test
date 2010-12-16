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
import com.inthinc.pro.service.reports.IFTAServiceStateMileageByVehicleGroupComparison;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.ReportsUtil;
import common.Logger;

@Component
public class IFTAServiceStateMileageByVehicleGroupComparisonImpl extends BaseIFTAServiceImpl 
    implements IFTAServiceStateMileageByVehicleGroupComparison {
    private static Logger logger = Logger.getLogger(IFTAServiceStateMileageByVehicleGroupComparisonImpl.class); 

    @Autowired
    public IFTAServiceStateMileageByVehicleGroupComparisonImpl(ReportsFacade reportsFacade, ReportsUtil reportsUtil) {
        super(reportsFacade, reportsUtil);
    }

    Response getStateMileageByVehicleStateComparisonWithFullParameters(Integer groupID, Date startDate, Date endDate, 
            boolean iftaOnly, Locale locale, MeasurementType measurementType) {

        List<StateMileageCompareByGroup> list = null;

        Interval interval = getInterval(startDate, endDate);
        try {
            list = reportsFacade.getStateMileageByVehicleStateComparison(groupID, interval, iftaOnly, locale, measurementType);
        } catch (Exception e) {
            logger.error(e.toString() + " for groupID:" + groupID + ", interval:" + interval 
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
}
