package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

public class IFTAServiceImplTest extends BaseUnitTest {

    @Autowired 
    @Mocked 
    private ReportsFacade reportsFacadeMock;

    @Autowired 
    @Mocked
    private ReportsUtil reportsUtilMock;

    IFTAServiceImpl serviceSUT = new IFTAServiceImpl(reportsFacadeMock, reportsUtilMock);

    private Integer expectedGroupID = 1504;

    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road Status

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput1(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.BAD_REQUEST).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput2(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.NOT_FOUND).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput3(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.FORBIDDEN).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusNoParamTest(@Mocked final ReportsFacade reportsFacadeMock){

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, false);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusDefaults(expectedGroupID);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, false );
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    /**********************************************************************************************************
    * State Mileage by Vehicle / Group Comparison by State-Province
    *******************************************************************************************************/
    
    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput1(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.BAD_REQUEST).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput2(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.NOT_FOUND).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput3(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.FORBIDDEN).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonNoParamTest(@Mocked final ReportsFacade reportsFacadeMock){

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, false);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonDefaults(expectedGroupID);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonOnlyRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, false );
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Month

    @Test
    public void getStateMileageByVehicleByMonthTestWihInvalidInput1(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.BAD_REQUEST).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthTestWihInvalidInput2(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.NOT_FOUND).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthTestWihInvalidInput3(){

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate));
                result =  Response.status(Status.FORBIDDEN).build();
            }
        };

        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate),
                buildDateFromString(expectedEndDate));

        assertNotNull(response);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> emptyList = new ArrayList<StateMileage>();

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, expectedIfta);
                result = emptyList;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, expectedIfta);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final boolean expectedIfta = true;

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthNoParamTest(@Mocked final ReportsFacade reportsFacadeMock){

        final Date startDate = buildDateFromString(daysAgoAsString(6));
        final Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID, startDate, endDate);
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, false);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthDefaults(expectedGroupID);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleByMonthOnlyRangeTest(@Mocked final ReportsFacade reportsFacadeMock, final ReportsUtil reportsUtilMock){

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileage> list = new ArrayList<StateMileage>();
        list.add(new StateMileage());

        new Expectations() {
            {
                reportsUtilMock.checkParameters(expectedGroupID,  buildDateFromString(expectedStrStartDate), buildDateFromString(expectedStrEndDate));
                result = null;
                reportsFacadeMock.getStateMileageByVehicleByMonth(expectedGroupID, interval, false );
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);
        serviceSUT.setReportsUtil(reportsUtilMock);

        Response response = serviceSUT.getStateMileageByVehicleByMonthWithDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    /* Utility methods*/

    private Date buildDateFromString(String strDate) {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat()); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private String todayAsString() {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat()); 
        Calendar c = Calendar.getInstance();     
        return df.format(c.getTime());
    }

    private String daysAgoAsString(Integer daysBack) {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat()); 
        Calendar c = Calendar.getInstance(); 
        c.add(Calendar.DATE, -daysBack);      
        return df.format(c.getTime());
    }

}
