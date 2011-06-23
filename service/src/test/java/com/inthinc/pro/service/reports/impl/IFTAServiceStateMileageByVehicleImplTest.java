package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.exceptions.BadDateRangeException;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.DateUtil;
import com.inthinc.pro.util.GroupList;

public class IFTAServiceStateMileageByVehicleImplTest extends BaseUnitTest {

    private static final Integer SAMPLE_GROUP_ID = 77;
    private static final int SIX_UNITS = 6;
    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    private List<Integer> expectedGroupIDList;
    private List<MileageByVehicle> returnList;
    private Date expectedStartDate;
    private Date expectedEndDate;
    private Interval interval;

    @Mocked private ReportsFacade reportsFacadeMock;

    private IFTAServiceStateMileageByVehicleImpl serviceSUT;

    @Before
    public void setUp() {
        serviceSUT = new IFTAServiceStateMileageByVehicleImpl(reportsFacadeMock);
        returnList = new ArrayList<MileageByVehicle>();
        returnList.add(new MileageByVehicle());
        expectedStartDate = buildDateFromString("20101201");
        expectedEndDate = buildDateFromString("20101231");
        try{
            interval = DateUtil.getInterval(expectedStartDate, expectedEndDate);
        }
        
        catch(BadDateRangeException bdre){
            
        }
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(SAMPLE_GROUP_ID);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleDefaults() {

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        (Interval)any, withEqual(false), locale, measureType);returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleWithDates() {

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        withEqual(interval), withEqual(false), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleWithIfta() {

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        (Interval)any, withEqual(true), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleWithIftaAndDates() {

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        withEqual(interval), withEqual(true), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns404OnEmptyList() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        (Interval) any, anyBoolean, locale, measureType);
                result = new ArrayList();
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns404OnNull() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any,
                        (Interval) any, anyBoolean, locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns500WhenInternalException() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusMonths(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusDays(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any,
                        (Interval) any, anyBoolean, locale, measureType);
                result = new RuntimeException("Dummy exception");
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID,
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = serviceSUT.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID,
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    // Multi-group support tests
    @Test
    public void testGetStateMileageByVehicleMultiGroupDefaults() {

        GroupList groupList = new GroupList(expectedGroupIDList);

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle(withEqual(expectedGroupIDList), 
                        (Interval)any, withEqual(false), locale, measureType);
                returns(returnList);
            }
        };

        Response res = serviceSUT.getStateMileageByVehicleDefaultsMultiGroup(groupList, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        
        new Verifications() {
            {
                try{
                    DateUtil.getInterval(null, null);
                }
                
                catch(BadDateRangeException bdre){
                    
                }
            }            
        };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleMultiGroupWithDates() {

        GroupList groupList = new GroupList(expectedGroupIDList);

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        withEqual(interval), withEqual(false), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithDatesMultiGroup(
                groupList, expectedStartDate, expectedEndDate, locale, measureType);
        
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        new Verifications() {
            {
                try{
                    DateUtil.getInterval(expectedStartDate, expectedEndDate);
                }
                
                catch(BadDateRangeException bdre){
                    
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleMultiGroupWithIfta() {

        GroupList groupList = new GroupList(expectedGroupIDList);

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        (Interval)any, withEqual(true), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithIftaMultiGroup(groupList, locale, measureType);
        
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        new Verifications() {
            {
                try{
                    DateUtil.getInterval(null, null);
                }
                
                catch(BadDateRangeException bdre){
                    
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleMultiGroupWithIftaAndDates() {

        GroupList groupList = new GroupList(expectedGroupIDList);

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((List<Integer>)any, 
                        withEqual(interval), withEqual(true), locale, measureType);
                returns(returnList);
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleWithIftaAndDatesMultiGroup(
                groupList, expectedStartDate, expectedEndDate, locale, measureType);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        new Verifications() {
            {
                try{
                    DateUtil.getInterval(expectedStartDate, expectedEndDate);
                }
                
                catch(BadDateRangeException bdre){
                    
                }
            }
        };
    }
    
}
