package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.service.reports.HOSServiceDotHoursRemaining;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import common.Logger;

public class HOSServiceDotHoursRemainingImpl extends BaseReportServiceImpl implements HOSServiceDotHoursRemaining {
    private static Logger logger = Logger.getLogger(HOSServiceDotHoursRemainingImpl.class);

    @Autowired
    public HOSServiceDotHoursRemainingImpl(ReportsFacade reportsFacade) {
        super(reportsFacade);
    }
    @Override
    @ValidParams
    public Response getDOTHoursRemaining(Integer groupID, Locale locale) {
        
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(groupID);
        List<DotHoursRemaining> list = null;
        try {
            list = reportsFacade.getDotHoursRemaining(ids, locale);
        } catch (Exception e) {
            logger.error(e.toString() + " for groupIDs:" + ids + ", locale:" + locale );
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<DotHoursRemaining>>(list) {}).build();
    }
//    Response getMileageByVehicleMultiGroup(List<Integer> idlist, Date startDate, Date endDate, Boolean iftaOnly, Locale locale, MeasurementType measurementType) {
//
//        Interval interval = DateUtil.getInterval(startDate, endDate);
//
//        List<MileageByVehicle> list = null;
//        try {
//            list = reportsFacade.getMileageByVehicle(idlist, interval, iftaOnly, locale, measurementType);
//        } catch (Exception e) {
//            logger.error(e.toString() + " for groupIDs:" + idlist + ", interval:" + interval + ", iftaOnly:" + iftaOnly + ", locale:" + locale + ", measurementType: " + measurementType);
//            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//        if (list == null || list.isEmpty()) {
//            return Response.status(Status.NOT_FOUND).build();
//        }
//
//        return Response.ok(new GenericEntity<List<MileageByVehicle>>(list) {}).build();
//    }

}
