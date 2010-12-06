package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.reports.AssetService;

/**
 * Default interface for Asset Reports Services.
 */
@Component
public class AssetServiceImpl implements AssetService {

    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";

    private static final int STATUS_BAD_REQUEST = 400;
    private RedFlagDAO redFlagDAO;
    private Clock systemClock;

    @Autowired
    public AssetServiceImpl(RedFlagDAO redFlagDAO, Clock systemClock) {
        this.redFlagDAO = redFlagDAO;
        this.systemClock = systemClock;
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer)
     */
    @Override
    public Response getRedFlagCount(Integer groupID) {
        Date today = systemClock.getNow();
        return getRedFlagCount(groupID, today, today);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date)
     */
    @Override
    public Response getRedFlagCount(Integer groupID, Date startDate) {
        Date today = systemClock.getNow();

        Date oneYearThreshold = getOneYearThresholdDate(today);

        if (startDate.before(oneYearThreshold)) {
            return Response.status(STATUS_BAD_REQUEST).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be before today minus one year.").build();
        }

        return getRedFlagCount(groupID, startDate, today);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getRedFlagCount(Integer groupID, Date startDate, Date endDate) {

        Date normalizedStartDate = getBeginningOfDay(startDate);
        Date normalizedEndDate = getBeginningOfDay(endDate);

        if (normalizedStartDate.after(normalizedEndDate)) {
            return Response.status(400).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be greater than end date (" + endDate + ").").build();
        }

        ArrayList<TableFilterField> emptyList = new ArrayList<TableFilterField>();
        Integer result = this.redFlagDAO.getRedFlagCount(groupID, normalizedStartDate, normalizedEndDate, RedFlagDAO.INCLUDE_FORGIVEN, emptyList);

        return Response.ok(new GenericEntity<Integer>(new Integer(result)) {}).build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord) {
        Date today = systemClock.getNow();
        return getRedFlags(groupID, firstRecord, lastRecord, today, today);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord, Date startDate) {
        Date today = systemClock.getNow();

        Date oneYearThreshold = getOneYearThresholdDate(today);

        if (startDate.before(oneYearThreshold)) {
            return Response.status(400).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be before today minus one year.").build();
        }

        return getRedFlags(groupID, firstRecord, lastRecord, startDate, today);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord, Date startDate, Date endDate) {
        Date normalizedStartDate = getBeginningOfDay(startDate);
        Date normalizedEndDate = getBeginningOfDay(endDate);

        if (firstRecord > lastRecord) {
            return Response.status(400).header(HEADER_ERROR_MESSAGE, "First recourd number can't be greater than last record number.").build();
        }

        if (normalizedStartDate.after(normalizedEndDate)) {
            return Response.status(400).header(HEADER_ERROR_MESSAGE, "Start date (" + startDate + ") can't be greater than end date (" + endDate + ")").build();
        }

        PageParams expectedPageParams = createPageParams(firstRecord, lastRecord);

        List<RedFlag> result = redFlagDAO.getRedFlagPage(groupID, normalizedStartDate, normalizedEndDate, RedFlagDAO.INCLUDE_FORGIVEN, expectedPageParams);

        return Response.ok(new GenericEntity<List<RedFlag>>(result) {}).build();
    }

    private Date getBeginningOfDay(Date day) {
        return new DateMidnight(day).toDate();
    }

    private Date getOneYearThresholdDate(Date day) {
        return new DateMidnight(day).minusYears(1).toDate();
    }

    private PageParams createPageParams(Integer firstRecord, Integer lastRecord) {
        PageParams expectedPageParams = new PageParams();
        expectedPageParams.setStartRow(firstRecord);
        expectedPageParams.setEndRow(lastRecord);
        expectedPageParams.setFilterList(new ArrayList<TableFilterField>());
        TableSortField sortCriteria = new TableSortField(SortOrder.DESCENDING, "time");
        expectedPageParams.setSort(sortCriteria);
        return expectedPageParams;
    }
}
