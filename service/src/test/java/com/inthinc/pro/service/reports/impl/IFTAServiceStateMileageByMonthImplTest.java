package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.GroupList;

/**
 * Unit tests for IFTA/DOT StateMileageByMonth Report Services.
 */
public class IFTAServiceStateMileageByMonthImplTest extends BaseUnitTest {
    
    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    
    @Mocked private ReportsFacade reportsFacadeMock;

    IFTAServiceStateMileageByMonthImpl serviceSUT;

    @Before
    public void setUp() {
        serviceSUT = new IFTAServiceStateMileageByMonthImpl(reportsFacadeMock);
    }

    private Integer expectedGroupID = 1504;
    private Integer expectedGroupID2 = 1505;
    private List<Integer> expectedGroupIDList;
    

    // One group selection
    @Test
    public void getStateMileageByMonthWithIftaAndDates() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIftaAndDatesEmptyResult() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileage> emptyList = new ArrayList<StateMileage>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = emptyList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIftaAndDatesNullResult() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIfta() {

        final boolean expectedIfta = true;

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIfta(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthDefaults() {

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthDefaults(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithDates() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
    // Multi group selection
    @Test
    public void getStateMileageByMonthWithIftaAndDatesMultiGroupTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
//                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, (Date)any, (Date)any);
//                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIftaAndDatesEmptyResultMultiGroupTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileage> emptyList = new ArrayList<StateMileage>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = emptyList;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIftaAndDatesNullResultMultiGroupTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDatesMultiGroup(gl, 
                startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByMonthWithIftaMultiGroupTest() {

        final boolean expectedIfta = true;

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaMultiGroup(gl, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleByMonthDefaultsMultiGroupTest() {

        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthDefaultsMultiGroup(gl, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleByMonthWithDatesMultiGroupTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupIDList, interval, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
