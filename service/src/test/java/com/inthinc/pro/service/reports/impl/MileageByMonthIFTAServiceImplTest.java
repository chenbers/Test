package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.IFTAService;
import com.inthinc.pro.service.reports.facade.ReportsFacade;
import com.inthinc.pro.util.ReportsUtil;

public class MileageByMonthIFTAServiceImplTest extends BaseUnitTest {

    private static final Integer SAMPLE_GROUP_ID = 77;
    private static final int SIX_UNITS = 6;

    @Mocked
    private ReportsFacade reportsFacadeMock;

    @Mocked
    private ReportsUtil reportsUtilMock;

    private IFTAService iftaServiceSUT;

    @Before
    public void setUpSUT() {
        iftaServiceSUT = new IFTAServiceImpl(reportsFacadeMock, reportsUtilMock);
    }

    @Test
    public void testGetStateMileageByMonthDefaults() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.checkParameters(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
                result = null;

                reportsFacadeMock.getStateMileageByMonth(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(false));
                times = 1;
            }
        };

        iftaServiceSUT.getStateMileageByMonthDefaults(SAMPLE_GROUP_ID);
    }

    @Test
    public void testGetStateMileageByMonthWithDates() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.checkParameters(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
                result = null;

                reportsFacadeMock.getStateMileageByMonth(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(false));
            }
        };

        iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    }

    @Test
    public void testGetStateMileageByMonthWithIfta() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusDays(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.checkParameters(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
                result = null;

                reportsFacadeMock.getStateMileageByMonth(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(true));
            }
        };

        iftaServiceSUT.getStateMileageByMonthWithIfta(SAMPLE_GROUP_ID);
    }

    @Test
    public void testGetStateMileageByMonthWithIftaAndDates() {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
        final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();

        final Interval interval = new Interval(expectedStartDate.getTime(), expectedEndDate.getTime());

        // Expectations & stubbing
        new Expectations() {
            {
                reportsUtilMock.checkParameters(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
                result = null;

                reportsFacadeMock.getStateMileageByMonth(withEqual(SAMPLE_GROUP_ID), withEqual(interval), withEqual(true));
            }
        };

        iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    }

    @Test
    public void testResponseErrorWhenParamValidationFails() {

        // For this test, any date will do.
        final Date expectedStartDate = new Date();
        final Date expectedEndDate = new Date();

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                reportsUtilMock.checkParameters((Integer) any, (Date) any, (Date) any);
                result = Response.status(Status.BAD_REQUEST).build();
            }
        };

        Response response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        new Verifications() {
            {
                reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
                times = 0;
            }
        };
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
                reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
                result = new ArrayList();
            }
        };

        Response response = iftaServiceSUT.getStateMileageByMonthDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
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
                reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
                result = null;
            }
        };

        Response response = iftaServiceSUT.getStateMileageByMonthDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
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
                reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
                result = new RuntimeException("Dummy exception");
            }
        };

        Response response = iftaServiceSUT.getStateMileageByMonthDefaults(SAMPLE_GROUP_ID);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIfta(SAMPLE_GROUP_ID);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    // @Test
    // public void testReturns400WhenInvalidDates() {
    //
    // final DateMidnight dateMidnight = new DateMidnight();
    //
    // // Start date after end date.
    // final Date expectedStartDate = dateMidnight.minusMonths(SIX_UNITS).toDate();
    // final Date expectedEndDate = dateMidnight.minusYears(SIX_UNITS).toDate();
    //
    // // Expectations & stubbing
    // new Expectations() {
    // {
    // reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
    // times = 0;
    // }
    // };
    //
    // Response response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    // }
    //
    // @Test
    // public void testReturns400WhenNullGroupId() {
    //
    // final DateMidnight dateMidnight = new DateMidnight();
    //
    // final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
    // final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();
    //
    // // Expectations & stubbing
    // new Expectations() {
    // {
    // reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
    // times = 0;
    // }
    // };
    //
    // Response response = iftaServiceSUT.getStateMileageByMonthDefaults(null);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithIfta(null);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithDates(null, expectedStartDate, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(null, expectedStartDate, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    // }
    //
    // @Test
    // public void testReturns400WhenNullStartDate() {
    //
    // final DateMidnight dateMidnight = new DateMidnight();
    //
    // final Date expectedEndDate = dateMidnight.minusMonths(SIX_UNITS).toDate();
    //
    // // Expectations & stubbing
    // new Expectations() {
    // {
    // reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
    // times = 0;
    // }
    // };
    //
    // Response response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, null, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, null, expectedEndDate);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    // }
    //
    // @Test
    // public void testReturns400WhenNullEndDate() {
    //
    // final DateMidnight dateMidnight = new DateMidnight();
    //
    // final Date expectedStartDate = dateMidnight.minusYears(SIX_UNITS).toDate();
    //
    // // Expectations & stubbing
    // new Expectations() {
    // {
    // reportsFacadeMock.getStateMileageByMonth((Integer) any, (Interval) any, anyBoolean);
    // times = 0;
    // }
    // };
    //
    // Response response = iftaServiceSUT.getStateMileageByMonthWithDates(SAMPLE_GROUP_ID, expectedStartDate, null);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //
    // response = iftaServiceSUT.getStateMileageByMonthWithIftaAndDates(SAMPLE_GROUP_ID, expectedStartDate, null);
    // assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    // }
}
