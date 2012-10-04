package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.AdminReportScheduleDAO;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;

public class AdminReportScheduleJDBCDAO extends SimpleJdbcDaoSupport implements AdminReportScheduleDAO{

    @Override
    public ReportSchedule findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, ReportSchedule entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(ReportSchedule entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    private static final String REPORTPREFS_DEEP = "select r.acctID,r.reportPrefID,r.paramType,r.reportID,r.userID,r.name,r.occurrence,r.startDate,r.lastDate,r.status, p.first, p.last from reportPref r,user u, person p where p.personID =u.personID and r.userID=u.userID and r.acctID=:acctID and (u.userID = :userID or u.groupID in(:groupIDs))";
    private static ParameterizedRowMapper<ReportSchedule> reportScheduleRowMapper = new ParameterizedRowMapper<ReportSchedule>() {
        @Override
        public ReportSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReportSchedule reportSchedule = new ReportSchedule();
            reportSchedule.setAccountID(ObjectToInteger(rs.getObject("acctID")));
            reportSchedule.setStartDate(rs.getDate("startDate"));
            reportSchedule.setLastDate(rs.getDate("lastDate"));
            if(reportSchedule.getLastDate().before(reportSchedule.getStartDate())){
                reportSchedule.setLastDate(null);
            }
            reportSchedule.setName(rs.getString("name"));
            reportSchedule.setOccurrence(Occurrence.valueOf(rs.getInt("occurrence")));
            reportSchedule.setParamType(ReportParamType.valueOf(rs.getInt("paramType")));
            reportSchedule.setReportID(ObjectToInteger(rs.getObject("reportID")));
            reportSchedule.setUserID(ObjectToInteger(rs.getObject("userID")));
            reportSchedule.setStatus(Status.valueOf(rs.getInt("status")));
            reportSchedule.setReportScheduleID(ObjectToInteger(rs.getObject("reportPrefID")));
            reportSchedule.setFullName(rs.getString("first")+ " "+rs.getString("last"));
            return reportSchedule;
        };
    };

    public List<ReportSchedule> getReportSchedulesForUsersDeep(Integer userID, List<Integer> groupIDs, Integer acctID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", userID);
        params.put("groupIDs", groupIDs);
        params.put("acctID", acctID);
        return getSimpleJdbcTemplate().query(REPORTPREFS_DEEP, reportScheduleRowMapper, params);
    }

    private static Integer ObjectToInteger(Object theObj) {
        Integer theInteger;
        if (theObj == null) {
            theInteger = null;
        } else if (theObj instanceof Long) {
            theInteger = ((Long) theObj).intValue();
        } else {
            theInteger = null;
        }
        return theInteger;
    }

}
