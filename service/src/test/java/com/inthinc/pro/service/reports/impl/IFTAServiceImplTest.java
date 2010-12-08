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

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

public class IFTAServiceImplTest extends BaseUnitTest {

    @Autowired 
    @Mocked 
    private ReportsFacade reportsFacadeMock;

    IFTAServiceImpl serviceSUT = new IFTAServiceImpl(reportsFacadeMock);

    private Integer expectedGroupID = 1504;
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Road Status

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput1(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                buildDateFromString("20110101"),
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput2(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(null, 
                buildDateFromString("20100101"),
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput3(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                null,
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput4(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                buildDateFromString("20100202"),
                null);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }


    @Test
    public void getStateMileageByVehicleRoadStatusTest(@Mocked final ReportsFacade reportsFacadeMock){

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
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;

        Date startDate = buildDateFromString(daysAgoAsString(6));
        Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                  reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedIfta);
                  result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getStateMileageByVehicleRoadStatusNoParamTest(@Mocked final ReportsFacade reportsFacadeMock){

        Date startDate = buildDateFromString(daysAgoAsString(6));
        Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                  reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, false);
                  result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusDefaults(expectedGroupID);
                                                                                      

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getStateMileageByVehicleRoadStatusOnlyRangeTest(@Mocked final ReportsFacade reportsFacadeMock){

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, false );
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusWithDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    // ----------------------------------------------------------------------
    // State Mileage by Vehicle / Group Comparison by State-Province
    
    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput1(){

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithDates(expectedGroupID, 
                buildDateFromString("20110101"),
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput2(){

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithDates(null, 
                buildDateFromString("20100101"),
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput3(){

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithDates(expectedGroupID, 
                null,
                buildDateFromString("20100202"));

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput4(){

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithDates(expectedGroupID, 
                buildDateFromString("20100202"),
                null);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }


    @Test
    public void getStateMileageByVehicleGroupComparisonTest(@Mocked final ReportsFacade reportsFacadeMock){

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
                reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, expectedIfta);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, expectedIfta);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithIftaAndDates(expectedGroupID, 
                startDate,
                endDate);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedIfta = true;

        Date startDate = buildDateFromString(daysAgoAsString(6));
        Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                  reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, expectedIfta);
                  result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithIfta(expectedGroupID);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getStateMileageByVehicleGroupComparisonNoParamTest(@Mocked final ReportsFacade reportsFacadeMock){

        Date startDate = buildDateFromString(daysAgoAsString(6));
        Date endDate = buildDateFromString(todayAsString());

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                  reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, false);
                  result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonDefaults(expectedGroupID);
                                                                                      

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getStateMileageByVehicleGroupComparisonOnlyRangeTest(@Mocked final ReportsFacade reportsFacadeMock){

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparaison(expectedGroupID, interval, false );
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleGroupComparisonWithDates(expectedGroupID, 
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
