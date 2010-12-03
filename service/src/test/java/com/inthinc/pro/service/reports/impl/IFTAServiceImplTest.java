package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

public class IFTAServiceImplTest extends BaseUnitTest {

    @Autowired 
    @Mocked 
    private ReportsFacade reportsFacadeMock;

    IFTAServiceImpl serviceSUT = new IFTAServiceImpl(reportsFacadeMock);

    private Integer expectedGroupID = 1504;

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput1(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                "20110101",
                "20100202", 
                true);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput2(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(null, 
                "20100101",
                "20100202", 
                true);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput3(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                null,
                "20100202", 
                true);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTestWihInvalidInput4(){

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                "20100202",
                null, 
                true);

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusTest(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedDot = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedDot);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                expectedStrStartDate,
                expectedStrEndDate, 
                expectedDot);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest1(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedDot = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedDot);
                result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                expectedStrStartDate,
                expectedStrEndDate, 
                expectedDot);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusWithEmptyResultTest2(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedDot = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, interval, expectedDot);
                result = null;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatus(expectedGroupID, 
                expectedStrStartDate,
                expectedStrEndDate, 
                expectedDot);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getStateMileageByVehicleRoadStatusDefaultRangeTest(@Mocked final ReportsFacade reportsFacadeMock){

        final boolean expectedDot = true;

        final List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        list.add(new StateMileageByVehicleRoadStatus());

        new Expectations() {
            {
                  reportsFacadeMock.getStateMileageByVehicleRoadStatus(expectedGroupID, (Interval)any, expectedDot);
                  result = list;
            }
        };

        serviceSUT.setFacade(reportsFacadeMock);

        Response response = serviceSUT.getStateMileageByVehicleRoadStatusDefaultRange(expectedGroupID, 
                                                                                      expectedDot);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

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
