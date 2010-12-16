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
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

/*******************************************************************************************************
 * State Mileage by Vehicle / Group Comparison by State-Province Services Unit Tests
 *******************************************************************************************************/
public class IFTAServiceStateMileageGroupComparisonImplTest extends BaseUnitTest {

    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    
    @Mocked
    private ReportsFacade reportsFacadeMock;

    @Mocked
    private ReportsUtil reportsUtilMock;

    IFTAServiceStateMileageByVehicleRoadStatusImpl roadStatusServiceSUT;
    IFTAServiceStateMileageByVehicleGroupComparisonImpl groupComparisonServiceSUT;
    IFTAServiceStateMileageByVehicleMonthImpl mileageByMonthServiceSUT;

    @Before
    public void setUp() {
        roadStatusServiceSUT = new IFTAServiceStateMileageByVehicleRoadStatusImpl(reportsFacadeMock, reportsUtilMock);
        groupComparisonServiceSUT = new IFTAServiceStateMileageByVehicleGroupComparisonImpl(reportsFacadeMock, reportsUtilMock);
        mileageByMonthServiceSUT = new IFTAServiceStateMileageByVehicleMonthImpl(reportsFacadeMock, reportsUtilMock);
    }

    private Integer expectedGroupID = 1504;
    
    @Test(expected=IllegalArgumentException.class)
    public void getStateMileageByVehicleGroupComparisonTestWihInvalidInput1() {

        final String expectedStartDate = "20110101";
        final String expectedEndDate = "20100202";

        groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                buildDateFromString(expectedStartDate), buildDateFromString(expectedEndDate), locale, measureType);

    }

    @Test
    public void getStateMileageByVehicleGroupComparisonTest() {

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
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(expectedGroupID, 
                startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest1() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(expectedGroupID, 
                startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonWithEmptyResultTest2() {

        final boolean expectedIfta = true;
        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, expectedIfta, locale, measureType);
                result = null;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithIftaAndDates(
                expectedGroupID, startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonDefaultRangeTest() {

        final boolean expectedIfta = true;


        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, (Interval)any, expectedIfta, locale, measureType);
                result = list;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithIfta(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonNoParamTest() {

        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, (Interval)any, false, locale, measureType);
                result = list;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonDefaults(expectedGroupID, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getStateMileageByVehicleGroupComparisonOnlyRangeTest() {

        final String expectedStrStartDate = "20100101";
        final String expectedStrEndDate = "20100202";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        final Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        final List<StateMileageCompareByGroup> list = new ArrayList<StateMileageCompareByGroup>();
        list.add(new StateMileageCompareByGroup());

        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicleStateComparison(expectedGroupID, interval, false, locale, measureType);
                result = list;
            }
        };

        Response response = groupComparisonServiceSUT.getStateMileageByVehicleStateComparisonWithDates(expectedGroupID, 
                startDate, endDate, locale, measureType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
