package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
import com.inthinc.pro.util.ReportsUtil;

public class IFTAServiceImplTest extends BaseUnitTest {

    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    
    @Mocked
    private ReportsFacade reportsFacadeMock;

    @Mocked
    private ReportsUtil reportsUtilMock;

    IFTAServiceStateMileageByVehicleRoadStatusImpl roadStatusServiceSUT;

    @Before
    public void setUp() {
        roadStatusServiceSUT = new IFTAServiceStateMileageByVehicleRoadStatusImpl(reportsFacadeMock, reportsUtilMock);
    }

    private Integer expectedGroupID = 1504;
    private Integer expectedGroupID2 = 1505;
    private List<Integer> expectedGroupIDList;
    

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road Status

    //------- One Group selection
    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput1() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.BAD_REQUEST).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput2() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.NOT_FOUND).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput3() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.FORBIDDEN).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate);

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

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate);

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

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = null;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, startDate, endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeTest() {

        final boolean expectedIfta = true;

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, withEqual(startDate), withEqual(endDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusNoParamTest() {

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, false);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusDefaults(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, false);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, startDate, endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
    // ----- Multi Group selection
    
    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInputMultiGroup1() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.BAD_REQUEST).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDatesMultiGroup(gl, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInputMultiGroup2() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.NOT_FOUND).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDatesMultiGroup(gl, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInputMultiGroup3() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result = Response.status(Status.FORBIDDEN).build();
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDatesMultiGroup(gl, buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusMultiGroupTest() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate);

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

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate);

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

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);
        
        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = null;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDatesMultiGroup(gl, startDate, endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeMultiGroupTest() {

        final boolean expectedIfta = true;

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, withEqual(startDate), withEqual(endDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, expectedIfta);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithIftaMultiGroup(gl);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusNoParamMultiGroupTest() {

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, false);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusDefaultsMultiGroup(gl);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeMultiGroupTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());
        expectedGroupIDList = new ArrayList<Integer>();
        expectedGroupIDList.add(expectedGroupID);
        expectedGroupIDList.add(expectedGroupID2);
        GroupList gl = new GroupList(expectedGroupIDList);

        new Expectations() {
            {
                reportsUtilMock.checkParametersMultiGroup(expectedGroupIDList, buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupIDList, interval, false);
                result = list;
            }
        };

        Response response = roadStatusServiceSUT.getStateMileageByVehicleRoadStatusWithDatesMultiGroup(gl, startDate, endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
 
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Month

    /* Utility methods */

    private String todayAsString() {
        DateFormat df = new SimpleDateFormat(BaseIFTAServiceImpl.DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    private String daysAgoAsString(Integer daysBack) {
        DateFormat df = new SimpleDateFormat(BaseIFTAServiceImpl.DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -daysBack);
        return df.format(c.getTime());
    }

    private Calendar getMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
        today.set(Calendar.MINUTE, 0); // set minute in hour
        today.set(Calendar.SECOND, 0); // set second in minute
        today.set(Calendar.MILLISECOND, 0); // set millis in second
        return today;
    }

}
