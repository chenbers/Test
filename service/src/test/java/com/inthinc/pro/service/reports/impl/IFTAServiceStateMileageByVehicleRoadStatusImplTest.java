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
        serviceSUT = new IFTAServiceStateMileageByVehicleRoadStatusImpl(reportsFacadeMock);
    }

    private Integer expectedGroupID = 2;
    private Integer expectedGroupID2 = 6;
    private List<Integer> expectedGroupIDList;
    
    @Test
    public void getStateMileageRoadStatusWithIftaAndDatesTest() {

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
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(withEqual(expectedGroupIDList), (Interval)any, withEqual(expectedIfta), withEqual(locale), withEqual(measureType));
                result = list;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageRoadStatusWithIftaAndDatesEmptyResultTest() {

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
                result = new ArrayList<StateMileageByVehicleRoadStatus>();;
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageRoadStatusWithIftaAndDatesNullResultTest() {

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
    public void getStateMileageRoadStatusWithIftaTest() {

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
    public void getStateMileageRoadStatusDefaultsTest() {

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
    public void getStateMileageRoadStatusWithDatesTest() {

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
    public void getStateMileageRoadStatusWithIftaAndDatesMultiGroupTest() {

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
    public void getStateMileageRoadStatusWithIftaAndDatesMultiGroupEmptyResultTest() {

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
                result = new ArrayList<StateMileageByVehicleRoadStatus>();
            }
        };

        Response response = serviceSUT.getStateMileageRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageRoadStatusWithIftaAndDatesMultiGroupNullResultTest() {

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
    public void getStateMileageRoadStatusWithIftaMultiGroupTest() {

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
    public void getStateMileageRoadStatusDefaultsMultiGroupTest() {

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
    public void getStateMileageRoadStatusWithDatesMultiGroupTest() {

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
