package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.service.reports.HOSService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.DateUtil;

@Component
public class HOSServiceImpl extends BaseReportServiceImpl implements HOSService {
    private static Logger logger = Logger.getLogger(HOSServiceImpl.class);

    @Autowired
    public HOSServiceImpl(ReportsFacade reportsFacade) {
        super(reportsFacade);
        // TODO Auto-generated constructor stub
    }

    @Override 
    @ValidParams
    public Response getHOSViolationDetails(Integer groupID, Date startDate, Date endDate, Locale locale) {
        String method = "HOS Violations Details Request";
 
        logger.debug(method+"("+groupID+", "+startDate+", "+endDate+") started");
        logger.debug(method+"calls ReportsFacade.getHosViolationsDetail()");
        List<ViolationsDetail> violationsList = null;
        try {
            Interval interval = DateUtil.getInterval(startDate, endDate);
            List<Integer> groupIDList = new ArrayList<Integer>();
            groupIDList.add(groupID);
            violationsList = reportsFacade.getHosViolationsDetail(groupIDList, interval, locale);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug(method+"done.");
        if (violationsList == null || violationsList.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(new GenericEntity<List<ViolationsDetail>>(violationsList) {}).build();

    }
    
    /**
     * The reportsFacade setter.
     * @param reportsFacade the reportsFacade to set
     */
    void setReportsFacade(ReportsFacade reportsFacade) {
        this.reportsFacade = reportsFacade;
    }

}
