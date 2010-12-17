package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;
import static mockit.Mockit.tearDownMocks;

import java.util.ArrayList;
import java.util.Date;
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
import com.inthinc.pro.util.ReportsUtil;

public class IFTAServiceStateMileageByVehicleImplTest {

    private static final Integer SAMPLE_GROUP_ID = 77;
    private static final int SIX_UNITS = 6;
    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;

    @Mocked
    private ReportsUtil reportsUtilMock;
    @Mocked
    private ReportsFacade reportsFacadeMock;

    private IFTAServiceStateMileageByVehicleImpl iftaService;

    @Before
    public void setUp() {
        iftaService = new IFTAServiceStateMileageByVehicleImpl(reportsFacadeMock, reportsUtilMock);
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
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), 
                        withEqual(interval), withEqual(false), locale, measureType);
            }
        };

        iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
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
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), 
                        withEqual(interval), withEqual(false), locale, measureType);
            }
        };

        iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
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
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), 
                        withEqual(interval), withEqual(true), locale, measureType);
            }
        };

        iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
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
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), 
                        withEqual(interval), withEqual(true), locale, measureType);
            }
        };

        iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
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
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, 
                        (Interval) any, anyBoolean, locale, measureType);
                result = new ArrayList();
            }
        };

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns404OnNull() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any,
                        (Interval) any, anyBoolean, locale, measureType);
                result = null;
            }
        };

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, 
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns500WhenInternalException() {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any,
                        (Interval) any, anyBoolean, locale, measureType);
                result = new RuntimeException("Dummy exception");
            }
        };

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID,
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID,
                expectedStartDate, expectedEndDate, locale, measureType);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
