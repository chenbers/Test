package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.NonStrictExpectations;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.service.reports.facade.ReportsFacade;

public class StateMileageByVehicleIFTAServiceImplTest {

    private static final Integer SAMPLE_GROUP_ID = 77;
    private static final int SIX_UNITS = 6;

    @Test
    public void testGetStateMileageByVehicleDefaults(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(false));
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);
        iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID);
    }

    @Test
    public void testGetStateMileageByVehicleWithDates(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(false));
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);
        iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    }

    @Test
    public void testGetStateMileageByVehicleWithIfta(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(true));
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);
        iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID);
    }

    @Test
    public void testGetStateMileageByVehicleWithIftaAndDates(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(true));
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);
        iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns404OnEmptyList(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                result = new ArrayList();
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns404OnNull(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                result = null;
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns400WhenInvalidDates(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        // Start date after end date.
        final Date expectedStartDate = dateMidnight.minusMonths(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusYears(SIX_UNITS).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                times = 0;
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns400WhenNullGroupId(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                times = 0;
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleDefaults(null);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(null);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(null, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(null, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns400WhenNullStartDate(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                times = 0;
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, null, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, null, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns400WhenNullEndDate(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                times = 0;
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, null);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, null);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturns500WhenInternalException(final ReportsFacade reportsFacadeMock) {

        final DateMidnight dateMidnight = new DateMidnight();

        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsFacadeMock.getStateMileageByVehicle((Integer) any, (Interval) any, anyBoolean);
                result = new RuntimeException("Dummy exception");
            }
        };

        IFTAServiceImpl iftaService = new IFTAServiceImpl(reportsFacadeMock);

        Response response = iftaService.getStateMileageByVehicleDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaService.getStateMileageByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

}
