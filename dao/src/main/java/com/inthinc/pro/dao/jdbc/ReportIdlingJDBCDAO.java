package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final String IDLING_REPORT_DATA = "SELECT di.driverID as driverID, di.driverName as driverName, di.groupID as groupID, di.groupName as groupName, sum(agg.driveTime) as driveTime,"
            + "sum(agg.idleLo) as lowIdleTime, sum(agg.idleHi) as highIdleTime, (BIT_OR(agg.emuFeatureMask) & 4 != 0) as hasRPM, driver.status " + "FROM driverInfo di "
            + "LEFT JOIN driver ON (driver.driverID = di.driverID) "
            + "LEFT JOIN agg on agg.driverID=di.driverID WHERE di.groupID in (select groupID from groups where groupPath like :groupID)" +
            " AND agg.aggDate between :intervalStart AND :intervalEnd " +
            " GROUP BY di.driverID;";

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

    public void createTest(int groupId, Date date) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupId", String.valueOf(groupId));
        params.put("groupPath", "/" + String.valueOf(groupId) + "/");
        getSimpleJdbcTemplate().update("insert into agg (aggID, driverID, vehicleID,deviceID,odometer1,odometer2,odometer3,odometer4,odometer5,odometer6,coachingEvents,  " +
                "mpgOdometer1,mpgOdometer2,mpgOdometer3,idleHi,idleLo,driveTime,trips,seatbeltCoaching,speedCoaching1,speedCoaching2,speedCoaching3,speedCoaching4,speedCoaching5, " +
                "seatbeltEvents,seatbeltClicks,speedEvents1,speedEvents2,speedEvents3,speedEvents4,speedEvents5,speedEvents1To7MphOver, speedEvents8To14MphOver, speedEvents15PlusMphOver, speedEventsOver80Mph," +
                "aggressiveBrakeEvents,aggressiveAccelEvents,aggressiveLeftEvents,aggressiveRightEvents, aggressiveBumpEvents,speedOdometer1, speedOdometer2, speedOdometer3,speedOdometer4, speedOdometer5," +
                "idleLoEvents, idleHiEvents,rpmEvents,crashEvents, emuFeatureMask, emuRpmDriveTime, driverStartingOdometer, driverEndingOdometer, driverCrashOdometer, driverCrashDays, driverCrashTotal," +
                " vehicleStartingOdometer, vehicleEndingOdometer, vehicleCrashOdometer,vehicleCrashTotal, backingEvents,backingTime,aggDate,modified )" +
                " values ( 8888888, 1111111 ,4069,0,0,0,0,0,1,1,0, 3,0,0,0,0,4,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,800,1,2,3,0,0,0,0,0,0,'2013-09-13','2014-09-13');", params);


        getSimpleJdbcTemplate().update("insert into driverInfo (groupId, driverID, driverName) values (:groupId, 1111111, 'test-name');", params);
        getSimpleJdbcTemplate().update("insert into groups (`desc`,groupId, acctID, name, parentID,status,groupPath) values ( 'test-desc',:groupId, '1', 'test-name', 4, 4,:groupPath);", params);
    }

    public void deleteTest(int groupId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupId", String.valueOf(groupId));
        getSimpleJdbcTemplate().update("delete from driverInfo where groupId = :groupId;", params);
        getSimpleJdbcTemplate().update("delete from groups where groupId = :groupId;", params);
        getSimpleJdbcTemplate().update("delete from agg where aggID = 8888888", params);

    }

}
