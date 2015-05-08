package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.ReportIdlingDAO;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.Status;

public class ReportIdlingJDBCDAO extends SimpleJdbcDaoSupport implements ReportIdlingDAO {
    //Instead of the hessian ReportDAO for idling
    private static final String IDLING_REPORT_DATA = "SELECT di.driverID as driverID, " +
            "       di.driverName as driverName, " +
            "       vi.groupID as groupID, " +
            "       g.name as groupName, " +
            "       agg.vehicleID as vehicleID, " +
            "       vi.name as vehicleName, " +
            "       sum(agg.driveTime) as driveTime, " +
            "       sum(agg.idleLo) as lowIdleTime, " +
            "       sum(agg.idleHi) as highIdleTime, " +
            "       (BIT_OR(agg.emuFeatureMask) & 4 != 0) as hasRPM, " +
            "       vi.status " +
            "  FROM driverInfo di " +
            "  JOIN agg " +
            "    on agg.driverID = di.driverID " +
            "    and agg.aggDate between :intervalStart AND :intervalEnd " +
            "  JOIN vehicle vi " +
            "    on agg.vehicleID = vi.vehicleID     " +
            "  JOIN groups g " +
            "    on vi.groupID = g.groupID    " +
            "    and g.groupId in " +
            "       (select g.groupID from groups g where g.groupPath like :groupID) " +
            " GROUP BY agg.vehicleID ";

    @Override
    public List<IdlingReportItem> getIdlingReportData(Integer groupID, Interval interval) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingReportSelect = new StringBuilder(IDLING_REPORT_DATA);
        List<IdlingReportItem> idlingReportItemList = getSimpleJdbcTemplate().query(idlingReportSelect.toString(), idlingReportRowMapper, params);

        return idlingReportItemList;

    }

    private ParameterizedRowMapper<IdlingReportItem> idlingReportRowMapper = new ParameterizedRowMapper<IdlingReportItem>() {
        @Override
        public IdlingReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            IdlingReportItem idlingReportItem = new IdlingReportItem();
            idlingReportItem.setDriverID(rs.getInt("driverID"));
            idlingReportItem.setDriverName(rs.getString("driverName"));
            idlingReportItem.setDriveTime(rs.getInt("driveTime"));
            idlingReportItem.setGroupID(rs.getInt("groupID"));
            idlingReportItem.setGroupName(rs.getString("groupName"));
            idlingReportItem.setHasRPM(rs.getInt("hasRPM"));
            idlingReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
            idlingReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
            idlingReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            return idlingReportItem;
        }
    };

}
