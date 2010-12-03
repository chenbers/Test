package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.joda.time.MutableDateTime;
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
        Date todayBegin = getBeginningOfDay(today);
        Date todayEnd = getEndOfDay(today);
        ArrayList<TableFilterField> emptyList = new ArrayList<TableFilterField>();

        Integer result = this.redFlagDAO.getRedFlagCount(groupID, todayBegin, todayEnd, RedFlagDAO.INCLUDE_FORGIVEN, emptyList);

        return Response.ok(new Integer(result)).build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date)
     */
    @Override
    public Response getRedFlagCount(Integer groupID, Date startDate) {
        Date today = systemClock.getNow();
        Date todayEnd = getEndOfDay(today);

        return getRedFlagCount(groupID, startDate, todayEnd);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getRedFlagCount(Integer groupID, Date startDate, Date endDate) {

        ArrayList<TableFilterField> emptyList = new ArrayList<TableFilterField>();
        Integer result = this.redFlagDAO.getRedFlagCount(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, emptyList);

        return Response.ok(new Integer(result)).build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord) {
        Date today = systemClock.getNow();
        Date todayBegin = getBeginningOfDay(today);
        Date todayEnd = getEndOfDay(today);

        PageParams expectedPageParams = createPageParams(firstRecord, lastRecord);

        List<RedFlag> result = redFlagDAO.getRedFlagPage(groupID, todayBegin, todayEnd, RedFlagDAO.INCLUDE_FORGIVEN, expectedPageParams);

        return Response.ok(result).build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord, Date startDate) {
        Date today = systemClock.getNow();
        Date todayEnd = getEndOfDay(today);

        return getRedFlags(groupID, firstRecord, lastRecord, startDate, todayEnd);
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date)
     */
    @Override
    public Response getRedFlags(Integer groupID, Integer firstRecord, Integer lastRecord, Date startDate, Date endDate) {
        PageParams expectedPageParams = createPageParams(firstRecord, lastRecord);

        List<RedFlag> result = redFlagDAO.getRedFlagPage(groupID, startDate, endDate, RedFlagDAO.INCLUDE_FORGIVEN, expectedPageParams);

        return Response.ok(result).build();
    }

    private Date getEndOfDay(Date day) {
        MutableDateTime todayEnd = new MutableDateTime(day);
        todayEnd.setHourOfDay(23);
        todayEnd.setMinuteOfHour(59);
        todayEnd.setSecondOfMinute(59);
        todayEnd.setMillisOfSecond(999);
        Date todayEnd2 = todayEnd.toDate();
        return todayEnd2;
    }

    private Date getBeginningOfDay(Date day) {
        MutableDateTime todayBegin = new MutableDateTime(day);
        todayBegin.setHourOfDay(0);
        todayBegin.setMinuteOfHour(0);
        todayBegin.setSecondOfMinute(0);
        todayBegin.setMillisOfSecond(0);
        Date todayBegin2 = todayBegin.toDate();
        return todayBegin2;
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
