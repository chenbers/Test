package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.DateUtil;


public class HOSServiceImplTest {//extends BaseUnitTest {
    private static final Integer GROUP_ID = 6;
    
    
    @Mocked 
    private ReportsFacade facadeMock;
    private HOSServiceImpl serviceSUT = new HOSServiceImpl(null);
    
    
    
    /**
     * Test getHOSViolations with the interval.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetHOSViolationsWithInterval() {
        // create interval dates
        DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.toDate();
        final Date expectedEndDate = dateMidnight.plusDays(1).toDate();

        // set the mock facade
        serviceSUT.setReportsFacade(facadeMock);

        
        new Expectations () {
            {
                Interval interval = null;
                try {
                    interval = DateUtil.getInterval(expectedStartDate, expectedEndDate);
                } catch (BadDateRangeException e) {
                    fail("bad interval exception");
                }
                List<ViolationsDetail> list = new ArrayList<ViolationsDetail>();
                ViolationsDetail violationDetail = new ViolationsDetail();
                violationDetail.setGroupID(GROUP_ID);
                list.add(violationDetail);
                List<Integer> groupIDList = new ArrayList<Integer>();
                groupIDList.add(GROUP_ID);
                facadeMock.getHosViolationsDetail(groupIDList, interval, Locale.US); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getHOSViolationDetails(GROUP_ID, expectedStartDate, expectedEndDate, Locale.US);
        
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        // check the content
        GenericEntity<List<ViolationsDetail>> entity = 
            (GenericEntity<List<ViolationsDetail>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(GROUP_ID, entity.getEntity().get(0).getGroupID());
        
        // check for wrong interval
        response = serviceSUT.getHOSViolationDetails(GROUP_ID, expectedEndDate, expectedStartDate, Locale.US);
        Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(),response.getStatus());
    }
    
}

