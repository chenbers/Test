package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.pagination.PageParams;
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

    private static final String GET_MESSAGEITEM = "SELECT * FROM message m JOIN alert a ON a.alertID=m.alertID JOIN cachedNoteView c on c.noteID=m.noteID "+
                    "JOIN timezone t on t.tzID=c.tzID where a.acctID=:acctID";

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
            messageItem.setTimeZone(TimeZone.getTimeZone(getStringOrNullFromRS(rs,"t.tzName")));
            return messageItem;
        }
    };

    @Override
    public List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        List<MessageItem> messages = getSimpleJdbcTemplate().query(GET_MESSAGEITEM,messageItemParameterizedRowMapper,params);

        return messages ;
    }

    @Override
    public Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList) {
        return null;
    }

    @Override
    public List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {
        return null;
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


}
