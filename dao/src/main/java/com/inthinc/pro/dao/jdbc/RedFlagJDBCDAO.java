package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * RedFlag DAO.
 */
public class RedFlagJDBCDAO extends SimpleJdbcDaoSupport implements RedFlagDAO {
    private static String RED_FLAG_QUERY = "SELECT cnv.noteID,cnv.driverID,cnv.vehicleID,cnv.type,cnv.aggType,cnv.time," +
            " cnv.speed, cnv.flags, cnv.latitude, cnv.longitude, cnv.topSpeed, cnv.avgSpeed, cnv.speedLimit, cnv.status," +
            " cnv.distance, cnv.deltaX, cnv.deltaY, cnv.deltaZ, cnv.forgiven, cnv.flagged, cnv.level, cnv.sent, cnv.idleLo, cnv.idleHi, cnv.zoneID, cnv.textId," +
            " cnv.textMsg, cnv.hazmatFlag, cnv.serviceId, cnv.trailerId, cnv.trailerIdOld, cnv.inspectionType, cnv.vehicleSafeToOperate, cnv.duration, cnv.groupID," +
            " cnv.driverGroupID, cnv.vehicleGroupID, cnv.personID, cnv.driverName, cnv.groupName, cnv.vehicleName, cnv.tzID, cnv.tzName  FROM cachedNoteView cnv " +
            " WHERE cnv.groupId in (select groupID from groups where groupPath like :groupID) and flagged=1 and cnv.forgiven=:forgiven and cnv.time between :startDate and :endDate ";
    private static String RED_FLAG_QUERY_COUNT = "SELECT count(*) as nr FROM cachedNoteView WHERE groupId in (select groupID from groups where groupPath like :groupID) and flagged=1 and time between :startDate and :endDate ";

    private static final Map<String, String> pagedColumnMapRedFlag = new HashMap<String, String>();

    static {
        pagedColumnMapRedFlag.put("level", "cnv.level");
        pagedColumnMapRedFlag.put("time", "cnv.time");
        pagedColumnMapRedFlag.put("groupName", "cnv.groupName");
        pagedColumnMapRedFlag.put("driverName", "cnv.driverName");
        pagedColumnMapRedFlag.put("vehicleName", "cnv.vehicleName");
        pagedColumnMapRedFlag.put("status", "cnv.status");
        pagedColumnMapRedFlag.put("type", "cnv.type");

    }

    private static final Map<String, String> pagedColumnMapRedFlagCount = new HashMap<String, String>();

    static {
        pagedColumnMapRedFlagCount.put("level", "level");
        pagedColumnMapRedFlagCount.put("time", "time");
        pagedColumnMapRedFlagCount.put("groupName", "groupName");
        pagedColumnMapRedFlagCount.put("driverName", "driverName");
        pagedColumnMapRedFlagCount.put("vehicleName", "vehicleName");
        pagedColumnMapRedFlagCount.put("status", "status");
        pagedColumnMapRedFlagCount.put("type", "type");

    }

    private EventDAO eventDAO;

    private ParameterizedRowMapper<RedFlag> redFlagParameterizedRowMapper = new ParameterizedRowMapper<RedFlag>() {
        @Override
        public RedFlag mapRow(ResultSet rs, int rowNum) throws SQLException {
            SimpleDateFormat dateFormat = getDateFormat(TimeZone.getTimeZone("UTC"));

            String strTime = rs.getString("cnv.time");
            Date time = null;
            try {
                time = dateFormat.parse(strTime);
            } catch (Exception e) {
                logger.error(e);
            }

            Event event = new Event();
            event.setNoteID(rs.getLong("cnv.noteID"));
            event.setForgiven(rs.getInt("cnv.forgiven"));
            event.setFlags(rs.getInt("cnv.flags"));
            event.setLatitude(rs.getDouble("cnv.latitude"));
            event.setLongitude(rs.getDouble("cnv.longitude"));
            event.setSpeed(rs.getInt("cnv.speed"));
            event.setTime(time);
            event.setType(NoteType.valueOf(rs.getInt("cnv.type")));
            event.setVehicleID(rs.getInt("cnv.vehicleID"));
            event.setDriverID(rs.getInt("cnv.driverID"));
            event.setGroupID(rs.getInt("cnv.groupID"));
            event.setDriverName(rs.getString("cnv.driverName"));
            event.setVehicleName(rs.getString("cnv.vehicleName"));
            event.setGroupName(rs.getString("cnv.groupName"));
            event.setDriverTimeZone(TimeZone.getTimeZone(rs.getString("cnv.tzName")));
            event.setSpeedLimit(rs.getInt("cnv.speedLimit"));


            List<Integer> msgIDList = new ArrayList<Integer>();
            msgIDList.add(rs.getInt("cnv.textId"));

            RedFlag redFlagItem = new RedFlag();
            redFlagItem.setEvent(event);
//            redFlagItem.setModified(modified);
//            redFlagItem.setCreated(created);
            redFlagItem.setLevel(RedFlagLevel.valueOf(rs.getInt("cnv.level")));
            redFlagItem.setMsgIDList(msgIDList);
            redFlagItem.setSent(AlertSentStatus.valueOf(rs.getInt("cnv.sent")));
            redFlagItem.setTimezone(TimeZone.getTimeZone(rs.getString("cnv.tzName")));

            return redFlagItem;
        }
    };

