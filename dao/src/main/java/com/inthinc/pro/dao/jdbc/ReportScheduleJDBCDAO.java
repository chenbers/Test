package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportScheduleJDBCDAO extends SimpleJdbcDaoSupport implements ReportScheduleDAO {

    private static final String ACTIVE_REPORT_SCHEDULES = "select r.* from reportPref r,user u where r.status=1 and r.acctID=:acctID and u.userID=r.userID and u.status=1";

    private static ParameterizedRowMapper<ReportSchedule> reportScheduleRowMapper = new ParameterizedRowMapper<ReportSchedule>() {
        @Override
        public ReportSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReportSchedule reportSchedule = new ReportSchedule();
            reportSchedule.setAccountID(NumberUtil.objectToInteger(rs.getObject("acctID")));
            reportSchedule.setStartDate(rs.getDate("startDate"));
            reportSchedule.setLastDate(rs.getDate("lastDate"));
            if (reportSchedule.getLastDate().before(reportSchedule.getStartDate())) {
                reportSchedule.setLastDate(null);
            }
            reportSchedule.setName(rs.getString("name"));
            reportSchedule.setOccurrence(Occurrence.valueOf(rs.getInt("occurrence")));
            reportSchedule.setParamType(ReportParamType.valueOf(rs.getInt("paramType")));
            reportSchedule.setReportID(NumberUtil.objectToInteger(rs.getObject("reportID")));
            reportSchedule.setUserID(NumberUtil.objectToInteger(rs.getObject("userID")));
            reportSchedule.setStatus(Status.valueOf(rs.getInt("status")));
            reportSchedule.setReportScheduleID(NumberUtil.objectToInteger(rs.getObject("reportPrefID")));
            reportSchedule.setTimeOfDay(rs.getInt("timeOfDay"));
            return reportSchedule;
        }

        ;
    };

    @Override
    public List<ReportSchedule> getActiveReportSchedulesByAccountID(Integer accountID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", accountID);
        return getSimpleJdbcTemplate().query(ACTIVE_REPORT_SCHEDULES, reportScheduleRowMapper, params);
    }

    @Override
    public ReportSchedule findByID(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer id, ReportSchedule entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(ReportSchedule entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public List<ReportSchedule> getReportSchedulesByUserID(Integer userID) {
        throw new NotImplementedException();
    }

    @Override
    public List<ReportSchedule> getReportSchedulesByAccountID(Integer accountID) {
        throw new NotImplementedException();
    }

    @Override
    public List<ReportSchedule> getReportSchedulesByUserIDDeep(Integer userID) {
        throw new NotImplementedException();
    }
}
