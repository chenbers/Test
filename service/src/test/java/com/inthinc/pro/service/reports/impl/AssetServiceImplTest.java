package com.inthinc.pro.service.reports.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import mockit.Expectations;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.service.phonecontrol.Clock;

public class AssetServiceImplTest {

    private static final Integer SAMPLE_GROUP_ID = 77;
    private static final Integer PAGE_START_ROW = 1;
    private static final Integer PAGE_END_ROW = 20;

    @Test
    public void testGetsRedFlagCountForTodaysDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = dateMidnight.toDate();
        final Date expectedEndDate = dateMidnight.plusDays(1).toDate();

        // Some pseudo random time for today date.
        final Date today = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagCount(withEqual(SAMPLE_GROUP_ID), withEqual(expectedStartDate), (Date)any, 
                        withEqual(RedFlagDAO.INCLUDE_FORGIVEN), withEqual(new ArrayList<TableFilterField>()));
                result = 200;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlagCount(SAMPLE_GROUP_ID);
    }

    @Test
    public void testGetsRedFlagCountFromBeginDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Start date will be 2012-11-25 at some random time
        final Date startDate = new DateTime(2012, 11, 25, 13, 45, 27, 1).toDate();

        // Expected end date is today's
        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedStartDate = new DateMidnight(startDate).toDate();
        final Date expectedEndDate = dateMidnight.toDate();

        // Some pseudo random time for today's date.
        final Date today = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagCount(withEqual(SAMPLE_GROUP_ID), withEqual(expectedStartDate), (Date)any, withEqual(RedFlagDAO.INCLUDE_FORGIVEN),
                        withEqual(new ArrayList<TableFilterField>())); //NOTE: this expectation will fail if startDate is more than one year old
                result = 200;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlagCount(SAMPLE_GROUP_ID, startDate);
    }

    @Test
    public void testGetsRedFlagCountFromBeginDateToEndDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Start date will be 2010-11-25
        final Date startDate = new DateTime(2010, 11, 25, 0, 0, 0, 0).toDate();
        // End date will be 2010-12-31
        final Date endDate = new DateTime(2010, 12, 31, 0, 0, 0, 0).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                redflagDaoMock.getRedFlagCount(withEqual(SAMPLE_GROUP_ID), withEqual(startDate), (Date)any, withEqual(RedFlagDAO.INCLUDE_FORGIVEN),
                        withEqual(new ArrayList<TableFilterField>()));
                result = 200;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlagCount(SAMPLE_GROUP_ID, startDate, endDate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetsRedFlagsForTodaysDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        final DateMidnight dateMidnight = new DateMidnight();
        final Date expectedBeginningOfDay = dateMidnight.toDate();
        final Date expectedEndOfDay = dateMidnight.toDate();

        // Some pseudo random time for today date.
        final Date today = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647).toDate();

        final PageParams expectedPageParams = createPageParams();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagPage(withEqual(SAMPLE_GROUP_ID), withEqual(expectedBeginningOfDay), withEqual(expectedEndOfDay), withEqual(RedFlagDAO.INCLUDE_FORGIVEN), (PageParams) any);
                result = new ArrayList();
                forEachInvocation = new Object() {
                    @SuppressWarnings("unused")
                    void validate(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams) {
                        validatePageParamsAttributes(expectedPageParams, pageParams);
                    }
                };

            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetsRedFlagsFromBeginDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Start date will be 2012-11-25
        final Date startDate = new DateTime(2012, 11, 25, 0, 0, 0, 0).toDate();

        // Expected end date is today's
        DateMidnight dateMidnight = new DateMidnight();
        final Date expectedEndDate = dateMidnight.toDate();

        // Some pseudo random time for today's date.
        final Date today = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647).toDate();

        final PageParams expectedPageParams = createPageParams();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                //NOTE: this expectation will fail if startDate is more than one year old
                redflagDaoMock.getRedFlagPage(withEqual(SAMPLE_GROUP_ID), withEqual(startDate), withEqual(expectedEndDate), withEqual(RedFlagDAO.INCLUDE_FORGIVEN), (PageParams) any);
                result = new ArrayList();
                forEachInvocation = new Object() {
                    @SuppressWarnings("unused")
                    void validate(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams) {
                        validatePageParamsAttributes(expectedPageParams, pageParams);
                    }
                };
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, startDate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetsRedFlagsFromBeginDateToEndDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Start date will be 2010-11-25
        final Date startDate = new DateTime(2010, 11, 25, 0, 0, 0, 0).toDate();
        // End date will be 2010-12-31
        final Date endDate = new DateTime(2010, 12, 31, 0, 0, 0, 0).toDate();

        final PageParams expectedPageParams = createPageParams();

        // Expectations & stubbing
        new Expectations() {
            {
                redflagDaoMock.getRedFlagPage(withEqual(SAMPLE_GROUP_ID), withEqual(startDate), withEqual(endDate), withEqual(RedFlagDAO.INCLUDE_FORGIVEN), (PageParams) any);
                result = new ArrayList();
                forEachInvocation = new Object() {
                    @SuppressWarnings("unused")
                    void validate(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams) {
                        validatePageParamsAttributes(expectedPageParams, pageParams);
                    }
                };
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, startDate, endDate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFailsIfStartDateGreaterThanNow(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Expected end date is today's
        final DateMidnight dateMidnight = new DateMidnight();

        // Some pseudo random time for today's date.
        DateTime todayDateTime = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647);
        final Date today = todayDateTime.toDate();

        // Start date will be one day after current date
        final Date startDate = new DateTime(todayDateTime.getYear(), todayDateTime.getMonthOfYear(), todayDateTime.getDayOfMonth(), 0, 0, 0, 0).plusDays(1).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagCount((Integer) any, (Date) any, (Date) any, (Integer) any, (List) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        Response response = assetService.getRedFlagCount(SAMPLE_GROUP_ID, startDate);
        assertEquals(400, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCountFailsIfStartDateBeforeLastYear(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Expected end date is today's
        final DateMidnight dateMidnight = new DateMidnight();

        // Some pseudo random time for today's date.
        DateTime todayDateTime = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647);
        final Date today = todayDateTime.toDate();

        // Start date will be one year and one day before now
        final Date startDate = dateMidnight.minusYears(1).minusDays(1).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagCount((Integer) any, (Date) any, (Date) any, (Integer) any, (List) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        Response response = assetService.getRedFlagCount(SAMPLE_GROUP_ID, startDate);
        assertEquals(400, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCountFailsIfStartDateAfterEndDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // End date will be 2010-12-31
        final Date endDate = new DateTime(2010, 12, 30, 0, 0, 0, 0).toDate();
        // Start date will be 1 millisecond after end date
        final Date startDate = new DateTime(2010, 12, 31, 0, 0, 0, 0).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                redflagDaoMock.getRedFlagCount((Integer) any, (Date) any, (Date) any, (Integer) any, (List) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);

        Response response = assetService.getRedFlagCount(SAMPLE_GROUP_ID, startDate, endDate);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testGetFlagsFailsIfStartDateBeforeLastYear(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Expected end date is today's
        final DateMidnight dateMidnight = new DateMidnight();

        // Some pseudo random time for today's date.
        DateTime todayDateTime = new DateTime(dateMidnight.getYear(), dateMidnight.getMonthOfYear(), dateMidnight.getDayOfMonth(), 12, 35, 47, 647);
        final Date today = todayDateTime.toDate();

        // Start date will be one year and one day before now
        final Date startDate = dateMidnight.minusYears(1).minusDays(1).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                systemClock.getNow();
                result = today;

                redflagDaoMock.getRedFlagPage((Integer) any, (Date) any, (Date) any, (Integer) any, (PageParams) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);
        Response response = assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, startDate);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testgetFlagsFailsIfStartDateAfterEndDate(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // End date will be 2010-12-30
        final Date endDate = new DateTime(2010, 12, 30, 0, 0, 0, 0).toDate();
        // Start date will be 2010-12-31
        final Date startDate = new DateTime(2010, 12, 31, 0, 0, 0, 0).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                redflagDaoMock.getRedFlagPage((Integer) any, (Date) any, (Date) any, (Integer) any, (PageParams) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);

        Response response = assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, startDate, endDate);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testFailsIfFirstRowGreaterThanLastRow(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Start date
        final Date startDate = new DateTime(2010, 12, 1, 0, 0, 0, 0).toDate();
        // End date
        final Date endDate = new DateTime(2010, 12, 31, 0, 0, 0, 0).toDate();

        // Expectations & stubbing
        new Expectations() {
            {
                redflagDaoMock.getRedFlagPage((Integer) any, (Date) any, (Date) any, (Integer) any, (PageParams) any);
                times = 0;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);

        Response response = assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_END_ROW, PAGE_START_ROW, startDate, endDate);
        assertEquals(400, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturns404IfRedflagListIsEmpty(final RedFlagDAO redflagDaoMock, final Clock systemClock) {

        // Expectations & stubbing
        new Expectations() {
            {
                // First call test for empty list
                redflagDaoMock.getRedFlagPage((Integer) any, (Date) any, (Date) any, (Integer) any, (PageParams) any);
                result = new ArrayList();

                // Second call test for null
                redflagDaoMock.getRedFlagPage((Integer) any, (Date) any, (Date) any, (Integer) any, (PageParams) any);
                result = null;
            }
        };

        AssetServiceImpl assetService = new AssetServiceImpl(redflagDaoMock, systemClock);

        Response response = assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, new Date(), new Date());
        assertEquals(404, response.getStatus());

        response = assetService.getRedFlags(SAMPLE_GROUP_ID, PAGE_START_ROW, PAGE_END_ROW, new Date(), new Date());
        assertEquals(404, response.getStatus());
    }

    private PageParams createPageParams() {
        final PageParams expectedPageParams = new PageParams();
        expectedPageParams.setStartRow(PAGE_START_ROW);
        expectedPageParams.setEndRow(PAGE_END_ROW);
        expectedPageParams.setFilterList(new ArrayList<TableFilterField>());
        TableSortField sortCriteria = new TableSortField(SortOrder.DESCENDING, "time");
        expectedPageParams.setSort(sortCriteria);
        return expectedPageParams;
    }

    private void validatePageParamsAttributes(final PageParams expectedPageParams, PageParams pageParams) {
        assertEquals(pageParams.getStartRow(), expectedPageParams.getStartRow());
        assertEquals(pageParams.getEndRow(), expectedPageParams.getEndRow());
        assertEquals(pageParams.getSort().getField(), expectedPageParams.getSort().getField());
        assertEquals(pageParams.getSort().getOrder(), expectedPageParams.getSort().getOrder());
        assertTrue(pageParams.getFilterList().isEmpty());
    }
}
