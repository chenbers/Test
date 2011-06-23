package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.DateUtil;
import com.inthinc.pro.util.GroupList;

/** 
 * State Mileage by Vehicle / Group Comparison by State-Province Services Unit Tests
 */
public class IFTAServiceStateMileageGroupComparisonImplTest extends BaseUnitTest {

    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    private final Integer expectedGroupID = 2;
    private final Date expectedStartDate;
    private final Date expectedEndDate;
    private final List<StateMileageCompareByGroup> returnList;
    private final List<Integer> groupIDList;

    private Interval interval;
    private IFTAServiceStateMileageGroupComparisonImpl serviceSUT;
        
    @Mocked
    private ReportsFacade reportsFacadeMock;

    public IFTAServiceStateMileageGroupComparisonImplTest() {
        expectedStartDate = buildDateFromString("20101201");
        expectedEndDate = buildDateFromString("20101231");
        
        returnList = new ArrayList<StateMileageCompareByGroup>();
        returnList.add(new StateMileageCompareByGroup());
        
        groupIDList = new ArrayList<Integer>();
        groupIDList.add(expectedGroupID);
    }

    @Before
    public void setUp() {
        serviceSUT = new IFTAServiceStateMileageGroupComparisonImpl(reportsFacadeMock);
        try{
        interval = DateUtil.getInterval(expectedStartDate, expectedEndDate);
        } catch(BadDateRangeException bdre){
            System.out.println(bdre.getMessage());
            
        }
    }
    
    // Negative tests
    @Test
    public void testGetStateMileageGroupComparisonWihInvalidDates() {

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                buildDateFromString("20110101"), buildDateFromString("20100202"), locale, measureType);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIftaAndDatesEmptyResult() {

       new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(true), locale, measureType);
                result = new ArrayList<StateMileageCompareByGroup>();
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(
                expectedGroupID, expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIftaAndDatesNullResult() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(true), locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(
                expectedGroupID, expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    // Positive tests 
    // Single group ID
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIftaAndDates() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(true), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(
                expectedGroupID, expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIfta() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, (Interval)any, withEqual(true), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIfta(
                expectedGroupID, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithDates() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(false), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(
                expectedGroupID, expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getStateMileageGroupComparisonDefaults() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, (Interval)any, withEqual(false), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonDefaults(
                expectedGroupID, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    // Multiple group IDs tests
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIftaAndDatesMultiGroup() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(true), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDatesMultiGroup(
                new GroupList(groupIDList), expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithIftaMultiGroup() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, (Interval)any, withEqual(true), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaMultiGroup(
                new GroupList(groupIDList), locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageGroupComparisonWithDatesMultiGroup() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, withEqual(interval), withEqual(false), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDatesMultiGroup(
                new GroupList(groupIDList), expectedStartDate, expectedEndDate, locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getStateMileageGroupComparisonDefaultsMultiGroup() {

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageGroupComparison(
                        (List<Integer>)any, (Interval)any, withEqual(false), locale, measureType);
                result = returnList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonDefaultsMultiGroup(
                new GroupList(groupIDList), locale, measureType);

        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
}