    @Override
    public Integer getRedFlagCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<TableFilterField> filterList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        //it seems the old hessian is ignoring the includeForgiven verification too and only selects the ones that are not forgiven
        if (includeForgiven == 1 || includeForgiven == 0) {
            params.put("forgiven", 0);
        }
        /***FILTERING***/
        StringBuilder redFlagSelectCount = new StringBuilder(addFiltersToQuery(filterList, RED_FLAG_QUERY_COUNT, params, pagedColumnMapRedFlagCount));
        if (includeForgiven == 1 || includeForgiven == 0) {
            params.put("forgiven", 0);
            redFlagSelectCount.append(" and forgiven=:forgiven");
        }

        List<Integer> cntRedFlag = getSimpleJdbcTemplate().query(redFlagSelectCount.toString(), redFlagCountRowMapper, params);

        Integer cnt = 0;
        if (cntRedFlag != null && !cntRedFlag.isEmpty())
            cnt = cntRedFlag.get(0);
        return cnt;
    }

    @Override
    public List<RedFlag> getRedFlagPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        //it seems the old hessian is ignoring the includeForgiven verification too and only selects the ones that are not forgiven
        if (includeForgiven == 1 || includeForgiven == 0) {
            params.put("forgiven", 0);
        }

        /***FILTERING***/
        StringBuilder redFlagSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), RED_FLAG_QUERY, params, pagedColumnMapRedFlag));
        if (includeForgiven == 1 || includeForgiven == 0) {
            params.put("forgiven", 0);
            redFlagSelect.append(" and forgiven=:forgiven");
        }

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            redFlagSelect.append(" ORDER BY " + pagedColumnMapRedFlag.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            redFlagSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<RedFlag> redFlagList = getSimpleJdbcTemplate().query(redFlagSelect.toString(), redFlagParameterizedRowMapper, params);

        return redFlagList;
    }

    private TableFilterField treatCustomFilters(TableFilterField filter) {

        if (filter.getFilter() == null || filter.getFilter().toString().trim().isEmpty())
            return filter;

        String filterVal = filter.getFilter().toString();

        // status
        if (filter.getField().equals("status") && !isNumeric(filterVal)) {
            filter.setFilter(Status.valueOf(filterVal).getCode());
        }

        //product version
        if (filter.getField().equals("productVersion")) {
            if (filter.getFilter() instanceof List) {
                filter.setFilterOp(FilterOp.IN);
            } else if (!isNumeric(filterVal)) {
                ProductType productType = ProductType.valueOf(filterVal);
                List<Integer> versionList = (List<Integer>) productType.getFilter();
                if (versionList != null && !versionList.isEmpty()) {
                    filter.setFilterOp(FilterOp.IN);
                    filter.setFilter(versionList);
                }
            }
        }

        return filter;
    }

    private boolean isNumeric(String str) {
        boolean ret = true;
        try {
            Integer.valueOf(str.trim());
        } catch (NumberFormatException nf) {
            ret = false;
        }
        return ret;
    }

    private String addFiltersToQuery(final List<TableFilterField> filters,
                                     String queryStr, Map<String, Object> params, Map<String, String> pagedColumnMap) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder countFilter = new StringBuilder();
            for (TableFilterField filter : filters) {
                filter = treatCustomFilters(filter);

                if (filter.getField() != null && pagedColumnMap.containsKey(filter.getField()) && filter.getFilter() != null) {
                    String paramName = "filter_" + filter.getField();
                    if (filter.getFilter().toString().isEmpty())
                        continue;
                    if (filter.getFilterOp() == FilterOp.IN) {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ")");
                        params.put(paramName, filter.getFilter());

                    } else if (filter.getFilterOp() == FilterOp.IN_OR_NULL) {
                        countFilter.append(" AND (" + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ") OR " + pagedColumnMap.get(filter.getField()) + " IS NULL)");
                        params.put(paramName, filter.getFilter());

                    } else {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " LIKE :" + paramName);
                        params.put(paramName, "%" + filter.getFilter().toString() + "%");
                    }

                }
            }
            queryStr = queryStr + countFilter.toString();
        }
        return queryStr;
    }

    private ParameterizedRowMapper<Integer> redFlagCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }


    @Override
    public RedFlag findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, RedFlag entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(RedFlag entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }

    public static SimpleDateFormat getDateFormat(TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
