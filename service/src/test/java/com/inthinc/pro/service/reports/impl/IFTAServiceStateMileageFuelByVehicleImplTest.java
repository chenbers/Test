package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;
import static mockit.Mockit.tearDownMocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.GroupList;
import com.inthinc.pro.util.ReportsUtil;

public class IFTAServiceStateMileageFuelByVehicleImplTest {

    private final Integer SAMPLE_GROUP_ID = 77;
    private final Integer SECOND_GROUP_ID = 22;
    private final Locale sampleLocale = new Locale("en_US");
    private final MeasurementType sampleMeasurementType = MeasurementType.ENGLISH;

    @SuppressWarnings("serial")
    private final List<Integer> groupIdList = new ArrayList<Integer>() {
        {
            add(SAMPLE_GROUP_ID);
        }
    };

    private final int SIX_UNITS = 6;

    @Mocked
    private ReportsUtil reportsUtilMock;
    @Mocked
    private ReportsFacade reportsFacadeMock;

    private IFTAServiceStateMileageFuelByVehicleImpl iftaService;

    @Before
    public void setUp() {
        iftaService = new IFTAServiceStateMileageFuelByVehicleImpl(reportsFacadeMock, reportsUtilMock);
    }

    @Before
    public void tearDown() {
        tearDownMocks();
    }

    @Test
    public void testGetStateMileageByVehicleDefaults() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle(withEqual(groupIdList), withEqual(interval), withEqual(false), withSameInstance(sampleLocale), withSameInstance(sampleMeasurementType));
            }
        };

        iftaService.getStateMileageFuelByVehicleDefaults(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
    }

    @Test
    public void testGetStateMileageByVehicleDefaultsMultiGroup() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        @SuppressWarnings("serial")
        final List<Integer> groupIds = new ArrayList<Integer>() {
            {
                add(SAMPLE_GROUP_ID);
                add(SECOND_GROUP_ID);
            }
        };

        GroupList groupList = new GroupList();
        groupList.setValue(groupIds);

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle(withEqual(groupIds), withEqual(interval), withEqual(false), withSameInstance(sampleLocale), withSameInstance(sampleMeasurementType));
            }
        };

        iftaService.getStateMileageFuelByVehicleDefaultsMultiGroup(groupList, sampleLocale, sampleMeasurementType);
    }

    @Test
    public void testGetStateMileageByVehicleWithDates() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageFuelByVehicle(withEqual(groupIdList), withEqual(interval), withEqual(false), withSameInstance(sampleLocale), withSameInstance(sampleMeasurementType));
            }
        };

        iftaService.getStateMileageFuelByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
    }

    @Test
    public void testGetStateMileageByVehicleWithIfta() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle(withEqual(groupIdList), withEqual(interval), withEqual(true), withSameInstance(sampleLocale), withSameInstance(sampleMeasurementType));
            }
        };

        iftaService.getStateMileageFuelByVehicleWithIfta(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
    }

    @Test
    public void testGetStateMileageByVehicleWithIftaAndDates() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageFuelByVehicle(withEqual(groupIdList), withEqual(interval), withEqual(true), withSameInstance(sampleLocale), withSameInstance(sampleMeasurementType));
            }
        };

        iftaService.getStateMileageFuelByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
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
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle((List) any, (Interval) any, anyBoolean, (Locale) any, (MeasurementType) any);
                result = new ArrayList();
            }
        };

        Response response = iftaService.getStateMileageFuelByVehicleDefaults(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIfta(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
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
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle((List) any, (Interval) any, anyBoolean, (Locale) any, (MeasurementType) any);
                result = null;
            }
        };

        Response response = iftaService.getStateMileageFuelByVehicleDefaults(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIfta(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns500WhenInternalException() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsUtilMock.getMidnight();
                result = getMidnight();
                reportsFacadeMock.getStateMileageFuelByVehicle((List) any, (Interval) any, anyBoolean, (Locale) any, (MeasurementType) any);
                result = new RuntimeException("Dummy exception");
            }
        };

        Response response = iftaService.getStateMileageFuelByVehicleDefaults(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIfta(SAMPLE_GROUP_ID, sampleLocale, sampleMeasurementType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageFuelByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate, sampleLocale, sampleMeasurementType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
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
