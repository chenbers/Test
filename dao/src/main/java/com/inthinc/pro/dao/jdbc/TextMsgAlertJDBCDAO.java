package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.mysql.jdbc.util.TimezoneDump;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class TextMsgAlertJDBCDAO extends SimpleJdbcDaoSupport implements TextMsgAlertDAO{

    private static final Map<String, String> pagedColumnMapTxtMsg = new HashMap<String, String>();

    static {
        pagedColumnMapTxtMsg.put("sendDate", "c.time");
        pagedColumnMapTxtMsg.put("from", "c.driverName");
        pagedColumnMapTxtMsg.put("message", "c.textMsg");
        pagedColumnMapTxtMsg.put("driverID", "c.driverID");
        pagedColumnMapTxtMsg.put("vehicleID", "c.vehicleID");
        pagedColumnMapTxtMsg.put("textID", "c.textID");
    }

    private static final String GET_MESSAGEITEM = "SELECT * FROM message m JOIN alert a ON a.alertID=m.alertID JOIN cachedNoteView c on c.noteID=m.noteID "+
                    "JOIN timezone t on t.tzID=c.tzID where a.acctID=:acctID and a.status != 3";

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);   }

    private ParameterizedRowMapper<MessageItem> messageItemParameterizedRowMapper = new ParameterizedRowMapper<MessageItem>(){
        @Override
        public MessageItem mapRow(ResultSet rs, int rowNum) throws SQLException {
         MessageItem messageItem = new MessageItem();
            messageItem.setSendDate(getDateOrNullFromRS(rs,"c.time"));
            messageItem.setFromDriverID(getIntOrNullFromRS(rs,"c.driverID"));
            messageItem.setFromVehicleID(getIntOrNullFromRS(rs,"c.vehicleID"));
            messageItem.setFrom(getStringOrNullFromRS(rs,"c.driverName"));
            messageItem.setMessage(getStringOrNullFromRS(rs,"c.textMsg"));
            messageItem.setDmrOffset(getIntOrNullFromRS(rs,"c.textID"));
            messageItem.setType(getIntOrNullFromRS(rs,"c.type"));
            messageItem.setTimeZone(TimeZone.getTimeZone(getStringOrNullFromRS(rs, "t.tzName")));
            return messageItem;
        }
    };

    @Override
    public List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        List<MessageItem> messages = getSimpleJdbcTemplate().query(GET_MESSAGEITEM, messageItemParameterizedRowMapper, params);

        return messages ;
    }

    @Override
    public Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList) {
        return null;
    }

    @Override
    public List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put ("startDate", startDate);
        params.put ("endDate", endDate);
        StringBuilder txtMsgSelect= new StringBuilder();
        txtMsgSelect.append("SELECT * FROM cachedNoteView c join timezone t on c.tzID=t.tzID where c.time >= :startDate AND c.time<= :endDate AND c.groupID IN " +
                        "(select groupID from groups where groupPath like :groupID and status != 3) AND c.type in (72,80,91,92) and c.forgiven=0");

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
        return null;
    }

    @Override
    public MessageItem findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, MessageItem entity) {
        return null;
    }

    @Override
    public Integer update(MessageItem entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return null;
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
}
