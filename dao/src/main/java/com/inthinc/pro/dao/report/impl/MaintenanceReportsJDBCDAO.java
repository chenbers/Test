package com.inthinc.pro.dao.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.dao.jdbc.GenericJDBCDAO;
import com.inthinc.pro.dao.report.MaintenanceReportsDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MaintenanceReportItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.event.VehicleEventData;

public class MaintenanceReportsJDBCDAO extends GenericJDBCDAO implements MaintenanceReportsDAO {
    
    @Autowired
    AdminVehicleJDBCDAO adminVehicleJDBCDAO;
    
    private static final long serialVersionUID = 1L;
    
    // private static final String EVENT_PREV_DATE = "SELECT max(time) last_date FROM noteXX a WHERE a.vehicleID = ? and TYPE = ? AND attrs LIKE '%NCODE%' AND time < ?";
    private static final String EVENT_PREV_DATE = "select DATE(time) last_date, vehicleID from cachedNoteView  where vehicleID in (?) and TYPE in (?) and attribs like '%NCODE%' and time < ? group by vehicleID, DATE(time)";
    private static final String EVENT_PREV_DATE_MANY = "select DATE(time) last_date, vehicleID from cachedNoteView  where  time between ? and ? and vehicleID in (VEHICLEID_LIST) and TYPE in (NOTETYPE_LIST) and (ATTRIBS_SQL)   group by vehicleID, DATE(time)";
    
    private static final String REPORT_ODOMETER_AT_DATE = "select sum(a.odometer6) odiff from agg a where a.vehicleID = ? and a.aggDate <= ?";
    
    private static final String VEHICLES_WITH_THRESHOLDS = "SELECT avs.vehicleID vehicleID, v.name vehicleName, v.odometer odometer, v.absOdometer absOdometer, v.groupID vehicleGroupID, settingID, value, g.name vehicleGroupName, acctID, v.year year, v.make make, v.model model "
                    + "from actualVSet avs "
                    + "join vehicle v on (avs.vehicleID = v.vehicleID) "
                    + "join groups g on (v.groupID = g.groupID) "
                    + "where groupID in (_GROUP_ID_LIST_) "
                    + "  and settingID in (" + SettingType.MAINT_THRESHOLD_ENGINE_HOURS.getCode() + " , " + SettingType.MAINT_THRESHOLD_ODOMETER + ") " + "order by avs.vehicleID ";
    
    private static final String MILES_DRIVEN = "SELECT MAX(vs.endingOdometer) milesDriven FROM vehicleScoreByDay vs where vs.vehicleID in (_VEHICLE_ID_LIST_)";
    
    @Override
    public List<MaintenanceReportItem> getVehiclesWithThreshold(List<Integer> groupIDList) {
        List<MaintenanceReportItem> results = new ArrayList<MaintenanceReportItem>();
        // TODO Auto-generated method stub
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder group_id_list = new StringBuilder();
        String prefix = "";
        for (Integer groupID : groupIDList) {
            group_id_list.append(prefix);
            if (prefix.equals("")) {
                prefix = " , ";
            }
            group_id_list.append(groupID);
        }
        
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(VEHICLES_WITH_THRESHOLDS.replace("_GROUP_ID_LIST_", group_id_list.toString()));
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MaintenanceReportItem item = new MaintenanceReportItem();
                item.setVehicleName(resultSet.getString("vehicleName"));
                // item.setCreated(null);//base entity
                // item.setEventOdometer(eventOdometer);
                // item.setEventTime(null)
                // item.setEventType(null);
                // item.setEventValue(null);
                item.setGroupID(resultSet.getInt("vehicleGroupID"));
                item.setGroupName(resultSet.getString("vehicleGroupName"));
                // item.setModified(modified); //base entity
                item.setSettingType(SettingType.valueOf(resultSet.getInt("settingID")));
                item.setThreshold(resultSet.getDouble("value"));
                item.setVehicleName(resultSet.getString("value"));
                item.setVehicleID(resultSet.getInt("vehicleID"));
                StringBuffer ymmString = new StringBuffer();
                ymmString.append(resultSet.getString("year") + " ");
                ymmString.append(resultSet.getString("make") + " ");
                ymmString.append(resultSet.getString("model") + " ");
                item.setYmmString(ymmString.toString());
                
                Long absOdometer = resultSet.getObject("absOdometer") == null ? null : (resultSet.getLong("absOdometer"));
                Long odometer = resultSet.getObject("odometer") == null ? null : resultSet.getLong("odometer");
                if (absOdometer != null) {
                    item.setVehicleOdometer(Long.valueOf(absOdometer / 100l).intValue());
                } else if (odometer != null) {
                    Integer milesDriven = getMilesDriven(item.getVehicleID());
                    item.setVehicleOdometer(Long.valueOf((odometer + milesDriven) / 100).intValue());
                }
                
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally
        return results;
    }
    
    public Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());
        
        Long driveSince = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_ODOMETER_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, sqlEvDate);
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driveSince = resultSet.getLong("odiff");
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        } // end catch
        finally { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally
        
        return driveSince;
    }
    
    @Override
    public Integer getMilesDriven(Integer vehicleID) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<Integer, Integer> getMilesDriven(Set<Integer> vehicleIDs) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<Integer, Integer> getEngineHours(List<Integer> vehicleIDs) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<Integer, Integer> getBaseOdometer(List<Integer> vehicleIDs) {
        // TODO Auto-generated method stub
        return null;
    }
}
