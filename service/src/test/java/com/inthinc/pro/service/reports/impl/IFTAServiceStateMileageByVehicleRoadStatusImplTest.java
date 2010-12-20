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
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.GroupList;

public class IFTAServiceStateMileageByVehicleRoadStatusImplTest extends BaseUnitTest {

    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    
    @Mocked private ReportsFacade reportsFacadeMock;

    private IFTAServiceStateMileageByVehicleRoadStatusImpl serviceSUT;

    @Before
    public void setUp() {
        serviceSUT = new IFTAServiceStateMileageByVehicleRoadStatusImpl(reportsFacadeMock, null);
    }

    private Integer expectedGroupID = 1504;
    private Integer expectedGroupID2 = 1505;
    private List<Integer> expectedGroupIDList;
    
    @Test
    public void getStateMileageByVehicleRoadStatusTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest1() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest2() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeTest() {

        final boolean expectedIfta = true;

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIfta(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusNoParamTest() {

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusDefaults(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
    // ----- Multi Group selection
    
    @Test
    public void getStateMileageByVehicleRoadStatusMultiGroupTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultMultiGroupTest1() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTestMultiGroup2() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = null;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeMultiGroupTest() {

        final boolean expectedIfta = true;

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaMultiGroup(gl, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusNoParamMultiGroupTest() {

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusDefaultsMultiGroup(gl, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeMultiGroupTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
 
}
