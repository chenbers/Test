package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class TextMsgAlertJDBCDAO extends SimpleJdbcDaoSupport implements TextMsgAlertDAO {

    private static final Map<String, String> pagedColumnMapTxtMsg = new HashMap<String, String>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    static {
        pagedColumnMapTxtMsg.put("sendDate", "c.time");
        pagedColumnMapTxtMsg.put("from", "c.driverName");
        pagedColumnMapTxtMsg.put("message", "c.textMsg");
        pagedColumnMapTxtMsg.put("driverID", "c.driverID");
        pagedColumnMapTxtMsg.put("vehicleID", "c.vehicleID");
        pagedColumnMapTxtMsg.put("textID", "c.textID");
    }

    private static final String GET_MESSAGEITEM = "SELECT * FROM message m JOIN alert a ON a.alertID=m.alertID JOIN cachedNoteView c on c.noteID=m.noteID " +
                    "JOIN timezone t on t.tzID=c.tzID where a.acctID=:acctID and a.status != 3";

    private static final String GET_MESSAGEITEM2 = "SELECT " +
                    "  allz.* " +
                    "FROM ((SELECT " +
                    "  created AS time, " +
                    "  fwdStr AS message, " +
                    "  v.vehicleID, " +
                    "  d.driverID, " +
                    "  fwdCmd, " +
                    "  (SELECT " +
                    "    CONCAT(first, ' ', last) " +
                    "  FROM person p2 " +
                    "  WHERE p2.personID = f.personID) AS \"FROM\", " +
                    "  (SELECT " +
                    "    CONCAT(first, ' ', last) " +
                    "  FROM person p2 " +
                    "  WHERE p2.personID = d.personID) AS \"TO\" " +
                    "FROM driver d, " +
                    "     fwd f, " +
                    "     vehicle v " +
                    "WHERE f.driverID = d.driverID " +
                    "AND f.vehicleID = v.vehicleID " +
                    "AND v.groupID IN (SELECT " +
                    "  w.groupID " +
                    "FROM (SELECT " +
                    "  groupID, " +
                    "  groupPath " +
                    "FROM groups " +
                    "WHERE groupPath LIKE CONCAT('%/', :groupID, '/%') AND status != 3 " +
                    "UNION " +
                    "SELECT " +
                    "  0 AS groupID, " +
                    "  '/0/' AS groupPath " +
                    "FROM dual) w) AND d.groupID IN (SELECT " +
                    "  k.groupID " +
                    "FROM (SELECT " +
                    "  groupID, " +
                    "  groupPath " +
                    "FROM groups " +
                    "WHERE groupPath LIKE CONCAT('%/', :groupID, '/%') AND status != 3 " +
                    "UNION " +
                    "SELECT " +
                    "  0 AS groupID, " +
                    "  '/0/' AS groupPath " +
                    "FROM dual) k)) " +
                    " " +
                    "UNION (SELECT " +
                    "  created AS time, " +
                    "  data AS message, " +
                    "  v.vehicleID, " +
                    "  d.driverID, " +
                    "  command fwdCmd, " +
                    "  (SELECT " +
                    "    CONCAT(first, ' ', last) " +
                    "  FROM person p2 " +
                    "  WHERE p2.personID = f.personID) AS \"FROM\", " +
                    "  (SELECT " +
                    "    CONCAT(first, ' ', last) " +
                    "  FROM person p2 " +
                    "  WHERE p2.personID = d.personID) AS \"TO\" " +
                    "FROM driver d, " +
                    "     Fwd_WSiridium f, " +
                    "     vehicle v " +
                    "WHERE f.driverID = d.driverID " +
                    "AND f.vehicleID = v.vehicleID " +
                    "AND v.groupID IN (SELECT " +
                    "  w.groupID " +
                    "FROM (SELECT " +
                    "  groupID, " +
                    "  groupPath " +
                    "FROM groups " +
                    "WHERE groupPath LIKE CONCAT('%/', :groupID, '/%') AND status != 3 " +
                    "UNION " +
                    "SELECT " +
                    "  0 AS groupID, " +
                    "  '/0/' AS groupPath " +
                    "FROM dual) w) AND d.groupID IN (SELECT " +
                    "  k.groupID " +
                    "FROM (SELECT " +
                    "  groupID, " +
                    "  groupPath " +
                    "FROM groups " +
                    "WHERE groupPath LIKE CONCAT('%/', :groupID, '/%') AND status != 3 " +
                    "UNION " +
                    "SELECT " +
                    "  0 AS groupID, " +
                    "  '/0/' AS groupPath " +
                    "FROM dual) k))) allz " +
                    "WHERE allz.time >= STR_TO_DATE(:startDate, '%d.%m.%Y') AND allz.time <= STR_TO_DATE(:endDate, '%d.%m.%Y') AND allz.fwdCmd = 355";

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getTimestamp(columnName);
    }

    private ParameterizedRowMapper<MessageItem> messageItemParameterizedRowMapper = new ParameterizedRowMapper<MessageItem>() {
        @Override
        public MessageItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessageItem messageItem = new MessageItem();
            messageItem.setSendDate(getDateOrNullFromRS(rs, "c.time"));
            messageItem.setFromDriverID(getIntOrNullFromRS(rs, "c.driverID"));
            messageItem.setFromVehicleID(getIntOrNullFromRS(rs, "c.vehicleID"));
            messageItem.setFrom(getStringOrNullFromRS(rs, "c.driverName"));
            messageItem.setMessage(getStringOrNullFromRS(rs, "c.textMsg"));
            messageItem.setDmrOffset(getIntOrNullFromRS(rs, "c.textID"));
            messageItem.setType(getIntOrNullFromRS(rs, "c.type"));
            messageItem.setTimeZone(TimeZone.getTimeZone(getStringOrNullFromRS(rs, "t.tzName")));
            return messageItem;
        }
    };

    private ParameterizedRowMapper<MessageItem> messageItemFwdParameterizedRowMapper = new ParameterizedRowMapper<MessageItem>() {
        @Override
        public MessageItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessageItem messageItem = new MessageItem();
            messageItem.setFromPortalSent(getDateOrNullFromRS(rs, "time"));
            messageItem.setFromDriverID(getIntOrNullFromRS(rs, "driverID"));
            messageItem.setFromVehicleID(getIntOrNullFromRS(rs, "vehicleID"));
            messageItem.setFromPortalFrom(getStringOrNullFromRS(rs, "FROM"));
            messageItem.setFrom(getStringOrNullFromRS(rs, "TO"));
            messageItem.setFromPortalMsg(getStringOrNullFromRS(rs, "message"));
            return messageItem;
        }
    };

    @Override
    public List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        List<MessageItem> messages = getSimpleJdbcTemplate().query(GET_MESSAGEITEM, messageItemParameterizedRowMapper, params);

        return messages;
    }

    @Override
    public Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        params.put("startDate", sdf.format(startDate));
        params.put("endDate", sdf.format(endDate));
        StringBuilder txtMsgSelect = new StringBuilder();
        txtMsgSelect.append("SELECT count(*) nr FROM cachedNoteView c join timezone t on c.tzID=t.tzID where c.time >= STR_TO_DATE(:startDate, '%d.%m.%Y') AND c.time<= STR_TO_DATE(:endDate, '%d.%m.%Y') AND c.groupID IN " +
                        "(select groupID from groups where groupPath like  concat('%/',:groupID,'/%') and status != 3) AND c.type in (72,80,91,92) and c.forgiven=0");
        txtMsgSelect = new StringBuilder(addFiltersToQuery(filterList, txtMsgSelect.toString(), params, pagedColumnMapTxtMsg));
        Integer cnt = getSimpleJdbcTemplate().queryForInt(txtMsgSelect.toString(), params);

        return cnt;
    }

    @Override
    public List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        params.put("startDate", sdf.format(startDate));
        params.put("endDate", sdf.format(endDate));
        StringBuilder txtMsgSelect = new StringBuilder();
        txtMsgSelect.append("SELECT * FROM cachedNoteView c join timezone t on c.tzID=t.tzID where c.time >= STR_TO_DATE(:startDate, '%d.%m.%Y') AND c.time<= STR_TO_DATE(:endDate, '%d.%m.%Y') AND c.groupID IN " +
                        "(select groupID from groups where groupPath like   concat('%/',:groupID,'/%')  and status != 3) AND c.type in (72,80,91,92) and c.forgiven=0");

        /***FILTERING***/
        txtMsgSelect = new StringBuilder(addFiltersToQuery(filterList, txtMsgSelect.toString(), params, pagedColumnMapTxtMsg));
        txtMsgSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), txtMsgSelect.toString(), params, pagedColumnMapTxtMsg));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            txtMsgSelect.append(" ORDER BY " + pagedColumnMapTxtMsg.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            txtMsgSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<MessageItem> messageItemList = getSimpleJdbcTemplate().query(txtMsgSelect.toString(), messageItemParameterizedRowMapper, params);
        return messageItemList;


    }

    @Override
    public List<MessageItem> getSentTextMsgsByGroupID(Integer groupID, Date startTime, Date stopTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        params.put("startDate", sdf.format(startTime));
        params.put("endDate", sdf.format(stopTime));
        List<MessageItem> messages = getSimpleJdbcTemplate().query(GET_MESSAGEITEM2, messageItemFwdParameterizedRowMapper, params);
        return messages;
    }

    @Override
    public MessageItem findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, MessageItem entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(MessageItem entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }

    private String addFiltersToQuery(final List<TableFilterField> filters,
                    String queryStr, Map<String, Object> params, Map<String, String> pagedColumnMap) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder countFilter = new StringBuilder();
            for (TableFilterField filter : filters) {
                filter = treatCustomFilters(filter);

                if (filter.getField() != null && pagedColumnMap.containsKey(filter.getField()) && filter.getFilter() != null) {
                    Range range = new Range();
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
                        if (filter.getFilter().getClass().isInstance(range)) {
                            range = (Range) filter.getFilter();
                            countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " BETWEEN " + range.getMin() + " and " + range.getMax());
                        } else {
                            countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " LIKE :" + paramName);
                            params.put(paramName, "%" + filter.getFilter().toString() + "%");
                        }
                    }

                }
            }
            queryStr = queryStr + countFilter.toString();
        }
        return queryStr;
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

    private ParameterizedRowMapper<Integer> MsgsCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };
}